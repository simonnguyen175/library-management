-- MySQL dump 10.13  Distrib 8.0.39, for Win64 (x86_64)
--
-- Host: localhost    Database: library
-- ------------------------------------------------------
-- Server version	8.0.39

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `books`
--

DROP TABLE IF EXISTS `books`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `books` (
                         `id` int NOT NULL AUTO_INCREMENT,
                         `title` varchar(255) NOT NULL,
                         `author` varchar(255) NOT NULL,
                         `genre` varchar(100) DEFAULT NULL,
                         `publisher` varchar(255) DEFAULT NULL,
                         `publication_year` int DEFAULT NULL,
                         `isbn` varchar(20) DEFAULT NULL,
                         `pages` int DEFAULT NULL,
                         `language` varchar(50) DEFAULT NULL,
                         `copies` int DEFAULT NULL,
                         `imageURL` text,
                         PRIMARY KEY (`id`),
                         UNIQUE KEY `isbn` (`isbn`)
) ENGINE=InnoDB AUTO_INCREMENT=1030 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `books`
--

LOCK TABLES `books` WRITE;
/*!40000 ALTER TABLE `books` DISABLE KEYS */;
INSERT INTO `books` VALUES (1001,'The Great Gatsby','F. Scott Fitzgerald','Fiction','Scribner',2004,'9780743273565',180,'English',10,'http://books.google.com/books/content?id=fIlQDwAAQBAJ&printsec=frontcover&img=1&zoom=1&edge=curl&source=gbs_api'),(1002,'Pride and Prejudice','Jane Austen','Romance','Penguin Classics',2002,'9780141439518',279,'English',7,'http://books.google.com/books/content?id=uY6MEAAAQBAJ&printsec=frontcover&img=1&zoom=1&source=gbs_api'),(1003,'The Catcher in the Rye','J.D. Salinger','Fiction','Little, Brown and Company',2001,'9780316769488',277,'English',5,'http://books.google.com/books/content?id=ZotvleqZomIC&printsec=frontcover&img=1&zoom=1&source=gbs_api'),(1004,'The Lord of the Rings','J.R.R. Tolkien','Fantasy','Mariner Books',2012,'9780544003415',1178,'English',5,'http://books.google.com/books/content?id=AVVoPwAACAAJ&printsec=frontcover&img=1&zoom=1&source=gbs_api'),(1005,'The Hobbit','J.R.R. Tolkien','Fantasy','Mariner Books',2012,'9780547928227',300,'English',8,'http://books.google.com/books/content?id=LLSpngEACAAJ&printsec=frontcover&img=1&zoom=1&source=gbs_api'),(1006,'The Hunger Games','Suzanne Collins','Dystopian','Scholastic Press',2010,'9780439023528',374,'English',9,'http://books.google.com/books/content?id=hlb_sM1AN0gC&printsec=frontcover&img=1&zoom=1&edge=curl&source=gbs_api'),(1007,'Divergent','Veronica Roth','Dystopian','Katherine Tegen Books',2012,'9780062024039',487,'English',7,'http://books.google.com/books/content?id=-TUinwEACAAJ&printsec=frontcover&img=1&zoom=1&source=gbs_api'),(1008,'The Fault in Our Stars','John Green','Romance','Dutton Books',2012,'9780525478812',313,'English',6,'http://books.google.com/books/content?id=Dc2LDQAAQBAJ&printsec=frontcover&img=1&zoom=1&source=gbs_api'),(1009,'Twilight','Stephenie Meyer','Romance','Little, Brown and Company',2006,'9780316007443',498,'English',5,'http://books.google.com/books/content?id=PY40AQAAQBAJ&printsec=frontcover&img=1&zoom=1&edge=curl&source=gbs_api'),(1010,'A Game of Thrones','George R.R. Martin','Fantasy','Bantam Books',2011,'9780553593716',835,'English',4,'http://books.google.com/books/content?id=YDzTCwAAQBAJ&printsec=frontcover&img=1&zoom=1&source=gbs_api'),(1011,'The Alchemist','Paulo Coelho','Philosophy','HarperOne',1993,'9780062315007',208,'English',6,'http://books.google.com/books/content?id=ZI3gAQAACAAJ&printsec=frontcover&img=1&zoom=1&source=gbs_api'),(1012,'The Road','Cormac McCarthy','Post-apocalyptic','Vintage International',2006,'9780307387899',287,'English',5,'http://books.google.com/books/content?id=53aMEAAAQBAJ&printsec=frontcover&img=1&zoom=1&source=gbs_api'),(1013,'Becoming','Michelle Obama','Memoir','Crown Publishing',2018,'9781524763138',448,'English',7,'http://books.google.com/books/content?id=hi17DwAAQBAJ&printsec=frontcover&img=1&zoom=1&source=gbs_api'),(1014,'Educated','Tara Westover','Memoir','Random House',2018,'9780399590504',334,'English',6,'http://books.google.com/books/content?id=6rdPEAAAQBAJ&printsec=frontcover&img=1&zoom=1&source=gbs_api'),(1015,'The Power of Habit','Charles Duhigg','Self-help','Random House',2014,'9780812981605',371,'English',5,'http://books.google.com/books/content?id=rrlPEAAAQBAJ&printsec=frontcover&img=1&zoom=1&source=gbs_api'),(1016,'Atomic Habits','James Clear','Self-help','Penguin Books',2018,'9780735211292',320,'English',8,'http://books.google.com/books/content?id=XfFvDwAAQBAJ&printsec=frontcover&img=1&zoom=1&source=gbs_api'),(1017,'The Subtle Art of Not Giving a F*ck','Mark Manson','Self-help','Harper',2016,'9780062457714',224,'English',9,'http://books.google.com/books/content?id=RobZjgEACAAJ&printsec=frontcover&img=1&zoom=1&source=gbs_api'),(1018,'Sapiens: A Brief History of Humankind','Yuval Noah Harari','History','Harper',2018,'9780062316097',464,'English',7,'http://books.google.com/books/content?id=ibALnwEACAAJ&printsec=frontcover&img=1&zoom=1&source=gbs_api'),(1019,'Homo Deus: A Brief History of Tomorrow','Yuval Noah Harari','History','Harper',2018,'9780062464316',464,'English',6,'http://books.google.com/books/content?id=hjPgjwEACAAJ&printsec=frontcover&img=1&zoom=1&source=gbs_api'),(1020,'21 Lessons for the 21st Century','Yuval Noah Harari','History','Spiegel & Grau',2018,'9780525512196',368,'English',8,'http://books.google.com/books/content?id=GrhPEAAAQBAJ&printsec=frontcover&img=1&zoom=1&source=gbs_api'),(1021,'Factfulness','Hans Rosling','Non-fiction','Flatiron Books',2018,'9781250123824',352,'English',5,'http://books.google.com/books/content?id=Bop0xAEACAAJ&printsec=frontcover&img=1&zoom=1&source=gbs_api'),(1022,'Outliers: The Story of Success','Malcolm Gladwell','Non-fiction','Little, Brown and Company',2008,'9780141903491',309,'English',6,'http://books.google.com/books/content?id=ialrgIT41OAC&printsec=frontcover&img=1&zoom=1&edge=curl&source=gbs_api'),(1028,'Manchester United','Jim White','Sports & Recreation','Little, Brown UK',2009,'9780751539110',464,'en',3,'http://books.google.com/books/content?id=UvrfPQAACAAJ&printsec=frontcover&img=1&zoom=1&source=gbs_api'),(1029,'Dog Man 13: Big Jim Begins','Dav Pilkey','Juvenile Fiction','Graphix',2024,'9781338896459',224,'en',1,'http://books.google.com/books/content?id=G6230AEACAAJ&printsec=frontcover&img=1&zoom=1&source=gbs_api');
/*!40000 ALTER TABLE `books` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `borrowed`
--

DROP TABLE IF EXISTS `borrowed`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `borrowed` (
                            `borrow_id` int NOT NULL AUTO_INCREMENT,
                            `user_id` int NOT NULL,
                            `book_id` int NOT NULL,
                            `borrowed_copies` int NOT NULL,
                            `borrow_date` date NOT NULL,
                            `due_date` date NOT NULL,
                            `status` enum('borrowed','returned') NOT NULL DEFAULT 'borrowed',
                            PRIMARY KEY (`borrow_id`),
                            KEY `user_id` (`user_id`),
                            KEY `book_id` (`book_id`),
                            CONSTRAINT `borrowed_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`),
                            CONSTRAINT `borrowed_ibfk_2` FOREIGN KEY (`book_id`) REFERENCES `books` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=53 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `borrowed`
--

LOCK TABLES `borrowed` WRITE;
/*!40000 ALTER TABLE `borrowed` DISABLE KEYS */;
INSERT INTO `borrowed` VALUES (24,240018,1009,1,'2024-11-24','2024-12-04','borrowed'),(25,240017,1008,2,'2024-11-23','2024-12-03','borrowed'),(26,240016,1001,2,'2024-11-22','2024-12-02','borrowed'),(27,240010,1002,1,'2024-11-21','2024-12-01','borrowed'),(28,240009,1001,3,'2024-11-20','2024-11-30','borrowed'),(29,240008,1008,1,'2024-11-19','2024-11-29','borrowed'),(30,240007,1007,2,'2024-11-18','2024-11-28','borrowed'),(31,240006,1006,1,'2024-11-17','2024-11-27','borrowed'),(32,240005,1005,3,'2024-11-16','2024-11-26','borrowed'),(33,240004,1004,1,'2024-11-15','2024-11-25','borrowed'),(34,240003,1003,2,'2024-11-14','2024-11-24','borrowed'),(35,240002,1002,1,'2024-11-12','2024-11-22','borrowed'),(36,240001,1001,2,'2024-11-10','2024-11-20','borrowed'),(37,240023,1014,2,'2024-11-10','2024-11-20','returned'),(38,240022,1013,1,'2024-11-09','2024-11-19','returned'),(39,240021,1012,1,'2024-11-08','2024-11-18','returned'),(40,240020,1011,1,'2024-11-07','2024-11-17','returned'),(41,240019,1010,1,'2024-11-06','2024-11-16','returned'),(42,240015,1007,2,'2024-11-05','2024-11-15','returned'),(43,240014,1006,1,'2024-11-04','2024-11-14','returned'),(44,240013,1005,2,'2024-11-03','2024-11-13','returned'),(45,240012,1004,1,'2024-11-02','2024-11-12','returned'),(46,240011,1003,2,'2024-11-01','2024-11-11','returned'),(47,240001,1003,1,'2024-11-29','2025-11-01','returned'),(48,240001,1003,1,'2024-11-29','2024-11-30','returned'),(49,240001,1003,1,'2024-11-29','2024-11-30','returned'),(50,240001,1003,1,'2024-11-30','2024-12-02','returned'),(51,240002,1001,1,'2024-12-04','2024-12-21','returned'),(52,240001,1003,2,'2024-12-05','2024-12-06','borrowed');
/*!40000 ALTER TABLE `borrowed` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `comments`
--

DROP TABLE IF EXISTS `comments`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `comments` (
                            `comment_id` int NOT NULL AUTO_INCREMENT,
                            `user_id` int NOT NULL,
                            `book_id` int NOT NULL,
                            `content` text,
                            `comment_date` date DEFAULT (curdate()),
                            PRIMARY KEY (`comment_id`),
                            KEY `user_id` (`user_id`),
                            KEY `book_id` (`book_id`),
                            CONSTRAINT `comments_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`),
                            CONSTRAINT `comments_ibfk_2` FOREIGN KEY (`book_id`) REFERENCES `books` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=59 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `comments`
--

LOCK TABLES `comments` WRITE;
/*!40000 ALTER TABLE `comments` DISABLE KEYS */;
INSERT INTO `comments` VALUES (1,240018,1009,'Một quyển sách rất thú vị, mình sẽ giới thiệu cho bạn bè.','2024-12-01'),(3,240016,1001,'Mình rất thích nội dung, dễ hiểu và cuốn hút.','2024-11-29'),(4,240010,1002,'Một trải nghiệm đọc thú vị, rất bổ ích.','2024-11-28'),(5,240009,1001,'Tuyệt vời! Mình đã học được nhiều điều từ sách này.','2024-11-27'),(6,240008,1008,'Nội dung rất hấp dẫn và đầy ý nghĩa.','2024-11-26'),(7,240007,1007,'Phong cách viết rất logic, giúp mình hiểu rõ vấn đề.','2024-11-25'),(8,240006,1006,'Cuốn sách này rất thực tế và hữu ích.','2024-11-24'),(9,240005,1005,'Một quyển sách không thể bỏ qua, rất hay.','2024-11-23'),(10,240004,1004,'Giải thích rõ ràng và dễ hiểu, rất phù hợp cho người mới.','2024-11-22'),(11,240003,1003,'Sách rất tuyệt, nội dung sâu sắc và ý nghĩa.','2024-11-21'),(12,240002,1002,'Mình rất thích cách trình bày của sách, rất dễ theo dõi.','2024-11-19'),(13,240001,1001,'Cuốn sách này làm mình suy ngẫm rất nhiều. Rất hay!','2024-11-17'),(14,240023,1014,'Cuốn sách này giúp mình hiểu sâu hơn về lĩnh vực yêu thích.','2024-11-17'),(15,240022,1013,'Nội dung có chiều sâu và rất ý nghĩa, mình đánh giá cao.','2024-11-16'),(16,240021,1012,'Phong cách viết đầy cảm hứng, phù hợp cho mọi người.','2024-11-15'),(17,240020,1011,'Một quyển sách đáng để đọc lại nhiều lần.','2024-11-14'),(18,240019,1010,'Sách viết rất chi tiết và dễ hiểu, mình rất thích.','2024-11-13'),(19,240015,1007,'Quyển sách này giúp mình giải đáp nhiều thắc mắc.','2024-11-12'),(20,240014,1006,'Một cuốn sách hay và đáng đọc.','2024-11-11'),(21,240013,1005,'Mình học được rất nhiều kiến thức thực tiễn từ sách này.','2024-11-10'),(22,240012,1004,'Cuốn sách rất hữu ích cho công việc và cuộc sống.','2024-11-09'),(23,240011,1003,'Nội dung sách đầy ý nghĩa, làm mình suy ngẫm rất nhiều.','2024-11-08'),(53,240001,1003,'sách hay ','2024-11-30'),(54,240001,1003,'bìa đẹp','2024-11-30'),(55,240001,1003,'sách hay quá bạn ơi','2024-11-30'),(56,240001,1003,'rất recommend','2024-11-30'),(57,240001,1004,'sách hay nha!!!','2024-12-04'),(58,240001,1006,'tiểu thuyết kinh điển','2024-12-05');
/*!40000 ALTER TABLE `comments` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
                         `user_id` int NOT NULL AUTO_INCREMENT,
                         `fullname` varchar(100) NOT NULL,
                         `username` varchar(50) NOT NULL,
                         `password` varchar(50) NOT NULL,
                         `phone` varchar(15) DEFAULT NULL,
                         `email` varchar(100) DEFAULT NULL,
                         PRIMARY KEY (`user_id`),
                         UNIQUE KEY `username` (`username`),
                         UNIQUE KEY `email` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=240040 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (240001,'Nguyễn Văn An','nguyenvanan','an123','0912-345-678','nguyenvanan@example.com'),(240002,'Trần Thị Bích','tranthibich','bich123','0934-567-890','tranthibich@example.com'),(240003,'Lê Minh Châu','leminhchau','chau123','0967-890-123','leminhchau@example.com'),(240004,'Phạm Quốc Đạt','phamquocdat','dat123','0981-234-567','phamquocdat@example.com'),(240005,'Nguyễn Hoàng Dũng','nguyenhoangdung','dung123','0915-678-901','nguyenhoangdung@example.com'),(240006,'Võ Thị Hương','vothihuong','huong123','0902-345-678','vothihuong@example.com'),(240007,'Đỗ Quỳnh Mai','doquynhmai','mai123','0977-890-123','doquynhmai@example.com'),(240008,'Nguyễn Phương Trinh','nguyenphuongtrinh','trinh123','0921-234-567','nguyenphuongtrinh@example.com'),(240009,'Phan Văn Sơn','phanvanson','son123','0944-567-890','phanvanson@example.com'),(240010,'Lý Đức Tài','lyductai','tai123','0933-456-789','lyductai@example.com'),(240011,'Lê Hồng Nhung','lehongnhung','nhung123','0909-678-901','lehongnhung@example.com'),(240012,'Trần Văn Khang','tranvankhang','khang123','0983-456-789','tranvankhang@example.com'),(240013,'Đặng Hồng Hạnh','danghonghanh','hanh123','0935-234-567','danghonghanh@example.com'),(240014,'Nguyễn Thị Lan','nguyenthilan','lan123','0906-345-678','nguyenthilan@example.com'),(240015,'Hoàng Phương Thảo','hoangphuongthao','thao123','0925-567-890','hoangphuongthao@example.com'),(240016,'Lý Minh Tuấn','lyminhtuan','tuan123','0948-678-901','lyminhtuan@example.com'),(240017,'Phạm Bảo Ngọc','phambaongoc','ngoc123','0904-234-567','phambaongoc@example.com'),(240018,'Vũ Hoàng Linh','vuhoanglinh','linh123','0972-345-678','vuhoanglinh@example.com'),(240019,'Bùi Thị Hạnh','buithihanh','hanh123','0938-456-789','buithihanh@example.com'),(240020,'Đoàn Quốc Hưng','doanquochung','hung123','0916-890-123','doanquochung@example.com'),(240021,'Phạm Minh Khoa','phamminkhoa','khoa123','0923-456-789','phamminkhoa@example.com'),(240022,'Nguyễn Đăng Khôi','nguyendangkhoi','khoi123','0901-567-890','nguyendangkhoi@example.com'),(240023,'Trần Hải Long','tranhailong','long123','0975-678-901','tranhailong@example.com'),(240024,'Lê Thị Phương','lethiphuong','phuong123','0989-234-567','lethiphuong@example.com'),(240025,'Đinh Gia Minh','dinhgiaminh','minh123','0917-890-123','dinhgiaminh@example.com'),(240038,'mạnh','simon','0','082307','nguyensymanh35@gmail.com');
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-12-05  1:21:09
