package library;

import java.util.List;

public class Book {
    private String title;
    private String author;
    private String publisher;
    private String isbn;
    private int pages;
    private String genre;
    private int year_published;
    private String image_url;
    private int copies;

    public Book(String title) {
        this.title = title;
    }

    public Book(String title, int copies) {
        this.title = title;
        this.copies = copies;
    }

    public Book(String title, String author, String publisher, String isbn, int pages, String genre, int year_published, int copies) {
        this.title = title;
        this.author = author;
        this.publisher = publisher;
        this.isbn = isbn;
        this.pages = pages;
        this.genre = genre;
        this.year_published = year_published;
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