package controller;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import library.Borrow;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

import static controller.Controller.connection;

public class BorrowListController implements Initializable {

    @FXML
    private TableView<Borrow> BorrowTable;

    @FXML
    private TableColumn<Borrow, Integer> borrowIdColumn;

    @FXML
    private TableColumn<Borrow, Integer> borrowerIdColumn;

    @FXML
    private TableColumn<Borrow, Integer> bookIdColumn;

    @FXML
    private TableColumn<Borrow, String> borrowDateColumn;

    @FXML
    private TableColumn<Borrow, String> dueDateColumn;

    private ObservableList<Borrow> BorrowData = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        borrowIdColumn.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getBorrowId()));
        borrowerIdColumn.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getBorrowerId()));
        bookIdColumn.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getBookId()));
        borrowDateColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getBorrowDate()));
        dueDateColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDueDate()));

        loadBorrowData();
        BorrowTable.setItems(BorrowData);
    }

    private void loadBorrowData() {
        String query = "SELECT borrow_id, user_id, book_id, borrow_date, due_date FROM borrowlist";

        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                int borrowId = resultSet.getInt("borrow_id");
                int borrowerId = resultSet.getInt("user_id");
                int bookId = resultSet.getInt("book_id");
                String borrowDate = resultSet.getString("borrow_date");
                String dueDate = resultSet.getString("due_date");

                Borrow borrow = new Borrow(borrowId, borrowerId, bookId, borrowDate, dueDate);
                BorrowData.add(borrow);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}