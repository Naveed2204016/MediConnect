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
  CONSTRAINT `appointment_ibfk_1` FOREIGN KEY (`d_id`) REFERENCES `doctor` (`doctor_id`),
  CONSTRAINT `appointment_ibfk_2` FOREIGN KEY (`p_id`) REFERENCES `patient` (`patient_id`),
  CONSTRAINT `appointment_ibfk_3` FOREIGN KEY (`a_id`) REFERENCES `assistant` (`assistant_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `appointment`
--

LOCK TABLES `appointment` WRITE;
/*!40000 ALTER TABLE `appointment` DISABLE KEYS */;
INSERT INTO `appointment` VALUES (1,1,2,1,'2025-10-03','confirmed'),(4,1,3,1,'2025-08-19','Confirmed');
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
  PRIMARY KEY (`assistant_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `assistant`
--

LOCK TABLES `assistant` WRITE;
/*!40000 ALTER TABLE `assistant` DISABLE KEYS */;
INSERT INTO `assistant` VALUES (1,1,'faizul','01930309821','faizul@gmail.com','yes');
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
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `doctor`
--

LOCK TABLES `doctor` WRITE;
/*!40000 ALTER TABLE `doctor` DISABLE KEYS */;
INSERT INTO `doctor` VALUES (1,'mahbub','medicine','FCPS',1000.00,'01768876682','mahbub@gmail.com',50,3,'doc123'),(2,'Dr. Jan','Neurology','MBBS, DM',600.00,'01987654321','jan@gmail.com',25,5,'doc456'),(3,'Dr. Ayesha Rahman','Cardiology','MBBS, MD',1000.00,'01812345678','ayesha.@gmail.com',45,15,'cardio789'),(4,'Dr. Tanvir Alam','Orthopedics','MBBS, MS',750.00,'01922334455','tanvir.@yahoo.com',38,10,'ortho321'),(5,'Dr. Nusrat Jahan','Dermatology','MBBS, DDVL',500.00,'01733445566','nusrat.@gmail.com',34,7,'derma555'),(6,'Dr. Imran Hossain','Pediatrics','MBBS, DCH',650.00,'01899887766','imran.n@hotmail.com',40,12,'peds111'),(7,'Dr. Farhana Akter','Gynecology','MBBS, FCPS',900.00,'01611223344','farhanr@gmail.com',42,14,'gyn999'),(8,'Dr. Salman Kabir','Psychiatry','MBBS, MPhil',550.00,'01599887722','salman@gmail.com',37,8,'psych333'),(9,'Dr. Rafiq Chowdhury','ENT','MBBS, DLO',700.00,'01955667788','rafiq@gmail.com',48,18,'ent777'),(10,'Dr. Sharmeen Sultana','Ophthalmology','MBBS, MS',720.00,'01788990011','sharmeen@gmail.com',36,9,'eye888');
/*!40000 ALTER TABLE `doctor` ENABLE KEYS */;
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
  CONSTRAINT `location_ibfk_1` FOREIGN KEY (`d_id`) REFERENCES `doctor` (`doctor_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `location`
--

LOCK TABLES `location` WRITE;
/*!40000 ALTER TABLE `location` DISABLE KEYS */;
INSERT INTO `location` VALUES (1,'parkview','Chittagong','18:00:00','21:00:00');
/*!40000 ALTER TABLE `location` ENABLE KEYS */;
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
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `patient`
--

LOCK TABLES `patient` WRITE;
/*!40000 ALTER TABLE `patient` DISABLE KEYS */;
INSERT INTO `patient` VALUES (2,'Naveed','2004-05-09','01617793506','u2204016@student.cuet.ac.bd','abcd'),(3,'arka','2003-08-06','0183344711','arka@gmail.com','sos'),(4,'Rahat','2003-09-24','01619945703','rahat@gmail.com','meow'),(5,'Aziz','2003-08-06','019','a@gmail.com','kdb');
/*!40000 ALTER TABLE `patient` ENABLE KEYS */;
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
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `test`
--

LOCK TABLES `test` WRITE;
/*!40000 ALTER TABLE `test` DISABLE KEYS */;
INSERT INTO `test` VALUES (1,'Blood Test','Parkview',800.00,'Fast for 8 hours before the test','08:30:00'),(2,'MRI Scan','Evercare',5000.00,'Remove all metallic objects before test','10:00:00'),(3,'X-Ray','Chevron',1500.00,'Wear loose clothing','14:15:00');
/*!40000 ALTER TABLE `test` ENABLE KEYS */;
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
  CONSTRAINT `testbooking_ibfk_1` FOREIGN KEY (`t_id`) REFERENCES `test` (`test_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `testbooking_ibfk_2` FOREIGN KEY (`p_id`) REFERENCES `patient` (`patient_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
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

-- Dump completed on 2025-08-23  2:15:20
