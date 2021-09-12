/*
 Navicat Premium Data Transfer

 Source Server         : local
 Source Server Type    : MySQL
 Source Server Version : 80002
 Source Host           : localhost:3306
 Source Schema         : jpa

 Target Server Type    : MySQL
 Target Server Version : 80002
 File Encoding         : 65001

 Date: 12/09/2021 11:18:40
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for hibernate_sequence
-- ----------------------------
DROP TABLE IF EXISTS `hibernate_sequence`;
CREATE TABLE `hibernate_sequence` (
  `next_val` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of hibernate_sequence
-- ----------------------------
BEGIN;
INSERT INTO `hibernate_sequence` VALUES (25);
INSERT INTO `hibernate_sequence` VALUES (25);
INSERT INTO `hibernate_sequence` VALUES (25);
COMMIT;

-- ----------------------------
-- Table structure for t_class
-- ----------------------------
DROP TABLE IF EXISTS `t_class`;
CREATE TABLE `t_class` (
  `c_id` int(11) NOT NULL AUTO_INCREMENT,
  `c_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`c_id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of t_class
-- ----------------------------
BEGIN;
INSERT INTO `t_class` VALUES (1, 'class A');
INSERT INTO `t_class` VALUES (2, 'class B');
INSERT INTO `t_class` VALUES (3, 'class C');
INSERT INTO `t_class` VALUES (4, 'class A1');
INSERT INTO `t_class` VALUES (5, 'class B1');
INSERT INTO `t_class` VALUES (6, 'class C1');
COMMIT;

-- ----------------------------
-- Table structure for t_exam_config
-- ----------------------------
DROP TABLE IF EXISTS `t_exam_config`;
CREATE TABLE `t_exam_config` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(20) NOT NULL,
  `link` varchar(100) NOT NULL,
  `sub_class_id` int(11) DEFAULT NULL COMMENT '考试id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COMMENT='考试配置表';

-- ----------------------------
-- Records of t_exam_config
-- ----------------------------
BEGIN;
INSERT INTO `t_exam_config` VALUES (1, '语文古诗词', 'www.grape.con', 1);
INSERT INTO `t_exam_config` VALUES (2, '英语语法', 'www.grape.com', 4);
INSERT INTO `t_exam_config` VALUES (3, '语文文言文', 'www.grape.con', 5);
INSERT INTO `t_exam_config` VALUES (4, '数学几何', 'www.grapemath.con', 7);
INSERT INTO `t_exam_config` VALUES (5, '数学代数', 'www.grape.con', 8);
INSERT INTO `t_exam_config` VALUES (6, '物理电磁学', 'www.grape.con', 9);
INSERT INTO `t_exam_config` VALUES (7, '生物细胞分裂', 'www.grape.con', 9);
COMMIT;

-- ----------------------------
-- Table structure for t_exam_user
-- ----------------------------
DROP TABLE IF EXISTS `t_exam_user`;
CREATE TABLE `t_exam_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `uid` varchar(20) NOT NULL,
  `score` int(11) NOT NULL COMMENT '考试分数',
  `status` varchar(10) NOT NULL COMMENT '考试状态',
  `exam_id` int(11) NOT NULL COMMENT '考试id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COMMENT='用户考试记录表';

-- ----------------------------
-- Records of t_exam_user
-- ----------------------------
BEGIN;
INSERT INTO `t_exam_user` VALUES (1, 'grape', 5, 'Y', 1);
INSERT INTO `t_exam_user` VALUES (2, 'grape', 5, 'Y', 2);
INSERT INTO `t_exam_user` VALUES (3, 'grape', 5, 'Y', 3);
INSERT INTO `t_exam_user` VALUES (4, 'grape', 5, 'Y', 4);
INSERT INTO `t_exam_user` VALUES (5, 'grape', 5, 'Y', 5);
INSERT INTO `t_exam_user` VALUES (6, 'grape', 5, 'Y', 6);
INSERT INTO `t_exam_user` VALUES (7, 'grape', 5, 'N', 7);
COMMIT;

-- ----------------------------
-- Table structure for t_parent_class
-- ----------------------------
DROP TABLE IF EXISTS `t_parent_class`;
CREATE TABLE `t_parent_class` (
  `id` tinyint(4) DEFAULT NULL,
  `name` varchar(20) DEFAULT NULL,
  `creator` varchar(40) DEFAULT NULL,
  `modifier` varchar(40) DEFAULT NULL,
  `creat_time` varchar(20) DEFAULT NULL,
  `modify_time` varchar(20) DEFAULT NULL,
  `link` varchar(200) DEFAULT NULL COMMENT '相关链接'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of t_parent_class
-- ----------------------------
BEGIN;
INSERT INTO `t_parent_class` VALUES (1, 'A', 'grape', 'Grape', '2021-09-11 14:16:08', NULL, 'www.localhost.com');
INSERT INTO `t_parent_class` VALUES (2, 'B', 'Grape', 'Grape', '2021-09-11', NULL, NULL);
INSERT INTO `t_parent_class` VALUES (3, 'C', 'Grape', 'Grape', '2021-09-11', NULL, NULL);
INSERT INTO `t_parent_class` VALUES (4, 'D', 'Grape', 'Grape', '2021-09-11', NULL, NULL);
INSERT INTO `t_parent_class` VALUES (21, '添加删除测试', 'grape', NULL, '2021-09-12 09:14:31', NULL, 'www.localhost.com');
COMMIT;

-- ----------------------------
-- Table structure for t_position
-- ----------------------------
DROP TABLE IF EXISTS `t_position`;
CREATE TABLE `t_position` (
  `id` int(11) DEFAULT NULL,
  `position_class` varchar(20) DEFAULT NULL,
  `level` smallint(6) DEFAULT NULL,
  `child_score` smallint(6) DEFAULT NULL,
  `parent_size` smallint(6) DEFAULT NULL,
  `creator` varchar(40) DEFAULT NULL,
  `modifier` varchar(40) DEFAULT NULL,
  `creat_time` varchar(20) DEFAULT NULL,
  `modify_time` varchar(20) DEFAULT NULL,
  `score` smallint(6) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of t_position
-- ----------------------------
BEGIN;
INSERT INTO `t_position` VALUES (1, 'M', 3, 5, 2, 'grape', NULL, NULL, NULL, 10);
INSERT INTO `t_position` VALUES (2, 'M', 4, 5, 2, 'grape', NULL, NULL, NULL, 15);
INSERT INTO `t_position` VALUES (3, 'M', 5, 5, 2, 'grape', NULL, NULL, NULL, 20);
INSERT INTO `t_position` VALUES (4, 'K', 6, 10, 3, 'grape', NULL, NULL, NULL, 40);
INSERT INTO `t_position` VALUES (5, 'K', 7, 10, 3, 'grape', NULL, NULL, NULL, 50);
INSERT INTO `t_position` VALUES (6, 'K', 8, 10, 3, 'grape', NULL, NULL, NULL, 60);
INSERT INTO `t_position` VALUES (7, 'Z', 11, 15, 4, 'grape', NULL, NULL, NULL, 80);
INSERT INTO `t_position` VALUES (8, 'Z', 12, 15, 4, 'grape', NULL, NULL, NULL, 90);
INSERT INTO `t_position` VALUES (9, 'Z', 13, 15, 4, 'grape', NULL, NULL, NULL, 100);
COMMIT;

-- ----------------------------
-- Table structure for t_student
-- ----------------------------
DROP TABLE IF EXISTS `t_student`;
CREATE TABLE `t_student` (
  `s_id` int(11) NOT NULL AUTO_INCREMENT,
  `age` int(11) DEFAULT NULL,
  `class_id` int(11) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`s_id`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of t_student
-- ----------------------------
BEGIN;
INSERT INTO `t_student` VALUES (1, 11, 101, 'aaaaa');
INSERT INTO `t_student` VALUES (2, 22, 102, 'abcde');
INSERT INTO `t_student` VALUES (3, 33, 103, 'abbcd');
INSERT INTO `t_student` VALUES (4, 44, 104, 'bcdee');
INSERT INTO `t_student` VALUES (5, 55, 105, 'abbcd');
INSERT INTO `t_student` VALUES (6, 6, 106, 'eee');
INSERT INTO `t_student` VALUES (7, 7, 107, 'fff');
INSERT INTO `t_student` VALUES (8, 8, 108, 'ggg');
INSERT INTO `t_student` VALUES (9, 9, 109, 'gg');
INSERT INTO `t_student` VALUES (10, 10, 110, 'g');
INSERT INTO `t_student` VALUES (11, 11, 111, 'hhh');
INSERT INTO `t_student` VALUES (12, 12, 112, 'gg');
INSERT INTO `t_student` VALUES (13, 13, 113, 'hg');
INSERT INTO `t_student` VALUES (14, 14, 114, 'as');
INSERT INTO `t_student` VALUES (15, 15, 115, '12');
INSERT INTO `t_student` VALUES (16, 16, 116, '34');
INSERT INTO `t_student` VALUES (17, 17, 117, 'er');
INSERT INTO `t_student` VALUES (18, 18, 118, 'ss');
INSERT INTO `t_student` VALUES (19, 19, 119, 'ui');
COMMIT;

-- ----------------------------
-- Table structure for t_sub_class
-- ----------------------------
DROP TABLE IF EXISTS `t_sub_class`;
CREATE TABLE `t_sub_class` (
  `id` int(11) DEFAULT NULL,
  `name` varchar(20) DEFAULT NULL,
  `score` smallint(6) DEFAULT NULL,
  `parent_id` int(11) DEFAULT NULL,
  `creator` varchar(40) DEFAULT NULL,
  `modifier` varchar(40) DEFAULT NULL,
  `creat_time` varchar(20) DEFAULT NULL,
  `modify_time` varchar(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of t_sub_class
-- ----------------------------
BEGIN;
INSERT INTO `t_sub_class` VALUES (1, '语文', 11, 1, 'Grape', 'grape_modife', NULL, '2021-09-11 14:14:50');
INSERT INTO `t_sub_class` VALUES (2, '数学', 10, 1, 'Grape', NULL, NULL, NULL);
INSERT INTO `t_sub_class` VALUES (3, '英语', 10, 1, 'Grape', NULL, NULL, NULL);
INSERT INTO `t_sub_class` VALUES (4, '物理', 5, 2, 'Grape', NULL, NULL, NULL);
INSERT INTO `t_sub_class` VALUES (5, '化学', 5, 2, 'Grape', NULL, NULL, NULL);
INSERT INTO `t_sub_class` VALUES (6, '生物', 5, 2, 'Grape', NULL, NULL, NULL);
INSERT INTO `t_sub_class` VALUES (7, '体育', 1, 3, 'Grape', NULL, NULL, NULL);
INSERT INTO `t_sub_class` VALUES (8, '地理', 2, 3, 'grape', NULL, '2021-09-11 13:32:11', NULL);
INSERT INTO `t_sub_class` VALUES (9, '政治', 2, 4, 'grape', NULL, NULL, NULL);
INSERT INTO `t_sub_class` VALUES (10, '历史', 2, 4, 'grape', NULL, NULL, NULL);
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
