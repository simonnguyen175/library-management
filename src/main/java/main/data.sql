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

CREATE TABLE books (
                       book_id INT PRIMARY KEY AUTO_INCREMENT, -- Mã sách tự động tăng, bắt đầu từ 1000001
                       title VARCHAR(255) NOT NULL,            -- Tiêu đề sách
                       author VARCHAR(100) NOT NULL,           -- Tác giả
                       genre VARCHAR(50),                      -- Thể loại
                       publisher VARCHAR(100),                 -- Nhà xuất bản
                       publication_year INT,                  -- Năm xuất bản
                       isbn VARCHAR(20) UNIQUE NOT NULL,       -- Mã ISBN
                       pages INT,                              -- Số trang
                       language VARCHAR(50),                   -- Ngôn ngữ
                       copies INT NOT NULL DEFAULT 1           -- Số bản copies
) AUTO_INCREMENT = 1001;

INSERT INTO Books (title, author, genre, publisher, publication_year, isbn, pages, language, copies) VALUES
-- Các sách tiếng Việt
('Đắc Nhân Tâm', 'Dale Carnegie', 'Phát triển bản thân', 'NXB Tổng Hợp TP.HCM', 2019, '9786045877326', 320, 'Tiếng Việt', 10),
('Tôi Thấy Hoa Vàng Trên Cỏ Xanh', 'Nguyễn Nhật Ánh', 'Văn học Việt Nam', 'NXB Trẻ', 2020, '9786041135796', 378, 'Tiếng Việt', 8),
('Cho Tôi Xin Một Vé Đi Tuổi Thơ', 'Nguyễn Nhật Ánh', 'Văn học Việt Nam', 'NXB Trẻ', 2018, '9786041134218', 400, 'Tiếng Việt', 12),
('Nhà Giả Kim', 'Paulo Coelho', 'Tiểu thuyết', 'NXB Văn Học', 2021, '9786041137448', 228, 'Tiếng Việt', 7),
('Hạt Giống Tâm Hồn', 'Nhiều tác giả', 'Truyện ngắn', 'NXB Tổng Hợp TP.HCM', 2015, '9786045877203', 250, 'Tiếng Việt', 5),

-- Sách khoa học và học thuật
('Sapiens: Lược Sử Loài Người', 'Yuval Noah Harari', 'Lịch sử', 'NXB Tri Thức', 2021, '9786049900877', 500, 'Tiếng Việt', 6),
('21 Bài Học Cho Thế Kỷ 21', 'Yuval Noah Harari', 'Lịch sử', 'NXB Tri Thức', 2022, '9786049900884', 450, 'Tiếng Việt', 4),
('Thế Giới Phẳng', 'Thomas L. Friedman', 'Kinh tế', 'NXB Trẻ', 2020, '9786041137025', 760, 'Tiếng Việt', 3),

-- Sách thiếu nhi
('Harry Potter và Hòn Đá Phù Thủy', 'J.K. Rowling', 'Phiêu lưu', 'NXB Trẻ', 2022, '9786041114675', 336, 'Tiếng Việt', 6),
('Dế Mèn Phiêu Lưu Ký', 'Tô Hoài', 'Văn học thiếu nhi', 'NXB Kim Đồng', 2019, '9786042114677', 180, 'Tiếng Việt', 7),

-- Sách ngoại văn
('Atomic Habits', 'James Clear', 'Phát triển bản thân', 'Penguin Books', 2018, '9780735211292', 320, 'Tiếng Anh', 5),
('The Subtle Art of Not Giving a F*ck', 'Mark Manson', 'Phát triển bản thân', 'Harper', 2019, '9780062457714', 224, 'Tiếng Anh', 4),
('Think and Grow Rich', 'Napoleon Hill', 'Kinh doanh', 'The Ralston Society', 2020, '9781937879501', 320, 'Tiếng Anh', 8),

-- Thêm sách tiếng Việt
('Bố Già', 'Mario Puzo', 'Tiểu thuyết', 'NXB Văn Học', 2020, '9786049590222', 512, 'Tiếng Việt', 7),
('Mắt Biếc', 'Nguyễn Nhật Ánh', 'Văn học Việt Nam', 'NXB Trẻ', 2019, '9786041135772', 300, 'Tiếng Việt', 9),
('Cánh Đồng Bất Tận', 'Nguyễn Ngọc Tư', 'Văn học Việt Nam', 'NXB Trẻ', 2017, '9786041132443', 252, 'Tiếng Việt', 6),
('Người Đua Diều', 'Khaled Hosseini', 'Tiểu thuyết', 'NXB Văn Học', 2020, '9786041139893', 420, 'Tiếng Việt', 5),
('Tắt Đèn', 'Ngô Tất Tố', 'Văn học Việt Nam', 'NXB Trẻ', 2019, '9786041134119', 360, 'Tiếng Việt', 8),

-- Các sách về kỹ năng
('Làm Chủ Tư Duy, Thay Đổi Vận Mệnh', 'Adam Khoo', 'Kỹ năng sống', 'NXB Trẻ', 2018, '9786041137592', 500, 'Tiếng Việt', 4),
('Dám Bị Ghét', 'Ichiro Kishimi & Fumitake Koga', 'Tâm lý', 'NXB Trẻ', 2021, '9786045693221', 300, 'Tiếng Việt', 5),
('Bí Quyết Gây Dựng Cơ Nghiệp Bạc Tỷ', 'Adam Khoo', 'Kinh doanh', 'NXB Trẻ', 2020, '9786041137882', 480, 'Tiếng Việt', 4);



select * from books;