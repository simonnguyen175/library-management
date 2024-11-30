package library;

public class User {
    private final int userId;
    private final String fullname;
    private final String username;
    private String phone;
    private String email;

    public User(int userId, String fullname, String username, String phone, String email) {
        this.userId = userId;
        this.fullname = fullname;
        this.username = username;
        this.phone = phone;
        this.email = email;
    }

    public User(String fullname, String username, String phone, String email) {
        this.fullname = fullname;
        this.username = username;
        this.phone = phone;
        this.email = email;
        this.userId = 0;
    }

    public int getUserId() {
        return userId;
    }

    public String getFullname() {
        return fullname;
    }

    public String getUsername() {
        return username;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    public void setPhone(String phone) {
        // Update the phone number
        this.phone = phone;
    }

    public void setEmail(String email) {
        // Update the email address
        this.email = email;
    }
}