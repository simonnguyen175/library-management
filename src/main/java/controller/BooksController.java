package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import library.Book;
import main.StageManager;

import javafx.scene.image.ImageView;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class BooksController implements Initializable {
    @FXML
    private AnchorPane rootPane;
    @FXML
    private GridPane booksGridPane;
    @FXML
    private Button previousButton, nextButton, newBookButton;

    private int currentPage = 0;
    private final int itemsPerPage = 8; // 2 rows * 4 columns
    private static List<Book> booksList = new ArrayList<>();
    private static List<VBox> bookBoxes = new ArrayList<>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        booksGridPane.setHgap(50); // Set horizontal gap between columns
        booksGridPane.setVgap(35); // Set vertical gap between rows

        if (booksList.isEmpty()) {
            loadBooksFromDatabase();
        }
        loadPage(currentPage);

        previousButton.setOnAction(event -> {
            if (currentPage > 0) {
                currentPage--;
                loadPage(currentPage);
            }
        });

        nextButton.setOnAction(event -> {
            if ((currentPage + 1) * itemsPerPage < booksList.size()) {
                currentPage++;
                loadPage(currentPage);
            }
        });

        newBookButton.setOnAction(event -> {
            StageManager.loadStage("/view/AddBook.fxml", "Add Book", 400, 500);
        });
    }

    private void loadBooksFromDatabase() {
        try (Statement statement = Controller.connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT title, copies FROM books")) {

            while (resultSet.next()) {
                String title = resultSet.getString("title");
                int copies = resultSet.getInt("copies");
                System.out.println(title + " " + copies);
                Book book = new Book(title, copies);
                booksList.add(book);
                bookBoxes.add(createBookBox(book));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadPage(int page) {
        booksGridPane.getChildren().clear();
        int start = page * itemsPerPage;
        int end = Math.min(start + itemsPerPage, bookBoxes.size());

        for (int i = start; i < end; i++) {
            booksGridPane.add(bookBoxes.get(i), (i - start) % 4, (i - start) / 4); // 4 columns
        }
    }

    private VBox createBookBox(Book book) {
        Label titleLabel = new Label(book.getTitle());
        titleLabel.setMaxWidth(125);
        titleLabel.setPrefHeight(0); // Set a fixed height for the title label
        titleLabel.setWrapText(true);// Set a fixed height for the title label

        Label countLabel = new Label("Copies: " + book.getCopies());
        countLabel.setMaxWidth(125);
        countLabel.setWrapText(true);

        ImageView bookImageView = new ImageView();
        bookImageView.setFitHeight(200.0);
        bookImageView.setFitWidth(100.0);
        bookImageView.setPreserveRatio(true);
        bookImageView.setImage(new Image("https://nxbhcm.com.vn/Image/Biasach/dacnhantam86.jpg"));

        Button detailButton = new Button("Detail");
        detailButton.setOnAction(event -> {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/BookDetail.fxml"));
                AnchorPane bookDetailPane = loader.load();

                // Get the controller and set the book title
                BookDetailController controller = loader.getController();
                controller.setBook(book);

                // Replace the current root pane with the book detail pane
                rootPane.getChildren().setAll(bookDetailPane);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        Button removeButton = new Button("Remove");

        HBox buttonBox = new HBox(10, detailButton, removeButton);
        VBox vBox = new VBox(10, bookImageView, titleLabel, countLabel, buttonBox);
        vBox.setSpacing(10);
        vBox.setPrefSize(200, 200); // Set preferred size for each book cell
        return vBox;
    }
}