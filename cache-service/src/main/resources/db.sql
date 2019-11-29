CREATE DATABASE IF NOT EXISTS `cache` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
USE `cache`;

CREATE TABLE IF NOT EXISTS `t_user` (
  `id` varchar(32) NOT NULL COMMENT '主键',
  `name` varchar(50) NOT NULL COMMENT '登录账号',
  `password` varchar(64) NOT NULL COMMENT '登录密码',
  `salt` varchar(120) NOT NULL COMMENT '密码加密盐值',
  `nickname` varchar(50) NOT NULL DEFAULT '' COMMENT '昵称',
  `avatar` varchar(200) NOT NULL DEFAULT '' COMMENT '头像',
  `status` tinyint(4) unsigned NOT NULL DEFAULT '1' COMMENT '状态：1、启用；2、禁用',
  `create_time` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT NULL COMMENT '更新时间',
  `last_login_time` timestamp NULL DEFAULT NULL COMMENT '上次登录时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `UNIQ_NAME` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='系统用户表';

/* 密码: 123456 */;
INSERT INTO `t_user` (`id`, `name`, `password`, `salt`, `nickname`, `avatar`, `status`, `create_time`, `update_time`, `last_login_time`) VALUES
	('1161915158750740481', 'admin', '5aa1442001a19757bc035c3a9d32cd7a', 'c1b6f04c-34e7-44de-8519-2d5a3632b1b5', '管理员', '', 1, '2019-08-15 16:18:56', '2019-08-15 16:18:56', '2019-08-15 16:18:56');
