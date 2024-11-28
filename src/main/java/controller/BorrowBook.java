package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class BorrowBook{
    @FXML
    TextField userId;
    @FXML
    TextField copies;
    @FXML
    Button borrowButton;

    int bookId;

    public BorrowBook(int bookId){
        this.bookId = bookId;
    }

    @FXML
    public void initialize() {
        borrowButton.setOnAction(event -> borrowBook(this.bookId));
    }

    private void borrowBook(int bookId){
        String userIdText = userId.getText().trim();
        String copiesText = copies.getText().trim();
        System.out.println("Borrowing book with id: " + userIdText + " for user with id: " + copiesText + " and number of copies: " + copies);
    }
}
