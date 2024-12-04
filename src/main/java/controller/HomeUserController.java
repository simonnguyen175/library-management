package controller;

import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import library.Book;
import library.Library;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class HomeUserController {
    @FXML
    private AnchorPane rootPane;
    @FXML
    private Button backButton;

    @FXML
    private Button forwardButton;

    @FXML
    private StackPane bookStackPane;

    @FXML
    private HBox bookHBox;
    @FXML
    private HBox bookHBox1;

    @FXML
    private ProgressIndicator progressIndicator;

    @FXML
    private ProgressIndicator progressIndicator1;

    private List<Book> recommendedBooks;
    private List<Book> newArrivalBooks;
    private List<ImageView> bookImageViews;
    private int currentIndex = 0;

    private final double IMAGE_SPACING = 25; // Spacing between images
    private final int MAX_VISIBLE = 3; // Maximum visible books at a time
    private final double IMAGE_WIDTH = 100; // Width of each image
    private final double IMAGE_HEIGHT = 150; // Height of each image

    @FXML
    private void initialize() {
        // Set button actions
        backButton.setOnAction(event -> showPreviousBooks());
        forwardButton.setOnAction(event -> showNextBooks());
        bookHBox.setSpacing(IMAGE_SPACING);
        bookHBox1.setSpacing(IMAGE_SPACING);

        // Set clipping area for the HBox
        Rectangle clip = new Rectangle(MAX_VISIBLE * (IMAGE_WIDTH + IMAGE_SPACING) - IMAGE_SPACING, IMAGE_HEIGHT);
        bookStackPane.setClip(clip);

        // Show progress indicator
        progressIndicator.setVisible(true);
        progressIndicator1.setVisible(true);

        // Load recommended books and initialize the image views
        Library myLib = Library.getInstance();
        this.recommendedBooks = myLib.suggestBook(Library.userId);
        initializeImageViews();

        // Load new arrival books
        loadNewArrivals();
    }

    private void initializeImageViews() {
        Thread thread = new Thread(() -> {
            List<ImageView> tempImageViews = new ArrayList<>();
            for (Book book : recommendedBooks) {
                ImageView bookImageView = new ImageView(book.getImageUrl());
                bookImageView.setFitHeight(IMAGE_HEIGHT);
                bookImageView.setFitWidth(IMAGE_WIDTH);
                setOnClickAction(bookImageView, book);
                setHoverEffect(bookImageView);
                tempImageViews.add(bookImageView);
            }

            Platform.runLater(() -> {
                bookImageViews = tempImageViews;
                bookHBox.getChildren().addAll(bookImageViews);
                updateBookDisplay();
                // Hide progress indicator
                progressIndicator.setVisible(false);
            });
        });
        thread.start();
    }

    private void updateBookDisplay() {
        // Animate the transition to update the display
        double translateX = -(currentIndex * (IMAGE_WIDTH + IMAGE_SPACING)); // Calculate the translate X position
        animateScroll(translateX);
    }

    private void showPreviousBooks() {
        if (currentIndex > 0) {
            currentIndex--; // Move back by one index
            updateBookDisplay();
        }
    }

    private void showNextBooks() {
        if (currentIndex + MAX_VISIBLE < bookImageViews.size()) {
            currentIndex++; // Move forward by one index
            updateBookDisplay();
        }
    }

    private void animateScroll(double translateX) {
        TranslateTransition transition = new TranslateTransition(Duration.millis(500), bookHBox); // 500ms animation
        transition.setToX(translateX); // Set the target translation
        transition.play(); // Start the animation
    }

    private void loadNewArrivals() {
        Thread thread = new Thread(() -> {

            Library myLib = Library.getInstance();
            List<Book> newArrivals = myLib.getNewArrivals(3); // Fetch the last 3 books

            Platform.runLater(() -> {
                for (Book book : newArrivals) {
                    ImageView bookImageView = new ImageView(book.getImageUrl());
                    bookImageView.setFitHeight(IMAGE_HEIGHT + 50);
                    bookImageView.setFitWidth(IMAGE_WIDTH + 50);

                    setOnClickAction(bookImageView, book);
                    setHoverEffect(bookImageView);

                    bookHBox1.getChildren().add(bookImageView);
                }
                progressIndicator1.setVisible(false);
            });
        });
        thread.start();
    }

    private void setHoverEffect(ImageView imageView) {
        Tooltip tooltip = new Tooltip("Click to view details");
        Tooltip.install(imageView, tooltip);
        imageView.setOnMouseEntered(event -> imageView.setOpacity(0.7));
        imageView.setOnMouseExited(event -> imageView.setOpacity(1.0));
    }

    private void setOnClickAction(ImageView imageView, Book book) {
        imageView.setOnMouseClicked(event -> {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/BookDetail.fxml"));
                AnchorPane bookDetailPane = loader.load();

                BookDetailController controller = loader.getController();
                controller.setBook(book, "/view/HomeUser.fxml");

                rootPane.getChildren().clear();
                rootPane.getChildren().setAll(bookDetailPane);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}