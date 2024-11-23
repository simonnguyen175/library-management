package controller;

import library.Book;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import library.Book;

public class APIControllerTest {

    @Test
    void testGetBookFromAPI() {
        APIController apiController = new APIController();

        // Thực hiện tìm kiếm cuốn sách với tiêu đề "Chí Phèo"
        String title = "Harry Potter and the Philosopher's Stone"; ;
        String ISBN = "9781781100219";

        Book result = apiController.getBookInfoFromAPI(ISBN);
        String description = apiController.getBookDescriptionFromAPI(result.getTitle());
        // Kiểm tra các thuộc tính của đối tượng Book
//        Assertions.assertNotNull(result, "Kết quả trả về không được null");
//        Assertions.assertNotNull(result.getTitle(), "Tiêu đề sách không được null");
//        Assertions.assertNotNull(result.getAuthor(), "Tên tác giả không được null");

        // In ra thông tin sách (nếu cần để debug)
        System.out.println("Title: " + result.getTitle());
        System.out.println("Author: " + result.getAuthor());
        System.out.println ("ISBN: " + result.getIsbn());
        System.out.println ("Pages: " + result.getPages());
        System.out.println ("Language: " + result.getLanguage());
        System.out.println ("Genre: " + result.getGenre());
        System.out.println ("Image URL: " + result.getImageUrl());
        System.out.println ("Year Published: " + result.getYearPublished());
        System.out.println ("Description: " + description);
    }
}
