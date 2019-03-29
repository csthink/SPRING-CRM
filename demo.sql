DROP DATABASE IF EXISTS crm;
CREATE DATABASE IF NOT EXISTS crm;
USE crm;

DROP TABLE IF EXISTS `department`, `employe`, `dept_emp`, `log`, `sms`;

SET default_storage_engine = InnoDB;

CREATE TABLE `department` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '部门编号',
  `dept_name` varchar(40) CHARACTER SET utf8 NOT NULL COMMENT '部门名称',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=100 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='部门信息表';

CREATE TABLE `employee` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '员工编号',
  `username` varchar(20) CHARACTER SET utf8mb4 NOT NULL COMMENT '用户名',
  `phone` char(11) CHARACTER SET utf8mb4 NOT NULL COMMENT '手机号',
  `password` char(32) CHARACTER SET utf8mb4 NOT NULL COMMENT '密码',
  `real_name` varchar(20) CHARACTER SET utf8mb4 NOT NULL DEFAULT '' COMMENT '姓名',
  `birth_date` date NOT NULL COMMENT '生日',
  `photo` varchar(40) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT 'photo-default.jpg' COMMENT '员工头像',
  `gender` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '性别 0保密,1男,2女,',
  `hire_date` date NOT NULL COMMENT '入职日期',
  `dept_id` int(11) unsigned NOT NULL COMMENT '部门编号',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uni_username` (`username`) USING BTREE,
  UNIQUE KEY `uk_phone` (`phone`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1000 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='员工信息表';

CREATE TABLE `dept_emp` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '记录编号',
  `emp_id` int(11) unsigned NOT NULL COMMENT '员工编号',
  `dept_id` int(11) unsigned NOT NULL COMMENT '部门编号',
  `from_date` date NOT NULL COMMENT '部门起始工作时间',
  `end_date` date NOT NULL COMMENT '部门截止工作时间',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '记录创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '记录修改时间',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `idx_emp_dept` (`emp_id`,`dept_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='部门员工关系表';

CREATE TABLE `log` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `log_type` varchar(10) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '日志类型 1 系统日志，2登录日志 3 操作日志',
  `operator` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '操作人员',
  `module` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '操作模块',
  `action` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '操作行为',
  `request_ip` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '请求ip',
  `request_result` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '请求结果',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '操作时间',
  PRIMARY KEY (`id`),
  KEY `idx_log_type` (`log_type`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='日志记录表';


CREATE TABLE `sms` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '短信编号',
  `phone` char(11) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '手机号',
  `content` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '短信内容',
  `send_status` tinyint(1) unsigned NOT NULL COMMENT '状态: 0发送成功, 1提交成功，2.提交失败 3 发送失败',
  `send_result` varchar(200) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '发送结果',
  `channel` tinyint(1) unsigned NOT NULL COMMENT '发送渠道: 1 榛子云',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '发送时间',
  PRIMARY KEY (`id`),
  KEY `idx_phone` (`phone`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='短信表';

INSERT INTO `crm`.`department`(`id`, `dept_name`) VALUES (100, '技术部');
INSERT INTO `crm`.`department`(`id`, `dept_name`) VALUES (101, '人事部');
INSERT INTO `crm`.`department`(`id`, `dept_name`) VALUES (102, '公关部');
INSERT INTO `crm`.`department`(`id`, `dept_name`) VALUES (103, '销售部');
INSERT INTO `crm`.`department`(`id`, `dept_name`) VALUES (104, '后勤部');
INSERT INTO `crm`.`department`(`id`, `dept_name`) VALUES (105, '客服部');


-- csthink 111
INSERT INTO `crm`.`employee`(`id`, `username`, `phone`, `password`, `real_name`, `birth_date`, `photo`, `gender`, `hire_date`, `dept_id`, `create_time`, `update_time`) VALUES (1000, 'csthink', '15056992627', '29d73329e98e48c88a0d01177d2d2a6b', '杜明洋', '1989-08-05', 'default-photo.jpg', 1, '2019-03-31', 100, '2019-03-29 09:31:21', '2019-03-29 14:28:05');


INSERT INTO `crm`.`dept_emp`(`id`, `emp_id`, `dept_id`, `from_date`, `end_date`, `create_time`, `update_time`) VALUES (1, 1000, 100, '2015-03-21', '2016-03-21', '2015-03-20 09:08:49', '2019-03-24 09:35:49');
INSERT INTO `crm`.`dept_emp`(`id`, `emp_id`, `dept_id`, `from_date`, `end_date`, `create_time`, `update_time`) VALUES (2, 1000, 101, '2017-03-24', '2018-03-24', '2017-03-22 09:08:49', '2019-03-24 09:35:49');
INSERT INTO `crm`.`dept_emp`(`id`, `emp_id`, `dept_id`, `from_date`, `end_date`, `create_time`, `update_time`) VALUES (3, 1000, 102, '2018-11-01', '9999-01-01', '2018-10-24 09:08:49', '2019-03-24 09:35:49');