import com.opencsv.bean.CsvBindByName;

public class Book {
    @CsvBindByName(column = "Title")
    private String title;
    
    @CsvBindByName(column = "Authors")
    private String authors;

    @CsvBindByName(column = "Subjects")
    private String subjects;

    @CsvBindByName(column = "Language")
    private String language;

    @CsvBindByName(column = "Text#")
    private String id;

    public String getAuthors() {
        return authors;
    }

    public String getId() {
        return id;
    }

    public String getLanguage() {
        return language;
    }

    public String getSubjects() {
        return subjects;
    }

    public String getTitle() {
        return title;
    }

}
