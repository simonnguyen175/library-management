package controller;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import library.Book;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutionException;

public class BooksController extends Controller implements Initializable {

    @FXML
    private GridPane booksGridPane;
    @FXML
    private Button previousButton, nextButton;
    @FXML
    private TextField searchBar;
    @FXML
    private Button Search;

    private int currentPage = 0;
    private final int itemsPerPage = 6; // Adjust as needed
    private List<Book> books = new ArrayList<>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Example data, replace with your actual data source
//        books.add(new Book("Book 1"));
//        books.add(new Book("Book 2"));
//        books.add(new Book("Book 3"));
//        books.add(new Book("Book 4"));
//        books.add(new Book("Book 5"));
//        books.add(new Book("Book 6"));
//        books.add(new Book("Book 7"));
//        books.add(new Book("Book 8"));
            super.initialize();
            books = new ArrayList<>();
        // Set column and row constraints for consistent cell sizes
        for (int i = 0; i < 3; i++) { // Adjust the number of columns as needed
            ColumnConstraints colConstraints = new ColumnConstraints();
            colConstraints.setPrefWidth(220); // Set preferred width for each cell
            booksGridPane.getColumnConstraints().add(colConstraints);
        }
        for (int i = 0; i < 2; i++) { // Adjust the number of rows as needed
            RowConstraints rowConstraints = new RowConstraints();
            rowConstraints.setPrefHeight(230); // Set preferred height for each cell
            booksGridPane.getRowConstraints().add(rowConstraints);
        }

        Search.setOnAction(event -> {
            String title = searchBar.getText();
            System.out.println ("Searching ......");
            Task<List<Book>> task = new Task<>(){
                @Override
                protected List<Book> call() throws Exception {
                    String query = "SELECT * FROM `books` WHERE title LIKE ? LIMIT 10";
                    PreparedStatement preparedStatement = connection.prepareStatement(query);
                    preparedStatement.setString(1,"%" + title.trim()+ "%");

                    ResultSet resultSet = preparedStatement.executeQuery();
                    List<Book> res = new ArrayList<>();
                    int nums = 10 ;
                    while (resultSet.next() && nums > 0){
                        Book book = new Book();
                        book.setTitle(resultSet.getString ("title"));
                        book.setIsbn(resultSet.getString("isbn"));
                        book.setImageUrl(resultSet.getString("image_url"));
                        book.setAuthor(resultSet.getString("author"));
                        book.setBook_id(resultSet.getString("book_id"));
                        res.add (book);
                        nums -- ;
                    }
                    if (res.isEmpty())return new ArrayList<>();
                    return res;
                }
            };

            Object lock = new Object ();
            task.setOnSucceeded(e -> {
                try {
                    books = task.get();
                    System.out.println ("Searching Success");
                    loadPage(currentPage);
                } catch (InterruptedException | ExecutionException ex) {
                    throw new RuntimeException(ex);
                }

            });

            task.setOnFailed(e -> {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("cant search");
                alert.setHeaderText("Having error");
                alert.showAndWait();
            });
            task.run();// Chạy tác vụ tìm kiếm trong luồng riêng biệt
        });

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

    private synchronized void loadPage(int page) {
        booksGridPane.getChildren().clear();
        int start = page * itemsPerPage;
        int end = Math.min(start + itemsPerPage, books.size());

        for (int i = start; i < end; i++) {
            VBox bookBox = createBookBox(books.get(i));
            booksGridPane.add(bookBox, (i - start) % 3, (i - start) / 3); // Adjust the number of columns as needed
        }
    }

    private VBox createBookBox(Book book) {
        Label titleLabel = new Label(book.getTitle());

        Button detailsButton = new Button("Show Details");
        detailsButton.setOnAction(event -> {
            Task<Book> task = new Task<>() {
                @Override
                protected Book call() throws Exception {
                    return getBook(book.getIsbn());
                }
            };

            task.setOnSucceeded(e -> {
                Book temp = getBook(book.getIsbn());
                openNewWindow(Objects.requireNonNullElse(temp, book));
            });

            task.run();
        });
        VBox vBox;
        if (!books.isEmpty() && !book.getImageUrl().equals("No Image"))
        {
            ImageView imageView = new ImageView(new Image(book.getImageUrl()));
            imageView.setFitHeight(150);
            imageView.setFitWidth(150);
            imageView.setPreserveRatio(true);
            vBox= new VBox(titleLabel,imageView,detailsButton);
        }
        else vBox = new VBox(titleLabel, detailsButton);
        vBox.setSpacing(10);
        vBox.setPrefSize(200, 200); // Set preferred size for each book cell
        return vBox;
    }

    private void showBookDetails(String title) {
        // Implement the logic to switch to the tab that shows the book details

        // Example: tabPane.getSelectionModel().select(detailsTab);
    }

    private synchronized void openNewWindow (Book book) {
        if (book == null) return;
        Stage newWindow = new Stage(); // Tạo một cửa sổ mới
        newWindow.setTitle("Book");

        Label book_Info = new Label();
        book_Info.setText("Title:\t" + book.getTitle() + "\n"
                +"Author:\t"+book.getAuthor()+"\n"
                +"Publisher:\t" + book.getPublisher()+"\n"
                +"Pages:\t" + book.getPages() +"\n"
                +"Language:\t" + book.getLanguage() + "\n"
                +"Genre:\t" + book.getGenre() + "\n");
        book_Info.setWrapText(true);
        book_Info.setPrefWidth(300);

        Label description = new Label();
        description.setText("Description:\t" + book.getDescription());
        description.setWrapText(true);
        description.setPrefWidth(270);

        ImageView imageView = new ImageView(new Image(book.getImageUrl()));
        imageView.setFitHeight(150);
        imageView.setFitWidth(150);
        imageView.setPreserveRatio(true);
        imageView.setX(50);
        imageView.setY(90);

        AnchorPane secondaryLayout = new AnchorPane();

        secondaryLayout.getChildren().add(imageView);
        secondaryLayout.getChildren().add(book_Info);
        secondaryLayout.getChildren().add(description);

        AnchorPane.setTopAnchor(imageView,30.0);
        AnchorPane.setLeftAnchor(imageView,70.0);

        AnchorPane.setTopAnchor(description,50.0);
        AnchorPane.setLeftAnchor(description,370.0);

        AnchorPane.setTopAnchor(book_Info,200.0);
        AnchorPane.setLeftAnchor(book_Info,50.0);

        Scene secondScene = new Scene(secondaryLayout, 660, 450);

        newWindow.setScene(secondScene);
        newWindow.setResizable(false);
        newWindow.setMaximized(false);
        newWindow.show();
    }
}