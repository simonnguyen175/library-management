package controller;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.application.Platform;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import library.*;
import controller.FixUserController;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
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

    @FXML
    private TitledPane detailsPane;

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

        // Add listener to detect when the TableView is added to the scene
        userTable.sceneProperty().addListener((observable, oldScene, newScene) -> {
            if (newScene != null) {
                newScene.addEventFilter(MouseEvent.MOUSE_PRESSED, event -> {
                    if (!userTable.isHover() && !detailsPane.isHover()) {
                        userTable.getSelectionModel().clearSelection();
                        clearUserDetails();
                    }
                });
            }
        });

        detailsPane.setExpanded(false);
    }

    private void addDeleteButtonToTable() {
        deleteColumn.setCellFactory(param -> new TableCell<>() {
            private final Button deleteButton = new Button();
            private final Button fixButton = new Button();
            private final HBox hBox = new HBox(5, fixButton, deleteButton); // Spacing of 5

            {
                hBox.setAlignment(Pos.CENTER);
            }

            {
                // Setting FontAwesome icons as Text
                Text deleteIcon = new Text("\uf1f8"); // trash
                deleteIcon.setStyle("-fx-font-family: 'FontAwesome'; -fx-font-size: 16px;");
                deleteIcon.setFill(Color.web("white"));

                Text fixIcon = new Text("\uf044");  // edit/fix
                fixIcon.setStyle("-fx-font-family: 'FontAwesome'; -fx-font-size: 16px;");
                fixIcon.setFill(Color.web("white"));

                // Set the buttons' graphic
                deleteButton.setGraphic(deleteIcon);
                fixButton.setGraphic(fixIcon);

                deleteButton.setOnAction(event -> {
                    User user = getTableView().getItems().get(getIndex());
                    deleteUser(user);
                });

                fixButton.setOnAction(event -> {
                    User user = getTableView().getItems().get(getIndex());
                    fixUser(user);
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

    private void fixUser(User user) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/FixUser.fxml"));
            AnchorPane root = loader.load();
            FixUserController controller = loader.getController();
            controller.setUser(user);

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Fix User");
            stage.setScene(new Scene(root));
            stage.showAndWait();

            ObservableList<User> updatedUsers = getUserData();
            userTable.setItems(updatedUsers);
        } catch (IOException e) {
            e.printStackTrace();
        }
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
                if (e instanceof SQLIntegrityConstraintViolationException) {
                    Platform.runLater(() -> {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Deletion Error");
                        alert.setHeaderText("Cannot delete user");
                        alert.setContentText("This user currently borrows book.");
                        alert.showAndWait();
                    });
                } else {
                    e.printStackTrace();
                }
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
                    User user = new User(
                            rs.getInt("user_id"),
                            rs.getString("fullname"),
                            rs.getString("username"),
                            rs.getString("phone"),
                            rs.getString("email")
                    );
                    Platform.runLater(() -> users.add(user));
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

    private void clearUserDetails() {
        emailLabel.setText("Email:");
        phoneLabel.setText("Phone:");
        totalBooksBorrowedLabel.setText("Tổng sách đã mượn:");
        totalBooksCurrentlyBorrowedLabel.setText("Sách đang mượn:");
        detailsPane.setExpanded(false); // Collapse the details pane
    }

    private void showUserDetails(User user) {
        detailsPane.setExpanded(false); // Collapse the details pane first
        if (user != null) {

            System.out.println("User selected: " + user.getEmail());
            emailLabel.setText("Email: " + user.getEmail());
            phoneLabel.setText("SĐT: " + user.getPhone());
            totalBooksBorrowedLabel.setText("Tổng sách đã mượn: " + getTotalBooksBorrowed(user.getUserId()));
            totalBooksCurrentlyBorrowedLabel.setText("Sách đang mượn: " + getTotalBooksCurrentlyBorrowed(user.getUserId()));
        }
    }

    private int getTotalBooksBorrowed(int userId) {
        String query = "SELECT SUM(borrowed_copies) AS total FROM borrowed WHERE user_id = " + userId + ";";
        try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
            if (rs.next()) {
                return rs.getInt("total");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    private int getTotalBooksCurrentlyBorrowed(int userId) {
        String query = "SELECT SUM(borrowed_copies) AS total FROM borrowed WHERE user_id = " + userId + " AND status = 'borrowed';";
        try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
            if (rs.next()) {
                return rs.getInt("total");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
}