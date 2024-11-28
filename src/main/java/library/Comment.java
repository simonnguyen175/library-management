package library;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

import static controller.Controller.connection;

public class Comment {
    private int commentId;
    private int userId;
    private int bookId;
    private String content;
    private String username;
    private Date date;

    // Constructor
    public Comment(int commentId, int userId, int bookId, String content, Date date) {
        this.commentId = commentId;
        this.userId = userId;
        this.bookId = bookId;
        this.content = content;
        this.date = date;

        this.username = null;
        String query = "SELECT username FROM users WHERE user_id = " + userId;
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            if (rs.next()) {
                this.username = rs.getString("username");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String getUsername() {
        return username;
    }

    public String getContent() {
        return content;
    }

    public String getDate() {
        return date.toString();
    }
}
