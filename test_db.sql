# ************************************************************
# Sequel Pro SQL dump
# Version 4541
#
# http://www.sequelpro.com/
# https://github.com/sequelpro/sequelpro
#
# Host: 127.0.0.1 (MySQL 5.5.5-10.10.2-MariaDB)
# Database: test_db
# Generation Time: 2023-05-15 07:15:24 +0000
# ************************************************************
create database readme;
use readme;

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


# Dump of table framework
# ------------------------------------------------------------

DROP TABLE IF EXISTS `framework`;

CREATE TABLE `framework` (
  `name` varchar(255) NOT NULL,
  `content` varchar(255) DEFAULT NULL,
  `type` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

LOCK TABLES `framework` WRITE;
/*!40000 ALTER TABLE `framework` DISABLE KEYS */;

INSERT INTO `framework` (`name`, `content`, `type`)
VALUES
	('Architecture','## Project Architecture (Tree Structure)<br>','Palette'),
	('Contributor','## Contributor<br><a href=\"https://github.com/userName/repositoryName/graphs/contributors\" target=\"_blank\"> <img src=\"https://contrib.rocks/image?repo=userName/repositoryName\" /> </a>','Palette'),
	('DB Table','## DB Table<br>','Palette'),
	('Dependency','## Dependencies<br>DependencyNamesDependencyContents','Palette'),
	('Header','![header](https://capsule-render.vercel.app/api?type=Waving&&color=979494&fontColor=black&height=300&section=header&text=repoName&fontSize=70)','Palette'),
	('License','## License<br>','Palette'),
	('Period','## Project Period <br><img src=\"PeriodImage\" width=100%><span style=\"width:20%\"><span/><span style=\"margin-right: 60%; margin-left: 4%;\" id=\"start_date\">startDate</span><span width=20% id=\"end_date\">endDate</span>','Palette'),
	('Social','<a href=\"social_Link\" target=\"_blank\"><img src=\"https://img.shields.io/badge/social-logo_color?style=flat-square&logo=social&logoColor=white\"/></a>','Palette'),
	('WebAPI','## Web API<br>','Palette');

/*!40000 ALTER TABLE `framework` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table project
# ------------------------------------------------------------

DROP TABLE IF EXISTS `project`;

CREATE TABLE `project` (
  `file_name` varchar(255) NOT NULL,
  `file_path` varchar(255) NOT NULL,
  `id` varchar(255) NOT NULL,
  `file_content` longtext DEFAULT NULL,
  `detail` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`file_name`,`file_path`,`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;



# Dump of table user
# ------------------------------------------------------------

DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `project_id` varchar(255) NOT NULL,
  `repository_name` varchar(255) DEFAULT NULL,
  `user_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`project_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;




/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
