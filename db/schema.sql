-- MySQL dump 10.13  Distrib 8.0.42, for Win64 (x86_64)
--
-- Host: localhost    Database: mediconnect
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
-- Table structure for table `admin`
--

DROP TABLE IF EXISTS `admin`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `admin` (
  `admin_id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(50) DEFAULT NULL,
  `password` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`admin_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `admin`
--

LOCK TABLES `admin` WRITE;
/*!40000 ALTER TABLE `admin` DISABLE KEYS */;
INSERT INTO `admin` VALUES (1,'aziz','xyz'),(2,'Arka','sos');
/*!40000 ALTER TABLE `admin` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `appointment`
--

DROP TABLE IF EXISTS `appointment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `appointment` (
  `appointment_id` int NOT NULL AUTO_INCREMENT,
  `d_id` int NOT NULL,
  `p_id` int NOT NULL,
  `a_id` int NOT NULL,
  `appointment_date` date DEFAULT NULL,
  `Status` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`appointment_id`),
  KEY `d_id` (`d_id`),
  KEY `p_id` (`p_id`),
  KEY `a_id` (`a_id`),
  CONSTRAINT `appointment_ibfk_1` FOREIGN KEY (`d_id`) REFERENCES `doctor` (`doctor_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `appointment_ibfk_2` FOREIGN KEY (`p_id`) REFERENCES `patient` (`patient_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `appointment_ibfk_3` FOREIGN KEY (`a_id`) REFERENCES `assistant` (`assistant_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `appointment`
--

LOCK TABLES `appointment` WRITE;
/*!40000 ALTER TABLE `appointment` DISABLE KEYS */;
INSERT INTO `appointment` VALUES (1,1,2,1,'2025-10-03','confirmed'),(4,1,3,1,'2025-08-19','Confirmed'),(6,6,2,6,'2025-08-30','Confirmed');
/*!40000 ALTER TABLE `appointment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `assistant`
--

DROP TABLE IF EXISTS `assistant`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `assistant` (
  `assistant_id` int NOT NULL AUTO_INCREMENT,
  `d_id` int NOT NULL,
  `name` varchar(50) NOT NULL,
  `contact_number` varchar(50) DEFAULT NULL,
  `email` varchar(50) DEFAULT NULL,
  `password` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`assistant_id`),
  KEY `d_id` (`d_id`),
  CONSTRAINT `assistant_ibfk_1` FOREIGN KEY (`d_id`) REFERENCES `doctor` (`doctor_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=31 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `assistant`
--

LOCK TABLES `assistant` WRITE;
/*!40000 ALTER TABLE `assistant` DISABLE KEYS */;
INSERT INTO `assistant` VALUES (1,1,'Faizul','01930309821','faizul@gmail.com','yes1'),(2,2,'Sadia Akter','01711223344','sadia.akter@gmail.com','pass2'),(3,3,'Rakib Hossain','01822334455','rakib.hossain@yahoo.com','pass3'),(4,4,'Nishat Jahan','01933445566','nishat.jahan@gmail.com','pass4'),(5,5,'Tamim Rahman','01644556677','tamim.rahman@hotmail.com','pass5'),(6,6,'Moushumi Akter','01755667788','moushumi.akter@gmail.com','pass6'),(7,7,'Kamrul Islam','01566778899','kamrul.islam@yahoo.com','pass7'),(8,8,'Shamima Sultana','01377889900','shamima.sultana@gmail.com','pass8'),(9,9,'Fahim Chowdhury','01888990011','fahim.chowdhury@gmail.com','pass9'),(11,31,'Rafiq Ahmed','01810101010','rafiq@med.com','asst101'),(12,32,'Sadia Akter','01820202020','sadiaa@med.com','asst102'),(13,33,'Tanvir Rahman','01830303030','tanvirr@med.com','asst103'),(14,34,'Mithila Chowdhury','01840404040','mithilac@med.com','asst104'),(15,35,'Kamrul Hasan','01850505050','kamrulh@med.com','asst105'),(16,36,'Farzana Karim','01860606060','farzanak@med.com','asst106'),(17,37,'Imran Sultana','01870707070','imrans@med.com','asst107'),(18,38,'Jannat Hossain','01880808080','jannath@med.com','asst108'),(19,39,'Omar Parvin','01890909090','omarp@med.com','asst109'),(20,40,'Shahida Binte','01811112222','shahidab@med.com','asst110'),(21,41,'Mehedi Khan','01822223333','mehedik@med.com','asst111'),(22,42,'Tahmina Rahman','01833334444','tahminar@med.com','asst112'),(23,43,'Nayeem Akhter','01844445555','nayeema@med.com','asst113'),(24,44,'Rasel Chowdhury','01855556666','raselc@med.com','asst114'),(25,45,'Lubna Haque','01866667777','lubnah@med.com','asst115'),(26,46,'Fahim Karim','01877778888','fahimk@med.com','asst116'),(27,47,'Sadia Alam','01888889999','sadiaa2@med.com','asst117'),(28,48,'Tanvir Chowdhury','01899990000','tanvirc@med.com','asst118'),(29,49,'Mithila Hasan','01812121212','mithilah@med.com','asst119'),(30,50,'Kamrul Akhter','01813131313','kamrula@med.com','asst120');
/*!40000 ALTER TABLE `assistant` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `doctor`
--

DROP TABLE IF EXISTS `doctor`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `doctor` (
  `doctor_id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(20) NOT NULL,
  `specialization` varchar(50) NOT NULL,
  `qualification` varchar(50) DEFAULT NULL,
  `fees` decimal(6,2) DEFAULT NULL,
  `contact_number` varchar(20) DEFAULT NULL,
  `email` varchar(20) DEFAULT NULL,
  `capacity_per_day` int DEFAULT NULL,
  `emergency_slots_per_day` int DEFAULT NULL,
  `password` varchar(40) DEFAULT NULL,
  PRIMARY KEY (`doctor_id`)
) ENGINE=InnoDB AUTO_INCREMENT=51 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `doctor`
--

LOCK TABLES `doctor` WRITE;
/*!40000 ALTER TABLE `doctor` DISABLE KEYS */;
INSERT INTO `doctor` VALUES (1,'Dr. Mahbub','Medicine','FCPS',1000.00,'01768876682','mahbub@gmail.com',50,3,'doc123'),(2,'Dr. Jan','Neurology','MBBS, DM',600.00,'01987654321','jan@gmail.com',25,5,'doc456'),(3,'Dr. Ayesha Rahman','Cardiology','MBBS, MD',1000.00,'01812345678','ayesha.@gmail.com',45,15,'cardio789'),(4,'Dr. Tanvir Alam','Orthopedics','MBBS, MS',750.00,'01922334455','tanvir.@yahoo.com',38,10,'ortho321'),(5,'Dr. Nusrat Jahan','Dermatology','MBBS, DDVL',500.00,'01733445566','nusrat.@gmail.com',34,7,'derma555'),(6,'Dr. Imran Hossain','Pediatrics','MBBS, DCH',650.00,'01899887766','imran.n@hotmail.com',40,12,'peds111'),(7,'Dr. Farhana Akter','Gynecology','MBBS, FCPS',900.00,'01611223344','farhanr@gmail.com',42,14,'gyn999'),(8,'Dr. Salman Kabir','Psychiatry','MBBS, MPhil',550.00,'01599887722','salman@gmail.com',37,8,'psych333'),(9,'Dr. Rafiq Chowdhury','ENT','MBBS, DLO',700.00,'01955667788','rafiq@gmail.com',48,18,'ent777'),(31,'Dr. Arif Hossain','Cardiologist','MBBS, FCPS',800.00,'01711112233','arif@med.com',25,5,'doc101'),(32,'Dr. Nusrat Jahan','Dermatologist','MBBS, MD',600.00,'01822223344','nusrat@med.com',20,4,'doc102'),(33,'Dr. Fahim Ahmed','Neurologist','MBBS, FCPS',900.00,'01933334455','fahim@med.com',18,3,'doc103'),(34,'Dr. Shamima Akter','Gynecologist','MBBS, DGO',700.00,'01544445566','shamima@med.com',22,4,'doc104'),(35,'Dr. Rasel Karim','Orthopedic','MBBS, MS',750.00,'01655556677','rasel@med.com',20,5,'doc105'),(36,'Dr. Sadia Rahman','Pediatrician','MBBS, FCPS',650.00,'01766667788','sadia@med.com',30,6,'doc106'),(37,'Dr. Tanvir Hasan','Oncologist','MBBS, MD',1000.00,'01877778899','tanvir@med.com',15,2,'doc107'),(38,'Dr. Mithila Sultana','Psychiatrist','MBBS, FCPS',700.00,'01988889900','mithila@med.com',20,3,'doc108'),(39,'Dr. Kamrul Alam','ENT Specialist','MBBS, MS',550.00,'01399990011','kamrul@med.com',25,5,'doc109'),(40,'Dr. Farzana Haque','Ophthalmologist','MBBS, MS',600.00,'01411112222','farzana@med.com',18,4,'doc110'),(41,'Dr. Nayeem Chowdhury','Cardiologist','MBBS, FCPS',950.00,'01522223333','nayeem@med.com',20,4,'doc111'),(42,'Dr. Lubna Akhter','Endocrinologist','MBBS, MD',800.00,'01633334444','lubna@med.com',22,5,'doc112'),(43,'Dr. Imran Kabir','Urologist','MBBS, FCPS',850.00,'01744445555','imran@med.com',19,3,'doc113'),(44,'Dr. Sumaiya Binte','Nephrologist','MBBS, MD',700.00,'01855556666','sumaiya@med.com',20,4,'doc114'),(45,'Dr. Rafiq Islam','General Surgeon','MBBS, MS',750.00,'01966667777','rafiq@med.com',23,5,'doc115'),(46,'Dr. Jannat Ferdous','Rheumatologist','MBBS, FCPS',900.00,'01377778888','jannat@med.com',18,3,'doc116'),(47,'Dr. Omar Faruk','Gastroenterologist','MBBS, MD',950.00,'01488889999','omar@med.com',20,4,'doc117'),(48,'Dr. Shahida Parvin','Pulmonologist','MBBS, MD',800.00,'01599990000','shahida@med.com',22,5,'doc118'),(49,'Dr. Mehedi Hasan','Hematologist','MBBS, FCPS',1000.00,'01611112222','mehedi@med.com',15,2,'doc119'),(50,'Dr. Tahmina Akter','Allergist','MBBS, MD',650.00,'01722223333','tahmina@med.com',24,6,'doc120');
/*!40000 ALTER TABLE `doctor` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `emergency_request`
--

DROP TABLE IF EXISTS `emergency_request`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `emergency_request` (
  `request_id` int NOT NULL AUTO_INCREMENT,
  `p_id` int NOT NULL,
  `a_id` int NOT NULL,
  `d_id` int NOT NULL,
  `symptoms` varchar(500) DEFAULT NULL,
  `request_date` date DEFAULT NULL,
  `tentative_date` date DEFAULT NULL,
  `status` varchar(20) DEFAULT NULL,
  `response_seen` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`request_id`),
  KEY `p_id` (`p_id`),
  KEY `d_id` (`d_id`),
  KEY `a_id` (`a_id`),
  CONSTRAINT `emergency_request_ibfk_1` FOREIGN KEY (`p_id`) REFERENCES `patient` (`patient_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `emergency_request_ibfk_2` FOREIGN KEY (`d_id`) REFERENCES `doctor` (`doctor_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `emergency_request_ibfk_3` FOREIGN KEY (`a_id`) REFERENCES `assistant` (`assistant_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `emergency_request`
--

LOCK TABLES `emergency_request` WRITE;
/*!40000 ALTER TABLE `emergency_request` DISABLE KEYS */;
INSERT INTO `emergency_request` VALUES (2,2,3,3,'sudden chest pain','2025-09-04',NULL,'Pending','Not Seen'),(3,2,4,4,'high fever','2025-09-17','2025-09-18','proposed','seen'),(4,2,1,1,'High fever','2025-09-17',NULL,'Pending','Not Seen');
/*!40000 ALTER TABLE `emergency_request` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `handle`
--

DROP TABLE IF EXISTS `handle`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `handle` (
  `a_id` int NOT NULL,
  `ad_id` int NOT NULL,
  PRIMARY KEY (`a_id`,`ad_id`),
  KEY `ad_id` (`ad_id`),
  CONSTRAINT `handle_ibfk_1` FOREIGN KEY (`ad_id`) REFERENCES `admin` (`admin_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `handle_ibfk_2` FOREIGN KEY (`a_id`) REFERENCES `assistant` (`assistant_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `handle`
--

LOCK TABLES `handle` WRITE;
/*!40000 ALTER TABLE `handle` DISABLE KEYS */;
/*!40000 ALTER TABLE `handle` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `location`
--

DROP TABLE IF EXISTS `location`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `location` (
  `d_id` int NOT NULL,
  `hospital` varchar(50) NOT NULL,
  `city` varchar(50) NOT NULL,
  `start_time` time NOT NULL,
  `End_time` time NOT NULL,
  PRIMARY KEY (`d_id`,`hospital`,`city`,`start_time`,`End_time`),
  CONSTRAINT `location_ibfk_3` FOREIGN KEY (`d_id`) REFERENCES `doctor` (`doctor_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `location`
--

LOCK TABLES `location` WRITE;
/*!40000 ALTER TABLE `location` DISABLE KEYS */;
INSERT INTO `location` VALUES (1,'parkview','Chittagong','18:00:00','21:00:00'),(2,'bellview','Chittagong','17:00:00','21:00:00'),(3,'evercare','Chittagong','19:00:00','22:00:00'),(4,'labaid','Dhaka','11:00:00','15:00:00'),(5,'parkview','Chittagong','12:00:00','16:00:00'),(6,'labaid','Dhaka','11:00:00','15:00:00'),(7,'apollo','Dhaka','15:00:00','18:30:00'),(8,'chevron','Chittagong','18:00:00','21:30:00'),(9,'chevron','Chittagong','19:00:00','22:00:00'),(31,'Square Hospital','Dhaka','09:00:00','17:00:00'),(32,'United Hospital','Dhaka','10:00:00','18:00:00'),(33,'Apollo Hospital','Chittagong','08:30:00','16:30:00'),(34,'Evercare Hospital','Dhaka','09:00:00','17:00:00'),(35,'BIRDEM Hospital','Dhaka','08:00:00','16:00:00'),(36,'Ibn Sina Hospital','Khulna','09:00:00','17:00:00'),(37,'LabAid Hospital','Dhaka','10:00:00','18:00:00'),(38,'Square Hospital','Dhaka','09:30:00','17:30:00'),(39,'United Hospital','Sylhet','08:00:00','16:00:00'),(40,'Apollo Hospital','Chittagong','09:00:00','17:00:00'),(41,'Evercare Hospital','Dhaka','10:00:00','18:00:00'),(42,'BIRDEM Hospital','Dhaka','08:00:00','16:00:00'),(43,'Ibn Sina Hospital','Khulna','09:00:00','17:00:00'),(44,'LabAid Hospital','Dhaka','10:00:00','18:00:00'),(45,'Square Hospital','Dhaka','09:30:00','17:30:00'),(46,'United Hospital','Sylhet','08:00:00','16:00:00'),(47,'Apollo Hospital','Chittagong','09:00:00','17:00:00'),(48,'Evercare Hospital','Dhaka','10:00:00','18:00:00'),(49,'BIRDEM Hospital','Dhaka','08:00:00','16:00:00'),(50,'Ibn Sina Hospital','Khulna','09:00:00','17:00:00');
/*!40000 ALTER TABLE `location` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `manage`
--

DROP TABLE IF EXISTS `manage`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `manage` (
  `d_id` int NOT NULL,
  `ad_id` int NOT NULL,
  PRIMARY KEY (`d_id`,`ad_id`),
  KEY `ad_id` (`ad_id`),
  CONSTRAINT `manage_ibfk_1` FOREIGN KEY (`ad_id`) REFERENCES `admin` (`admin_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `manage_ibfk_2` FOREIGN KEY (`d_id`) REFERENCES `doctor` (`doctor_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `manage`
--

LOCK TABLES `manage` WRITE;
/*!40000 ALTER TABLE `manage` DISABLE KEYS */;
/*!40000 ALTER TABLE `manage` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `patient`
--

DROP TABLE IF EXISTS `patient`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `patient` (
  `patient_id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  `date_of_birth` date DEFAULT NULL,
  `contact_number` varchar(50) DEFAULT NULL,
  `email` varchar(50) DEFAULT NULL,
  `password` varchar(50) NOT NULL,
  PRIMARY KEY (`patient_id`)
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `patient`
--

LOCK TABLES `patient` WRITE;
/*!40000 ALTER TABLE `patient` DISABLE KEYS */;
INSERT INTO `patient` VALUES (2,'Naveed','2004-05-09','01617793506','u2204016@student.cuet.ac.bd','abcd'),(3,'arka','2003-08-06','0183344711','arka@gmail.com','sos'),(4,'Rahat','2003-09-24','01619945703','rahat@gmail.com','meow'),(5,'Aziz','2003-08-06','019','a@gmail.com','kdb'),(6,'A. Nafees','2002-08-15','01817793509','nafees@yahoo.com','anaf'),(7,'kevin','2001-09-13','01819955771','kevin@gmail.com','2n2n'),(8,'Arman Hossain','1995-03-12','01760010001','arman@med.com','pat10'),(9,'Sumaiya Akter','1998-07-25','01860020002','sumaiya@med.com','pat11'),(10,'Shafiqul Islam','1985-11-02','01960030003','shafiq@med.com','pat12'),(11,'Farzana Yasmin','1992-02-14','01660040004','farzana@med.com','pat13'),(12,'Mokbul Hossain','1978-05-20','01560050005','mokbul@med.com','pat14'),(13,'Rokeya Begum','1989-08-30','01760060006','rokeya@med.com','pat15'),(14,'Tanvir Ahmed','1996-12-10','01860070007','tanvir@med.com','pat16'),(15,'Shaila Parvin','2000-01-19','01960080008','shaila@med.com','pat17'),(16,'Masud Karim','1984-09-23','01660090009','masud@med.com','pat18'),(17,'Nusrat Jahan','1997-06-05','01560100010','nusrat@med.com','pat19'),(18,'Aminul Islam','1981-10-15','01760110011','aminul@med.com','pat20'),(19,'Sharmin Akter','1994-04-18','01860120012','sharmin@med.com','pat21'),(20,'Hasibur Rahman','1988-07-28','01960130013','hasibur@med.com','pat22'),(21,'Lubna Chowdhury','1993-12-09','01660140014','lubna@med.com','pat23'),(22,'Jamil Hossain','1979-03-22','01560150015','jamil@med.com','pat24'),(23,'Shirin Jahan','1991-11-11','01760160016','shirin@med.com','pat25'),(24,'Rafiqul Bari','1986-01-25','01860170017','rafiqb@med.com','pat26'),(25,'Nazia Sultana','1999-09-01','01960180018','nazia@med.com','pat27'),(26,'Muntasir Rahman','1990-05-17','01660190019','muntasir@med.com','pat28'),(27,'Salma Khatun','1982-02-02','01560200020','salma@med.com','pat29');
/*!40000 ALTER TABLE `patient` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `primary_checkup`
--

DROP TABLE IF EXISTS `primary_checkup`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `primary_checkup` (
  `patient_id` int DEFAULT NULL,
  `doctor_id` int DEFAULT NULL,
  `assistant_id` int DEFAULT NULL,
  `weight` decimal(5,2) DEFAULT NULL,
  `age` int DEFAULT NULL,
  `blood_pressure` varchar(20) DEFAULT NULL,
  KEY `patient_id` (`patient_id`),
  KEY `doctor_id` (`doctor_id`),
  KEY `assistant_id` (`assistant_id`),
  CONSTRAINT `primary_checkup_ibfk_1` FOREIGN KEY (`patient_id`) REFERENCES `patient` (`patient_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `primary_checkup_ibfk_2` FOREIGN KEY (`doctor_id`) REFERENCES `doctor` (`doctor_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `primary_checkup_ibfk_3` FOREIGN KEY (`assistant_id`) REFERENCES `assistant` (`assistant_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `primary_checkup`
--

LOCK TABLES `primary_checkup` WRITE;
/*!40000 ALTER TABLE `primary_checkup` DISABLE KEYS */;
INSERT INTO `primary_checkup` VALUES (2,1,1,52.00,21,'120/80');
/*!40000 ALTER TABLE `primary_checkup` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `record`
--

DROP TABLE IF EXISTS `record`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `record` (
  `record_id` int NOT NULL AUTO_INCREMENT,
  `p_id` int NOT NULL,
  `d_id` int NOT NULL,
  `visit_date` date NOT NULL,
  `diagnosis` varchar(255) DEFAULT NULL,
  `treatment` varchar(255) DEFAULT NULL,
  `notes` text,
  `prescribed_test` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`record_id`),
  KEY `fk_patient` (`p_id`),
  KEY `fk_doctor` (`d_id`),
  CONSTRAINT `fk_doctor` FOREIGN KEY (`d_id`) REFERENCES `doctor` (`doctor_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_patient` FOREIGN KEY (`p_id`) REFERENCES `patient` (`patient_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `record`
--

LOCK TABLES `record` WRITE;
/*!40000 ALTER TABLE `record` DISABLE KEYS */;
INSERT INTO `record` VALUES (1,5,1,'2025-09-01','Common Cold','Rest and hydration','Patient advised to drink plenty of fluids','CBC, CRP'),(2,2,1,'2025-09-02','Flu','Antiviral medication','Monitor temperature and hydration','CBC, Influenza Test'),(3,3,2,'2025-09-03','Migraine','Painkillers','Avoid bright lights, stress management','MRI, Blood Pressure'),(4,5,2,'2025-09-04','Stomach Ache','Antacids','Avoid spicy food','Ultrasound, CBC'),(5,2,3,'2025-09-05','Back Pain','Physiotherapy','Maintain posture','X-ray, MRI'),(6,3,1,'2025-09-06','Hypertension','Lifestyle changes and meds','Monitor blood pressure daily','BP Check, ECG'),(7,5,3,'2025-09-06','Diabetes Checkup','Diet modification','Monitor blood sugar','Fasting Blood Sugar, HbA1c');
/*!40000 ALTER TABLE `record` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `search`
--

DROP TABLE IF EXISTS `search`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `search` (
  `d_id` int NOT NULL,
  `p_id` int NOT NULL,
  PRIMARY KEY (`d_id`,`p_id`),
  KEY `p_id` (`p_id`),
  CONSTRAINT `search_ibfk_1` FOREIGN KEY (`d_id`) REFERENCES `doctor` (`doctor_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `search_ibfk_2` FOREIGN KEY (`p_id`) REFERENCES `patient` (`patient_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `search`
--

LOCK TABLES `search` WRITE;
/*!40000 ALTER TABLE `search` DISABLE KEYS */;
/*!40000 ALTER TABLE `search` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `test`
--

DROP TABLE IF EXISTS `test`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `test` (
  `test_id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  `hospital_name` varchar(50) DEFAULT NULL,
  `fee` decimal(8,2) DEFAULT NULL,
  `instructions` varchar(250) DEFAULT NULL,
  `test_time_slot` time DEFAULT NULL,
  PRIMARY KEY (`test_id`)
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `test`
--

LOCK TABLES `test` WRITE;
/*!40000 ALTER TABLE `test` DISABLE KEYS */;
INSERT INTO `test` VALUES (1,'Blood Test','Parkview',800.00,'Fast for 8 hours before the test','08:30:00'),(2,'MRI Scan','Evercare',5100.00,'Remove all metallic objects before test','09:00:00'),(3,'X-Ray','Chevron',1600.00,'wear loose clothing','15:15:00'),(6,'ECG','Square Hospital',600.00,'Avoid coffee before test','09:00:00'),(7,'Ultrasound','United Hospital',1200.00,'Drink water, do not urinate','10:00:00'),(8,'CT Scan','Ibn Sina Hospital',3000.00,'Fasting 6 hours','11:00:00'),(9,'Blood Sugar','Popular Hospital',300.00,'Overnight fasting required','08:00:00'),(10,'Liver Function Test','Evercare Hospital',1500.00,'No alcohol 24 hrs prior','09:30:00'),(11,'Thyroid Test','LabAid Hospital',1000.00,'Normal diet allowed','10:30:00'),(12,'Urine Test','Chattogram Med Centre',400.00,'Collect morning sample','09:00:00'),(13,'MRI Scan','Sylhet Women?s Hospital',5000.00,'Remove all metallic items','12:00:00'),(14,'X-Ray Chest','National Cancer Institute',800.00,'Wear loose clothing','13:00:00'),(15,'Echo Cardiogram','Kidney Foundation',2000.00,'No restrictions','14:00:00'),(16,'CBC','BIRDEM Hospital',500.00,'Normal diet allowed','09:00:00'),(17,'Vitamin D Test','Dhaka Med College',1200.00,'Fasting not needed','10:00:00'),(18,'Allergy Test','Rajshahi Med College',1800.00,'Stop antihistamines 48 hrs before','11:00:00'),(19,'Bone Density','Barisal Med College',2500.00,'No calcium 24 hrs before','12:00:00'),(20,'Renal Profile','Mymensingh Med College',1400.00,'Normal diet allowed','09:30:00'),(21,'HIV Test','Comilla Med Centre',1000.00,'Confidential testing','10:00:00'),(22,'COVID-19 PCR','Rangpur Med Centre',3000.00,'Nasopharyngeal swab','11:30:00'),(23,'Cholesterol Test','Dinajpur Med Centre',800.00,'Overnight fasting required','09:00:00'),(24,'Dengue NS1','Khulna Med Centre',1500.00,'Blood sample needed','10:30:00'),(25,'Malaria Test','Jessore Med Centre',700.00,'Blood sample needed','11:00:00');
/*!40000 ALTER TABLE `test` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `test_search`
--

DROP TABLE IF EXISTS `test_search`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `test_search` (
  `p_id` int NOT NULL,
  `t_id` int NOT NULL,
  PRIMARY KEY (`p_id`,`t_id`),
  KEY `t_id` (`t_id`),
  CONSTRAINT `test_search_ibfk_1` FOREIGN KEY (`p_id`) REFERENCES `patient` (`patient_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `test_search_ibfk_2` FOREIGN KEY (`t_id`) REFERENCES `test` (`test_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `test_search`
--

LOCK TABLES `test_search` WRITE;
/*!40000 ALTER TABLE `test_search` DISABLE KEYS */;
/*!40000 ALTER TABLE `test_search` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `test_update`
--

DROP TABLE IF EXISTS `test_update`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `test_update` (
  `ad_id` int NOT NULL,
  `t_id` int NOT NULL,
  PRIMARY KEY (`t_id`,`ad_id`),
  KEY `ad_id` (`ad_id`),
  CONSTRAINT `test_update_ibfk_1` FOREIGN KEY (`t_id`) REFERENCES `test` (`test_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `test_update_ibfk_2` FOREIGN KEY (`ad_id`) REFERENCES `admin` (`admin_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `test_update`
--

LOCK TABLES `test_update` WRITE;
/*!40000 ALTER TABLE `test_update` DISABLE KEYS */;
/*!40000 ALTER TABLE `test_update` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `testbooking`
--

DROP TABLE IF EXISTS `testbooking`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `testbooking` (
  `booking_id` int NOT NULL AUTO_INCREMENT,
  `t_id` int NOT NULL,
  `p_id` int NOT NULL,
  `booking_date` date DEFAULT NULL,
  `test_date` date DEFAULT NULL,
  `result_delivery_date` date DEFAULT NULL,
  `result_delivery_mode` varchar(50) DEFAULT NULL,
  `due_amount` decimal(8,2) DEFAULT NULL,
  PRIMARY KEY (`booking_id`),
  KEY `t_id` (`t_id`),
  KEY `p_id` (`p_id`),
  CONSTRAINT `testbooking_ibfk_1` FOREIGN KEY (`t_id`) REFERENCES `test` (`test_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `testbooking_ibfk_2` FOREIGN KEY (`p_id`) REFERENCES `patient` (`patient_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `testbooking`
--

LOCK TABLES `testbooking` WRITE;
/*!40000 ALTER TABLE `testbooking` DISABLE KEYS */;
INSERT INTO `testbooking` VALUES (1,2,2,'2025-08-17','2025-08-18','2025-08-25','Email',1000.00),(2,3,2,'2025-08-17','2025-08-20','2025-08-27','SMS',500.00);
/*!40000 ALTER TABLE `testbooking` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-09-20 12:33:17
