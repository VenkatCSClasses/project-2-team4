import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;
import javafx.scene.control.TextField;
import model.User;
import service.Authentication;
import database.UserRepo;
import database.DatabaseManager;

public class MainApp extends Application {

    private Authentication authService;
    private Stage primaryStage;
    private User currentUser;

    public void setPrimaryStage(Stage stage){
        this.primaryStage = stage;
    }

    @Override
    public void start(Stage stage) {
        this.primaryStage = stage;

        try {
            DatabaseManager.initialize();
            authService = new Authentication(new UserRepo());
        } catch (Exception e) {
            System.out.println("Database failed to initialize: " + e.getMessage());
        }

        stage.setScene(createLoginScene());
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

    public Scene createMainScene(User user) {
        this.currentUser = user;
        BorderPane newPane = new BorderPane();
        newPane.setStyle("-fx-background-color: grey;");

        Label l = new Label("Welcome, " + user.getUsername() + "!");

        Button bSearch = new Button("Search");
        Button bProfile = new Button("Profile");
        Button bSavedBooks = new Button("My Library");
        Button bLogout = new Button("Logout");

        Search test = new Search(user);
        SavedBooks test2 = new SavedBooks(user);
        bSearch.setOnAction(e -> primaryStage.setScene(test.createSearchScene()));        
        bProfile.setOnAction(e -> primaryStage.setScene(new Profile().createProfileScene()));
        bSavedBooks.setOnAction(e -> primaryStage.setScene(new SavedBooks(user).createSavedBooksScene()));
        bLogout.setOnAction(e -> primaryStage.setScene(createLoginScene()));

        HBox hbox = new HBox();
        hbox.setPadding(new Insets(15, 12, 15, 12));
        hbox.setSpacing(10);
        hbox.getChildren().addAll(bSearch, bProfile, bSavedBooks, bLogout);
        hbox.setAlignment(Pos.CENTER);
        hbox.setStyle("-fx-background-color: #CCD4BC;");

        VBox vbox = new VBox();
        vbox.setPadding(new Insets(10));
        vbox.setSpacing(8);
        vbox.getChildren().addAll(l);
        vbox.setAlignment(Pos.CENTER);
        vbox.setStyle("-fx-background-color: #CCD4BC;");

        newPane.setCenter(hbox);
        newPane.setTop(vbox);

        return new Scene(newPane, 1200, 800);
    }

    public Scene createLoginScene() {
        BorderPane p = new BorderPane();
        p.setStyle("-fx-background-color: grey;");
        Label l = new Label("Welcome! Please enter username and password.\nPress 'Register' if you don't have an account.");
        TextField usernameTF = new TextField();
        PasswordField passwordTF = new PasswordField();
        Button bLogin = new Button("Login");
        Button bRegister = new Button("Register");

        bLogin.setOnAction(e -> {
            try {
                User user = authService.login(usernameTF.getText(), passwordTF.getText());
                primaryStage.setScene(createMainScene(user));
            } catch (Exception ex) {
                l.setText("Login failed: " + ex.getMessage());
            }
        });

        bRegister.setOnAction(e -> {
            try {
                authService.register(usernameTF.getText(), passwordTF.getText());
                l.setText("Registered! Please log in.");
            } catch (Exception ex) {
                l.setText("Registration failed: " + ex.getMessage());
            }
        });



        VBox vbox = new VBox();
        vbox.setPadding(new Insets(10,10,15,12));
        vbox.setSpacing(15);
        vbox.getChildren().addAll(usernameTF, passwordTF, bLogin, bRegister);
        vbox.setPrefWidth(150);
        bLogin.setMinWidth(vbox.getPrefWidth());
        bRegister.setMinWidth(vbox.getPrefWidth());
        usernameTF.setMaxWidth(vbox.getPrefWidth());
        passwordTF.setMaxWidth(vbox.getPrefWidth());
        vbox.setAlignment(Pos.CENTER);
        vbox.setStyle("-fx-background-color: #CCD4BC;");



        HBox hbox = new HBox();
        hbox.setPadding(new Insets(15, 10, 15, 12));
        hbox.setSpacing(10);
        hbox.getChildren().addAll(l);
        hbox.setAlignment(Pos.CENTER);

        p.setCenter(vbox);
        p.setTop(hbox);
        return new Scene(p, 1200, 800);
    }
}