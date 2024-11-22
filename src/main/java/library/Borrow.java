package library;

public class Borrow {
    private int borrowId;
    private int borrowerId;
    private int bookId;
    private String borrowDate;
    private String dueDate;
    private int amount;

    public Borrow(int borrowId, int borrowerId, int bookId, String borrowDate, String dueDate) {
        this.borrowId = borrowId;
        this.borrowerId = borrowerId;
        this.bookId = bookId;
        this.borrowDate = borrowDate;
        this.dueDate = dueDate;
    }

    public int getBorrowId() {
        return borrowId;
    }

    public int getBorrowerId() {
        return borrowerId;
    }

    public int getBookId() {
        return bookId;
    }

    public String getDueDate() {
        return dueDate;
    }

    public String getBorrowDate() {
        return borrowDate;
    }

    public int getAmount() {
        return amount;
    }

    public void setBorrowId(int borrowId) {
        this.borrowId = borrowId;
    }

    public void setBorrowerId(int borrowerId) {
        this.borrowerId = borrowerId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public void setBorrowDate(String borrowDate) {
        this.borrowDate = borrowDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
