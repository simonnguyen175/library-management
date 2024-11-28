package controller;

import library.Library;
import services.APIController;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import library.Book;

public class AddBookController {

    @FXML
    private TextField titleTextField, authorTextField, genreTextField, publisherTextField, yearTextField, isbnTextField, pagesTextField, languageTextField, copiesTextField, imageUrlTextField;
    @FXML
    private Button addButton, autofillButton;

    private APIController apiController = APIController.getInstance();
    private boolean addSuccess = false; // Check if the book is added successfully

    @FXML
    public void initialize() {
        autofillButton.setOnAction(event -> autofillBookDetails());
        addButton.setOnAction(event -> addBookToDatabase());
    }

    private void autofillBookDetails() {
        String isbn = isbnTextField.getText().trim();
        if (!isbn.isEmpty()) {
            Book book = apiController.getBookInfoFromAPI(isbn);
            if (book != null) {
                titleTextField.setText(book.getTitle());
                authorTextField.setText(book.getAuthor());
                genreTextField.setText(book.getGenre());
                publisherTextField.setText(book.getPublisher());
                yearTextField.setText(String.valueOf(book.getPublicationYear()));
                pagesTextField.setText(String.valueOf(book.getPages()));
                languageTextField.setText(book.getLanguage());
                imageUrlTextField.setText(book.getImageUrl());
            }
        }
    }

    private void addBookToDatabase() {
        String title = titleTextField.getText().trim();
        String author = authorTextField.getText().trim();
        String genre = genreTextField.getText().trim();
        String publisher = publisherTextField.getText().trim();
        int year = yearTextField.getText().trim().isEmpty() ? 0 : Integer.parseInt(yearTextField.getText().trim());
        String isbn = isbnTextField.getText().trim();
        int pages = pagesTextField.getText().trim().isEmpty() ? 0 : Integer.parseInt(pagesTextField.getText().trim());
        String language = languageTextField.getText().trim();
        int copies = copiesTextField.getText().trim().isEmpty() ? 0 : Integer.parseInt(copiesTextField.getText().trim());
        String imageUrl = imageUrlTextField.getText().trim();

        if (title.isEmpty() || author.isEmpty() || genre.isEmpty() || publisher.isEmpty() || year == 0 || isbn.isEmpty() || pages == 0 || language.isEmpty() || copies == 0 || imageUrl.isEmpty()) {
            // Show alert for missing information
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Missing information");
            alert.showAndWait();
            return;
        }
        else{
            // Add book to database
            addSuccess = true;
            Library myLib = Library.getInstance();
            myLib.addBook(new Book(title, author, genre, publisher, year, isbn, pages, language, copies, imageUrl));
        }

        // Close the stage
        Stage stage = (Stage) addButton.getScene().getWindow();
        stage.close();
    }

    public boolean isAddSuccess() {
        return addSuccess;
    }
}