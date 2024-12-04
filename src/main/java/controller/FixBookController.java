package controller;

import com.mysql.cj.jdbc.ha.LoadBalanceExceptionChecker;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import library.Book;
import library.Library;

import java.net.URL;
import java.util.ResourceBundle;

public class FixBookController extends Controller implements Initializable {

    @FXML
    private TextField authorField;
    @FXML
    private TextField publisherField;
    @FXML
    private TextField genreField;
    @FXML
    private TextField pagesField;
    @FXML
    private TextField yearField;
    @FXML
    private TextField languageField;
    @FXML
    private TextField isbnField;
    @FXML
    private TextField copiesField;
    @FXML
    private TextField imageUrlField;
    @FXML
    private Button applyButton;
    @FXML
    private Label titleLabel;

    private Book book;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Initialize the controller
        applyButton.setOnAction(event -> applyChanges());
    }

    public void setBook(Book book) {
        this.book = book;
        authorField.setText(book.getAuthor());
        publisherField.setText(book.getPublisher());
        genreField.setText(book.getGenre());
        pagesField.setText(String.valueOf(book.getPages()));
        yearField.setText(String.valueOf(book.getPublicationYear()));
        languageField.setText(book.getLanguage());
        isbnField.setText(book.getIsbn());
        copiesField.setText(String.valueOf(book.getCopies()));
        imageUrlField.setText(book.getImageUrl());
        titleLabel.setText(book.getTitle());
    }

    @FXML
    private void applyChanges() {
        book.setAuthor(authorField.getText());
        book.setPublisher(publisherField.getText());
        book.setGenre(genreField.getText());
        book.setPages(Integer.parseInt(pagesField.getText()));
        book.setPublicationYear(Integer.parseInt(yearField.getText()));
        book.setLanguage(languageField.getText());
        book.setIsbn(isbnField.getText());
        book.setCopies(Integer.parseInt(copiesField.getText()));
        book.setImageUrl(imageUrlField.getText());

        //Save changes to the database or perform other necessary actions
        Library myLib = Library.getInstance();
        myLib.fixBook(book);

        // Close the window
        applyButton.getScene().getWindow().hide();
    }
}