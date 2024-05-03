/*
 Navicat Premium Data Transfer

 Source Server         : root
 Source Server Type    : MySQL
 Source Server Version : 50744 (5.7.44)
 Source Host           : localhost:3306
 Source Schema         : smart-medicine

 Target Server Type    : MySQL
 Target Server Version : 50744 (5.7.44)
 File Encoding         : 65001

 Date: 03/05/2024 21:30:28
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for feedback
-- ----------------------------
DROP TABLE IF EXISTS `feedback`;
CREATE TABLE `feedback`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `name` varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '反馈用户',
  `email` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '邮箱地址',
  `title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '反馈标题',
  `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '反馈内容',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of feedback
-- ----------------------------

-- ----------------------------
-- Table structure for history
-- ----------------------------
DROP TABLE IF EXISTS `history`;
CREATE TABLE `history`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '用户搜索历史主键id',
  `user_id` int(11) NULL DEFAULT NULL COMMENT '用户ID',
  `keyword` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '搜索关键字',
  `operate_type` int(1) NULL DEFAULT NULL COMMENT '类型：1搜索，2科目，3药品',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 163 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of history
-- ----------------------------
INSERT INTO `history` VALUES (126, 4, '10,无', 1, '2022-05-03 16:09:34', '2022-05-03 16:09:34');
INSERT INTO `history` VALUES (127, 4, '10,无', 1, '2022-05-03 16:09:40', '2022-05-03 16:09:40');
INSERT INTO `history` VALUES (128, 4, '病毒性感冒', 2, '2022-05-03 16:09:48', '2022-05-03 16:09:48');
INSERT INTO `history` VALUES (129, 4, '10,无', 1, '2022-05-03 16:09:52', '2022-05-03 16:09:52');
INSERT INTO `history` VALUES (130, 4, '湿疹', 2, '2022-05-03 16:12:15', '2022-05-03 16:12:15');
INSERT INTO `history` VALUES (131, 4, '偏头痛', 2, '2022-05-03 16:12:49', '2022-05-03 16:12:49');
INSERT INTO `history` VALUES (132, 5, '7,无', 1, '2022-05-03 16:17:41', '2022-05-03 16:17:41');
INSERT INTO `history` VALUES (133, 5, '湿疹', 2, '2022-05-03 16:17:53', '2022-05-03 16:17:53');
INSERT INTO `history` VALUES (134, 5, '感冒', 2, '2022-05-03 16:18:08', '2022-05-03 16:18:08');
INSERT INTO `history` VALUES (135, 5, '17,无', 1, '2022-05-03 16:18:22', '2022-05-03 16:18:22');
INSERT INTO `history` VALUES (136, 5, '17,溃疡', 1, '2022-05-03 16:18:28', '2022-05-03 16:18:28');
INSERT INTO `history` VALUES (137, 5, '溃疡', 2, '2022-05-03 16:18:28', '2022-05-03 16:18:28');
INSERT INTO `history` VALUES (138, 5, '17,溃疡', 1, '2022-05-03 16:26:48', '2022-05-03 16:26:48');
INSERT INTO `history` VALUES (139, 5, '溃疡', 2, '2022-05-03 16:26:48', '2022-05-03 16:26:48');
INSERT INTO `history` VALUES (140, 5, '17,溃疡', 1, '2022-05-03 16:28:20', '2022-05-03 16:28:20');
INSERT INTO `history` VALUES (141, 5, '溃疡', 2, '2022-05-03 16:28:20', '2022-05-03 16:28:20');
INSERT INTO `history` VALUES (142, 5, '17,溃疡', 1, '2022-05-03 16:33:52', '2022-05-03 16:33:52');
INSERT INTO `history` VALUES (143, 5, '溃疡', 2, '2022-05-03 16:33:52', '2022-05-03 16:33:52');
INSERT INTO `history` VALUES (144, 5, '溃疡', 2, '2022-05-03 16:34:08', '2022-05-03 16:34:08');
INSERT INTO `history` VALUES (145, 5, '7,无', 1, '2022-05-03 16:37:57', '2022-05-03 16:37:57');
INSERT INTO `history` VALUES (146, 5, '9,无', 1, '2022-05-03 16:38:34', '2022-05-03 16:38:34');
INSERT INTO `history` VALUES (147, 5, '9,无', 1, '2022-05-03 16:41:59', '2022-05-03 16:41:59');
INSERT INTO `history` VALUES (148, 5, '9,无', 1, '2022-05-03 16:42:14', '2022-05-03 16:42:14');
INSERT INTO `history` VALUES (149, 5, '9,无', 1, '2022-05-03 16:42:45', '2022-05-03 16:42:45');
INSERT INTO `history` VALUES (150, 5, '9,无', 1, '2022-05-03 16:43:54', '2022-05-03 16:43:54');
INSERT INTO `history` VALUES (151, 5, '9,无', 1, '2022-05-03 16:44:26', '2022-05-03 16:44:26');
INSERT INTO `history` VALUES (152, 5, '9,无', 1, '2022-05-03 16:44:45', '2022-05-03 16:44:45');
INSERT INTO `history` VALUES (153, 5, '2,无', 1, '2022-05-03 16:44:51', '2022-05-03 16:44:51');
INSERT INTO `history` VALUES (154, 5, '2,无', 1, '2022-05-03 16:45:46', '2022-05-03 16:45:46');
INSERT INTO `history` VALUES (155, 5, '1', 3, '2022-05-07 15:34:34', '2022-05-07 15:34:34');
INSERT INTO `history` VALUES (156, 4, '牙周炎', 2, '2022-07-14 19:32:05', '2022-07-14 19:32:05');
INSERT INTO `history` VALUES (157, 4, '9,无', 1, '2022-07-14 19:32:52', '2022-07-14 19:32:52');
INSERT INTO `history` VALUES (158, 4, '1,无', 1, '2022-07-14 19:32:56', '2022-07-14 19:32:56');
INSERT INTO `history` VALUES (159, 4, '17,无', 1, '2022-07-14 19:32:59', '2022-07-14 19:32:59');
INSERT INTO `history` VALUES (160, 6, '湿疹', 2, '2024-04-18 16:59:47', '2024-04-18 16:59:47');
INSERT INTO `history` VALUES (161, 4, '高血压', 2, '2024-04-30 11:53:26', '2024-04-30 11:53:26');
INSERT INTO `history` VALUES (162, 4, '高血压', 2, '2024-04-30 12:01:49', '2024-04-30 12:01:49');

-- ----------------------------
-- Table structure for illness
-- ----------------------------
DROP TABLE IF EXISTS `illness`;
CREATE TABLE `illness`  (
  `id` int(1) NOT NULL AUTO_INCREMENT COMMENT '疾病id',
  `kind_id` int(11) NULL DEFAULT NULL COMMENT '疾病分类ID',
  `illness_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '疾病名字',
  `include_reason` mediumtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '诱发因素',
  `illness_symptom` mediumtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '疾病症状',
  `special_symptom` mediumtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '特殊症状',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 18 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of illness
-- ----------------------------
INSERT INTO `illness` VALUES (14, 2, '感冒', '感冒通常在受凉、淋雨或过度疲劳后，在身体抵抗力下降的情况下发生，但并不表示上述情况是致病原因。寒冷本身不会引起伤风。但是过度长时间、剧烈的寒冷影响会使免疫系统的免疫能力下降，导致人体对病毒的抵抗能力变弱。', '临床症状和体征无特异性,在24~72小时的潜伏期后突然起病, 伴鼻或喉部灼热感,随后出现打喷嚏,流涕和全身不适.不发热为本病特点,尤其是鼻病毒或冠状病毒感染时.通常早期有咽炎；喉炎和气管炎因人而异,也与不同致病原有关.鼻腔分泌物开始为大量水样,以后变为粘液样和脓性.粘液脓性分泌物并不说明有细菌重叠感染.咳嗽通常不剧烈但常持续2周.有慢性呼吸道疾病者感冒后常见支气管炎加剧.脓痰或严重下呼吸道感染症状提示鼻病毒外的病毒感染,并有原发的或继发的细菌感染.哮喘和支气管炎患者病毒感染后呼吸道症状常较严重.化脓性鼻窦炎和中耳炎为细菌性并发症,但偶尔也与粘膜内壁的病毒感染有关.如无并发症,感冒症状一般在4~10天内消失。', '上呼吸道感冒：一般会伴有上呼吸道某一部位或整个上呼吸道的炎症，包括鼻、鼻窦、喉、咽、并常累及气管和支气管。一年四季均可发生。全身症状轻，通常不发热，轻度畏寒、头痛。', '2024-04-30 10:53:49', '2024-04-30 10:53:49');
INSERT INTO `illness` VALUES (15, 2, '高血压', '高血压的发病原因多为遗传因素和不健康的生活方式，如高盐饮食、过量饮酒、长期精神紧张和体力活动不足等。 高血压在5类人群中易发：有家族史的人群；情绪易激动的人群；摄入盐量偏高的人群；嗜酒人群；工作或生活压力大的人群。', '大多数起病缓慢、渐进，一般缺乏特殊的临床表现。约1／5患者无症状，仅在测量血压时或发生心、脑、肾等并发症时才被发现。一般常见症状有头晕、头痛、颈项板紧、疲劳、心悸等，呈轻度持续性，多数症状可自行缓解，在紧张或劳累后加重。也可出现视力模糊、鼻出血等较重症状。症状与血压水平有一定的关联，因高血压性血管痉挛或扩张所致。典型的高血压头痛在血压下降后即可消失。高血压患者可以同时合并其他原因的头痛，往往与血压高度无关，例如精神焦虑性头痛、偏头痛、青光眼等。如果突然发生严重头晕与眩晕，要注意可能是短暂性脑缺血发作或者过度降压、直立性低血压，这在高血压合并动脉粥样硬化、心功能减退者容易发生。高血压患者还可以出现受累器官的症状，如胸闷、气短、心绞痛、多尿等。另外，有些症状可能是降压药的不良反应所致。', '高血压常见的并发症有冠心病、糖尿病、心力衰竭、高血脂、肾病、周围动脉疾病、中风、左心室肥厚等。在高血压的各种并发症中，以心、脑、肾的损害最为显著。高血压最严重的并发症是脑中风，发生脑中风的概率是正常血压人的7.76倍。', '2024-04-30 11:01:54', '2024-04-30 12:02:38');
INSERT INTO `illness` VALUES (16, 10, '艾滋病', '是一种受人类免疫缺乏病毒（又称艾滋病病毒, 简称HIV）感染后, 引发的一种综合症。艾滋病本身不是一种疾病，而是一种综合症。艾滋病病毒本身并不会引发任何疾病，但它会破坏人体免疫系统. 当免疫系统被艾滋病病毒破坏后，人体就容易感染其他的疾病, 这种无法抵抗其它疾病的状态和感染上其它疾病后表现出来的综合症状就是艾滋病。', '从感染艾滋病病毒到发病有一个完整的自然过程，临床上将这个过程分为四期：急性感染期、潜伏期、艾滋病前期、典型艾滋病期。不是每个感染者都会完整的出现四期表现，但每个疾病阶段的患者在临床上都可以见到。四个时期不同的临床表现是一个渐进的和连贯的病程发展过程。', '淋巴结肿大：此期最主要的临床表现之一。主要是浅表淋巴结肿大。发生的部位多见于头颈部、腋窝、腹股沟、颈后、耳前、耳后、股淋巴结、颌下淋巴结等。一般至少有两处以上的部位，有的多达十几处。肿大的淋巴结对一般治疗无反应，常持续肿大超过半年以上。约30%的病人临床上只有浅表淋巴结肿大，而无其他全身症状。\n全身症状：病人常有病毒性疾病的全身不适，肌肉疼痛等症状。约50%的病有疲倦无力及周期性低热，常持续数月。夜间盗汗，1月内多于5次。约1/3的病人体重减轻10%以上，这种体重减轻不能单纯用发热解释，补充足够的热量也不能控制这种体重减轻。有的病人头痛、抑郁或焦虑，有的出现感觉神经末梢病变，可能与病毒侵犯神经系统有关，有的可出现反应性精神紊乱。3/4的病人可出现脾肿大。', '2024-05-02 19:52:21', '2024-05-02 19:52:46');
INSERT INTO `illness` VALUES (17, 18, '白血病', '对白血病病因的精确原因还在研究中。一般骨髓干细胞内的DNA变异导致它们的恶化。其原因可以是暴露在放射线中、接触致癌物质和其它细胞内遗传物质的变异。病毒也可能导致白血病。', '白血病的症状，主要跟骨髓内造血功能的破坏有关。由于，白血球有穿渗进入组织的作用，部分症状也跟此种特性有关。大部分白血病的症状，没有特殊性，拥有这里列举症状的人，不一定是得到白血病。得到白血病的病人，也不一定会拥有这描述的所有症状。主要有感染发热、出血、剧烈头痛。', '肺部疾患\n由于白血病患者正常成熟中性粒细胞减少免疫功能降低，常常导致肺部感染。此外白血病细胞浸润可阻塞肺部小血管、支气管而发生呼吸困难、呼吸窘迫综合征, 胸片可有毛玻璃状或粟粒网状，可作肺部放射的试验性治疗。\n电解质失衡\n白积压病治疗过程中常因白血病细胞破坏过多或因化疗药物性肾损害等原因而排钾过多又因化疗引起饮食欲差，消化系统功能紊乱，纳入量不足而致低血钾或因白血病细胞破坏使磷释放增多，导致低钙等。因此在治疗过程中要注意钾钙、钠等电解质浓度。\n播散性血管内凝血（DIC）\n播散性血管是一组严重的出血综合征。', '2024-05-02 19:55:50', '2024-05-02 20:07:00');

-- ----------------------------
-- Table structure for illness_kind
-- ----------------------------
DROP TABLE IF EXISTS `illness_kind`;
CREATE TABLE `illness_kind`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '分类名称',
  `info` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '描述',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 19 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of illness_kind
-- ----------------------------
INSERT INTO `illness_kind` VALUES (1, '急诊科', '急诊科疾病', '2024-04-30 11:57:39', '2024-04-30 11:57:39');
INSERT INTO `illness_kind` VALUES (2, '内科', '内科疾病', '2024-04-30 11:57:39', '2024-04-30 11:57:39');
INSERT INTO `illness_kind` VALUES (3, '外科', '外科疾病', '2024-04-30 11:57:39', '2024-04-30 11:57:39');
INSERT INTO `illness_kind` VALUES (4, '妇产科', '妇产科疾病', '2024-04-30 11:57:39', '2024-04-30 11:57:39');
INSERT INTO `illness_kind` VALUES (5, '儿科', '儿科疾病', '2024-04-30 11:57:39', '2024-04-30 11:57:39');
INSERT INTO `illness_kind` VALUES (6, '男科', '男科疾病', '2024-04-30 11:57:39', '2024-04-30 11:57:39');
INSERT INTO `illness_kind` VALUES (7, '皮肤科', '皮肤科疾病', '2024-04-30 11:57:39', '2024-04-30 11:57:39');
INSERT INTO `illness_kind` VALUES (9, '肝病', '肝病疾病', '2024-04-30 11:57:39', '2024-04-30 11:57:39');
INSERT INTO `illness_kind` VALUES (10, '传染科', '传染科疾病', '2024-04-30 11:57:39', '2024-04-30 11:57:39');
INSERT INTO `illness_kind` VALUES (16, '耳鼻喉科', '耳鼻喉科疾病', '2024-04-30 11:57:39', '2024-04-30 11:57:39');
INSERT INTO `illness_kind` VALUES (17, '口腔科', '口腔科疾病', '2024-04-30 11:57:39', '2024-04-30 11:57:39');
INSERT INTO `illness_kind` VALUES (18, '血液科', '血液科疾病', '2024-05-02 19:59:33', '2024-05-02 19:59:33');

-- ----------------------------
-- Table structure for illness_medicine
-- ----------------------------
DROP TABLE IF EXISTS `illness_medicine`;
CREATE TABLE `illness_medicine`  (
  `id` int(1) NOT NULL AUTO_INCREMENT COMMENT '病和药品关联id',
  `illness_id` int(1) NULL DEFAULT NULL COMMENT '病id',
  `medicine_id` int(1) NULL DEFAULT NULL COMMENT '药品id',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 22 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of illness_medicine
-- ----------------------------
INSERT INTO `illness_medicine` VALUES (21, 15, 8, '2024-04-30 12:02:49', '2024-04-30 12:02:49');

-- ----------------------------
-- Table structure for medicine
-- ----------------------------
DROP TABLE IF EXISTS `medicine`;
CREATE TABLE `medicine`  (
  `id` int(1) NOT NULL AUTO_INCREMENT COMMENT '药品主键ID',
  `medicine_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '药的名字',
  `keyword` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '关键字搜索',
  `medicine_effect` mediumtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '药的功效',
  `medicine_brand` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '药的品牌',
  `interaction` mediumtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '药的相互作用',
  `taboo` mediumtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '禁忌',
  `us_age` mediumtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '用法用量',
  `medicine_type` int(1) NULL DEFAULT NULL COMMENT '药的类型，0西药，1中药，2中成药',
  `img_path` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '相关图片路径',
  `medicine_price` decimal(10, 2) NULL DEFAULT NULL COMMENT '药的价格',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 12 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of medicine
-- ----------------------------
INSERT INTO `medicine` VALUES (8, '氢氯噻嗪', '氢氯噻嗪', '主要抑制远曲小管近端对Na+和Cl-的重吸收，使肾脏对氯化钠的排泄增加而产生利尿作用,是一种中效利尿药。\n本品有降压作用。\n本品还有抗利尿的作用，可用于治疗尿崩症。', '曼斯特生物科技有限公司', '无。', '1.长期服用，易发生电解质平衡紊乱，如低钠血症、低氯血症、低钾血症性碱血症。\n2.潴留现象 如高尿酸血症、高钙血症，主要是药物减少细胞外液容量，增加近曲小管对尿酸的再吸收所致，痛风者慎用。\n3．代谢性变化 与剂量有关，可致高血糖、高脂血症。致肾素、醛固酮的过度分泌。对血脂可使血清甘油三酯及低密度脂蛋白-胆固醇（LDL-cho）量增加，同时伴有高密度脂蛋白（HDL）的减少，有报道同时应用β受体阻断药可防止利尿药引起的LDL-cho的升高。还可降低糖耐量，使血糖升高，可能是抑制了胰岛素的分泌或抑制肝PDE，因此使cAMP中介的糖原分解作用加强，糖尿病者慎用。\n4．高敏反应 如发热、皮疹、过敏反应。\n5．其他 可增高血尿素氮，加重肾功能不良。无尿及对磺胺过敏者禁用本类药物。\n6.不可突然停药，应逐渐减量。\n7.在治疗肝硬化而引起的腹水时，最好与螺内酯合用，以防止发生肝昏迷。\n8.肝肾功能不全者、痛风、糖尿病患者慎用。', '1次1片，1日3次。', 1, 'https://smart-medi01.oss-cn-beijing.aliyuncs.com/4/df6d6b9e76a54125928c01690ef85aee.png', 25.00, '2024-04-30 11:58:55', '2024-04-30 11:58:55');
INSERT INTO `medicine` VALUES (9, '阿司匹林', '阿司匹林', '主要治疗以下疾病：\n镇痛、解热\n消炎、抗风湿\n关节炎\n抗血栓\n皮肤粘膜淋巴结综合症(川崎病)', '拜阿司匹灵', '无。', '一般用于解热镇痛的剂量很少引起不良反应。但长期大量用药(如治疗风湿热)、尤其是当药物血浓度＞200μg／ml时则较易出现副作用。血浓度愈高，副作用愈明显。', '成人常用量口服。\n解热、镇痛，一次—0.6g，一日3次，必要时每4小时1次', 1, 'https://smart-medi01.oss-cn-beijing.aliyuncs.com/4/ca67510e843a4473ba180b836cbdfb50.jpg', 13.62, '2024-04-30 12:06:35', '2024-05-02 12:09:42');
INSERT INTO `medicine` VALUES (10, '氯霉素', '氯霉素', '氯霉素具有广谱抗菌作用。在需氧革兰氏阳性细菌中，对草绿色链球菌、白喉杆菌、炭疽杆菌、金黄色葡萄球菌、溶血性链球菌、肺炎链球菌等均敏感，对D组链球菌则相对不敏感；在需氧革兰氏阴性菌中，对流感杆菌、志贺氏菌属、百日咳杆菌、淋球菌及脑膜炎球菌均有良好抗菌作用，对沙门氏菌属、大肠杆菌属、奇异变形杆菌、霍乱弧菌等亦敏感，而对粘质塞拉蒂(原译沙雷)氏菌、肠杆菌属、克雷伯氏肺炎杆菌则不甚敏感。', '平光', '氯霉素类抗生素可作用于细菌核糖核蛋白体的50S亚基，而阻挠蛋白质的合成，属抑菌性广谱抗生素。\n细菌细胞的70S核糖体是合成蛋白质的主要细胞成分，它包括50S和30S两个亚基。氯霉素通过可逆地与50S亚基结合,阻断转肽酰酶的作用，干扰带有氨基酸的胺基酰-tRNA终端与50S亚基结合，从而使新肽链的形成受阻，抑制蛋白质合成。', '主要不良反应是抑制骨髓造血机能。症状有二：一为可逆的各类血细胞减少，其中粒细胞首先下降，这一反应与剂量和疗程有关。一旦发现，应及时停药，可以恢复；二是不可逆的再生障碍性贫血，虽然少见，但死亡率高。此反应属于变态反应与剂量疗程无直接关系。', '口服。成人一日1.5～3g，分3～4次服用；小儿按体重一日25～50mg/kg，分3～4次服用；新生儿一日不超过25mg/kg，分4次服用。', 1, 'https://smart-medi01.oss-cn-beijing.aliyuncs.com/4/945ec147daac406fb8cceb26b059c0fe.jpg', 17.40, '2024-05-02 20:10:16', '2024-05-02 20:10:16');
INSERT INTO `medicine` VALUES (11, '对乙酰氨基酚', '对乙酰氨基酚，扑热息痛', '对乙酰氨基酚（Paracetamol），又称扑热息痛（英文音译），是一种非甾体抗炎药。以其为基本成分制成片剂、胶囊剂、咀嚼片、栓剂、口服液、注射剂等，均为非处方药物（OTC），患者可在药店自行购买，但应咨询医师意见。本品主要用于感冒发热、偏头痛、神经痛、关节痛等。不良反应较少，偶可引起胃肠出血。', '散利痛', '交叉过敏反应：对阿司匹林过敏者对本品一般不发生过敏反应，但有报告在因阿司匹林过敏发生喘息的病人中，少数（＜5%＝可于应用本品后发生轻度支气管痉挛性反应。　', '对本品过敏及严重肝肾功能不全者禁用。', '口服，6岁以上儿童每一次0.5片～1片；成人一次1～2片，一日3次。可以用水或饮料吞服。', 1, 'https://smart-medi01.oss-cn-beijing.aliyuncs.com/4/609afde4abde435fab69c88d368c478e.jpg', 14.72, '2024-05-02 20:12:37', '2024-05-02 20:12:37');

-- ----------------------------
-- Table structure for pageview
-- ----------------------------
DROP TABLE IF EXISTS `pageview`;
CREATE TABLE `pageview`  (
  `id` int(1) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `pageviews` int(1) NULL DEFAULT NULL COMMENT '浏览量',
  `illness_id` int(11) NULL DEFAULT NULL COMMENT '病的id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 16 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of pageview
-- ----------------------------
INSERT INTO `pageview` VALUES (5, 5, 1);
INSERT INTO `pageview` VALUES (6, 5, 13);
INSERT INTO `pageview` VALUES (7, 2, 4);
INSERT INTO `pageview` VALUES (8, 1, 2);
INSERT INTO `pageview` VALUES (9, 1, 3);
INSERT INTO `pageview` VALUES (10, 1, 5);
INSERT INTO `pageview` VALUES (11, 1, 6);
INSERT INTO `pageview` VALUES (12, 2, 7);
INSERT INTO `pageview` VALUES (13, 1, 8);
INSERT INTO `pageview` VALUES (14, 1, 9);
INSERT INTO `pageview` VALUES (15, 2, 15);

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '用户主键id',
  `user_account` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户账号',
  `user_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户的真实名字',
  `user_pwd` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户密码',
  `user_age` int(11) NULL DEFAULT NULL COMMENT '用户年龄',
  `user_sex` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户性别',
  `user_email` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户邮箱',
  `user_tel` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '手机号',
  `role_status` int(11) NULL DEFAULT NULL COMMENT '角色状态，1管理员，0普通用户',
  `img_path` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户头像',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `create_time`(`create_time`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (4, 'admin', '管理员', '123456', 23, '男', '2678788262@qq.com', '17746678954', 1, 'https://moti-cloud-v2.oss-cn-beijing.aliyuncs.com/Snipaste_2022-05-01_15-37-01.png', '2022-05-03 15:55:41', '2022-05-03 15:56:15');
INSERT INTO `user` VALUES (5, 'zhangsan', '张三', '123456', 23, '女', 'isxuewei@qq.com', '17879544343', 0, 'https://su-share.oss-cn-beijing.aliyuncs.com/5/5dc107dcd2db4cbd8ad561f4c1642886.png', '2022-05-03 16:15:53', '2022-05-03 16:17:12');
INSERT INTO `user` VALUES (6, 'test_user01', '张三', '123456', 22, '男', '3153474402@qq.com', '12345678901', 0, 'https://moti-cloud-v2.oss-cn-beijing.aliyuncs.com/Snipaste_2022-05-01_15-37-01.png', '2024-04-18 16:59:39', '2024-04-18 16:59:39');

SET FOREIGN_KEY_CHECKS = 1;
