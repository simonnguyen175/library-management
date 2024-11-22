use library; -- select database

CREATE TABLE Users (
                       user_id INT PRIMARY KEY AUTO_INCREMENT, -- Mã người dùng tự động tăng
                       fullname VARCHAR(100) NOT NULL,         -- Tên đầy đủ
                       username VARCHAR(50) UNIQUE NOT NULL,  -- Tên tài khoản (không trùng lặp)
                       password VARCHAR(50) NOT NULL,         -- Mật khẩu
                       phone VARCHAR(15),                     -- Số điện thoại
                       email VARCHAR(100) UNIQUE             -- Email (không trùng lặp)
) AUTO_INCREMENT = 240001;

INSERT INTO Users (fullname, username, password, phone, email) VALUES
('Nguyễn Văn An', 'nguyenvanan', 'an123', '0912-345-678', 'nguyenvanan@example.com'),
('Trần Thị Bích', 'tranthibich', 'bich123', '0934-567-890', 'tranthibich@example.com'),
('Lê Minh Châu', 'leminhchau', 'chau123', '0967-890-123', 'leminhchau@example.com'),
('Phạm Quốc Đạt', 'phamquocdat', 'dat123', '0981-234-567', 'phamquocdat@example.com'),
('Nguyễn Hoàng Dũng', 'nguyenhoangdung', 'dung123', '0915-678-901', 'nguyenhoangdung@example.com'),
('Võ Thị Hương', 'vothihuong', 'huong123', '0902-345-678', 'vothihuong@example.com'),
('Đỗ Quỳnh Mai', 'doquynhmai', 'mai123', '0977-890-123', 'doquynhmai@example.com'),
('Nguyễn Phương Trinh', 'nguyenphuongtrinh', 'trinh123', '0921-234-567', 'nguyenphuongtrinh@example.com'),
('Phan Văn Sơn', 'phanvanson', 'son123', '0944-567-890', 'phanvanson@example.com'),
('Lý Đức Tài', 'lyductai', 'tai123', '0933-456-789', 'lyductai@example.com'),
('Lê Hồng Nhung', 'lehongnhung', 'nhung123', '0909-678-901', 'lehongnhung@example.com'),
('Trần Văn Khang', 'tranvankhang', 'khang123', '0983-456-789', 'tranvankhang@example.com'),
('Đặng Hồng Hạnh', 'danghonghanh', 'hanh123', '0935-234-567', 'danghonghanh@example.com'),
('Nguyễn Thị Lan', 'nguyenthilan', 'lan123', '0906-345-678', 'nguyenthilan@example.com'),
('Hoàng Phương Thảo', 'hoangphuongthao', 'thao123', '0925-567-890', 'hoangphuongthao@example.com'),
('Lý Minh Tuấn', 'lyminhtuan', 'tuan123', '0948-678-901', 'lyminhtuan@example.com'),
('Phạm Bảo Ngọc', 'phambaongoc', 'ngoc123', '0904-234-567', 'phambaongoc@example.com'),
('Vũ Hoàng Linh', 'vuhoanglinh', 'linh123', '0972-345-678', 'vuhoanglinh@example.com'),
('Bùi Thị Hạnh', 'buithihanh', 'hanh123', '0938-456-789', 'buithihanh@example.com'),
('Đoàn Quốc Hưng', 'doanquochung', 'hung123', '0916-890-123', 'doanquochung@example.com'),
('Phạm Minh Khoa', 'phamminkhoa', 'khoa123', '0923-456-789', 'phamminkhoa@example.com'),
('Nguyễn Đăng Khôi', 'nguyendangkhoi', 'khoi123', '0901-567-890', 'nguyendangkhoi@example.com'),
('Trần Hải Long', 'tranhailong', 'long123', '0975-678-901', 'tranhailong@example.com'),
('Lê Thị Phương', 'lethiphuong', 'phuong123', '0989-234-567', 'lethiphuong@example.com'),
('Đinh Gia Minh', 'dinhgiaminh', 'minh123', '0917-890-123', 'dinhgiaminh@example.com');

select * from users;

CREATE TABLE Books (
                       id INT AUTO_INCREMENT PRIMARY KEY,
                       title VARCHAR(255) NOT NULL,
                       author VARCHAR(255) NOT NULL,
                       genre VARCHAR(100),
                       publisher VARCHAR(255),
                       publication_year INT,
                       isbn VARCHAR(20) UNIQUE,
                       pages INT,
                       language VARCHAR(50),
                       copies INT,
                       imageURL TEXT
) auto_increment = 1001;


INSERT INTO Books (title, author, genre, publisher, publication_year, isbn, pages, language, copies) VALUES
('The Great Gatsby', 'F. Scott Fitzgerald', 'Fiction', 'Scribner', 2004, '9780743273565', 180, 'English', 10),
('Pride and Prejudice', 'Jane Austen', 'Romance', 'Penguin Classics', 2002, '9780141439518', 279, 'English', 7),
('The Catcher in the Rye', 'J.D. Salinger', 'Fiction', 'Little, Brown and Company', 2001, '9780316769488', 277, 'English', 6),

('The Lord of the Rings', 'J.R.R. Tolkien', 'Fantasy', 'Mariner Books', 2012, '9780544003415', 1178, 'English', 5),
('The Hobbit', 'J.R.R. Tolkien', 'Fantasy', 'Mariner Books', 2012, '9780547928227', 300, 'English', 8),
('The Hunger Games', 'Suzanne Collins', 'Dystopian', 'Scholastic Press', 2010, '9780439023528', 374, 'English', 9),
('Divergent', 'Veronica Roth', 'Dystopian', 'Katherine Tegen Books', 2012, '9780062024039', 487, 'English', 7),

('The Fault in Our Stars', 'John Green', 'Romance', 'Dutton Books', 2012, '9780525478812', 313, 'English', 6),
('Twilight', 'Stephenie Meyer', 'Romance', 'Little, Brown and Company', 2006, '9780316015844', 498, 'English', 5),
('A Game of Thrones', 'George R.R. Martin', 'Fantasy', 'Bantam Books', 2011, '9780553593716', 835, 'English', 4),
('The Alchemist', 'Paulo Coelho', 'Philosophy', 'HarperOne', 1993, '9780062315007', 208, 'English', 6),
('The Road', 'Cormac McCarthy', 'Post-apocalyptic', 'Vintage International', 2006, '9780307387899', 287, 'English', 5),

('Becoming', 'Michelle Obama', 'Memoir', 'Crown Publishing', 2018, '9781524763138', 448, 'English', 7),
('Educated', 'Tara Westover', 'Memoir', 'Random House', 2018, '9780399590504', 334, 'English', 6),
('The Power of Habit', 'Charles Duhigg', 'Self-help', 'Random House', 2014, '9780812981605', 371, 'English', 5),
('Atomic Habits', 'James Clear', 'Self-help', 'Penguin Books', 2018, '9780735211292', 320, 'English', 8),
('The Subtle Art of Not Giving a F*ck', 'Mark Manson', 'Self-help', 'Harper', 2016, '9780062457714', 224, 'English', 9),

('Sapiens: A Brief History of Humankind', 'Yuval Noah Harari', 'History', 'Harper', 2018, '9780062316097', 464, 'English', 7),
('Homo Deus: A Brief History of Tomorrow', 'Yuval Noah Harari', 'History', 'Harper', 2018, '9780062464316', 464, 'English', 6),
('21 Lessons for the 21st Century', 'Yuval Noah Harari', 'History', 'Spiegel & Grau', 2018, '9780525512196', 368, 'English', 8),
('Factfulness', 'Hans Rosling', 'Non-fiction', 'Flatiron Books', 2018, '9781250123824', 352, 'English', 5),
('Outliers: The Story of Success', 'Malcolm Gladwell', 'Non-fiction', 'Little, Brown and Company', 2008, '9780316017930', 309, 'English', 6);

select * from books;

CREATE TABLE BorrowList (
                            borrow_id INT AUTO_INCREMENT PRIMARY KEY, -- ID mượn tự động tăng
                            user_id INT NOT NULL,                     -- ID của người dùng
                            book_id INT NOT NULL,                     -- ID của sách
                            borrowed_copies INT NOT NULL,             -- Số bản mượn
                            borrow_date DATE NOT NULL,                -- Ngày mượn
                            due_date DATE NOT NULL,                   -- Hạn trả sách
                            FOREIGN KEY (user_id) REFERENCES Users(user_id), -- Liên kết đến bảng Users
                            FOREIGN KEY (book_id) REFERENCES Books(id)       -- Liên kết đến bảng Books
);


INSERT INTO BorrowList (user_id, book_id, borrowed_copies, borrow_date, due_date)
VALUES
(240001, 1001, 2, '2024-11-01', '2024-11-10'),
(240002, 1003, 1, '2024-11-02', '2024-11-12'),
(240003, 1005, 3, '2024-11-03', '2024-11-15'),
(240004, 1007, 1, '2024-11-04', '2024-11-20'),
(240005, 1009, 2, '2024-11-05', '2024-11-25');