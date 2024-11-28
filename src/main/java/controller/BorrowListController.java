package controller;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import library.Borrowed;
import library.Library;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

import static controller.Controller.connection;

public class BorrowListController implements Initializable {

    @FXML
    private TableView<Borrowed> BorrowTable;

    @FXML
    private TableColumn<Borrowed, Integer> borrowIdColumn;

    @FXML
    private TableColumn<Borrowed, String> userFullnameColumn;

    @FXML
    private TableColumn<Borrowed, String> bookTitleColumn;

    @FXML
    private TableColumn<Borrowed, String> borrowDateColumn;

    @FXML
    private TableColumn<Borrowed, String> dueDateColumn;

    @FXML
    private TableColumn<Borrowed, String> statusColumn;

    @FXML
    private Label userIdLabel;

    @FXML
    private Label bookIdLabel;

    @FXML
    private Label borrowedCopiesLabel;

    @FXML
    private Label userPhoneLabel;

    @FXML
    private Label userEmailLabel;

    @FXML
    private Button returnBookButton;

    private ObservableList<Borrowed> borrowedData = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        borrowIdColumn.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getBorrowId()));
        userFullnameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getUserfullname()));
        bookTitleColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getBooktitle()));
        borrowDateColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getBorrowDate()));
        dueDateColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDueDate()));
        statusColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getStatus()));

        loadBorrowData();
        BorrowTable.setItems(borrowedData);
        BorrowTable.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> showBorrowDetails(newValue)
        );
    }

    private void loadBorrowData() {
        String query = "SELECT b.borrow_id, b.user_id, b.book_id, b.borrow_date, b.borrowed_copies, b.due_date, b.status, u.fullname AS user_fullname, u.phone AS phone, u.email AS email, bk.title AS book_title " +
                "FROM borrowed b " +
                "JOIN users u ON b.user_id = u.user_id " +
                "JOIN books bk ON b.book_id = bk.id " +
                "ORDER BY b.borrow_date DESC";

        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                int borrowId = resultSet.getInt("borrow_id");
                int userId = resultSet.getInt("user_id");
                int bookId = resultSet.getInt("book_id");
                String borrowDate = resultSet.getString("borrow_date");
                String dueDate = resultSet.getString("due_date");
                int borrowedCopies = resultSet.getInt("borrowed_copies");
                String userFullname = resultSet.getString("user_fullname");
                String bookTitle = resultSet.getString("book_title");
                String status = resultSet.getString("status");
                String userPhone = resultSet.getString("phone");
                String userEmail = resultSet.getString("email");

                Borrowed borrowed = new Borrowed(borrowId, userId, bookId, borrowedCopies, borrowDate, dueDate, status, userFullname, bookTitle, userPhone, userEmail);
                borrowedData.add(borrowed);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void showBorrowDetails(Borrowed borrowed) {
        if (borrowed != null) {
            userIdLabel.setText("Mã người mượn: " + String.valueOf(borrowed.getUserId()));
            bookIdLabel.setText("Mã sách mượn: " + String.valueOf(borrowed.getBookId()));
            borrowedCopiesLabel.setText("Số lượng: " + String.valueOf(borrowed.getBorrowedCopies()));
            userPhoneLabel.setText("Sđt người mượn: " + borrowed.getUserPhone());
            userEmailLabel.setText("Email người mượn: " + borrowed.getUserEmail());

            if (borrowed.getStatus().equals("borrowed")) {
                returnBookButton.setVisible(true);
            } else {
                returnBookButton.setVisible(false) ;
            }

            returnBookButton.setOnAction(event->handleReturnBook(borrowed));
        }
    }

    private void handleReturnBook(Borrowed borrowed) {
        if (borrowed != null) {
            Library myLib = Library.getInstance();
            myLib.ReturnBook(borrowed.getBorrowId());
        }

        borrowedData.clear();
        loadBorrowData();
        BorrowTable.setItems(borrowedData);
        BorrowTable.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> showBorrowDetails(newValue)
        );
    }
}