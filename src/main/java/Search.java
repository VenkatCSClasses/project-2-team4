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
        Pane newPane = new Pane();
        newPane.setStyle("-fx-background-color: grey;");
        Label l = new Label("Enter a title, author, or genre into the search bar and press the corresponding button!");
        TextField searchTF = new TextField();
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
            stage.setScene(createResultsScene(results));

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


        bBack.setLayoutX(0);
        bBack.setLayoutY(0);


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

    public Scene createResultsScene(ArrayList<Book> results){
        Pane newPane = new Pane();
        newPane.setStyle("-fx-background-color: grey;");

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

        //layout for book labels
        bk1.setLayoutX(150);
        bk1.setLayoutY(0);
        
        bk2.setLayoutX(150);
        bk2.setLayoutY(50);

        bk3.setLayoutX(150);
        bk3.setLayoutY(100);

        bk4.setLayoutX(150);
        bk4.setLayoutY(150);

        bk5.setLayoutX(150);
        bk5.setLayoutY(200);
        
        //layout for book buttons
        bBook1.setLayoutX(450);
        bBook1.setLayoutY(0);

        bBook2.setLayoutX(450);
        bBook2.setLayoutY(50);

        bBook3.setLayoutX(450);
        bBook3.setLayoutY(100);

        bBook4.setLayoutX(450);
        bBook4.setLayoutY(150);

        bBook5.setLayoutX(450);
        bBook5.setLayoutY(200);

        //actions for book selection buttons
        bBook1.setOnAction(e -> {
            Stage stage = (Stage) bBack.getScene().getWindow();
            SelectedBook test = new SelectedBook(user, results.get(0), results);
            stage.setScene(test.CreateSelectScene());
        });

        bBook2.setOnAction(e -> {
            Stage stage = (Stage) bBack.getScene().getWindow();
            SelectedBook test = new SelectedBook(user, results.get(1), results);
            stage.setScene(test.CreateSelectScene());
        });

        bBook3.setOnAction(e -> {
            Stage stage = (Stage) bBack.getScene().getWindow();
            SelectedBook test = new SelectedBook(user, results.get(2), results);
            stage.setScene(test.CreateSelectScene());
        });

        bBook4.setOnAction(e -> {
            Stage stage = (Stage) bBack.getScene().getWindow();
            SelectedBook test = new SelectedBook(user, results.get(3), results);
            stage.setScene(test.CreateSelectScene());
        });

        bBook5.setOnAction(e -> {
            Stage stage = (Stage) bBack.getScene().getWindow();
            SelectedBook test = new SelectedBook(user, results.get(4), results);
            stage.setScene(test.CreateSelectScene());
        });

        //back button layout
        bBack.setLayoutX(0);
        bBack.setLayoutY(0);

        bBack.setOnAction(e -> {
            Stage stage = (Stage) bBack.getScene().getWindow();
            stage.setScene(createSearchScene());
        });

        newPane.getChildren().addAll(bk1, bk2, bk3, bk4, bk5, bBook1, bBook2, bBook3, bBook4, bBook5, bBack);
        Scene ResultsScene = new Scene(newPane, 600, 400);
        return ResultsScene;
    }
}
