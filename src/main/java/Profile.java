import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Profile {
    public Scene createProfileScene(){

        // TODO: pass MainApp or Stage reference to Profile constructor instead of using new MainApp()
        Pane newPane = new Pane();
        newPane.setStyle("-fx-background-color: grey;");
        Label username = new Label("Username:"); 
        TextField usernameTF = new TextField();
        // can't be implemented until currentUser is implemented and passed to this class constructor
        // usernameTF.setText(currentUser.getUsername());

        Label password = new Label("Password: ********"); // placeholder for password, will need to be updated to reflect logged in user
        // want to implement a way to click on the password and view it in plain text, but will need to figure out how to do that


        password.setOnMouseClicked(e -> {
            if (password.getText().contains("*")) {
                // will be implemented once currentUswer is implemented and passed to this class constructor
                //password.setText("Password: " + currentUser.getPassword()); // placeholder for password, will need to be updated to reflect logged in user
            }
            else {
                password.setText("Password: ********");
            }
        });

        // Create button for library and go to library scene when library button is clicked
        Button library = new Button("Library");
        library.setOnAction(e -> { 
            Stage stage = (Stage) library.getScene().getWindow();
           // Library library = new Library(); -- can be implemented once Library class is created
            //stage.setScene(mainApp.createLibraryScene());
        });

        // Create button for main and go to main scene when main button is clicked
        Button goBack = new Button("Back");
        goBack.setOnAction(e -> { // go back to main scene when button is clicked
            //Stage stage = (Stage) goBack.getScene().getWindow();
            MainApp mainApp = new MainApp();
            //((Stage) goBack.getScene().getWindow()).setScene(mainApp.createMainScene()); 
            /** eventually createMainScene needs to be changed to createMainScene(currentUser)
             *   and currentUser needs to be passed to Profile constructor
             */
        });

        // Top left, navigation. buttons
        goBack.setLayoutX(20);
        goBack.setLayoutY(20);
        library.setLayoutX(20);
        library.setLayoutY(50);

        // Top right, set positions for username labels
        username.setLayoutX(350);
        username.setLayoutY(50);
        usernameTF.setLayoutX(350);
        usernameTF.setLayoutY(70);

        // Below username in top right, set positions for password labels
        password.setLayoutX(350);
        password.setLayoutY(120);


        // Add all elements to the pane
        newPane.getChildren().add(username);
        newPane.getChildren().add(usernameTF);
        newPane.getChildren().add(password);
        newPane.getChildren().add(library);
        newPane.getChildren().add(goBack);

        // Create scene and return
        Scene profileScene = new Scene(newPane, 600, 400);
        return profileScene;
    }
}
