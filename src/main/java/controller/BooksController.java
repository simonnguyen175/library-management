package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.shape.Rectangle;
import javafx.stage.Modality;
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

    private APIController apiController = APIController.getInstance();

    private int currentPage = 0;
    private final int itemsPerPage = 10; // 2 rows * 5 columns
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
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/AddBook.fxml"));
                AnchorPane addBookPane = loader.load();
                Stage addBookStage = new Stage();
                addBookStage.setResizable(false);
                addBookStage.setScene(new Scene(addBookPane));
                addBookStage.setTitle("Add Book");
                addBookStage.initModality(Modality.APPLICATION_MODAL);
                addBookPane.setOnMouseClicked(mouseEvent -> addBookPane.requestFocus());

                addBookStage.showAndWait();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    private void loadBooksFromDatabase() {
        try (Statement statement = Controller.connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT title, copies, imageUrl FROM books")) {

            while (resultSet.next()) {
                String title = resultSet.getString("title");
                int copies = resultSet.getInt("copies");
                String imageUrl = resultSet.getString("imageUrl");

//                if (imageUrl == null || imageUrl.isEmpty()) {
//                    imageUrl = apiController.getBookInfoFromAPI(resultSet.getString("isbn")).getImageUrl();
//                    if (imageUrl != null) {
//                        try (Statement updateStatement = Controller.connection.createStatement()) {
//                            updateStatement.executeUpdate("UPDATE books SET imageUrl = '" + imageUrl + "' WHERE title = '" + title + "'");
//                        }
//                    }
//                }

                Book book = new Book(title, copies, imageUrl);
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
            booksGridPane.add(bookBoxes.get(i), (i - start) % 5, (i - start) / 5); // 5 columns
        }
    }

    private VBox createBookBox(Book book) {
        Label titleLabel = new Label(book.getTitle());
        titleLabel.setMaxWidth(100);
        titleLabel.setPrefHeight(0);
        titleLabel.setWrapText(true);

        Label countLabel = new Label("Copies: " + book.getCopies());
        countLabel.setMaxWidth(100);
        countLabel.setWrapText(true);

        ImageView bookImageView = new ImageView();
        if (book.getImageUrl() != null) {
            new Thread(() -> {
                Image image = new Image(book.getImageUrl());
                bookImageView.setImage(image);
            }).start();
        } else {
            bookImageView.setImage(new Image("/resources/image-placeholder.png"));
        }

        bookImageView.setFitHeight(100);
        bookImageView.setFitWidth(75);
        bookImageView.setPreserveRatio(true);

        Button detailButton = new Button("Detail");
        detailButton.setOnAction(event -> {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/BookDetail.fxml"));
                AnchorPane bookDetailPane = loader.load();

                BookDetailController controller = loader.getController();
                controller.setBook(book);

                rootPane.getChildren().setAll(bookDetailPane);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        Button removeButton = new Button("Remove");

        HBox buttonBox = new HBox(10, detailButton, removeButton);
        VBox vBox = new VBox(10, bookImageView, titleLabel, countLabel, buttonBox);
        vBox.setSpacing(10);
        vBox.setPrefSize(150, 150);
        return vBox;
    }
}