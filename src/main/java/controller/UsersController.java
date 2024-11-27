package controller;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import library.Library;
import library.User;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

public class UsersController extends Controller implements Initializable {

    @FXML
    private TableView<User> userTable;

    @FXML
    private TableColumn<User, Integer> userIdColumn;

    @FXML
    private TableColumn<User, String> fullnameColumn;

    @FXML
    private TableColumn<User, String> usernameColumn;

    @FXML
    private TableColumn<User, Void> deleteColumn;

    @FXML
    private TextField searchTextField;

    @FXML
    private Button searchButton;

    @FXML
    private Label emailLabel;

    @FXML
    private Label phoneLabel;

    @FXML
    private Label totalBooksBorrowedLabel;

    @FXML
    private Label totalBooksCurrentlyBorrowedLabel;

    @FXML
    private TextField fullnameField;

    @FXML
    private TextField usernameField;

    @FXML
    private TextField phoneField;

    @FXML
    private TextField emailField;

    @FXML
    private Button addButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        userIdColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getUserId()).asObject());
        fullnameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getFullname()));
        usernameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getUsername()));

        addDeleteButtonToTable();

        // Load user data from the database
        userTable.setItems(getUserData());

        searchTextField.setOnAction(event -> searchUser());
        searchButton.setOnAction(event -> searchUser());

        // Add listener to table selection
        userTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> showUserDetails(newValue));

        // Add action for addButton
        addButton.setOnAction(event -> addUser());
    }

    private void addDeleteButtonToTable() {
        deleteColumn.setCellFactory(param -> new TableCell<>() {
            private final Button deleteButton = new Button("Delete");
            private final HBox hBox = new HBox(deleteButton);

            {
                hBox.setStyle("-fx-alignment: center;");
                HBox.setHgrow(deleteButton, Priority.ALWAYS);

                deleteButton.setOnAction(event -> {
                    User user = getTableView().getItems().get(getIndex());
                    deleteUser(user);
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(hBox);
                }
            }
        });
    }

    private void deleteUser(User user) {
        String query = "DELETE FROM users WHERE user_id = " + user.getUserId() + ";";

        Thread dbThread = new Thread(() -> {
            try (Statement stmt = connection.createStatement()) {
                stmt.executeUpdate(query);
                // Reload the user data
                ObservableList<User> updatedUsers = getUserData();
                userTable.setItems(updatedUsers);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
        dbThread.start();
    }

    private void addUser() {
        String fullname = ((TextField) addButton.getScene().lookup("#fullnameField")).getText();
        String username = ((TextField) addButton.getScene().lookup("#usernameField")).getText();
        String phone = ((TextField) addButton.getScene().lookup("#phoneField")).getText();
        String email = ((TextField) addButton.getScene().lookup("#emailField")).getText();

        if (fullname.isEmpty() || username.isEmpty() || phone.isEmpty() || email.isEmpty()) {
            System.out.println("Thiếu thông tin người dùng.");
            return;
        }

        User newUser = new User(fullname, username, phone, email);

        Thread dbThread = new Thread(() -> {
            try {
                Library myLib = Library.getInstance();
                myLib.addUser(newUser);
                // Reload the user data
                ObservableList<User> updatedUsers = getUserData();
                userTable.setItems(updatedUsers);

                // Clear the input fields
                fullnameField.clear();
                usernameField.clear();
                phoneField.clear();
                emailField.clear();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        dbThread.start();
    }

    private ObservableList<User> getUserData() {
        ObservableList<User> users = FXCollections.observableArrayList();
        String query = "SELECT * FROM users;";

        Thread dbThread = new Thread(() -> {
            try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
                while (rs.next()) {
                    users.add(new User(
                            rs.getInt("user_id"),
                            rs.getString("fullname"),
                            rs.getString("username"),
                            rs.getString("phone"),
                            rs.getString("email")
                    ));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
        dbThread.start();

        return users;
    }

    private void searchUser() {
        String searchText = searchTextField.getText().trim();
        if (!searchText.isEmpty()) {
            String query = "SELECT user_id, fullname, username, phone, email FROM users WHERE user_id LIKE '%" + searchText + "%' OR fullname LIKE '%" + searchText + "%';";

            Thread dbThread = new Thread(() -> {
                ObservableList<User> users = FXCollections.observableArrayList();
                try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
                    while (rs.next()) {
                        users.add(new User(
                                rs.getInt("user_id"),
                                rs.getString("fullname"),
                                rs.getString("username"),
                                rs.getString("phone"),
                                rs.getString("email")
                        ));
                    }
                    userTable.setItems(users);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            });
            dbThread.start();
        }
    }

    private void showUserDetails(User user) {
        if (user != null) {
            System.out.println("User selected: " + user.getEmail());
            emailLabel.setText("Email: " + user.getEmail());
            phoneLabel.setText("SĐT: " + user.getPhone());
        } else {
            emailLabel.setText("");
            phoneLabel.setText("");
            totalBooksBorrowedLabel.setText("");
            totalBooksCurrentlyBorrowedLabel.setText("");
        }
    }
}