package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import library.Book;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class BooksController extends DashboardController implements Initializable {

    @FXML
    private GridPane booksGridPane;
    @FXML
    private AnchorPane rootPane;
    @FXML
    private Button previousButton, nextButton;
    @FXML
    private TextField searchBar;

    private int currentPage = 0;
    private final int itemsPerPage = 12; // 2 rows * 6 columns
    private final Map<String, Book> booksMap = new HashMap<>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadBooksFromDatabase();

        // Set column and row constraints for consistent cell sizes
        for (int i = 0; i < 6; i++) { // 6 columns
            ColumnConstraints colConstraints = new ColumnConstraints();
            colConstraints.setPrefWidth(200); // Set preferred width for each cell
            booksGridPane.getColumnConstraints().add(colConstraints);
        }
        for (int i = 0; i < 2; i++) { // 2 rows
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
            if ((currentPage + 1) * itemsPerPage < booksMap.size()) {
                currentPage++;
                loadPage(currentPage);
            }
        });
    }

    private void loadBooksFromDatabase() {
        try (Statement statement = Controller.connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT title, COUNT(*) as copies FROM Book GROUP BY title")) {

            while (resultSet.next()) {
                String title = resultSet.getString("title");
                int copies = resultSet.getInt("copies");
                booksMap.put(title, new Book(title, copies));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadPage(int page) {
        booksGridPane.getChildren().clear();
        int start = page * itemsPerPage;
        int end = Math.min(start + itemsPerPage, booksMap.size());

        int index = 0;
        for (Map.Entry<String, Book> entry : booksMap.entrySet()) {
            if (index >= start && index < end) {
                VBox bookBox = createBookBox(entry.getValue());
                booksGridPane.add(bookBox, (index - start) % 6, (index - start) / 6); // 6 columns
            }
            index++;
        }
    }

    private VBox createBookBox(Book book) {
        Label titleLabel = new Label(book.getTitle());
        Label countLabel = new Label("Copies: " + book.getCopies());
        Button detailButton = new Button("Detail");
        detailButton.setOnAction(event -> {
            try{
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/BookDetail.fxml"));
                Pane bookDetailPane = loader.load();

                BookDetailController controller = loader.getController();
                controller.setBookTitle(book.getTitle());

                rootPane.getChildren().setAll(bookDetailPane);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        VBox vBox = new VBox(titleLabel, countLabel, detailButton);
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