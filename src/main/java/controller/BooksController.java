package controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import library.Book;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class BooksController implements Initializable {

    @FXML
    private GridPane booksGridPane;
    @FXML
    private Button previousButton, nextButton;
    @FXML
    private TextField searchBar;

    private int currentPage = 0;
    private final int itemsPerPage = 6; // Adjust as needed
    private final List<Book> books = new ArrayList<>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Example data, replace with your actual data source
        books.add(new Book("Book 1"));
        books.add(new Book("Book 2"));
        books.add(new Book("Book 3"));
        books.add(new Book("Book 4"));
        books.add(new Book("Book 5"));
        books.add(new Book("Book 6"));
        books.add(new Book("Book 7"));
        books.add(new Book("Book 8"));

        // Set column and row constraints for consistent cell sizes
        for (int i = 0; i < 3; i++) { // Adjust the number of columns as needed
            ColumnConstraints colConstraints = new ColumnConstraints();
            colConstraints.setPrefWidth(200); // Set preferred width for each cell
            booksGridPane.getColumnConstraints().add(colConstraints);
        }
        for (int i = 0; i < 2; i++) { // Adjust the number of rows as needed
            RowConstraints rowConstraints = new RowConstraints();
            rowConstraints.setPrefHeight(200); // Set preferred height for each cell
            booksGridPane.getRowConstraints().add(rowConstraints);
        }

        loadPage(currentPage);

        previousButton.setOnAction(event -> {
            if (currentPage > 0) {
                currentPage--;
                loadPage(currentPage);
            }
        });

        nextButton.setOnAction(event -> {
            if ((currentPage + 1) * itemsPerPage < books.size()) {
                currentPage++;
                loadPage(currentPage);
            }
        });
    }

    private void loadPage(int page) {
        booksGridPane.getChildren().clear();
        int start = page * itemsPerPage;
        int end = Math.min(start + itemsPerPage, books.size());

        for (int i = start; i < end; i++) {
            VBox bookBox = createBookBox(books.get(i).getTitle());
            booksGridPane.add(bookBox, (i - start) % 3, (i - start) / 3); // Adjust the number of columns as needed
        }
    }

    private VBox createBookBox(String title) {
        Label titleLabel = new Label(title);

        Button detailsButton = new Button("Show Details");
        detailsButton.setOnAction(event -> {
            System.out.println("Showing details for: " + title);
            showBookDetails(title);
        });

        VBox vBox = new VBox(titleLabel, detailsButton);
        vBox.setSpacing(10);
        vBox.setPrefSize(200, 200); // Set preferred size for each book cell
        return vBox;
    }

    private void showBookDetails(String title) {
        // Implement the logic to switch to the tab that shows the book details
        System.out.println("Showing details for: " + title);
        // Example: tabPane.getSelectionModel().select(detailsTab);
    }
}