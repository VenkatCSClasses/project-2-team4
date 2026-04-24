import javafx.animation.PauseTransition;
import javafx.concurrent.Worker;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.User;
import database.UserBooksRepo;
import java.util.*;
import javafx.scene.layout.Priority;

public class SavedBooks {

    private final User user;
    private final UserBooksRepo userBooksRepo;

    public SavedBooks(User user) {
        this.user = user;
        this.userBooksRepo = new UserBooksRepo();
    }

    public Scene createSavedBooksScene() {
        BorderPane newPane = new BorderPane();

        Label title = new Label("My Saved Books — " + user.getUsername());
        Label statusLabel = new Label();
        Button bBack = new Button("Back");

        HBox holder = new HBox(title,bBack);
        holder.setPadding(new Insets(10));
        holder.setSpacing(10);
        holder.setAlignment(Pos.CENTER);

        bBack.setOnAction(e -> {
            Stage stage = (Stage) bBack.getScene().getWindow();
            MainApp app = new MainApp();
            app.setPrimaryStage(stage);
            stage.setScene(app.createMainScene(user));
        });

        newPane.setCenter(loadBooks(newPane, statusLabel));
        newPane.setTop(holder);

        Scene savedBooksScene = new Scene(newPane, 1200, 800);
        savedBooksScene.getStylesheets().add("styles.css");
        return savedBooksScene;
    }

    private VBox loadBooks(Pane pane, Label statusLabel) {
        VBox mainBox = new VBox();
        mainBox.setPadding(new Insets(20));
        mainBox.setSpacing(12);
        mainBox.setAlignment(Pos.CENTER);
        //List<Label> lList = new ArrayList<>();
        

        try {
            List<Map<String, String>> books = 
                userBooksRepo.getBooksForUser(user.getId());

            if (books.isEmpty()) {
                Label empty = new Label("No saved books yet — search for books to add!");
                mainBox.getChildren().add(empty);
                return mainBox;
            }

            //for (int i = 0; i < books.size(); i++) {
            for (Map<String, String> book : books) {
                // Create the Book Label with title, author, and status
                Label bookLabel = new Label(
                    book.get("title") + " — " + book.get("author") +
                    "  [" + book.get("status") + "]"
                );
                // Allow the label to expand horizontally and push buttons to the right
                bookLabel.setMaxWidth(Double.MAX_VALUE);
                HBox.setHgrow(bookLabel, Priority.ALWAYS);

                Button bRead   = new Button("Read");
                Button bStatus = new Button("Update Status");
                Button bRemove = new Button("Remove");

                // bookLabel.setLayoutX(10);  bookLabel.setLayoutY(yPos);
                // bRead.setLayoutX(500);     bRead.setLayoutY(yPos);
                // bStatus.setLayoutX(570);   bStatus.setLayoutY(yPos);
                // bRemove.setLayoutX(690);   bRemove.setLayoutY(yPos);

                // HBox to hold the label and buttons on the same row
                HBox row = new HBox(15, bookLabel, bRead, bStatus, bRemove);
                row.setAlignment(Pos.CENTER_LEFT);
                row.setPadding(new Insets(8, 12, 8, 12));

                int gutId = Integer.parseInt(book.get("gutenberg_id"));
                String bookTitle = book.get("title");

                bRead.setOnAction(e -> {
                    try {
                        String current = book.get("status");
                        if (current.equals("NOT_STARTED")) {
                            userBooksRepo.updateStatus(user.getId(), gutId, "IN_PROGRESS");
                        }
                        Stage stage = (Stage) bRead.getScene().getWindow();
                        stage.setScene(createReaderScene(stage, gutId, bookTitle));
                    } catch (Exception ex) {
                        statusLabel.setText("Error: " + ex.getMessage());
                    }
                });

                bRemove.setOnAction(e -> {
                    try {
                        userBooksRepo.removeBook(user.getId(), gutId);
                        Stage stage = (Stage) bRemove.getScene().getWindow();
                        stage.setScene(new SavedBooks(user).createSavedBooksScene());
                    } catch (Exception ex) {
                        statusLabel.setText("Error: " + ex.getMessage());
                    }
                });

                bStatus.setOnAction(e -> {
                    try {
                        String current = book.get("status");
                        String next = switch (current) {
                            case "NOT_STARTED" -> "IN_PROGRESS";
                            case "IN_PROGRESS" -> "FINISHED";
                            default            -> "NOT_STARTED";
                        };
                        userBooksRepo.updateStatus(user.getId(), gutId, next);
                        Stage stage = (Stage) bStatus.getScene().getWindow();
                        stage.setScene(new SavedBooks(user).createSavedBooksScene());
                    } catch (Exception ex) {
                        statusLabel.setText("Error: " + ex.getMessage());
                    }
                });

                //pane.getChildren().addAll(bookLabel, bRead, bStatus, bRemove);
                mainBox.getChildren().add(row);
            }
            return mainBox;

        } catch (Exception e) {
            statusLabel.setText("Failed to load books: " + e.getMessage());
            System.out.println(e.getMessage());
            return mainBox;
        }
    }

    private Scene createReaderScene(Stage stage, int gutenbergId, String bookTitle) {
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
                Button retryRead = new Button("Try Again");
                
                HBox buttons = new HBox(bBack,retryRead);
                buttons.setAlignment(Pos.CENTER);

                pane.setTop(errorLabel);
                pane.setCenter(buttons);
            }
        });
        //webView.getEngine().load("https://www.gutenberg.org/cache/epub/" + gutenbergId + "/pg" + gutenbergId + "-images.html");
        webView.getEngine().getLoadWorker().stateProperty().addListener((obs, oldState, newState) -> {
            System.out.println("State: " + newState);
            if (newState == Worker.State.RUNNING){
                timeout.playFromStart();
            }else if (newState == Worker.State.FAILED) {
                System.out.println("FAILED");
                timeout.stop();
                Label errorLabel = new Label("Book could not be read. Please check your internet connection and try again.");
                Button bBack = new Button("Back to Library");
                bBack.setOnAction(e -> stage.setScene(
                            new SavedBooks(user).createSavedBooksScene()));
                
                HBox buttons = new HBox(bBack);
                buttons.setAlignment(Pos.CENTER);

                pane.setTop(errorLabel);
                pane.setCenter(buttons);
            }else if (newState == Worker.State.SUCCEEDED){
                System.out.println("SUCCEEDED");
                timeout.stop();
                // webView.setPrefSize(600, 360);
                // webView.setLayoutX(0);
                // webView.setLayoutY(40);

                Label titleLabel = new Label(bookTitle);
                Button bBack = new Button("Back to Library");
                HBox topHolder = new HBox(titleLabel, bBack);
                topHolder.setSpacing(10);
                topHolder.setAlignment(Pos.CENTER);

                bBack.setOnAction(e -> stage.setScene(
                            new SavedBooks(user).createSavedBooksScene()));

                pane.setCenter(webView);
                pane.setTop(topHolder);
            }
        });
        Scene readerScene = new Scene(pane, 1200, 800);
        readerScene.getStylesheets().add("styles.css");
        webView.getEngine().load("https://www.gutenberg.org/cache/epub/" + gutenbergId + "/pg" + gutenbergId + "-images.html");
        return readerScene;
    }
}
