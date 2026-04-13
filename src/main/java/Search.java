import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Search {
    public static Scene createSearchScene(){
        Pane newPane = new Pane();
        newPane.setStyle("-fx-background-color: grey;");
        Label l = new Label("Enter a title, author, or genre into the search bar and press the corresponding button!");
        TextField searchTF = new TextField();
        Button bSearchTitle = new Button("Search by Title");
        Button bSearchAuth = new Button("Search by Author");
        Button bSearchGenre = new Button("Search by Genre");
        Button bBack = new Button("Back");
        bBack.setOnAction(e -> {
            //((Stage) bBack.getScene().getWindow()).setScene(MainApp.createMainScene());
            //must make static in main
        });


        l.setLayoutX(200);
        l.setLayoutY(0);


        bSearchTitle.setLayoutX(100);
        bSearchTitle.setLayoutY(200);
        bSearchTitle.setMinWidth(100);

        bSearchAuth.setLayoutX(200);
        bSearchAuth.setLayoutY(200);
        bSearchAuth.setMinWidth(100);


        bSearchGenre.setLayoutX(300);
        bSearchGenre.setLayoutY(200);
        bSearchGenre.setMinWidth(100);


        bBack.setLayoutX(350);
        bBack.setLayoutY(550);


        searchTF.setLayoutX(200);
        searchTF.setLayoutY(175);

        newPane.getChildren().add(bSearchTitle);
        newPane.getChildren().add(bSearchAuth);
        newPane.getChildren().add(bSearchGenre);
        newPane.getChildren().add(l);
        newPane.getChildren().add(searchTF);
        newPane.getChildren().add(bBack);
        Scene SearchScene = new Scene(newPane, 600, 400);
        return SearchScene;
    }
}
