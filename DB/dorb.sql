-- phpMyAdmin SQL Dump
-- version 4.7.4
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Gegenereerd op: 15 jun 2018 om 12:27
-- Serverversie: 10.1.29-MariaDB
-- PHP-versie: 7.2.0

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `dorb`
--

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `admins`
--

CREATE TABLE `admins` (
  `user_Id` int(11) NOT NULL,
  `voornaam` varchar(255) NOT NULL,
  `tussenvoegsel` varchar(255) NOT NULL,
  `achternaam` varchar(255) NOT NULL,
  `inlognaam` varchar(255) NOT NULL,
  `wachtwoord` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Gegevens worden geëxporteerd voor tabel `admins`
--

INSERT INTO `admins` (`user_Id`, `voornaam`, `tussenvoegsel`, `achternaam`, `inlognaam`, `wachtwoord`) VALUES
(130, 'Jack', 'van', 'Stan', 'stan', 'admin'),
(241, '', '', '', '', '');

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `chauffeurs`
--

CREATE TABLE `chauffeurs` (
  `id` int(11) NOT NULL,
  `user_Id` int(11) NOT NULL,
  `rijbewijs` enum('C','D') NOT NULL,
  `werkdagen` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Gegevens worden geëxporteerd voor tabel `chauffeurs`
--

INSERT INTO `chauffeurs` (`id`, `user_Id`, `rijbewijs`, `werkdagen`) VALUES
(79, 109, 'C', 3),
(80, 110, 'C', 5),
(81, 111, 'C', 3),
(82, 112, 'C', 5),
(83, 113, 'C', 3),
(84, 114, 'C', 2),
(85, 115, 'C', 1),
(86, 116, 'C', 2),
(87, 117, 'C', 5),
(88, 118, 'C', 1),
(89, 119, 'C', 4),
(90, 120, 'C', 5),
(91, 121, 'C', 4),
(92, 122, 'C', 2),
(93, 123, 'C', 2),
(94, 124, 'C', 2),
(95, 125, 'C', 3),
(96, 126, 'C', 5),
(97, 127, 'C', 2),
(98, 128, 'C', 2),
(99, 129, 'D', 4),
(100, 130, 'D', 3),
(101, 131, 'D', 1),
(102, 132, 'D', 5),
(103, 133, 'D', 4),
(104, 141, 'D', 6),
(105, 152, 'D', 7),
(107, 162, 'C', 5),
(111, 183, 'C', 5);

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `hibernate_sequence`
--

CREATE TABLE `hibernate_sequence` (
  `next_val` bigint(20) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Gegevens worden geëxporteerd voor tabel `hibernate_sequence`
--

INSERT INTO `hibernate_sequence` (`next_val`) VALUES
(5);

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `klanten`
--

CREATE TABLE `klanten` (
  `Id` int(11) NOT NULL,
  `user_Id` int(11) NOT NULL,
  `rekeningnummer` varchar(18) NOT NULL,
  `achternaam` varchar(255) DEFAULT NULL,
  `inlognaam` varchar(255) DEFAULT NULL,
  `tussenvoegsel` varchar(255) DEFAULT NULL,
  `voornaam` varchar(255) DEFAULT NULL,
  `wachtwoord` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Gegevens worden geëxporteerd voor tabel `klanten`
--

INSERT INTO `klanten` (`Id`, `user_Id`, `rekeningnummer`, `achternaam`, `inlognaam`, `tussenvoegsel`, `voornaam`, `wachtwoord`) VALUES
(111, 141, '123456789011', NULL, NULL, NULL, NULL, NULL),
(112, 143, 'utdjtrstwu65eki', NULL, NULL, NULL, NULL, NULL),
(113, 153, '6yygt77utfiufi76', NULL, NULL, NULL, NULL, NULL);

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `landen`
--

CREATE TABLE `landen` (
  `land_Id` int(11) NOT NULL,
  `land` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Gegevens worden geëxporteerd voor tabel `landen`
--

INSERT INTO `landen` (`land_Id`, `land`) VALUES
(5, 'Nederland'),
(6, 'Duitsland'),
(7, 'Belgie'),
(8, 'Luxemburg');

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `managers`
--

CREATE TABLE `managers` (
  `Id` int(11) NOT NULL,
  `user_Id` int(11) NOT NULL,
  `achternaam` varchar(255) DEFAULT NULL,
  `inlognaam` varchar(255) DEFAULT NULL,
  `tussenvoegsel` varchar(255) DEFAULT NULL,
  `voornaam` varchar(255) DEFAULT NULL,
  `wachtwoord` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Gegevens worden geëxporteerd voor tabel `managers`
--

INSERT INTO `managers` (`Id`, `user_Id`, `achternaam`, `inlognaam`, `tussenvoegsel`, `voornaam`, `wachtwoord`) VALUES
(39, 134, NULL, NULL, NULL, NULL, NULL),
(40, 135, NULL, NULL, NULL, NULL, NULL);

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `nationaliteit`
--

CREATE TABLE `nationaliteit` (
  `nat_Id` int(11) NOT NULL,
  `chauffeur_Id` int(11) NOT NULL,
  `land_Id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Gegevens worden geëxporteerd voor tabel `nationaliteit`
--

INSERT INTO `nationaliteit` (`nat_Id`, `chauffeur_Id`, `land_Id`) VALUES
(76, 109, 7),
(77, 109, 6),
(78, 109, 8),
(79, 109, 5),
(80, 110, 7),
(81, 110, 6),
(82, 110, 8),
(83, 110, 5),
(84, 111, 7),
(85, 111, 6),
(86, 111, 8),
(87, 111, 5),
(88, 112, 7),
(89, 112, 6),
(90, 112, 8),
(91, 112, 5),
(92, 113, 7),
(93, 113, 6),
(94, 113, 8),
(95, 113, 5),
(96, 114, 7),
(97, 114, 6),
(98, 114, 8),
(99, 115, 5),
(100, 116, 5),
(101, 117, 5),
(102, 118, 5),
(103, 119, 5),
(104, 120, 7),
(105, 120, 8),
(106, 120, 5),
(107, 121, 7),
(108, 121, 8),
(109, 121, 5),
(110, 122, 7),
(111, 122, 8),
(112, 122, 5),
(113, 123, 7),
(114, 123, 8),
(115, 124, 7),
(116, 124, 5),
(117, 125, 5),
(118, 126, 5),
(119, 126, 6),
(120, 127, 6),
(121, 127, 5),
(122, 128, 5),
(123, 128, 6),
(124, 129, 7),
(125, 129, 6),
(126, 129, 8),
(127, 129, 5),
(128, 130, 7),
(129, 130, 6),
(130, 130, 8),
(131, 130, 5),
(132, 131, 7),
(133, 131, 6),
(134, 131, 8),
(135, 131, 5),
(136, 132, 7),
(137, 132, 6),
(138, 132, 8),
(139, 132, 5),
(140, 133, 7),
(141, 133, 6),
(142, 133, 8),
(143, 133, 5),
(144, 125, 7),
(145, 110, 5),
(146, 110, 7),
(147, 110, 8),
(148, 110, 6),
(149, 183, 5),
(150, 183, 7),
(151, 183, 8),
(152, 183, 6);

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `orders`
--

CREATE TABLE `orders` (
  `order_Id` int(11) NOT NULL,
  `klant_Id` int(11) DEFAULT NULL,
  `adres` varchar(255) NOT NULL,
  `prijs` varchar(255) NOT NULL,
  `datum` date DEFAULT NULL,
  `rit_Id` int(11) DEFAULT NULL,
  `land_Id` int(11) DEFAULT NULL,
  `pallet_aantal` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Gegevens worden geëxporteerd voor tabel `orders`
--

INSERT INTO `orders` (`order_Id`, `klant_Id`, `adres`, `prijs`, `datum`, `rit_Id`, `land_Id`, `pallet_aantal`) VALUES
(14, 111, 'Ruisdaelstraat 36, 6717 TM Ede, Nederland', '7.1', '2018-04-28', 9, 6, 15),
(16, 111, 'Ruisdaelstraat 36, 6717 TM Ede, Nederland', '10.15', '2018-04-28', 9, 5, 1),
(17, 111, 'Ruisdaelstraat 36, 6717 TM Ede, Nederland', '10.15', '2018-04-28', 9, 5, 1),
(18, 111, 'Ruisdaelstraat 36, 6717 TM Ede, Nederland', '7.1', '2018-04-28', 9, 5, 6),
(19, 111, 'Ruisdaelstraat 36, 6717 TM Ede, Nederland', '7.1', '2018-04-28', 9, 5, 6),
(20, 111, 'Ruisdaelstraat 36, 6717 TM Ede, Nederland', '7.1', '2018-04-28', 10, 5, 10),
(21, 111, 'Ruisdaelstraat 36, 6717 TM Ede, Nederland', '9.13', '2018-04-28', 9, 5, 2),
(22, 111, 'Ruisdaelstraat 36, 6717 TM Ede, Nederland', '7.1', '2018-04-28', 9, 5, 6),
(23, 111, 'Ruisdaelstraat 36, 6717 TM Ede, Nederland', '7.1', '2018-04-25', 11, 5, 6),
(24, 111, 'Ruisdaelstraat 36, 6717 TM Ede, Nederland', '7.1', '2018-04-25', 11, 5, 14),
(25, 111, 'Ruisdaelstraat 36, 6717 TM Ede, Nederland', '10.15', '2018-04-25', 11, 5, 1),
(26, 111, 'Ruisdaelstraat 36, 6717 TM Ede, Nederland', '7.1', '2018-04-25', 12, 5, 10),
(27, 111, 'Ruisdaelstraat 36, 6717 TM Ede, Nederland', '7.1', '2018-04-25', 11, 5, 9),
(28, 111, 'Ruisdaelstraat 36, 6717 TM Ede, Nederland', '7.1', '2018-04-25', 12, 5, 9),
(29, 111, 'Ruisdaelstraat 36, 6717 TM Ede, Nederland', '7.1', '2018-04-25', 13, 5, 12),
(30, 111, 'Ruisdaelstraat 36, 6717 TM Ede, Nederland', '7.1', '2018-04-25', 14, 5, 20),
(31, 111, 'Ruisdaelstraat 36, 6717 TM Ede, Nederland', '7.1', '2018-04-25', 15, 5, 20),
(32, 111, 'Ruisdaelstraat 36, 6717 TM Ede, Nederland', '9.13', '2018-04-25', 12, 5, 2),
(33, 111, 'Ruisdaelstraat 36, 6717 TM Ede, Nederland', '7.1', '2018-05-19', 16, 5, 15),
(34, 111, 'Ruisdaelstraat 34, 6717 TM Ede, Nederland', '7.1', '2018-04-29', 17, 5, 20),
(35, 111, 'Ruisdaelstraat 36, 6717 TM Ede, Nederland', '7.1', '2019-04-20', 18, 5, 20),
(36, 111, 'Lützowpl. 17, 10785 Berlin, Duitsland', '230.1', '2019-04-25', 19, 6, 20),
(37, 111, 'Archipelstraat 79, 6524 LM Nijmegen, Nederland', '8.22', '2018-05-07', 20, 5, 6),
(38, 111, 'De Pas 44, 6836 BK Arnhem, Nederland', '2.91', '2018-05-07', 20, 5, 10),
(39, 111, 'Hessenweg 22, 3731 JK De Bilt, Nederland', '21.91', '2018-05-07', 20, 5, 6),
(40, 111, 'Diedenweg 10, 6717 KT Ede, Nederland', '7.49', '2018-05-07', 21, 5, 10);

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `pallets`
--

CREATE TABLE `pallets` (
  `pallet_Id` int(11) NOT NULL,
  `wat` varchar(255) NOT NULL,
  `order_Id` int(11) NOT NULL,
  `aantal` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Gegevens worden geëxporteerd voor tabel `pallets`
--

INSERT INTO `pallets` (`pallet_Id`, `wat`, `order_Id`, `aantal`) VALUES
(10, 'cola', 14, 0),
(12, 's', 16, 0),
(13, 's', 17, 20),
(14, 'e', 18, 0),
(15, 'e', 19, 0),
(16, 'e', 20, 0),
(17, 'e', 21, 0),
(18, 'e', 22, 0),
(19, 'e', 23, 0),
(20, 'e', 24, 0),
(21, 'e', 25, 0),
(22, 'e', 26, 0),
(23, 'e', 27, 0),
(24, 'e', 28, 0),
(25, 'e', 29, 0),
(26, 'e', 30, 0),
(27, 'e', 31, 0),
(28, 'e', 32, 0),
(29, 'cola', 33, 10),
(30, 'sinas', 33, 9),
(31, 'ijs', 34, 30),
(32, 'cola', 35, 8),
(33, 'ijs', 35, 12),
(34, 'ijs', 36, 20),
(35, 'volle schoenendozen', 37, 6),
(36, 'cola', 38, 10),
(37, 'horloge bandjes', 39, 6),
(38, 'sigarenbandjes', 40, 10);

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `planners`
--

CREATE TABLE `planners` (
  `Id` int(11) NOT NULL,
  `user_Id` int(11) NOT NULL,
  `achternaam` varchar(255) DEFAULT NULL,
  `inlognaam` varchar(255) DEFAULT NULL,
  `tussenvoegsel` varchar(255) DEFAULT NULL,
  `voornaam` varchar(255) DEFAULT NULL,
  `wachtwoord` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `prijzen`
--

CREATE TABLE `prijzen` (
  `prijs_Id` int(11) NOT NULL,
  `wat` varchar(255) NOT NULL,
  `prijs` varchar(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Gegevens worden geëxporteerd voor tabel `prijzen`
--

INSERT INTO `prijzen` (`prijs_Id`, `wat`, `prijs`) VALUES
(1, '1Pallet', '0.50'),
(2, '2Pallet', '0.45'),
(3, '3Pallet', '0.45'),
(4, '4Pallet', '0.40'),
(5, '5Pallet', '0.40'),
(6, '6Pallet', '0.35'),
(7, 'Nederland', '0.00'),
(8, 'Duitsland', '25.00'),
(9, 'Belgie', '15.00'),
(10, 'Luxemburg', '20.00');

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `rit`
--

CREATE TABLE `rit` (
  `rit_Id` int(11) NOT NULL,
  `ruimte` int(11) DEFAULT NULL,
  `datum` date DEFAULT NULL,
  `vrachtwagen_Id` int(11) DEFAULT NULL,
  `chauffeur_Id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Gegevens worden geëxporteerd voor tabel `rit`
--

INSERT INTO `rit` (`rit_Id`, `ruimte`, `datum`, `vrachtwagen_Id`, `chauffeur_Id`) VALUES
(9, 30, '2018-04-28', 45, 111),
(10, 10, '2018-04-28', 26, 79),
(11, 30, '2018-04-25', 43, 101),
(12, 21, '2018-04-25', 45, 99),
(13, 12, '2018-04-25', 37, 79),
(14, 20, '2018-04-25', 38, 79),
(15, 20, '2018-04-25', 39, 81),
(16, 15, '2018-05-19', 43, 99),
(17, 20, '2018-04-29', 41, 86),
(18, 20, '2019-04-20', 37, 79),
(19, 20, '2019-04-25', 37, 79),
(20, 22, '2018-05-07', 43, 99),
(21, 10, '2018-05-07', 26, 79);

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `rollen`
--

CREATE TABLE `rollen` (
  `id` int(11) NOT NULL,
  `user_Id` int(11) NOT NULL,
  `rol` enum('admin','chauffeur','klant','manager','planner') NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Gegevens worden geëxporteerd voor tabel `rollen`
--

INSERT INTO `rollen` (`id`, `user_Id`, `rol`) VALUES
(38, 109, 'chauffeur'),
(39, 110, 'chauffeur'),
(40, 111, 'chauffeur'),
(41, 112, 'chauffeur'),
(42, 113, 'chauffeur'),
(43, 114, 'chauffeur'),
(44, 115, 'chauffeur'),
(45, 116, 'chauffeur'),
(46, 117, 'chauffeur'),
(47, 118, 'chauffeur'),
(48, 119, 'chauffeur'),
(49, 120, 'chauffeur'),
(50, 121, 'chauffeur'),
(51, 122, 'chauffeur'),
(52, 123, 'chauffeur'),
(53, 124, 'chauffeur'),
(54, 125, 'chauffeur'),
(55, 126, 'chauffeur'),
(56, 127, 'chauffeur'),
(57, 128, 'chauffeur'),
(58, 129, 'chauffeur'),
(59, 130, 'chauffeur'),
(60, 131, 'chauffeur'),
(61, 132, 'chauffeur'),
(62, 133, 'chauffeur'),
(63, 134, 'manager'),
(64, 135, 'manager'),
(65, 136, 'planner'),
(66, 137, 'planner'),
(67, 138, 'planner'),
(68, 139, 'planner'),
(69, 140, 'planner'),
(71, 141, 'planner'),
(72, 141, 'chauffeur'),
(73, 141, 'manager'),
(74, 141, 'klant'),
(75, 143, 'klant'),
(76, 152, 'chauffeur'),
(77, 153, 'klant'),
(82, 162, 'chauffeur'),
(88, 168, 'planner'),
(97, 183, 'chauffeur');

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `typevrachtwagens`
--

CREATE TABLE `typevrachtwagens` (
  `typ_Id` int(11) NOT NULL,
  `type` varchar(255) NOT NULL,
  `ruimte` int(11) NOT NULL,
  `rijbewijs` enum('C','D') NOT NULL,
  `grootst` enum('true','false') NOT NULL,
  `volgorde` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Gegevens worden geëxporteerd voor tabel `typevrachtwagens`
--

INSERT INTO `typevrachtwagens` (`typ_Id`, `type`, `ruimte`, `rijbewijs`, `grootst`, `volgorde`) VALUES
(6, 'CF 300 FA', 10, 'C', 'false', 2),
(7, 'CF 410 FTN', 20, 'C', 'false', 3),
(8, 'XF 430 FTM', 30, 'D', 'true', 4);

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `users`
--

CREATE TABLE `users` (
  `user_Id` int(11) NOT NULL,
  `voornaam` varchar(255) NOT NULL,
  `tussenvoegsel` varchar(255) NOT NULL,
  `achternaam` varchar(255) NOT NULL,
  `inlognaam` varchar(255) NOT NULL,
  `wachtwoord` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Gegevens worden geëxporteerd voor tabel `users`
--

INSERT INTO `users` (`user_Id`, `voornaam`, `tussenvoegsel`, `achternaam`, `inlognaam`, `wachtwoord`) VALUES
(109, 'Ricky', 'de', 'Groene', 'r.groene', 'Q81m6565zb'),
(110, 'Kasper', '', 'Opdam', 'k.opdam', '0V69B7YF8i'),
(111, 'Sjoerd', '', 'Hendrix', 'S.hendrix', 'rLzng9S89g'),
(112, 'Luc', 'van', 'Engelen', 'l.engelen', 'R4D4z21pxn'),
(113, 'Youri', '', 'Spa', 'y.spa', '8db75hZh1i'),
(114, 'Jan', 'van de', 'Berg', 'j.berg', 'CFGc69o7Kq'),
(115, 'Joop', 'van', 'Ede', 'j.ede', '5O9rOt5n7P'),
(116, 'Bart', 'de', 'Jong', 'o.jong', 'VN84I3nu2m'),
(117, 'Susan', '', 'Havinga', 's.havinga', 'xU3YGYwe8D'),
(118, 'Laura', 'de', 'Groot', 'l.groot', 'hpDpq85NuZ'),
(119, 'Piet', 'de', 'Klerck', 'p.klerck', '7G6p3sHE7t'),
(120, 'Sjef', '', 'Smits', 's.smits', 'PM5hgO3i5s'),
(121, 'Rosa', '', 'Veenstra', 'r.veenstra', 'RQAdU5Rr34'),
(122, 'Henry', '', 'Burgers', 'h.burgers', 'o1U62Eiv84'),
(123, 'Cor', '', 'Vink', 'c.vink', '6SBS3IY8nP'),
(124, 'Tom', '', 'Schotten', 't.schotten', 'jAWc270F16'),
(125, 'Bas', 'van de', 'Brink', 'b.brink', 'GB0rqQG4x2'),
(126, 'Klaas', 'de', 'Boer', 'k.boer', '5pF5AC0IhZ'),
(127, 'Sofie', 'de', 'Vos', 's.vos', 'kuRH1321me'),
(128, 'Max', 'de', 'Koning', 'm.koning', 'c91u7K28ZA'),
(129, 'Piet', 'de', 'Leeuw', 'p.leeuw', 'JF6WO36kEa'),
(130, 'Bart', '', 'Vaessen', 'b.vaessen', 'kuJr8n5r6d'),
(131, 'Jan', '', 'Jansen', 'j.jansen', 'C038472XFi'),
(132, 'Henk', 'de', 'Rechter', 'h.rechter', 'bm699iJScZ'),
(133, 'Rik', '', 'Lamers', 'r.lamers', 'i7Pz5NjOe9'),
(134, 'Jacqueline', 'op den', 'Dorb', 'j.dorb', 'Arnhem1974'),
(135, 'Gerbert', 'op den', 'Dorb', 'g.dorb', 'Welkom01'),
(136, 'Trudy', '', 'Linssen', 't.linssen', '60v35MiX5'),
(137, 'Corine', 'ter', 'Dal', 'c.dal', 'e6TN07mCH'),
(138, 'Jaap', '', 'Gerritsen', 'j.gerritsen', 'uJ522Rmqo2'),
(139, 'Susan', '', 'Vlierma', 's.vlierma', 'JZ6uG13atj'),
(140, 'Jop', '', 'Spanjers', 'j.spanjers', 'I9Y9H9sU0'),
(141, 'Stan', 'van', 'Jack', 'stan', 'jack'),
(143, 'Klant', 'klant', 'klant', 'klant', 'klant'),
(152, 'min', 'van', 'jack', 'm.jack', 'w7rt82gyfkuyg'),
(153, 'tessa', 'van', 'jack', 't.jack', 'w7rt8erhyfkuyg'),
(162, 'vos', 'van', 'jack', 'v.jack', '842394g2fq'),
(168, 'Oliver Willam James', 'van der', 'Paus', 'owj.paus', 'wry363er'),
(183, 'Land', 'van', 'Jack', 'l.jack', 'f78429fi');

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `vrachtwagens`
--

CREATE TABLE `vrachtwagens` (
  `vrachtwagen_Id` int(11) NOT NULL,
  `typ_Id` int(11) DEFAULT NULL,
  `kenteken` varchar(255) NOT NULL,
  `apk` date NOT NULL,
  `status` enum('beschikbaar','onderhoud') NOT NULL DEFAULT 'beschikbaar'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Gegevens worden geëxporteerd voor tabel `vrachtwagens`
--

INSERT INTO `vrachtwagens` (`vrachtwagen_Id`, `typ_Id`, `kenteken`, `apk`, `status`) VALUES
(26, 6, 'BV-45-KS', '2018-07-01', 'beschikbaar'),
(27, 6, 'BX-85-JL', '2018-01-21', 'beschikbaar'),
(28, 6, 'BW-84-KL', '2017-12-01', 'beschikbaar'),
(29, 6, 'BA-14-GF', '2018-09-14', 'beschikbaar'),
(30, 6, 'BP-77-TX', '2018-08-04', 'beschikbaar'),
(31, 6, 'BZ-84-DR', '2018-06-08', 'beschikbaar'),
(32, 6, 'BHT-87-L', '2019-04-13', 'beschikbaar'),
(33, 6, 'BVT-96-R', '2018-06-08', 'beschikbaar'),
(34, 6, 'BPH-44-X', '2018-09-14', 'beschikbaar'),
(35, 6, 'BY-749-B', '2018-09-14', 'beschikbaar'),
(36, 6, 'BF-659-V', '2018-09-14', 'beschikbaar'),
(37, 7, 'BT-83-BP', '2018-09-14', 'beschikbaar'),
(38, 7, 'BT-86-GH', '2018-09-14', 'beschikbaar'),
(39, 7, 'BTT-58-J', '2018-10-23', 'beschikbaar'),
(40, 7, 'BYK-71-S', '2018-10-23', 'beschikbaar'),
(41, 7, 'BGP-56-Z', '2018-10-23', 'beschikbaar'),
(42, 7, 'BBD-10-D', '2018-04-29', 'beschikbaar'),
(43, 8, 'BZ-89-LW', '2018-10-23', 'beschikbaar'),
(44, 8, 'B-845-KH', '2018-12-11', 'beschikbaar'),
(45, 8, 'BWP-32-S', '2019-04-19', 'onderhoud');

--
-- Indexen voor geëxporteerde tabellen
--

--
-- Indexen voor tabel `admins`
--
ALTER TABLE `admins`
  ADD PRIMARY KEY (`user_Id`),
  ADD UNIQUE KEY `inlognaam` (`inlognaam`),
  ADD UNIQUE KEY `wachtwoord` (`wachtwoord`);

--
-- Indexen voor tabel `chauffeurs`
--
ALTER TABLE `chauffeurs`
  ADD PRIMARY KEY (`id`),
  ADD KEY `rijbewijs` (`rijbewijs`),
  ADD KEY `user_Id` (`user_Id`);

--
-- Indexen voor tabel `klanten`
--
ALTER TABLE `klanten`
  ADD PRIMARY KEY (`Id`),
  ADD UNIQUE KEY `user_Id_2` (`user_Id`),
  ADD KEY `user_Id` (`user_Id`);

--
-- Indexen voor tabel `landen`
--
ALTER TABLE `landen`
  ADD PRIMARY KEY (`land_Id`);

--
-- Indexen voor tabel `managers`
--
ALTER TABLE `managers`
  ADD PRIMARY KEY (`Id`),
  ADD UNIQUE KEY `user_Id_2` (`user_Id`),
  ADD KEY `user_Id` (`user_Id`);

--
-- Indexen voor tabel `nationaliteit`
--
ALTER TABLE `nationaliteit`
  ADD PRIMARY KEY (`nat_Id`),
  ADD KEY `chauffeur_Id` (`chauffeur_Id`),
  ADD KEY `land_Id` (`land_Id`);

--
-- Indexen voor tabel `orders`
--
ALTER TABLE `orders`
  ADD PRIMARY KEY (`order_Id`),
  ADD KEY `rit_Id` (`rit_Id`),
  ADD KEY `klant_Id` (`klant_Id`),
  ADD KEY `land_Id` (`land_Id`);

--
-- Indexen voor tabel `pallets`
--
ALTER TABLE `pallets`
  ADD PRIMARY KEY (`pallet_Id`),
  ADD KEY `order_Id` (`order_Id`);

--
-- Indexen voor tabel `planners`
--
ALTER TABLE `planners`
  ADD PRIMARY KEY (`Id`),
  ADD UNIQUE KEY `user_Id` (`user_Id`),
  ADD KEY `user_Id_2` (`user_Id`);

--
-- Indexen voor tabel `prijzen`
--
ALTER TABLE `prijzen`
  ADD PRIMARY KEY (`prijs_Id`);

--
-- Indexen voor tabel `rit`
--
ALTER TABLE `rit`
  ADD PRIMARY KEY (`rit_Id`),
  ADD KEY `vrachtwagen_Id` (`vrachtwagen_Id`,`chauffeur_Id`),
  ADD KEY `chauffeur_Id` (`chauffeur_Id`);

--
-- Indexen voor tabel `rollen`
--
ALTER TABLE `rollen`
  ADD PRIMARY KEY (`id`),
  ADD KEY `user_Id` (`user_Id`);

--
-- Indexen voor tabel `typevrachtwagens`
--
ALTER TABLE `typevrachtwagens`
  ADD PRIMARY KEY (`typ_Id`),
  ADD UNIQUE KEY `volgorde` (`volgorde`);

--
-- Indexen voor tabel `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`user_Id`),
  ADD UNIQUE KEY `inlognaam` (`inlognaam`),
  ADD UNIQUE KEY `wachtwoord` (`wachtwoord`);

--
-- Indexen voor tabel `vrachtwagens`
--
ALTER TABLE `vrachtwagens`
  ADD PRIMARY KEY (`vrachtwagen_Id`),
  ADD KEY `typ_Id` (`typ_Id`);

--
-- AUTO_INCREMENT voor geëxporteerde tabellen
--

--
-- AUTO_INCREMENT voor een tabel `admins`
--
ALTER TABLE `admins`
  MODIFY `user_Id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=242;

--
-- AUTO_INCREMENT voor een tabel `chauffeurs`
--
ALTER TABLE `chauffeurs`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=112;

--
-- AUTO_INCREMENT voor een tabel `klanten`
--
ALTER TABLE `klanten`
  MODIFY `Id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=114;

--
-- AUTO_INCREMENT voor een tabel `landen`
--
ALTER TABLE `landen`
  MODIFY `land_Id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- AUTO_INCREMENT voor een tabel `managers`
--
ALTER TABLE `managers`
  MODIFY `Id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=41;

--
-- AUTO_INCREMENT voor een tabel `nationaliteit`
--
ALTER TABLE `nationaliteit`
  MODIFY `nat_Id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=153;

--
-- AUTO_INCREMENT voor een tabel `orders`
--
ALTER TABLE `orders`
  MODIFY `order_Id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=41;

--
-- AUTO_INCREMENT voor een tabel `pallets`
--
ALTER TABLE `pallets`
  MODIFY `pallet_Id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=39;

--
-- AUTO_INCREMENT voor een tabel `planners`
--
ALTER TABLE `planners`
  MODIFY `Id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT voor een tabel `prijzen`
--
ALTER TABLE `prijzen`
  MODIFY `prijs_Id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT voor een tabel `rit`
--
ALTER TABLE `rit`
  MODIFY `rit_Id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=22;

--
-- AUTO_INCREMENT voor een tabel `rollen`
--
ALTER TABLE `rollen`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=98;

--
-- AUTO_INCREMENT voor een tabel `typevrachtwagens`
--
ALTER TABLE `typevrachtwagens`
  MODIFY `typ_Id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- AUTO_INCREMENT voor een tabel `users`
--
ALTER TABLE `users`
  MODIFY `user_Id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=184;

--
-- AUTO_INCREMENT voor een tabel `vrachtwagens`
--
ALTER TABLE `vrachtwagens`
  MODIFY `vrachtwagen_Id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=46;

--
-- Beperkingen voor geëxporteerde tabellen
--

--
-- Beperkingen voor tabel `chauffeurs`
--
ALTER TABLE `chauffeurs`
  ADD CONSTRAINT `chauffeurs_ibfk_1` FOREIGN KEY (`user_Id`) REFERENCES `users` (`user_Id`);

--
-- Beperkingen voor tabel `klanten`
--
ALTER TABLE `klanten`
  ADD CONSTRAINT `klanten_ibfk_1` FOREIGN KEY (`user_Id`) REFERENCES `users` (`user_Id`);

--
-- Beperkingen voor tabel `nationaliteit`
--
ALTER TABLE `nationaliteit`
  ADD CONSTRAINT `nationaliteit_ibfk_2` FOREIGN KEY (`land_Id`) REFERENCES `landen` (`land_Id`),
  ADD CONSTRAINT `nationaliteit_ibfk_3` FOREIGN KEY (`chauffeur_Id`) REFERENCES `users` (`user_Id`);

--
-- Beperkingen voor tabel `orders`
--
ALTER TABLE `orders`
  ADD CONSTRAINT `orders_ibfk_1` FOREIGN KEY (`rit_Id`) REFERENCES `rit` (`rit_Id`),
  ADD CONSTRAINT `orders_ibfk_2` FOREIGN KEY (`klant_Id`) REFERENCES `klanten` (`Id`),
  ADD CONSTRAINT `orders_ibfk_4` FOREIGN KEY (`land_Id`) REFERENCES `landen` (`land_Id`);

--
-- Beperkingen voor tabel `pallets`
--
ALTER TABLE `pallets`
  ADD CONSTRAINT `pallets_ibfk_1` FOREIGN KEY (`order_Id`) REFERENCES `orders` (`order_Id`);

--
-- Beperkingen voor tabel `rit`
--
ALTER TABLE `rit`
  ADD CONSTRAINT `rit_ibfk_1` FOREIGN KEY (`chauffeur_Id`) REFERENCES `chauffeurs` (`id`),
  ADD CONSTRAINT `rit_ibfk_2` FOREIGN KEY (`vrachtwagen_Id`) REFERENCES `vrachtwagens` (`vrachtwagen_Id`);

--
-- Beperkingen voor tabel `rollen`
--
ALTER TABLE `rollen`
  ADD CONSTRAINT `rollen_ibfk_1` FOREIGN KEY (`user_Id`) REFERENCES `users` (`user_Id`);

--
-- Beperkingen voor tabel `vrachtwagens`
--
ALTER TABLE `vrachtwagens`
  ADD CONSTRAINT `vrachtwagens_ibfk_1` FOREIGN KEY (`typ_Id`) REFERENCES `typevrachtwagens` (`typ_Id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
