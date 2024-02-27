-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1:3306
-- Generation Time: Feb 27, 2024 at 08:25 PM
-- Server version: 8.2.0
-- PHP Version: 8.2.13

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `university`
--

-- --------------------------------------------------------

--
-- Table structure for table `units`
--

DROP TABLE IF EXISTS `units`;
CREATE TABLE IF NOT EXISTS `units` (
  `id` int NOT NULL AUTO_INCREMENT,
  `unit_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `Professor` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `Hours` int NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `Professor` (`Professor`)
) ENGINE=MyISAM AUTO_INCREMENT=30 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `units`
--

INSERT INTO `units` (`id`, `unit_name`, `Professor`, `Hours`) VALUES
(18, 'Réseaux', 'Bouchra', 18),
(16, 'English', 'Souhaila', 20),
(29, 'theory des nombres', 'cherabi', 99),
(14, 'Cryptographie et Securité de l\'Information', 'Souidi', 32);

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
CREATE TABLE IF NOT EXISTS `users` (
  `id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(191) NOT NULL,
  `password` varchar(255) NOT NULL,
  `role` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `unit_name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `points` int DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=29 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`id`, `username`, `password`, `role`, `unit_name`, `points`) VALUES
(1, 'achraf', 'achraf', 'admin', NULL, NULL),
(11, 'anass', 'anass', 'student', NULL, 20),
(19, 'souidi', 'souidi', 'teacher', 'crypto', 0),
(12, 'imane', 'imane', 'student', 'null', 209),
(20, 'cherabi', 'cherabi', 'teacher', 'theory des nombres', 0),
(15, 'youssef', 'youssef', 'student', 'null', 22),
(16, 'chentoufi', 'chentoufi', 'student', NULL, 99),
(18, 'bouchra', 'bouchra', 'teacher', 'reseau', 0);

-- --------------------------------------------------------

--
-- Table structure for table `users_units`
--

DROP TABLE IF EXISTS `users_units`;
CREATE TABLE IF NOT EXISTS `users_units` (
  `student_id` int NOT NULL,
  `unit_id` int NOT NULL,
  PRIMARY KEY (`student_id`,`unit_id`),
  KEY `unit_id` (`unit_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `users_units`
--

INSERT INTO `users_units` (`student_id`, `unit_id`) VALUES
(2, 14),
(2, 16),
(2, 18),
(7, 14),
(7, 16),
(7, 18),
(7, 29);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
