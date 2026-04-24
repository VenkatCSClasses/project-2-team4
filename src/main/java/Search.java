import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.text.similarity.LevenshteinDistance;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

import database.UserBooksRepo;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.User;

public class Search {
    private final User user;

    public Search(User user) {
        this.user = user;
    }
    
    public Scene createSearchScene(){
        BorderPane newPane = new BorderPane();
        Label l = new Label("Enter a title, author, or genre into the search bar and press the corresponding button!");
        TextField searchTF = new TextField();
        searchTF.prefWidth(150);
        Button bSearchTitle = new Button("Search by Title");
        Button bSearchAuth = new Button("Search by Author");
        Button bSearchGenre = new Button("Browse by Genre");
        Button bBack = new Button("Back");
        bBack.setOnAction(e -> {
            Stage stage = (Stage) bSearchTitle.getScene().getWindow();
            MainApp test = new MainApp();
            test.setPrimaryStage(stage);
            stage.setScene(test.createMainScene(user));
        });

        bSearchTitle.setOnAction(e -> {
            ArrayList<Book> results = new ArrayList<>();
            Reader reader = null;
            try {
                reader = new BufferedReader(new FileReader("pg_catalog.csv"));
            } catch (FileNotFoundException e1) {
                e1.printStackTrace();
            }
            //gets all books from CSV and puts them in beans with getters for various fields
            CsvToBean<Book> csvReader = new CsvToBeanBuilder(reader).withType(Book.class).withSeparator(',').withIgnoreLeadingWhiteSpace(true).withIgnoreEmptyLine(true).build();
            List<Book> books = csvReader.parse();

            //parses csv list 1st for substrings
            for (Book book : books){
                String title = book.getTitle();
                if (title.toUpperCase().contains(searchTF.getText().toUpperCase())){
                    if (results.size() < 5){
                        results.add(book);
                    }
                }
            }
            //2nd parse is for similar strings
            if (results.size() < 5);
                for (Book book : books){
                    String title = book.getTitle();
                    LevenshteinDistance distance = LevenshteinDistance.getDefaultInstance();
                    int result = distance.apply(title,searchTF.getText());
                    if (result <= 1){
                        if (results.size() <= 5){
                            results.add(book);
                        }
                    }
                }

            Stage stage = (Stage) bSearchTitle.getScene().getWindow();
            stage.setScene(createResultsScene(results));

        });

        bSearchAuth.setOnAction(e -> {
            ArrayList<Book> results = new ArrayList<>();
            Reader reader = null;
            try {
                reader = new BufferedReader(new FileReader("pg_catalog.csv"));
            } catch (FileNotFoundException e1) {
                e1.printStackTrace();
            }
            //gets all books from CSV and puts them in beans with getters for various fields
            CsvToBean<Book> csvReader = new CsvToBeanBuilder(reader).withType(Book.class).withSeparator(',').withIgnoreLeadingWhiteSpace(true).withIgnoreEmptyLine(true).build();
            List<Book> books = csvReader.parse();

            //parses csv list 1st for substrings
            for (Book book : books){
                String authors = book.getAuthors();
                if (authors.toUpperCase().contains(searchTF.getText().toUpperCase())){
                    if (results.size() < 5){
                        results.add(book);
                    }
                }
            }
            //2nd parse is for similar strings
            if (results.size() < 5);
                for (Book book : books){
                    String authors = book.getAuthors();
                    LevenshteinDistance distance = LevenshteinDistance.getDefaultInstance();
                    int result = distance.apply(authors,searchTF.getText());
                    if (result <= 1){
                        if (results.size() <= 5){
                            results.add(book);
                        }
                    }
                }

            Stage stage = (Stage) bSearchTitle.getScene().getWindow();
            stage.setScene(createResultsScene(results));

        });

        bSearchGenre.setOnAction(e -> {
            ArrayList<Book> results = new ArrayList<>();
            Reader reader = null;
            try {
                reader = new BufferedReader(new FileReader("pg_catalog.csv"));
            } catch (FileNotFoundException e1) {
                e1.printStackTrace();
            }
            //gets all books from CSV and puts them in beans with getters for various fields
            CsvToBean<Book> csvReader = new CsvToBeanBuilder(reader).withType(Book.class).withSeparator(',').withIgnoreLeadingWhiteSpace(true).withIgnoreEmptyLine(true).build();
            List<Book> books = csvReader.parse();
            
            Stage stage = (Stage) bSearchTitle.getScene().getWindow();
            stage.setScene(createGenreScene(books));

        });


        HBox searchButtons = new HBox(bSearchTitle, bSearchAuth, bSearchGenre);
        searchButtons.setPadding(new Insets(15, 12, 15, 12));
        searchButtons.setSpacing(10);
        searchButtons.setAlignment(Pos.CENTER);

        VBox search = new VBox(searchTF, searchButtons, bBack);
        search.setPadding(new Insets(10));
        search.setSpacing(8);
        search.setAlignment(Pos.CENTER);

        VBox label = new VBox(l);
        label.setPadding(new Insets(10));
        label.setSpacing(8);
        label.setAlignment(Pos.CENTER);

        newPane.setCenter(search);
        newPane.setTop(label);
        Scene SearchScene = new Scene(newPane, 1200, 800);
        SearchScene.getStylesheets().add("styles.css");
        return SearchScene;
    }

    public Scene createResultsScene(ArrayList<Book> results){
        BorderPane newPane = new BorderPane();

        Label bk1 = new Label(results.get(0).getTitle() + " by " + results.get(0).getAuthors() + "\n");
        Label bk2 = new Label(results.get(1).getTitle() + " by " + results.get(1).getAuthors() + "\n");
        Label bk3 = new Label(results.get(2).getTitle() + " by " + results.get(2).getAuthors() + "\n");
        Label bk4 = new Label(results.get(3).getTitle() + " by " + results.get(3).getAuthors() + "\n");
        Label bk5 = new Label(results.get(4).getTitle() + " by " + results.get(4).getAuthors() + "\n");
        
        Button bBook1 = new Button("Select this book");
        Button bBook2 = new Button("Select this book");
        Button bBook3 = new Button("Select this book");
        Button bBook4 = new Button("Select this book");
        Button bBook5 = new Button("Select this book");

        Button bBack = new Button("Search Again");

        VBox buttons = new VBox(bBook1, bBook2, bBook3, bBook4, bBook5);
        buttons.setPadding(new Insets(10));
        buttons.setSpacing(8);
        buttons.setAlignment(Pos.CENTER);

        VBox booksBox = new VBox(bk1, bk2, bk3, bk4, bk5);
        booksBox.setPadding(new Insets(10));
        booksBox.setSpacing(8);
        booksBox.setSpacing(25);
        booksBox.setAlignment(Pos.CENTER);

        HBox holder = new HBox(booksBox, buttons);
        holder.setPadding(new Insets(10));
        holder.setAlignment(Pos.CENTER);

        VBox back = new VBox(bBack);
        back.setPadding(new Insets(10));
        back.setSpacing(8);
        back.setAlignment(Pos.CENTER);

        //actions for book selection buttons
        bBook1.setOnAction(e -> {
            Stage stage = (Stage) bBack.getScene().getWindow();
            SelectedBook test = new SelectedBook(user, results.get(0), results);
            stage.setScene(test.CreateSelectScene(false));
        });

        bBook2.setOnAction(e -> {
            Stage stage = (Stage) bBack.getScene().getWindow();
            SelectedBook test = new SelectedBook(user, results.get(1), results);
            stage.setScene(test.CreateSelectScene(false));
        });

        bBook3.setOnAction(e -> {
            Stage stage = (Stage) bBack.getScene().getWindow();
            SelectedBook test = new SelectedBook(user, results.get(2), results);
            stage.setScene(test.CreateSelectScene(false));
        });

        bBook4.setOnAction(e -> {
            Stage stage = (Stage) bBack.getScene().getWindow();
            SelectedBook test = new SelectedBook(user, results.get(3), results);
            stage.setScene(test.CreateSelectScene(false));
        });

        bBook5.setOnAction(e -> {
            Stage stage = (Stage) bBack.getScene().getWindow();
            SelectedBook test = new SelectedBook(user, results.get(4), results);
            stage.setScene(test.CreateSelectScene(false));
        });


        bBack.setOnAction(e -> {
            Stage stage = (Stage) bBack.getScene().getWindow();
            stage.setScene(createSearchScene());
        });
       
        newPane.setCenter(holder);
        newPane.setBottom(back);
        
        Scene ResultsScene = new Scene(newPane, 1200, 800);
        ResultsScene.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());
        return ResultsScene;
    }

    public Scene createGenreScene(List<Book> books){
        BorderPane newPane = new BorderPane();
        Label l = new Label("Select a Genre:");
        Button scifi = new Button("Science Fiction");
        Button adventure = new Button("Adventure");
        Button history = new Button("History");
        Button poetry = new Button("Poetry");
        Button mystery = new Button("Mystery");
        Button fantasy = new Button("Fantasy");

        scifi.setOnAction(e -> {
            ArrayList<Book> results = new ArrayList<>();
            for (Book book : books){
                String subjects = book.getSubjects();
                if (subjects.toUpperCase().contains("SCIENCE FICTION")){
                    if (results.size() < 15){
                        results.add(book);
                    }
                }
            }
            Stage stage = (Stage) scifi.getScene().getWindow();
            stage.setScene(createGenreResultsScene(results));
        
        });

        adventure.setOnAction(e -> {
            ArrayList<Book> results = new ArrayList<>();
            for (Book book : books){
                String subjects = book.getSubjects();
                if (subjects.toUpperCase().contains("ADVENTURE")){
                    if (results.size() < 15){
                        results.add(book);
                    }
                }
            }

            Stage stage = (Stage) scifi.getScene().getWindow();
            stage.setScene(createGenreResultsScene(results));
        });

        history.setOnAction(e -> {
            ArrayList<Book> results = new ArrayList<>();
            for (Book book : books){
                String subjects = book.getSubjects();
                if (subjects.toUpperCase().contains("HISTORY")){
                    if (results.size() < 15){
                        results.add(book);
                    }
                }
            }

            Stage stage = (Stage) scifi.getScene().getWindow();
            stage.setScene(createGenreResultsScene(results));
        });

        poetry.setOnAction(e -> {
            ArrayList<Book> results = new ArrayList<>();
            for (Book book : books){
                String subjects = book.getSubjects();
                if (subjects.toUpperCase().contains("POETRY")){
                    if (results.size() < 15){
                        results.add(book);
                    }
                }
            }

            Stage stage = (Stage) scifi.getScene().getWindow();
            stage.setScene(createGenreResultsScene(results));
        });

        mystery.setOnAction(e -> {
            ArrayList<Book> results = new ArrayList<>();
            for (Book book : books){
                String subjects = book.getSubjects();
                if (subjects.toUpperCase().contains("MYSTERY")){
                    if (results.size() < 15){
                        results.add(book);
                    }
                }
            }

            Stage stage = (Stage) scifi.getScene().getWindow();
            stage.setScene(createGenreResultsScene(results));
        });

        fantasy.setOnAction(e -> {
            ArrayList<Book> results = new ArrayList<>();
            for (Book book : books){
                String subjects = book.getSubjects();
                if (subjects.toUpperCase().contains("FANTASY")){
                    if (results.size() < 15){
                        results.add(book);
                    }
                }
            }

            Stage stage = (Stage) scifi.getScene().getWindow();
            stage.setScene(createGenreResultsScene(results));
        });

        Button bBack = new Button("Back");
        bBack.setOnAction(e -> {
            Stage stage = (Stage) bBack.getScene().getWindow();
            stage.setScene(createSearchScene());
        });

        VBox vbox = new VBox();
        vbox.setPadding(new Insets(10));
        vbox.setSpacing(8);
        vbox.getChildren().addAll(scifi, adventure, history, poetry, mystery, fantasy, bBack);
        vbox.setAlignment(Pos.CENTER);

        newPane.setCenter(vbox);
        newPane.setTop(l);

        Scene GenreScene = new Scene(newPane, 1200, 800);
        GenreScene.getStylesheets().add("styles.css");
        return GenreScene;
    }

    public Scene createGenreResultsScene(ArrayList<Book> results){
        BorderPane newPane = new BorderPane();
        VBox labels = new VBox();
        labels.setPadding(new Insets(10));
        labels.setSpacing(25);
        labels.setAlignment(Pos.CENTER);
        List<Label> lList = new ArrayList<>();
        for (Book book : results){
            lList.add(new Label(book.getTitle() + " by " + book.getAuthors() + "\n"));
        }
        labels.getChildren().addAll(lList);

        VBox buttons = new VBox();
        buttons.setPadding(new Insets(10));
        buttons.setSpacing(8);
        buttons.setAlignment(Pos.CENTER);
        List<Button> bList = new ArrayList<>();
        for (Book book : results){
            Button b1 = new Button("Select This Book");
            b1.setOnAction(e -> {
                Stage stage = (Stage) b1.getScene().getWindow();
                SelectedBook test = new SelectedBook(user, book, results);
                stage.setScene(test.CreateSelectScene(true));
            });
            bList.add(b1);
        }
        buttons.getChildren().addAll(bList);

        HBox holder = new HBox();
        holder.getChildren().addAll(labels,buttons);
        holder.setAlignment(Pos.CENTER);

        HBox back = new HBox();
        Button bBack = new Button("Back");
        bBack.setOnAction(e -> {
            Stage stage = (Stage) bBack.getScene().getWindow();
            stage.setScene(createSearchScene());
        });
        back.getChildren().addAll(bBack);
        back.setPadding(new Insets(10));
        back.setAlignment(Pos.CENTER);

        newPane.setCenter(holder);
        newPane.setBottom(back);

        Scene GenreScene = new Scene(newPane, 1200, 800);
        GenreScene.getStylesheets().add("styles.css");
        return GenreScene;
    }

    
}
