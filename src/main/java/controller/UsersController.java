package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class UsersController implements Initializable {

    @FXML
    private TableView<User> userTable;

    @FXML
    private TableColumn<User, String> usernameColumn;

    @FXML
    private TableColumn<User, String> emailColumn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        usernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));

        // Load user data (excluding admin)
        userTable.setItems(getUserData());
    }

    private ObservableList<User> getUserData() {
        // Replace with actual data retrieval logic
        ObservableList<User> users = FXCollections.observableArrayList();
        users.add(new User("user1", "user1@example.com"));
        users.add(new User("user2", "user2@example.com"));
        // Add more users as needed
        return users;
    }

    public static class User {
        private final String username;
        private final String email;

        public User(String username, String email) {
            this.username = username;
            this.email = email;
        }

        public String getUsername() {
            return username;
        }

        public String getEmail() {
            return email;
        }
    }
}