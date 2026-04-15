import java.util.ArrayList;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.User;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

public class SelectedBook {
    private final User user;
    private final Book book;
    private final ArrayList<Book> results;

    public SelectedBook(User user, Book book, ArrayList<Book> results) {
        this.user = user;
        this.book = book;
        this.results = results;
    }
    
    public Scene CreateSelectScene(){
        BorderPane newPane = new BorderPane();
        newPane.setStyle("-fx-background-color: grey;");
        Label titleLabel = new Label(book.getTitle());
        Label authLabel = new Label(book.getAuthors());
        Label subLabel = new Label(book.getSubjects());
        Label idLabel = new Label(book.getId());
        Label langLabel = new Label(book.getLanguage());
        Button readButton = new Button("Read This Book");
        Button addToLibButton = new Button("Add This Book to Your Library");
        Button returnButton = new Button("Return to Search");

        readButton.setOnAction(e -> {
            //open webview page
        });

        addToLibButton.setOnAction(e -> {
            //add to personal library
        });

        returnButton.setOnAction(e -> {
            //return to original search
        });

        //new layout style, havent tested yet
        HBox hbox = new HBox();
        hbox.setPadding(new Insets(15, 12, 15, 12));
        hbox.setSpacing(10);
        hbox.getChildren().addAll(readButton, addToLibButton, returnButton);

        VBox vbox = new VBox();
        vbox.setPadding(new Insets(10));
        vbox.setSpacing(8);
        vbox.getChildren().addAll(titleLabel,authLabel,subLabel,idLabel,langLabel);

        newPane.setBottom(hbox);
        newPane.setCenter(vbox);
        
        Scene SelectScene = new Scene(newPane, 600, 400);
        return SelectScene; 
    }
}
