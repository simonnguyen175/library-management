package controller;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Popup;
import javafx.stage.Stage;
import library.*;
import services.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import services.QRCodeGenerator;
import javafx.scene.input.MouseEvent;

import javax.swing.plaf.TableHeaderUI;
import java.io.IOException;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import static controller.Controller.connection;

public class BookDetailController extends Controller {
    @FXML
    private Button backButton;
    @FXML
    private AnchorPane rootPane;
    @FXML
    private Label labelTitle;
    @FXML
    private TextArea description;
    @FXML
    private ImageView bookImage;
    @FXML
    private Button borrowButton;
    @FXML
    private Button QRButton;
    @FXML
    private Label authorLabel;
    @FXML
    private Label genreLabel;
    @FXML
    private Label isbnLabel;
    @FXML
    private Label pagesLabel;
    @FXML
    private Label publisherLabel;
    @FXML
    private Label yearLabel;
    @FXML
    private Label languageLabel;
    @FXML
    private VBox commentList;
    @FXML
    private TextField commentField;
    @FXML
    private ScrollPane commentScrollPane;
    @FXML
    private Button fixButton;

    private ImageView qrCodeImage;

    private Book book;

    private String backPane;

    @FXML
    public void initialize() {
        backButton.setOnAction(event -> {
            handleBackButtonAction();
        });

        QRButton.setOnAction(event -> {
            handleQRButtonAction();
        });

        if (Library.role == "admin") {
            borrowButton.setVisible(true);
        } else {
            borrowButton.setVisible(false);
            QRButton.setLayoutX(QRButton.getLayoutX() + 25);
        }

        borrowButton.setOnAction(event -> {
            handleBookBorrowAction();
        });

        if (Library.role == "admin") {
            commentField.setDisable(true);
        } else commentField.setDisable(false);

        commentField.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                addComment();
            }
        });

        commentScrollPane.setContent(commentList);
        commentScrollPane.setFitToWidth(true);
        commentScrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        if (Library.role == "admin") {
            fixButton.setVisible(true);
        } else fixButton.setVisible(false);
        fixButton.setOnAction(event -> {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/FixBook.fxml"));
                AnchorPane fixBookPane = loader.load();
                Stage fixBookStage = new Stage();
                fixBookStage.setResizable(false);
                fixBookStage.setScene(new Scene(fixBookPane));
                fixBookStage.setTitle("Fix Book");
                FixBookController fixBookController = loader.getController();
                fixBookController.setBook(book);
                fixBookStage.initModality(Modality.APPLICATION_MODAL);
                fixBookPane.setOnMouseClicked(mouseEvent -> fixBookPane.requestFocus());
                fixBookStage.showAndWait();

                // Update the book details
                setBook(book, "/view/Books.fxml");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    public void setBook(Book book, String backPane) {
        this.book = book;
        this.backPane = backPane;
        labelTitle.setText(book.getTitle());

        new Thread(() -> {
            Image image = new Image(book.getImageUrl());
            bookImage.setImage(image);
        }).start();

        new Thread(() -> {
            APIController apiController = APIController.getInstance();
            String des = apiController.getBookDescriptionFromAPI(book.getTitle());
            if (des != null) {
                description.setText(des);
            } else {
                description.setText("No description available");
            }
        }).start();

        // Set the book details
        authorLabel.setText("Tác giả: " + book.getAuthor());
        publisherLabel.setText("Nhà xuất bản: " + book.getPublisher());
        genreLabel.setText("Thể loại: " + book.getGenre());
        pagesLabel.setText("Số trang: " + book.getPages());
        yearLabel.setText("Năm xuất bản: " + book.getPublicationYear());
        languageLabel.setText("Ngôn ngữ: " + book.getLanguage());
        isbnLabel.setText("ISBN: " + book.getIsbn());

        // Add comments to the commentList
        new Thread(() -> {
            commentList.setSpacing(20);
            loadComments(this.book.getBookId());
        }).start();

        // Generate QR code
        new Thread(() -> {
            qrCodeImage = new ImageView();
            QRCodeGenerator QR = QRCodeGenerator.getInstance();
            qrCodeImage.setImage(QR.generateQRCode(book.getIsbn()));
        }).start();
    }

    private void loadComments(int bookId) {
        List<Comment> comments = getComments(bookId);
        Platform.runLater(() -> {
            commentList.getChildren().clear();
            for (Comment comment : comments) {
                commentList.getChildren().add(createCommentNode(comment));
            }
        });
    }

    private List<Comment> getComments(int bookId) {
        List<Comment> comments = new ArrayList<>();
        String query = "SELECT * FROM comments WHERE book_id = " + bookId
                + " ORDER BY comment_id DESC";

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                int commentId = rs.getInt("comment_id");
                int userId = rs.getInt("user_id");
                String content = rs.getString("content");
                Date date = rs.getDate("comment_date");
                comments.add(new Comment(commentId, userId, bookId, content, date));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return comments;
    }

    private Node createCommentNode(Comment comment) {
        VBox commentBox = new VBox();
        commentBox.setSpacing(5);

        Text userName = new Text(comment.getUsername());
        userName.setStyle("-fx-font-weight: bold;");
        userName.setWrappingWidth(410);

        Text contentText = new Text(comment.getContent());
        contentText.setWrappingWidth(410);

        Text dateText = new Text(comment.getDate());
        dateText.setStyle("-fx-font-size: 10px; -fx-text-fill: gray;");
        dateText.setWrappingWidth(410);

        commentBox.getChildren().addAll(userName, contentText, dateText);
        return commentBox;
    }

    private void addComment() {
        String content = commentField.getText();
        Library myLib = Library.getInstance();
        myLib.addComment(new Comment(0, Library.userId,
                book.getBookId(), content, new Date(System.currentTimeMillis())));
        commentField.clear();
        commentField.getParent().requestFocus();
        loadComments(book.getBookId());
    }

    private void handleBackButtonAction() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(backPane));
            AnchorPane booksPane = loader.load();
            rootPane.getChildren().setAll(booksPane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void handleQRButtonAction() {
        Popup popup = new Popup();

        // Create a DropShadow effect
        DropShadow dropShadow = new DropShadow();
        dropShadow.setRadius(10.0);
        dropShadow.setOffsetX(0);
        dropShadow.setOffsetY(0);
        dropShadow.setColor(Color.BLACK);
        qrCodeImage.setEffect(dropShadow);

        // Create a StackPane to add padding and set background color to white
        StackPane stackPane = new StackPane(qrCodeImage);
        stackPane.setStyle("-fx-padding: 10; -fx-background-color: darkgrey; -fx-border-color: darkgrey; -fx-border-width: 0;");

        popup.getContent().add(stackPane);
        popup.setAutoHide(true);
        popup.show(QRButton.getScene().getWindow());
    }

    private void handleBookBorrowAction() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/BorrowBook.fxml"));
            AnchorPane borrowBookPane = loader.load();
            Stage borrowBookStage = new Stage();
            borrowBookStage.setResizable(false);
            borrowBookStage.setScene(new Scene(borrowBookPane));
            borrowBookStage.setTitle("BorrowBook Book");
            BorrowBookController borrowBookController = loader.getController();
            borrowBookController.setBook(book);
            borrowBookStage.initModality(Modality.APPLICATION_MODAL);
            borrowBookPane.setOnMouseClicked(mouseEvent -> borrowBookPane.requestFocus());
            borrowBookStage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}