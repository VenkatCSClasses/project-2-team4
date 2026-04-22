import java.util.ArrayList;

import database.UserBooksRepo;
import javafx.animation.PauseTransition;
import javafx.concurrent.Worker;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.User;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

public class SelectedBook {
    private final User user;
    private final Book book;
    private final ArrayList<Book> results;
    private final UserBooksRepo userBooksRepo;
    private final PauseTransition timeout;


    public SelectedBook(User user, Book book, ArrayList<Book> results) {
        this.user = user;
        this.book = book;
        this.results = results;
        this.userBooksRepo = new UserBooksRepo();
        timeout = new PauseTransition(Duration.seconds(15));
    }
    
    public Scene CreateSelectScene(Boolean genreSearch){
        
        int id = Integer.parseInt(book.getId());
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
            Stage stage = (Stage) readButton.getScene().getWindow();
            stage.setScene(createReaderScene(stage, id, book.getTitle(), genreSearch));
        });

        addToLibButton.setOnAction(e -> {
            try {
                this.userBooksRepo.addBook(user.getId(), id, book.getTitle(), book.getAuthors());
            } catch (java.sql.SQLException ex) {
                ex.printStackTrace();
            }
        });

        returnButton.setOnAction(e -> {
            if (genreSearch == true){
                Stage stage = (Stage) returnButton.getScene().getWindow();
                Search test = new Search(user);
                stage.setScene(test.createGenreResultsScene(results));
            }else{
                Stage stage = (Stage) returnButton.getScene().getWindow();
                Search test = new Search(user);
                stage.setScene(test.createResultsScene(results));
            }
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
        
        Scene SelectScene = new Scene(newPane, 1200, 800);
        return SelectScene; 
    }

    private Scene createReaderScene(Stage stage, int gutenbergId, String bookTitle, Boolean genreSearch) {
        BorderPane pane = new BorderPane();

        javafx.scene.web.WebView webView = new javafx.scene.web.WebView();
        Worker<Void> worker = webView.getEngine().getLoadWorker();

        // Create a 10-second timeout timer
        PauseTransition timeout = new PauseTransition(Duration.seconds(10));
        timeout.setOnFinished(e -> {
            if (worker.isRunning()) {
                worker.cancel();
                Label errorLabel = new Label("Book could not be read. Please check your internet connection and try again.");
                Button bBack = new Button("Back to Search");
                bBack.setOnAction(f -> stage.setScene(CreateSelectScene(genreSearch)));
                
                HBox buttons = new HBox(bBack);
                buttons.setAlignment(Pos.CENTER);

                pane.setTop(errorLabel);
                pane.setCenter(buttons);
            }
        });
        webView.getEngine().load("https://www.gutenberg.org/cache/epub/" + gutenbergId + "/pg" + gutenbergId + "-images.html");
        webView.getEngine().getLoadWorker().stateProperty().addListener((obs, oldState, newState) -> {
            if (newState == Worker.State.RUNNING){
                timeout.playFromStart();
            }else if (newState == Worker.State.FAILED) {
                timeout.stop();
                Label errorLabel = new Label("Book could not be read. Please check your internet connection and try again.");
                Button bBack = new Button("Back to Search");
                bBack.setOnAction(f -> stage.setScene(CreateSelectScene(genreSearch)));
                
                HBox buttons = new HBox(bBack);
                buttons.setAlignment(Pos.CENTER);

                pane.setTop(errorLabel);
                pane.setCenter(buttons);
            }else if (newState == Worker.State.SUCCEEDED){
                timeout.stop();
                webView.setPrefSize(600, 360);
                webView.setLayoutX(0);
                webView.setLayoutY(40);

                Label titleLabel = new Label(bookTitle);
                Button bBack = new Button("Back to Search");
                HBox topHolder = new HBox(titleLabel, bBack);
                topHolder.setSpacing(10);
                topHolder.setAlignment(Pos.CENTER);

                bBack.setOnAction(e -> stage.setScene(CreateSelectScene(genreSearch)));

                pane.setCenter(webView);
                pane.setTop(topHolder);
            }
        });
        Scene readerScene = new Scene(pane, 1200, 800);
        readerScene.getStylesheets().add("styles.css");
        return readerScene;
    }



}