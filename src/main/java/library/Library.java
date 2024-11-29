package library;

import controller.Controller;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class Library {
    // singleton pattern
    static DateTimeFormatter formatter;
    private static Library INSTANCE;

    public static String role = "admin";
    public static int userId = 1;

    private Library() {
        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    }

    public static Library getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new Library();
        }
        return INSTANCE;
    }

    public static class Admin {
        private String name;
        private String pass;

        public Admin(String name, String pass) {
            this.name = name;
            this.pass = pass;
        }

        public String getName() {
            return name;
        }

        public String getPass() {
            return pass;
        }
    }

    public static List<Admin> admins = Arrays.asList(
            new Admin("", ""),
            new Admin("admin", "admin"),
            new Admin("admin1", "admin1")
    );


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
            String sql = "SELECT SUM(borrowed_copies) FROM Borrowed WHERE book_id = ? AND status = 'borrowed'";
            PreparedStatement preparedStatement = Controller.connection.prepareStatement(sql);
            preparedStatement.setInt(1, book_id);
            ResultSet rS = preparedStatement.executeQuery();
            if (!rS.next()) {
                System.err.println("DB Error");
                return false;
            }
            availableCopies -= rS.getInt(1);
            if (borrowed_copies > availableCopies) {
                System.err.println("Not enough copies available. Available: " + availableCopies +
                    ", Requested: " + borrowed_copies);
                return false;
            }
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
            System.err.println("Error while adding borrowed: " + e.getMessage());
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

    public List<Book> suggestBook(int userId) {
        List<Book> suggestedBooks = new ArrayList<>();

        // SQL queries
        String queryGenresAuthors = """
            SELECT DISTINCT b.genre, b.author
            FROM Borrowed br
            JOIN Books b ON br.book_id = b.id
            WHERE br.user_id = ?;
        """;

        String querySuggestionsTemplate = """
            SELECT b.*
            FROM Books b
            WHERE (%s OR %s)
            AND b.id NOT IN (
                SELECT book_id FROM Borrowed WHERE user_id = ?
            )
            LIMIT 5;
        """;

        String queryMostBorrowed = """
            SELECT b.*
            FROM Books b
            JOIN (
                SELECT book_id
                FROM Borrowed
                GROUP BY book_id
                ORDER BY COUNT(*) DESC
            ) as most_borrowed ON b.id = most_borrowed.book_id;
        """;

        try  {
            // Step 1: Get genres and authors of books the user has borrowed
            List<String> genres = new ArrayList<>();
            List<String> authors = new ArrayList<>();

            try (PreparedStatement ps = Controller.connection.prepareStatement(queryGenresAuthors)) {
                ps.setInt(1, userId);

                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        genres.add(rs.getString("genre"));
                        authors.add(rs.getString("author"));
                    }
                }
            }

            // Step 2: Suggest books based on genres and authors
            if (!genres.isEmpty() || !authors.isEmpty()) {
                String genreCondition = genres.isEmpty() ? "1=0" :
                    "b.genre IN ('" + String.join("','", genres) + "')";
                String authorCondition = authors.isEmpty() ? "1=0" :
                    "b.author IN ('" + String.join("','", authors) + "')";

                String querySuggestions = String.format(querySuggestionsTemplate, genreCondition, authorCondition);

                try (PreparedStatement ps = Controller.connection.prepareStatement(querySuggestions)) {
                    ps.setInt(1, userId);

                    try (ResultSet rs = ps.executeQuery()) {
                        while (rs.next()) {
                            suggestedBooks.add(buildBookFromResultSet(rs));
                        }
                    }
                }
            }

            // Step 3: If list size < 5, fill with most borrowed books
            if (suggestedBooks.size() < 5) {
                try (PreparedStatement ps = Controller.connection.prepareStatement(queryMostBorrowed);
                    ResultSet rs = ps.executeQuery()) {

                    while (rs.next() && suggestedBooks.size() < 5) {
                        Book book = buildBookFromResultSet(rs);

                        // Avoid adding duplicates
                        if (!containsBook(suggestedBooks, book.getBookId())) {
                            suggestedBooks.add(book);
                        }
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return suggestedBooks;
    }

    // Helper method to build a Book object from ResultSet
    private static Book buildBookFromResultSet(ResultSet rs) throws SQLException {
        Book book = new Book();
        book.setBookId(rs.getInt("id"));
        book.setTitle(rs.getString("title"));
        book.setAuthor(rs.getString("author"));
        book.setGenre(rs.getString("genre"));
        book.setPublisher(rs.getString("publisher"));
        book.setPublicationYear(rs.getInt("publication_year"));
        book.setIsbn(rs.getString("isbn"));
        book.setPages(rs.getInt("pages"));
        book.setLanguage(rs.getString("language"));
        book.setCopies(rs.getInt("copies"));
        book.setImageUrl(rs.getString("imageURL"));
        return book;
    }

    // Helper method to check if a list already contains a book by ID
    private static boolean containsBook(List<Book> books, int bookId) {
        return books.stream().anyMatch(book -> book.getBookId() == bookId);
    }

    public int getTotalBooks() {

        // Câu SQL để tính tổng cột copies
        String query = "SELECT SUM(copies) AS total_copies FROM Books";

        // Kết quả trả về
        int totalCopies = 0;

        try (PreparedStatement statement = Controller.connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery()) {

            // Xử lý kết quả truy vấn
            if (resultSet.next()) {
                totalCopies = resultSet.getInt("total_copies");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return totalCopies;
    }

    public int getTotalUser() {
        // Câu SQL để đếm số sách
        String query = "SELECT COUNT(*) AS total FROM Users";

        // Kết quả trả về
        int totalBooks = 0;

        try (PreparedStatement statement = Controller.connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery()) {

            // Xử lý kết quả truy vấn
            if (resultSet.next()) {
                totalBooks = resultSet.getInt("total");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return totalBooks;
    }


}
