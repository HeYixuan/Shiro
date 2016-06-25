/*
SQLyog v10.2 
MySQL - 5.5.28 : Database - auto
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`auto` /*!40100 DEFAULT CHARACTER SET utf8 COLLATE utf8_bin */;

USE `auto`;

/*Table structure for table `system_permission` */

DROP TABLE IF EXISTS `system_permission`;

CREATE TABLE `system_permission` (
  `ID` varchar(36) COLLATE utf8_bin NOT NULL COMMENT '编号',
  `PERMISSION_NAME` varchar(36) COLLATE utf8_bin DEFAULT NULL COMMENT '权限名称',
  `URL` varchar(500) COLLATE utf8_bin DEFAULT NULL COMMENT 'URL地址',
  `ICON` varchar(500) COLLATE utf8_bin DEFAULT NULL COMMENT '图标',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Data for the table `system_permission` */

/*Table structure for table `system_role` */

DROP TABLE IF EXISTS `system_role`;

CREATE TABLE `system_role` (
  `ID` varchar(36) COLLATE utf8_bin NOT NULL COMMENT '编号',
  `ROLE_NAME` varchar(36) COLLATE utf8_bin DEFAULT NULL COMMENT '角色名称',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Data for the table `system_role` */

/*Table structure for table `system_role_permission` */

DROP TABLE IF EXISTS `system_role_permission`;

CREATE TABLE `system_role_permission` (
  `ID` varchar(36) COLLATE utf8_bin NOT NULL COMMENT '编号',
  `SYSTEM_ROLE_ID` varchar(36) COLLATE utf8_bin NOT NULL COMMENT '外键 系统角色编号',
  `PERMISSION_ID` varchar(36) COLLATE utf8_bin NOT NULL COMMENT '外键 权限编号',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Data for the table `system_role_permission` */

/*Table structure for table `system_user` */

DROP TABLE IF EXISTS `system_user`;

CREATE TABLE `system_user` (
  `ID` varchar(36) COLLATE utf8_bin NOT NULL COMMENT '编号',
  `FIRST_NAME` varchar(4) COLLATE utf8_bin DEFAULT NULL COMMENT '姓氏',
  `LAST_NAME` varchar(4) COLLATE utf8_bin DEFAULT NULL COMMENT '名称',
  `USER_NAME` varchar(36) COLLATE utf8_bin DEFAULT NULL COMMENT '用户名',
  `PASSWORD` varchar(500) COLLATE utf8_bin DEFAULT NULL COMMENT '密码',
  `EMAIL` varchar(20) COLLATE utf8_bin DEFAULT NULL COMMENT '电子邮箱',
  `ENABLED` tinyint(1) DEFAULT NULL COMMENT '是否启用',
  `CREATE_USER` varchar(36) COLLATE utf8_bin DEFAULT NULL COMMENT '创建人',
  `CREATE_TIME` date DEFAULT NULL COMMENT '创建时间',
  `UPDATE_USER` varchar(36) COLLATE utf8_bin DEFAULT NULL COMMENT '修改人',
  `UPDATE_TIME` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Data for the table `system_user` */

/*Table structure for table `system_user_role` */

DROP TABLE IF EXISTS `system_user_role`;

CREATE TABLE `system_user_role` (
  `ID` varchar(36) COLLATE utf8_bin NOT NULL COMMENT '编号',
  `SYSTEM_USER_ID` varchar(36) COLLATE utf8_bin NOT NULL COMMENT '外键 系统用户编号',
  `SYSTEM_ROLE_ID` varchar(36) COLLATE utf8_bin NOT NULL COMMENT '外键 角色编号',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Data for the table `system_user_role` */

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
