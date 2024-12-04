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
    private ProgressIndicator progressIndicator;

    @FXML
    private ImageView newArrival1;

    @FXML
    private ImageView newArrival2;

    @FXML
    private ImageView newArrival3;

    private List<Book> recommendedBooks;
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

        // Set clipping area for the HBox
        Rectangle clip = new Rectangle(MAX_VISIBLE * (IMAGE_WIDTH + IMAGE_SPACING) - IMAGE_SPACING, IMAGE_HEIGHT);
        bookStackPane.setClip(clip);

        // Show progress indicator
        progressIndicator.setVisible(true);

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
                Tooltip tooltip = new Tooltip(book.getTitle());
                Tooltip.install(bookImageView, tooltip);

                bookImageView.setOnMousePressed(event -> {
                    try {
                        // Handle book click
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
                if (newArrivals.size() >= 3) {
                    newArrival1.setImage(new Image(newArrivals.get(0).getImageUrl()));
                    newArrival2.setImage(new Image(newArrivals.get(1).getImageUrl()));
                    newArrival3.setImage(new Image(newArrivals.get(2).getImageUrl()));
                }
            });
        });
        thread.start();
    }
}