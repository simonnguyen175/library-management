package controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import library.Book;

import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

public class APIController {

    protected final String api = "https://www.googleapis.com/books/v1/volumes?q=";
    protected final String API_KEY = "AIzaSyCGjYZoZgZxXgNmU3_uVQxag9ddQN_O2p4";

    private boolean check_valid_book(JsonNode volume , String raw_title) {
        return volume.has("title")
                && volume.get("title").asText().trim().equalsIgnoreCase(raw_title)
                && volume.has("authors")
                && volume.has("industryIdentifiers")
                && volume.get("industryIdentifiers").isArray()
                && !volume.get("industryIdentifiers").isEmpty()
                && (volume.get("industryIdentifiers").findValuesAsText("type").contains("ISBN_13")
                || volume.get("industryIdentifiers").findValuesAsText("type").contains("ISBN_10"));
    }


    private Book getBookInfoFromJson(String json , String raw_title) {
        try {
            Book temp = new Book();
            JsonNode node = new ObjectMapper().readTree(json);
            if (node.has("items")) {
                JsonNode items = node.get("items");
                for (int i = 0; i < items.size(); i++) {
                    JsonNode volumeInfo = items.get(i).get("volumeInfo");
                    if (volumeInfo != null) {
                        if (volumeInfo.has("categories") && temp.getGenre() == null) {
                            temp.setGenre(volumeInfo.get("categories").get(0).asText().trim());
                        }
                        if (volumeInfo.has("imageLinks") && temp.getImageUrl() == null) {
                            temp.setImageUrl(volumeInfo.get("imageLinks").get("thumbnail").asText().trim());
                        }
                        if (!check_valid_book(volumeInfo,raw_title)) continue;
                        temp.setTitle(volumeInfo.get("title").asText().trim());
                        temp.setAuthor(volumeInfo.get("authors").get(0).asText().trim());
                        temp.setIsbn(volumeInfo.get("industryIdentifiers").get(0).get("identifier").asText().trim());
                        temp.setPages(volumeInfo.get("pageCount").asInt());
                        temp.setLanguage(volumeInfo.get("language").asText().trim());
                        String publishedDate = volumeInfo.get("publishedDate").asText().trim();
                        int year = publishedDate.length() >= 4 ? Integer.parseInt(publishedDate.substring(0, 4)) : 0;
                        temp.setYearPublished(year);
                        if (temp.getGenre()!= null && temp.getImageUrl() != null) {
                            break;
                        }
                    }
                }
            }
            return temp;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private String getBookDescriptionFromJson(String json , String raw_title) {
        try {
            String result = "No description found";
            JsonNode node = new ObjectMapper().readTree(json);
            if (node.has("items")) {
                JsonNode items = node.get("items");
                for (int i = 0; i < items.size(); i++) {
                    JsonNode volumeInfo = items.get(i).get("volumeInfo");
                    if (volumeInfo != null && volumeInfo.has("description") && volumeInfo.has("title")) {
                        String temp = volumeInfo.get("description").asText().trim();
                        if (volumeInfo.get("title").asText().trim().equalsIgnoreCase(raw_title) && result.length() < temp.length()) {
                            result = temp;
                        }
                    }
                }
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
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString(StandardCharsets.UTF_8));

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

    //trả về thông tin quyển sách bao gồm tên sách, tác giả, mã ISBN, số trang, ngôn ngữ, thể loại, ảnh bìa
    public synchronized Book getBookInfoFromAPI(String title) {
        try {
            String encode_title = URLEncoder.encode(title.trim(), StandardCharsets.UTF_8);
            return getBookInfoFromJson(getHttpResponse(api +"intitle:"+ encode_title + "&key=" + API_KEY), title);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    // trả về mô tả của sách
    public synchronized String getBookDescriptionFromAPI(String title) {
        try {
            String encode_title = URLEncoder.encode(title.trim(), StandardCharsets.UTF_8);
            return getBookDescriptionFromJson(getHttpResponse(api +"intitle:"+ encode_title + "&key=" + API_KEY), title);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private String getBookTitleFromISBN (String json){
        try {
            JsonNode node = new ObjectMapper().readTree(json);
            JsonNode items = node.get("items");
            JsonNode volumeInfo = items.get(0).get("volumeInfo");
            return volumeInfo.get("title").asText().trim();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // trả về thông tin sách từ mã ISBN
    public synchronized Book getBookFromISBN(String isbn) {
        try {
            String url = api + "isbn:" + URLEncoder.encode(isbn, StandardCharsets.UTF_8) + "&key=" + API_KEY;
            String jsonResponse = getHttpResponse(url);

            // Kiểm tra phản hồi HTTP
            if (jsonResponse == null) {
                System.out.println("No response from API");
                return null;
            }


            String title = getBookTitleFromISBN(jsonResponse);

            if (title == null) {
                System.out.println("No book found with the given ISBN");
                return null;
            }else {
                System.out.println ("Title: " + title);
            }

            return getBookInfoFromAPI(title);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
