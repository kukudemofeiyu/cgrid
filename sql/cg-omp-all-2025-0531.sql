/*
 Navicat Premium Data Transfer

 Source Server         : cg-47.119.51.62
 Source Server Type    : MySQL
 Source Server Version : 50743
 Source Host           : 47.119.51.62:3309
 Source Schema         : cg-omp

 Target Server Type    : MySQL
 Target Server Version : 50743
 File Encoding         : 65001

 Date: 05/06/2025 10:39:41
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for app_banners
-- ----------------------------
DROP TABLE IF EXISTS `app_banners`;
CREATE TABLE `app_banners`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT 'Banner 标题',
  `image_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT 'Banner 图片 URL',
  `link_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT 'Banner 链接 URL',
  `is_active` tinyint(1) NULL DEFAULT 1 COMMENT '是否激活（1=激活，0=不激活）',
  `sort_order` int(11) NULL DEFAULT 0 COMMENT '排序',
  `created_at` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `updated_at` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for app_payment_type
-- ----------------------------
DROP TABLE IF EXISTS `app_payment_type`;
CREATE TABLE `app_payment_type`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `type_code` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '支付类型代码',
  `type_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '支付类型名称',
  `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '支付类型描述',
  `is_enabled` tinyint(1) NOT NULL DEFAULT 1 COMMENT '是否启用（0：禁用，1：启用）',
  `is_default` tinyint(1) NOT NULL DEFAULT 0 COMMENT '系统是否默认（0：非默认，1：默认）',
  `create_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_type_code`(`type_code`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '支付类型表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for app_program_config
-- ----------------------------
DROP TABLE IF EXISTS `app_program_config`;
CREATE TABLE `app_program_config`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '配置ID',
  `appid` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '微信小程序的AppID',
  `secret` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '微信小程序的Secret',
  `token` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '微信小程序消息服务器配置的Token',
  `aes_key` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '微信小程序消息服务器配置的EncodingAESKey',
  `msg_data_format` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT 'JSON' COMMENT '消息格式，XML或者JSON',
  `create_user` bigint(20) NULL DEFAULT NULL COMMENT '创建人',
  `create_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_user` bigint(20) NULL DEFAULT NULL COMMENT '修改人',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uniq_appid`(`appid`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '微信小程序配置表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for app_recharge_order
-- ----------------------------
DROP TABLE IF EXISTS `app_recharge_order`;
CREATE TABLE `app_recharge_order`  (
  `id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '订单ID',
  `user_id` bigint(20) NOT NULL COMMENT '用户ID',
  `status` tinyint(4) NOT NULL DEFAULT 0 COMMENT '支付状态：0-待支付，1-支付成功，2-支付失败, 3-已退款',
  `type` int(11) NULL DEFAULT 1 COMMENT '支付方式：1-微信，2-支付宝，3-抖音 4-系统',
  `amount` decimal(10, 2) NOT NULL COMMENT '充值金额',
  `third_party_order_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '第三方支付交易订单号',
  `create_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  `pay_time` datetime(0) NULL DEFAULT NULL COMMENT '支付时间',
  `pay_amount` decimal(10, 2) NULL DEFAULT NULL COMMENT '支付金额',
  `discount_amount` decimal(10, 2) NULL DEFAULT NULL COMMENT '优惠金额',
  `appid` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '公众账号ID',
  `mchid` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '商户号',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '充值订单表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for app_third_pay_order
-- ----------------------------
DROP TABLE IF EXISTS `app_third_pay_order`;
CREATE TABLE `app_third_pay_order`  (
  `id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '订单ID',
  `user_id` bigint(20) NOT NULL COMMENT '用户ID',
  `status` tinyint(4) NOT NULL DEFAULT 0 COMMENT '订单状态：0-待支付，1-支付成功，2-支付失败, 3-已退款',
  `order_source` int(11) NULL DEFAULT 1 COMMENT '订单来源：1-微信，2-支付宝，3-抖音',
  `amount` decimal(10, 2) NOT NULL COMMENT '充值金额',
  `third_party_order_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '第三方支付交易订单号',
  `create_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  `pay_time` datetime(0) NULL DEFAULT NULL COMMENT '支付时间',
  `appid` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '公众账号ID',
  `mchid` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '商户号',
  `attach` json NULL COMMENT '附加数据',
  `charge_order_sn` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '充电订单号集合',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '第三方支付订单表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for app_user
-- ----------------------------
DROP TABLE IF EXISTS `app_user`;
CREATE TABLE `app_user`  (
  `user_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `wx_open_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '微信openID',
  `wx_union_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '微信unionID',
  `mobile` varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '手机号码',
  `avatar` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '头像',
  `nick_name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '用户昵称',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '1' COMMENT '状态（1正常 0停用）',
  `payment_type_id` bigint(20) NULL DEFAULT NULL COMMENT '支付方式ID',
  `first_charge` int(1) NULL DEFAULT 1 COMMENT '首次充电:0-否 1-是',
  `create_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  PRIMARY KEY (`user_id`) USING BTREE,
  UNIQUE INDEX `app_user_pk`(`mobile`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 36 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '注册用户表（微信小程序用户）' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for app_user_blacklist
-- ----------------------------
DROP TABLE IF EXISTS `app_user_blacklist`;
CREATE TABLE `app_user_blacklist`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `user_id` bigint(20) NULL DEFAULT NULL COMMENT '用户ID',
  `site_range` int(11) NULL DEFAULT 1 COMMENT '站点维度（1全部站点 2部分站点）',
  `site_ids` json NULL COMMENT '站点ID集合',
  `unseal_time` datetime(0) NULL DEFAULT NULL COMMENT '解禁时间',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '原因',
  `create_by` bigint(20) NULL DEFAULT NULL COMMENT '创建人',
  `create_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_by` bigint(20) NULL DEFAULT NULL COMMENT '修改人',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '黑名单表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for app_user_car
-- ----------------------------
DROP TABLE IF EXISTS `app_user_car`;
CREATE TABLE `app_user_car`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `user_id` bigint(20) NOT NULL COMMENT '用户ID',
  `license_plate_number` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '车牌号码',
  `license_plate_color` int(11) NULL DEFAULT 2 COMMENT '车牌颜色（1蓝牌 2绿牌 3黄牌）',
  `bind_time` datetime(0) NULL DEFAULT NULL COMMENT '绑定时间',
  `bind_status` int(11) NULL DEFAULT 1 COMMENT '绑定状态（1已绑定 0已解绑）',
  `is_default` int(11) NULL DEFAULT 0 COMMENT '是否为默认（0非默认 1默认）',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 18 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '车辆信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for app_user_group
-- ----------------------------
DROP TABLE IF EXISTS `app_user_group`;
CREATE TABLE `app_user_group`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `operator_id` bigint(20) NULL DEFAULT NULL COMMENT '运营商ID',
  `name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '用户组名称',
  `create_by` bigint(20) NULL DEFAULT NULL,
  `create_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0),
  `update_by` bigint(20) NULL DEFAULT NULL,
  `update_time` datetime(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0),
  `del_flag` int(1) NOT NULL DEFAULT 0 COMMENT '0-正常 1-删除',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `del_flag`(`del_flag`, `operator_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for app_user_group_user
-- ----------------------------
DROP TABLE IF EXISTS `app_user_group_user`;
CREATE TABLE `app_user_group_user`  (
  `group_id` bigint(20) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  PRIMARY KEY (`group_id`, `user_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = 'app用户组映射表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for device_alarm
-- ----------------------------
DROP TABLE IF EXISTS `device_alarm`;
CREATE TABLE `device_alarm`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `device_id` bigint(20) NOT NULL COMMENT '充电桩id',
  `port_id` bigint(20) NULL DEFAULT NULL COMMENT '枪口',
  `type` tinyint(1) NOT NULL COMMENT '告警类型, 0-车故障 1-车桩交互故障 2-桩/平台故障 3-桩故障 4-自定义故障',
  `code` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '告警编码',
  `reason` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '告警原因',
  `detail_info` json NULL COMMENT '设备的告警内容的详情',
  `alarm_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '告警时间',
  `recovery_time` timestamp(0) NULL DEFAULT NULL COMMENT '恢复时间',
  `recover_type` int(1) NULL DEFAULT NULL COMMENT '恢复类型  0设备自动恢复 1人为操作恢复',
  `deal_status` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否已处理 0未处理 1已处理\n',
  `deal_by` bigint(20) NULL DEFAULT NULL COMMENT '处理人',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `union_index`(`device_id`, `port_id`, `type`, `code`, `deal_status`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '告警管理-告警信息记录' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for device_buy_record
-- ----------------------------
DROP TABLE IF EXISTS `device_buy_record`;
CREATE TABLE `device_buy_record`  (
  `id` int(11) NOT NULL,
  `device_number` int(11) NULL DEFAULT NULL COMMENT '采购设备数量',
  `buy_date` datetime(0) NULL DEFAULT NULL,
  `buy_user` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `price` decimal(10, 2) NULL DEFAULT NULL,
  `product_id` int(11) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '设备采购表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for device_charge_record_exception
-- ----------------------------
DROP TABLE IF EXISTS `device_charge_record_exception`;
CREATE TABLE `device_charge_record_exception`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `order_no` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `vin` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `pile_id` int(11) NULL DEFAULT NULL,
  `port_id` int(11) NULL DEFAULT NULL,
  `card_no` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `start_time` datetime(0) NULL DEFAULT NULL,
  `end_time` datetime(0) NULL DEFAULT NULL,
  `electricity` decimal(10, 0) NULL DEFAULT NULL,
  `amount` decimal(10, 0) NULL DEFAULT NULL,
  `flag` tinyint(255) NULL DEFAULT NULL COMMENT '1app充电  2卡启动',
  `order_time` datetime(0) NULL DEFAULT NULL,
  `end_reason_code` tinyint(255) NULL DEFAULT NULL COMMENT '结束原因编码',
  `end_reason_desc` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '结束原因描述',
  `fee_rate_json` json NULL COMMENT '费率信息',
  `event_ts` datetime(0) NULL DEFAULT NULL COMMENT '发生时间',
  `commit_time` datetime(0) NULL DEFAULT NULL COMMENT '记录提交时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `order_no_index`(`order_no`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 62 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for device_connect_status
-- ----------------------------
DROP TABLE IF EXISTS `device_connect_status`;
CREATE TABLE `device_connect_status`  (
  `device_id` bigint(20) NOT NULL,
  `status` int(11) NULL DEFAULT NULL COMMENT '0：离线 1:在线',
  `node_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '最后活跃时间',
  `valid_time` datetime(0) NULL DEFAULT NULL COMMENT '过期时间',
  `session_start_time` datetime(0) NULL DEFAULT NULL COMMENT '会话开始时间',
  `broker_id` int(11) NULL DEFAULT NULL,
  PRIMARY KEY (`device_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '设备连接状态' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for device_ic_card
-- ----------------------------
DROP TABLE IF EXISTS `device_ic_card`;
CREATE TABLE `device_ic_card`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `ic_card_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '卡号',
  `iccid` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '物理卡号',
  `balance` decimal(10, 2) NULL COMMENT '余额',
  `agent_id` bigint(20) NULL DEFAULT NULL COMMENT '所属代理商',
  `create_user` bigint(20) NULL DEFAULT NULL COMMENT '创建人',
  `create_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_user` bigint(20) NULL DEFAULT NULL COMMENT '修改人',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  `status` tinyint(4) NULL DEFAULT NULL COMMENT '0-正常，1-挂失',
  `user_id` bigint(20) NULL DEFAULT NULL COMMENT '用户表id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = 'IC卡表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for device_ic_comsume_log
-- ----------------------------
DROP TABLE IF EXISTS `device_ic_comsume_log`;
CREATE TABLE `device_ic_comsume_log`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `order_no` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '订单id',
  `create_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `order_type` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '0-按度数消费',
  `order_status` tinyint(4) NULL DEFAULT NULL COMMENT '0-未支付 1-已支付',
  `expect_end_time` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '预期结束时间',
  `ic_card_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '卡号',
  `ic_card_status` tinyint(4) NULL DEFAULT NULL COMMENT '卡状态',
  `order_money` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '订单金额',
  `phone` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '手机号',
  `user_name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '0.00' COMMENT '用户名',
  `update_user` bigint(20) NULL DEFAULT NULL COMMENT '修改人',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  `site_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '电站名称',
  `device_sn` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '桩编码',
  `device_port` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '桩端口',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = 'IC卡记录表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for device_ic_recharge_log
-- ----------------------------
DROP TABLE IF EXISTS `device_ic_recharge_log`;
CREATE TABLE `device_ic_recharge_log`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `ic_card_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '卡号',
  `status` tinyint(1) NULL DEFAULT NULL COMMENT '0-挂失，1-正常',
  `money` decimal(10, 2) NULL DEFAULT NULL COMMENT '充值金额',
  `user_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '用户姓名',
  `phone` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '用户电话',
  `update_user` bigint(20) NULL DEFAULT NULL COMMENT '修改人',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  `agent_id` bigint(20) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = 'IC卡充值记录表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for device_info
-- ----------------------------
DROP TABLE IF EXISTS `device_info`;
CREATE TABLE `device_info`  (
  `device_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '设备编号',
  `sn` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '设备唯一序列',
  `name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '设备名称',
  `charge_type` tinyint(4) NULL DEFAULT NULL COMMENT '充电类型，0-快充，1-慢充',
  `electric` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '电流信息（A）',
  `voltage` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '电压信息（V）',
  `max_power` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '最大功率（KW）',
  `status` tinyint(4) NULL DEFAULT NULL COMMENT '1可用 0不可用 2已删除',
  `site_id` int(11) NULL DEFAULT NULL COMMENT '站点id',
  `is_free` tinyint(4) NULL DEFAULT NULL COMMENT '是否收费，0-否，1-是',
  `product_id` int(11) NULL DEFAULT NULL COMMENT '产品型号id',
  `operator_id` bigint(20) NULL DEFAULT NULL COMMENT '运营商ID',
  `remark` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '设备备注信息',
  `alias_sn` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '在同一父设备中具有唯一性,可用于端口ID，用来和硬件交互',
  `parent_id` bigint(20) NULL DEFAULT 0 COMMENT '上级设备ID',
  `connect_device_id` bigint(20) NULL DEFAULT NULL COMMENT '通信设备ID',
  `create_by` bigint(20) NULL DEFAULT NULL COMMENT '创建人',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` bigint(20) NULL DEFAULT NULL COMMENT '修改人',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `ancestor_ids` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0' COMMENT '祖级设备id列表',
  `port_num` int(11) NULL DEFAULT NULL COMMENT '端口数',
  `sim` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `sim_expire` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'SIM卡过期时间',
  `component` int(255) NULL DEFAULT 0 COMMENT '0-桩（point） 1-枪（port）',
  `pay_rule_id` bigint(20) NULL DEFAULT NULL COMMENT '最新计费规则id（平台）',
  `pay_model_id` int(11) NULL DEFAULT NULL COMMENT '最新计费模型编号（1-9999,平台）',
  `current_pay_rule_id` bigint(20) NULL DEFAULT NULL COMMENT '当前计费规则id（设备）',
  `current_pay_model_id` int(11) NULL DEFAULT NULL COMMENT '当前计费模型编号（1-9999,设备）',
  `active_time` datetime(0) NULL DEFAULT NULL COMMENT '激活时间（第一次上线时间）',
  PRIMARY KEY (`device_id`) USING BTREE,
  INDEX `status`(`status`, `site_id`, `component`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 16 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '充电桩设备表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for device_node_broker
-- ----------------------------
DROP TABLE IF EXISTS `device_node_broker`;
CREATE TABLE `device_node_broker`  (
  `broker_id` int(11) NOT NULL,
  `broker_name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `protocol` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `status` int(11) NULL DEFAULT NULL COMMENT '1启动 2初始化但不启动 3不启动',
  `port` int(11) NULL DEFAULT NULL,
  `service_bean` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `task_check` tinyint(1) NULL DEFAULT NULL,
  `keepalive_time` int(11) NULL DEFAULT NULL,
  `abnormal_off_line_time` int(11) NULL DEFAULT NULL COMMENT '异常离线时间（分钟），当最后活跃时间+异常离线时间>当前时间，判定离线',
  `sync_session_interval_time` int(11) NULL DEFAULT NULL,
  `config_info` json NULL,
  PRIMARY KEY (`broker_id`) USING BTREE,
  UNIQUE INDEX `iot_broker_config_service_bean_uindex`(`service_bean`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'HUB-Broker配置' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for device_node_broker_run_info
-- ----------------------------
DROP TABLE IF EXISTS `device_node_broker_run_info`;
CREATE TABLE `device_node_broker_run_info`  (
  `id` bigint(11) NOT NULL AUTO_INCREMENT,
  `service_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `broker_id` int(11) NOT NULL,
  `broker_name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `state` int(11) NOT NULL COMMENT '1初始化 2初始化完成 3运行中 4停止',
  `service_port` int(11) NULL DEFAULT NULL,
  `update_time` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `iot_broker_run_info_pk2`(`service_id`, `broker_id`) USING BTREE,
  INDEX `borker_id`(`broker_id`, `update_time`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1892188437575487526 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for device_node_info
-- ----------------------------
DROP TABLE IF EXISTS `device_node_info`;
CREATE TABLE `device_node_info`  (
  `service_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `service_name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '服务名称',
  `visit_port` int(11) NOT NULL,
  `visit_ip` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `topic` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `context_path` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `update_time` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`service_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'hub节点信息' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for device_port_status
-- ----------------------------
DROP TABLE IF EXISTS `device_port_status`;
CREATE TABLE `device_port_status`  (
  `port_id` bigint(20) NOT NULL COMMENT '充电枪ID',
  `status` int(11) NULL DEFAULT NULL COMMENT '充电枪状态(0离线 1故障 2空闲 3充电)',
  `vin` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '当前充电VIN号',
  `order_sn` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '当前充电订单号',
  `status_time` bigint(20) NULL DEFAULT NULL COMMENT '状态变化时间',
  `port_inserted` bit(1) NULL DEFAULT b'0' COMMENT '是否插枪',
  `port_inserted_time` bigint(20) NULL DEFAULT NULL COMMENT '第一次插枪时间',
  `home_status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '1' COMMENT '归位状态(0否 1是 2未知)',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '记录更新时间',
  PRIMARY KEY (`port_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '充电枪状态表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for device_product
-- ----------------------------
DROP TABLE IF EXISTS `device_product`;
CREATE TABLE `device_product`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '产品id',
  `factory_id` bigint(20) NULL DEFAULT NULL COMMENT '厂商id，device_product_model表id',
  `model` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '产品型号',
  `protocol` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `operator_id` bigint(20) NULL DEFAULT NULL COMMENT '运营商表',
  `node_type` int(11) NULL DEFAULT NULL COMMENT '1直连 2网关子设备 3网关设备',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `create_by` int(20) NULL DEFAULT NULL COMMENT '创建人',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '充电桩厂商表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for device_product_model
-- ----------------------------
DROP TABLE IF EXISTS `device_product_model`;
CREATE TABLE `device_product_model`  (
  `id` bigint(20) NOT NULL COMMENT '产品id',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '厂商名称',
  `tell_person` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '联系人',
  `tell_phone` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '联系电话',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `create_by` int(20) NULL DEFAULT NULL COMMENT '创建人',
  `operator_id` bigint(12) NULL DEFAULT NULL COMMENT '运营商表',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '充电桩厂商表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for device_site
-- ----------------------------
DROP TABLE IF EXISTS `device_site`;
CREATE TABLE `device_site`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '站点id',
  `sn` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '站点唯一序列号',
  `name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '站点名称',
  `country_code` bigint(20) NULL DEFAULT NULL COMMENT '国家编码',
  `province_code` bigint(20) NULL DEFAULT NULL COMMENT '省份编码',
  `city_code` bigint(20) NULL DEFAULT NULL COMMENT '城市编码',
  `district_code` bigint(20) NULL DEFAULT NULL COMMENT '区编码',
  `address` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '站点地址',
  `longitude` decimal(12, 8) NULL DEFAULT NULL COMMENT '经度',
  `latitude` decimal(12, 8) NULL DEFAULT NULL COMMENT '纬度',
  `parking_info` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '停车收费信息',
  `fee_type` int(11) NULL DEFAULT NULL COMMENT '停车收费类型（1停车收费 0停车免费）',
  `parking_free_time` decimal(10, 2) NULL DEFAULT NULL COMMENT '停车免费时长（小时）',
  `is_invoice` int(11) NULL DEFAULT NULL COMMENT '是否可以发票（1可以 0不可以）',
  `is_reservation` int(11) NULL DEFAULT NULL COMMENT '是否可以预约(1支持 0不支持)',
  `support` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '配套设施(0免费WiFi 1便利店 2洗车 3厕所) 可多选 逗号分开',
  `director_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '负责人姓名',
  `director_phone` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '负责人手机',
  `remarks` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '备注',
  `operate_status` int(11) NULL DEFAULT 1 COMMENT '运营状态：0-停业 1-在营',
  `app_display` int(11) NULL DEFAULT NULL COMMENT '小程序是否显示（0隐藏 1显示）',
  `type` int(11) NULL DEFAULT NULL COMMENT '充电站类型（0 公共充电站 1商业充电站 2居住充电站 3高速公路充电站 4智能充电站）',
  `photos` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL COMMENT '站点图片 多个用,隔开',
  `operate_start_time` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '站点运营开始时间',
  `operate_end_time` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '站点运营结束时间',
  `create_by` bigint(20) NULL DEFAULT NULL COMMENT '创建人',
  `create_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_by` bigint(20) NULL DEFAULT NULL COMMENT '修改人',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  `operator_id` bigint(20) NULL DEFAULT NULL COMMENT '运营商ID',
  `pile_lock_time` int(11) NULL DEFAULT NULL COMMENT '锁桩时间（分钟）',
  `lock_deposit` decimal(10, 2) NULL DEFAULT NULL COMMENT '锁单保证金（元）',
  `del_flag` int(1) NULL DEFAULT 0 COMMENT '0-正常 1-删除',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `agent_id`(`del_flag`) USING BTREE,
  INDEX `org_id`(`del_flag`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '站点表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for device_site_area_code
-- ----------------------------
DROP TABLE IF EXISTS `device_site_area_code`;
CREATE TABLE `device_site_area_code`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `area_code` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '地区编码',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '名称',
  `level` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '级别',
  `longitude` decimal(18, 6) NULL DEFAULT NULL COMMENT '经度',
  `latitude` decimal(18, 6) NULL DEFAULT NULL COMMENT '纬度',
  `parent_id` bigint(20) NOT NULL COMMENT '父节点，当前表id',
  `project_id` bigint(20) NULL DEFAULT 0 COMMENT '项目id',
  `create_by` bigint(20) NULL DEFAULT NULL COMMENT '创建人',
  `create_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `last_modify_by` bigint(20) NULL DEFAULT NULL COMMENT '最后修改人',
  `last_modify_time` timestamp(0) NULL DEFAULT NULL COMMENT '最后修改时间',
  `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '备注',
  `show_flag` int(1) NULL DEFAULT 1 COMMENT '0-隐藏 1-显示',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `area_code`(`area_code`, `level`, `parent_id`) USING BTREE,
  INDEX `show_flag`(`show_flag`, `level`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 659010004 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '地区编码表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for device_site_statistics_day
-- ----------------------------
DROP TABLE IF EXISTS `device_site_statistics_day`;
CREATE TABLE `device_site_statistics_day`  (
  `site_id` bigint(20) NOT NULL COMMENT '站点ID',
  `statistics_date` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '统计日期',
  `charge_count` int(11) NULL DEFAULT NULL COMMENT '充电次数',
  `charge_time` int(11) NULL DEFAULT NULL COMMENT '充电时长(小时)',
  `charge_electricity` decimal(18, 2) NULL DEFAULT NULL COMMENT '充电量',
  `order_amount` decimal(12, 4) NULL DEFAULT NULL COMMENT '订单金额',
  `charge_amount` decimal(12, 4) NULL DEFAULT NULL COMMENT '充电金额',
  `service_amount` decimal(12, 4) NULL DEFAULT NULL COMMENT '服务金额',
  `pay_amount` decimal(12, 4) NULL DEFAULT NULL COMMENT '实际支付金额',
  `refund_amount` decimal(12, 4) NULL DEFAULT NULL COMMENT '退款金额',
  `create_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`site_id`, `statistics_date`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '站点数据统计(以天维度)' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for device_site_statistics_hour
-- ----------------------------
DROP TABLE IF EXISTS `device_site_statistics_hour`;
CREATE TABLE `device_site_statistics_hour`  (
  `site_id` bigint(20) NOT NULL COMMENT '站点ID',
  `statistics_date` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '统计日期',
  `statistics_time` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '统计时间',
  `charge_count` int(11) NULL DEFAULT NULL COMMENT '充电次数',
  `charge_time` int(11) NULL DEFAULT NULL COMMENT '充电时长(小时)',
  `charge_electricity` decimal(18, 2) NULL DEFAULT NULL COMMENT '充电量',
  `order_amount` decimal(12, 4) NULL DEFAULT NULL COMMENT '订单金额',
  `charge_amount` decimal(12, 4) NULL DEFAULT NULL COMMENT '充电金额',
  `service_amount` decimal(12, 4) NULL DEFAULT NULL COMMENT '服务金额',
  `pay_amount` decimal(12, 4) NULL DEFAULT NULL COMMENT '实际支付金额',
  `refund_amount` decimal(12, 4) NULL DEFAULT NULL COMMENT '退款金额',
  `create_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`site_id`, `statistics_date`, `statistics_time`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '站点数据统计(以小时维度)' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for mq_producer_fail_record
-- ----------------------------
DROP TABLE IF EXISTS `mq_producer_fail_record`;
CREATE TABLE `mq_producer_fail_record`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `topic` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '消息主题',
  `tag` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '消息标签',
  `message_format` json NULL COMMENT '格式后消息体，用于查看内容',
  `message` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL COMMENT 'protostuff编码后的消息体',
  `retry_count` int(11) NULL DEFAULT 0 COMMENT '重发次数',
  `modules` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '所属模块',
  `create_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = 'MQ队列消息发送失败记录表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for order_appointment
-- ----------------------------
DROP TABLE IF EXISTS `order_appointment`;
CREATE TABLE `order_appointment`  (
  `order_id` int(11) NOT NULL,
  `start_time` datetime(0) NULL DEFAULT NULL COMMENT '开始时间',
  `end_time` datetime(0) NULL DEFAULT NULL COMMENT '结束时间',
  `amount` decimal(18, 6) NULL COMMENT '预约金额',
  PRIMARY KEY (`order_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '预约订单表' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for order_charge_trade_detail
-- ----------------------------
DROP TABLE IF EXISTS `order_charge_trade_detail`;
CREATE TABLE `order_charge_trade_detail`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `order_id` bigint(20) NOT NULL COMMENT '订单id',
  `start_time` datetime(0) NULL DEFAULT NULL COMMENT '开始时间',
  `end_time` datetime(0) NULL DEFAULT NULL COMMENT '结束时间',
  `device_unit_price` decimal(18, 5) NOT NULL COMMENT '设备总单价',
  `pt_unit_elec` decimal(18, 4) NULL DEFAULT NULL COMMENT '平台电费单价',
  `pt_unit_service` decimal(18, 4) NULL DEFAULT NULL COMMENT '平台服务费单价',
  `pt_unit_price` decimal(18, 4) NULL DEFAULT NULL COMMENT '平台总单价',
  `energy` decimal(18, 4) NOT NULL COMMENT '电量',
  `lose_energy` decimal(18, 4) NOT NULL COMMENT '计损电量',
  `device_amount` decimal(18, 4) NOT NULL COMMENT '金额',
  `create_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `order_id`(`order_id`, `start_time`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3813 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '订单充电明细表' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for order_commission_child_record
-- ----------------------------
DROP TABLE IF EXISTS `order_commission_child_record`;
CREATE TABLE `order_commission_child_record`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `parent_id` bigint(20) NULL DEFAULT NULL COMMENT '父级ID（分账明细ID）',
  `shareholders_id` bigint(20) NULL DEFAULT NULL COMMENT '分成者ID',
  `commission_percent` decimal(10, 2) NULL COMMENT '分成比例',
  `amount` decimal(10, 2) NULL COMMENT '分成金额',
  `create_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '分账子明细表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for order_commission_record
-- ----------------------------
DROP TABLE IF EXISTS `order_commission_record`;
CREATE TABLE `order_commission_record`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `order_id` bigint(20) NOT NULL COMMENT '订单ID',
  `operator_id` bigint(20) NOT NULL COMMENT '运营商ID',
  `rule_id` bigint(20) NOT NULL COMMENT '分成规则ID',
  `commission_type` int(11) NULL DEFAULT NULL COMMENT '分成类型（1电费 2服务费  3电费+服务费）',
  `platform_percent` decimal(10, 2) NULL DEFAULT NULL COMMENT '平台分成比例',
  `operator_percent` decimal(10, 2) NULL DEFAULT NULL COMMENT '运营商分成比例',
  `platform_amount` decimal(12, 4) NULL DEFAULT NULL COMMENT '平台分成金额',
  `operator_amount` decimal(12, 4) NULL DEFAULT NULL COMMENT '运营商分成金额',
  `operator_real_amount` decimal(12, 4) NULL DEFAULT NULL COMMENT '运营商实际分成金额（运营商分成金额-子分成者分成金额）',
  `event_time` datetime(0) NULL DEFAULT NULL COMMENT '订单发生时间',
  `create_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 50 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '分账明细表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for order_commission_rule
-- ----------------------------
DROP TABLE IF EXISTS `order_commission_rule`;
CREATE TABLE `order_commission_rule`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `user_id` bigint(20) NULL DEFAULT NULL COMMENT '用户ID',
  `operator_id` bigint(20) NULL DEFAULT NULL COMMENT '运营商ID',
  `site_id` bigint(20) NULL DEFAULT 0 COMMENT '站点ID',
  `ratio` decimal(10, 2) NOT NULL COMMENT '分成比例',
  `level` int(11) NULL DEFAULT NULL COMMENT '分成等级（1运营商级 2运营商-站点级 3-分成者级 4分成者-站点级）',
  `type` int(11) NULL DEFAULT NULL COMMENT '分成类型（1电费 2服务费 3电费+服务费）',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '1' COMMENT '状态（1生效 0停用）',
  `remark` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '备注',
  `del_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '0' COMMENT '删除标识(1删除 0未删除)',
  `create_by` bigint(20) NULL DEFAULT NULL COMMENT '创建人',
  `create_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_by` bigint(20) NULL DEFAULT NULL COMMENT '修改人',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `order_commission_rule_pk`(`operator_id`, `user_id`, `site_id`, `level`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '分成规则表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for order_discount
-- ----------------------------
DROP TABLE IF EXISTS `order_discount`;
CREATE TABLE `order_discount`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `order_id` bigint(20) NOT NULL COMMENT '订单id',
  `activity_id` bigint(20) NULL DEFAULT NULL COMMENT '活动id',
  `template_id` bigint(20) NULL DEFAULT NULL COMMENT '模板id',
  `coupon_id` bigint(20) NULL DEFAULT NULL COMMENT '优惠券id',
  `discount_class` int(1) NOT NULL COMMENT '0-抵扣 1-折扣 2-一口价',
  `config` json NULL COMMENT '优惠券配置/站点活动配置',
  `discount_type` tinyint(2) NULL DEFAULT NULL COMMENT '优惠券类型：0-现金折扣 1-折扣比例',
  `deduction_type` tinyint(1) NULL DEFAULT NULL COMMENT '0-服务费 1-总费用',
  `discount_amount` decimal(18, 2) NOT NULL COMMENT '优惠金额',
  `create_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `order_id`(`order_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 34 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '订单折扣明细表' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for order_info
-- ----------------------------
DROP TABLE IF EXISTS `order_info`;
CREATE TABLE `order_info`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `sn` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '订单号',
  `trade_sn` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '交易流水号',
  `order_type` int(1) NULL DEFAULT 0 COMMENT '订单类型：0-充电订单 1-预约订单 2-占位订单',
  `order_state` int(1) NULL DEFAULT 1 COMMENT '订单状态：1.充电中 2.结束充电 3.交易记录确认 4.已拔枪',
  `abnormal_status` int(1) NULL DEFAULT 0 COMMENT '异常状态：0-正常 1-异常',
  `order_source` int(11) NULL DEFAULT 1 COMMENT '订单来源：1-微信小程序，2-H5，3-卡',
  `bill_type` int(2) NULL DEFAULT 0 COMMENT '计费类型，0-按小时计费（取决于用户选择,默认） 1-按度数计费（取决于 用户选择）',
  `parent_id` bigint(20) NULL DEFAULT 0 COMMENT '父id',
  `pile_id` bigint(20) NULL DEFAULT NULL COMMENT '充电桩ID',
  `port_id` bigint(20) NULL DEFAULT NULL COMMENT '充电口ID',
  `appoint_start_time` datetime(0) NULL DEFAULT NULL COMMENT '开始时间',
  `appoint_end_time` datetime(0) NULL DEFAULT NULL COMMENT '结束时间',
  `appoint_charging_time` decimal(18, 2) NULL DEFAULT NULL COMMENT '充电时长，用户选择充电时间(小时)',
  `real_start_time` datetime(0) NULL DEFAULT NULL COMMENT '时间充电开始时间',
  `real_end_time` datetime(0) NULL DEFAULT NULL COMMENT '实际充电结束时间',
  `real_charging_time` decimal(18, 2) NULL DEFAULT NULL COMMENT '实际充电时长（小时）',
  `end_reason_code` int(3) NULL DEFAULT NULL COMMENT '结束原因编码',
  `end_reason_desc` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '结束原因描述',
  `is_fee` int(2) NOT NULL DEFAULT 0 COMMENT '0-不收费 1-收费（默认） 取决于充电桩',
  `device_type` int(2) NOT NULL DEFAULT 2 COMMENT '设备类型 2:二轮车 4:四轮车',
  `consume_electricity` decimal(18, 2) NULL DEFAULT NULL COMMENT '耗电量 单位 kWh',
  `pay_rule_id` bigint(20) NULL DEFAULT NULL COMMENT '计费规则id',
  `pay_model_id` int(11) NULL DEFAULT NULL COMMENT '计费模型编号',
  `user_id` bigint(20) NULL DEFAULT NULL COMMENT '对应于app_user主键',
  `phone` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '手机号',
  `license_plate_number` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '车牌号码',
  `order_amount` decimal(20, 2) NULL DEFAULT NULL COMMENT '订单金额',
  `charge_fee` decimal(12, 4) NULL DEFAULT NULL COMMENT '充电费用',
  `service_fee` decimal(12, 4) NULL DEFAULT NULL COMMENT '服务费',
  `charge_discount_fee` decimal(12, 4) NULL DEFAULT NULL COMMENT '电费优惠金额',
  `service_discount_fee` decimal(12, 4) NULL DEFAULT NULL COMMENT '服务费优惠金额',
  `coupon_amount` decimal(12, 4) NULL DEFAULT NULL COMMENT '优惠券优惠金额',
  `site_discount_amount` decimal(12, 4) NULL DEFAULT NULL COMMENT '站点优惠金额',
  `pay_amount` decimal(20, 2) NULL DEFAULT NULL COMMENT '支付金额',
  `refund_status` int(1) NULL DEFAULT 0 COMMENT '退款状态：0-未退款 1-退款',
  `refund_amount` decimal(18, 2) NULL DEFAULT NULL COMMENT '退款金额',
  `refund_reason` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '退款原因',
  `refund_time` datetime(0) NULL DEFAULT NULL COMMENT '退款时间',
  `card_no` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '卡号(卡支付时插入)',
  `pay_type` int(1) NULL DEFAULT 2 COMMENT '0-在线支付 1-卡支付 2-钱包支付',
  `pay_status` int(1) NULL DEFAULT 0 COMMENT '0-未支付 1-已支付',
  `pay_time` datetime(0) NULL DEFAULT NULL COMMENT '支付时间',
  `pay_order_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '支付订单id',
  `create_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '订单创建时间',
  `site_id` bigint(20) NULL DEFAULT NULL COMMENT '站点id',
  `group_order` int(1) NULL DEFAULT 0 COMMENT '是否为团体订单：0-否 1-是',
  `group_card_no` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '团体卡号',
  `version` bigint(20) NULL DEFAULT 0 COMMENT '版本号',
  `vin` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'VIN码',
  `insert_time` datetime(0) NULL DEFAULT NULL COMMENT '插枪时间',
  `settlement_time` datetime(0) NULL DEFAULT NULL COMMENT '交易记录确认时间',
  `draw_gun_time` datetime(0) NULL DEFAULT NULL COMMENT '拔枪时间',
  `debt_status` int(1) NULL DEFAULT 0 COMMENT '是否欠款：0-否 1-是',
  `debt_amount` decimal(18, 2) NULL DEFAULT NULL COMMENT '欠款金额',
  `process_loss` int(1) NULL DEFAULT 0 COMMENT '流程缺失：0-否 1-是',
  `process_step` int(1) NULL DEFAULT 3 COMMENT '0-处理完毕 1-未返积分 2-未数据统计 3-未分佣',
  `occupy_type` int(1) NULL DEFAULT 1 COMMENT '0-充电前占位 1-充电后占位',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `idx_ordernumber`(`sn`) USING BTREE,
  INDEX `idx_parklockid`(`port_id`) USING BTREE,
  INDEX `idx_parkid`(`pile_id`) USING BTREE,
  INDEX `idx_createtime`(`create_time`) USING BTREE,
  INDEX `idx_cardno`(`card_no`) USING BTREE,
  INDEX `idx_createTime_orderstate`(`create_time`) USING BTREE,
  INDEX `site_id`(`site_id`) USING BTREE,
  INDEX `vin`(`vin`) USING BTREE,
  INDEX `parent_id`(`parent_id`) USING BTREE,
  INDEX `trade_sn`(`trade_sn`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 461 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '充电订单表' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for order_invoice
-- ----------------------------
DROP TABLE IF EXISTS `order_invoice`;
CREATE TABLE `order_invoice`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `sn` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '发票流水号',
  `user_id` int(11) NULL DEFAULT NULL COMMENT '用户id',
  `amount` decimal(18, 6) NULL COMMENT '金额',
  `title` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '发票抬头',
  `state` int(2) NULL DEFAULT 0 COMMENT '订单状态：0.待审核 1.开票中 2.已驳回 3.开票失败 4.开票成功签章中 5.已开票 6.开票成功签章失败 7.作废中 8.已作废 9.开票完成（最终状态）',
  `make_out_time` datetime(0) NULL DEFAULT NULL COMMENT '开票时间',
  `remark` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  `create_by` int(11) NULL DEFAULT NULL COMMENT '创建人',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '发票表' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for order_invoice_order_ef
-- ----------------------------
DROP TABLE IF EXISTS `order_invoice_order_ef`;
CREATE TABLE `order_invoice_order_ef`  (
  `invoice_id` int(11) NOT NULL COMMENT '发票id',
  `order_id` int(11) NOT NULL COMMENT '订单id',
  PRIMARY KEY (`invoice_id`, `order_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '发票订单表' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for order_log
-- ----------------------------
DROP TABLE IF EXISTS `order_log`;
CREATE TABLE `order_log`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `order_id` bigint(20) NOT NULL COMMENT '订单id',
  `title` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '日志主题',
  `content` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '日志内容',
  `create_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `order_id`(`order_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 653 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '订单日志表' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for order_shareholders
-- ----------------------------
DROP TABLE IF EXISTS `order_shareholders`;
CREATE TABLE `order_shareholders`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '分成者ID',
  `user_id` bigint(20) NULL DEFAULT NULL COMMENT '用户ID',
  `operator_id` bigint(20) NULL DEFAULT NULL COMMENT '运营商ID',
  `address` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '分成者地址',
  `entry_date` date NULL DEFAULT NULL COMMENT '入驻日期',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '1' COMMENT '分成者状态（1启用 0停用）',
  `del_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '0' COMMENT '删除标识 0存在 1删除',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '备注',
  `create_by` bigint(20) NULL DEFAULT NULL COMMENT '创建人',
  `create_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_by` bigint(20) NULL DEFAULT NULL COMMENT '更新人',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 13 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '分成者表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for order_site_occupy_fee
-- ----------------------------
DROP TABLE IF EXISTS `order_site_occupy_fee`;
CREATE TABLE `order_site_occupy_fee`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `site_id` bigint(20) NULL DEFAULT NULL,
  `type` int(1) NULL DEFAULT NULL COMMENT '0-前置 1-后置',
  `status` int(1) NULL DEFAULT 1 COMMENT '0-禁用 1-开启',
  `free_duration` int(4) NULL DEFAULT NULL COMMENT '免费时长（分钟）',
  `unit_price` decimal(18, 4) NULL DEFAULT NULL COMMENT '单价（元/分钟）',
  `capped_amount` decimal(18, 4) NULL DEFAULT NULL COMMENT '封顶金额（元）',
  `create_by` bigint(20) NULL DEFAULT NULL COMMENT '创建者',
  `create_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_by` bigint(20) NULL DEFAULT NULL COMMENT '更新者',
  `update_time` datetime(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `site_id_2`(`site_id`, `type`) USING BTREE,
  INDEX `site_id`(`site_id`, `type`, `status`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '站点占位费表' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for pay_coupon
-- ----------------------------
DROP TABLE IF EXISTS `pay_coupon`;
CREATE TABLE `pay_coupon`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `sn` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '编号',
  `activity_id` bigint(20) NOT NULL COMMENT '活动id',
  `template_id` bigint(20) NOT NULL COMMENT '模板id',
  `user_id` bigint(20) NOT NULL COMMENT '用户id',
  `start_time` datetime(0) NULL DEFAULT NULL COMMENT '开始时间',
  `end_time` datetime(0) NULL DEFAULT NULL COMMENT '结束时间',
  `user_time` datetime(0) NULL DEFAULT NULL COMMENT '使用时间',
  `status` int(1) NULL DEFAULT 0 COMMENT '0-未使用 1-已使用',
  `create_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `user_id`(`user_id`, `status`, `start_time`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10002 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '优惠券表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for pay_coupon_activity
-- ----------------------------
DROP TABLE IF EXISTS `pay_coupon_activity`;
CREATE TABLE `pay_coupon_activity`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '活动名称',
  `operator_id` bigint(20) NULL DEFAULT NULL COMMENT '运营商ID',
  `status` int(1) NULL DEFAULT 1 COMMENT ' 0-未开始 1-进行中 2-已结束(自动到期) 3-已结束(手动停用)',
  `type` int(1) NULL DEFAULT NULL COMMENT '1-首次充电活动 2-单次充电活动 3-内部发券活动',
  `start_time` datetime(0) NULL DEFAULT NULL COMMENT '活动开始时间',
  `end_time` datetime(0) NULL DEFAULT NULL COMMENT '活动结束时间',
  `rule` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '规则url',
  `remark` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '介绍',
  `config` json NULL COMMENT '活动配置',
  `create_by` bigint(20) NULL DEFAULT NULL COMMENT '创建人',
  `create_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_by` bigint(20) NULL DEFAULT NULL COMMENT '修改人',
  `update_time` datetime(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `operator_id`(`operator_id`, `status`, `type`, `start_time`, `end_time`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '优惠券活动表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for pay_coupon_activity_limit_day
-- ----------------------------
DROP TABLE IF EXISTS `pay_coupon_activity_limit_day`;
CREATE TABLE `pay_coupon_activity_limit_day`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL COMMENT '用户id',
  `activity_id` bigint(20) NOT NULL COMMENT '活动id',
  `date` date NOT NULL COMMENT '领取日期',
  `number` int(12) NULL DEFAULT 1 COMMENT '已领次数',
  `create_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `version` int(20) NULL DEFAULT 0 COMMENT '版本号',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `user_id`(`user_id`, `activity_id`, `date`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '优惠券活动限领次数（天）表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for pay_coupon_activity_limit_total
-- ----------------------------
DROP TABLE IF EXISTS `pay_coupon_activity_limit_total`;
CREATE TABLE `pay_coupon_activity_limit_total`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL COMMENT '用户id',
  `activity_id` bigint(20) NOT NULL COMMENT '活动id',
  `number` int(12) NULL DEFAULT 1 COMMENT '已领次数',
  `create_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `version` int(20) NULL DEFAULT 0 COMMENT '版本号',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `user_id`(`user_id`, `activity_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '优惠券活动限领次数（总）表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for pay_coupon_activity_template
-- ----------------------------
DROP TABLE IF EXISTS `pay_coupon_activity_template`;
CREATE TABLE `pay_coupon_activity_template`  (
  `activity_id` bigint(20) NOT NULL COMMENT '活动id',
  `template_id` bigint(20) NOT NULL COMMENT '模板id',
  PRIMARY KEY (`activity_id`, `template_id`) USING BTREE,
  INDEX `template_id`(`template_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '优惠券活动模板表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for pay_coupon_activity_user
-- ----------------------------
DROP TABLE IF EXISTS `pay_coupon_activity_user`;
CREATE TABLE `pay_coupon_activity_user`  (
  `activity_id` bigint(20) NOT NULL COMMENT '活动id',
  `user_id` bigint(20) NOT NULL COMMENT '用户id',
  PRIMARY KEY (`activity_id`, `user_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '优惠券活动-用户表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for pay_coupon_template
-- ----------------------------
DROP TABLE IF EXISTS `pay_coupon_template`;
CREATE TABLE `pay_coupon_template`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '优惠券名称',
  `operator_id` bigint(20) NULL DEFAULT NULL COMMENT '运营商id',
  `status` int(1) NULL DEFAULT 1 COMMENT '0-暂停使用 1-正常',
  `coupon_type` tinyint(2) NULL DEFAULT NULL COMMENT '优惠券类型：0-现金券 1-折扣券',
  `face_value` decimal(10, 2) NULL DEFAULT NULL COMMENT '面额',
  `rate` int(10) NULL DEFAULT NULL COMMENT '折扣比例(%)',
  `total_number` int(12) NULL DEFAULT NULL COMMENT '总张数',
  `available_number` int(12) NULL DEFAULT NULL COMMENT '可用张数',
  `cancel_number` int(12) NULL DEFAULT 0 COMMENT '作废张数',
  `deduction_type` tinyint(1) NULL DEFAULT NULL COMMENT '可抵扣费用(0-服务费 1-总费用)',
  `fee_limit` decimal(10, 2) NULL COMMENT '满足总费用额度',
  `use_time_type` int(1) NULL DEFAULT NULL COMMENT '使用时间类型(0-相对时间 1-绝对时间)',
  `available_days` int(12) NULL DEFAULT NULL COMMENT '可使用天数',
  `start_time` date NULL DEFAULT NULL COMMENT '使用开始时间',
  `end_time` date NULL DEFAULT NULL COMMENT '使用结束时间',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  `site_dimension` int(1) NULL DEFAULT NULL COMMENT '可用站点维度(0-全部站点 1-部分站点)',
  `create_by` bigint(20) NULL DEFAULT NULL COMMENT '创建人',
  `create_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_by` bigint(20) NULL DEFAULT NULL COMMENT '修改人',
  `update_time` datetime(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '修改时间',
  `version` int(20) NULL DEFAULT 0 COMMENT '版本号',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `operator_id`(`operator_id`, `available_number`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '优惠券模板表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for pay_coupon_template_site
-- ----------------------------
DROP TABLE IF EXISTS `pay_coupon_template_site`;
CREATE TABLE `pay_coupon_template_site`  (
  `template_id` bigint(20) NOT NULL COMMENT '模板id',
  `site_id` bigint(20) NOT NULL COMMENT '站点id',
  PRIMARY KEY (`template_id`, `site_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '优惠券模板站点表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for pay_rule
-- ----------------------------
DROP TABLE IF EXISTS `pay_rule`;
CREATE TABLE `pay_rule`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `model_id` int(11) NOT NULL COMMENT '正常：模型编号（0-9999）',
  `del_flag` int(1) NULL DEFAULT 0 COMMENT '0-正常 1-删除',
  `name` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '规则名称',
  `operator_id` bigint(20) NULL DEFAULT NULL COMMENT '运营商ID',
  `operator_default` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否为运营商默认规则 1:是 0:否',
  `sys_default` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否为平台默认规则 1:是 0:否',
  `content` json NULL COMMENT '内容',
  `create_by` bigint(20) NULL DEFAULT NULL COMMENT '创建者',
  `create_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_by` bigint(20) NULL DEFAULT NULL COMMENT '更新者',
  `update_time` datetime(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  PRIMARY KEY (`id`, `model_id`) USING BTREE,
  UNIQUE INDEX `operator_id`(`operator_id`, `model_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '收费规则表' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for pay_site_discount_activity
-- ----------------------------
DROP TABLE IF EXISTS `pay_site_discount_activity`;
CREATE TABLE `pay_site_discount_activity`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '活动名称',
  `operator_id` bigint(20) NULL DEFAULT NULL COMMENT '运营商ID',
  `status` int(1) NULL DEFAULT 1 COMMENT '0-未开始 1-进行中 2-已结束(自动到期) 3-已结束(手动停用)',
  `site_dimension` int(1) NULL DEFAULT NULL COMMENT '可用站点维度(0-全部站点 1-部分站点)',
  `start_time` date NULL DEFAULT NULL COMMENT '活动开始时间',
  `end_time` date NULL DEFAULT NULL COMMENT '活动结束时间',
  `create_by` bigint(20) NULL DEFAULT NULL COMMENT '创建人',
  `create_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_by` bigint(20) NULL DEFAULT NULL COMMENT '修改人',
  `update_time` datetime(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '修改时间',
  `del_flag` int(255) NULL DEFAULT 0 COMMENT '0-正常 1-删除',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `operator_id`(`del_flag`, `operator_id`, `status`, `start_time`, `end_time`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '站点折扣活动表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for pay_site_discount_activity_site
-- ----------------------------
DROP TABLE IF EXISTS `pay_site_discount_activity_site`;
CREATE TABLE `pay_site_discount_activity_site`  (
  `activity_id` bigint(20) NOT NULL COMMENT '活动id',
  `site_id` bigint(20) NOT NULL COMMENT '站点id',
  PRIMARY KEY (`activity_id`, `site_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '站点活动-站点表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for pay_site_discount_activity_template
-- ----------------------------
DROP TABLE IF EXISTS `pay_site_discount_activity_template`;
CREATE TABLE `pay_site_discount_activity_template`  (
  `activity_id` bigint(20) NOT NULL COMMENT '活动id',
  `template_id` bigint(20) NOT NULL COMMENT '模板id',
  PRIMARY KEY (`activity_id`, `template_id`) USING BTREE,
  INDEX `template_id`(`template_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '站点活动模板表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for pay_site_discount_template
-- ----------------------------
DROP TABLE IF EXISTS `pay_site_discount_template`;
CREATE TABLE `pay_site_discount_template`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `operator_id` bigint(20) NOT NULL COMMENT '运营商ID',
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '模板名称',
  `activity_type` tinyint(1) NULL DEFAULT NULL COMMENT '活动类型(0-站点折扣 1-服务费折扣 2-站点一口价 3-服务费一口价)',
  `discount` decimal(18, 2) NULL DEFAULT NULL COMMENT '折扣数值(折扣为%)',
  `effective_type` tinyint(1) NULL DEFAULT NULL COMMENT '生效类型(0-全天 1-时段)',
  `start_time` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '时段开始时间',
  `end_time` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '时段结束时间',
  `create_by` bigint(20) NULL DEFAULT NULL COMMENT '创建人',
  `create_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_by` bigint(20) NULL DEFAULT NULL COMMENT '修改人',
  `update_time` datetime(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '修改时间',
  `del_flag` int(1) NULL DEFAULT 0 COMMENT ' 0-正常 1-删除',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `operator_id`(`del_flag`, `operator_id`, `start_time`, `end_time`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '站点折扣模板表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for system_amount_record
-- ----------------------------
DROP TABLE IF EXISTS `system_amount_record`;
CREATE TABLE `system_amount_record`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `card_no` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT 'IC卡号',
  `bind_user_id` bigint(20) NULL DEFAULT NULL COMMENT '记录绑定的用户ID',
  `operate_user_id` bigint(20) NULL DEFAULT NULL COMMENT '记录操作的用户ID',
  `serial_number` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '流水号',
  `amount` decimal(10, 2) NULL COMMENT '发生金额',
  `change_before` decimal(10, 2) NULL COMMENT '变化前金额',
  `change_after` decimal(10, 2) NULL COMMENT '变化后金额',
  `refund_amount` decimal(10, 2) NULL COMMENT '已退款金额',
  `channel` int(11) NULL DEFAULT NULL COMMENT '交易渠道：1账户余额，2IC卡，3微信授信',
  `module` int(11) NULL DEFAULT NULL COMMENT '服务模块(1代理商 2分成者 3注册用户 4IC卡)',
  `type` int(11) NULL DEFAULT NULL COMMENT '类型(1用户充值 2订单支付 3提现 4系统充值 5退款)',
  `record_type` int(11) NULL DEFAULT NULL COMMENT '收支类型 0-收入 1支出',
  `status` int(11) NULL DEFAULT NULL COMMENT '状态(1正常 0失败 2处理中)',
  `user_type` varchar(4) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '用户类型 01平台用户 02小程序用户',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '备注',
  `event_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '发生时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 239 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '金额记录表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for system_config
-- ----------------------------
DROP TABLE IF EXISTS `system_config`;
CREATE TABLE `system_config`  (
  `config_id` int(5) NOT NULL AUTO_INCREMENT COMMENT '参数主键',
  `config_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '参数名称',
  `config_key` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '参数键名',
  `config_value` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL COMMENT '参数键值',
  `config_type` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT 'N' COMMENT '系统内置（Y是 N否）',
  `create_by` bigint(20) NULL DEFAULT NULL COMMENT '创建者',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` bigint(20) NULL DEFAULT NULL COMMENT '更新者',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`config_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 101 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '参数配置表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for system_dict_data
-- ----------------------------
DROP TABLE IF EXISTS `system_dict_data`;
CREATE TABLE `system_dict_data`  (
  `dict_code` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '字典编码',
  `dict_sort` int(4) NULL DEFAULT 0 COMMENT '字典排序',
  `dict_label` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '字典标签',
  `dict_value` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '字典键值',
  `dict_type` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '字典类型',
  `css_class` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '样式属性（其他样式扩展）',
  `list_class` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '表格回显样式',
  `is_default` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT 'N' COMMENT '是否默认（Y是 N否）',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '0' COMMENT '状态（0正常 1停用）',
  `create_by` bigint(20) NULL DEFAULT NULL COMMENT '创建者',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` bigint(20) NULL DEFAULT NULL COMMENT '更新者',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`dict_code`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 30 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '字典数据表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for system_dict_type
-- ----------------------------
DROP TABLE IF EXISTS `system_dict_type`;
CREATE TABLE `system_dict_type`  (
  `dict_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '字典主键',
  `dict_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '字典名称',
  `dict_type` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '字典类型',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '0' COMMENT '状态（0正常 1停用）',
  `create_by` bigint(20) NULL DEFAULT NULL COMMENT '创建者',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` bigint(20) NULL DEFAULT NULL COMMENT '更新者',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`dict_id`) USING BTREE,
  UNIQUE INDEX `dict_type`(`dict_type`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 11 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '字典类型表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for system_logininfor
-- ----------------------------
DROP TABLE IF EXISTS `system_logininfor`;
CREATE TABLE `system_logininfor`  (
  `info_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '访问ID',
  `user_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '用户账号',
  `ipaddr` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '登录IP地址',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '1' COMMENT '登录状态（1成功 0失败）',
  `msg` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '提示信息',
  `access_time` datetime(0) NULL DEFAULT NULL COMMENT '访问时间',
  PRIMARY KEY (`info_id`) USING BTREE,
  INDEX `idx_system_logininfor_s`(`status`) USING BTREE,
  INDEX `idx_system_logininfor_lt`(`access_time`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 340 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '系统访问记录' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for system_menu
-- ----------------------------
DROP TABLE IF EXISTS `system_menu`;
CREATE TABLE `system_menu`  (
  `menu_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '菜单ID',
  `menu_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '菜单名称',
  `parent_id` bigint(20) NULL DEFAULT 0 COMMENT '父菜单ID',
  `order_num` int(4) NULL DEFAULT 0 COMMENT '显示顺序',
  `path` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '路由地址',
  `component` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '组件路径',
  `query` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '路由参数',
  `is_frame` int(1) NULL DEFAULT 0 COMMENT '是否为外链（1是 0否）',
  `is_cache` int(1) NULL DEFAULT 1 COMMENT '是否缓存（1缓存 0不缓存）',
  `menu_type` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '菜单类型（M目录 C菜单 F按钮）',
  `visible` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '1' COMMENT '菜单状态（1显示 0隐藏）',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '1' COMMENT '菜单状态（1正常 0停用）',
  `perms` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '权限标识',
  `icon` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '#' COMMENT '菜单图标',
  `create_by` bigint(20) NULL DEFAULT 1 COMMENT '创建者',
  `create_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_by` bigint(20) NULL DEFAULT NULL COMMENT '更新者',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '备注',
  PRIMARY KEY (`menu_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2632 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '菜单权限表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for system_notice
-- ----------------------------
DROP TABLE IF EXISTS `system_notice`;
CREATE TABLE `system_notice`  (
  `notice_id` int(4) NOT NULL AUTO_INCREMENT COMMENT '公告ID',
  `notice_title` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '公告标题',
  `notice_type` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '公告类型（1通知 2公告）',
  `notice_content` longblob NULL COMMENT '公告内容',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '1' COMMENT '公告状态（1启用 0停用）',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`notice_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '通知公告表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for system_oper_log
-- ----------------------------
DROP TABLE IF EXISTS `system_oper_log`;
CREATE TABLE `system_oper_log`  (
  `oper_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '日志主键',
  `title` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '模块标题',
  `business_type` int(2) NULL DEFAULT 0 COMMENT '业务类型（0其它 1新增 2修改 3删除）',
  `method` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '方法名称',
  `request_method` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '请求方式',
  `operator_type` int(1) NULL DEFAULT 0 COMMENT '操作类别（0其它 1后台用户 2手机端用户）',
  `oper_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '操作人员',
  `org_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '部门名称',
  `oper_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '请求URL',
  `oper_ip` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '主机地址',
  `oper_location` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '操作地点',
  `oper_param` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '请求参数',
  `json_result` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '返回参数',
  `status` int(1) NULL DEFAULT 1 COMMENT '操作状态（1正常 0异常）',
  `error_msg` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '错误消息',
  `oper_time` datetime(0) NULL DEFAULT NULL COMMENT '操作时间',
  `cost_time` bigint(20) NULL DEFAULT 0 COMMENT '消耗时间',
  PRIMARY KEY (`oper_id`) USING BTREE,
  INDEX `idx_system_oper_log_bt`(`business_type`) USING BTREE,
  INDEX `idx_system_oper_log_s`(`status`) USING BTREE,
  INDEX `idx_system_oper_log_ot`(`oper_time`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 242 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '操作日志记录' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for system_operator
-- ----------------------------
DROP TABLE IF EXISTS `system_operator`;
CREATE TABLE `system_operator`  (
  `operator_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '运营商ID',
  `user_id` bigint(20) NULL DEFAULT NULL COMMENT '用户ID',
  `name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '运营商名称',
  `address` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '运营商地址',
  `entry_date` date NULL DEFAULT NULL COMMENT '入驻日期',
  `commission_percent` decimal(10, 2) NULL COMMENT '运营商分成比例',
  `commission_type` int(11) NULL DEFAULT NULL COMMENT '分成类型（1电费 2服务费  3电费+服务费）',
  `platform_percent` decimal(10, 2) NULL COMMENT '平台分成比例',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '1' COMMENT '运营商状态（1启用 0停用）',
  `del_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '0' COMMENT '删除标志（0代表存在 1代表删除）',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '备注',
  `org_id` bigint(20) NULL DEFAULT NULL COMMENT '组织ID，冗余字段，方便查询',
  `create_by` bigint(20) NULL DEFAULT NULL COMMENT '创建者',
  `create_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_by` bigint(20) NULL DEFAULT NULL COMMENT '创建者',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新者',
  PRIMARY KEY (`operator_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '运营商表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for system_org
-- ----------------------------
DROP TABLE IF EXISTS `system_org`;
CREATE TABLE `system_org`  (
  `org_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '组织ID',
  `parent_id` bigint(20) NULL DEFAULT 0 COMMENT '父部门id',
  `ancestors` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '祖级列表',
  `org_name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '组织名称',
  `order_num` int(4) NULL DEFAULT 1 COMMENT '显示顺序',
  `leader` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '负责人',
  `phone` varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '联系电话',
  `email` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '邮箱',
  `type` int(11) NULL DEFAULT NULL COMMENT '类型 0平台用户 1运营商',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '1' COMMENT '部门状态（1正常 0异常）',
  `del_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '0' COMMENT '删除标志（0代表存在 1代表删除）',
  `create_by` bigint(20) NULL DEFAULT NULL COMMENT '创建者',
  `create_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_by` bigint(20) NULL DEFAULT NULL COMMENT '更新者',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`org_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 102 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '部门表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for system_org_site
-- ----------------------------
DROP TABLE IF EXISTS `system_org_site`;
CREATE TABLE `system_org_site`  (
  `org_id` bigint(20) NOT NULL COMMENT '组织ID',
  `site_id` bigint(20) NOT NULL COMMENT '站点ID',
  PRIMARY KEY (`org_id`, `site_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '组织站点关联表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for system_post
-- ----------------------------
DROP TABLE IF EXISTS `system_post`;
CREATE TABLE `system_post`  (
  `post_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '岗位ID',
  `post_code` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '岗位编码',
  `post_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '岗位名称',
  `post_sort` int(4) NOT NULL COMMENT '显示顺序',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '1' COMMENT '状态（1正常 0异常）',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`post_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '岗位信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for system_role
-- ----------------------------
DROP TABLE IF EXISTS `system_role`;
CREATE TABLE `system_role`  (
  `role_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '角色ID',
  `role_name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '角色名称',
  `role_code` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '角色唯一标识',
  `role_sort` int(4) NOT NULL DEFAULT 100 COMMENT '显示顺序',
  `data_scope` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '1' COMMENT '1=所有数据权限,2=自定义数据权限,3=本组织数据权限,4=本组织及以下数据权限,5=仅本人数据权限,6=代理商权限',
  `menu_check_strictly` tinyint(1) NULL DEFAULT 0 COMMENT '菜单树选择项是否关联显示',
  `org_check_strictly` tinyint(1) NULL DEFAULT 0 COMMENT '组织树选择项是否关联显示',
  `type` int(11) NULL DEFAULT 1 COMMENT '0系统预设 1手动',
  `org_type` int(11) NULL DEFAULT NULL COMMENT '角色组织类型，内置角色使用（0平台角色 1运营商角色）',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '1' COMMENT '角色状态（1正常 0异常）',
  `del_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '0' COMMENT '删除标志（0代表存在 1代表删除）',
  `home_menu_id` bigint(20) NULL DEFAULT NULL COMMENT '进入首页展示菜单的id',
  `half_menu_id_config` json NULL COMMENT '半选菜单id(前端使用)',
  `check_enable` bit(1) NULL DEFAULT b'1' COMMENT '是否可选（新增用户时是否可选用此角色）',
  `create_by` bigint(20) NULL DEFAULT NULL COMMENT '创建者',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` bigint(20) NULL DEFAULT NULL COMMENT '更新者',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '备注',
  `org_id` bigint(20) NULL DEFAULT NULL COMMENT '组织ID',
  PRIMARY KEY (`role_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 102 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '角色信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for system_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `system_role_menu`;
CREATE TABLE `system_role_menu`  (
  `role_id` bigint(20) NOT NULL COMMENT '角色ID',
  `menu_id` bigint(20) NOT NULL COMMENT '菜单ID',
  PRIMARY KEY (`role_id`, `menu_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '角色和菜单关联表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for system_role_org
-- ----------------------------
DROP TABLE IF EXISTS `system_role_org`;
CREATE TABLE `system_role_org`  (
  `role_id` bigint(20) NOT NULL COMMENT '角色ID',
  `org_id` bigint(20) NOT NULL COMMENT '组织ID',
  PRIMARY KEY (`role_id`, `org_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '角色和部门关联表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for system_user
-- ----------------------------
DROP TABLE IF EXISTS `system_user`;
CREATE TABLE `system_user`  (
  `user_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `org_id` bigint(20) NULL DEFAULT NULL COMMENT '组织ID',
  `username` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '用户账号',
  `real_name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '真实名称',
  `user_type` int(11) NULL DEFAULT 2 COMMENT '用户类型（0平台用户 1运营商管理员 2运营商用户）',
  `email` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '用户邮箱',
  `mobile` varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '手机号码',
  `avatar` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '头像地址',
  `password` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '密码',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '1' COMMENT '帐号状态（1正常 0异常）',
  `del_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '0' COMMENT '删除标志（0代表存在 1代表删除）',
  `operator_range` int(11) NULL DEFAULT 1 COMMENT '运营商维度(1全部运营商 2部分运营商)',
  `site_range` int(11) NULL DEFAULT 1 COMMENT '站点权限维度(1全部站点 2部分站点)',
  `login_ip` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '最后登录IP',
  `login_date` datetime(0) NULL DEFAULT NULL COMMENT '最后登录时间',
  `create_by` bigint(20) NULL DEFAULT NULL COMMENT '创建者',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` bigint(20) NULL DEFAULT NULL COMMENT '更新者',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '备注',
  `password_update_time` datetime(0) NULL DEFAULT NULL COMMENT '密码更新时间',
  PRIMARY KEY (`user_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 101 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '用户信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for system_user_account
-- ----------------------------
DROP TABLE IF EXISTS `system_user_account`;
CREATE TABLE `system_user_account`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `user_id` bigint(20) NOT NULL COMMENT '用户ID',
  `balance` decimal(10, 2) NOT NULL COMMENT '余额',
  `type` varchar(4) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '账号类型（01平台用户 02小程序用户）',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '备注',
  `version` bigint(20) NULL DEFAULT 0 COMMENT '版本号',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '1' COMMENT '账号状态 1正常 0停用',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `system_user_account_pk2`(`user_id`, `type`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 122 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '用户账号表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for system_user_operator
-- ----------------------------
DROP TABLE IF EXISTS `system_user_operator`;
CREATE TABLE `system_user_operator`  (
  `user_id` bigint(20) NOT NULL COMMENT '用户ID',
  `operator_id` bigint(20) NOT NULL COMMENT '运营商ID',
  PRIMARY KEY (`user_id`, `operator_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '用户运营商关联表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for system_user_post
-- ----------------------------
DROP TABLE IF EXISTS `system_user_post`;
CREATE TABLE `system_user_post`  (
  `user_id` bigint(20) NOT NULL COMMENT '用户ID',
  `post_id` bigint(20) NOT NULL COMMENT '岗位ID',
  PRIMARY KEY (`user_id`, `post_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '用户与岗位关联表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for system_user_role
-- ----------------------------
DROP TABLE IF EXISTS `system_user_role`;
CREATE TABLE `system_user_role`  (
  `user_id` bigint(20) NOT NULL COMMENT '用户ID',
  `role_id` bigint(20) NOT NULL COMMENT '角色ID',
  PRIMARY KEY (`user_id`, `role_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '用户和角色关联表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for system_user_site
-- ----------------------------
DROP TABLE IF EXISTS `system_user_site`;
CREATE TABLE `system_user_site`  (
  `user_id` bigint(20) NOT NULL COMMENT '用户ID',
  `site_id` bigint(20) NOT NULL COMMENT '站点ID',
  PRIMARY KEY (`site_id`, `user_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '用户站点关联表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for system_withdraw_record
-- ----------------------------
DROP TABLE IF EXISTS `system_withdraw_record`;
CREATE TABLE `system_withdraw_record`  (
  `id` bigint(20) NULL DEFAULT NULL COMMENT '主键ID',
  `user_id` bigint(20) NULL DEFAULT NULL COMMENT '申请用户ID（代理商用户和小程序注册用户）',
  `agent_id` bigint(20) NULL DEFAULT NULL COMMENT '代理商ID(只有代理商提现时才有此值)',
  `amount` decimal(10, 2) NULL COMMENT '发生金额',
  `gift_amount` decimal(10, 2) NULL COMMENT '赠送金额(待确认业务)',
  `module` int(11) NULL DEFAULT NULL COMMENT '服务模块(1代理商 2分成者 3注册用户 4IC卡)',
  `status` int(11) NULL DEFAULT NULL COMMENT '提现状态(0失败 1成功 2审核中)',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '备注',
  `event_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '申请时间',
  `handle_by` bigint(20) NULL DEFAULT NULL COMMENT '处理人',
  `handle_time` datetime(0) NULL DEFAULT NULL COMMENT '处理时间'
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '提现记录表' ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
