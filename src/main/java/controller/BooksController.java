package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import library.Book;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class BooksController implements Initializable {
    @FXML
    private AnchorPane rootPane;
    @FXML
    private GridPane booksGridPane;
    @FXML
    private Button previousButton, nextButton;

    private int currentPage = 0;
    private final int itemsPerPage = 12; // 2 rows * 6 columns
    private final Map<String, Book> booksMap = new HashMap<>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadBooksFromDatabase();
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
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/BookDetail.fxml"));
                AnchorPane bookDetailPane = loader.load();

                // Get the controller and set the book title
                BookDetailController controller = loader.getController();
                controller.setBookTitle(book.getTitle());

                // Replace the current root pane with the book detail pane
                rootPane.getChildren().setAll(bookDetailPane);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        VBox vBox = new VBox(titleLabel, countLabel, detailButton);
        vBox.setSpacing(10);
        vBox.setPrefSize(200, 200); // Set preferred size for each book cell
        return vBox;
    }
}