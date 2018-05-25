-- MySQL dump 10.13  Distrib 5.7.17, for Win64 (x86_64)
--
-- Host: 158.38.101.201    Database: device_control_vislab_db
-- ------------------------------------------------------
-- Server version	5.7.21

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `device`
--

DROP TABLE IF EXISTS `device`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `device` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `default_name` varchar(255) DEFAULT NULL,
  `ip_address` varchar(255) NOT NULL,
  `port` int(11) NOT NULL,
  `rotation` int(11) NOT NULL,
  `x_pos` int(11) NOT NULL,
  `y_pos` int(11) NOT NULL,
  `device_info_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK37utayxgcs0p3nwf455t8oj6f` (`device_info_id`),
  CONSTRAINT `FK37utayxgcs0p3nwf455t8oj6f` FOREIGN KEY (`device_info_id`) REFERENCES `device_info` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `device`
--

LOCK TABLES `device` WRITE;
/*!40000 ALTER TABLE `device` DISABLE KEYS */;
/*!40000 ALTER TABLE `device` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `device_info`
--

DROP TABLE IF EXISTS `device_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `device_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `manufacturer` varchar(255) NOT NULL,
  `model` varchar(255) NOT NULL,
  `device_type_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKrr3up9dm5co3el0rpq1q5hngh` (`device_type_id`),
  CONSTRAINT `FKrr3up9dm5co3el0rpq1q5hngh` FOREIGN KEY (`device_type_id`) REFERENCES `device_type` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `device_info`
--

LOCK TABLES `device_info` WRITE;
/*!40000 ALTER TABLE `device_info` DISABLE KEYS */;
/*!40000 ALTER TABLE `device_info` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `device_type`
--

DROP TABLE IF EXISTS `device_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `device_type` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `type` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `device_type`
--

LOCK TABLES `device_type` WRITE;
/*!40000 ALTER TABLE `device_type` DISABLE KEYS */;
INSERT INTO `device_type` VALUES (1,'Projector'),(2,'Sound System'),(3,'Device');
/*!40000 ALTER TABLE `device_type` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `devicegroup`
--

DROP TABLE IF EXISTS `devicegroup`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `devicegroup` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `group_name` varchar(255) NOT NULL,
  `is_defaultdgroup` bit(1) NOT NULL,
  `theatre_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKjff0eu22irym1pq6fjh4yjt45` (`theatre_id`),
  CONSTRAINT `FKjff0eu22irym1pq6fjh4yjt45` FOREIGN KEY (`theatre_id`) REFERENCES `theatre` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `devicegroup`
--

LOCK TABLES `devicegroup` WRITE;
/*!40000 ALTER TABLE `devicegroup` DISABLE KEYS */;
/*!40000 ALTER TABLE `devicegroup` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `junction_devicegroup_device`
--

DROP TABLE IF EXISTS `junction_devicegroup_device`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `junction_devicegroup_device` (
  `devicegroup_id` int(11) NOT NULL,
  `device_id` int(11) NOT NULL,
  KEY `FKrgk4mfxgp04pfsqol62tfk89k` (`device_id`),
  KEY `FKnbp8qd627v1c1e317i4f3w7rb` (`devicegroup_id`),
  CONSTRAINT `FKnbp8qd627v1c1e317i4f3w7rb` FOREIGN KEY (`devicegroup_id`) REFERENCES `devicegroup` (`id`),
  CONSTRAINT `FKrgk4mfxgp04pfsqol62tfk89k` FOREIGN KEY (`device_id`) REFERENCES `device` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `junction_devicegroup_device`
--

LOCK TABLES `junction_devicegroup_device` WRITE;
/*!40000 ALTER TABLE `junction_devicegroup_device` DISABLE KEYS */;
/*!40000 ALTER TABLE `junction_devicegroup_device` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `junction_devicegroup_user`
--

DROP TABLE IF EXISTS `junction_devicegroup_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `junction_devicegroup_user` (
  `devicegroup_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  KEY `FK8tvg9cxbhryhqc7ke5qjsddv` (`user_id`),
  KEY `FK68glkyl902dropo3d31c43khb` (`devicegroup_id`),
  CONSTRAINT `FK68glkyl902dropo3d31c43khb` FOREIGN KEY (`devicegroup_id`) REFERENCES `devicegroup` (`id`),
  CONSTRAINT `FK8tvg9cxbhryhqc7ke5qjsddv` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `junction_devicegroup_user`
--

LOCK TABLES `junction_devicegroup_user` WRITE;
/*!40000 ALTER TABLE `junction_devicegroup_user` DISABLE KEYS */;
/*!40000 ALTER TABLE `junction_devicegroup_user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `junction_theatre_device`
--

DROP TABLE IF EXISTS `junction_theatre_device`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `junction_theatre_device` (
  `theatre_id` int(11) NOT NULL,
  `device_id` int(11) NOT NULL,
  KEY `FKmohncgdowib9e5kb42u8tmfrr` (`device_id`),
  KEY `FK33jcaqlk6w7fkcw8dmj5fw464` (`theatre_id`),
  CONSTRAINT `FK33jcaqlk6w7fkcw8dmj5fw464` FOREIGN KEY (`theatre_id`) REFERENCES `theatre` (`id`),
  CONSTRAINT `FKmohncgdowib9e5kb42u8tmfrr` FOREIGN KEY (`device_id`) REFERENCES `device` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `junction_theatre_device`
--

LOCK TABLES `junction_theatre_device` WRITE;
/*!40000 ALTER TABLE `junction_theatre_device` DISABLE KEYS */;
/*!40000 ALTER TABLE `junction_theatre_device` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `role`
--

DROP TABLE IF EXISTS `role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `role_name` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `role`
--

LOCK TABLES `role` WRITE;
/*!40000 ALTER TABLE `role` DISABLE KEYS */;
INSERT INTO `role` VALUES (1,'ADMIN'),(2,'USER');
/*!40000 ALTER TABLE `role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `theatre`
--

DROP TABLE IF EXISTS `theatre`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `theatre` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `theatre_name` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `theatre`
--

LOCK TABLES `theatre` WRITE;
/*!40000 ALTER TABLE `theatre` DISABLE KEYS */;
/*!40000 ALTER TABLE `theatre` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `default_theatre` tinyblob,
  `email` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `username` varchar(255) NOT NULL,
  `role_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKn82ha3ccdebhokx3a8fgdqeyy` (`role_id`),
  CONSTRAINT `FKn82ha3ccdebhokx3a8fgdqeyy` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,NULL,'no@mail.tmp','$2a$04$JXPPjN3sZ0/zol0gsv1BRumGkz9pomqTd3Bwb8B0eU6hyp/mvuJ8i','admin',1);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-05-22 14:48:20
