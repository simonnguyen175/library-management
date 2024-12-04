package controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import library.Book;
import library.Library;
import library.User;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class HomeController implements Initializable {
    @FXML
    private VBox topUsersVbox;
    @FXML
    private HBox topBooksHbox;
    @FXML
    private Label totalUsersLabel;
    @FXML
    private Label totalBooksLabel;
    @FXML
    private ProgressIndicator progress;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Library myLib = Library.getInstance();
        List<User> topUsers = myLib.getTopUsers();
        List<Book> topBooks = myLib.getTopBooks();

        populateUserVBox(topUsersVbox, topUsers);
        populateBookHBox(topBooksHbox, topBooks);

        topBooksHbox.setSpacing(25);
        topUsersVbox.setSpacing(25);

        totalBooksLabel.setText(String.valueOf(myLib.getTotalBooks()));
        totalUsersLabel.setText(String.valueOf(myLib.getTotalUser()));

        progress.setVisible(true);
    }

    private void populateUserVBox(VBox vbox, List<User> users) {
        for (User user : users) {
            Label label = new Label(user.getFullname());
            label.setStyle("-fx-font-size: 15px; -fx-font-weight: bold;");
            vbox.getChildren().add(label);
        }
    }

    private void populateBookHBox(HBox hbox, List<Book> books) {
        new Thread(() -> {
            for (Book book : books) {
                ImageView imageView = new ImageView(new Image(book.getImageUrl()));
                imageView.setFitHeight(150);
                imageView.setFitWidth(100);
                Platform.runLater(() -> hbox.getChildren().add(imageView));
            }
            progress.setVisible(false);
        }).start();
    }
}