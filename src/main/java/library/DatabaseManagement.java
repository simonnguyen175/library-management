package library;

import controller.Controller;
import controller.DashboardController;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DatabaseManagement {
    // singleton pattern

    private static DatabaseManagement INSTANCE;

    private DatabaseManagement() {

    }

    public static DatabaseManagement getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new DatabaseManagement();
        }
        return INSTANCE;
    }

    // Add book to database
    // if book already exits base on isbn, just change the copies of book
    public void addBook(Book book) {
        String checkSql = "SELECT copies FROM Books WHERE isbn = ?";
        String updateSql = "UPDATE Books SET copies = copies + ? WHERE isbn = ?";
        String insertSql = "INSERT INTO Books (title, author, genre, publisher, publication_year, isbn, pages, language, copies, imageUrl) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement checkStatement = Controller.connection.prepareStatement(checkSql)) {
            checkStatement.setString(1, book.getIsbn());
            ResultSet resultSet = checkStatement.executeQuery();

            if (resultSet.next()) {
                // already exists, increase copies
                int currentCopies = resultSet.getInt("copies");
                try (PreparedStatement updateStatement = Controller.connection.prepareStatement(updateSql)) {
                    updateStatement.setInt(1, book.getCopies());
                    updateStatement.setString(2, book.getIsbn());
                    int rowsUpdated = updateStatement.executeUpdate();
                    if (rowsUpdated > 0) {
                        System.out.println("Book already exists. Copies updated successfully.");
                    }
                }
            } else {
                // Sách không tồn tại, thêm mới
                try (PreparedStatement insertStatement = Controller.connection.prepareStatement(insertSql)) {
                    insertStatement.setString(1, book.getTitle());
                    insertStatement.setString(2, book.getAuthor());
                    insertStatement.setString(3, book.getGenre());
                    insertStatement.setString(4, book.getPublisher());
                    insertStatement.setInt(5, book.getPublicationYear());
                    insertStatement.setString(6, book.getIsbn());
                    insertStatement.setInt(7, book.getPages());
                    insertStatement.setString(8, book.getLanguage());
                    insertStatement.setInt(9, book.getCopies());
                    insertStatement.setString(10, book.getImageUrl());

                    int rowsInserted = insertStatement.executeUpdate();
                    if (rowsInserted > 0) {
                        System.out.println("New book added successfully.");
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Error while adding/updating book: " + e.getMessage());
        }
    }


    //delete book from database by book id
    public void deleteBook(int bookId) {
        String sql = "DELETE FROM Books WHERE id = ?";

        try (PreparedStatement preparedStatement = Controller.connection.prepareStatement(sql)) {
            preparedStatement.setString(1, Integer.toString(bookId));

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Book with ID " + bookId + " was deleted successfully.");
            } else {
                System.out.println("No book found with ID " + bookId + ".");
            }
        } catch (SQLException e) {
            System.err.println("Error while deleting book: " + e.getMessage());
        }
    }

    // Add user to database
    public void addUser(User user) {
        String sql = "INSERT INTO Users (fullname, username, password, phone, email) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = Controller.connection.prepareStatement(sql)) {
            preparedStatement.setString(1, user.getFullname());
            preparedStatement.setString(2, user.getUsername());
            preparedStatement.setString(3, "0");
            preparedStatement.setString(4, user.getPhone());
            preparedStatement.setString(5, user.getEmail());

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("User added successfully.");
            }
        } catch (SQLException e) {
            System.err.println("Error while adding user: " + e.getMessage());
        }
    }

    public void deleteUser(int userId) {
        String sql = "DELETE FROM Users WHERE user_id = ?";

        try (PreparedStatement preparedStatement = Controller.connection.prepareStatement(sql)) {
            preparedStatement.setString(1, Integer.toString(userId));

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("User with ID " + userId + " was deleted successfully.");
            } else {
                System.out.println("No user found with ID " + userId + ".");
            }
        } catch (SQLException e) {
            System.err.println("Error while deleting user: " + e.getMessage());
        }
    }


}
