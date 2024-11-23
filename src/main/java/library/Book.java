package library;

import java.util.List;

public class Book {
    private String title;
    private String author;
    private String genre;
    private String publisher;
    private int publicationYear;
    private String isbn;
    private int pages;
    private String language;
    private int copies;
    private String imageUrl;

    public Book(){

    }

    // Constructor
    public Book(String title, int copies) {
        this.title = title;
        this.copies = copies;
    }

    public Book(String title, String author, String genre, String publisher, int publicationYear, String isbn, int pages, String language, int copies) {
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.publisher = publisher;
        this.publicationYear = publicationYear;
        this.isbn = isbn;
        this.pages = pages;
        this.language = language;
        this.copies = copies;
    }

    public Book(String title, int copies, String imageUrl) {
        this.title = title;
        this.copies = copies;
        this.imageUrl = imageUrl;
    }

    // Getters and Setters
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public int getPublicationYear() {
        return publicationYear;
    }

    public void setPublicationYear(int publicationYear) {
        this.publicationYear = publicationYear;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public int getCopies() {
        return copies;
    }

    public void setCopies(int copies) {
        this.copies = copies;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setYearPublished(int year) {
        this.publicationYear = year;
    }
}

