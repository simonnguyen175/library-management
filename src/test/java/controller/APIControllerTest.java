package controller;

import library.Book;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class APIControllerTest {

    @Test
    void testGetBookFromAPI() {
        APIController apiController = APIController.getInstance();

        // Thực hiện tìm kiếm cuốn sách với tiêu đề "Chí Phèo"
        String title = "Harry Potter and the Sorcerer's Stone" ;
        String ISBN = "6044913104";

        Book result = apiController.getBookInfoFromAPI(title);
        String description = apiController.getBookDescriptionFromAPI(title);
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
        System.out.println ("Year Published: " + result.getPublicationYear());
        System.out.println ("Description: " + description);
    }
}
