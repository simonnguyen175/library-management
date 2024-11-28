CREATE TABLE Users (
                       user_id INT PRIMARY KEY AUTO_INCREMENT, -- Mã người dùng tự động tăng
                       fullname VARCHAR(100) NOT NULL,         -- Tên đầy đủ
                       username VARCHAR(50) UNIQUE NOT NULL,  -- Tên tài khoản (không trùng lặp)
                       password VARCHAR(50) NOT NULL,         -- Mật khẩu
                       phone VARCHAR(15),                     -- Số điện thoại
                       email VARCHAR(100) UNIQUE             -- Email (không trùng lặp)
) AUTO_INCREMENT = 240001;

ALTER TABLE users
    MODIFY COLUMN user_id INT AUTO_INCREMENT;

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
                                                                                                         ('A Game of Thrones', 'George R.R. Martin', 'Fantasy', 'Bantam Books', 2011, '9780553593716isbnisbn', 835, 'English', 4),
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

CREATE TABLE BorrowedBooks (
                               borrow_id INT AUTO_INCREMENT PRIMARY KEY, -- ID mượn tự động tăng
                               user_id INT NOT NULL,                     -- ID của người dùng
                               book_id INT NOT NULL,                     -- ID của sách
                               borrowed_copies INT NOT NULL,             -- Số bản mượn
                               borrow_date DATE NOT NULL,                -- Ngày mượn
                               return_date DATE,                         -- Ngày trả
                               FOREIGN KEY (user_id) REFERENCES Users(user_id), -- Liên kết đến bảng Users
                               FOREIGN KEY (book_id) REFERENCES Books(id)       -- Liên kết đến bảng Books
);

CREATE TABLE Borrowed (
                          borrow_id INT AUTO_INCREMENT PRIMARY KEY, -- ID mượn tự động tăng
                          user_id INT NOT NULL,                     -- ID của người dùng
                          book_id INT NOT NULL,                     -- ID của sách
                          borrowed_copies INT NOT NULL,             -- Số lượng bản sách mượn
                          borrow_date DATE NOT NULL,                -- Ngày mượn
                          due_date DATE NOT NULL,                   -- Hạn trả
                          status ENUM('borrowed', 'returned') NOT NULL DEFAULT 'borrowed', -- Trạng thái mượn
                          FOREIGN KEY (user_id) REFERENCES Users(user_id), -- Khóa ngoại liên kết bảng Users
                          FOREIGN KEY (book_id) REFERENCES Books(id)      -- Khóa ngoại liên kết bảng Books
);

INSERT INTO Borrowed (user_id, book_id, borrowed_copies, borrow_date, due_date, status)
VALUES
-- Trạng thái borrowed (đảm bảo số lượng sách không vượt quá số bản copy)
(240018, 1009, 1, '2024-11-24', '2024-12-04', 'borrowed'),
(240017, 1008, 2, '2024-11-23', '2024-12-03', 'borrowed'),
(240016, 1001, 2, '2024-11-22', '2024-12-02', 'borrowed'),
(240010, 1002, 1, '2024-11-21', '2024-12-01', 'borrowed'),
(240009, 1001, 3, '2024-11-20', '2024-11-30', 'borrowed'),
(240008, 1008, 1, '2024-11-19', '2024-11-29', 'borrowed'),
(240007, 1007, 2, '2024-11-18', '2024-11-28', 'borrowed'),
(240006, 1006, 1, '2024-11-17', '2024-11-27', 'borrowed'),
(240005, 1005, 3, '2024-11-16', '2024-11-26', 'borrowed'),
(240004, 1004, 1, '2024-11-15', '2024-11-25', 'borrowed'),
(240003, 1003, 2, '2024-11-14', '2024-11-24', 'borrowed'),
(240002, 1002, 1, '2024-11-12', '2024-11-22', 'borrowed'),
(240001, 1001, 2, '2024-11-10', '2024-11-20', 'borrowed'),

-- Trạng thái returned (giảm số lượng đang mượn)
(240023, 1014, 2, '2024-11-10', '2024-11-20', 'returned'),
(240022, 1013, 1, '2024-11-09', '2024-11-19', 'returned'),
(240021, 1012, 1, '2024-11-08', '2024-11-18', 'returned'),
(240020, 1011, 1, '2024-11-07', '2024-11-17', 'returned'),
(240019, 1010, 1, '2024-11-06', '2024-11-16', 'returned'),
(240015, 1007, 2, '2024-11-05', '2024-11-15', 'returned'),
(240014, 1006, 1, '2024-11-04', '2024-11-14', 'returned'),
(240013, 1005, 2, '2024-11-03', '2024-11-13', 'returned'),
(240012, 1004, 1, '2024-11-02', '2024-11-12', 'returned'),
(240011, 1003, 2, '2024-11-01', '2024-11-11', 'returned');


select * from borrowed;

SET SQL_SAFE_UPDATES = 0;

DELETE FROM borrowed;

CREATE TABLE comments (
                          comment_id INT AUTO_INCREMENT PRIMARY KEY,       -- ID bình luận
                          user_id INT NOT NULL,                            -- ID người dùng
                          book_id INT NOT NULL,                            -- ID sách
                          content text NOT NULL,                           -- Nội dung bình luận
                          comment_date DATE NOT NULL, -- Ngày bình luận
                          FOREIGN KEY (user_id) REFERENCES Users(user_id), -- Khóa ngoại liên kết Users
                          FOREIGN KEY (book_id) REFERENCES Books(id)       -- Khóa ngoại liên kết Books
);

ALTER TABLE comments MODIFY content TEXT;

select * from comments;

INSERT INTO comments (user_id, book_id, content, comment_date)
VALUES
-- Bình luận cho trạng thái borrowed
(240018, 1009, 'Một quyển sách rất thú vị, mình sẽ giới thiệu cho bạn bè.', '2024-12-01'),
(240017, 1008, 'Cuốn sách này thật sự truyền cảm hứng cho mình.', '2024-11-30'),
(240016, 1001, 'Mình rất thích nội dung, dễ hiểu và cuốn hút.', '2024-11-29'),
(240010, 1002, 'Một trải nghiệm đọc thú vị, rất bổ ích.', '2024-11-28'),
(240009, 1001, 'Tuyệt vời! Mình đã học được nhiều điều từ sách này.', '2024-11-27'),
(240008, 1008, 'Nội dung rất hấp dẫn và đầy ý nghĩa.', '2024-11-26'),
(240007, 1007, 'Phong cách viết rất logic, giúp mình hiểu rõ vấn đề.', '2024-11-25'),
(240006, 1006, 'Cuốn sách này rất thực tế và hữu ích.', '2024-11-24'),
(240005, 1005, 'Một quyển sách không thể bỏ qua, rất hay.', '2024-11-23'),
(240004, 1004, 'Giải thích rõ ràng và dễ hiểu, rất phù hợp cho người mới.', '2024-11-22'),
(240003, 1003, 'Sách rất tuyệt, nội dung sâu sắc và ý nghĩa.', '2024-11-21'),
(240002, 1002, 'Mình rất thích cách trình bày của sách, rất dễ theo dõi.', '2024-11-19'),
(240001, 1001, 'Cuốn sách này làm mình suy ngẫm rất nhiều. Rất hay!', '2024-11-17'),

-- Bình luận cho trạng thái returned
(240023, 1014, 'Cuốn sách này giúp mình hiểu sâu hơn về lĩnh vực yêu thích.', '2024-11-17'),
(240022, 1013, 'Nội dung có chiều sâu và rất ý nghĩa, mình đánh giá cao.', '2024-11-16'),
(240021, 1012, 'Phong cách viết đầy cảm hứng, phù hợp cho mọi người.', '2024-11-15'),
(240020, 1011, 'Một quyển sách đáng để đọc lại nhiều lần.', '2024-11-14'),
(240019, 1010, 'Sách viết rất chi tiết và dễ hiểu, mình rất thích.', '2024-11-13'),
(240015, 1007, 'Quyển sách này giúp mình giải đáp nhiều thắc mắc.', '2024-11-12'),
(240014, 1006, 'Một cuốn sách hay và đáng đọc.', '2024-11-11'),
(240013, 1005, 'Mình học được rất nhiều kiến thức thực tiễn từ sách này.', '2024-11-10'),
(240012, 1004, 'Cuốn sách rất hữu ích cho công việc và cuộc sống.', '2024-11-09'),
(240011, 1003, 'Nội dung sách đầy ý nghĩa, làm mình suy ngẫm rất nhiều.', '2024-11-08');