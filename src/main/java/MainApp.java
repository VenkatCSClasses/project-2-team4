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

public class MainApp extends Application{
    
    @Override
    public void start(Stage stage) {
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


        l.setLayoutX(200);
        l.setLayoutY(0);
        bSearch.setLayoutX(200);
        bSearch.setLayoutY(200);
        searchTF.setLayoutX(200);
        searchTF.setLayoutY(175);

        newPane.getChildren().add(bSearch);
        newPane.getChildren().add(l);
        newPane.getChildren().add(searchTF);
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

        });
        bRegister.setOnAction(e -> {
            //account registration code goes here
            l.setText("Account Registered! Please enter your account information and press login!");
        });

    
        l.setLayoutX(200);
        l.setLayoutY(0);
        bLogin.setLayoutX(200);
        bLogin.setLayoutY(200);
        usernameTF.setLayoutX(200);
        usernameTF.setLayoutY(150);
        passwordTF.setLayoutX(200);
        passwordTF.setLayoutY(175);

        p.getChildren().add(bLogin);
        p.getChildren().add(l);
        p.getChildren().add(usernameTF);
        p.getChildren().add(passwordTF);
        Scene LoginScene = new Scene(p, 600, 400);
        return LoginScene;
    }
}
