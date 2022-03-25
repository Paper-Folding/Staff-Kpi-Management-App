/*
 Navicat Premium Data Transfer

 Source Server         : kpimgr
 Source Server Type    : MySQL
 Source Server Version : 80027
 Source Host           : 110.40.195.78:3306
 Source Schema         : kpimgr

 Target Server Type    : MySQL
 Target Server Version : 80027
 File Encoding         : 65001

 Date: 20/03/2022 12:47:46
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for annual_evaluate
-- ----------------------------
DROP TABLE IF EXISTS `annual_evaluate`;
CREATE TABLE `annual_evaluate`  (
                                    `id` bigint NOT NULL AUTO_INCREMENT,
                                    `staff_info_id` bigint NULL DEFAULT NULL COMMENT '对应教工id',
                                    `year` date NULL DEFAULT NULL COMMENT '年份',
                                    `result` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '考评结果',
                                    PRIMARY KEY (`id`) USING BTREE,
                                    INDEX `staff_info_id`(`staff_info_id`) USING BTREE,
                                    CONSTRAINT `annual_evaluate_ibfk_1` FOREIGN KEY (`staff_info_id`) REFERENCES `staff_info` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '年度考核表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for authorization
-- ----------------------------
DROP TABLE IF EXISTS `authorization`;
CREATE TABLE `authorization`
(
    `id`            bigint                                                        NOT NULL AUTO_INCREMENT,
    `staff_info_id` bigint                                                        NULL     DEFAULT NULL COMMENT '关联staff_info中的人员id',
    `username`      varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT 'unique',
    `password`      varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL     DEFAULT '',
    PRIMARY KEY (`id`) USING BTREE,
    INDEX `staff_info_id` (`staff_info_id`) USING BTREE,
    INDEX `username` (`username`) USING BTREE,
    CONSTRAINT `authorization_ibfk_1` FOREIGN KEY (`staff_info_id`) REFERENCES `staff_info` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci COMMENT = '身份验证表'
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for class
-- ----------------------------
DROP TABLE IF EXISTS `class`;
CREATE TABLE `class`
(
    `id`                             bigint                                                        NOT NULL AUTO_INCREMENT,
    `staff_info_id`                  bigint                                                        NULL DEFAULT NULL COMMENT '教工号',
    `class`                          varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '班级名称',
    `duty_time`                      date                                                          NULL DEFAULT NULL COMMENT '任职年份',
                          `add_time` datetime NULL DEFAULT NULL COMMENT '登记时间',
                          `extra` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '备注',
                          PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '班主任任职信息表（或称班级表）' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for contest
-- ----------------------------
DROP TABLE IF EXISTS `contest`;
CREATE TABLE `contest`  (
                            `id` bigint NOT NULL AUTO_INCREMENT,
                            `no` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '编号',
                            `type` enum('学生竞赛','教师获奖') CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '竞赛类型, enum(\'学生竞赛\',\'教师获奖\')类型',
                            `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '比赛名称',
                            `students` json NULL COMMENT '参赛学生(多人)，json类型',
                            `tutor_staff_info_id` bigint NULL DEFAULT NULL COMMENT '指导教师id',
                            `prize` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '获奖奖项',
                            `level` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '获奖级别',
                            `award_time` date NULL DEFAULT NULL COMMENT '获奖时间',
                            `certificate` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '获奖证书（pdf文件，存储存放路径）',
                            `add_time` datetime NULL DEFAULT NULL COMMENT '登记时间',
                            `add_staff_info_id` bigint NULL DEFAULT NULL COMMENT '登记人id',
                            PRIMARY KEY (`id`) USING BTREE,
                            INDEX `tutor_staff_info_id`(`tutor_staff_info_id`) USING BTREE,
                            INDEX `add_staff_info_id`(`add_staff_info_id`) USING BTREE,
                            CONSTRAINT `contest_ibfk_1` FOREIGN KEY (`tutor_staff_info_id`) REFERENCES `staff_info` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
                            CONSTRAINT `contest_ibfk_2` FOREIGN KEY (`add_staff_info_id`) REFERENCES `staff_info` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '学生竞赛、教师获奖表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for daily_meeting
-- ----------------------------
DROP TABLE IF EXISTS `daily_meeting`;
CREATE TABLE `daily_meeting`  (
                                  `id` bigint NOT NULL AUTO_INCREMENT,
                                  `no` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '编号',
                                  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '会议名称',
                                  `time` datetime NULL DEFAULT NULL COMMENT '时间',
                                  `place` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '地点',
                                  `theme` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '会议主题',
                                  `hoster` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '主持人',
                                  `content` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '会议内容',
                                  `hostees` json NULL COMMENT '参会人员，json类型',
                                  `recorder` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '记录人',
                                  `record_time` datetime NULL DEFAULT NULL COMMENT '记录时间',
                                  `auditer` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '审核人',
                                  `audit_time` datetime NULL DEFAULT NULL COMMENT '审核时间',
                                  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '日常会议记录表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for day_off
-- ----------------------------
DROP TABLE IF EXISTS `day_off`;
CREATE TABLE `day_off`  (
                            `id` bigint NOT NULL AUTO_INCREMENT,
                            `start_time` datetime NULL DEFAULT NULL COMMENT '起始日期',
                            `end_time` datetime NULL DEFAULT NULL COMMENT '截止日期',
                            `vehicle` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '车辆后三位（自驾填写，非自驾填写工具）',
                            `start_place` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '出发地',
                            `end_place` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '目的地',
                            `staff_info_id` bigint NULL DEFAULT NULL COMMENT '出差人员id',
                            `add_time` datetime NULL DEFAULT NULL COMMENT '登记日期',
                            `add_by_staff_info_id` bigint NULL DEFAULT NULL COMMENT '登记人id',
                            PRIMARY KEY (`id`) USING BTREE,
                            INDEX `staff_info_id`(`staff_info_id`) USING BTREE,
                            INDEX `add_by_staff_info_id`(`add_by_staff_info_id`) USING BTREE,
                            CONSTRAINT `day_off_ibfk_1` FOREIGN KEY (`staff_info_id`) REFERENCES `staff_info` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
                            CONSTRAINT `day_off_ibfk_2` FOREIGN KEY (`add_by_staff_info_id`) REFERENCES `staff_info` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '差旅发票报销表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for dict_operation
-- ----------------------------
DROP TABLE IF EXISTS `dict_operation`;
CREATE TABLE `dict_operation`
(
    `id`          bigint                                                        NOT NULL AUTO_INCREMENT COMMENT '操作标识符',
    `name`        varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '操作名',
    `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '操作描述',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci COMMENT = '操作字典表'
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for dict_operation_object
-- ----------------------------
DROP TABLE IF EXISTS `dict_operation_object`;
CREATE TABLE `dict_operation_object`
(
    `id`          bigint                                                        NOT NULL AUTO_INCREMENT,
    `table_name`  varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '',
    `column_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '',
    PRIMARY KEY (`id`) USING BTREE,
    INDEX `get id` (`table_name`, `column_name`) USING BTREE,
    UNIQUE INDEX `id` (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci COMMENT = '权限作用域对象表，其实就是表格的字段表，用于描述某个权限的可作用对象'
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for monthly_news
-- ----------------------------
DROP TABLE IF EXISTS `monthly_news`;
CREATE TABLE `monthly_news`
(
    `id`                                bigint                                                    NOT NULL AUTO_INCREMENT,
    `staff_info_id`                     bigint                                                    NULL DEFAULT NULL COMMENT '教工号',
    `date`                              date                                                      NULL DEFAULT NULL COMMENT '日期',
    `content`                           longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '填写内容',
                                 `time` datetime NULL DEFAULT NULL COMMENT '填写时间',
                                 PRIMARY KEY (`id`) USING BTREE,
                                 INDEX `staff_info_id`(`staff_info_id`) USING BTREE,
                                 CONSTRAINT `monthly_news_ibfk_1` FOREIGN KEY (`staff_info_id`) REFERENCES `staff_info` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '月报信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for practice_hq
-- ----------------------------
DROP TABLE IF EXISTS `practice_hq`;
CREATE TABLE `practice_hq`  (
                                `id` bigint NOT NULL AUTO_INCREMENT,
                                `no` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '编号',
                                `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '公司名称',
                                `dutier` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '负责人(姓名)',
                                `sign_time` datetime NULL DEFAULT NULL COMMENT '签订时间',
                                `situation` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '目前运营情况',
                                `add_time` datetime NULL DEFAULT NULL COMMENT '登记时间',
                                `extra` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '备注',
                                PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '实践基地表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for research
-- ----------------------------
DROP TABLE IF EXISTS `research`;
CREATE TABLE `research`  (
                             `id` bigint NOT NULL AUTO_INCREMENT,
                             `no` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '项目编号',
                             `type` enum('科研','教研','other') CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '项目类型，enum(\'科研\',\'教研\',\'other\')类型',
                             `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '项目名称',
                             `level` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '项目级别',
                             `budget` decimal(12, 2) NULL DEFAULT 0.00 COMMENT '经费额度，注意为decimal类型',
                             `dutier_staff_info_id` bigint NULL DEFAULT NULL COMMENT '负责人id',
                             `department` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '负责单位',
                             `found_time` datetime NULL DEFAULT NULL COMMENT '立项时间',
                             `deadline` datetime NULL DEFAULT NULL COMMENT '截止时间',
                             `is_finish` enum('否','是') CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '是否结题，enum(\'否\',\'是\')类型',
                             `finish_time` datetime NULL DEFAULT NULL COMMENT '结题时间',
                             `add_time` datetime NULL DEFAULT NULL COMMENT '登记时间',
                             `add_by_staff_info_id` bigint NULL DEFAULT NULL COMMENT '登记人id',
                             PRIMARY KEY (`id`) USING BTREE,
                             INDEX `dutier_staff_info_id`(`dutier_staff_info_id`) USING BTREE,
                             INDEX `add_by_staff_info_id`(`add_by_staff_info_id`) USING BTREE,
                             CONSTRAINT `research_ibfk_1` FOREIGN KEY (`dutier_staff_info_id`) REFERENCES `staff_info` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
                             CONSTRAINT `research_ibfk_2` FOREIGN KEY (`add_by_staff_info_id`) REFERENCES `staff_info` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '研究表，有关科研、教研（课程建设）' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role`
(
    `id`                       bigint                                                        NOT NULL AUTO_INCREMENT,
    `name`                     varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '用户权限名',
    `description`              varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '权限描述',
    `expiration`               datetime                                                      NULL DEFAULT NULL COMMENT '角色过期时间',
    `creator_authorization_id` bigint                                                        NULL DEFAULT NULL COMMENT '角色创建者authorization id',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE INDEX `id` (`id`) USING BTREE,
    UNIQUE INDEX `name` (`name`) USING BTREE,
    INDEX `expiration` (`expiration`) USING BTREE,
    INDEX `creator_authorization_id` (`creator_authorization_id`) USING BTREE,
    CONSTRAINT `role_ibfk_1` FOREIGN KEY (`creator_authorization_id`) REFERENCES `authorization` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci COMMENT = '权限字典表'
  ROW_FORMAT = Dynamic;
-- ----------------------------
-- Table structure for role_scope
-- ----------------------------
DROP TABLE IF EXISTS `role_scope`;
CREATE TABLE `role_scope`
(
    `id`           bigint NOT NULL AUTO_INCREMENT,
    `role_id`      bigint NULL DEFAULT NULL COMMENT '角色id',
    `operation_id` bigint NULL DEFAULT NULL COMMENT '操作id',
    `object_id`    bigint NULL DEFAULT NULL COMMENT '操作对象id',
    PRIMARY KEY (`id`) USING BTREE,
    INDEX `role_id` (`role_id`) USING BTREE,
    INDEX `operation_id` (`operation_id`) USING BTREE,
    INDEX `object_id` (`object_id`) USING BTREE,
    CONSTRAINT `role_scope_ibfk_1` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
    CONSTRAINT `role_scope_ibfk_2` FOREIGN KEY (`operation_id`) REFERENCES `dict_operation` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
    CONSTRAINT `role_scope_ibfk_3` FOREIGN KEY (`object_id`) REFERENCES `dict_operation_object` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci COMMENT = '权限访问作用域表'
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for semester_evaluate
-- ----------------------------
DROP TABLE IF EXISTS `semester_evaluate`;
CREATE TABLE `semester_evaluate`
(
    `id`                                         bigint                                                        NOT NULL AUTO_INCREMENT,
    `staff_info_id`                              bigint                                                        NULL DEFAULT NULL COMMENT '对应教工id',
    `course_no`                                  varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci  NULL DEFAULT '' COMMENT '课程编号',
    `course_name`                                varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '课程名',
                                      `class_id` bigint NULL DEFAULT NULL COMMENT '班级id',
                                      `semester` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '学期',
                                      `result` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '考核结果',
                                      PRIMARY KEY (`id`) USING BTREE,
                                      INDEX `staff_info_id`(`staff_info_id`) USING BTREE,
                                      INDEX `class_id`(`class_id`) USING BTREE,
                                      CONSTRAINT `semester_evaluate_ibfk_1` FOREIGN KEY (`staff_info_id`) REFERENCES `staff_info` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
                                      CONSTRAINT `semester_evaluate_ibfk_2` FOREIGN KEY (`class_id`) REFERENCES `class` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '每学期的教学业绩考核' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for staff_info
-- ----------------------------
DROP TABLE IF EXISTS `staff_info`;
CREATE TABLE `staff_info`  (
                               `id` bigint NOT NULL AUTO_INCREMENT,
                               `no`                 varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci  NOT NULL DEFAULT '' COMMENT '工号',
                               `name`               varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci   NULL     DEFAULT '' COMMENT '姓名',
                               `gender`             enum('女','男') CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL     DEFAULT NULL COMMENT '性别，enum(\'女\',\'男\')类型',
                               `nation`             varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci   NULL     DEFAULT '' COMMENT '民族',
                               `birth`              date                                                           NULL     DEFAULT NULL COMMENT '出生年月日',
                               `enroll_time`        date                                                           NULL     DEFAULT NULL COMMENT '参加工作时间',
                               `politic`            varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci   NULL     DEFAULT '' COMMENT '政治面貌',
                               `major`              varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci  NULL     DEFAULT '' COMMENT '专业',
                               `level`              varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci  NULL     DEFAULT '' COMMENT '学历学位',
                               `level_unit`         varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci  NULL     DEFAULT '' COMMENT '学历学位授予单位',
                               `level_date`         date                                                           NULL     DEFAULT NULL COMMENT '学历学位授予日期',
                               `job_alias`          varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci  NULL     DEFAULT '' COMMENT '职称',
                               `research_direction` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci  NULL     DEFAULT '' COMMENT '主要研究方向',
                               `job`                varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci  NULL     DEFAULT '' COMMENT '职务',
                               `department`         varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci  NULL     DEFAULT NULL COMMENT '部门',
                               `idcard`             varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci  NULL     DEFAULT NULL COMMENT '身份证号',
                               `phone`              varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci   NULL     DEFAULT NULL COMMENT '办公电话',
                               `long_phone`         varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci   NULL     DEFAULT NULL COMMENT '长号',
                               `short_phone`        varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci   NULL     DEFAULT NULL COMMENT '短号',
                               PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci COMMENT = '员工基本信息表'
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for trivia
-- ----------------------------
DROP TABLE IF EXISTS `trivia`;
CREATE TABLE `trivia`
(
    `id`                                       bigint                                                                                  NOT NULL AUTO_INCREMENT,
    `staff_info_id`                            bigint                                                                                  NULL DEFAULT NULL COMMENT '教工id',
    `type`                                     enum ('进修','培训','学术会议','学习交流','other') CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '类别，enum(\'进修\',\'培训\',\'学术会议\',\'学习交流\',\'other\')类型',
    `name`                                     varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci                           NULL DEFAULT '' COMMENT '名称',
                           `count` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '记录学时数',
                           `time` datetime NULL DEFAULT NULL COMMENT '发生时间',
                           `add_staff_info_id` bigint NULL DEFAULT NULL COMMENT '登记人id',
                           `add_time` datetime NULL DEFAULT NULL COMMENT '登记时间',
                           `extra` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '备注',
                           PRIMARY KEY (`id`) USING BTREE,
                           INDEX `staff_info_id`(`staff_info_id`) USING BTREE,
                           INDEX `add_staff_info_id`(`add_staff_info_id`) USING BTREE,
                           CONSTRAINT `trivia_ibfk_1` FOREIGN KEY (`staff_info_id`) REFERENCES `staff_info` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
                           CONSTRAINT `trivia_ibfk_2` FOREIGN KEY (`add_staff_info_id`) REFERENCES `staff_info` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '教师进修、培训、学术会议等信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for tutor
-- ----------------------------
DROP TABLE IF EXISTS `tutor`;
CREATE TABLE `tutor`  (
                          `id` bigint NOT NULL AUTO_INCREMENT,
                          `staff_info_id` bigint NULL DEFAULT NULL COMMENT '导师id',
                          `stu_no` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '学生学号',
                          `stu_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '学生姓名',
                          `major` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '专业',
                          `contact` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '联系方式',
                          `extra` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '备注',
                          PRIMARY KEY (`id`) USING BTREE,
                          INDEX `staff_info_id`(`staff_info_id`) USING BTREE,
                          CONSTRAINT `tutor_ibfk_1` FOREIGN KEY (`staff_info_id`) REFERENCES `staff_info` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '导师表，考研，退学警示，记录指导记录信息' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for user_roles
-- ----------------------------
DROP TABLE IF EXISTS `user_roles`;
CREATE TABLE `user_roles`
(
    `id`               bigint NOT NULL AUTO_INCREMENT,
    `authorization_id` bigint NULL DEFAULT NULL COMMENT '用户id',
    `role_id`          bigint NULL DEFAULT NULL COMMENT '权限id',
    PRIMARY KEY (`id`) USING BTREE,
    INDEX `authorization_id` (`authorization_id`) USING BTREE,
    INDEX `role_id` (`role_id`) USING BTREE,
    CONSTRAINT `user_roles_ibfk_1` FOREIGN KEY (`authorization_id`) REFERENCES `authorization` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
    CONSTRAINT `user_roles_ibfk_2` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci COMMENT = '用户鉴权表'
  ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
