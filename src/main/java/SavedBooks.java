import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.geometry.Insets;
import javafx.stage.Stage;
import model.User;
import database.UserBooksRepo;
import java.util.*;


public class SavedBooks {

    private final User user;
    private final UserBooksRepo userBookRepo;

    public SavedBooks(User user, UserBooksRepo userBookRepo) {
        this.user = user;
        this.userBookRepo = userBookRepo;
    }

    public Scene createScene() {
        // header
        Label title = new Label("My Library — " + user.getUsername());
        Button bBack = new Button("Back");

        HBox header = new HBox(10, title, bBack);
        header.setPadding(new Insets(10));

        // book list
        VBox booksBox = new VBox(8);
        booksBox.setPadding(new Insets(10));

        Label statusLabel = new Label();

        loadBooks(booksBox, statusLabel);

        ScrollPane scrollPane = new ScrollPane(booksBox);
        scrollPane.setFitToWidth(true);

        // back button — swap createLoginScene() for whatever 
        // the previous page ends up being
        bBack.setOnAction(e -> {
            // ((Stage) bBack.getScene().getWindow())
            //     .setScene(previousScene);
            System.out.println("back button — link this later");
        });

        BorderPane layout = new BorderPane();
        layout.setTop(header);
        layout.setCenter(scrollPane);
        layout.setBottom(statusLabel);

        return new Scene(layout, 600, 400);
    }

    private void loadBooks(VBox booksBox, Label statusLabel) {
        booksBox.getChildren().clear();

        try {
            List<Map<String, String>> books = 
                userBookRepo.getBooksForUser(user.getId());

            if (books.isEmpty()) {
                booksBox.getChildren().add(
                    new Label("Your library is empty — search for books to add!")
                );
                return;
            }

            for (Map<String, String> book : books) {
                HBox row = new HBox(10);
                row.setPadding(new Insets(5));

                Label bookLabel = new Label(
                    book.get("title") + " — " + book.get("author") +
                    "  [" + book.get("status") + "]"
                );

                Button bRemove = new Button("Remove");
                Button bStatus = new Button("Update Status");

                // remove from database
                bRemove.setOnAction(e -> {
                    try {
                        userBookRepo.removeBook(
                            user.getId(),
                            Integer.parseInt(book.get("gutenberg_id"))
                        );
                        loadBooks(booksBox, statusLabel); // refresh list
                    } catch (Exception ex) {
                        statusLabel.setText("Error: " + ex.getMessage());
                    }
                });

                // cycle through statuses
                bStatus.setOnAction(e -> {
                    try {
                        String current = book.get("status");
                        String next = switch (current) {
                            case "NOT_STARTED" -> "IN_PROGRESS";
                            case "IN_PROGRESS" -> "FINISHED";
                            default -> "NOT_STARTED";
                        };
                        userBookRepo.updateStatus(
                            user.getId(),
                            Integer.parseInt(book.get("gutenberg_id")),
                            next
                        );
                        loadBooks(booksBox, statusLabel); // refresh
                    } catch (Exception ex) {
                        statusLabel.setText("Error: " + ex.getMessage());
                    }
                });

                row.getChildren().addAll(bookLabel, bStatus, bRemove);
                booksBox.getChildren().add(row);
            }

        } catch (Exception e) {
            booksBox.getChildren().add(new Label("Failed to load library."));
            System.out.println(e.getMessage());
        }
    }
}