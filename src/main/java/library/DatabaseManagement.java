package library;
import controller.Controller;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DatabaseManagement {
  // Phương thức thêm sách vào database
  public void addBook(Book book) {
    String sql = "INSERT INTO Books (title, author, genre, publisher, publication_year, isbn, pages, language, copies) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

    try (PreparedStatement preparedStatement = Controller.connection.prepareStatement(sql)) {
      preparedStatement.setString(1, book.getTitle());
      preparedStatement.setString(2, book.getAuthor());
      preparedStatement.setString(3, book.getGenre());
      preparedStatement.setString(4, book.getPublisher());
      preparedStatement.setInt(5, book.getPublicationYear());
      preparedStatement.setString(6, book.getIsbn());
      preparedStatement.setInt(7, book.getPages());
      preparedStatement.setString(8, book.getLanguage());
      preparedStatement.setInt(9, book.getCopies());

      int rowsAffected = preparedStatement.executeUpdate();
      if (rowsAffected > 0) {
        System.out.println("Book added successfully.");
      }
    } catch (SQLException e) {
      System.err.println("Error while adding book: " + e.getMessage());
    }
  }

  public void deleteBook(int bookId) {
    String sql = "DELETE FROM Books WHERE book_id = ?";

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



}
