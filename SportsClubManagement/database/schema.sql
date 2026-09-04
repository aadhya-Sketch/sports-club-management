-- MySQL dump 10.13  Distrib 8.0.46, for Win64 (x86_64)
--
-- Host: localhost    Database: sports_club_db
-- ------------------------------------------------------
-- Server version	8.0.46

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `bookings`
--

DROP TABLE IF EXISTS `bookings`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `bookings` (
  `booking_id` int NOT NULL AUTO_INCREMENT,
  `member_id` int DEFAULT NULL,
  `unit_id` int DEFAULT NULL,
  `booking_date` date DEFAULT NULL,
  `start_time` time DEFAULT NULL,
  `end_time` time DEFAULT NULL,
  `booking_status` varchar(20) DEFAULT 'Confirmed',
  `created_at` datetime DEFAULT CURRENT_TIMESTAMP,
  `cancelled_at` datetime DEFAULT NULL,
  PRIMARY KEY (`booking_id`),
  UNIQUE KEY `unique_booking` (`unit_id`,`booking_date`,`start_time`),
  KEY `member_id` (`member_id`),
  CONSTRAINT `bookings_ibfk_1` FOREIGN KEY (`member_id`) REFERENCES `members` (`member_id`),
  CONSTRAINT `bookings_ibfk_2` FOREIGN KEY (`unit_id`) REFERENCES `facilities_units` (`unit_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bookings`
--

LOCK TABLES `bookings` WRITE;
/*!40000 ALTER TABLE `bookings` DISABLE KEYS */;
INSERT INTO `bookings` VALUES (1,1,1,'2026-09-01','10:00:00','11:00:00','Cancelled','2026-08-28 14:11:48','2026-08-28 14:14:17'),(2,1,3,'2026-09-03','16:00:00','17:00:00','Confirmed','2026-08-28 23:00:38',NULL),(3,5,1,'2026-09-01','09:00:00','10:00:00','Confirmed','2026-08-28 23:11:30',NULL),(4,5,2,'2026-08-30','11:00:00','12:00:00','Confirmed','2026-08-28 23:48:41',NULL),(5,5,3,'2026-08-29','12:00:00','13:00:00','Confirmed','2026-08-28 23:55:10',NULL);
/*!40000 ALTER TABLE `bookings` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `facilities`
--

DROP TABLE IF EXISTS `facilities`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `facilities` (
  `facility_id` int NOT NULL AUTO_INCREMENT,
  `facility_name` varchar(100) DEFAULT NULL,
  `facility_type` varchar(50) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `no_of_units` int DEFAULT NULL,
  PRIMARY KEY (`facility_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `facilities`
--

LOCK TABLES `facilities` WRITE;
/*!40000 ALTER TABLE `facilities` DISABLE KEYS */;
INSERT INTO `facilities` VALUES (1,'Tennis Court','Tennis','Outdoor tennis courts',2),(2,'Badminton Court','Badminton','Indoor badminton courts',3),(3,'Multi-purpose Field','Multi-purpose','Open field for football/cricket',1),(4,'Archery Range','Archery','Outdoor archery range',1);
/*!40000 ALTER TABLE `facilities` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `facilities_units`
--

DROP TABLE IF EXISTS `facilities_units`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `facilities_units` (
  `unit_id` int NOT NULL AUTO_INCREMENT,
  `facility_id` int DEFAULT NULL,
  `unit_name` varchar(100) DEFAULT NULL,
  `status` varchar(20) DEFAULT 'Active',
  PRIMARY KEY (`unit_id`),
  KEY `facility_id` (`facility_id`),
  CONSTRAINT `facilities_units_ibfk_1` FOREIGN KEY (`facility_id`) REFERENCES `facilities` (`facility_id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `facilities_units`
--

LOCK TABLES `facilities_units` WRITE;
/*!40000 ALTER TABLE `facilities_units` DISABLE KEYS */;
INSERT INTO `facilities_units` VALUES (1,1,'Tennis Court 1','Active'),(2,1,'Tennis Court 2','Active'),(3,2,'Badminton Court 1','Active'),(4,2,'Badminton Court 2','Active'),(5,2,'Badminton Court 3','Active'),(6,3,'Multi-purpose Field 1','Active'),(7,4,'Archery Range 1','Active');
/*!40000 ALTER TABLE `facilities_units` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `members`
--

DROP TABLE IF EXISTS `members`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `members` (
  `member_id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  `phone_number` varchar(15) DEFAULT NULL,
  `email` varchar(100) DEFAULT NULL,
  `password` varchar(255) NOT NULL,
  `join_date` date DEFAULT NULL,
  `status` varchar(20) DEFAULT 'Active',
  PRIMARY KEY (`member_id`),
  UNIQUE KEY `email` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `members`
--

LOCK TABLES `members` WRITE;
/*!40000 ALTER TABLE `members` DISABLE KEYS */;
INSERT INTO `members` VALUES (1,'Ravi Kumar','9876543210','ravi@example.com','pass123','2026-01-10','Active'),(2,'Anita Sharma','9876543211','anita@example.com','pass123','2026-02-15','Active'),(3,'John Mathews','9876543212','john@example.com','pass123','2026-03-05','Active'),(4,'Test User','9999999999','testuser@example.com','test123','2026-08-27','Active'),(5,'Aadhya','7349505997','aadhyabn20@gmail.com','aadhya','2026-08-28','Active'),(6,'Ankitha','7598061908','ankitha@gmail.com','ankitha','2026-08-28','Active'),(7,'Raksha','9845798472','raksha@gmail.com','raksha','2026-08-28','Active');
/*!40000 ALTER TABLE `members` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `memberships`
--

DROP TABLE IF EXISTS `memberships`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `memberships` (
  `membership_id` int NOT NULL AUTO_INCREMENT,
  `member_id` int DEFAULT NULL,
  `membership_type` varchar(50) DEFAULT NULL,
  `start_date` date DEFAULT NULL,
  `end_date` date DEFAULT NULL,
  `total_amount` decimal(10,2) DEFAULT NULL,
  `status` varchar(20) DEFAULT 'Active',
  PRIMARY KEY (`membership_id`),
  KEY `member_id` (`member_id`),
  CONSTRAINT `memberships_ibfk_1` FOREIGN KEY (`member_id`) REFERENCES `members` (`member_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `memberships`
--

LOCK TABLES `memberships` WRITE;
/*!40000 ALTER TABLE `memberships` DISABLE KEYS */;
INSERT INTO `memberships` VALUES (1,1,'Monthly','2026-09-01','2026-10-01',1500.00,'Active'),(2,5,'Monthly','2026-09-01','2026-10-01',2000.00,'Active'),(3,6,'Monthly','2026-09-01','2026-10-01',1200.00,'Active'),(4,7,'Monthly','2026-08-01','2026-09-01',500.00,'Active');
/*!40000 ALTER TABLE `memberships` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `payments`
--

DROP TABLE IF EXISTS `payments`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `payments` (
  `payment_id` int NOT NULL AUTO_INCREMENT,
  `member_id` int DEFAULT NULL,
  `amount` decimal(10,2) DEFAULT NULL,
  `payment_date` datetime DEFAULT CURRENT_TIMESTAMP,
  `payment_type` varchar(20) DEFAULT NULL,
  `reference_id` int DEFAULT NULL,
  `status` varchar(20) DEFAULT 'Success',
  `payment_method` varchar(20) DEFAULT 'Cash',
  PRIMARY KEY (`payment_id`),
  KEY `member_id` (`member_id`),
  CONSTRAINT `payments_ibfk_1` FOREIGN KEY (`member_id`) REFERENCES `members` (`member_id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `payments`
--

LOCK TABLES `payments` WRITE;
/*!40000 ALTER TABLE `payments` DISABLE KEYS */;
INSERT INTO `payments` VALUES (1,1,300.00,'2026-08-28 23:00:39','Booking',2,'Success','Cash'),(2,1,1500.00,'2026-08-28 23:05:23','Membership',1,'Success','Cash'),(3,5,300.00,'2026-08-28 23:11:44','Booking',3,'Success','Cash'),(4,5,2000.00,'2026-08-28 23:13:03','Membership',2,'Success','Cash'),(5,6,1200.00,'2026-08-28 23:21:47','Membership',3,'Success','Cash'),(6,7,500.00,'2026-08-28 23:35:21','Membership',4,'Success','Cash'),(7,5,300.00,'2026-08-28 23:55:21','Booking',5,'Success','UPI');
/*!40000 ALTER TABLE `payments` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2026-08-30 23:10:48
