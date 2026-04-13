import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;
import javafx.scene.control.TextField;
import model.User;
import service.Authentication;
import database.UserRepo;
import database.DatabaseManager;



public class MainApp extends Application{
    
    private Authentication authService;
    @Override
    public void start(Stage stage) {
        
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



    public Scene createMainScene(){
        Pane newPane = new Pane();
        newPane.setStyle("-fx-background-color: grey;");

        Label l = new Label("Login Successful!");
        
        TextField searchTF = new TextField();
        Button bSearch = new Button("Search");
        Button bLogout = new Button("Logout");
        bLogout.setOnAction(e -> {
            ((Stage) bLogout.getScene().getWindow()).setScene(createLoginScene());
        });


        l.setLayoutX(200);
        l.setLayoutY(0);
        bSearch.setLayoutX(200);
        bSearch.setLayoutY(200);
        bLogout.setLayoutX(250);
        bLogout.setLayoutY(200);
        searchTF.setLayoutX(200);
        searchTF.setLayoutY(175);

        newPane.getChildren().add(bSearch);
        newPane.getChildren().add(l);
        newPane.getChildren().add(searchTF);
        newPane.getChildren().add(bLogout);
        Scene MenuScene = new Scene(newPane, 600, 400);
        return MenuScene;
    }

    public Scene createLoginScene(){
        Pane p = new Pane(); 
        p.setStyle("-fx-background-color: grey;");
        Label l = new Label("Welcome to the Reader! Please enter username and password. \nPress 'Register' if the account does not yet exist");
        TextField usernameTF = new TextField();
        PasswordField passwordTF = new PasswordField();
        Button bLogin = new Button("Login");
        Button bRegister = new Button("Register");
        bLogin.setOnAction(e -> {
            ((Stage) bLogin.getScene().getWindow()).setScene(createMainScene());
            //do login check in the event below; if successful, change scenes with code given
            try {
                
                User user = authService.login(usernameTF.getText(), passwordTF.getText());
                ((Stage) bLogin.getScene().getWindow()).setScene(createMainScene(user));
            } catch (Exception ex) {
                l.setText("Login failed: " + ex.getMessage());
            }
        });
        
        bRegister.setOnAction(e -> {
            //account registration code goes here
            try {
                
                authService.register(usernameTF.getText(), passwordTF.getText());
                l.setText("Account Registered! Please enter your account information and press login!");
            } catch (Exception ex) {
                l.setText("Registration failed: " + ex.getMessage());
            }
            

        });

    
        l.setLayoutX(200);
        l.setLayoutY(0);
        bLogin.setLayoutX(200);
        bLogin.setLayoutY(200);
        bRegister.setLayoutX(250);
        bRegister.setLayoutY(200);
        usernameTF.setLayoutX(200);
        usernameTF.setLayoutY(150);
        passwordTF.setLayoutX(200);
        passwordTF.setLayoutY(175);

        p.getChildren().add(bLogin);
        p.getChildren().add(l);
        p.getChildren().add(usernameTF);
        p.getChildren().add(passwordTF);
        p.getChildren().add(bRegister);
        Scene LoginScene = new Scene(p, 600, 400);
        return LoginScene;
    }
}
