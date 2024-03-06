-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1:3306
-- Generation Time: Mar 06, 2024 at 10:13 PM
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
-- Table structure for table `students`
--

DROP TABLE IF EXISTS `students`;
CREATE TABLE IF NOT EXISTS `students` (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_id` int DEFAULT NULL,
  `points` int UNSIGNED DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_student_id` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=44 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `students`
--

INSERT INTO `students` (`id`, `user_id`, `points`) VALUES
(34, 11, 40),
(35, 16, 21),
(37, 15, 5),
(42, 43, 27);

-- --------------------------------------------------------

--
-- Table structure for table `student_unit`
--

DROP TABLE IF EXISTS `student_unit`;
CREATE TABLE IF NOT EXISTS `student_unit` (
  `student_id` int NOT NULL,
  `unit_name` varchar(30) NOT NULL,
  PRIMARY KEY (`student_id`,`unit_name`),
  KEY `fk_unit` (`unit_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `student_unit`
--

INSERT INTO `student_unit` (`student_id`, `unit_name`) VALUES
(34, 'cryptography'),
(35, 'cryptography'),
(35, 'reseaux'),
(37, 'reseaux'),
(34, 'theory des nombres');

-- --------------------------------------------------------

--
-- Table structure for table `teachers`
--

DROP TABLE IF EXISTS `teachers`;
CREATE TABLE IF NOT EXISTS `teachers` (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_id` int DEFAULT NULL,
  `unit_name` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_teacher_id` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `teachers`
--

INSERT INTO `teachers` (`id`, `user_id`, `unit_name`) VALUES
(5, 18, 'reseaux'),
(6, 19, 'cryptography'),
(7, 20, 'theory des nombres'),
(13, 44, 'Java');

-- --------------------------------------------------------

--
-- Table structure for table `units`
--

DROP TABLE IF EXISTS `units`;
CREATE TABLE IF NOT EXISTS `units` (
  `unit_name` varchar(30) NOT NULL,
  `teacher_id` int DEFAULT NULL,
  PRIMARY KEY (`unit_name`),
  KEY `fk_teacher` (`teacher_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `units`
--

INSERT INTO `units` (`unit_name`, `teacher_id`) VALUES
('reseaux', 5),
('cryptography', 6),
('theory des nombres', 7),
('Java', 13);

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
CREATE TABLE IF NOT EXISTS `users` (
  `id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(191) NOT NULL,
  `password` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `role` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `salt` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=47 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`id`, `username`, `password`, `role`, `salt`) VALUES
(1, 'achraf', '$2a$10$secaW45MzQZ/w49yZOuec.zQLW0kHe9uU4VfBAum4u96FtJA5tMsC', 'admin', '$2a$10$secaW45MzQZ/w49yZOuec.'),
(11, 'anass', '$2a$10$Pdjb46Wxnzq/OVvJAiLgp.wYss2t/Ya3LK0DylUzchBC1qnFFchpW', 'student', '$2a$10$Pdjb46Wxnzq/OVvJAiLgp.'),
(15, 'youssef', '$2a$10$B8yWCENmxigN9BNq.JPire2HsQJX2vFfEdLe4/4QI4m.RaoaefdAu', 'student', '$2a$10$B8yWCENmxigN9BNq.JPire'),
(16, 'chentoufi', '$2a$10$Y8uOf9q4b5ByWC7x9MRJqOdJW8lIRGuwncou2f3jEBd0glSuJDVi.', 'student', '$2a$10$Y8uOf9q4b5ByWC7x9MRJqO'),
(18, 'bouchra', '$2a$10$03jU6aOYWljmVmyfIa4FHOpCmqta/.4rdsBsqK2sFf.t.6C8ki6/i', 'teacher', '$2a$10$03jU6aOYWljmVmyfIa4FHO'),
(19, 'souidi', '$2a$10$DjO9a7TsIGb.hEYEgCpKjuXf3xa8yNPxjedQfbOZ7dpxcCuNpp7.6', 'teacher', '$2a$10$DjO9a7TsIGb.hEYEgCpKju'),
(20, 'cherabi', '$2a$10$drOdSZ774eHOxCez5rHJOuJ7uduTY3BRaBgGADzSopj610NWRp1Oa', 'teacher', '$2a$10$drOdSZ774eHOxCez5rHJOu'),
(43, 'imane', '$2a$10$Xqur7TzfNX9sr6xKWqOAKe9fbLr6puJolr07mH6MbAfrCcVuwRInO', 'student', '$2a$10$Xqur7TzfNX9sr6xKWqOAKe'),
(44, 'bouhouch', '$2a$10$3eoPB5rUMvxt9n/IdMW8hOVo1n9iHpNTOoTU.MnN6Lgtk/vXMplSy', 'teacher', '$2a$10$3eoPB5rUMvxt9n/IdMW8hO');

--
-- Constraints for dumped tables
--

--
-- Constraints for table `students`
--
ALTER TABLE `students`
  ADD CONSTRAINT `fk_student_id` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `student_unit`
--
ALTER TABLE `student_unit`
  ADD CONSTRAINT `fk_student` FOREIGN KEY (`student_id`) REFERENCES `students` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `fk_unit` FOREIGN KEY (`unit_name`) REFERENCES `units` (`unit_name`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `teachers`
--
ALTER TABLE `teachers`
  ADD CONSTRAINT `fk_teacher_id` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `units`
--
ALTER TABLE `units`
  ADD CONSTRAINT `fk_teacher` FOREIGN KEY (`teacher_id`) REFERENCES `teachers` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
