package library;

import controller.Controller;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.Date;

import java.util.List;
import org.junit.jupiter.api.Test;

class LibraryTest {

  @Test
  void addComment(){
    try {
      String url = "jdbc:mysql://localhost:3306/library";
      String dbUser = "root";
      String dbPassword = "0";
      Controller.connection = DriverManager.getConnection(url, dbUser, dbPassword);
      Library lb = Library.getInstance();
      Comment comment = new Comment(0, 240017, 1001, "Tuyet day", new Date());
      lb.addComment(comment);
    } catch (Exception e)
    {
      e.printStackTrace();
    }
  }

  @Test
  void borrowBook() {
    try {
      String url = "jdbc:mysql://localhost:3306/library";
      String dbUser = "root";
      String dbPassword = "0";
      Controller.connection = DriverManager.getConnection(url, dbUser, dbPassword);
      Library lb = Library.getInstance();
      lb.BorrowBook(240008, 1001, 1, "2024-12-01");
    } catch (Exception e)
    {
      e.printStackTrace();
    }
  }


  @Test
  void returnBook() {
    try {
      String url = "jdbc:mysql://localhost:3306/library";
      String dbUser = "root";
      String dbPassword = "0";
      Controller.connection = DriverManager.getConnection(url, dbUser, dbPassword);
      Library lb = Library.getInstance();
      lb.ReturnBook(27);
    } catch (Exception e)
    {
      e.printStackTrace();
    }
  }

  @Test
  void suggestBook() {
    try {
      String url = "jdbc:mysql://localhost:3306/library";
      String dbUser = "root";
      String dbPassword = "0";
      Controller.connection = DriverManager.getConnection(url, dbUser, dbPassword);
      Library lb = Library.getInstance();
      List<Book> list = lb.suggestBook(240006);
      for (Book book : list) {
        System.out.print(book.getBookId() + " ");
        System.out.println(book.getTitle() + " ");
      }
    } catch (Exception e)
    {
      e.printStackTrace();
    }
  }
}