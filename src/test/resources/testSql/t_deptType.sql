/*
Navicat MySQL Data Transfer

Source Server         : localMysql
Source Server Version : 50525
Source Host           : localhost:3306
Source Database       : ihos

Target Server Type    : MYSQL
Target Server Version : 50525
File Encoding         : 65001

Date: 2013-10-08 16:26:45
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `t_depttype`
-- ----------------------------
DROP TABLE IF EXISTS `t_depttype`;
CREATE TABLE `t_depttype` (
  `deptTypeId` varchar(255) NOT NULL,
  `deptTypeName` varchar(20) NOT NULL,
  `disabled` tinyint(4) NOT NULL,
  PRIMARY KEY (`deptTypeId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_depttype
-- ----------------------------
INSERT INTO `t_depttype` VALUES ('1', '管理', '0');
INSERT INTO `t_depttype` VALUES ('2', '医辅', '0');
INSERT INTO `t_depttype` VALUES ('3', '科研', '0');
INSERT INTO `t_depttype` VALUES ('4', '医技', '0');
INSERT INTO `t_depttype` VALUES ('5', '医疗', '0');
INSERT INTO `t_depttype` VALUES ('6', '未纳入', '0');
