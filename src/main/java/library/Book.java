package library;

public class Book {
    private String title;
    private int copies;

    public Book(String title) {
        this.title = title;
    }

    public Book(String title, int copies) {
        this.title = title;
        this.copies = copies;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getCopies() {
        return copies;
    }
}