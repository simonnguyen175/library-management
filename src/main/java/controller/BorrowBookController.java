package controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import library.Book;
import library.Library;
import javafx.scene.input.MouseEvent;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BorrowBookController {
    @FXML
    private Button borrowButton;

    @FXML
    private Label titleLabel;

    @FXML
    private Label remainLabel;

    @FXML
    private TextField userIdField;

    @FXML
    private TextField amountField;

    @FXML
    private DatePicker dueDatePicker;

    public void setBook(Book book) {
        titleLabel.setText(book.getTitle());
        new Thread(()->{
            remainLabel.setText("Available: " + getAvailable(book.getBookId(), book.getCopies()));
        }).start();
        borrowButton.setOnAction(event -> handleBorrowButtonAction(book));
        dueDatePicker.setOnAction(event -> {
            System.out.println(dueDatePicker.getValue().toString());
        });
    }

    private int getAvailable(int bookId, int copies) {
        String sql = "SELECT SUM(borrowed_copies) FROM Borrowed WHERE book_id = ? AND status = 'borrowed'";
        try (PreparedStatement preparedStatement = Controller.connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, bookId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return copies - resultSet.getInt(1);
            }
        } catch (SQLException e) {
            System.err.println("Error while fetching borrowed copies: " + e.getMessage());
        }
        return 0;
    }

    private void handleBorrowButtonAction(Book book) {
        if (userIdField.getText().isEmpty() || amountField.getText().isEmpty() || dueDatePicker.getValue() == null) {
            // Show alert for missing information
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Missing information");
            alert.showAndWait();
            return;
        }

        int amount = Integer.parseInt(amountField.getText());
        int available = getAvailable(book.getBookId(), book.getCopies());

        if (amount > available) {
            // Show alert for amount greater than available
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Amount exceeds available copies");
            alert.showAndWait();
            return;
        }

        Library myLib = Library.getInstance();
        new Thread(() -> {
            myLib.BorrowBook(Integer.parseInt(userIdField.getText()),
                    book.getBookId(), Integer.parseInt(amountField.getText()),
                    dueDatePicker.getValue().toString());
        }).start();

        Stage stage = (Stage) borrowButton.getScene().getWindow();
        stage.close();
    }
}