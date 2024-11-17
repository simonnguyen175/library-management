package controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import library.Book;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public abstract class Controller {
    protected Connection connection;
    protected final String api = "https://www.googleapis.com/books/v1/volumes?q=isbn%3D";
    protected final String API_KEY = "AIzaSyCGjYZoZgZxXgNmU3_uVQxag9ddQN_O2p4";

    public void initialize() {
        connectToDatabase();
    }

    private void connectToDatabase() {
        String url = "jdbc:mysql://localhost:3306/library";
        String dbUser = "root";
        String dbPassword = "";

        try {
            connection = DriverManager.getConnection(url, dbUser, dbPassword);
            System.out.println("Database connection successful");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Database connection failed");
        }
    }

    private Book getBookFromJson(String json) {
        try {
            Book result = new Book();
            JsonNode node = new ObjectMapper().readTree(json);
            if (node.has("items")) {
                JsonNode items = node.get("items");
                result.setBook_id(String.valueOf(items.get(0).get("id")));
                JsonNode volumeInfo = items.get(0).get("volumeInfo");
                result.setTitle(String.valueOf(volumeInfo.get("title")));
                result.setAuthor(String.valueOf(volumeInfo.get("authors").get(0)));
                result.setIsbn(String.valueOf(volumeInfo.get("industryIdentifiers").get(0).get("identifier")));
                result.setDescription(String.valueOf(volumeInfo.get("description")));
                result.setPages(Integer.parseInt(String.valueOf(volumeInfo.get("pageCount"))));
                result.setLanguage(String.valueOf(volumeInfo.get("language")));
                result.setGenre(String.valueOf(volumeInfo.get("categories")));
                result.setImageUrl(String.valueOf(volumeInfo.get("imageLinks").get("thumbnail")));
            }
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private String getHttpResponse(String url) {
        try (HttpClient client = HttpClient.newHttpClient()) {
            System.out.println("Getting " + url);
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .GET()
                    .build();
            HttpResponse<String> response = client.send(request,
                    HttpResponse.BodyHandlers.ofString());
            int status = response.statusCode();
            if (status != 200) {
                throw new RuntimeException("Failed with HTTP error code : " + status);
            }
            return response.body();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Book getBook (String isbn){
        isbn = isbn.trim();
        isbn = isbn.replaceAll(" ", "+");
        return getBookFromJson(getHttpResponse(api + isbn + "&maxResults=10&key=" + API_KEY));
    }
}