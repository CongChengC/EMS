/*
SQLyog Ultimate v10.00 Beta1
MySQL - 5.1.62-community : Database - oa_center
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`oa_center` /*!40100 DEFAULT CHARACTER SET gb2312 */;

USE `oa_center`;

/*Table structure for table `oa_address` */

DROP TABLE IF EXISTS `oa_address`;

CREATE TABLE `oa_address` (
  `ADDRESS_ID` int(11) NOT NULL AUTO_INCREMENT,
  `CITY_ID` int(11) DEFAULT NULL,
  `POST_CODE` varchar(255) DEFAULT NULL,
  `DETAILS` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ADDRESS_ID`),
  KEY `FK7AFCE667ED58511F` (`CITY_ID`),
  CONSTRAINT `FK7AFCE667ED58511F` FOREIGN KEY (`CITY_ID`) REFERENCES `oa_city` (`CITY_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=gb2312;

/*Data for the table `oa_address` */

/*Table structure for table `oa_authority` */

DROP TABLE IF EXISTS `oa_authority`;

CREATE TABLE `oa_authority` (
  `AUTHORITY_ID` int(11) NOT NULL AUTO_INCREMENT,
  `AUTHORITY_NAME` varchar(255) DEFAULT NULL,
  `DISPLAY_NAME` varchar(255) DEFAULT NULL,
  `PARENT_AUTHORITY_ID` int(11) DEFAULT NULL,
  `RESOURCE_ID` int(11) DEFAULT NULL,
  `RELATED_AUTHORITIES` varchar(255) DEFAULT NULL,
  `MAIN_RESOURCE_ID` int(11) DEFAULT NULL,
  PRIMARY KEY (`AUTHORITY_ID`),
  KEY `FKD3307CF69EE342A` (`PARENT_AUTHORITY_ID`),
  KEY `FKD3307CF61980FFBF` (`RESOURCE_ID`),
  KEY `FK_s18a2k22fvmsa5cu7x2vfoxfj` (`MAIN_RESOURCE_ID`),
  CONSTRAINT `FKD3307CF61980FFBF` FOREIGN KEY (`RESOURCE_ID`) REFERENCES `oa_resource` (`RESOURCE_ID`),
  CONSTRAINT `FKD3307CF69EE342A` FOREIGN KEY (`PARENT_AUTHORITY_ID`) REFERENCES `oa_authority` (`AUTHORITY_ID`),
  CONSTRAINT `FK_s18a2k22fvmsa5cu7x2vfoxfj` FOREIGN KEY (`MAIN_RESOURCE_ID`) REFERENCES `oa_resource` (`RESOURCE_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=gb2312;

/*Data for the table `oa_authority` */

insert  into `oa_authority`(`AUTHORITY_ID`,`AUTHORITY_NAME`,`DISPLAY_NAME`,`PARENT_AUTHORITY_ID`,`RESOURCE_ID`,`RELATED_AUTHORITIES`,`MAIN_RESOURCE_ID`) values (1,'ROLE_EMP_SAVE','员工添加',8,1,NULL,1),(2,'ROLE_EMP_DELETE','员工删除',8,NULL,',3,',NULL),(3,'ROLE_EMP_LIST','员工查询',8,4,NULL,4),(4,'ROLE_EMP_UPDATE','员工修改',8,NULL,',3,',NULL),(5,'ROLE_EMP_DOWNLOAD','员工信息下载',8,NULL,NULL,NULL),(7,'ROLE_EMP_UPLOAD','员工信息上传',8,NULL,NULL,5),(8,'ROLE_EMP','员工管理',NULL,NULL,NULL,NULL),(9,'ROLE_ROLE_SAVE','角色添加',13,NULL,NULL,7),(10,'ROLE_ROLE_DELETE','角色删除',13,NULL,',11,',NULL),(11,'ROLE_ROLE_LIST','角色查询',13,NULL,NULL,6),(12,'ROLE_ROLE_UPDATE','角色修改',13,NULL,',11,',NULL),(13,'ROLE_ROLE','角色管理',NULL,NULL,NULL,NULL);

/*Table structure for table `oa_authority_resource` */

DROP TABLE IF EXISTS `oa_authority_resource`;

CREATE TABLE `oa_authority_resource` (
  `AUTHORITY_ID` int(11) NOT NULL,
  `RESOUCE_ID` int(11) NOT NULL,
  `RESOURCE_ID` int(11) NOT NULL,
  PRIMARY KEY (`AUTHORITY_ID`,`RESOUCE_ID`),
  KEY `FK33E49D373284EEB5` (`AUTHORITY_ID`),
  KEY `FK33E49D377EE3EC25` (`RESOUCE_ID`),
  KEY `FK_iv67yilyadg7seuoa3kkum1o` (`RESOURCE_ID`),
  CONSTRAINT `FK33E49D373284EEB5` FOREIGN KEY (`AUTHORITY_ID`) REFERENCES `oa_authority` (`AUTHORITY_ID`),
  CONSTRAINT `FK33E49D377EE3EC25` FOREIGN KEY (`RESOUCE_ID`) REFERENCES `oa_resource` (`RESOURCE_ID`),
  CONSTRAINT `FK_iv67yilyadg7seuoa3kkum1o` FOREIGN KEY (`RESOURCE_ID`) REFERENCES `oa_resource` (`RESOURCE_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=gb2312;

/*Data for the table `oa_authority_resource` */

insert  into `oa_authority_resource`(`AUTHORITY_ID`,`RESOUCE_ID`,`RESOURCE_ID`) values (1,1,1),(1,2,2),(2,3,3),(3,4,4),(7,5,5),(11,6,6),(9,7,7);

/*Table structure for table `oa_city` */

DROP TABLE IF EXISTS `oa_city`;

CREATE TABLE `oa_city` (
  `CITY_ID` int(11) NOT NULL AUTO_INCREMENT,
  `CITY_NAME` varchar(255) DEFAULT NULL,
  `PROVINCE_ID` int(11) DEFAULT NULL,
  PRIMARY KEY (`CITY_ID`),
  KEY `FKC75193B83563147F` (`PROVINCE_ID`),
  CONSTRAINT `FKC75193B83563147F` FOREIGN KEY (`PROVINCE_ID`) REFERENCES `oa_province` (`PROVINCE_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=gb2312;

/*Data for the table `oa_city` */

/*Table structure for table `oa_department` */

DROP TABLE IF EXISTS `oa_department`;

CREATE TABLE `oa_department` (
  `DEPARTMENT_ID` int(11) NOT NULL AUTO_INCREMENT,
  `DEPARTMENT_NAME` varchar(255) DEFAULT NULL,
  `MANAGER_ID` int(11) DEFAULT NULL,
  PRIMARY KEY (`DEPARTMENT_ID`),
  KEY `FK1EE083DFC01B3E60` (`MANAGER_ID`),
  CONSTRAINT `FK1EE083DFC01B3E60` FOREIGN KEY (`MANAGER_ID`) REFERENCES `oa_employee` (`EMPLOYEE_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=gb2312;

/*Data for the table `oa_department` */

insert  into `oa_department`(`DEPARTMENT_ID`,`DEPARTMENT_NAME`,`MANAGER_ID`) values (1,'开发部',13),(2,'公关部',3),(3,'行政部',4),(4,'人事部',6);

/*Table structure for table `oa_employee` */

DROP TABLE IF EXISTS `oa_employee`;

CREATE TABLE `oa_employee` (
  `EMPLOYEE_ID` int(11) NOT NULL AUTO_INCREMENT,
  `LOGIN_NAME` varchar(255) DEFAULT NULL,
  `EMPLOYEE_NAME` varchar(255) DEFAULT NULL,
  `PASSWORD` varchar(255) DEFAULT NULL,
  `ENABLED` int(11) DEFAULT NULL,
  `DEPARTMENT_ID` int(11) DEFAULT NULL,
  `BIRTH` date DEFAULT NULL,
  `GENDER` varchar(255) DEFAULT NULL,
  `EMAIL` varchar(255) DEFAULT NULL,
  `MOBILE_PHONE` varchar(255) DEFAULT NULL,
  `VISITED_TIMES` int(11) DEFAULT NULL,
  `IS_DELETED` int(11) DEFAULT NULL,
  `EDITOR_ID` int(11) DEFAULT NULL,
  PRIMARY KEY (`EMPLOYEE_ID`),
  KEY `FK7466EA7B3EF26BFF` (`DEPARTMENT_ID`),
  KEY `FK7466EA7BBD09D4A0` (`EDITOR_ID`),
  CONSTRAINT `FK7466EA7B3EF26BFF` FOREIGN KEY (`DEPARTMENT_ID`) REFERENCES `oa_department` (`DEPARTMENT_ID`),
  CONSTRAINT `FK7466EA7BBD09D4A0` FOREIGN KEY (`EDITOR_ID`) REFERENCES `oa_employee` (`EMPLOYEE_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=109 DEFAULT CHARSET=gb2312;

/*Data for the table `oa_employee` */

insert  into `oa_employee`(`EMPLOYEE_ID`,`LOGIN_NAME`,`EMPLOYEE_NAME`,`PASSWORD`,`ENABLED`,`DEPARTMENT_ID`,`BIRTH`,`GENDER`,`EMAIL`,`MOBILE_PHONE`,`VISITED_TIMES`,`IS_DELETED`,`EDITOR_ID`) values (1,'AAAAAA','xiaoyong','06e5fff4a2dfa8bafcee6ffda4feb377',1,2,NULL,'1','aa@163.com','',91,1,NULL),(2,'AABBCC','王立波','34ee23bbe5213dbccc685a9213a646f2',1,3,'2014-04-07','0','aa@163.com','13130012811',186,1,3),(3,'ABCDEFG','子文','1234567890',1,4,'1994-08-24','0','ziwen@163.com','13456778899',10,1,5),(4,'zhengLiBo','郑立波','123456',1,1,'2014-04-09','1','libo@126.com','13567890099',5,1,2),(5,'FM12345','真味','123456',1,3,'2014-04-01','1','zhenwei@163.com','13456789900',82,0,2),(6,'aAAAAAA','AAAAbbbbbbbbb',NULL,1,4,'2014-04-10','1','xx@163.com','13455443311',0,100,2),(7,'aAAAAAA2','ABcd','123456',1,2,'2014-04-07','1','xx@163.com','13455443311',1,1,2),(12,'AABBCC2','ABC2','123456',1,4,'2014-04-02','1','xx@163.com','13455443311',10,0,2),(13,'ATGUIGU8','尚硅谷','123456',1,3,'2014-04-05','1','atguigu@163.com','189',25,1,2),(14,'ATGUIGU82','尚硅谷','123456',0,1,'1990-12-14','1','atguigu@163.com','188',0,1,2),(15,'ABCVFGA','夏磊',NULL,1,3,'2009-03-07','1','xialei@163.com','18900112244',10,1,2),(16,'abcdsss','acc','123456',1,1,'2015-01-08','0','a@163.com','18900009999',0,1,NULL),(18,'abcdsss3','acc3','123456',0,2,NULL,'1','a@163.com',NULL,0,1,NULL),(19,'AAMMNNV22','搜狗5222','123456',1,2,'2015-01-07','1','amv2@163.com','19012345672',0,1,5),(20,'AABBCC3','A','123456',1,2,'2015-01-06','1','a@163.com','18799990000',0,1,5),(27,'ABCCCCC','xiaoyong','123456',1,2,NULL,'1','',NULL,0,1,5),(28,'AABBCC-2','王立波','123456',1,3,NULL,'0','aa@163.com',NULL,0,1,5),(30,'zhengLiBo-2','郑立波','123456',1,3,NULL,'0','libo@126.com',NULL,0,1,5),(31,'FM1AAAAA','真味','123456',1,3,NULL,'1','zhenwei@163.com',NULL,0,1,5),(32,'Aaaaaaa12','AAAA','123456',1,2,NULL,'1','xx@163.com',NULL,0,1,5),(34,'ACCMMMM','Aa','123456',1,3,'2015-01-05','1','a@163.com','18900008877',0,0,5),(36,'abzxcv','a','f7925457537dc76c9a240dfec3a16fee',1,2,'2014-12-30','1','z@187.com','19088776655',5,0,5),(40,'Aaaaaaa2222','Aaaaaaa-2222','123456',1,3,'2015-03-02','0','Aaaaaaa-2222@163.com','19012345678',11,1,5),(51,'AAAAAA88','xiaoyong',NULL,0,2,NULL,'1',NULL,NULL,0,1,NULL),(52,'AABBCC88','王立波',NULL,1,3,NULL,'0','aa@163.com',NULL,0,0,NULL),(53,'ABCDEFG88','子文',NULL,1,2,NULL,'1','ziwen@163.com',NULL,0,1,NULL),(56,'Aaaaaaa22224','Aaaaaaa-2222','123456',1,1,'2015-05-05','1','Aaaaaaa-2222@163.com','19012345678',0,1,4),(57,'Aazzzzzzz','xiaoyong','123456',1,2,NULL,'1',NULL,NULL,0,1,4),(58,'AABBCCzz67','王立波','123456',1,3,NULL,'1','aa@163.com',NULL,0,0,4),(59,'ABCDEFGzz33','子文','123456',1,2,NULL,'1','ziwen@163.com',NULL,0,1,4),(60,'zhengLiBozz55','郑立波','123456',1,3,NULL,'0','libo@126.com',NULL,0,0,4),(61,'FM12345zz44','真味','123456',1,3,NULL,'1','zhenwei@163.com',NULL,0,0,4),(62,'aAAAAAAzz22','AAAA','123456',1,2,NULL,'1','xx@163.com',NULL,0,0,4),(63,'zhengLiBozz11','ABcd','123456',1,2,NULL,'1','xx@163.com',NULL,0,0,4),(64,'AAAAAA5','搜狗','123456',1,2,'2015-06-04','1','amv@163.com','19012345678',0,0,5),(66,'A111111111','aa','123456',1,2,'2015-06-27','1','a@163.com',NULL,0,0,2),(67,'B111111111','bb','123456',1,3,'2015-06-27','1','a@163.com',NULL,0,0,2),(68,'C111111111','cc','123456',0,4,'2015-06-27','0','a@163.com',NULL,0,0,2),(69,'Aaaaaaa2222dd','Aaaaaaa-2222','123456',1,3,'2015-08-06','1','amv@163.com','19012345678',0,1,5),(70,'AAAAAA11','xiaoyong','123456',1,2,'2015-08-04','1','aa@163.com',NULL,0,0,2),(71,'ABCDEFG11','子文','123456',1,2,'2015-08-04','1','ziwen@163.com',NULL,0,0,2),(72,'AABBCC11','王立波','123456',0,3,'2015-08-04','0','aa@163.com',NULL,0,0,2),(73,'zhengLiBo11','郑立波','123456',1,3,'2015-08-04','1','libo@126.com',NULL,0,0,2),(74,'FM1234511','真味','123456',1,3,'2015-08-04','1','zhenwei@163.com',NULL,1,0,2),(75,'AAAAAA112','xiaoyong','123456',1,2,'2015-08-04','1','aa@163.com',NULL,0,0,2),(76,'AABBCC112','王立波','123456',0,3,'2015-08-04','0','aa@163.com',NULL,0,0,2),(77,'ABCDEFG112','子文','123456',1,2,'2015-08-04','1','ziwen@163.com',NULL,0,0,2),(78,'zhengLiBo112','郑立波','123456',1,3,'2015-08-04','1','libo@126.com',NULL,0,0,2),(79,'FM12345112','真味','123456',1,3,'2015-08-04','1','zhenwei@163.com',NULL,0,0,2),(80,'AAAAAAgg','xiaoyong',NULL,1,2,NULL,'1','aa@163.com',NULL,0,0,5),(81,'AABBCCgg','王立波',NULL,1,3,NULL,'0','aa@163.com',NULL,0,0,5),(82,'ABCDEFGgg','子文',NULL,1,2,NULL,'1','ziwen@163.com',NULL,0,0,5),(83,'zhengLiBogg','郑立波',NULL,1,3,NULL,'1','libo@126.com',NULL,0,0,5),(84,'FM12345gg','真味',NULL,1,3,NULL,'1','zhenwei@163.com',NULL,0,0,5),(85,'ABCDEFG6666','搜狗','12345678',1,4,'2111-11-11','1','amv@163.com','19012345678',11,1,5),(86,'C1111111115','cc',NULL,0,4,NULL,'1','a@163.com',NULL,0,0,NULL),(87,'C1111111115','cc',NULL,0,4,NULL,'0','a@163.com',NULL,0,0,NULL),(88,'C1111111115','cc',NULL,1,4,NULL,'0','a@163.com',NULL,0,0,NULL),(89,'Aaaaaaa2222dd5','Aaaaaaa-2222',NULL,1,3,NULL,'1','amv@163.com',NULL,0,0,NULL),(90,'Aaaaaaa2222dd5','Aaaaaaa-2222',NULL,1,3,NULL,'1','amv@163.com',NULL,0,0,NULL),(91,'Aaaaaaa2222dd5','Aaaaaaa-2222',NULL,1,3,NULL,'1','amv@163.com',NULL,0,0,NULL),(94,'ABCDDDDD','A','123456',0,4,'1990-11-11','0','a@163.com','18711009900',0,1,74),(96,'zxxsssss','zxxsssss','123456',1,3,'2015-11-03','0','zxxsssss@163.com','18900000000',0,0,5),(97,'accccccc2','z1',NULL,1,1,NULL,'1','abc5@163.com',NULL,0,0,NULL),(98,'acccc4444','z2',NULL,0,1,NULL,'1','abc@163.com',NULL,0,0,NULL),(99,'acxswertyoopp','z3',NULL,1,3,NULL,'1','abc2@163.com',NULL,0,0,NULL),(100,'AABBCC3333','z4',NULL,0,1,NULL,'0','abc3@163.com',NULL,0,1,NULL),(101,'ABCCVVFF','张晶晶','123456',1,3,'2015-11-04','0','zxcc@163.com','19012345678',0,0,5),(103,'AABBXXSS','搜狗','123456',1,2,'2015-11-05','1','amv@163.com','19012345678',1,0,5),(104,'AABBCCwxx','Aaaaaaa-2222','123456',1,3,'2015-11-04','1','amv@163.com','189111111111',0,0,5),(105,'ACXXXEEE','c','123456',1,1,NULL,'1','a@163.com',NULL,0,0,NULL),(106,'AABBCC133','d','123456',1,2,NULL,'0','b@163.com',NULL,0,0,NULL),(107,'AADDDCCSW','e','123456',0,4,NULL,'1','c@163.com',NULL,0,0,NULL),(108,'AXXXSSSA','f','123456',0,4,NULL,'1','d@163.com',NULL,0,0,NULL);

/*Table structure for table `oa_employee_role` */

DROP TABLE IF EXISTS `oa_employee_role`;

CREATE TABLE `oa_employee_role` (
  `EMPLOYEE_ID` int(11) NOT NULL,
  `ROLE_ID` int(11) NOT NULL,
  PRIMARY KEY (`EMPLOYEE_ID`,`ROLE_ID`),
  KEY `FK80A09BBA109FF13F` (`ROLE_ID`),
  KEY `FK80A09BBA61DE7BBF` (`EMPLOYEE_ID`),
  CONSTRAINT `FK80A09BBA109FF13F` FOREIGN KEY (`ROLE_ID`) REFERENCES `oa_role` (`ROLE_ID`),
  CONSTRAINT `FK80A09BBA61DE7BBF` FOREIGN KEY (`EMPLOYEE_ID`) REFERENCES `oa_employee` (`EMPLOYEE_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=gb2312;

/*Data for the table `oa_employee_role` */

insert  into `oa_employee_role`(`EMPLOYEE_ID`,`ROLE_ID`) values (13,1),(15,1),(16,1),(18,1),(27,1),(30,1),(31,1),(36,1),(51,1),(56,1),(57,1),(61,1),(64,1),(66,1),(67,1),(68,1),(69,1),(86,1),(87,1),(88,1),(89,1),(90,1),(91,1),(94,1),(101,1),(103,1),(19,2),(64,2),(67,2),(69,2),(89,2),(90,2),(91,2),(97,2),(101,2),(103,2),(7,4),(12,4),(16,4),(19,4),(30,4),(31,4),(32,4),(62,4),(63,4),(64,4),(68,4),(69,4),(85,4),(86,4),(87,4),(88,4),(89,4),(90,4),(91,4),(96,4),(101,4),(103,4),(104,4),(2,6),(3,6),(4,6),(12,6),(19,6),(20,6),(28,6),(34,6),(40,6),(52,6),(53,6),(58,6),(59,6),(60,6),(68,6),(71,6),(72,6),(73,6),(74,6),(76,6),(77,6),(78,6),(79,6),(81,6),(82,6),(83,6),(84,6),(85,6),(86,6),(87,6),(88,6),(96,6),(104,6),(105,6),(106,6),(107,6),(108,6),(1,9),(2,9),(70,9),(75,9),(80,9),(84,9),(105,9),(106,9),(97,10),(98,10),(99,10),(100,10),(105,10),(106,10),(107,10);

/*Table structure for table `oa_province` */

DROP TABLE IF EXISTS `oa_province`;

CREATE TABLE `oa_province` (
  `PROVINCE_ID` int(11) NOT NULL AUTO_INCREMENT,
  `PROVINCE_NAME` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`PROVINCE_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=gb2312;

/*Data for the table `oa_province` */

/*Table structure for table `oa_resource` */

DROP TABLE IF EXISTS `oa_resource`;

CREATE TABLE `oa_resource` (
  `RESOURCE_ID` int(11) NOT NULL AUTO_INCREMENT,
  `URL` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`RESOURCE_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=gb2312;

/*Data for the table `oa_resource` */

insert  into `oa_resource`(`RESOURCE_ID`,`URL`) values (1,'/emp-input'),(2,'/emp-save'),(3,'/emp-delete'),(4,'/emp-list'),(5,'/emp_upload'),(6,'/role-list'),(7,'/role-input');

/*Table structure for table `oa_resume` */

DROP TABLE IF EXISTS `oa_resume`;

CREATE TABLE `oa_resume` (
  `RESUME_ID` int(11) NOT NULL AUTO_INCREMENT,
  `OWNER_ID` int(11) DEFAULT NULL,
  `ADDRESS_ID` int(11) DEFAULT NULL,
  `SCHOOL_ID` int(11) DEFAULT NULL,
  `MAJOR` varchar(255) DEFAULT NULL,
  `LEVEL` int(11) DEFAULT NULL,
  PRIMARY KEY (`RESUME_ID`),
  UNIQUE KEY `OWNER_ID` (`OWNER_ID`),
  KEY `FK529B5F3AF1DB1FD5` (`ADDRESS_ID`),
  KEY `FK529B5F3A5D836B3F` (`SCHOOL_ID`),
  KEY `FK529B5F3A8E84359A` (`OWNER_ID`),
  CONSTRAINT `FK529B5F3A5D836B3F` FOREIGN KEY (`SCHOOL_ID`) REFERENCES `oa_school` (`SCHOOL_ID`),
  CONSTRAINT `FK529B5F3A8E84359A` FOREIGN KEY (`OWNER_ID`) REFERENCES `oa_employee` (`EMPLOYEE_ID`),
  CONSTRAINT `FK529B5F3AF1DB1FD5` FOREIGN KEY (`ADDRESS_ID`) REFERENCES `oa_address` (`ADDRESS_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=gb2312;

/*Data for the table `oa_resume` */

/*Table structure for table `oa_role` */

DROP TABLE IF EXISTS `oa_role`;

CREATE TABLE `oa_role` (
  `ROLE_ID` int(11) NOT NULL AUTO_INCREMENT,
  `ROLE_NAME` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ROLE_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=gb2312;

/*Data for the table `oa_role` */

insert  into `oa_role`(`ROLE_ID`,`ROLE_NAME`) values (1,'管理员'),(2,'讲师'),(4,'班主任'),(6,'ABCDE'),(9,'CC'),(10,'AAAA');

/*Table structure for table `oa_role_authority` */

DROP TABLE IF EXISTS `oa_role_authority`;

CREATE TABLE `oa_role_authority` (
  `ROLE_ID` int(11) NOT NULL,
  `AUTHORITY_ID` int(11) NOT NULL,
  PRIMARY KEY (`ROLE_ID`,`AUTHORITY_ID`),
  KEY `FK860934A7109FF13F` (`ROLE_ID`),
  KEY `FK860934A73284EEB5` (`AUTHORITY_ID`),
  CONSTRAINT `FK860934A7109FF13F` FOREIGN KEY (`ROLE_ID`) REFERENCES `oa_role` (`ROLE_ID`),
  CONSTRAINT `FK860934A73284EEB5` FOREIGN KEY (`AUTHORITY_ID`) REFERENCES `oa_authority` (`AUTHORITY_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=gb2312;

/*Data for the table `oa_role_authority` */

insert  into `oa_role_authority`(`ROLE_ID`,`AUTHORITY_ID`) values (1,2),(1,3),(1,10),(1,11),(2,1),(2,3),(2,7),(2,11),(2,12),(4,1),(4,2),(4,3),(4,5),(4,10),(4,11),(6,1),(6,2),(6,3),(6,4),(6,5),(6,7),(6,9),(6,10),(6,11),(6,12),(9,2),(9,3),(10,1),(10,3),(10,10),(10,11),(10,12);

/*Table structure for table `oa_school` */

DROP TABLE IF EXISTS `oa_school`;

CREATE TABLE `oa_school` (
  `SCHOOL_ID` int(11) NOT NULL AUTO_INCREMENT,
  `SCHOOL_NAME` varchar(255) DEFAULT NULL,
  `ADDRESS_ID` int(11) DEFAULT NULL,
  PRIMARY KEY (`SCHOOL_ID`),
  KEY `FK542EF281F1DB1FD5` (`ADDRESS_ID`),
  CONSTRAINT `FK542EF281F1DB1FD5` FOREIGN KEY (`ADDRESS_ID`) REFERENCES `oa_address` (`ADDRESS_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=gb2312;

/*Data for the table `oa_school` */

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
