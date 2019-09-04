/*
SQLyog Professional v12.08 (64 bit)
MySQL - 5.7.26 : Database - ripple
*********************************************************************
*/


/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`ripple` /*!40100 DEFAULT CHARACTER SET utf8mb4 */;

USE `ripple`;

/*Table structure for table `sequence` */

DROP TABLE IF EXISTS `sequence`;

CREATE TABLE `sequence` (
  `id` varchar(64) NOT NULL COMMENT '序号表ID',
  `type` varchar(255) DEFAULT NULL COMMENT '类型',
  `sequence` int(10) unsigned zerofill DEFAULT '0000000000' COMMENT '序号',
  `create_by` varchar(64) DEFAULT NULL COMMENT '创建者',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT NULL COMMENT '更新者',
  `update_date` datetime DEFAULT NULL COMMENT '更新时间',
  `flag` varchar(64) DEFAULT NULL COMMENT '标记',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `sequence` */

insert  into `sequence`(`id`,`type`,`sequence`,`create_by`,`create_date`,`update_by`,`update_date`,`flag`) values ('07260013212290001','USER',0000000001,'admin','2019-07-26 00:13:21','admin','2019-07-26 00:13:21','新建'),('07260014347840003','USER',0000000002,'admin','2019-07-26 00:14:35','admin','2019-07-26 00:14:35','新建'),('07260104156070001','USER',0000000003,'admin','2019-07-26 01:04:16','admin','2019-07-26 01:04:16','新建');

/*Table structure for table `user` */

DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `id` varchar(64) NOT NULL COMMENT '主键',
  `name` varchar(255) DEFAULT NULL COMMENT '用户名',
  `age` int(64) DEFAULT NULL COMMENT '年龄',
  `create_by` varchar(255) DEFAULT NULL COMMENT '创建者',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(255) DEFAULT NULL COMMENT '更新者',
  `update_date` datetime DEFAULT NULL COMMENT '更新时间',
  `flag` varchar(255) DEFAULT NULL COMMENT '标记',
  `sort` int(64) DEFAULT NULL COMMENT '排序号',
  `password` varchar(64) DEFAULT NULL COMMENT '密码',
  `account` varchar(64) DEFAULT NULL COMMENT '账号',
  `code` varchar(64) DEFAULT NULL COMMENT '编码',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

/*Data for the table `user` */

insert  into `user`(`id`,`name`,`age`,`create_by`,`create_date`,`update_by`,`update_date`,`flag`,`sort`,`password`,`account`,`code`) values ('07251713305620000','admin',25,'test','2019-07-25 17:13:31','test','2019-07-25 17:17:05','新建',NULL,'202cb962ac59075b964b07152d234b70','admin','USER'),('0726001321820000','bb',25,'admin','2019-07-26 00:13:21','admin','2019-07-26 00:13:21','新建',NULL,'202cb962ac59075b964b07152d234b70','ak-test-3@126.top','USER0000'),('07260014347050002','cc',25,'admin','2019-07-26 00:14:35','admin','2019-07-26 00:14:35','新建',NULL,'202cb962ac59075b964b07152d234b70','ak@126.top','USER0001'),('07260104154220000','dd',25,'admin','2019-07-26 01:04:16','admin','2019-07-26 11:57:07','更新',NULL,'202cb962ac59075b964b07152d234b70','aktest@126.top','USER0002');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
