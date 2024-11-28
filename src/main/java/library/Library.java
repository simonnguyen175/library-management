package library;

import controller.Controller;

import java.lang.classfile.instruction.LabelTarget;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;


public class Library {
    // singleton pattern
    static DateTimeFormatter formatter;
    private static Library INSTANCE;

    private Library() {
        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    }

    public static Library getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new Library();
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
    // delete user from database
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
    // them comment
    public void addComment(Comment comment) {
        String sql = "INSERT INTO Comments (user_id, book_id, content, comment_date) VALUES (?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = Controller.connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, comment.getUserId());
            preparedStatement.setInt(2, comment.getBookId());
            preparedStatement.setString(3, comment.getContent());
            preparedStatement.setDate(4, comment.getSQLDate());


            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Comment added successfully.");
            }
        } catch (SQLException e) {
            System.err.println("Error while adding comment: " + e.getMessage());
        }
    }
    // them truy van muon sach
    // luu y: dueDate dau vao phai co dinh dang "YYYY-MM-DD" VD: "2005-01-07"
    public boolean BorrowBook(int user_id, int book_id, int borrowed_copies, String dueDate)
    {
        try {
            Controller.connection.setAutoCommit(false);
            // kiem tra xem book_id co ton tai ko, kiem tra xem so luong sach co du ko
            String checkCopiesSQL = "SELECT copies FROM Books WHERE id = ?";
            PreparedStatement checkCopiesStmt = Controller.connection.prepareStatement(checkCopiesSQL);
            checkCopiesStmt.setInt(1, book_id);
            ResultSet resultSet = checkCopiesStmt.executeQuery();
            if (!resultSet.next()) {
                System.err.println("Book with ID " + book_id + " not found.");
                return false;
            }
            int availableCopies = resultSet.getInt("copies");
            if (borrowed_copies > availableCopies) {
                System.err.println("Not enough copies available. Available: " + availableCopies +
                    ", Requested: " + borrowed_copies);
                return false;
            }
            // Giam so luong copies trong table books
            String updateCopiesSQL = "UPDATE Books SET copies = copies - ? WHERE id = ?";
            PreparedStatement updateCopiesStmt = Controller.connection.prepareStatement(updateCopiesSQL);
            updateCopiesStmt.setInt(1, borrowed_copies);
            updateCopiesStmt.setInt(2, book_id);
            updateCopiesStmt.executeUpdate();
            // Them truy van borrowed vao table borrowed
            String insertBorrowSQL = "INSERT INTO Borrowed (user_id, book_id, borrowed_copies, borrow_date, due_date, status) "
                + "VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement insertBorrowStmt = Controller.connection.prepareStatement(insertBorrowSQL);
            insertBorrowStmt.setInt(1, user_id);
            insertBorrowStmt.setInt(2, book_id);
            insertBorrowStmt.setInt(3, borrowed_copies);
            insertBorrowStmt.setString(4, LocalDate.now().format(formatter));
            insertBorrowStmt.setString(5, dueDate);
            insertBorrowStmt.setString(6, "borrowed");
            insertBorrowStmt.executeUpdate();
            Controller.connection.commit();
            System.out.println("Book borrowed successfully.");
            return true;
        } catch (SQLException e) {
            // neu loi phai rollback lai
            try {
                Controller.connection.rollback(); // Rollback nếu có lỗi
            } catch (SQLException rollbackEx) {
                System.err.println("Rollback error: " + rollbackEx.getMessage());
            }
            System.err.println("Error while adding comment: " + e.getMessage());
            return false;
        }
    }
    // Truy van tra sach
    public boolean ReturnBook(int borrowedId) {

        try {
            Controller.connection.setAutoCommit(false);

            // Lay thong tin cua phieu muon
            String getBorrowedSQL = "SELECT book_id, borrowed_copies FROM Borrowed WHERE borrow_id = ? AND status = 'borrowed'";
            PreparedStatement getBorrowedStmt = Controller.connection.prepareStatement(getBorrowedSQL);
            getBorrowedStmt.setInt(1, borrowedId);
            ResultSet resultSet = getBorrowedStmt.executeQuery();

            if (!resultSet.next()) {
                System.err.println("Borrowed record with ID " + borrowedId + " not found or already returned.");
                return false;
            }

            int bookId = resultSet.getInt("book_id");
            int borrowedCopies = resultSet.getInt("borrowed_copies");

            // Cap nhat so luon sach
            String updateBookCopiesSQL = "UPDATE Books SET copies = copies + ? WHERE id = ?";
            PreparedStatement updateBookCopiesStmt = Controller.connection.prepareStatement(updateBookCopiesSQL);
            updateBookCopiesStmt.setInt(1, borrowedCopies);
            updateBookCopiesStmt.setInt(2, bookId);
            updateBookCopiesStmt.executeUpdate();

            // Cap nhat thanh 'returned'
            String updateBorrowedStatusSQL = "UPDATE Borrowed SET status = 'returned' WHERE borrow_id = ?";
            PreparedStatement updateBorrowedStatusStmt = Controller.connection.prepareStatement(updateBorrowedStatusSQL);
            updateBorrowedStatusStmt.setInt(1, borrowedId);
            updateBorrowedStatusStmt.executeUpdate();

            Controller.connection.commit();
            System.out.println("Book returned successfully.");
            return true;

        } catch (SQLException e) {
            System.err.println("Database error: " + e.getMessage());
            try {
                Controller.connection.rollback(); // Rollback nếu có lỗi
            } catch (SQLException rollbackEx) {
                System.err.println("Rollback error: " + rollbackEx.getMessage());
            }
            return false;
        }
    }





}
