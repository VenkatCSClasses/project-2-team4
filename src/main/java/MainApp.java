import javafx.application.Application;
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
        Pane newPane = new Pane();
        newPane.setStyle("-fx-background-color: grey;");

        Label l = new Label("Welcome, " + user.getUsername() + "!");

        Button bSearch = new Button("Search");
       // Button bProfile = new Button("Profile");
        Button bSavedBooks = new Button("Saved Books");
        Button bLogout = new Button("Logout");

        Search test = new Search(user);
        SavedBooks test2 = new SavedBooks(user, null);
        bSearch.setOnAction(e -> primaryStage.setScene(test.createSearchScene()));        
        //bProfile.setOnAction(e -> primaryStage.setScene(test2.createScene()));
        //bSavedBooks.setOnAction(e -> primaryStage.setScene(new SavedBooks().createSavedBooksScene()));
        bLogout.setOnAction(e -> primaryStage.setScene(createLoginScene()));

        l.setLayoutX(200);           l.setLayoutY(20);
        bSearch.setLayoutX(200);     bSearch.setLayoutY(150);
       // bProfile.setLayoutX(200);    bProfile.setLayoutY(180);
        bSavedBooks.setLayoutX(200); bSavedBooks.setLayoutY(210);
        bLogout.setLayoutX(200);     bLogout.setLayoutY(250);

        newPane.getChildren().addAll(l, bSearch, bSavedBooks, bLogout);
        return new Scene(newPane, 600, 400);
    }

    public Scene createLoginScene() {
        Pane p = new Pane();
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

        l.setLayoutX(200);           l.setLayoutY(0);
        usernameTF.setLayoutX(200);  usernameTF.setLayoutY(150);
        passwordTF.setLayoutX(200);  passwordTF.setLayoutY(175);
        bLogin.setLayoutX(200);      bLogin.setLayoutY(210);
        bRegister.setLayoutX(260);   bRegister.setLayoutY(210);

        p.getChildren().addAll(l, usernameTF, passwordTF, bLogin, bRegister);
        return new Scene(p, 600, 400);
    }
}