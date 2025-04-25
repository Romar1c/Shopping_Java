-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Hôte : 127.0.0.1:3306
-- Généré le : ven. 25 avr. 2025 à 16:17
-- Version du serveur : 8.0.41
-- Version de PHP : 8.2.13

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données : `shopping_db`
--

-- --------------------------------------------------------

--
-- Structure de la table `articles`
--

DROP TABLE IF EXISTS `articles`;
CREATE TABLE IF NOT EXISTS `articles` (
  `id` int NOT NULL AUTO_INCREMENT,
  `nom` varchar(100) NOT NULL,
  `marque` varchar(100) NOT NULL,
  `prix_unitaire` double NOT NULL,
  `prix_vrac` double NOT NULL,
  `quantite_vrac` int NOT NULL,
  `stock` int DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Déchargement des données de la table `articles`
--

INSERT INTO `articles` (`id`, `nom`, `marque`, `prix_unitaire`, `prix_vrac`, `quantite_vrac`, `stock`) VALUES
(11, 'Jean', 'Levis', 80, 45, 10, 90),
(12, 'Jean', 'Zara', 45, 40, 10, 100),
(13, 'T-shirt', 'Zara', 15, 12, 10, 95),
(14, 'T-Shirt', 'Adidas', 10, 8, 20, 100),
(15, 'Basket', 'Adidas', 120, 100, 15, 100),
(16, 'Basket', 'Nike', 110, 99.99, 20, 80),
(17, 'Sweat', 'Tommy', 100, 95, 10, 100),
(18, 'Sweat', 'Pull&Bear', 40, 35, 25, 75),
(19, 'Robe', 'Zara', 80, 70, 15, 85),
(20, 'Robe', 'Uniqlo', 60, 55, 10, 95);

-- --------------------------------------------------------

--
-- Structure de la table `clients`
--

DROP TABLE IF EXISTS `clients`;
CREATE TABLE IF NOT EXISTS `clients` (
  `id` int NOT NULL AUTO_INCREMENT,
  `nom` varchar(50) DEFAULT NULL,
  `email` varchar(100) DEFAULT NULL,
  `mot_de_passe` varchar(255) DEFAULT NULL,
  `admin` int DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Déchargement des données de la table `clients`
--

INSERT INTO `clients` (`id`, `nom`, `email`, `mot_de_passe`, `admin`) VALUES
(1, 'admin', 'admin@admin.com', '01234', 1),
(18, 'Romaric', 'romaric@gmail.com', '01234', 0),
(19, 'Miguel', 'Miguel@gmail.com', '01234', 0),
(20, 'Talia', 'Talia@gmail.com', '01234', 0),
(21, 'Julia', 'Julia@gmail.com', '01234', 0);

-- --------------------------------------------------------

--
-- Structure de la table `commandes`
--

DROP TABLE IF EXISTS `commandes`;
CREATE TABLE IF NOT EXISTS `commandes` (
  `id` int NOT NULL AUTO_INCREMENT,
  `client_id` int NOT NULL,
  `date_commande` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `total` double NOT NULL,
  PRIMARY KEY (`id`),
  KEY `client_id` (`client_id`)
) ENGINE=InnoDB AUTO_INCREMENT=89 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Déchargement des données de la table `commandes`
--

INSERT INTO `commandes` (`id`, `client_id`, `date_commande`, `total`) VALUES
(85, 18, '2025-04-25 16:11:21', 1425),
(86, 18, '2025-04-25 16:11:52', 1999.8),
(87, 19, '2025-04-25 16:16:35', 875),
(88, 19, '2025-04-25 16:16:53', 450);

-- --------------------------------------------------------

--
-- Structure de la table `commande_articles`
--

DROP TABLE IF EXISTS `commande_articles`;
CREATE TABLE IF NOT EXISTS `commande_articles` (
  `id` int NOT NULL AUTO_INCREMENT,
  `commande_id` int NOT NULL,
  `article_id` int NOT NULL,
  `quantite` int NOT NULL,
  `prix_total` double NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `commande_id` (`commande_id`),
  KEY `article_id` (`article_id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Déchargement des données de la table `commande_articles`
--

INSERT INTO `commande_articles` (`id`, `commande_id`, `article_id`, `quantite`, `prix_total`) VALUES
(1, 85, 13, 5, 75),
(2, 85, 20, 5, 300),
(3, 85, 19, 15, 1050),
(4, 86, 16, 20, 1999.8),
(5, 87, 18, 25, 875),
(6, 88, 11, 10, 450);

--
-- Contraintes pour les tables déchargées
--

--
-- Contraintes pour la table `commandes`
--
ALTER TABLE `commandes`
  ADD CONSTRAINT `commandes_ibfk_1` FOREIGN KEY (`client_id`) REFERENCES `clients` (`id`) ON DELETE CASCADE;

--
-- Contraintes pour la table `commande_articles`
--
ALTER TABLE `commande_articles`
  ADD CONSTRAINT `commande_articles_ibfk_1` FOREIGN KEY (`commande_id`) REFERENCES `commandes` (`id`) ON DELETE CASCADE,
  ADD CONSTRAINT `commande_articles_ibfk_2` FOREIGN KEY (`article_id`) REFERENCES `articles` (`id`) ON DELETE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
