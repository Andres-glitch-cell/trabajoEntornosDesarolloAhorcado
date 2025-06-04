-- MySQL dump 10.13  Distrib 8.0.39, for Linux (x86_64)
--
-- Host: localhost    Database: ahorcadoJuegoEntornosJesus
-- ------------------------------------------------------
-- Server version	8.0.39-0ubuntu0.22.04.1

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
-- Table structure for table `Administrador`
--

DROP TABLE IF EXISTS `Administrador`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Administrador` (
  `idAdministrador` int NOT NULL AUTO_INCREMENT,
  `idUsuario` int DEFAULT NULL,
  `nivelAdministrador` varchar(20) DEFAULT NULL,
  `fechaAsignacion` datetime DEFAULT NULL,
  `ultimoAcceso` datetime DEFAULT NULL,
  PRIMARY KEY (`idAdministrador`),
  KEY `idUsuario` (`idUsuario`),
  CONSTRAINT `Administrador_ibfk_1` FOREIGN KEY (`idUsuario`) REFERENCES `Usuario` (`idUsuario`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Administrador`
--

LOCK TABLES `Administrador` WRITE;
/*!40000 ALTER TABLE `Administrador` DISABLE KEYS */;
INSERT INTO `Administrador` VALUES (1,1,'Senior','2025-04-01 12:00:00','2025-04-04 09:00:00'),(2,2,'Junior','2025-04-02 16:00:00','2025-04-03 14:00:00');
/*!40000 ALTER TABLE `Administrador` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Cuota`
--

DROP TABLE IF EXISTS `Cuota`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Cuota` (
  `idCuota` int NOT NULL AUTO_INCREMENT,
  `TipoDeCuota` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`idCuota`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Cuota`
--

LOCK TABLES `Cuota` WRITE;
/*!40000 ALTER TABLE `Cuota` DISABLE KEYS */;
INSERT INTO `Cuota` VALUES (1,'Mensual'),(2,'Anual');
/*!40000 ALTER TABLE `Cuota` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Historial`
--

DROP TABLE IF EXISTS `Historial`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Historial` (
  `idHistorial` int NOT NULL AUTO_INCREMENT,
  `idPalabra` int DEFAULT NULL,
  `idFrase` int DEFAULT NULL,
  `idAdministrador` int DEFAULT NULL,
  `significadoFrase` text,
  `significadoPalabra` text,
  PRIMARY KEY (`idHistorial`),
  KEY `idPalabra` (`idPalabra`),
  KEY `idFrase` (`idFrase`),
  KEY `idAdministrador` (`idAdministrador`),
  CONSTRAINT `Historial_ibfk_1` FOREIGN KEY (`idPalabra`) REFERENCES `PalabrasFrases` (`idPalabra`),
  CONSTRAINT `Historial_ibfk_2` FOREIGN KEY (`idFrase`) REFERENCES `PalabrasFrases` (`idPalabra`),
  CONSTRAINT `Historial_ibfk_3` FOREIGN KEY (`idAdministrador`) REFERENCES `Administrador` (`idAdministrador`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Historial`
--

LOCK TABLES `Historial` WRITE;
/*!40000 ALTER TABLE `Historial` DISABLE KEYS */;
INSERT INTO `Historial` VALUES (1,1,NULL,1,NULL,'Estrella que ilumina la Tierra'),(2,2,2,2,'Descripción del clima',NULL);
/*!40000 ALTER TABLE `Historial` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Idioma`
--

DROP TABLE IF EXISTS `Idioma`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Idioma` (
  `idIdioma` int NOT NULL AUTO_INCREMENT,
  `nombreIdioma` varchar(50) DEFAULT NULL,
  `codigoISO` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`idIdioma`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Idioma`
--

LOCK TABLES `Idioma` WRITE;
/*!40000 ALTER TABLE `Idioma` DISABLE KEYS */;
INSERT INTO `Idioma` VALUES (1,'Español','ES'),(2,'Inglés','EN');
/*!40000 ALTER TABLE `Idioma` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Juego`
--

DROP TABLE IF EXISTS `Juego`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Juego` (
  `idJuego` int NOT NULL AUTO_INCREMENT,
  `resultado` varchar(50) DEFAULT NULL,
  `modoJuego` varchar(50) DEFAULT NULL,
  `maxIntentos` int DEFAULT NULL,
  `dificultadBase` int DEFAULT NULL,
  PRIMARY KEY (`idJuego`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Juego`
--

LOCK TABLES `Juego` WRITE;
/*!40000 ALTER TABLE `Juego` DISABLE KEYS */;
INSERT INTO `Juego` VALUES (1,'Ganado','Clásico',6,3),(2,'Perdido','Rápido',5,2);
/*!40000 ALTER TABLE `Juego` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Jugador`
--

DROP TABLE IF EXISTS `Jugador`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Jugador` (
  `idJugador` int NOT NULL AUTO_INCREMENT,
  `idUsuario` int DEFAULT NULL,
  `puntosTotales` int DEFAULT NULL,
  `primeraPartida` datetime DEFAULT NULL,
  `ultimaPartida` datetime DEFAULT NULL,
  PRIMARY KEY (`idJugador`),
  KEY `idUsuario` (`idUsuario`),
  CONSTRAINT `Jugador_ibfk_1` FOREIGN KEY (`idUsuario`) REFERENCES `Usuario` (`idUsuario`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Jugador`
--

LOCK TABLES `Jugador` WRITE;
/*!40000 ALTER TABLE `Jugador` DISABLE KEYS */;
INSERT INTO `Jugador` VALUES (1,1,150,'2025-04-01 11:00:00','2025-04-03 18:00:00'),(2,2,80,'2025-04-02 17:00:00','2025-04-04 10:00:00');
/*!40000 ALTER TABLE `Jugador` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `PalabrasFrases`
--

DROP TABLE IF EXISTS `PalabrasFrases`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `PalabrasFrases` (
  `idPalabra` int NOT NULL AUTO_INCREMENT,
  `idFrase` int DEFAULT NULL,
  `categoria` varchar(50) DEFAULT NULL,
  `fechaCreacion` datetime DEFAULT NULL,
  `contenido` text,
  `significadoFrase` text,
  `significadoPalabra` text,
  PRIMARY KEY (`idPalabra`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `PalabrasFrases`
--

LOCK TABLES `PalabrasFrases` WRITE;
/*!40000 ALTER TABLE `PalabrasFrases` DISABLE KEYS */;
INSERT INTO `PalabrasFrases` VALUES (1,NULL,'Sustantivo','2025-04-01 09:00:00','Sol',NULL,'Estrella que ilumina la Tierra'),(2,1,'Frase','2025-04-02 12:00:00','El sol brilla','Descripción del clima',NULL);
/*!40000 ALTER TABLE `PalabrasFrases` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Partida`
--

DROP TABLE IF EXISTS `Partida`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Partida` (
  `idPartida` int NOT NULL AUTO_INCREMENT,
  `idJugador` int DEFAULT NULL,
  `idPalabra` int DEFAULT NULL,
  `resultado` varchar(50) DEFAULT NULL,
  `idJuego` int DEFAULT NULL,
  `nivelDificultad` int DEFAULT NULL,
  `fechaInicio` datetime DEFAULT NULL,
  `fechaFin` datetime DEFAULT NULL,
  `puntosObtenidos` int DEFAULT NULL,
  `partesAhorcado` int DEFAULT NULL,
  `tiempoTotal` int DEFAULT NULL,
  `modoEspecial` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`idPartida`),
  KEY `idPalabra` (`idPalabra`),
  KEY `idJuego` (`idJuego`),
  CONSTRAINT `Partida_ibfk_1` FOREIGN KEY (`idPalabra`) REFERENCES `PalabrasFrases` (`idPalabra`),
  CONSTRAINT `Partida_ibfk_2` FOREIGN KEY (`idJuego`) REFERENCES `Juego` (`idJuego`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Partida`
--

LOCK TABLES `Partida` WRITE;
/*!40000 ALTER TABLE `Partida` DISABLE KEYS */;
INSERT INTO `Partida` VALUES (1,NULL,1,'Ganado',1,3,'2025-04-03 10:00:00','2025-04-03 10:05:00',50,2,300,'Normal'),(2,NULL,2,'Perdido',2,2,'2025-04-04 11:00:00','2025-04-04 11:03:00',0,5,180,'Cronometrado');
/*!40000 ALTER TABLE `Partida` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Usuario`
--

DROP TABLE IF EXISTS `Usuario`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Usuario` (
  `idUsuario` int NOT NULL AUTO_INCREMENT,
  `nombreCompleto` varchar(100) DEFAULT NULL,
  `nombreUsuario` varchar(50) DEFAULT NULL,
  `correoElectronico` varchar(100) DEFAULT NULL,
  `contraseña` varchar(150) DEFAULT NULL,
  `fechaRegistro` datetime DEFAULT NULL,
  PRIMARY KEY (`idUsuario`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Usuario`
--

LOCK TABLES `Usuario` WRITE;
/*!40000 ALTER TABLE `Usuario` DISABLE KEYS */;
INSERT INTO `Usuario` VALUES (1,'Jesús Pérez','jesus123','jesus@example.com','pass123','2025-04-01 10:00:00'),(2,'María Gómez','maria_g','maria@example.com','secure456','2025-04-02 15:30:00');
/*!40000 ALTER TABLE `Usuario` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-05-28 18:51:53
