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

/*Table structure for table `picture` */

DROP TABLE IF EXISTS `picture`;

CREATE TABLE `picture` (
  `ID` varchar(36) COLLATE utf8_bin NOT NULL COMMENT '编号',
  `imgUrl` varchar(500) COLLATE utf8_bin DEFAULT NULL COMMENT '图片URL地址',
  `imgType` int(1) DEFAULT NULL COMMENT '图片类型',
  `CreateTime` datetime DEFAULT NULL COMMENT '创建时间',
  `UpdateTime` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Data for the table `picture` */

insert  into `picture`(`ID`,`imgUrl`,`imgType`,`CreateTime`,`UpdateTime`) values ('8a60c6a05557dba9015557dce7a80000','e3f1d5ed-a929-4746-a257-8f0e84b9e966.jpg',1,'2016-06-16 14:19:02',NULL);

/*Table structure for table `system_permission` */

DROP TABLE IF EXISTS `system_permission`;

CREATE TABLE `system_permission` (
  `ID` varchar(36) COLLATE utf8_bin NOT NULL COMMENT '编号',
  `PERMISSION_NAME` varchar(36) COLLATE utf8_bin DEFAULT NULL COMMENT '权限名称',
  `PID` varchar(36) COLLATE utf8_bin DEFAULT NULL COMMENT '父节点ID',
  `TYPE` int(1) DEFAULT NULL COMMENT '资源类型 0菜单 1按钮',
  `STATUS` int(1) DEFAULT NULL COMMENT '状态启用 0启用 1停用',
  `URL` varchar(500) COLLATE utf8_bin DEFAULT NULL COMMENT 'URL地址',
  `ICON` varchar(500) COLLATE utf8_bin DEFAULT NULL COMMENT '图标',
  `SORT` int(1) DEFAULT NULL COMMENT '排序',
  `DESCRIPTION` varchar(36) COLLATE utf8_bin DEFAULT NULL COMMENT '描述',
  PRIMARY KEY (`ID`),
  KEY `FK_PID` (`PID`),
  CONSTRAINT `FK_PID` FOREIGN KEY (`PID`) REFERENCES `system_permission` (`ID`) ON DELETE CASCADE ON UPDATE CASCADE
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

insert  into `system_role`(`ID`,`ROLE_NAME`) values ('7fec3422-efee-47b9-920c-689024af6942','普通用户'),('b1cdbaf6-6db2-488a-a5d4-dc401c609d20','超级管理员');

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
  `ENABLED` int(1) DEFAULT NULL COMMENT '是否启用',
  `CREATE_TIME` datetime DEFAULT NULL COMMENT '创建时间',
  `UPDATE_TIME` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Data for the table `system_user` */

insert  into `system_user`(`ID`,`FIRST_NAME`,`LAST_NAME`,`USER_NAME`,`PASSWORD`,`EMAIL`,`ENABLED`,`CREATE_TIME`,`UPDATE_TIME`) values ('9f9d8b55-68c0-46a8-8f60-fa93c17b2bb6','何','壹轩','HeYixuan','218b466bae5298bf2fbe5d7b908c4350','15517551511@126.com',0,'2016-06-29 17:10:53',NULL);

/*Table structure for table `system_user_role` */

DROP TABLE IF EXISTS `system_user_role`;

CREATE TABLE `system_user_role` (
  `ID` varchar(36) COLLATE utf8_bin NOT NULL COMMENT '编号',
  `SYSTEM_USER_ID` varchar(36) COLLATE utf8_bin NOT NULL COMMENT '外键 系统用户编号',
  `SYSTEM_ROLE_ID` varchar(36) COLLATE utf8_bin NOT NULL COMMENT '外键 角色编号',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Data for the table `system_user_role` */

insert  into `system_user_role`(`ID`,`SYSTEM_USER_ID`,`SYSTEM_ROLE_ID`) values ('2033b456-e571-4df1-864e-3d8395010ed0','9f9d8b55-68c0-46a8-8f60-fa93c17b2bb6','b1cdbaf6-6db2-488a-a5d4-dc401c609d20');

/*Table structure for table `users` */

DROP TABLE IF EXISTS `users`;

CREATE TABLE `users` (
  `ID` varchar(36) COLLATE utf8_bin NOT NULL COMMENT '编号',
  `UserName` varchar(6) COLLATE utf8_bin DEFAULT NULL COMMENT '用户名',
  `Password` varchar(500) COLLATE utf8_bin DEFAULT NULL COMMENT '密码',
  `Email` varchar(36) COLLATE utf8_bin DEFAULT NULL COMMENT '邮箱',
  `isEnable` int(1) DEFAULT NULL COMMENT '是否启用  1:启用 0:停用',
  `CreateTime` datetime DEFAULT NULL COMMENT '创建时间',
  `UpdateTime` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Data for the table `users` */

insert  into `users`(`ID`,`UserName`,`Password`,`Email`,`isEnable`,`CreateTime`,`UpdateTime`) values ('8a60c6a0554e2a1301554e2aaf4c0000','何壹轩何','123456','15517551511@126.com',1,'2016-06-14 17:07:26','2016-06-14 17:07:26'),('8a60c6a0554e2a1301554e2ad7ca0001','何壹轩','123456','15517551511@126.com',1,'2016-06-14 17:07:26','2016-06-14 17:07:26'),('8a60c6a4557b948101557b95efde0000','何壹轩何','123456','15517551511@126.com',1,'2016-06-23 12:47:51','2016-06-23 12:47:51'),('8a60c6a4557b948101557b95f3390001','何壹轩','123456','15517551511@126.com',1,'2016-06-23 12:47:51','2016-06-23 12:47:51');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;


SELECT * FROM SYSTEM_USER;

 SELECT   
      systemUser.`ID` AS SystemUserID,
      systemUser.`USER_NAME` AS UserName,
      systemUser.`PASSWORD` AS PASSWORD,
      role.`ID` AS ROLE_ID,
      role.`ROLE_NAME` AS ROLE_NAME,
      p.`ID` AS PERMISSION_ID,
      p.`PERMISSION_NAME` AS PERMISSION_NAME  
    FROM  
      SYSTEM_USER AS systemUser,  
      SYSTEM_ROLE AS role,  
      SYSTEM_PERMISSION AS p,
      SYSTEM_ROLE_PERMISSION AS pr,  
      SYSTEM_USER_ROLE AS ur
    WHERE  
      systemUser.`ID` = ur.`SYSTEM_USER_ID` 
    AND  
      role.`ID` = ur.`SYSTEM_ROLE_ID`
    AND  
      p.`ID` = pr.`PERMISSION_ID`  
    AND  
      role.`ID` = pr.`SYSTEM_ROLE_ID`;

SELECT * FROM SYSTEM_USER;9f9d8b55-68c0-46a8-8f60-fa93c17b2bb6
SELECT * FROM SYSTEM_ROLE; b1cdbaf6-6db2-488a-a5d4-dc401c609d20
SELECT * FROM SYSTEM_USER_ROLE;

SELECT * FROM SYSTEM_USER AS U,system_role AS r,system_user_role AS ur
WHERE ur.`SYSTEM_USER_ID` = U.`ID` AND UR.`SYSTEM_ROLE_ID` = R.`ID`;