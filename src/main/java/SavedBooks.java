import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import model.User;
import database.UserBooksRepo;
import java.util.*;

public class SavedBooks {

    private final User user;
    private final UserBooksRepo userBooksRepo;

    public SavedBooks(User user) {
        this.user = user;
        this.userBooksRepo = new UserBooksRepo();
    }

    public Scene createSavedBooksScene() {
        Pane newPane = new Pane();
        newPane.setStyle("-fx-background-color: grey;");

        Label title = new Label("My Saved Books — " + user.getUsername());
        Label statusLabel = new Label();
        Button bBack = new Button("Back");

        title.setLayoutX(200);      title.setLayoutY(10);
        bBack.setLayoutX(0);        bBack.setLayoutY(0);
        statusLabel.setLayoutX(50); statusLabel.setLayoutY(370);

        bBack.setOnAction(e -> {
            Stage stage = (Stage) bBack.getScene().getWindow();
            MainApp app = new MainApp();
            app.setPrimaryStage(stage);
            stage.setScene(app.createMainScene(user));
        });

        loadBooks(newPane, statusLabel);

        newPane.getChildren().addAll(title, bBack, statusLabel);
        return new Scene(newPane, 600, 400);
    }

    private void loadBooks(Pane pane, Label statusLabel) {
        try {
            List<Map<String, String>> books = 
                userBooksRepo.getBooksForUser(user.getId());

            if (books.isEmpty()) {
                Label empty = new Label("No saved books yet — search for books to add!");
                empty.setLayoutX(150);
                empty.setLayoutY(150);
                pane.getChildren().add(empty);
                return;
            }

            for (int i = 0; i < books.size(); i++) {
                Map<String, String> book = books.get(i);
                int yPos = 40 + (i * 40);

                Label bookLabel = new Label(
                    book.get("title") + " — " + book.get("author") +
                    "  [" + book.get("status") + "]"
                );
                Button bRead   = new Button("Read");
                Button bStatus = new Button("Update Status");
                Button bRemove = new Button("Remove");

                bookLabel.setLayoutX(10);  bookLabel.setLayoutY(yPos);
                bRead.setLayoutX(350);     bRead.setLayoutY(yPos);
                bStatus.setLayoutX(400);   bStatus.setLayoutY(yPos);
                bRemove.setLayoutX(490);   bRemove.setLayoutY(yPos);

                int gutId = Integer.parseInt(book.get("gutenberg_id"));
                String bookTitle = book.get("title");

                bRead.setOnAction(e -> {
                    Stage stage = (Stage) bRead.getScene().getWindow();
                    stage.setScene(createReaderScene(stage, gutId, bookTitle));
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

                pane.getChildren().addAll(bookLabel, bRead, bStatus, bRemove);
            }

        } catch (Exception e) {
            statusLabel.setText("Failed to load books: " + e.getMessage());
            System.out.println(e.getMessage());
        }
    }

    private Scene createReaderScene(Stage stage, int gutenbergId, String bookTitle) {
        Pane pane = new Pane();

        javafx.scene.web.WebView webView = new javafx.scene.web.WebView();
        webView.getEngine().load("https://www.gutenberg.org/cache/epub/" + gutenbergId + "/pg" + gutenbergId + "-images.html");
        webView.setPrefSize(600, 360);
        webView.setLayoutX(0);
        webView.setLayoutY(40);

        Label titleLabel = new Label(bookTitle);
        Button bBack = new Button("Back to Library");

        titleLabel.setLayoutX(10); titleLabel.setLayoutY(10);
        bBack.setLayoutX(480);     bBack.setLayoutY(10);

        bBack.setOnAction(e -> stage.setScene(
            new SavedBooks(user).createSavedBooksScene()));

        pane.getChildren().addAll(titleLabel, bBack, webView);
        return new Scene(pane, 600, 400);
    }
}