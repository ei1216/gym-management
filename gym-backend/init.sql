-- --------------------------------------------------------
-- 主机:                           127.0.0.1
-- 服务器版本:                        8.0.26 - MySQL Community Server - GPL
-- 服务器操作系统:                      Win64
-- HeidiSQL 版本:                  12.6.0.6765
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


-- 导出 gym 的数据库结构
CREATE DATABASE IF NOT EXISTS `gym` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `gym`;

-- 导出  表 gym.admins 结构
CREATE TABLE IF NOT EXISTS `admins` (
  `id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
  `password` varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
  `phone` varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `create_Date` date NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- 正在导出表  gym.admins 的数据：~3 rows (大约)
INSERT INTO `admins` (`id`, `username`, `password`, `phone`, `create_Date`) VALUES
	(1, 'haonian', 'e10adc3949ba59abbe56e057f20f883e', '18959332522', '2024-07-13'),
	(2, 'beq', 'e10adc3949ba59abbe56e057f20f883e', '18086168872', '2024-07-18'),
	(3, 'ethan', '81dc9bdb52d04dc20036dbd8313ed055', '13568521534', '2024-07-18');

-- 导出  表 gym.coaches 结构
CREATE TABLE IF NOT EXISTS `coaches` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(200) COLLATE utf8mb4_general_ci NOT NULL,
  `phone` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `height` int NOT NULL,
  `weight` int NOT NULL,
  `join_date` date NOT NULL,
  `salary` int NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `phone` (`phone`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- 正在导出表  gym.coaches 的数据：~5 rows (大约)
INSERT INTO `coaches` (`id`, `name`, `phone`, `height`, `weight`, `join_date`, `salary`) VALUES
	(1, '陈康', '13568244521', 180, 85, '2024-07-13', 8999),
	(2, '鹿晨辉', '13268531251', 185, 95, '2024-07-14', 9500),
	(3, '小萝卜', '18068531251', 178, 82, '2024-07-14', 9000),
	(5, '嘴哥', '18925253623', 180, 80, '2024-07-14', 9000),
	(7, '昆图斯', '18356235424', 183, 80, '2024-07-19', 8998);

-- 导出  表 gym.courses 结构
CREATE TABLE IF NOT EXISTS `courses` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '课程名称',
  `description` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci COMMENT '课程描述',
  `coach_id` int NOT NULL COMMENT '私教id',
  `start_date` date NOT NULL COMMENT '开始日期',
  `end_date` date NOT NULL COMMENT '结束日期',
  `days_of_week` enum('星期一','星期二','星期三','星期四','星期五','星期六','星期日') CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '每周周几上课',
  `price` decimal(10,2) NOT NULL DEFAULT (0) COMMENT '价格',
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`),
  KEY `coach_id` (`coach_id`),
  CONSTRAINT `courses_ibfk_1` FOREIGN KEY (`coach_id`) REFERENCES `coaches` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- 正在导出表  gym.courses 的数据：~5 rows (大约)
INSERT INTO `courses` (`id`, `name`, `description`, `coach_id`, `start_date`, `end_date`, `days_of_week`, `price`) VALUES
	(1, '30天减脂训练营', '只需30天，帮您快速减脂', 1, '2024-07-20', '2024-08-20', '星期二', 1999.00),
	(2, '半年增肌训练营', '训练周期长，祝您更快更高质量增肌', 2, '2024-07-20', '2025-01-20', '星期六', 6999.00),
	(3, '一年增肌+减脂训练营', '只要进入，保证增肌和减脂', 3, '2024-07-22', '2025-07-22', '星期一', 9999.90),
	(5, '动感单车纯享课', '高强度减脂', 1, '2024-07-20', '2024-08-20', '星期一', 299.80),
	(6, '新手小白基础课', '(限时打折！)教你如何在健身房锻炼想要的部位', 2, '2024-07-26', '2024-08-10', '星期三', 159.90);

-- 导出  表 gym.course_registrations 结构
CREATE TABLE IF NOT EXISTS `course_registrations` (
  `id` int NOT NULL AUTO_INCREMENT,
  `member_id` int NOT NULL COMMENT '会员id',
  `member_name` varchar(50) COLLATE utf8mb4_general_ci NOT NULL COMMENT '会员姓名',
  `course_id` int NOT NULL COMMENT '课程id',
  `course_name` varchar(50) COLLATE utf8mb4_general_ci NOT NULL COMMENT '课程名称',
  `registration_date` date NOT NULL COMMENT '会员注册课程日期',
  PRIMARY KEY (`id`),
  KEY `member_id` (`member_id`),
  KEY `course_id` (`course_id`),
  CONSTRAINT `course_registrations_ibfk_1` FOREIGN KEY (`member_id`) REFERENCES `members` (`id`),
  CONSTRAINT `course_registrations_ibfk_2` FOREIGN KEY (`course_id`) REFERENCES `courses` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- 正在导出表  gym.course_registrations 的数据：~7 rows (大约)
INSERT INTO `course_registrations` (`id`, `member_id`, `member_name`, `course_id`, `course_name`, `registration_date`) VALUES
	(15, 1, 'xiehaonian', 1, '30天减脂训练营', '2024-07-20'),
	(16, 1, 'xiehaonian', 5, '动感单车纯享课', '2024-07-20'),
	(17, 4, 'Eason', 1, '30天减脂训练营', '2024-07-20'),
	(18, 2, 'John', 6, '新手小白基础课', '2024-07-20'),
	(19, 3, 'Mary', 5, '动感单车纯享课', '2024-07-20'),
	(20, 5, 'Musk', 3, '一年增肌+减脂训练营', '2024-07-20');

-- 导出  表 gym.equipment 结构
CREATE TABLE IF NOT EXISTS `equipment` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '名称',
  `quantity` int NOT NULL COMMENT '数量',
  `status` enum('正常','维护') CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '状态',
  `update_date` date NOT NULL COMMENT '购买日期',
  `notes` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci COMMENT '备注',
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- 正在导出表  gym.equipment 的数据：~3 rows (大约)
INSERT INTO `equipment` (`id`, `name`, `quantity`, `status`, `update_date`, `notes`) VALUES
	(2, '直杠铃', 29, '正常', '2024-07-19', '1.2m'),
	(3, '弯杠铃', 20, '正常', '2024-07-14', '1.5m'),
	(4, '龙门架', 12, '维护', '2024-07-14', '大型器械，可练胸背腿等大肌肉群');

-- 导出  表 gym.members 结构
CREATE TABLE IF NOT EXISTS `members` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(200) COLLATE utf8mb4_general_ci NOT NULL,
  `phone` varchar(20) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `type` enum('月卡会员','季卡会员','年卡会员') CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `join_date` date NOT NULL COMMENT '加入日期',
  `expiry_date` date NOT NULL COMMENT '过期日期',
  PRIMARY KEY (`id`),
  UNIQUE KEY `phone` (`phone`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- 正在导出表  gym.members 的数据：~5 rows (大约)
INSERT INTO `members` (`id`, `name`, `phone`, `type`, `join_date`, `expiry_date`) VALUES
	(1, 'xiehaonian', '18959332506', '月卡会员', '2024-07-14', '2024-08-19'),
	(2, 'John', '13526589562', '月卡会员', '2024-07-14', '2024-08-19'),
	(3, 'Mary', '18254386252', '月卡会员', '2024-07-14', '2024-08-14'),
	(4, 'Eason', '13685223689', '季卡会员', '2024-07-14', '2024-10-14'),
	(5, 'Musk', '13586521548', '季卡会员', '2024-07-14', '2024-10-14');

/*!40103 SET TIME_ZONE=IFNULL(@OLD_TIME_ZONE, 'system') */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
