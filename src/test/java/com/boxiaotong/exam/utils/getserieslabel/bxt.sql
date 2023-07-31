-- MySQL dump 10.13  Distrib 8.0.33, for macos13.3 (arm64)
--
-- Host: 127.0.0.1    Database: bxt
-- ------------------------------------------------------
-- Server version	8.0.33

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `brand_map`
--

DROP TABLE IF EXISTS `brand_map`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `brand_map` (
  `id` int NOT NULL AUTO_INCREMENT,
  `brand` varchar(30) NOT NULL,
  `first_key` varchar(30) NOT NULL,
  `second_key` varchar(30) DEFAULT NULL,
  `third_key` varchar(30) DEFAULT NULL,
  `fourth_key` varchar(30) DEFAULT NULL,
  `series` varchar(30) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `brand_map`
--

LOCK TABLES `brand_map` WRITE;
/*!40000 ALTER TABLE `brand_map` DISABLE KEYS */;
INSERT INTO `brand_map` VALUES (1,'雪花','8度','8°',NULL,NULL,'清爽'),(2,'雪花','淡爽',NULL,NULL,NULL,'淡爽'),(3,'迷失海岸','花生巧克力牛奶世涛','花生','巧克力','牛奶','花生巧克力牛奶世涛'),(4,'海妖精酿','海妖之泪',NULL,NULL,NULL,'海妖之泪');
/*!40000 ALTER TABLE `brand_map` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `goods`
--

DROP TABLE IF EXISTS `goods`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `goods` (
  `id` int NOT NULL AUTO_INCREMENT,
  `brand` varchar(30) NOT NULL,
  `name` varchar(255) NOT NULL,
  `series` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `goods`
--

LOCK TABLES `goods` WRITE;
/*!40000 ALTER TABLE `goods` DISABLE KEYS */;
INSERT INTO `goods` VALUES (1,'其他','菠萝啤整箱装 24罐*320ml零酒精果啤果味汽水碳酸饮料夏日饮品',NULL),(2,'雪花','SNOW雪花纯生啤酒8度500ml*12罐匠心营造易拉罐装整箱黄啤酒 500mL*12瓶',NULL),(3,'雪花','雪花啤酒8°清爽啤酒330ml*24听 罐装整箱麦芽酿制 武汉满百包邮',NULL),(4,'雪花','雪花（SNOW）啤酒  淡爽  500ml*12听  整箱装  送礼自饮佳品',NULL),(5,'迷失海岸','进口精酿啤酒迷失海岸花生酱牛奶世涛卡斯四料特浓巧克力组合装',NULL),(6,'迷失海岸','迷失海岸美国进口精酿啤酒巧克力牛奶花生酱迷雾快艇幽灵浑浊IPA美国原装进口 17种口味可选 355ml 单瓶',NULL),(7,'海妖精酿','海妖精酿啤酒瓶装比利时小麦白啤330ml12瓶包邮',NULL);
/*!40000 ALTER TABLE `goods` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-07-31 11:59:25
