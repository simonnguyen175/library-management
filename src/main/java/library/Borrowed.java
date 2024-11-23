package library;

public class Borrowed {
    private int borrowId;
    private int userId;
    private int bookId;
    private int borrowedCopies;
    private String borrowDate;
    private String dueDate;
    private String status;
    private String userfullname;
    private String booktitle;
    private String userPhone;
    private String userEmail;

    // Constructor
    public Borrowed(int borrowId, int userId, int bookId, int borrowedCopies, String borrowDate, String dueDate, String status, String userfullname, String booktitle, String userPhone, String userEmail) {
        this.borrowId = borrowId;
        this.userId = userId;
        this.bookId = bookId;
        this.borrowedCopies = borrowedCopies;
        this.borrowDate = borrowDate;
        this.dueDate = dueDate;
        this.status = status;
        this.userfullname = userfullname;
        this.booktitle = booktitle;
        this.userPhone = userPhone;
        this.userEmail = userEmail;
    }

    // Getters
    public int getBorrowId() {
        return borrowId;
    }

    public int getUserId() {
        return userId;
    }

    public int getBookId() {
        return bookId;
    }

    public int getBorrowedCopies() {
        return borrowedCopies;
    }

    public String getBorrowDate() {
        return borrowDate;
    }

    public String getDueDate() {
        return dueDate;
    }

    public String getStatus() {
        return status;
    }

    // Setters
    public void setBorrowId(int borrowId) {
        this.borrowId = borrowId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public void setBorrowedCopies(int borrowedCopies) {
        this.borrowedCopies = borrowedCopies;
    }

    public void setBorrowDate(String borrowDate) {
        this.borrowDate = borrowDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUserfullname() {
        return userfullname;
    }

    public String getBooktitle() {
        return booktitle;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public String getUserPhone() {
        return userPhone;
    }
}