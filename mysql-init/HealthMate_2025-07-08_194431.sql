-- MySQL dump 10.13  Distrib 8.0.36, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: HealthMate
-- ------------------------------------------------------
-- Server version	8.0.42

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
-- Table structure for table `answers`
--

DROP TABLE IF EXISTS `answers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `answers` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `content` text,
  `created_at` datetime(6) DEFAULT NULL,
  `user_reply` text,
  `user_reply_at` datetime(6) DEFAULT NULL,
  `doctor_id` bigint DEFAULT NULL,
  `question_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKfaccd3c7e85e0ypatctw2kbi4` (`doctor_id`),
  KEY `FK3erw1a3t0r78st8ty27x6v3g1` (`question_id`),
  CONSTRAINT `FK3erw1a3t0r78st8ty27x6v3g1` FOREIGN KEY (`question_id`) REFERENCES `questions` (`id`),
  CONSTRAINT `FKfaccd3c7e85e0ypatctw2kbi4` FOREIGN KEY (`doctor_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `answers`
--

/*!40000 ALTER TABLE `answers` DISABLE KEYS */;
INSERT INTO `answers` VALUES (1,'Chào bạn, triple test nếu thực hiện lúc mang thai thì chỉ có ý nghĩa với bất thường về lệch bội nhiễm sắc thể liên quan hội chứng Edward và dị tật ống thần kinh thôi nhé. Nếu con bạn sinh ra bình thườn tức k bị hội chứng Edward và dị tật ống thần kinh thì là ổn rồi. Còn các bệnh lý khác thì triple test không có giá trị gì để gợi ý hay chẩn đoán nhé.','2025-07-01 12:23:25.682743','dạ cảm ơn bác sĩ','2025-07-01 12:40:39.220338',14,2),(2,'chào em ','2025-07-01 12:38:50.286151',NULL,NULL,14,1),(3,'bạn có cần hỏi gì thêm không ','2025-07-01 20:20:19.078034',NULL,NULL,14,2);
/*!40000 ALTER TABLE `answers` ENABLE KEYS */;

--
-- Table structure for table `appointment`
--

DROP TABLE IF EXISTS `appointment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `appointment` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `appointment_date` date DEFAULT NULL,
  `appointment_time` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
  `appointment_type` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
  `doctor` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
  `notes` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
  `patient_name` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
  `patient_phone` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
  `symptoms` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `appointment`
--

/*!40000 ALTER TABLE `appointment` DISABLE KEYS */;
INSERT INTO `appointment` VALUES (1,'2025-07-10','10:30','Tư vấn','BS NGuyễn Hoàng Giang - Chuyên khoa Nội tiết','không có','Nguyễn Ngọc Lan ','032131233','dị ứng hải sản'),(2,'2025-07-07','14:00','Tái khám','BS Nguyễn Khánh Ngọc - Chuyên khoa Da liễu','không có','Nguyễn Ngọc Lan ','0123131312','bị nổi mụn đỏ ');
/*!40000 ALTER TABLE `appointment` ENABLE KEYS */;

--
-- Table structure for table `appointment_dv`
--

DROP TABLE IF EXISTS `appointment_dv`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `appointment_dv` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `address` varchar(255) DEFAULT NULL,
  `appointment_date` varchar(255) DEFAULT NULL,
  `appointment_time` varchar(255) DEFAULT NULL,
  `cmnd` varchar(255) DEFAULT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `dob` varchar(255) DEFAULT NULL,
  `full_name` varchar(255) DEFAULT NULL,
  `gender` varchar(255) DEFAULT NULL,
  `nationality` varchar(255) DEFAULT NULL,
  `payment_status` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `province` varchar(255) DEFAULT NULL,
  `service_id` bigint DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `user_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `appointment_dv`
--

/*!40000 ALTER TABLE `appointment_dv` DISABLE KEYS */;
/*!40000 ALTER TABLE `appointment_dv` ENABLE KEYS */;

--
-- Table structure for table `articles`
--

DROP TABLE IF EXISTS `articles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `articles` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `title` varchar(255) NOT NULL,
  `category` varchar(255) DEFAULT NULL,
  `content` text,
  `image_url` varchar(255) DEFAULT NULL,
  `created_at` datetime DEFAULT CURRENT_TIMESTAMP,
  `updated_at` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `articles`
--

/*!40000 ALTER TABLE `articles` DISABLE KEYS */;
INSERT INTO `articles` VALUES (1,'HIỂU VỀ NHỮNG BIẾN CHỨNG TIỀM ẨN TRONG THAI KỲ','SỨC KHỎE PHỤ NỮ','<h2>Hiểu về những biến chứng tiềm ẩn trong thai kỳ</h2>\n<p>Biến chứng là điều cuối cùng mà bất kỳ bậc cha mẹ tương lai nào muốn nghĩ đến. Tuy nhiên, việc hiểu rõ những điều bất ngờ có thể xảy ra và biết cách xử lý khi chúng xảy ra là vô cùng quan trọng.</p>\n\n<p>Mặc dù ai cũng mong muốn một thai kỳ suôn sẻ, nhưng các biến chứng vẫn có thể xuất hiện và ảnh hưởng đến cả mẹ lẫn thai nhi — từ nhẹ đến nguy hiểm đến tính mạng. Theo bác sĩ <strong>Liana Koe</strong>, bác sĩ sản phụ khoa tại Bệnh viện Mount Alvernia, các yếu tố nguy cơ cũng rất đa dạng. Chúng bao gồm: tuổi mẹ cao, hút thuốc, lạm dụng rượu và ma túy, cũng như chỉ số khối cơ thể (BMI) quá thấp hoặc quá cao – đều có liên quan đến hầu hết các biến chứng thai kỳ.</p>\n\n<p>Trong bài viết này, chúng tôi sẽ trình bày một số biến chứng thai kỳ phổ biến, nguyên nhân có thể, cũng như các biện pháp phòng ngừa và lựa chọn điều trị dành cho mẹ bầu.</p>\n\n<h3>1. Ra máu trong 3 tháng đầu và sảy thai</h3>\n<p>Còn được gọi là tình trạng chảy máu và mất thai trong 12 tuần đầu của thai kỳ, đây là một hiện tượng phổ biến, ảnh hưởng đến khoảng 1 trong 4 trường hợp mang thai.</p>\n<p><strong>Nguyên nhân:</strong> tổn thương cổ tử cung như polyp, quan hệ tình dục, hoặc xuất huyết dưới màng đệm.</p>\n<p><strong>Lời khuyên:</strong> Tránh vận động mạnh và quan hệ tình dục nếu có chảy máu. Nên đi khám sớm và có thể dùng thuốc progesterone nếu cần.</p>\n\n<h3>2. Xuất huyết trước sinh (APH)</h3>\n<p>Là hiện tượng chảy máu sau tuần thứ 24. Nguyên nhân có thể do nhau tiền đạo, nhau bong non, hoặc nhiễm trùng âm đạo.</p>\n<p><strong>Nguy cơ:</strong> mất máu, thiếu máu, sinh non hoặc nguy hiểm tính mạng nếu nghiêm trọng.</p>\n<p><strong>Lời khuyên:</strong> Nghỉ ngơi, tránh vận động mạnh, dùng progesterone nếu có dấu hiệu sinh non, bổ sung sắt hoặc truyền máu khi thiếu máu.</p>\n\n<h3>3. Tăng huyết áp thai kỳ và tiền sản giật</h3>\n<p>Xuất hiện sau tuần 20, có thể dẫn đến các biến chứng nghiêm trọng như đột quỵ, suy tim, co giật nếu không kiểm soát.</p>\n<p><strong>Điều trị:</strong> Dùng thuốc hạ áp, chống co giật. Trường hợp nặng có thể phải sinh sớm để chấm dứt tình trạng.</p>\n\n<h3>4. Đái tháo đường thai kỳ (GDM)</h3>\n<p>Thường xuất hiện ở tam cá nguyệt 2 hoặc 3. Nguy cơ tăng ở người châu Á, người có tiền sử sinh con to hoặc buồng trứng đa nang.</p>\n<p><strong>Điều trị:</strong> Chế độ ăn hợp lý và tập thể dục; khoảng 10% trường hợp cần thuốc hoặc insulin.</p>\n\n<h3>5. Sinh non</h3>\n<p>Sinh trước 37 tuần, có thể do nhiễm trùng, bệnh lây qua đường tình dục, APH hoặc tiền sản giật.</p>\n<p><strong>Dấu hiệu:</strong> ra khí hư có mùi tanh, loãng hoặc màu xanh – có thể do viêm âm đạo do vi khuẩn.</p>\n<p><strong>Điều trị:</strong> Thuốc giảm co, tiêm steroid giúp phát triển phổi thai nhi, dùng progesterone nếu có tiền sử can thiệp cổ tử cung.</p>\n\n<p><em>Bài viết được đóng góp bởi bác sĩ Liana Koe, bác sĩ tại Bệnh viện Mount Alvernia.<br>\nTrích từ Tạp chí <strong>MyAlvernia Magazine – Số 50</strong>.</em></p>\n','	https://mtalvernia.sg/wp-content/uploads/2024/01/Understanding-Potential-Pregnancy-Complications.jpg','2025-06-28 16:12:55','2025-06-28 16:12:55'),(2,'4 ĐIỀU BẠN CẦN BIẾT VỀ BỆNH LẠC NỘI MẠC TỬ CUNG','SỨC KHỎE PHỤ NỮ','\n    <h2>4 điều bạn cần biết về bệnh lạc nội mạc tử cung</h2>\n	<p>Nếu bạn thường xuyên bị đau bụng kinh dữ dội và nghi ngờ đó không chỉ là những cơn đau thông thường, rất có thể bạn đang mắc một tình trạng gọi là <strong>lạc nội mạc tử cung</strong>. Bác sĩ <strong>Hong Sze Ching</strong>, chuyên gia sản phụ khoa tại Bệnh viện Mount Alvernia, sẽ giúp chúng ta hiểu rõ hơn về căn bệnh này.</p>\n\n	<p>Cơn đau quặn thắt ở bụng và vùng chậu khiến bạn phải gập người lại… Hãy tưởng tượng phải chịu đựng điều đó 5 ngày mỗi tháng, 12 tháng mỗi năm, trong nhiều thập kỷ — cho đến khi bạn mãn kinh. Đó là thực tế mà nhiều phụ nữ phải đối mặt khi mắc bệnh lạc nội mạc tử cung.</p>\n\n	<h3>1. Lạc nội mạc tử cung xảy ra như thế nào?</h3>\n	<p>Lạc nội mạc tử cung là tình trạng mô tương tự lớp nội mạc tử cung (niêm mạc trong tử cung) xuất hiện ở những vị trí bên ngoài tử cung. Các vị trí phổ biến gồm: buồng trứng (có thể hình thành <em>u sô-cô-la</em>), ống dẫn trứng, bề mặt tử cung, phúc mạc, và khu vực giữa âm đạo và trực tràng. Khi mô này nằm sâu trong thành cơ tử cung, bệnh được gọi là <strong>lạc nội mạc trong cơ tử cung (adenomyosis)</strong>.</p>\n	<p><strong>Tỷ lệ mắc:</strong> Khoảng 1 trong 10 phụ nữ trong độ tuổi sinh sản. Nguy cơ cao hơn nếu có người thân mắc bệnh.</p>\n\n	<h3>2. Nguyên nhân do đâu?</h3>\n	<p>Hiện nay vẫn chưa xác định chính xác nguyên nhân. Một số giả thuyết bao gồm: kinh nguyệt trào ngược, yếu tố di truyền và rối loạn miễn dịch.</p>\n	<p>Là bệnh phụ thuộc hormone: các mô ngoài tử cung cũng phản ứng với chu kỳ kinh nguyệt – gây chảy máu, viêm, sẹo và tổn thương vùng chậu.</p>\n	<p><strong>Chẩn đoán:</strong> khám phụ khoa, siêu âm vùng chậu, nội soi ổ bụng và xét nghiệm máu CA125.</p>\n\n	<h3>3. Đau bụng kinh hay là lạc nội mạc tử cung?</h3>\n	<p>Dấu hiệu điển hình là <strong>đau có tính chu kỳ</strong>, trở nên nghiêm trọng trước và trong kỳ kinh.</p>\n	<p><strong>Các triệu chứng khác:</strong></p>\n	<ul>\n	  <li>Đau khi quan hệ hoặc sau quan hệ</li>\n	  <li>Khó mang thai</li>\n	  <li>Đau bàng quang hoặc trực tràng</li>\n	  <li>Kinh nguyệt kéo dài, ra máu nhiều (adenomyosis)</li>\n	</ul>\n\n	<h3>4. Điều trị lạc nội mạc tử cung</h3>\n	<p>Điều trị phụ thuộc vào triệu chứng, mức độ bệnh và kế hoạch sinh con.</p>\n	<p><strong>Các phương pháp điều trị:</strong></p>\n	<ul>\n	  <li>Thuốc giảm đau nhóm NSAIDs</li>\n	  <li>Liệu pháp hormone:</li>\n	  <ul>\n		<li>Progestogen đường uống</li>\n		<li>Dụng cụ tử cung chứa hormone</li>\n		<li>Tiêm hormone GnRHa</li>\n	  </ul>\n	  <li>Phẫu thuật loại bỏ mô hoặc u lạc nội mạc nếu cần</li>\n	</ul>\n\n	<p>Nếu bạn nghi ngờ mình mắc bệnh, đừng ngần ngại đi khám sớm – bạn không cần phải chịu đựng nỗi đau này trong im lặng.</p>\n\n	<p><em>Bài viết được đóng góp bởi bác sĩ <strong>Hong Sze Ching</strong>, bác sĩ sản phụ khoa tại Bệnh viện Mount Alvernia.<br>\n	Trích từ tạp chí <strong>MyAlvernia Magazine – Số 49</strong>.</em></p>\n\n	','https://mtalvernia.sg/wp-content/uploads/2024/01/4-Things-You-Should-Know-About-Endometriosis.jpg','2025-06-28 16:29:35','2025-06-28 16:29:35'),(3,'CÁC BỆNH UNG THƯ Ở PHỤ NỮ','SỨC KHỎE PHỤ NỮ','\n    <h2>TS.BS. Lee Guek Eng và BS. Germaine Xu nói về ung thư ở phụ nữ</h2>\n\n	<p><strong>TS.BS. Lee Guek Eng</strong>, bác sĩ chuyên khoa ung thư vú tại Trung tâm Ung thư Icon (Mount Alvernia), và <strong>BS. Germaine Xu</strong>, bác sĩ phẫu thuật tại Phòng khám Phẫu thuật Vú và Tổng quát Germaine, chia sẻ những sự thật đáng chú ý và tin tích cực về ung thư ở phụ nữ.</p>\n\n	<h3>Tình hình ung thư ở phụ nữ tại Singapore</h3>\n	<ul>\n	  <li>Ung thư vú: 29,1%</li>\n	  <li>Ung thư nội mạc tử cung: 6,9%</li>\n	  <li>Ung thư buồng trứng: 5,4%</li>\n	  <li>Ung thư cổ tử cung: 3,1%</li>\n	</ul>\n	<p>Đáng lo ngại: Tỷ lệ ung thư vú tăng gần 3 lần trong 40 năm qua. Tuy nhiên, phát hiện sớm giúp điều trị hiệu quả và tăng khả năng khỏi bệnh.</p>\n\n	<h3>Triệu chứng ung thư vú</h3>\n	<ul>\n	  <li>Khối u ở vú hoặc nách</li>\n	  <li>Thay đổi da vùng vú, núm vú tụt vào trong</li>\n	  <li>Tiết dịch núm vú, đặc biệt có máu</li>\n	  <li>Đau vú hiếm khi là ung thư</li>\n	</ul>\n\n	<h3>Yếu tố nguy cơ ung thư vú</h3>\n	<ul>\n	  <li>Kinh nguyệt sớm, mãn kinh muộn</li>\n	  <li>Không sinh con hoặc sinh sau 30 tuổi</li>\n	  <li>Không cho con bú</li>\n	  <li>Tiền sử gia đình, đột biến BRCA1/2</li>\n	  <li>Béo phì, ít vận động, uống rượu</li>\n	</ul>\n\n	<h3>Các dấu hiệu cảnh báo sớm</h3>\n	<table border=\"1\" cellpadding=\"5\">\n	  <thead>\n		<tr>\n		  <th>Loại ung thư</th>\n		  <th>Dấu hiệu cảnh báo</th>\n		</tr>\n	  </thead>\n	  <tbody>\n		<tr><td>Ung thư vú</td><td>Khối u, tiết dịch, thay đổi da vú</td></tr>\n		<tr><td>Ung thư tử cung</td><td>Ra máu bất thường, đau vùng chậu</td></tr>\n		<tr><td>Ung thư buồng trứng</td><td>Đầy hơi, rối loạn tiêu hóa</td></tr>\n		<tr><td>Ung thư cổ tử cung</td><td>Ra máu sau quan hệ, dịch âm đạo có mùi</td></tr>\n	  </tbody>\n	</table>\n\n	<h3>Tỷ lệ sống sau 5 năm</h3>\n	<table border=\"1\" cellpadding=\"5\">\n	  <thead>\n		<tr>\n		  <th>Loại ung thư</th>\n		  <th>Tại chỗ</th>\n		  <th>Lan vùng</th>\n		  <th>Di căn xa</th>\n		</tr>\n	  </thead>\n	  <tbody>\n		<tr><td>Ung thư vú</td><td>99%</td><td>85%</td><td>27%</td></tr>\n		<tr><td>Ung thư tử cung</td><td>96%</td><td>70%</td><td>18%</td></tr>\n		<tr><td>Ung thư buồng trứng</td><td>92%</td><td>75%</td><td>30%</td></tr>\n		<tr><td>Ung thư cổ tử cung</td><td>92%</td><td>56%</td><td>17%</td></tr>\n	  </tbody>\n	</table>\n\n	<h3>Lời khuyên từ bác sĩ</h3>\n	<ul>\n	  <li>Bỏ thuốc lá, hạn chế rượu</li>\n	  <li>Duy trì cân nặng hợp lý</li>\n	  <li>Tham gia tầm soát định kỳ</li>\n	  <li>Tự kiểm tra vú thường xuyên</li>\n	</ul>\n\n	<h3>Hy vọng nhờ tiến bộ y học</h3>\n	<p>Các liệu pháp miễn dịch và thuốc nhắm trúng đích đang giúp cải thiện kết quả điều trị ung thư vú. Phụ nữ hãy chủ động quan tâm sức khỏe và luôn giữ niềm hy vọng.</p>\n\n	<p><em>Bài viết được trích từ tạp chí <strong>MyAlvernia Magazine – Số 46</strong>.</em></p>\n\n    ','https://mtalvernia.sg/wp-content/uploads/2019/12/womens-cancers.jpg','2025-06-28 16:34:43','2025-06-28 16:34:43'),(4,'SÁU CÂU HỎI NÓNG BỎNG VỀ THAI KỲ, DINH DƯỠNG VÀ CƠN ĐAU CHUYỂN DẠ','SỨC KHỎE PHỤ NỮ','<h2>Sáu câu hỏi nóng bỏng về thai kỳ, dinh dưỡng và cơn đau chuyển dạ</h2>\n\n<p><strong>Buổi phát sóng trực tiếp trên Facebook</strong> do Bệnh viện Mount Alvernia và Singlife with Aviva tổ chức ngày 18/1/2022, có sự tham gia của các chuyên gia: <strong>Rita Francis</strong> – Chuyên gia Chăm sóc Tiền sản & Tư vấn nuôi con bằng sữa mẹ, và <strong>Jacqueline Low</strong> – Chuyên gia Dinh dưỡng.</p>\n\n<h3>Câu hỏi 1: Có thực phẩm nào bà bầu nên tránh không?</h3>\n<p><strong>Jacqueline Low:</strong> Có. Bà bầu nên tránh:</p>\n<ul>\n  <li>Rượu – không có ngưỡng an toàn nào</li>\n  <li>Thịt sống, hải sản sống, trứng sống (kể cả trong sốt mayonnaise)</li>\n  <li>Cá chứa thủy ngân cao như cá kiếm, cá mập, cá thu lớn</li>\n  <li>Phô mai mốc (blue cheese, brie), không dùng được</li>\n  <li>Phô mai chín kỹ (mozzarella trên pizza) thì dùng được</li>\n</ul>\n\n<h3>Câu hỏi 2: Có cần hạn chế ăn mặn khi mang thai?</h3>\n<p><strong>Jacqueline Low:</strong> Chỉ cần hạn chế nếu có nguy cơ tiền sản giật. Tránh thực phẩm muối chua, đóng hộp. Nêm nếm vừa phải với nước tương hoặc dầu hào là chấp nhận được.</p>\n\n<h3>Câu hỏi 3: Mẹ bầu bị nôn nhiều nên ăn uống thế nào?</h3>\n<p><strong>Jacqueline Low:</strong> Giữ đủ nước là quan trọng nhất. Dùng vitamin B6 tự nhiên từ thực phẩm như chuối, yến mạch, thịt heo. Có thể dùng thuốc bổ dạng lỏng, uống chậm trong ngày.</p>\n\n<h3>Câu hỏi 4: Làm sao phòng ngừa đái tháo đường thai kỳ (GDM)?</h3>\n<p><strong>Jacqueline Low:</strong> Không có cách phòng ngừa tuyệt đối. Các yếu tố nguy cơ gồm:</p>\n<ul>\n  <li>Tuổi mẹ cao</li>\n  <li>BMI &gt; 30</li>\n  <li>Lịch sử sinh con nặng &gt; 4kg</li>\n  <li>Chủng tộc châu Á</li>\n</ul>\n<p>Giải pháp:</p>\n<ul>\n  <li>Tập thể dục thường xuyên</li>\n  <li>Ăn tinh bột chia nhiều bữa, không cắt hoàn toàn</li>\n  <li>Tránh ăn nhiều tinh bột một lúc</li>\n</ul>\n\n<h3>Câu hỏi 5: Dấu hiệu sắp sinh là gì?</h3>\n<p><strong>Rita Francis:</strong> Có 3 dấu hiệu:</p>\n<ol>\n  <li><strong>Ra nhớt hồng:</strong> Nút nhầy cổ tử cung bong. Theo dõi thêm tại nhà.</li>\n  <li><strong>Cơn gò đều:</strong> Tăng dần tần suất &amp; cường độ → Cần nhập viện.</li>\n  <li><strong>Vỡ ối:</strong> Nếu nước ối có màu xanh/vàng → Có thể suy thai → Đến viện ngay.</li>\n</ol>\n\n<h3>Câu hỏi 6: Phân biệt chuyển dạ thật và Braxton Hicks?</h3>\n<p><strong>Rita Francis:</strong> Braxton Hicks là gò giả, không đều, không tăng dần. Nếu:</p>\n<ul>\n  <li>Đau đều đặn, đau lưng</li>\n  <li>Cảm giác mót rặn</li>\n  <li>Chưa đến ngày sinh dự kiến</li>\n</ul>\n<p>→ Gọi bác sĩ để kiểm tra sinh non.</p>\n\n<p><em>Bài viết trích từ buổi chia sẻ trên Facebook của Bệnh viện Mount Alvernia – 18/1/2022.</em></p>\n','https://mtalvernia.sg/wp-content/uploads/2022/06/Six-Burning-Questions-About-Pregnancy1080.jpg','2025-06-28 16:36:31','2025-06-28 16:41:19'),(5,'HƯỚNG DẪN TOÀN DIỆN VỀ CÁC LOẠI UNG THƯ PHỤ KHOA','SỨC KHỎE PHỤ NỮ','\n    Ung thư phụ khoa là gì? Dấu hiệu nhận biết ra sao, và ai là người có nguy cơ?\n	Bác sĩ Timothy Lim, Chuyên gia sản phụ khoa cao cấp tại Phòng khám Timothy Lim về Phụ nữ & Phẫu thuật Ung thư, lý giải vì sao phụ nữ cần lưu tâm — nhưng cũng có lý do để hy vọng.\n\n	UNG THƯ CƠ QUAN SINH DỤC NỮ\n	Ung thư phụ khoa là nhóm ung thư bắt nguồn từ các cơ quan sinh sản của phụ nữ. Ba loại phổ biến nhất gồm:\n\n	Ung thư cổ tử cung\n\n	Ung thư tử cung\n\n	Ung thư buồng trứng\n\n	Ung thư vú không được xem là ung thư phụ khoa. Ngoài ra còn có các loại ung thư hiếm hơn như ung thư âm đạo, âm hộ và ống dẫn trứng.\n\n	1. UNG THƯ CỔ TỬ CUNG\n	Phần lớn các trường hợp ung thư cổ tử cung là do virus HPV (Human Papillomavirus) – một loại virus lây truyền qua đường tình dục hoặc qua tiếp xúc da với người nhiễm bệnh.\n\n	Triệu chứng: Ra huyết âm đạo bất thường, đặc biệt sau khi quan hệ tình dục.\n\n	Điều trị: Phẫu thuật đơn thuần hoặc kết hợp với xạ trị/hoá trị.\n\n	Nguy cơ: Nhiễm HPV, hút thuốc lá và các yếu tố khác làm tăng nguy cơ.\n\n	✅ TIN TỐT:\n	Ung thư cổ tử cung có thể phòng ngừa hiệu quả nhờ vắc xin HPV. Ngoài ra, các biện pháp tầm soát như phết tế bào cổ tử cung (Pap smear) và tầm soát HPV cũng rất hữu hiệu.\n\n	2. UNG THƯ TỬ CUNG\n	Ung thư tử cung (còn gọi là ung thư nội mạc tử cung) thường bắt đầu từ niêm mạc tử cung, nhưng cũng có thể phát sinh từ lớp cơ tử cung. Phụ nữ trên 40 tuổi dễ mắc bệnh. Chẩn đoán thường thông qua sinh thiết nội mạc tử cung.\n\n	Triệu chứng: Ra máu bất thường ở tử cung, ví dụ: sau mãn kinh hoặc rối loạn kinh nguyệt.\n\n	Điều trị: Phẫu thuật là bước đầu tiên, sau đó có thể cần xạ trị hoặc hoá trị tùy vào giai đoạn bệnh.\n\n	Nguy cơ: Tuổi cao, béo phì, hội chứng chuyển hoá, tiểu đường tuýp 2, buồng trứng đa nang, tiền sử gia đình mắc ung thư tử cung/ruột/ buồng trứng, người mang gene Lynch.\n\n	✅ TIN TỐT:\n	Khoảng 60% bệnh nhân được phát hiện ở giai đoạn sớm, nên khả năng khỏi chỉ bằng phẫu thuật rất cao. Duy trì cân nặng hợp lý và lối sống lành mạnh giúp giảm nguy cơ mắc ung thư tử cung cũng như nhiều loại ung thư khác.\n\n	3. UNG THƯ BUỒNG TRỨNG\n	Đây được gọi là “sát thủ thầm lặng”, vì thường được phát hiện khi đã ở giai đoạn muộn. Khoảng 5–10% ung thư buồng trứng là do di truyền. Đáng tiếc là chưa có phương pháp tầm soát hiệu quả cho loại ung thư này.\n\n	Triệu chứng: Không đặc hiệu — bụng trướng, ăn uống khó khăn, chán ăn.\n\n	Điều trị: Kết hợp phẫu thuật, hóa trị và liệu pháp nhắm trúng đích.\n\n	Nguy cơ: Người mang đột biến gene BRCA-1/BRCA-2, người có hội chứng Lynch, tiền sử gia đình có người mắc ung thư buồng trứng hoặc mắc lạc nội mạc tử cung.\n\n	✅ TIN TỐT:\n	Nguy cơ ung thư buồng trứng giảm đi ở phụ nữ sinh nhiều con và cho con bú lâu dài. Một số loại thuốc nội tiết kết hợp estrogen và progesteron (thuốc tránh thai phối hợp) cũng có thể giảm nguy cơ mắc bệnh.\n\n	🔹 Bài viết được trích từ tạp chí MyAlvernia Magazine – Số 45.\n	','	https://mtalvernia.sg/wp-content/uploads/2021/10/iStock-916013054.jpg','2025-06-28 16:44:00','2025-06-28 16:44:00'),(6,'ĐIỀU MỌI PHỤ NỮ CẦN BIẾT VỀ UNG THƯ CỔ TỬ CUNG','SỨC KHỎE PHỤ NỮ','\n    <h2>Những điều cần biết về ung thư cổ tử cung</h2>\n	<p><strong>Bác sĩ Wong Wai Loong</strong>, chuyên gia ung thư phụ khoa cao cấp tại Bệnh viện Mount Alvernia, chia sẻ những thông tin quan trọng về căn bệnh ung thư cổ tử cung – loại ung thư phổ biến thứ 10 ở phụ nữ Singapore.</p>\n\n	<h3>Tại sao tầm soát định kỳ lại quan trọng?</h3>\n	<ul>\n	  <li>90% ca ung thư cổ tử cung là do virus HPV (Human Papillomavirus) – lây qua đường tình dục hoặc tiếp xúc da.</li>\n	  <li>HPV có thể âm thầm tồn tại và mất nhiều năm để tiến triển thành ung thư.</li>\n	  <li>Giai đoạn tiền ung thư (loạn sản tế bào cổ tử cung - CIN) thường không có triệu chứng rõ ràng.</li>\n	</ul>\n	<p><strong>➡️ Tin tốt:</strong> Giai đoạn tiền ung thư có thể chữa khỏi nếu được phát hiện sớm – tầm soát định kỳ rất quan trọng.</p>\n	<p><strong>🔹 Khuyến nghị:</strong> Phụ nữ từ 25 tuổi trở lên đã có quan hệ tình dục nên bắt đầu tầm soát.</p>\n\n	<h3>Các phương pháp tầm soát hiện nay</h3>\n	<ul>\n	  <li><strong>Xét nghiệm HPV:</strong> Phát hiện các chủng HPV nguy cơ cao.</li>\n	  <li><strong>PAP smear (phết tế bào cổ tử cung):</strong> Tìm tế bào bất thường tại cổ tử cung.</li>\n	</ul>\n	<p><strong>📌 Tần suất:</strong></p>\n	<ul>\n	  <li>Từ 25–29 tuổi: PAP smear mỗi 3 năm.</li>\n	  <li>Từ 30 tuổi trở lên: Xét nghiệm HPV mỗi 5 năm.</li>\n	</ul>\n\n	<h3>Tiến bộ trong công nghệ tầm soát</h3>\n	<p>Hiện nay, bộ kit tự lấy mẫu HPV đã xuất hiện tại một số phòng khám phụ khoa – không cần dùng mỏ vịt, nhưng vẫn chính xác tương đương xét nghiệm của bác sĩ.</p>\n	<p><em>Trong tương lai, kit tại nhà có thể giúp phụ nữ chủ động hơn trong tầm soát.</em></p>\n\n	<h3>Những hiểu lầm thường gặp</h3>\n	<ul>\n	  <li><strong>❌ Ung thư cổ tử cung là bệnh di truyền:</strong> Sai. Do HPV, không phải gen di truyền.</li>\n	  <li><strong>❌ Tiêm vắc-xin HPV rồi không cần xét nghiệm:</strong> Sai. Vẫn có 10% nguy cơ cần tầm soát định kỳ.</li>\n	  <li><strong>❌ HPV là bệnh STD:</strong> Sai một phần. HPV không chính thức được xếp vào nhóm STD.</li>\n	</ul>\n\n	<h3>Cổ tử cung là gì?</h3>\n	<p>Là phần dưới cùng và hẹp của tử cung, nối với âm đạo.</p>\n	<p><strong>Chức năng:</strong></p>\n	<ul>\n	  <li>Tiết dịch nhầy hỗ trợ thụ thai.</li>\n	  <li>Mở rộng trong quá trình sinh nở.</li>\n	  <li>Ngăn vi khuẩn, dị vật xâm nhập tử cung.</li>\n	</ul>\n\n	<h3>Làm sao giảm nguy cơ ung thư cổ tử cung?</h3>\n	<ul>\n	  <li>✅ Ngưng hút thuốc</li>\n	  <li>✅ Quan hệ tình dục muộn hơn</li>\n	  <li>✅ Hạn chế số lượng bạn tình</li>\n	  <li>✅ Tầm soát định kỳ (PAP smear, xét nghiệm HPV)</li>\n	  <li>✅ Lối sống lành mạnh (ăn uống, vận động, giảm stress)</li>\n	</ul>\n\n	<p><em>🔹 Bài viết được đóng góp bởi bác sĩ Wong Wai Loong, bác sĩ được chứng nhận tại Bệnh viện Mount Alvernia.<br>\n	🔹 Trích từ tạp chí <strong>MyAlvernia – Số 52</strong>.</em></p>\n		\n	','https://mtalvernia.sg/wp-content/uploads/2024/03/hpv-vaccine-770x436-1.jpg','2025-06-28 16:49:32','2025-06-28 16:49:32'),(7,'Y HỌC CÁ NHÂN HÓA TRONG ĐIỀU TRỊ UNG THƯ VÚ','SỨC KHỎE PHỤ NỮ','\n	<p>Vào ngày 19 tháng 9 năm 2020, Bệnh viện Mount Alvernia và Trung tâm Ung thư Icon đã phối hợp tổ chức một sự kiện phát trực tuyến, với sự hỗ trợ từ Mundipharma Singapore. Bác sĩ Lee Guek Eng – bác sĩ chuyên khoa tại bệnh viện – đã chia sẻ về việc áp dụng y học cá nhân hóa trong điều trị ung thư vú, cũng như cách quản lý các tác dụng phụ liên quan.</p>\n\n	<h3>UNG THƯ VÚ – LOẠI UNG THƯ PHỔ BIẾN NHẤT Ở NỮ GIỚI</h3>\n	<p>Bác sĩ Lee nhấn mạnh tỷ lệ mắc ung thư vú đã tăng gấp ba lần kể từ những năm 1970. Nhờ việc tăng cường tầm soát, ngày càng nhiều ca ung thư vú giai đoạn đầu được phát hiện. Nhiều phụ nữ trẻ hiện nay cũng ý thức hơn trong việc tự khám và tầm soát.</p>\n	<p>Những thay đổi nên được kiểm tra gồm: khối u ở vú, thay đổi da, núm vú hoặc tiết dịch bất thường. Không phải tất cả đều là dấu hiệu ung thư, nhưng nên được bác sĩ đánh giá.</p>\n\n	<h3>CÁC PHƯƠNG PHÁP ĐIỀU TRỊ</h3>\n	<p>Việc điều trị cá nhân hóa dựa trên giai đoạn và đặc điểm sinh học như thụ thể hormone, HER2. Có ba nhóm chính:</p>\n	<ul>\n	  <li>Dương tính với thụ thể hormone</li>\n	  <li>Dương tính với HER2</li>\n	  <li>Âm tính ba lần</li>\n	</ul>\n	<p>Ung thư dương tính với hormone thường ít ác tính hơn, nhưng hiện nay các nhóm đều có liệu pháp hiệu quả. Với giai đoạn IV, điều trị thường là hóa trị toàn thân và đa ngành.</p>\n\n	<h3>QUẢN LÝ TÁC DỤNG PHỤ</h3>\n	<p>Buồn nôn, rụng tóc và mất mật độ xương là những tác dụng phụ phổ biến nhưng có thể kiểm soát. Thuốc chống nôn, tiêm ngừa mất xương và sử dụng tóc giả giúp cải thiện chất lượng sống.</p>\n\n	<h3>SINH SẢN VÀ UNG THƯ</h3>\n	<p>Phụ nữ trẻ nên được tư vấn trước điều trị nếu có kế hoạch sinh con. Bác sĩ khuyến nghị đánh giá khả năng sinh sản trước khi bắt đầu phác đồ.</p>\n\n	<h3>KHỐI U LÀNH VÀ UNG THƯ</h3>\n	<p>Khối u lành cần được chẩn đoán hình ảnh và có thể sinh thiết. Theo dõi định kỳ giúp kiểm soát nguy cơ.</p>\n\n	<h3>TỶ LỆ SỐNG SÓT</h3>\n	<ul>\n	  <li>Giai đoạn I: trên 90 phần trăm</li>\n	  <li>Giai đoạn II: khoảng 80 phần trăm</li>\n	  <li>Giảm dần ở các giai đoạn sau</li>\n	</ul>\n	<p>Ung thư tái phát hoặc di căn vẫn có thể kiểm soát tốt bằng thuốc.</p>\n\n	<h3>UNG THƯ TÁI PHÁT</h3>\n	<p>Cần sinh thiết lại để xác định tính chất ung thư. Điều trị tùy thuộc đặc điểm sinh học mới.</p>\n\n	<h3>NGUY CƠ MẮC UNG THƯ KHÁC</h3>\n	<p>Nếu mang gen BRCA đột biến, nguy cơ mắc các ung thư khác như tuyến tiền liệt, tụy ở nam giới và buồng trứng ở nữ giới sẽ tăng cao.</p>\n\n	<h3>CẮT BỎ VÚ DỰ PHÒNG</h3>\n	<p>Với người mang gen BRCA đột biến, có thể lựa chọn phẫu thuật cắt bỏ vú dự phòng để giảm gần như tuyệt đối nguy cơ mắc ung thư vú.</p>\n\n	<h3>DANH SÁCH KIỂM TRA PHÒNG NGỪA</h3>\n	<ul>\n	  <li>Biết tiền sử gia đình</li>\n	  <li>Tập thể dục đều đặn</li>\n	  <li>Khám sức khỏe và tầm soát định kỳ</li>\n	  <li>Tự khám vú</li>\n	  <li>Báo ngay thay đổi bất thường</li>\n	  <li>Không hút thuốc</li>\n	  <li>Hạn chế rượu</li>\n	  <li>Ăn uống lành mạnh</li>\n	</ul>\n\n	<h3>GIẢI THÍCH THUẬT NGỮ</h3>\n	<ul>\n	  <li>Y học cá nhân hóa: điều trị theo đặc điểm riêng của từng bệnh nhân</li>\n	  <li>Thụ thể hormone hoặc HER2: protein ảnh hưởng đến cách ung thư phát triển</li>\n	  <li>Âm tính ba lần: không có thụ thể estrogen, progesterone và HER2</li>\n	  <li>BRCA1 hoặc BRCA2: gen ức chế khối u, khi đột biến sẽ làm tăng nguy cơ ung thư</li>\n	  <li>Hiệu ứng Angelina Jolie: hiện tượng nhiều người xét nghiệm gen sau khi cô công bố phẫu thuật dự phòng</li>\n	</ul>\n\n	<p><em>(Nội dung trích từ Facebook Live ngày 19 tháng 9 năm 2020 và Tạp chí My Alvernia số 42 và 43)</em></p>\n\n  ','https://mtalvernia.sg/wp-content/uploads/2021/01/Personalised-Medicine-for-Breast-Cancer.jpg','2025-06-28 17:12:44','2025-06-28 17:12:44'),(8,'CÁC RỐI LOẠN PHỤ KHOA THƯỜNG GẶP','SỨC KHỎE PHỤ NỮ','\n   <h2>SỨC KHỎE SAU KHI SINH CON</h2>\n	<p>Ngoài thai kỳ và sinh nở, bạn nên tiếp tục chăm sóc sức khỏe tổng thể của mình để giảm nguy cơ mắc các bệnh như ung thư vú, bất thường cổ tử cung, sự phát triển của u xơ tử cung và u nang buồng trứng. Với hầu hết phụ nữ, các bệnh lý này dễ điều trị, nhưng ở một số người, tình trạng có thể để lại hậu quả nghiêm trọng. Trong những trường hợp nặng, bệnh có thể ảnh hưởng đến khả năng sinh sản và sức khỏe thai nhi nếu bạn đang có kế hoạch mang thai lần nữa.</p>\n\n	<p>Dưới đây là những bệnh lý phụ khoa phổ biến nhất ảnh hưởng đến các bà mẹ tại Singapore:</p>\n\n	<h3>U XƠ TỬ CUNG (UTERINE FIBROID)</h3>\n\n	<p>U xơ là những khối u hoặc sự phát triển bất thường xuất hiện trong thành tử cung. Những khối u này bao gồm các mô cơ và mô xơ. U xơ là tình trạng phổ biến và thường gặp ở phụ nữ trong độ tuổi từ 30 đến 50.</p>\n\n	<p>Hiện chưa xác định được nguyên nhân chính xác gây ra u xơ. Ngoài yếu tố di truyền, sự hiện diện của các hormone nữ như estrogen và progesterone có liên quan đến sự phát triển của u xơ. Do liên hệ này, u xơ thường phát triển trong thai kỳ khi nồng độ hormone tăng cao, và sẽ co lại sau khi mãn kinh khi hormone giảm.</p>\n\n	<p>Béo phì cũng làm tăng nguy cơ mắc u xơ, do lượng estrogen trong cơ thể người béo cao hơn.</p>\n\n	<p>Các yếu tố nguy cơ khác bao gồm: bắt đầu có kinh sớm, chế độ ăn nhiều thịt đỏ, ít rau xanh và trái cây, cũng như tiêu thụ rượu. Tuy chưa có bằng chứng rõ ràng liên hệ giữa dinh dưỡng và u xơ, nhưng chế độ ăn giàu rau xanh được xem là có thể giúp giảm tỷ lệ mắc bệnh.</p>\n\n	<p>Sau phẫu thuật, u xơ có thể tái phát. Không có bằng chứng cho thấy việc tránh một số loại thực phẩm có thể ngăn chặn tái phát. Sau khi cắt bỏ u xơ (myomectomy), có từ 15 đến 30 phần trăm khả năng u xơ sẽ phát triển trở lại trong vòng 10 năm. Tuy nhiên, đó không nhất thiết là cùng một khối u mà có thể là khối u mới.</p>\n\n	<p>Phụ nữ có thể xuất hiện nhiều khối u xơ với kích thước khác nhau:</p>\n\n	<ul>\n	  <li>Dưới 2 đến 3 cm: nhỏ</li>\n	  <li>Từ 4 đến 6 cm: trung bình</li>\n	  <li>Trên 8 cm: lớn</li>\n	</ul>\n\n	<p>U xơ lớn có thể gây chảy máu kinh nguyệt nhiều dẫn đến thiếu máu. Ngoài ra, nếu u xơ chèn ép vào ruột hoặc bàng quang, có thể gây táo bón hoặc đi tiểu nhiều lần.</p>\n\n	<p>U xơ đôi khi có liên quan đến vô sinh, sẩy thai và sinh non. Trong những trường hợp cực kỳ hiếm, u xơ rất lớn có thể liên quan đến ung thư tử cung.</p>\n\n  ','https://mtalvernia.sg/wp-content/uploads/2019/06/Common-Gynaecological-Disorders.jpg','2025-06-28 17:18:50','2025-06-28 17:18:50'),(9,'ĐIỀU TRỊ CÁC RỐI LOẠN PHỤ KHOA BẰNG PHẪU THUẬT ÍT XÂM LẤN','SỨC KHỎE PHỤ NỮ','\n   Chúng ta cùng tìm hiểu thêm về phương pháp này qua chia sẻ của bác sĩ Hong Sze Ching từ Phòng khám SOG – SC Hong Clinic for Women tại Bệnh viện Mount Alvernia.\n\n	PHÁT TRIỂN PHẪU THUẬT NỘI SOI Ổ BỤNG\n	Vài thập kỷ trước, phẫu thuật nội soi chỉ được sử dụng hạn chế trong lĩnh vực phụ khoa để chẩn đoán và triệt sản ống dẫn trứng. Tuy nhiên, trong vòng 40 năm qua, kỹ thuật này đã phát triển vượt bậc, trở thành công cụ chính trong nhiều ca phẫu thuật phụ khoa và ngoài phụ khoa.\n\n	Nội soi hiện là phương pháp điều trị ưu tiên cho nhiều bệnh lý phụ khoa như:\n\n	Thai ngoài tử cung\n\n	Lạc nội mạc tử cung\n\n	U nang buồng trứng\n\n	U xơ tử cung\n\n	Ngoài ra, nó còn hữu ích trong việc chẩn đoán các tình trạng như đau vùng chậu hoặc lạc nội mạc tử cung. Với kỹ thuật phẫu thuật ngày càng tiên tiến, một số loại ung thư giai đoạn sớm cũng có thể được điều trị bằng nội soi. Phạm vi ứng dụng của phẫu thuật nội soi tiếp tục được mở rộng và hiện nay đã trở thành thủ thuật phổ biến trên toàn thế giới – cả trong chẩn đoán lẫn điều trị.\n\n	Với sự phát triển của công nghệ, nội soi ngày càng tinh vi hơn. Nhờ có camera hiện đại và các thiết bị phẫu thuật tiên tiến, bác sĩ phẫu thuật giờ đây có thể thực hiện những ca mổ phức tạp hơn. Một số đổi mới gần đây bao gồm:\n\n	Nội soi hỗ trợ bằng robot\n\n	Nội soi qua cổng đơn (single-port)\n\n	Phẫu thuật qua lỗ tự nhiên (natural orifice transluminal surgery)\n\n	Phẫu thuật ít xâm lấn mang lại nhiều lợi ích:\n\n	Ít đau sau mổ\n\n	Thời gian nằm viện ngắn\n\n	Hồi phục nhanh\n\n	Nguy cơ nhiễm trùng vết mổ thấp hơn nhờ đường rạch nhỏ\n\n	Thẩm mỹ cao hơn do sẹo nhỏ hơn so với phẫu thuật hở\n\n	Đối với bác sĩ phẫu thuật, thiết bị nội soi giúp phóng đại vùng phẫu thuật, hỗ trợ thực hiện các thao tác chính xác và phức tạp hơn.\n\n	Bác sĩ Hong chia sẻ:\n	“Nội soi thường được thực hiện dưới gây mê toàn thân bởi bác sĩ phụ khoa. Phần lớn bệnh nhân có thể xuất viện trong ngày hoặc ngày hôm sau và phục hồi nhanh hơn so với phẫu thuật truyền thống.”\n	Hầu hết bệnh nhân có thể trở lại sinh hoạt bình thường chỉ sau vài tuần, so với khoảng 4 tuần trong phẫu thuật mổ hở.\n\n\n  ','https://mtalvernia.sg/wp-content/uploads/2018/06/rsz_abc.jpg','2025-06-28 17:22:21','2025-06-28 17:22:21'),(10,'Vì sao cần giáo dục giới tính cho trẻ em?','GIÁO DỤC GIỚI TÍNH','\n    <h2>VAI TRÒ CỦA GIÁO DỤC GIỚI TÍNH TRONG TUỔI DẬY THÌ</h2>\n\n<p>Khi bước vào tuổi dậy thì, trẻ không chỉ nhận thấy được những thay đổi bên ngoài cơ thể mà còn cảm nhận được những thay đổi từ bên trong. Điều này khiến trẻ bỡ ngỡ và bắt đầu có xu hướng tò mò, muốn khám phá nhiều hơn. Giáo dục giới tính kịp thời sẽ giúp trẻ tránh được những cái nhìn sai lệch, hiểu rõ bản thân và các vấn đề mà cơ thể đang gặp phải, cụ thể như sau:</p>\n\n<h3>1. Hiểu rõ vấn đề sinh lý của bản thân</h3>\n<p>Cơ thể trẻ ở mỗi giới tính sẽ có những điểm khác nhau. Tương tự, sự thay đổi của cơ thể khi bước vào giai đoạn dậy thì cũng khác nhau. Giáo dục giới tính giúp trẻ hiểu rõ về các vấn đề sinh lý, sự thay đổi của cơ thể trong từng giai đoạn, từ đó hình thành tâm lý phù hợp.</p>\n\n<h3>2. Tránh xa tệ nạn</h3>\n<p>Giáo dục giới tính giúp trẻ biết được những bộ phận cần được bảo vệ trên cơ thể. Từ đó, trẻ biết cách bảo vệ bản thân, giảm nguy cơ bị xâm hại tình dục.</p>\n\n<h3>3. Giảm tỷ lệ mang thai ngoài ý muốn</h3>\n<p>Thông qua giáo dục giới tính, trẻ có thể hiểu được hậu quả của các hoạt động liên quan đến tình dục không đúng cách, nắm rõ thông tin về sức khỏe sinh sản và biết cách tránh thai an toàn, hiệu quả. Đây là một trong những ý nghĩa quan trọng của giáo dục giới tính vì nó giúp giảm tỷ lệ mang thai ngoài ý muốn, nạo phá thai và nguy cơ mắc các bệnh truyền nhiễm qua đường tình dục.</p>\n\n<h3>4. Giúp trẻ nhận thức được giá trị của bản thân</h3>\n<p>Từ những kiến thức nhận được thông qua giáo dục giới tính, trẻ sẽ hiểu rõ hơn về sự phát triển và thay đổi của cơ thể. Từ đó, trẻ nhận thức được giá trị của bản thân, hiểu được đâu là những mối quan hệ không lành mạnh và tình dục không an toàn.</p>\n\n<h3>5. Biết cách phòng ngừa trước những nguy cơ xâm hại tình dục</h3>\n<p>Một cuộc khảo sát cho thấy có đến tám phần trăm học sinh trung học bị ép buộc quan hệ tình dục, tức trung bình cứ mười học sinh thì có một em bị bạo lực tình dục. Giáo dục giới tính giúp trẻ có cái nhìn đúng về tình dục, hiểu về các mối quan hệ tốt và xấu, cũng như cách bảo vệ bản thân trước những cám dỗ và nguy cơ bị xâm hại.</p>\n\n<p><em>(Trích từ khảo sát về giáo dục giới tính và an toàn tình dục học đường)</em></p>\n\n	','https://ischool.vn/wp-content/uploads/2022/12/giao-duc-gioi-tinh-thumb.jpeg','2025-06-28 18:31:13','2025-06-28 18:35:47'),(11,'Cách giáo dục giới tính cho trẻ em như thế nào?','GIÁO DỤC GIỚI TÍNH','\n	<h2>PHƯƠNG PHÁP GIÁO DỤC GIỚI TÍNH CHO TRẺ PHÙ HỢP TỪNG ĐỘ TUỔI</h2>\n\n<p>Nhiều phụ huynh có xu hướng ép trẻ học nhiều hơn để ngày càng giỏi giang hơn nhưng lại quên mất rằng giáo dục giới tính cho trẻ là điều vô cùng quan trọng. Thậm chí, nhiều phụ huynh còn lảng tránh hoặc lúng túng do không có phương pháp phù hợp theo từng độ tuổi. Dưới đây là một số phương pháp giáo dục giới tính cho trẻ tinh tế mà bố mẹ có thể tham khảo và thực hiện:</p>\n\n<h3>1. Giáo dục từ sớm nhưng đừng quá vội vàng</h3>\n\n<p>Khi lên bốn tuổi, trẻ bước vào giai đoạn muốn khám phá thế giới xung quanh nhiều hơn, nhưng đây cũng là độ tuổi trẻ dễ bị xâm hại nhất. Lúc này, bố mẹ nên bắt đầu giải thích cho trẻ hiểu về giới tính bằng những thông tin cơ bản, nhẹ nhàng.</p>\n\n<p><strong>Ví dụ:</strong> Khi tắm hoặc thay đồ cho con, bố mẹ nên nói cho trẻ hiểu về những bộ phận riêng tư trên cơ thể, và không ai được phép nhìn hay chạm vào những vị trí đó.</p>\n\n<h3>2. Giải thích cho trẻ một cách đơn giản, dễ hiểu</h3>\n\n<p>Trước đây, giáo dục giới tính không được phổ biến rộng rãi khiến nhiều phụ huynh cũng không thật sự hiểu rõ khái niệm này. Đồng thời, khoảng cách thế hệ khiến việc trò chuyện với trẻ về giới tính trở nên khó khăn.</p>\n\n<p>Tuy nhiên, giáo dục giới tính là cách tốt nhất giúp trẻ hiểu về cơ thể, giới tính, sinh lý, tình dục và cách phòng tránh các rủi ro ảnh hưởng đến tương lai. Vì vậy, bố mẹ cần vượt qua các rào cản để trò chuyện với trẻ một cách đơn giản, chậm rãi, cởi mở. Nếu bố mẹ tỏ ra ngại ngùng, trẻ cũng sẽ khó cảm thấy thoải mái và có thể hiểu sai hoặc tìm cách né tránh vấn đề.</p>\n\n<h3>3. Sử dụng nhiều cách tiếp cận vấn đề tình dục gián tiếp</h3>\n\n<p>Trong trường hợp cảm thấy quá khó để giáo dục cho trẻ một cách trực tiếp, bố mẹ có thể chọn các phương pháp gián tiếp thông qua câu chuyện, phim ảnh hoặc các tình huống giả định.</p>\n\n<p><strong>Ví dụ:</strong> Bố mẹ nên giải thích cho trẻ rằng chỉ có sự tin tưởng và thấu hiểu mới là nền tảng của tình yêu, chứ không phải tình dục. Trẻ cần học cách chịu trách nhiệm cho hành vi của mình. Khi đủ trưởng thành, đủ khả năng chịu trách nhiệm và thật sự tin tưởng vào đối phương, trẻ mới nên nghĩ đến tình dục.</p>\n\n<h3>4. Giáo dục theo độ tuổi</h3>\n\n<p>Tùy vào từng độ tuổi, trẻ sẽ có sự thay đổi về nhận thức, tâm lý và sinh lý. Việc giáo dục giới tính quá sớm hoặc quá muộn đều có thể gây phản tác dụng. Vì vậy, để đảm bảo hiệu quả, bố mẹ nên chọn lọc thông tin phù hợp với từng giai đoạn phát triển của trẻ.</p>\n\n<p>Việc giáo dục giới tính đúng cách sẽ giúp trẻ không chỉ hiểu về bản thân mà còn hình thành kỹ năng sống, biết tự bảo vệ và tôn trọng người khác.</p>\n\n    \n	','https://tamanhhospital.vn/wp-content/uploads/2023/10/giao-duc-gioi-tinh-giup-tre-hieu-ro-hon-ve-cac-van-de-sinh-ly.jpg','2025-06-28 18:33:29','2025-06-28 18:33:29'),(12,'Độ tuổi thích hợp để giáo dục sức khỏe giới tính?','GIÁO DỤC GIỚI TÍNH','\n    Giáo dục giới tính cho trẻ nên được bắt đầu khi trẻ còn nhỏ và duy trì cho đến khi trưởng thành. Điều này sẽ giúp trẻ nắm bắt thông tin một cách dễ dàng, chặt chẽ, động thời, giúp bố mẹ cảm thấy tự nhiên và dễ trò chuyện với con về vấn đề tình dục, giới tính. Tuy nhiên, khả năng tiếp nhận thông tin của trẻ ở mỗi độ tuổi sẽ khác nhau cho nên tốt nhất, bố mẹ nên chia lượng thông tin giáo dục giới tính thành nhiều giai đoạn, phù hợp với độ tuổi của trẻ. (3)\n\n1. Trẻ từ 13 – 24 tháng\nỞ giai đoạn này, trẻ cần nhận biết được tên gọi của các bộ phận trên cơ thể. Điều này sẽ giúp trẻ giao tiếp tốt hơn khi nói về các vấn đề sức khỏe, chấn thương liên quan hoặc làm dụng tình dục (nếu có).\n\nTrẻ từ 13 – 24 tháng tuổi đã có thể nhận biết về sự khác biệt giữa nam và nữ. Tuy nhiên, bố mẹ nên giúp trẻ hiểu giới tính của một người không được xác định bởi bộ phận sinh dục của họ mà nó có thể được thể hiện qua nhiều cách khác. Cơ thể của mỗi người là riêng tư, việc trẻ tò mò và tự chạm vào các bộ phận trên cơ thể là bình thường nhưng chúng cần biết nơi nào là nơi phù hợp và nơi nào là không phù hợp cho những hành động như thế.\n	',' https://tamanhhospital.vn/wp-content/uploads/2023/10/bo-me-co-the-day-cho-tre-cac-thong-tin-co-ban.jpg','2025-06-28 18:37:02','2025-06-28 18:37:02');
/*!40000 ALTER TABLE `articles` ENABLE KEYS */;

--
-- Table structure for table `consultants`
--

DROP TABLE IF EXISTS `consultants`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `consultants` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `email` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `experience` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `notes` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci,
  `phone` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `specialization` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `status` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKe607pkkegh9hni7v2l8pthhvy` (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `consultants`
--

/*!40000 ALTER TABLE `consultants` DISABLE KEYS */;
/*!40000 ALTER TABLE `consultants` ENABLE KEYS */;

--
-- Table structure for table `cycles`
--

DROP TABLE IF EXISTS `cycles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cycles` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `cycle_length` int NOT NULL,
  `method` varchar(255) DEFAULT NULL,
  `period_length` int NOT NULL,
  `start_date` date DEFAULT NULL,
  `user_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKst79at6qlv0gnr1fo70cp38ai` (`user_id`),
  CONSTRAINT `FKst79at6qlv0gnr1fo70cp38ai` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cycles`
--

/*!40000 ALTER TABLE `cycles` DISABLE KEYS */;
INSERT INTO `cycles` VALUES (1,21,'PILL_21',5,'2025-06-25',1),(2,33,'regular',5,'2025-06-19',1);
/*!40000 ALTER TABLE `cycles` ENABLE KEYS */;

--
-- Table structure for table `doctors`
--

DROP TABLE IF EXISTS `doctors`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `doctors` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `email` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
  `name` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
  `rating` double NOT NULL,
  `specialty` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
  `img_url` varchar(500) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
  `price` double DEFAULT NULL,
  `appointment_type` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
  `location` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `doctors`
--

/*!40000 ALTER TABLE `doctors` DISABLE KEYS */;
INSERT INTO `doctors` VALUES (1,'chuthiminh@gmail.com','Chu Thị Minh',0,'Lão và bệnh phổi','/images/pDoctors/BSChuThiMinh.webp',150000,NULL,NULL),(2,'nguyenhoanggiang@gmail.com','Nguyễn Hoàng Giang',0,'Y học gia đình','/images/pDoctors/BSNguyenHoangGiang.webp',150000,NULL,NULL),(3,'hominhtam@gmail.com','Hồ Minh Tâm',0,'Đa khoa','/images/pDoctors/BSHoMinhTam.webp',150000,NULL,NULL),(4,'lequocviet@gmail.com','Lê Quốc Việt',0,'Nội cơ xương khớp','/images/pDoctors/BSLeQuocViet.webp',150000,NULL,NULL),(5,'duongthihanh@gmail.com','Dương Thị Hạnh',0,'Ngoại sản','/images/pDoctors/BSDuongThiHanh.webp',150000,NULL,NULL),(6,'phamthiquynh@gmail.com','Phạm Thị Quỳnh',0,'Tư vấn tâm lý','/images/pDoctors/BSPhamThiQuynh.webp',150000,NULL,NULL),(7,'phamthihongnhung@gmail.com','Phạm Thị Hồng Nhung',0,'Da liễu','/images/pDoctors/BSPhamThiHongNhung.webp',150000,NULL,NULL),(8,'buinguyenthucdoan@gmail.com','Bùi Nguyễn Thục Đoan',0,'Da liễu','/images/pDoctors/BSBuiNguyenThucDoan.webp',150000,NULL,NULL),(9,'nguyenthoanh@gmail.com','Nguyễn Thọ Anh',0,'Nhi khoa','/images/pDoctors/BSNguyenThoAnh.webp',150000,NULL,NULL),(10,'nguyenhuyennhung@gmail.com','Nguyễn Huyền Nhung',0,'Nội dị ứng - Miễn dịch lâm sàng','/images/pDoctors/BSNguyenHuyenNhung.webp',150000,NULL,NULL),(11,'damnhatthanh@gmail.com','Đàm Nhật Thanh',0,'Tai Mũi Họng','/images/pDoctors/BSDamNhatThanh.webp',150000,NULL,NULL),(12,'leducviet@gmail.com','Lê Đức Việt',0,'Nội tim mạch','/images/pDoctors/BSLeDucViet.webp',150000,NULL,NULL),(13,'tranphuongthao@gmail.com','Trần Phương Thảo',0,'Nội tổng hợp','/images/pDoctors/BSTranPhuongThao.webp',150000,NULL,NULL),(14,'lethiphuongthao@gmail.com','Lê Thị Phương Thảo',0,'Tâm lý','/images/pDoctors/BSLeThiPhuongThao.webp',150000,NULL,NULL),(15,'thaidamdung@gmail.com','Thái Đàm Dũng',0,'Nội khoa','/images/pDoctors/BSThaiDamDung.webp',150000,NULL,NULL),(16,'nguyenthihoaian@gmail.com','Nguyễn Thị Hoài An',0,'Tai Mũi Họng','/images/pDoctors/BSNguyenThiHoaiAn.webp',150000,NULL,NULL),(17,'tranthithanhnho@gmail.com','Trần Thị Thanh Nho',0,'Da liễu','/images/pDoctors/BSTranThiThanhNho.webp',150000,NULL,NULL),(18,'buithiminhhue@gmail.com','Bùi Thị Minh Huệ',0,'Sản phụ khoa','/images/pDoctors/BSBuiThiMinhHue.webp',150000,NULL,NULL),(19,'maivansam@gmail.com','Mai Văn Sâm ',0,'Nội tiết','/images/pDoctors/BSMaiVanSam.webp',150000,NULL,NULL);
/*!40000 ALTER TABLE `doctors` ENABLE KEYS */;

--
-- Table structure for table `health_record`
--

DROP TABLE IF EXISTS `health_record`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `health_record` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `allergies` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `blood_pressure` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `blood_type` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `heart_rate` int DEFAULT NULL,
  `height` double DEFAULT NULL,
  `medical_history` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci,
  `medications` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `weight` double DEFAULT NULL,
  `user_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKrox6csgl290ya3ngejyyud18i` (`user_id`),
  CONSTRAINT `FKrox6csgl290ya3ngejyyud18i` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `health_record`
--

/*!40000 ALTER TABLE `health_record` DISABLE KEYS */;
/*!40000 ALTER TABLE `health_record` ENABLE KEYS */;

--
-- Table structure for table `health_service_dv`
--

DROP TABLE IF EXISTS `health_service_dv`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `health_service_dv` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `image_url` varchar(255) DEFAULT NULL,
  `price` int NOT NULL,
  `category` varchar(100) DEFAULT NULL,
  `location` varchar(255) DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `category_id` bigint DEFAULT NULL,
  `location_id` bigint DEFAULT NULL,
  `description` text,
  PRIMARY KEY (`id`),
  KEY `FKhtsytqw1qxl27m85ell6mndxu` (`category_id`),
  KEY `FKjrxtr3yvbn1giwcmmxtsfhypf` (`location_id`),
  CONSTRAINT `FKhtsytqw1qxl27m85ell6mndxu` FOREIGN KEY (`category_id`) REFERENCES `service_category` (`id`),
  CONSTRAINT `FKjrxtr3yvbn1giwcmmxtsfhypf` FOREIGN KEY (`location_id`) REFERENCES `service_location` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `health_service_dv`
--

/*!40000 ALTER TABLE `health_service_dv` DISABLE KEYS */;
INSERT INTO `health_service_dv` VALUES (1,'Khám Nội tại BV Đa khoa Bảo Sơn','https://isofhcare-backup.s3-ap-southeast-1.amazonaws.com/images/1(6)-jpg_26aa6709_c7ef_43c1_bbb8_7dcc7eab752c.png',200000,'Khám Bệnh','Hà Nội','2025-07-04 04:35:19','2025-07-06 15:59:45',1,1,'<p>Dịch vụ Khám Nội tại Bệnh viện Đa khoa Bảo Sơn thăm khám các bệnh lý về hô hấp, tim mạch, nội tiết, tiêu hoá, thần kinh... đáp ứng nhu cầu khám chữa bệnh của bệnh nhân.</p>\n<p>Được trang bị trang thiết bị y tế tiên tiến, với công nghệ hoàn toàn mới, đặc biệt với hệ thống máy móc xét nghiệm hiện đại và đa chức năng, bậc nhất Việt Nam, Bệnh viện Bảo Sơn luôn mang đến cho người bệnh những trải nghiệm dịch vụ tiện ích và chăm sóc sức khoẻ 1 cách toàn diện.</p>\n'),(2,'Khám Tai Mũi Họng với PGS Hoài An - BV An Việt','https://isofhcare-backup.s3-ap-southeast-1.amazonaws.com/images/bs-hoai-an---bv-an-viet-png_f4f7167e_6b2a_4fd8_834b_65bc42724ecd.png',400000,'Khám Bệnh','Hà Nội','2025-07-04 04:35:19','2025-07-06 15:59:45',1,1,'<p>Dịch vụ Khám Nội tại Bệnh viện Đa khoa Bảo Sơn thăm khám các bệnh lý về hô hấp, tim mạch, nội tiết, tiêu hoá, thần kinh... đáp ứng nhu cầu khám chữa bệnh của bệnh nhân.</p>\n<p>Được trang bị trang thiết bị y tế tiên tiến, với công nghệ hoàn toàn mới, đặc biệt với hệ thống máy móc xét nghiệm hiện đại và đa chức năng, bậc nhất Việt Nam, Bệnh viện Bảo Sơn luôn mang đến cho người bệnh những trải nghiệm dịch vụ tiện ích và chăm sóc sức khoẻ 1 cách toàn diện.</p>\n'),(3,'Khám Tai Mũi Họng Theo Yêu Cầu','https://isofhcare-backup.s3-ap-southeast-1.amazonaws.com/images/tai-mui-hong_ed3517cc_292b_417a_a60a_b9c854c1d0a3.jpg',100000,'Khám Bệnh','Hà Nội','2025-07-04 04:35:19','2025-07-06 15:59:45',1,1,'<p>Dịch vụ Khám Nội tại Bệnh viện Đa khoa Bảo Sơn thăm khám các bệnh lý về hô hấp, tim mạch, nội tiết, tiêu hoá, thần kinh... đáp ứng nhu cầu khám chữa bệnh của bệnh nhân.</p>\n<p>Được trang bị trang thiết bị y tế tiên tiến, với công nghệ hoàn toàn mới, đặc biệt với hệ thống máy móc xét nghiệm hiện đại và đa chức năng, bậc nhất Việt Nam, Bệnh viện Bảo Sơn luôn mang đến cho người bệnh những trải nghiệm dịch vụ tiện ích và chăm sóc sức khoẻ 1 cách toàn diện.</p>\n'),(4,'Bác sỹ khám Nội khoa tại nhà dưới 10km','https://ivie.vn/_next/static/logo_isc_big.png',500000,'Khám Tại Nhà','TP. Hồ Chí Minh','2025-07-04 04:35:19','2025-07-06 15:59:45',2,2,'<p>Dịch vụ Khám Nội tại Bệnh viện Đa khoa Bảo Sơn thăm khám các bệnh lý về hô hấp, tim mạch, nội tiết, tiêu hoá, thần kinh... đáp ứng nhu cầu khám chữa bệnh của bệnh nhân.</p>\n<p>Được trang bị trang thiết bị y tế tiên tiến, với công nghệ hoàn toàn mới, đặc biệt với hệ thống máy móc xét nghiệm hiện đại và đa chức năng, bậc nhất Việt Nam, Bệnh viện Bảo Sơn luôn mang đến cho người bệnh những trải nghiệm dịch vụ tiện ích và chăm sóc sức khoẻ 1 cách toàn diện.</p>\n'),(5,'Bác sỹ khám Nội khoa tại nhà dưới 20km','https://ivie.vn/_next/static/logo_isc_big.png',700000,'Khám Tại Nhà','TP. Hồ Chí Minh','2025-07-04 04:35:19','2025-07-06 15:59:45',2,2,'<p>Dịch vụ Khám Nội tại Bệnh viện Đa khoa Bảo Sơn thăm khám các bệnh lý về hô hấp, tim mạch, nội tiết, tiêu hoá, thần kinh... đáp ứng nhu cầu khám chữa bệnh của bệnh nhân.</p>\n<p>Được trang bị trang thiết bị y tế tiên tiến, với công nghệ hoàn toàn mới, đặc biệt với hệ thống máy móc xét nghiệm hiện đại và đa chức năng, bậc nhất Việt Nam, Bệnh viện Bảo Sơn luôn mang đến cho người bệnh những trải nghiệm dịch vụ tiện ích và chăm sóc sức khoẻ 1 cách toàn diện.</p>\n'),(6,'Test nhanh ADENO','https://isofhcare-backup.s3-ap-southeast-1.amazonaws.com/images/virusadeno-gay-viem-gan-bi-an-1663256794120850290854-1663256814055676862614_3c8fd813_860a_4bb2_89e3_bca1a799943c.png',200000,'Khám Tại Nhà','TP. Hồ Chí Minh','2025-07-04 04:35:19','2025-07-06 15:59:45',3,2,'<p>Dịch vụ Khám Nội tại Bệnh viện Đa khoa Bảo Sơn thăm khám các bệnh lý về hô hấp, tim mạch, nội tiết, tiêu hoá, thần kinh... đáp ứng nhu cầu khám chữa bệnh của bệnh nhân.</p>\n<p>Được trang bị trang thiết bị y tế tiên tiến, với công nghệ hoàn toàn mới, đặc biệt với hệ thống máy móc xét nghiệm hiện đại và đa chức năng, bậc nhất Việt Nam, Bệnh viện Bảo Sơn luôn mang đến cho người bệnh những trải nghiệm dịch vụ tiện ích và chăm sóc sức khoẻ 1 cách toàn diện.</p>\n'),(7,'Xét nghiệm NIPT - PK Sản phụ khoa 43 Nguyễn Khang','https://isofhcare-backup.s3-ap-southeast-1.amazonaws.com/images/unnamed-(7)-jpg_0cd97aa7_f50f_4d75_b62a_a101f47a8179.png',2700000,'Khám Nhi & Sàng lọc sơ sinh','Đà Nẵng','2025-07-04 04:35:19','2025-07-06 15:59:45',3,3,'<p>Dịch vụ Khám Nội tại Bệnh viện Đa khoa Bảo Sơn thăm khám các bệnh lý về hô hấp, tim mạch, nội tiết, tiêu hoá, thần kinh... đáp ứng nhu cầu khám chữa bệnh của bệnh nhân.</p>\n<p>Được trang bị trang thiết bị y tế tiên tiến, với công nghệ hoàn toàn mới, đặc biệt với hệ thống máy móc xét nghiệm hiện đại và đa chức năng, bậc nhất Việt Nam, Bệnh viện Bảo Sơn luôn mang đến cho người bệnh những trải nghiệm dịch vụ tiện ích và chăm sóc sức khoẻ 1 cách toàn diện.</p>\n'),(8,'Khám Răng Hàm Mặt & Mắt theo yêu cầu','https://isofhcare-backup.s3-ap-southeast-1.amazonaws.com/images/rang-ham-mat_2572b592_782a_4358_8392_e2e96ecf7b78.jpg',100000,'Khám BệnhKhám Bệnh','Đà Nẵng','2025-07-04 04:35:19','2025-07-06 15:59:45',1,3,'<p>Dịch vụ Khám Nội tại Bệnh viện Đa khoa Bảo Sơn thăm khám các bệnh lý về hô hấp, tim mạch, nội tiết, tiêu hoá, thần kinh... đáp ứng nhu cầu khám chữa bệnh của bệnh nhân.</p>\n<p>Được trang bị trang thiết bị y tế tiên tiến, với công nghệ hoàn toàn mới, đặc biệt với hệ thống máy móc xét nghiệm hiện đại và đa chức năng, bậc nhất Việt Nam, Bệnh viện Bảo Sơn luôn mang đến cho người bệnh những trải nghiệm dịch vụ tiện ích và chăm sóc sức khoẻ 1 cách toàn diện.</p>\n'),(9,'Gói chăm sóc người cao tuổi tại nhà - 1 năm','https://isofhcare-backup.s3-ap-southeast-1.amazonaws.com/images/tai-xuong_d5d27ed6_faf1_4d5d_a1f3_495c9ea2ec8c.jpg',21600000,'Khám Tại Nhà','Đà Nẵng','2025-07-04 04:35:19','2025-07-06 15:59:45',2,3,'<p>Dịch vụ Khám Nội tại Bệnh viện Đa khoa Bảo Sơn thăm khám các bệnh lý về hô hấp, tim mạch, nội tiết, tiêu hoá, thần kinh... đáp ứng nhu cầu khám chữa bệnh của bệnh nhân.</p>\n<p>Được trang bị trang thiết bị y tế tiên tiến, với công nghệ hoàn toàn mới, đặc biệt với hệ thống máy móc xét nghiệm hiện đại và đa chức năng, bậc nhất Việt Nam, Bệnh viện Bảo Sơn luôn mang đến cho người bệnh những trải nghiệm dịch vụ tiện ích và chăm sóc sức khoẻ 1 cách toàn diện.</p>\n'),(10,'Xét nghiệm Virus ADENO','https://isofhcare-backup.s3-ap-southeast-1.amazonaws.com/images/20200108_xet-nghiem-aslo-01_4a515edb_2056_415f_9a2e_4f9d94deaf15.jpg',1100000,'Khám Nhi & Sàng lọc sơ sinhKhám Nhi & Sàng lọc sơ sinh','Hà Nội','2025-07-04 04:35:19','2025-07-06 15:59:45',3,1,'<p>Dịch vụ Khám Nội tại Bệnh viện Đa khoa Bảo Sơn thăm khám các bệnh lý về hô hấp, tim mạch, nội tiết, tiêu hoá, thần kinh... đáp ứng nhu cầu khám chữa bệnh của bệnh nhân.</p>\n<p>Được trang bị trang thiết bị y tế tiên tiến, với công nghệ hoàn toàn mới, đặc biệt với hệ thống máy móc xét nghiệm hiện đại và đa chức năng, bậc nhất Việt Nam, Bệnh viện Bảo Sơn luôn mang đến cho người bệnh những trải nghiệm dịch vụ tiện ích và chăm sóc sức khoẻ 1 cách toàn diện.</p>\n'),(11,'Khám Nam khoa tại Bệnh viện An Việt','https://isofhcare-backup.s3-ap-southeast-1.amazonaws.com/images/kham-nam-khoa-isofhcare-jpg_a7ceaaf3_1f85_453e_824a_74382d72ff53.png',300000,'Nam Khoa','Hà Nội','2025-07-04 04:35:19','2025-07-06 15:59:45',4,1,'<p>Dịch vụ Khám Nội tại Bệnh viện Đa khoa Bảo Sơn thăm khám các bệnh lý về hô hấp, tim mạch, nội tiết, tiêu hoá, thần kinh... đáp ứng nhu cầu khám chữa bệnh của bệnh nhân.</p>\n<p>Được trang bị trang thiết bị y tế tiên tiến, với công nghệ hoàn toàn mới, đặc biệt với hệ thống máy móc xét nghiệm hiện đại và đa chức năng, bậc nhất Việt Nam, Bệnh viện Bảo Sơn luôn mang đến cho người bệnh những trải nghiệm dịch vụ tiện ích và chăm sóc sức khoẻ 1 cách toàn diện.</p>\n');
/*!40000 ALTER TABLE `health_service_dv` ENABLE KEYS */;

--
-- Table structure for table `pill_reminder`
--

DROP TABLE IF EXISTS `pill_reminder`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `pill_reminder` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `date` date DEFAULT NULL,
  `time` time(6) DEFAULT NULL,
  `cycle_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK78iqwngki2ajdo4m4mf8ondcp` (`cycle_id`),
  CONSTRAINT `FK78iqwngki2ajdo4m4mf8ondcp` FOREIGN KEY (`cycle_id`) REFERENCES `cycles` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=55 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pill_reminder`
--

/*!40000 ALTER TABLE `pill_reminder` DISABLE KEYS */;
INSERT INTO `pill_reminder` VALUES (1,'2025-06-25','19:08:00.000000',1),(2,'2025-06-26','19:08:00.000000',1),(3,'2025-06-27','19:08:00.000000',1),(4,'2025-06-28','19:08:00.000000',1),(5,'2025-06-29','19:08:00.000000',1),(6,'2025-06-30','19:08:00.000000',1),(7,'2025-07-01','19:08:00.000000',1),(8,'2025-07-02','19:08:00.000000',1),(9,'2025-07-03','19:08:00.000000',1),(10,'2025-07-04','19:08:00.000000',1),(11,'2025-07-05','19:08:00.000000',1),(12,'2025-07-06','19:08:00.000000',1),(13,'2025-07-07','19:08:00.000000',1),(14,'2025-07-08','19:08:00.000000',1),(15,'2025-07-09','19:08:00.000000',1),(16,'2025-07-10','19:08:00.000000',1),(17,'2025-07-11','19:08:00.000000',1),(18,'2025-07-12','19:08:00.000000',1),(19,'2025-07-13','19:08:00.000000',1),(20,'2025-07-14','19:08:00.000000',1),(21,'2025-07-15','19:08:00.000000',1),(22,'2025-06-19','20:00:00.000000',2),(23,'2025-06-20','20:00:00.000000',2),(24,'2025-06-21','20:00:00.000000',2),(25,'2025-06-22','20:00:00.000000',2),(26,'2025-06-23','20:00:00.000000',2),(27,'2025-06-24','20:00:00.000000',2),(28,'2025-06-25','20:00:00.000000',2),(29,'2025-06-26','20:00:00.000000',2),(30,'2025-06-27','20:00:00.000000',2),(31,'2025-06-28','20:00:00.000000',2),(32,'2025-06-29','20:00:00.000000',2),(33,'2025-06-30','20:00:00.000000',2),(34,'2025-07-01','20:00:00.000000',2),(35,'2025-07-02','20:00:00.000000',2),(36,'2025-07-03','20:00:00.000000',2),(37,'2025-07-04','20:00:00.000000',2),(38,'2025-07-05','20:00:00.000000',2),(39,'2025-07-06','20:00:00.000000',2),(40,'2025-07-07','20:00:00.000000',2),(41,'2025-07-08','20:00:00.000000',2),(42,'2025-07-09','20:00:00.000000',2),(43,'2025-07-10','20:00:00.000000',2),(44,'2025-07-11','20:00:00.000000',2),(45,'2025-07-12','20:00:00.000000',2),(46,'2025-07-13','20:00:00.000000',2),(47,'2025-07-14','20:00:00.000000',2),(48,'2025-07-15','20:00:00.000000',2),(49,'2025-07-16','20:00:00.000000',2),(50,'2025-07-17','20:00:00.000000',2),(51,'2025-07-18','20:00:00.000000',2),(52,'2025-07-19','20:00:00.000000',2),(53,'2025-07-20','20:00:00.000000',2),(54,'2025-07-21','20:00:00.000000',2);
/*!40000 ALTER TABLE `pill_reminder` ENABLE KEYS */;

--
-- Table structure for table `questions`
--

DROP TABLE IF EXISTS `questions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `questions` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `age` int DEFAULT NULL,
  `answered_at` datetime(6) DEFAULT NULL,
  `assigned_at` datetime(6) DEFAULT NULL,
  `closed` bit(1) DEFAULT NULL,
  `content` text,
  `created_at` datetime(6) DEFAULT NULL,
  `gender` varchar(255) DEFAULT NULL,
  `image_url` varchar(255) DEFAULT NULL,
  `rejected` bit(1) DEFAULT NULL,
  `rejected_reason` varchar(255) DEFAULT NULL,
  `specialization` varchar(255) DEFAULT NULL,
  `thank_count` int DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `assigned_doctor_id` bigint DEFAULT NULL,
  `user_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKbcr183vjtppdysfo8wemwnly0` (`assigned_doctor_id`),
  KEY `FKjoo8hp6d3gfwctr68dl2iaemj` (`user_id`),
  CONSTRAINT `FKbcr183vjtppdysfo8wemwnly0` FOREIGN KEY (`assigned_doctor_id`) REFERENCES `users` (`id`),
  CONSTRAINT `FKjoo8hp6d3gfwctr68dl2iaemj` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `questions`
--

/*!40000 ALTER TABLE `questions` DISABLE KEYS */;
INSERT INTO `questions` VALUES (1,24,'2025-07-01 12:38:50.429208',NULL,0x00,'Bs cho e hỏi con e được 4m thì cần bổ sung những gì k ạ','2025-07-01 12:20:19.379942','Nữ',NULL,0x00,NULL,'Nội nhi',0,'bổ sung',NULL,17),(2,1,'2025-07-01 20:20:19.440802',NULL,0x00,'Bé nhà cháu lúc 4, 5th có xét nghiệm triple test thì nồng độ afp cao nhưng bé sinh ra hình dạng bình thường thì có ảnh hưởng gì bên trong bé không ạ? Và bé có phải đi khám kiểm tra gì không để đảm bảo là cháu không bị dị tật gì không ạ?','2025-07-01 12:21:23.768535','Nam',NULL,0x00,NULL,'Nội nhi',1,'Triple test nồng độ afp cao',NULL,1),(3,30,NULL,NULL,0x00,'Chào bác sĩ ạ! Con em hơn 2 tuổi (30 tháng), cháu bị nổi nhọt trên da đầu đến hôm nay là khoảng nửa tháng, cách đây 10 ngày có đi khám ở bệnh viện huyện, đã xét nghiệm máu và được kê đơn thuốc uống nhưng đến hôm nay hiện trạng như ảnh đính kèm ạ. Mong được bác sĩ tư vấn ạ! Cảm ơn bác sĩ!','2025-07-02 03:41:48.490965','Nữ',NULL,0x00,NULL,'Nội nhi',0,'Nổi nhọt trên da đầu',NULL,18);
/*!40000 ALTER TABLE `questions` ENABLE KEYS */;

--
-- Table structure for table `reminder`
--

DROP TABLE IF EXISTS `reminder`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `reminder` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `message` varchar(255) DEFAULT NULL,
  `notified` bit(1) NOT NULL,
  `remind_at` datetime(6) DEFAULT NULL,
  `user_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `reminder`
--

/*!40000 ALTER TABLE `reminder` DISABLE KEYS */;
/*!40000 ALTER TABLE `reminder` ENABLE KEYS */;

--
-- Table structure for table `service_category`
--

DROP TABLE IF EXISTS `service_category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `service_category` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `description` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `service_category`
--

/*!40000 ALTER TABLE `service_category` DISABLE KEYS */;
INSERT INTO `service_category` VALUES (1,'Khám Bệnh','2025-07-04 04:36:09',NULL),(2,'Khám Tại Nhà','2025-07-04 04:36:09',NULL),(3,'Khám Nhi Và Sàng Lọc Sơ Sinh','2025-07-04 04:36:09',NULL),(4,'Nam Khoa','2025-07-04 04:36:09',NULL),(5,'Sản Phụ Khoa','2025-07-04 04:36:09',NULL),(6,'Tầm Soát Bệnh Truyền Nhiễm','2025-07-04 04:36:09',NULL);
/*!40000 ALTER TABLE `service_category` ENABLE KEYS */;

--
-- Table structure for table `service_location`
--

DROP TABLE IF EXISTS `service_location`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `service_location` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `address` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `service_location`
--

/*!40000 ALTER TABLE `service_location` DISABLE KEYS */;
INSERT INTO `service_location` VALUES (1,'Hà Nội','Số 1 Đại Cồ Việt, Hai Bà Trưng, Hà Nội','024-12345678','2025-07-04 04:36:27'),(2,'TP. Hồ Chí Minh','Số 123 Nguyễn Huệ, Quận 1, TP.HCM','028-87654321','2025-07-04 04:36:27'),(3,'Đà Nẵng','Số 456 Lê Duẩn, Hải Châu, Đà Nẵng','0236-11223344','2025-07-04 04:36:27');
/*!40000 ALTER TABLE `service_location` ENABLE KEYS */;

--
-- Table structure for table `test_orders`
--

DROP TABLE IF EXISTS `test_orders`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `test_orders` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `appointment_time` datetime(6) DEFAULT NULL,
  `birth_date` date DEFAULT NULL,
  `booking_time` datetime(6) DEFAULT NULL,
  `customer_code` varchar(255) DEFAULT NULL,
  `doctor_signature` varchar(255) DEFAULT NULL,
  `full_name` varchar(255) DEFAULT NULL,
  `gender` varchar(255) DEFAULT NULL,
  `location` varchar(255) DEFAULT NULL,
  `note` varchar(255) DEFAULT NULL,
  `pdf_path` varchar(255) DEFAULT NULL,
  `price` int DEFAULT NULL,
  `result` varchar(255) DEFAULT NULL,
  `result_date` date DEFAULT NULL,
  `result_time` datetime(6) DEFAULT NULL,
  `sample_collected_time` datetime(6) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `test_type` varchar(255) DEFAULT NULL,
  `user_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `test_orders`
--

/*!40000 ALTER TABLE `test_orders` DISABLE KEYS */;
INSERT INTO `test_orders` VALUES (1,'2025-08-11 02:30:00.000000',NULL,'2025-07-08 09:04:27.936909','IVIE-477248',NULL,'Duy Cường',NULL,'Bệnh viện Bệnh Nhiệt Đới - 12 Nguyễn Thị Minh Khai, Phường 6, Quận 3, TP.HCM',NULL,NULL,500000,NULL,NULL,NULL,NULL,'BOOKED','HIV',1);
/*!40000 ALTER TABLE `test_orders` ENABLE KEYS */;

--
-- Table structure for table `thank_history`
--

DROP TABLE IF EXISTS `thank_history`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `thank_history` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `question_id` bigint NOT NULL,
  `user_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKfaw3gnp72m7q88mpyd2xgqqse` (`question_id`,`user_id`),
  KEY `FK6ywrl9t446ii4ame1dy3f3ch5` (`user_id`),
  CONSTRAINT `FK6ywrl9t446ii4ame1dy3f3ch5` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`),
  CONSTRAINT `FK8ay9hawwp4wo0l6w922nlqc1v` FOREIGN KEY (`question_id`) REFERENCES `questions` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `thank_history`
--

/*!40000 ALTER TABLE `thank_history` DISABLE KEYS */;
INSERT INTO `thank_history` VALUES (1,2,1);
/*!40000 ALTER TABLE `thank_history` ENABLE KEYS */;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `email` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
  `name` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
  `password` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
  `role` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `specialization` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,'duycuong@gmail.com','Duy Cường','duy123','USER',NULL),(2,'thanh91@gmail.com','Đức Thành','thanh22','USER',NULL),(3,'nguyenhoa@gmail.com','Nguyễn Hòa','Hoa1234!','USER',NULL),(4,'tranphuong@gmail.com','Trần Phương','Phuong5678','USER',NULL),(5,'leanh@gmail.com','Lê Anh','Anh91011','USER',NULL),(6,'phammy@gmail.com','Phạm My','12345','USER',NULL),(7,'hoangkim@gmail.com','Hoàng Kim','Trang2021','USER',NULL),(8,'ngoclan@gmail.com','Ngọc Lan ','12345','USER',NULL),(9,'vuongly@gmail.com','Vương Ly','12345','USER',NULL),(10,'doantrang@gmail.com','Đoàn Trang','12345','USER',NULL),(11,'nguyenchi@gmail.com','Nguyễn Chi','12345','USER',NULL),(12,'nguyenminh@gmail.com','Nguyễn Minh','Minh1234!','USER',NULL),(13,'hoanglong@gmail.com','anh long dragon','Long1415','USER',NULL),(14,'jing@gmail.com','jing','12345','DOCTOR','Nội nhi'),(15,'my@gmail.com','Thanh My','12345','USER',NULL),(16,'chi@gmail.com','quynhchi','12345','USER',NULL),(17,'linh@gmail.com','Trúc Linh','12345','USER',NULL),(18,'lan@gmail.com','Ngọc Lan ','12345','USER',NULL),(19,'hanh@gmail.com','BS DƯƠNG THỊ HẠNH','12345','DOCTOR','Chuyên khoa Phụ khoa'),(20,'tri11@gmail.com','Dũng Trí','12345','USER',NULL),(21,'duc@gmail.com','Lê Trí Đức','1111','USER',NULL);
/*!40000 ALTER TABLE `users` ENABLE KEYS */;

--
-- Dumping routines for database 'HealthMate'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-07-08 19:44:34
