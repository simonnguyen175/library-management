package controller;

import javafx.fxml.FXML;
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
        int year = Integer.parseInt(yearTextField.getText().trim());
        String isbn = isbnTextField.getText().trim();
        int pages = Integer.parseInt(pagesTextField.getText().trim());
        String language = languageTextField.getText().trim();
        int copies = Integer.parseInt(copiesTextField.getText().trim());
        String imageUrl = imageUrlTextField.getText().trim();

        // Close the stage
        Stage stage = (Stage) addButton.getScene().getWindow();
        stage.close();
    }
}