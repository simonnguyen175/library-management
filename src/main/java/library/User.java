package library;

public class User {
    private final int userId;
    private final String fullname;
    private final String username;
    private final String phone;
    private final String email;

    public User(int userId, String fullname, String username) {
        this.userId = userId;
        this.fullname = fullname;
        this.username = username;
        this.phone = "";
        this.email = "";
    }

    public User(int userId, String fullname, String username, String phone, String email) {
        this.userId = userId;
        this.fullname = fullname;
        this.username = username;
        this.phone = phone;
        this.email = email;
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
}
