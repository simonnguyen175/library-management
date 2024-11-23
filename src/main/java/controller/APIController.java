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

    private boolean check_valid_book(JsonNode volume) {
        return volume.has("title")
                && volume.has("authors")
                && volume.has("industryIdentifiers")
                && volume.get("industryIdentifiers").isArray()
                && !volume.get("industryIdentifiers").isEmpty()
                && (volume.get("industryIdentifiers").findValuesAsText("type").contains("ISBN_13")
                || volume.get("industryIdentifiers").findValuesAsText("type").contains("ISBN_10"));
    }


    private Book getBookInfoFromJson(String json) {
        try {
            Book temp = new Book();
            JsonNode node = new ObjectMapper().readTree(json);

            if (node.has("items")) {
                JsonNode items = node.get("items");
                if (items.size() > 2) {
                    temp.setTitle(items.get(1).get("volumeInfo").get("title").asText().trim());
                }
                else {
                    temp.setTitle(items.get(0).get("volumeInfo").get("title").asText().trim());
                }
                // Vòng lặp đầu tiên: Kiểm tra xem có kết quả nào đầy đủ thông tin không
                for (int i = 0; i < items.size(); i++) {
                    JsonNode volumeInfo = items.get(i).get("volumeInfo");

                    if (volumeInfo != null && check_valid_book(volumeInfo)) {
                        if (volumeInfo.has("title")
                                && volumeInfo.has("authors")
                                && volumeInfo.has("categories")
                                && volumeInfo.has("imageLinks")
                                && volumeInfo.has("industryIdentifiers")
                                && volumeInfo.has("pageCount")
                                && volumeInfo.has("language")
                                && volumeInfo.has("publishedDate")) {

                            // Gán giá trị cho temp nếu đầy đủ thông tin
                            temp.setTitle(volumeInfo.get("title").asText().trim());
                            temp.setAuthor(volumeInfo.get("authors").get(0).asText().trim());
                            temp.setGenre(volumeInfo.get("categories").get(0).asText().trim());
                            temp.setImageUrl(volumeInfo.get("imageLinks").get("thumbnail").asText().trim());
                            temp.setIsbn(volumeInfo.get("industryIdentifiers").get(0).get("identifier").asText().trim());
                            temp.setPages(volumeInfo.get("pageCount").asInt());
                            temp.setLanguage(volumeInfo.get("language").asText().trim());

                            String publishedDate = volumeInfo.get("publishedDate").asText().trim();
                            int year = publishedDate.length() >= 4 ? Integer.parseInt(publishedDate.substring(0, 4)) : 0;
                            temp.setYearPublished(year);

                            // Đã tìm thấy mục đầy đủ, thoát vòng lặp
                            break;
                        }
                    }
                }

                // Vòng lặp thứ hai: Bổ sung các thông tin còn thiếu
                for (int i = 0; i < items.size(); i++) {
                    JsonNode volumeInfo = items.get(i).get("volumeInfo");
                    if (temp.getGenre() == null && volumeInfo.has("categories")) {
                        temp.setGenre(volumeInfo.get("categories").get(0).asText().trim());
                    }
                    if (temp.getImageUrl() == null && volumeInfo.has("imageLinks")) {
                        temp.setImageUrl(volumeInfo.get("imageLinks").get("thumbnail").asText().trim());
                    }
                    if (volumeInfo != null && check_valid_book(volumeInfo)) {
                        if (temp.getTitle() == null && volumeInfo.has("title")) {
                            temp.setTitle(volumeInfo.get("title").asText().trim());
                        }
                        if (temp.getAuthor() == null && volumeInfo.has("authors")) {
                            temp.setAuthor(volumeInfo.get("authors").get(0).asText().trim());
                        }

                        if (temp.getIsbn() == null && volumeInfo.has("industryIdentifiers")) {
                            temp.setIsbn(volumeInfo.get("industryIdentifiers").get(0).get("identifier").asText().trim());
                        }
                        if (temp.getPages() == 0 && volumeInfo.has("pageCount")) {
                            temp.setPages(volumeInfo.get("pageCount").asInt());
                        }
                        if (temp.getLanguage() == null && volumeInfo.has("language")) {
                            temp.setLanguage(volumeInfo.get("language").asText().trim());
                        }
                        if (temp.getYearPublished() == 0 && volumeInfo.has("publishedDate")) {
                            String publishedDate = volumeInfo.get("publishedDate").asText().trim();
                            int year = publishedDate.length() >= 4 ? Integer.parseInt(publishedDate.substring(0, 4)) : 0;
                            temp.setYearPublished(year);
                        }

                        // Nếu đã đủ thông tin, thoát vòng lặp
                        if (temp.getTitle() != null && temp.getAuthor() != null
                                && temp.getGenre() != null && temp.getImageUrl() != null) {
                            break;
                        }
                    }
                }
            }
            return temp;
        } catch (Exception e) {
            System.err.println("Error parsing JSON: " + e.getMessage());
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


    /**
     * Lấy thông tin sách từ API
     * @param inp Tên sách hoặc mã ISBN
     * @return Đối tượng Book chứa thông tin sách
     */
    public synchronized Book getBookInfoFromAPI(String inp) {
        try {
            if (inp.length() >= 10 && inp.matches("[0-9]+")) {
                return getBookFromISBN(inp);
            }
            String encode_title = URLEncoder.encode(inp.trim(), StandardCharsets.UTF_8);
            return getBookInfoFromJson(getHttpResponse(api + "intitle:"+ encode_title + "&key=" + API_KEY));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    // trả về mô tả của sách
    public synchronized String getBookDescriptionFromAPI(String title) {
        try {
            String encode_title = URLEncoder.encode(title.trim(), StandardCharsets.UTF_8);
            return getBookDescriptionFromJson(getHttpResponse(api + encode_title + "&key=" + API_KEY), title);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private String getBookTitleFromJson (String json){
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
    private synchronized Book getBookFromISBN(String isbn) {
        try {
            Book result = new Book();
            String url = api + "isbn:" + URLEncoder.encode(isbn, StandardCharsets.UTF_8) + "&key=" + API_KEY;
            String jsonResponse = getHttpResponse(url);

            // Kiểm tra phản hồi HTTP
            if (jsonResponse == null) {
                System.out.println("No response from API");
                return null;
            }

            try {
                JsonNode rootNode = new ObjectMapper().readTree(jsonResponse);
                JsonNode items = rootNode.get("items");
                if (items != null && !items.isEmpty()) {
                    JsonNode volumeInfo = items.get(0).get("volumeInfo");
                    result.setTitle(volumeInfo.get("title").asText().trim());
                    result.setIsbn(isbn);
                    result.setLanguage(volumeInfo.get("language").asText().trim());
                    result.setYearPublished(Integer.parseInt(volumeInfo.get("publishedDate").asText().substring(0, 4)));
                    result.setPages(volumeInfo.get("pageCount").asInt());
                    result.setAuthor(volumeInfo.get("authors").get(0).asText().trim());
                    if (volumeInfo.has("categories")) {
                        result.setGenre(volumeInfo.get("categories").get(0).asText().trim());
                    }
                    if (volumeInfo.has("imageLinks")) {
                        result.setImageUrl(volumeInfo.get("imageLinks").get("thumbnail").asText().trim());
                    }
                }
            } catch (Exception e) {
                System.err.println("Error parsing main API response: " + e.getMessage());
                e.printStackTrace();
            }

            // Nếu thiếu thông tin bổ sung (genre hoặc imageUrl)
            if (result.getGenre() == null || result.getImageUrl() == null) {
                String json = getHttpResponse(api + "intitle:" + URLEncoder.encode(result.getTitle(), StandardCharsets.UTF_8) + "&key=" + API_KEY);
                JsonNode items = new ObjectMapper().readTree(json).get("items");
                if (items != null && !items.isEmpty()) {
                    for (int i = 0; i < items.size(); i++) {
                        JsonNode volumeInfo = items.get(i).get("volumeInfo");
                        if (volumeInfo != null && volumeInfo.get("title").asText().trim().equalsIgnoreCase(result.getTitle())) {
                            if (volumeInfo.has("categories") && result.getGenre() == null) {
                                result.setGenre(volumeInfo.get("categories").get(0).asText().trim());
                            }
                            if (volumeInfo.has("imageLinks") && result.getImageUrl() == null) {
                                result.setImageUrl(volumeInfo.get("imageLinks").get("thumbnail").asText().trim());
                            }
                            if (result.getGenre() != null && result.getImageUrl() != null) {
                                break;
                            }
                        }
                    }
                } else {
                    System.out.println("No additional results from API for title: " + result.getTitle());
                }
            }
            return result;
        } catch (Exception e) {
            System.err.println("Error fetching book by ISBN: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
}
