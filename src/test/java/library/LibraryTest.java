package library;

import controller.Controller;

import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.Date;

import java.util.List;

import org.junit.jupiter.api.Test;

class LibraryTest {

    @Test
    void addComment() {
        try {
            String url = "jdbc:mysql://localhost:3306/library";
            String dbUser = "root";
            String dbPassword = "123";
            Controller.connection = DriverManager.getConnection(url, dbUser, dbPassword);
            Library lb = Library.getInstance();
            Comment comment = new Comment(0, 240017, 1001, "Tuyet day", new Date());
            lb.addComment(comment);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void borrowBook() {
        try {
            String url = "jdbc:mysql://localhost:3306/library";
            String dbUser = "root";
            String dbPassword = "123";
            Controller.connection = DriverManager.getConnection(url, dbUser, dbPassword);
            Library lb = Library.getInstance();
            lb.BorrowBook(240008, 1002, 1, "2024-12-01");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Test
    void returnBook() {
        try {
            String url = "jdbc:mysql://localhost:3306/library";
            String dbUser = "root";
            String dbPassword = "123";
            Controller.connection = DriverManager.getConnection(url, dbUser, dbPassword);
            Library lb = Library.getInstance();
            lb.ReturnBook(27);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void suggestBook() {
        try {
            String url = "jdbc:mysql://localhost:3306/library";
            String dbUser = "root";
            String dbPassword = "123";
            Controller.connection = DriverManager.getConnection(url, dbUser, dbPassword);
            Library lb = Library.getInstance();
            List<Book> list = lb.suggestBook(240006);
            for (Book book : list) {
                System.out.print(book.getBookId() + " ");
                System.out.println(book.getTitle() + " ");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void getTotalBooks() {
        try {
            String url = "jdbc:mysql://localhost:3306/library";
            String dbUser = "root";
            String dbPassword = "123";
            Controller.connection = DriverManager.getConnection(url, dbUser, dbPassword);
            Library lb = Library.getInstance();
            System.out.print(lb.getTotalBooks());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void getTotalUser() {
        try {
            String url = "jdbc:mysql://localhost:3306/library";
            String dbUser = "root";
            String dbPassword = "123";
            Controller.connection = DriverManager.getConnection(url, dbUser, dbPassword);
            Library lb = Library.getInstance();
            System.out.print(lb.getTotalUser());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void addBook() {
        try {
            String url = "jdbc:mysql://localhost:3306/library";
            String dbUser = "root";
            String dbPassword = "123";
            Controller.connection = DriverManager.getConnection(url, dbUser, dbPassword);
            Library lb = Library.getInstance();
            Book book = new Book();
            lb.addBook(book);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void deleteBook() {
        try {
            String url = "jdbc:mysql://localhost:3306/library";
            String dbUser = "root";
            String dbPassword = "123";
            Controller.connection = DriverManager.getConnection(url, dbUser, dbPassword);
            Library lb = Library.getInstance();
            lb.deleteBook(1028);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void addUser() {
        try {
            String url = "jdbc:mysql://localhost:3306/library";
            String dbUser = "root";
            String dbPassword = "123";
            Controller.connection = DriverManager.getConnection(url, dbUser, dbPassword);
            Library lb = Library.getInstance();
            User user = new User("Nguyễn Đại Phong", "phongdai123", "0983-223-438", "daiphongdp@example.com");
            lb.addUser(user);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void deleteUser() {
        try {
            String url = "jdbc:mysql://localhost:3306/library";
            String dbUser = "root";
            String dbPassword = "123";
            Controller.connection = DriverManager.getConnection(url, dbUser, dbPassword);
            Library lb = Library.getInstance();
            lb.deleteUser(240040);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void getTopBooks() {
        try {
            String url = "jdbc:mysql://localhost:3306/library";
            String dbUser = "root";
            String dbPassword = "123";
            Controller.connection = DriverManager.getConnection(url, dbUser, dbPassword);
            Library lb = Library.getInstance();
            List<Book> list = lb.getTopBooks();
            for (Book book : list) {
                System.out.print(book.getBookId() + " ");
                System.out.println(book.getTitle() + " ");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void getTopUsers() {
        try {
            String url = "jdbc:mysql://localhost:3306/library";
            String dbUser = "root";
            String dbPassword = "123";
            Controller.connection = DriverManager.getConnection(url, dbUser, dbPassword);
            Library lb = Library.getInstance();
            List<User> list = lb.getTopUsers();
            for (User user : list) {
                System.out.print(user.getUserId() + " ");
                System.out.println(user.getFullname() + " ");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}