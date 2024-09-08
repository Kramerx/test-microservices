CREATE DATABASE `BDD_TRANSACTIONAL`

-- BDD_TRANSACTIONAL.tra_person definition

DROP TABLE IF EXISTS `tra_person`;
CREATE TABLE `tra_person` (
  `id_person` bigint NOT NULL AUTO_INCREMENT,
  `create_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `address` varchar(255) NOT NULL,
  `age` int NOT NULL,
  `gender` varchar(255) NOT NULL,
  `name` varchar(255) NOT NULL,
  `phone` varchar(255) NOT NULL,
  PRIMARY KEY (`id_person`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


-- BDD_TRANSACTIONAL.tra_client definition

DROP TABLE IF EXISTS `tra_client`;
CREATE TABLE `tra_client` (
  `id_client` bigint NOT NULL,
  `password` varchar(255) NOT NULL,
  `state_client` int NOT NULL,
  `id_person` bigint NOT NULL,
  PRIMARY KEY (`id_person`),
  CONSTRAINT `FK_TRA_CLIENT_TRA_PERSON` FOREIGN KEY (`id_person`) REFERENCES `tra_person` (`id_person`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- BDD_TRANSACTIONAL.tra_account definition

DROP TABLE IF EXISTS `tra_account`;
CREATE TABLE `tra_account` (
  `id_account` bigint NOT NULL AUTO_INCREMENT,
  `create_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `id_client` bigint NOT NULL,
  `initial_balance` decimal(38,2) NOT NULL,
  `number_account` bigint NOT NULL,
  `state_account` int NOT NULL,
  `type_account` varchar(255) NOT NULL,
  PRIMARY KEY (`id_account`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- BDD_TRANSACTIONAL.tra_movement definition

DROP TABLE IF EXISTS `tra_movement`;
CREATE TABLE `tra_movement` (
  `id_movement` bigint NOT NULL AUTO_INCREMENT,
  `create_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `amount` decimal(38,2) NOT NULL,
  `final_balance` decimal(38,2) NOT NULL,
  `type_movement` varchar(255) NOT NULL,
  `id_account` bigint DEFAULT NULL,
  PRIMARY KEY (`id_movement`),
  KEY `FK_TRA_MOVEMENT_TRA_ACCOUNT` (`id_account`),
  CONSTRAINT `FK_TRA_MOVEMENT_TRA_ACCOUNT` FOREIGN KEY (`id_account`) REFERENCES `tra_account` (`id_account`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;