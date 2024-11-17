package library;

public class Book {
    private String title;
    private String author;
    private String isbn;
    private String description;
    private String imageUrl;
    private String genre;
    private String language;
    private int pages ;
    private String publisher;
    private String book_id;

    public Book(String title) {
        this.title = title;
    }

    public Book() {

    }


    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author.replaceAll("^\"|\"$", "");
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description.replaceAll("^\"|\"$", "");
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn.replaceAll("^\"|\"$", "");
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title.replaceAll("^\"|\"$", "");
    }

    public String toString() {
        return title;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl.replaceAll("^\"|\"$", "");
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre.replaceAll("^\"|\"$", "");
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language.replaceAll("^\"|\"$", "");
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        if (publisher == null){
            publisher = "No publisher";
        }
        this.publisher = publisher.replaceAll("^\"|\"$", "");

    }

    public String getBook_id() {
        return book_id;
    }

    public void setBook_id(String book_id) {
        this.book_id = book_id;
    }
}