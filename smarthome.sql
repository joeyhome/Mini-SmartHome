/*
Navicat MySQL Data Transfer

Source Server         : linpcloud@CentOS
Source Server Version : 50173
Source Host           : 218.244.138.146:3306
Source Database       : linpcloud

Target Server Type    : MYSQL
Target Server Version : 50173
File Encoding         : 65001

Date: 2014-06-08 23:48:57
*/

SET names utf8;
SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for tb_datapoint
-- ----------------------------
DROP TABLE IF EXISTS `tb_datapoint`;
CREATE TABLE `tb_datapoint` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `sensor_id` int(11) NOT NULL,
  `timestamp` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `sensor_id` (`sensor_id`),
  CONSTRAINT `tb_datapoint_ibfk_1` FOREIGN KEY (`sensor_id`) REFERENCES `tb_sensor` (`id`) ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=103 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_datapoint
-- ----------------------------
INSERT INTO `tb_datapoint` VALUES ('100', '100000', '100');
INSERT INTO `tb_datapoint` VALUES ('101', '100000', '105');
INSERT INTO `tb_datapoint` VALUES ('102', '100000', '110');

-- ----------------------------
-- Table structure for tb_datapoint_general
-- ----------------------------
DROP TABLE IF EXISTS `tb_datapoint_general`;
CREATE TABLE `tb_datapoint_general` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `sensor_id` int(11) NOT NULL,
  `timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `value` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `sensor_id` (`sensor_id`),
  CONSTRAINT `tb_datapoint_general_ibfk_1` FOREIGN KEY (`sensor_id`) REFERENCES `tb_sensor` (`id`) ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_datapoint_general
-- ----------------------------

-- ----------------------------
-- Table structure for tb_datapoint_lite
-- ----------------------------
DROP TABLE IF EXISTS `tb_datapoint_lite`;
CREATE TABLE `tb_datapoint_lite` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `sensor_id` int(11) NOT NULL,
  `timestamp` int(11) NOT NULL,
  `value` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `sensor_id` (`sensor_id`),
  CONSTRAINT `tb_datapoint_lite_ibfk_1` FOREIGN KEY (`sensor_id`) REFERENCES `tb_sensor` (`id`) ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=1052 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_datapoint_lite
-- ----------------------------
INSERT INTO `tb_datapoint_lite` VALUES ('1017', '100005', '1399906977', '66ffcc');
INSERT INTO `tb_datapoint_lite` VALUES ('1018', '100006', '1399906977', '30%');
INSERT INTO `tb_datapoint_lite` VALUES ('1019', '100007', '1399906977', '1');
INSERT INTO `tb_datapoint_lite` VALUES ('1020', '100005', '1399906997', '66ffcc');
INSERT INTO `tb_datapoint_lite` VALUES ('1021', '100006', '1399906997', '30%');
INSERT INTO `tb_datapoint_lite` VALUES ('1022', '100007', '1399906997', '1');
INSERT INTO `tb_datapoint_lite` VALUES ('1023', '100005', '1399907025', '66ffcc');
INSERT INTO `tb_datapoint_lite` VALUES ('1024', '100006', '1399907025', '30%');
INSERT INTO `tb_datapoint_lite` VALUES ('1025', '100007', '1399907025', '1');
INSERT INTO `tb_datapoint_lite` VALUES ('1026', '100005', '1399907078', '66ffcc');
INSERT INTO `tb_datapoint_lite` VALUES ('1027', '100006', '1399907078', '30%');
INSERT INTO `tb_datapoint_lite` VALUES ('1028', '100007', '1399907078', '1');
INSERT INTO `tb_datapoint_lite` VALUES ('1029', '100005', '1399907131', '66ffcc');
INSERT INTO `tb_datapoint_lite` VALUES ('1030', '100006', '1399907131', '30%');
INSERT INTO `tb_datapoint_lite` VALUES ('1031', '100007', '1399907131', '1');
INSERT INTO `tb_datapoint_lite` VALUES ('1032', '100005', '1399907197', '66ffcc');
INSERT INTO `tb_datapoint_lite` VALUES ('1033', '100006', '1399907197', '30%');
INSERT INTO `tb_datapoint_lite` VALUES ('1034', '100007', '1399907197', '1');
INSERT INTO `tb_datapoint_lite` VALUES ('1035', '100005', '1400502382', 'ffffff');
INSERT INTO `tb_datapoint_lite` VALUES ('1036', '100006', '1400502382', '30%');
INSERT INTO `tb_datapoint_lite` VALUES ('1037', '100007', '1400502382', '1');
INSERT INTO `tb_datapoint_lite` VALUES ('1038', '100009', '1400580675', 'hello world');
INSERT INTO `tb_datapoint_lite` VALUES ('1039', '100009', '1400580763', 'hello world');
INSERT INTO `tb_datapoint_lite` VALUES ('1040', '100009', '1400580837', 'hello world');
INSERT INTO `tb_datapoint_lite` VALUES ('1041', '100009', '1400594511', 'hello world');
INSERT INTO `tb_datapoint_lite` VALUES ('1042', '100009', '1400594584', 'hello world');
INSERT INTO `tb_datapoint_lite` VALUES ('1043', '100009', '1400595887', 'hello world');
INSERT INTO `tb_datapoint_lite` VALUES ('1044', '100005', '1401553001', '123456');
INSERT INTO `tb_datapoint_lite` VALUES ('1049', '100005', '1402227627', '000000');
INSERT INTO `tb_datapoint_lite` VALUES ('1050', '100005', '1402227933', '123456');
INSERT INTO `tb_datapoint_lite` VALUES ('1051', '100005', '1402227983', '123456');

-- ----------------------------
-- Table structure for tb_datapoint_number
-- ----------------------------
DROP TABLE IF EXISTS `tb_datapoint_number`;
CREATE TABLE `tb_datapoint_number` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `sensor_id` int(11) NOT NULL,
  `timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `value` float NOT NULL,
  PRIMARY KEY (`id`),
  KEY `sensor_id` (`sensor_id`),
  CONSTRAINT `tb_datapoint_number_ibfk_1` FOREIGN KEY (`sensor_id`) REFERENCES `tb_sensor` (`id`) ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_datapoint_number
-- ----------------------------

-- ----------------------------
-- Table structure for tb_datapoint_value
-- ----------------------------
DROP TABLE IF EXISTS `tb_datapoint_value`;
CREATE TABLE `tb_datapoint_value` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `dp_id` int(11) NOT NULL,
  `key` varchar(30) NOT NULL,
  `value` varchar(100) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `dp_id` (`dp_id`),
  CONSTRAINT `tb_datapoint_value_ibfk_1` FOREIGN KEY (`dp_id`) REFERENCES `tb_datapoint` (`id`) ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_datapoint_value
-- ----------------------------

-- ----------------------------
-- Table structure for tb_device
-- ----------------------------
DROP TABLE IF EXISTS `tb_device`;
CREATE TABLE `tb_device` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  `tags` varchar(50) DEFAULT NULL,
  `about` varchar(200) DEFAULT NULL,
  `locate` varchar(50) DEFAULT NULL,
  `user_id` int(11) NOT NULL,
  `create_time` int(11) DEFAULT '0',
  `last_active` int(11) DEFAULT NULL,
  `status` tinyint(4) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `tb_device_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `tb_user` (`id`) ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=10020 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_device
-- ----------------------------
INSERT INTO `tb_device` VALUES ('3', '标题', '1,2', '描述', '位\'置', '1000', '0', '0', '1');
INSERT INTO `tb_device` VALUES ('6', '标题', '1,2,3', '描述', 'x,y,z', '10000', '0', '0', '1');
INSERT INTO `tb_device` VALUES ('1000', 'A', 'A', 'A', 'A', '1000', '0', '0', '1');
INSERT INTO `tb_device` VALUES ('10000', 'new_device', '', '', '', '10000', '1398080232', '1398154075', '1');
INSERT INTO `tb_device` VALUES ('10002', 'test', '', '', '', '1000', '1398089199', '1398089199', '1');
INSERT INTO `tb_device` VALUES ('10003', 'device from Postman', 'any tags?', 'This is a device created by Postman', 'Wuhan', '10000', '1398090692', '1398788244', '1');
INSERT INTO `tb_device` VALUES ('10007', '测试设备', null, '据说用中文很不专业', null, '10000', '1399738659', '1399739910', '1');
INSERT INTO `tb_device` VALUES ('10008', 'RGB Light', 'none', '带颜色的LED灯，RGB颜色可调，亮度可调，附带开关', 'Wuhan', '10000', '1399861186', '1402227627', '1');
INSERT INTO `tb_device` VALUES ('10009', 'Normal Light', 'none', 'Normal light with a switch only.', '武汉', '10000', '1399861676', '1399861676', '1');
INSERT INTO `tb_device` VALUES ('10010', 'device from java', null, null, null, '10000', '1400572848', '1400574407', '0');
INSERT INTO `tb_device` VALUES ('10011', 'java@2014-05-20 20:08:55', 'tags', 'about', 'locate', '10000', '1400587735', null, '1');
INSERT INTO `tb_device` VALUES ('10012', 'java-device@2014-05-20 20:15:42', 'tags', 'device created by java api, at 2014-05-20 20:15:42', 'locate', '10000', '1400588142', null, '1');
INSERT INTO `tb_device` VALUES ('10019', 'java-device@2014-05-20 22:24:47', 'tags', 'device created by java api, at 2014-05-20 22:24:47', 'locate', '10000', '1400595887', null, '1');

-- ----------------------------
-- Table structure for tb_sensor
-- ----------------------------
DROP TABLE IF EXISTS `tb_sensor`;
CREATE TABLE `tb_sensor` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  `type` int(11) NOT NULL,
  `tags` varchar(50) DEFAULT NULL,
  `about` varchar(200) DEFAULT NULL,
  `device_id` int(11) NOT NULL,
  `last_update` int(11) DEFAULT '0',
  `last_data` varchar(100) DEFAULT NULL,
  `status` tinyint(4) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `device_id` (`device_id`),
  KEY `type` (`type`),
  CONSTRAINT `tb_sensor_ibfk_1` FOREIGN KEY (`device_id`) REFERENCES `tb_device` (`id`) ON UPDATE CASCADE,
  CONSTRAINT `tb_sensor_ibfk_2` FOREIGN KEY (`type`) REFERENCES `tb_sensor_type` (`id`) ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=100015 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_sensor
-- ----------------------------
INSERT INTO `tb_sensor` VALUES ('1', 'Sensor\'Name', '1', '标签1,标签2,标签3', '一些描述', '1000', '0', null, '1');
INSERT INTO `tb_sensor` VALUES ('2', 'Sensor\'Name', '1', '标签1,标签2,标签3', '一些描述', '1000', '0', null, '1');
INSERT INTO `tb_sensor` VALUES ('100000', 'SensorTest', '1', null, null, '10000', '1398152829', null, '1');
INSERT INTO `tb_sensor` VALUES ('100001', 'SensorTest', '1', null, null, '10000', '1398154572', null, '1');
INSERT INTO `tb_sensor` VALUES ('100002', 'sensor from Postman', '1', 'any tags?', 'This is a sensor created by Postman', '10000', '1398857584', null, '1');
INSERT INTO `tb_sensor` VALUES ('100003', 'put check', '1', 'none', 'no zuo no die', '10000', '1398861831', null, '0');
INSERT INTO `tb_sensor` VALUES ('100004', '传感器实例-改', '1', null, null, '10007', '1399898159', '66ccff', '1');
INSERT INTO `tb_sensor` VALUES ('100005', 'RGB', '1', 'none', 'Used to set RGB value of the light', '10008', '1402227983', '123456', '1');
INSERT INTO `tb_sensor` VALUES ('100006', 'Brightness', '1', 'none', 'Used to set Brightness of the light', '10008', '1400502382', '30%', '1');
INSERT INTO `tb_sensor` VALUES ('100007', 'Switch', '3', 'none', 'Switch of the light', '10008', '1400502382', '1', '1');
INSERT INTO `tb_sensor` VALUES ('100008', 'Switch', '3', 'none', 'Used to switch a light.', '10009', '1399898006', '1', '1');
INSERT INTO `tb_sensor` VALUES ('100009', 'zhangxing', '1', 'test', 'test', '10008', '1400595887', 'hello world', '1');
INSERT INTO `tb_sensor` VALUES ('100010', 'java-sensor@2014-05-20 20:17:46', '1', 'tags', 'sensor created by java api, at 2014-05-20 20:17:46', '10012', null, null, '1');
INSERT INTO `tb_sensor` VALUES ('100011', 'java-sensor@2014-05-20 21:58:51', '1', 'tags', 'sensor created by java api, at 2014-05-20 21:58:51', '10012', null, null, '1');
INSERT INTO `tb_sensor` VALUES ('100012', 'java-sensor@2014-05-20 22:01:51', '1', 'tags', 'sensor created by java api, at 2014-05-20 22:01:51', '10012', null, null, '1');
INSERT INTO `tb_sensor` VALUES ('100013', 'java-sensor@2014-05-20 22:03:04', '1', 'tags', 'sensor created by java api, at 2014-05-20 22:03:04', '10012', null, null, '1');
INSERT INTO `tb_sensor` VALUES ('100014', 'java-sensor@2014-05-20 22:24:47', '1', 'tags', 'sensor created by java api, at 2014-05-20 22:24:47', '10012', null, null, '1');

-- ----------------------------
-- Table structure for tb_sensor_type
-- ----------------------------
DROP TABLE IF EXISTS `tb_sensor_type`;
CREATE TABLE `tb_sensor_type` (
  `id` int(11) NOT NULL,
  `name` varchar(50) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `status` tinyint(4) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_sensor_type
-- ----------------------------
INSERT INTO `tb_sensor_type` VALUES ('1', 'General Sensor', 'Used to store json object string value', '1');
INSERT INTO `tb_sensor_type` VALUES ('2', 'Numeric sensor', 'Used to get numeric value', '1');
INSERT INTO `tb_sensor_type` VALUES ('3', 'Boolean Sensor', 'Boolean 0 and 1, used as a switch', '1');

-- ----------------------------
-- Table structure for tb_user
-- ----------------------------
DROP TABLE IF EXISTS `tb_user`;
CREATE TABLE `tb_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(30) NOT NULL,
  `password` varchar(100) NOT NULL,
  `email` varchar(50) NOT NULL,
  `token` varchar(50) NOT NULL,
  `token_exptime` int(11) NOT NULL,
  `status` tinyint(4) NOT NULL DEFAULT '0',
  `regtime` int(11) NOT NULL,
  `apikey` varchar(100) NOT NULL,
  `about` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `unique_name` (`username`),
  UNIQUE KEY `unique_email` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=10001 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_user
-- ----------------------------
INSERT INTO `tb_user` VALUES ('10', 'hust', 'e60a6985ef76c751abfaee35c2df6279', 'muyuxingguang@163.com', 'acf5d80947cc5a299d50dd2f7f444727', '1395197925', '1', '1395111525', '0014742854278810d91b8c7ff24ccb83', '我爱你，1314');
INSERT INTO `tb_user` VALUES ('100', 'mains', '1234', 'mains2114@gmail.com', '1234567890', '0', '1', '0', '4ace551f16e9b3b8b3381ebb06c60c44', null);
INSERT INTO `tb_user` VALUES ('1000', 'admin', '81dc9bdb52d04dc20036dbd8313ed055', 'admin@linpcloud.com', '1234567890', '0', '1', '0', '3f438b9720b17eb1fefde189069fe759', null);
INSERT INTO `tb_user` VALUES ('10000', 'linpcloud', '81dc9bdb52d04dc20036dbd8313ed055', 'linpcloud@linpcloud.com', '1234567890', '0', '1', '0', '1a39ad4c87ba09ef861ead97f010df7b', 'linpcloud test user');

-- ----------------------------
-- Table structure for tb_user_token
-- ----------------------------
DROP TABLE IF EXISTS `tb_user_token`;
CREATE TABLE `tb_user_token` (
  `user_id` int(11) NOT NULL,
  `token` varchar(50) NOT NULL,
  `deadline` int(11) NOT NULL,
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `access_token` (`token`),
  CONSTRAINT `tb_user_token_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `tb_user` (`id`) ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_user_token
-- ----------------------------
INSERT INTO `tb_user_token` VALUES ('10000', '203ee1b9ab49ddd9b7ceb03077d3c594f8dac79a', '1400494285');
