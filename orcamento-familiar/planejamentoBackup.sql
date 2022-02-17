CREATE DATABASE  IF NOT EXISTS `planejamentofamiliar`;
USE `planejamentofamiliar`;


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;



DROP TABLE IF EXISTS `despesa`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `despesa` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `data` date DEFAULT NULL,
  `descricao` varchar(255) DEFAULT NULL,
  `valor` decimal(19,2) DEFAULT NULL,
  `categoria` varchar(255) DEFAULT NULL,
  `usuario_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKg0dmhghr0iurl20kqixpqorjj` (`usuario_id`),
  CONSTRAINT `FKg0dmhghr0iurl20kqixpqorjj` FOREIGN KEY (`usuario_id`) REFERENCES `usuarios` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;



LOCK TABLES `despesa` WRITE;
/*!40000 ALTER TABLE `despesa` DISABLE KEYS */;
INSERT INTO `despesa` VALUES (2,'2022-02-27','Aluguel',1000.00,'Moradia',NULL),(3,'2022-01-10','Conta De Luz',200.00,'Moradia',NULL),(4,'2022-01-10','Conta De Agua',100.00,'Moradia',NULL),(5,'2022-01-15','Internet',99.00,'Moradia',NULL),(6,'2022-02-15','Internet',99.00,'Moradia',NULL),(7,'2022-03-15','Internet',99.00,'Moradia',NULL),(8,'2022-04-15','Internet',99.00,'Moradia',NULL),(9,'2022-05-15','Internet',99.00,'Moradia',NULL),(10,'2022-02-15','Oftalmo',300.00,'Saúde',NULL),(11,'2022-02-15','Nutricionista',400.00,'Saúde',NULL),(12,'2022-05-15','Nutricionista',400.00,'Saúde',1),(13,'2022-07-15','Nutricionista',400.00,'Saúde',1);
/*!40000 ALTER TABLE `despesa` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `perfis`
--

DROP TABLE IF EXISTS `perfis`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `perfis` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `nome` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `perfis`
--

LOCK TABLES `perfis` WRITE;
/*!40000 ALTER TABLE `perfis` DISABLE KEYS */;
INSERT INTO `perfis` VALUES (1,'admin');
/*!40000 ALTER TABLE `perfis` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `receita`
--

DROP TABLE IF EXISTS `receita`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `receita` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `data` date DEFAULT NULL,
  `descricao` varchar(255) DEFAULT NULL,
  `valor` decimal(19,2) DEFAULT NULL,
  `usuario_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKtrydt04bvx3kfv5w2848a3sy` (`usuario_id`),
  CONSTRAINT `FKtrydt04bvx3kfv5w2848a3sy` FOREIGN KEY (`usuario_id`) REFERENCES `usuarios` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `receita`
--

LOCK TABLES `receita` WRITE;
/*!40000 ALTER TABLE `receita` DISABLE KEYS */;
INSERT INTO `receita` VALUES (1,'2022-04-10','Luz',150.00,NULL),(3,'2022-01-16','Internet',99.00,NULL),(4,'2022-01-20','Internet',99.00,NULL),(5,'2022-02-20','Internet',99.00,NULL),(6,'2022-03-20','Internet',99.00,NULL),(7,'2022-04-21','Internet',99.00,NULL),(8,'2022-01-27','Salario',2000.00,NULL),(9,'2022-05-27','Salario',2000.00,1);
/*!40000 ALTER TABLE `receita` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usuarios`
--

DROP TABLE IF EXISTS `usuarios`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `usuarios` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `login` varchar(255) DEFAULT NULL,
  `nome` varchar(255) DEFAULT NULL,
  `senha` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usuarios`
--

LOCK TABLES `usuarios` WRITE;
/*!40000 ALTER TABLE `usuarios` DISABLE KEYS */;
INSERT INTO `usuarios` VALUES (1,'joao2022','Joao','$2a$10$S/0TVYy/uzHvO2xQgztfNOQiAksLNlzYvoLimrd20ZAy6ESRi4IsC'),(2,'michael','Michael Diogo','$2a$10$vSM7mQduwe2lIiHA3d3Sf.ZmZg1KIEmNO9EZTUWipeTvOLhD8OjDS'),(3,'reus','Marco Reus','$2a$10$6baoIcITuK4zIL5ykmexaOymbwslqbyPvos2rZS/DKQrxI0sMystu');
/*!40000 ALTER TABLE `usuarios` ENABLE KEYS */;
UNLOCK TABLES;


DROP TABLE IF EXISTS `usuarios_perfis`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `usuarios_perfis` (
  `usuario_id` bigint NOT NULL,
  `perfis_id` bigint NOT NULL,
  KEY `FKcvq90lk95py1n889uimu18atx` (`perfis_id`),
  KEY `FKotpfkn8c9nmhqqy4pb8hkgp18` (`usuario_id`),
  CONSTRAINT `FKcvq90lk95py1n889uimu18atx` FOREIGN KEY (`perfis_id`) REFERENCES `perfis` (`id`),
  CONSTRAINT `FKotpfkn8c9nmhqqy4pb8hkgp18` FOREIGN KEY (`usuario_id`) REFERENCES `usuarios` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;


LOCK TABLES `usuarios_perfis` WRITE;
/*!40000 ALTER TABLE `usuarios_perfis` DISABLE KEYS */;
/*!40000 ALTER TABLE `usuarios_perfis` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-02-14 21:20:58
