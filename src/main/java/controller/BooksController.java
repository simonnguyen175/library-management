package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
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
    private Button searchButton, previousButton, nextButton, newBookButton;
    @FXML
    private ComboBox<String> genreComboBox;
    @FXML
    private TextField bookTitleTextField, authorTextField;

    private APIController apiController = APIController.getInstance();

    private int currentPage = 0;
    private final int itemsPerPage = 10; // 2 rows * 5 columns
    private List<VBox> bookBoxes = new ArrayList<>();
    private List<HBox> buttonBoxes = new ArrayList<>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        booksGridPane.setHgap(50); // Set horizontal gap between columns
        booksGridPane.setVgap(35); // Set vertical gap between rows

        loadBooksFromDatabase("SELECT title, copies, imageUrl FROM books");
        loadGenresFromDatabase();
        loadPage(currentPage);
        System.out.println(buttonBoxes.size());

        previousButton.setOnAction(event -> {
            if (currentPage > 0) {
                currentPage--;
                loadPage(currentPage);
            }
        });

        nextButton.setOnAction(event -> {
            if ((currentPage + 1) * itemsPerPage < bookBoxes.size()) {
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

        searchButton.setOnAction(event -> searchBooks());
        genreComboBox.setOnAction(event -> searchBooks());
    }

    private void searchBooks() {
        String bookTitle = bookTitleTextField.getText().trim();
        String author = authorTextField.getText().trim();
        String selectedGenre = genreComboBox.getValue();
        String query = "SELECT title, copies, imageUrl FROM books WHERE 1=1";

        if (!bookTitle.isEmpty()) {
            query += " AND title LIKE '%" + bookTitle + "%'";
        }
        if (!author.isEmpty()) {
            query += " AND author LIKE '%" + author + "%'";
        }
        if (selectedGenre != null && !"All".equals(selectedGenre)) {
            query += " AND genre = '" + selectedGenre + "'";
        }

        loadBooksFromDatabase(query);
        loadPage(currentPage);
    }

    private void loadGenresFromDatabase() {
        genreComboBox.getItems().add("All");
        try (Statement statement = Controller.connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT DISTINCT genre FROM books")) {

            while (resultSet.next()) {
                String genre = resultSet.getString("genre");
                genreComboBox.getItems().add(genre);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadBooksFromDatabase(String query) {
        booksGridPane.getChildren().clear();
        bookBoxes.clear();
        buttonBoxes.clear();

        try (Statement statement = Controller.connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                String title = resultSet.getString("title");
                int copies = resultSet.getInt("copies");
                String imageUrl = resultSet.getString("imageUrl");

                if (imageUrl == null || imageUrl.isEmpty()) {
                    imageUrl = apiController.getBookInfoFromAPI(resultSet.getString("isbn")).getImageUrl();
                    if (imageUrl != null) {
                        try (Statement updateStatement = Controller.connection.createStatement()) {
                            updateStatement.executeUpdate("UPDATE books SET imageUrl = '" + imageUrl + "' WHERE title = '" + title + "'");
                        }
                    }
                }

                Book book = new Book(title, copies, imageUrl);
                bookBoxes.add(createBookBox(book));
                buttonBoxes.add(buttonBox(book));
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
            VBox curBox = new VBox(bookBoxes.get(i), buttonBoxes.get(i));
            curBox.setSpacing(10);
            booksGridPane.add(curBox, (i - start) % 5, (i - start) / 5); // 5 columns
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

        VBox vBox = new VBox(10, bookImageView, titleLabel, countLabel);
        vBox.setSpacing(10);
        vBox.setPrefSize(150, 150);
        return vBox;
    }

    private HBox buttonBox(Book book) {
        Button detailButton = new Button("Detail");
        detailButton.setOnAction(event -> {
            try {
                System.out.println("Detail button clicked");
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/BookDetail.fxml"));
                AnchorPane bookDetailPane = loader.load();

                BookDetailController controller = loader.getController();
                controller.setBook(book);

                rootPane.getChildren().clear();
                rootPane.getChildren().setAll(bookDetailPane);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        Button removeButton = new Button("Remove");
        removeButton.setOnAction(event -> showRemoveConfirmation(book));

        HBox BBox = new HBox(10, detailButton, removeButton);
        return BBox;
    }

    private void showRemoveConfirmation(Book book) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Remove");
        alert.setHeaderText(null);
        alert.setContentText("Bạn có chắc muốn xoá sách " + book.getTitle() + "?");

        ButtonType cancelButton = new ButtonType("Huỷ", ButtonBar.ButtonData.CANCEL_CLOSE);
        ButtonType deleteButton = new ButtonType("Xoá", ButtonBar.ButtonData.OK_DONE);

        alert.getButtonTypes().setAll(cancelButton, deleteButton);

        alert.showAndWait().ifPresent(response -> {
            if (response == deleteButton) {
                removeBook(book);
            }
        });
    }

    private void removeBook(Book book) {
        // Implement the logic to remove the book from the database and update the UI
        // For example:
//        try (Statement statement = Controller.connection.createStatement()) {
//            statement.executeUpdate("DELETE FROM books WHERE title = '" + book.getTitle() + "'");
//            loadBooksFromDatabase("SELECT title, copies, imageUrl FROM books");
//            loadPage(currentPage);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }


}