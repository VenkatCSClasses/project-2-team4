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

import java.io.File;
import java.util.Random;

import database.DatabaseManager;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

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
        // newPane.setStyle("-fx-background-color: CCD4BC;");

        Label l = new Label("Welcome, " + user.getUsername() + "!");
        l.setStyle("-fx-font: 50px Courier;");

        Button bSearch = new Button("Search");
        bSearch.setPrefSize(100, 50);
        bSearch.setStyle("-fx-font: 15px Courier;");

        // Button bProfile = new Button("Profile");
        // bProfile.setPrefSize(100, 50);
        // bProfile.setStyle("-fx-font: 15px Courier;");

        Button bSavedBooks = new Button("My Library");
        bSavedBooks.setPrefSize(100, 50);
        bSavedBooks.setStyle("-fx-font: 15px Courier;");

        Button bLogout = new Button("Logout");
        bLogout.setPrefSize(100, 50);
        bLogout.setStyle("-fx-font: 15px Courier;");

        
        Search test = new Search(user);
        bSearch.setOnAction(e -> primaryStage.setScene(test.createSearchScene()));        
        //bProfile.setOnAction(e -> primaryStage.setScene(new Profile().createProfileScene()));
        bSavedBooks.setOnAction(e -> primaryStage.setScene(new SavedBooks(user).createSavedBooksScene()));
        bLogout.setOnAction(e -> primaryStage.setScene(createLoginScene()));

        ImageView bookIcon = new ImageView(new Image("resources/icon.png"));
        bookIcon.setFitHeight(150);
        bookIcon.setFitWidth(150);

        ImageView column1 = new ImageView(new Image("resources/bm1.jpg"));
        column1.setFitHeight(400);
        column1.setFitWidth(150);
        VBox column1Box = new VBox(column1);
        column1Box.setAlignment(Pos.CENTER);

        ImageView column2 = new ImageView(new Image("resources/bm1.jpg"));
        column2.setFitHeight(400);
        column2.setFitWidth(150);
        VBox column2Box = new VBox(column2);
        column2Box.setAlignment(Pos.CENTER);

        Random rand = new Random();
        int max = 28;
        int randomNum = rand.nextInt(max) + 1;

        ImageView coverImageView1 = new ImageView(new Image("covers/" + randomNum + ".jpg"));
        coverImageView1.setFitHeight(150);
        coverImageView1.setFitWidth(100);
        randomNum = rand.nextInt(max) + 1;

        ImageView coverImageView2 = new ImageView(new Image("covers/" + randomNum + ".jpg"));
        coverImageView2.setFitHeight(150);
        coverImageView2.setFitWidth(100);
        randomNum = rand.nextInt(max) + 1; 

        ImageView coverImageView3 = new ImageView(new Image("covers/" + randomNum + ".jpg"));
        coverImageView3.setFitHeight(150);
        coverImageView3.setFitWidth(100);
        randomNum = rand.nextInt(max) + 1; 

        ImageView coverImageView4 = new ImageView(new Image("covers/" + randomNum + ".jpg"));
        coverImageView4.setFitHeight(150);
        coverImageView4.setFitWidth(100);
        randomNum = rand.nextInt(max) + 1; 

        ImageView coverImageView5 = new ImageView(new Image("covers/" + randomNum + ".jpg"));
        coverImageView5.setFitHeight(150);
        coverImageView5.setFitWidth(100);

        HBox hbox = new HBox();
        hbox.setPadding(new Insets(15, 12, 15, 12));
        hbox.setSpacing(10);
        hbox.getChildren().addAll(bSearch, bSavedBooks, bLogout);
        hbox.setAlignment(Pos.CENTER);
        //hbox.setStyle("-fx-background-color: #CCD4BC;");

        HBox coverBox = new HBox();
        coverBox.setSpacing(10);
        coverBox.getChildren().addAll(coverImageView1, coverImageView2, coverImageView3, coverImageView4, coverImageView5);
        coverBox.setAlignment(Pos.CENTER);
        coverBox.setStyle("-fx-background-color: #a5ab98;");


        VBox vbox = new VBox();
        vbox.setPadding(new Insets(10));
        vbox.setSpacing(8);
        vbox.getChildren().addAll(l);
        vbox.setAlignment(Pos.CENTER);
        //vbox.setStyle("-fx-background-color: #a5ab98;");

        VBox icon = new VBox();
        vbox.setPadding(new Insets(10));
        vbox.setSpacing(8);
        vbox.getChildren().addAll(bookIcon);
        hbox.getChildren().add(icon);

        newPane.setCenter(hbox);
        newPane.setTop(vbox);
        newPane.setBottom(coverBox);
        newPane.setRight(column1Box);
        newPane.setLeft(column2Box);

        Scene scene = new Scene(newPane, 1200, 800);
        scene.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());
        return scene;

    }

    public Scene createLoginScene() {
        BorderPane p = new BorderPane();
        //p.setStyle("-fx-background-color: CCD4BC;");
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
       //vbox.setStyle("-fx-background-color: #CCD4BC;");



        HBox hbox = new HBox();
        hbox.setPadding(new Insets(15, 10, 15, 12));
        hbox.setSpacing(10);
        hbox.getChildren().addAll(l);
        hbox.setAlignment(Pos.CENTER);
        //hbox.setStyle("-fx-background-color: #a5ab98;");

        p.setCenter(vbox);
        p.setTop(hbox);

        Scene scene = new Scene(p, 1200, 800);
        scene.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());
        return scene;

    }
}