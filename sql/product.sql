/*
 Navicat MySQL Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 50731
 Source Host           : localhost:3306
 Source Schema         : product

 Target Server Type    : MySQL
 Target Server Version : 50731
 File Encoding         : 65001

 Date: 08/02/2021 17:16:01
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for check_account
-- ----------------------------
DROP TABLE IF EXISTS `check_account`;
CREATE TABLE `check_account`  (
  `check_id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT,
  `check_code` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '对账单编码',
  `years` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '对账年份',
  `months` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '对账月份',
  `sum_price` decimal(11, 2) NULL DEFAULT NULL COMMENT '账单金额',
  `remark` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '备注',
  `status` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '状态:制单，发布',
  `create_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  `operator` int(11) NULL DEFAULT NULL COMMENT '修改人',
  PRIMARY KEY (`check_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of check_account
-- ----------------------------
INSERT INTO `check_account` VALUES (1, '2021010001', '2021', '01', NULL, NULL, '制单', '2021-02-04 20:47:38', '2021-02-04 20:47:38', 1);
INSERT INTO `check_account` VALUES (2, '2021010001', '2021', '01', NULL, NULL, '制单', '2021-02-04 20:47:52', '2021-02-04 20:47:52', 1);

-- ----------------------------
-- Table structure for check_account_line
-- ----------------------------
DROP TABLE IF EXISTS `check_account_line`;
CREATE TABLE `check_account_line`  (
  `check_line_id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT,
  `check_id` int(11) NOT NULL COMMENT '对账单头ID',
  `order_code` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '订单编号',
  `row_num` smallint(6) NULL DEFAULT NULL COMMENT '订单行号',
  `unit` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '单位',
  `product_code` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '产品编码',
  `product_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '产品名称',
  `customer_code` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '客户编码',
  `customer_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '客户名称',
  `actual_quantity` int(11) NULL DEFAULT NULL COMMENT '实际数量',
  `finish_price` decimal(11, 2) NULL DEFAULT NULL COMMENT '结算价格',
  `finish_date` timestamp(0) NULL DEFAULT NULL COMMENT '完结日期',
  `remark` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '备注',
  PRIMARY KEY (`check_line_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for customer
-- ----------------------------
DROP TABLE IF EXISTS `customer`;
CREATE TABLE `customer`  (
  `customer_id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `customer_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '客户名称',
  `customer_code` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '客户编码',
  `phone_num` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '联系电话',
  `address` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '客户地址',
  `remark` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '备注',
  `create_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  `operator` int(10) NULL DEFAULT NULL COMMENT '修改人',
  PRIMARY KEY (`customer_id`) USING BTREE,
  UNIQUE INDEX `customer_u1`(`customer_code`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 60 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of customer
-- ----------------------------
INSERT INTO `customer` VALUES (4, '电机1', 'dj-1', '13025252', NULL, NULL, '2021-01-22 16:19:56', '2021-01-22 16:19:56', NULL);
INSERT INTO `customer` VALUES (5, '客户5', 'ct-4', '13025252', NULL, NULL, '2021-01-22 16:19:56', '2021-01-22 16:19:56', NULL);
INSERT INTO `customer` VALUES (6, '客户002', 'kh-003', NULL, NULL, NULL, NULL, '2021-01-28 17:55:42', NULL);
INSERT INTO `customer` VALUES (7, '客户7', 'ct-6', '13025252', NULL, NULL, '2021-01-22 16:19:56', '2021-01-22 16:19:56', NULL);
INSERT INTO `customer` VALUES (8, '机加工1', 'mz-1', '150121135', NULL, NULL, '2021-01-26 20:43:24', '2021-01-26 20:43:24', NULL);
INSERT INTO `customer` VALUES (9, '机加工2', 'mz-2', '150121135', NULL, NULL, '2021-01-26 20:43:25', '2021-01-26 20:43:25', NULL);
INSERT INTO `customer` VALUES (10, '机加工3', 'mz-3', '150121135', NULL, NULL, '2021-01-26 20:43:25', '2021-01-26 20:43:25', NULL);
INSERT INTO `customer` VALUES (11, '机加工4', 'mz-4', '150121135', NULL, NULL, '2021-01-26 20:43:25', '2021-01-26 20:43:25', NULL);
INSERT INTO `customer` VALUES (12, '机加工5', 'mz-5', '150121135', NULL, NULL, '2021-01-26 20:43:25', '2021-01-26 20:43:25', NULL);
INSERT INTO `customer` VALUES (13, '机加工6', 'mz-6', '150121135', NULL, NULL, '2021-01-26 20:43:25', '2021-01-26 20:43:25', NULL);
INSERT INTO `customer` VALUES (14, '机加工7', 'mz-7', '150121135', NULL, NULL, '2021-01-26 20:43:25', '2021-01-26 20:43:25', NULL);
INSERT INTO `customer` VALUES (15, '机加工8', 'mz-8', '150121135', NULL, NULL, '2021-01-26 20:43:25', '2021-01-26 20:43:25', NULL);
INSERT INTO `customer` VALUES (53, '123', '011', '123', '123', '123', '2021-02-08 15:21:00', '2021-02-08 16:18:55', NULL);
INSERT INTO `customer` VALUES (54, '123', '012', '123', '123', '123', '2021-02-08 16:20:40', '2021-02-08 16:20:42', NULL);
INSERT INTO `customer` VALUES (55, '123', '013', '213', '123', '123', '2021-02-08 16:21:17', '2021-02-08 16:21:22', NULL);
INSERT INTO `customer` VALUES (56, '123', '014', '123', '123', '123', '2021-02-08 16:23:12', '2021-02-08 16:23:18', NULL);
INSERT INTO `customer` VALUES (57, '213', '123', '123', '123', '123', '2021-02-08 16:23:53', '2021-02-08 16:23:58', NULL);
INSERT INTO `customer` VALUES (58, '123', '015', '123', '123', '123', '2021-02-08 16:24:27', '2021-02-08 16:24:32', NULL);
INSERT INTO `customer` VALUES (59, '123', '017', '123', '312', '其1', '2021-02-08 16:26:05', '2021-02-08 16:26:05', NULL);

-- ----------------------------
-- Table structure for customer_product
-- ----------------------------
DROP TABLE IF EXISTS `customer_product`;
CREATE TABLE `customer_product`  (
  `product_line_id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `customer_id` int(10) NOT NULL COMMENT '客户ID',
  `product_code` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '成品编码/图号',
  `price` decimal(10, 2) NOT NULL COMMENT '默认价格',
  `remark` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '备注',
  `create_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  `operator` int(11) NULL DEFAULT NULL COMMENT '修改人',
  PRIMARY KEY (`product_line_id`) USING BTREE,
  UNIQUE INDEX `cus_product_u1`(`customer_id`, `product_code`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 57 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of customer_product
-- ----------------------------
INSERT INTO `customer_product` VALUES (39, 5, '12312', 111.00, NULL, '2021-02-07 16:53:18', '2021-02-07 16:53:22', NULL);
INSERT INTO `customer_product` VALUES (40, 5, '123', 11.00, NULL, '2021-02-07 17:38:34', '2021-02-07 17:38:34', NULL);
INSERT INTO `customer_product` VALUES (44, 53, '反反复复', 1.00, '123', '2021-02-08 16:18:55', '2021-02-08 16:18:55', NULL);
INSERT INTO `customer_product` VALUES (45, 54, 'iijjjj', 0.00, '123', '2021-02-08 16:20:42', '2021-02-08 16:20:42', NULL);
INSERT INTO `customer_product` VALUES (47, 55, '44444', 444.00, '4444', '2021-02-08 16:21:22', '2021-02-08 16:21:22', NULL);
INSERT INTO `customer_product` VALUES (49, 56, '反反复复', 1.00, '123', '2021-02-08 16:23:15', '2021-02-08 16:23:15', NULL);
INSERT INTO `customer_product` VALUES (50, 56, 'iijjjj', 0.00, '123', '2021-02-08 16:23:18', '2021-02-08 16:23:18', NULL);
INSERT INTO `customer_product` VALUES (52, 57, '12312', 0.00, '123123', '2021-02-08 16:23:56', '2021-02-08 16:23:56', NULL);
INSERT INTO `customer_product` VALUES (53, 57, 'aab01', 10.00, '这个是描述', '2021-02-08 16:23:58', '2021-02-08 16:23:58', NULL);

-- ----------------------------
-- Table structure for material
-- ----------------------------
DROP TABLE IF EXISTS `material`;
CREATE TABLE `material`  (
  `material_id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `material_code` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '物料编码',
  `material_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '物料名称',
  `material_type` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '预留',
  `status` varchar(4) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '状态',
  `remark` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '备注',
  `create_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  `operator` int(10) NULL DEFAULT NULL COMMENT '修改人',
  PRIMARY KEY (`material_id`) USING BTREE,
  UNIQUE INDEX `material_u1`(`material_code`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 41 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of material
-- ----------------------------
INSERT INTO `material` VALUES (9, 'aj-3', '铜三', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `material` VALUES (10, 'aj-4', '铜四', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `material` VALUES (11, 'aj-5', '铜五', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `material` VALUES (12, 'aj-6', '铜六', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `material` VALUES (13, 'aj-7', '铜七', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `material` VALUES (14, 'aj-8', '铜八', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `material` VALUES (15, 'dd-1', '铁1', NULL, NULL, NULL, '2021-01-28 22:06:11', '2021-01-28 22:06:11', NULL);
INSERT INTO `material` VALUES (16, 'dd-2', '铁2', NULL, NULL, NULL, '2021-01-28 22:06:11', '2021-01-28 22:06:11', NULL);
INSERT INTO `material` VALUES (17, 'dd-3', '铁3', NULL, NULL, NULL, '2021-01-28 22:06:11', '2021-01-28 22:06:11', NULL);
INSERT INTO `material` VALUES (18, 'dd-4', '铁4', NULL, NULL, NULL, '2021-01-28 22:06:11', '2021-01-28 22:06:11', NULL);
INSERT INTO `material` VALUES (19, 'dd-5', '铁5', NULL, NULL, NULL, '2021-01-28 22:06:11', '2021-01-28 22:06:11', NULL);
INSERT INTO `material` VALUES (20, 'dd-6', '铁6', NULL, NULL, NULL, '2021-01-28 22:06:45', '2021-01-28 22:06:45', NULL);
INSERT INTO `material` VALUES (21, 'dd-7', '铁7', NULL, NULL, NULL, '2021-01-28 22:06:46', '2021-01-28 22:06:46', NULL);
INSERT INTO `material` VALUES (22, 'dd-8', '铁8', NULL, NULL, NULL, '2021-01-28 22:06:46', '2021-01-28 22:06:46', NULL);
INSERT INTO `material` VALUES (23, 'dd-9', '铁9', NULL, NULL, NULL, '2021-01-28 22:06:46', '2021-01-28 22:06:46', NULL);
INSERT INTO `material` VALUES (24, 'dd-10', '铁10', NULL, NULL, NULL, '2021-01-28 22:06:46', '2021-01-28 22:06:46', NULL);
INSERT INTO `material` VALUES (25, 'gg-1', '钢1', NULL, NULL, NULL, '2021-01-28 22:07:38', '2021-01-28 22:07:38', NULL);
INSERT INTO `material` VALUES (26, 'gg-2', '钢2', NULL, NULL, NULL, '2021-01-28 22:07:38', '2021-01-28 22:07:38', NULL);
INSERT INTO `material` VALUES (28, '11-1', '123123', NULL, NULL, '测试', '2021-02-02 21:21:35', '2021-02-02 21:21:35', NULL);
INSERT INTO `material` VALUES (29, '1111', '123213', NULL, NULL, 'ceshi a asdasd', '2021-02-02 21:21:55', '2021-02-02 21:30:49', NULL);
INSERT INTO `material` VALUES (34, 'cesssss', 'ces', NULL, NULL, 'ces', '2021-02-02 21:45:37', '2021-02-02 21:45:37', NULL);
INSERT INTO `material` VALUES (35, 'asuidbuiw2b', 'ahduiasb', NULL, NULL, '', '2021-02-02 21:47:49', '2021-02-02 21:47:49', NULL);
INSERT INTO `material` VALUES (36, '1982h1982h9812', '12hd8912hdf', NULL, NULL, '', '2021-02-02 21:48:22', '2021-02-02 21:48:22', NULL);
INSERT INTO `material` VALUES (37, '11', '1', NULL, NULL, '', '2021-02-02 21:48:31', '2021-02-02 21:48:31', NULL);
INSERT INTO `material` VALUES (38, '1982h1982h98121982h1982h9812', '1982h1982h9812', NULL, NULL, '', '2021-02-02 21:48:40', '2021-02-02 21:48:40', NULL);
INSERT INTO `material` VALUES (39, '1232132131231233', '123123123', NULL, NULL, '', '2021-02-02 21:51:40', '2021-02-02 21:51:40', NULL);
INSERT INTO `material` VALUES (40, 'ok', 'ok', NULL, NULL, '', '2021-02-03 20:25:39', '2021-02-03 20:25:39', NULL);

-- ----------------------------
-- Table structure for order_header
-- ----------------------------
DROP TABLE IF EXISTS `order_header`;
CREATE TABLE `order_header`  (
  `order_id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT,
  `order_type` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '订单类型：退货单，送货单',
  `order_num` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '订单编号',
  `status` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '制单' COMMENT '状态：A:制单，C:完结',
  `customer_code` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '客户编码',
  `customer_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '客户名称',
  `delivery_address` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '送货地址',
  `send_address` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '发货地址',
  `sum_price` decimal(10, 2) NULL DEFAULT NULL COMMENT '总金额',
  `remark` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '备注',
  `phone_num` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '联系电话',
  `finish_time` timestamp(0) NULL DEFAULT NULL COMMENT '完结时间',
  `create_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  `operator` int(11) NULL DEFAULT NULL COMMENT '修改人',
  PRIMARY KEY (`order_id`) USING BTREE,
  UNIQUE INDEX `order_header_u1`(`order_num`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of order_header
-- ----------------------------
INSERT INTO `order_header` VALUES (2, '送货单', 'SH202101290002', '制单', 'dj-1', NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2021-01-29 15:59:18', '2021-02-02 16:14:56', NULL);
INSERT INTO `order_header` VALUES (3, '送货单', 'SH202101290003', '制单', 'dj-1', NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2021-01-29 15:59:18', '2021-02-02 16:14:58', NULL);
INSERT INTO `order_header` VALUES (4, '送货单', 'TH202102010001', '制单', 'dj-1', '电机1', NULL, NULL, 0.00, NULL, NULL, NULL, '2021-02-01 15:29:22', '2021-02-01 15:29:22', NULL);
INSERT INTO `order_header` VALUES (5, '送货单', 'SH202102010001', '完结', 'dj-1', '电机1', NULL, NULL, 0.10, NULL, NULL, '2021-02-01 17:40:46', '2021-02-01 16:54:34', '2021-02-01 17:40:47', NULL);
INSERT INTO `order_header` VALUES (6, '送货单', 'SH202102010002', '制单', 'dj-1', '电机1', NULL, NULL, 0.00, NULL, NULL, NULL, '2021-02-01 16:54:37', '2021-02-01 16:54:37', NULL);

-- ----------------------------
-- Table structure for order_line
-- ----------------------------
DROP TABLE IF EXISTS `order_line`;
CREATE TABLE `order_line`  (
  `order_line_id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT,
  `order_id` int(11) NOT NULL COMMENT '订单头ID',
  `product_code` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '产品编号',
  `product_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '产品名称',
  `unit` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '件' COMMENT '单位',
  `quantity` int(11) NOT NULL COMMENT '订单数量',
  `actual_quantity` int(11) NULL DEFAULT NULL COMMENT '实际数量',
  `default_price` decimal(10, 2) NOT NULL COMMENT '默认单价',
  `finish_price` decimal(10, 2) NULL DEFAULT NULL COMMENT '成交单价',
  `remark` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '备注',
  `create_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  `operator` int(11) NULL DEFAULT NULL COMMENT '修改人',
  PRIMARY KEY (`order_line_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of order_line
-- ----------------------------
INSERT INTO `order_line` VALUES (1, 5, '321', '321', NULL, 2, NULL, 11.00, NULL, NULL, '2021-02-01 17:16:59', '2021-02-01 17:16:59', NULL);
INSERT INTO `order_line` VALUES (2, 5, 'ceshi', '123', NULL, 2, NULL, 0.10, NULL, NULL, '2021-02-01 17:33:59', '2021-02-01 17:35:38', NULL);
INSERT INTO `order_line` VALUES (3, 5, 'ceshi', '123', NULL, 2, 2, 0.10, 0.10, NULL, '2021-02-01 17:34:20', '2021-02-01 17:40:46', NULL);
INSERT INTO `order_line` VALUES (4, 5, 'ceshi', '123', '件', 2, NULL, 0.10, NULL, NULL, '2021-02-01 17:35:47', '2021-02-01 17:35:47', NULL);
INSERT INTO `order_line` VALUES (5, 5, 'ceshi', '123', '件', 2, NULL, 0.10, NULL, NULL, '2021-02-01 17:37:02', '2021-02-01 17:37:02', NULL);

-- ----------------------------
-- Table structure for product
-- ----------------------------
DROP TABLE IF EXISTS `product`;
CREATE TABLE `product`  (
  `product_id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `product_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '成品名称/图名',
  `product_code` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '成品编码/图号',
  `product_description` varchar(300) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '描述',
  `unit` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '件' COMMENT '单位',
  `price` decimal(10, 2) UNSIGNED NOT NULL COMMENT '默认单价',
  `material_code` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '使用物料',
  `size_description` varchar(300) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '开料尺寸',
  `create_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  `operator` int(10) NULL DEFAULT NULL COMMENT '修改人',
  PRIMARY KEY (`product_id`) USING BTREE,
  UNIQUE INDEX `product_u1`(`product_code`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 17 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of product
-- ----------------------------
INSERT INTO `product` VALUES (3, '产品ab01', 'aab01', '这个是描述', '件', 10.00, 'aj-2', '10*10*10', '2021-01-25 16:41:26', '2021-01-31 13:27:07', NULL);
INSERT INTO `product` VALUES (12, '12321', '12312', '123123', '件', 0.00, 'gg-2', '12321', '2021-02-02 21:42:20', '2021-02-02 21:42:20', NULL);
INSERT INTO `product` VALUES (13, '123', '123', '123', '件', 0.00, 'gg-2', '123', '2021-02-02 21:43:20', '2021-02-02 21:43:20', NULL);
INSERT INTO `product` VALUES (14, '123213', '44444', '4444', '件', 444.00, 'gg-2', '444', '2021-02-02 21:44:15', '2021-02-02 21:44:15', NULL);
INSERT INTO `product` VALUES (15, '123123', '反反复复', '123', '件', 1.00, 'gg-1', '123213', '2021-02-02 21:44:44', '2021-02-06 13:25:57', NULL);
INSERT INTO `product` VALUES (16, 'asdasd', 'iijjjj', '123', '件', 0.00, 'asuidbuiw2b', '12321', '2021-02-02 21:51:55', '2021-02-02 21:51:55', NULL);

-- ----------------------------
-- Table structure for product_map
-- ----------------------------
DROP TABLE IF EXISTS `product_map`;
CREATE TABLE `product_map`  (
  `map_Id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `type_code` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '业务编码:单据类型',
  `map_code` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '字典编码',
  `map_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '字典名称',
  `attribute1` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '属性1',
  `attribute2` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '属性2',
  `attribute3` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '属性3',
  `create_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  `operator` int(11) NULL DEFAULT NULL COMMENT '修改人',
  PRIMARY KEY (`map_Id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `user_id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `user_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `user_account` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `user_pwd` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `create_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0),
  `update_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0),
  PRIMARY KEY (`user_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1, '管理员', 'admin', '1234', NULL, '2021-01-20 17:49:09');

SET FOREIGN_KEY_CHECKS = 1;
