-- MySQL dump 10.13  Distrib 8.0.40, for Win64 (x86_64)
--
-- Host: localhost    Database: mediconnect
-- ------------------------------------------------------
-- Server version	8.0.40

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
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

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
  `details` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`patient_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

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
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-08-23 14:17:19
