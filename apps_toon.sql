-- phpMyAdmin SQL Dump
-- version 4.8.5
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jun 18, 2019 at 10:25 AM
-- Server version: 10.1.40-MariaDB
-- PHP Version: 7.2.18

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `apps_toon`
--

-- --------------------------------------------------------

--
-- Table structure for table `cart`
--

CREATE TABLE `cart` (
  `UserID` text NOT NULL,
  `ProductID` text NOT NULL,
  `Qty` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `detailtransaction`
--

CREATE TABLE `detailtransaction` (
  `TransactionID` text NOT NULL,
  `ProductID` text NOT NULL,
  `Qty` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `detailtransaction`
--

INSERT INTO `detailtransaction` (`TransactionID`, `ProductID`, `Qty`) VALUES
('TR001', 'PD002', 3),
('TR001', 'PD012', 2),
('TR002', 'PD008', 3),
('TR002', 'PD010', 2),
('TR003', 'PD003', 1),
('TR003', 'PD005', 1);

-- --------------------------------------------------------

--
-- Table structure for table `genre`
--

CREATE TABLE `genre` (
  `GenreID` text NOT NULL,
  `GenreName` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `genre`
--

INSERT INTO `genre` (`GenreID`, `GenreName`) VALUES
('GR001', 'Romance'),
('GR002', 'Drama'),
('GR003', 'Fantasy'),
('GR004', 'Comedy'),
('GR005', 'Action'),
('GR006', 'Horror'),
('GR007', 'Slice of Life');

-- --------------------------------------------------------

--
-- Table structure for table `headertransaction`
--

CREATE TABLE `headertransaction` (
  `TransactionID` text NOT NULL,
  `UserID` text NOT NULL,
  `TransactionDate` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `headertransaction`
--

INSERT INTO `headertransaction` (`TransactionID`, `UserID`, `TransactionDate`) VALUES
('TR001', 'US005', '2019-06-18'),
('TR002', 'US006', '2019-06-18'),
('TR003', 'US006', '2019-06-18');

-- --------------------------------------------------------

--
-- Table structure for table `product`
--

CREATE TABLE `product` (
  `ProductID` text NOT NULL,
  `GenreID` text NOT NULL,
  `ProductName` text NOT NULL,
  `ProductPrice` int(11) NOT NULL,
  `ProductQuantity` int(11) NOT NULL,
  `ProductImage` text NOT NULL,
  `ProductRating` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `product`
--

INSERT INTO `product` (`ProductID`, `GenreID`, `ProductName`, `ProductPrice`, `ProductQuantity`, `ProductImage`, `ProductRating`) VALUES
('PD001', 'GR001', 'Flawless', 12000, 16, 'Flawless.jpg', 8),
('PD002', 'GR002', 'LOOKISM', 8000, 9, 'LOOKISM.jpg', 10),
('PD003', 'GR004', 'Tahilalats', 11000, 13, 'Tahilalats.jpg', 9),
('PD004', 'GR002', 'The Secret of Angel', 9000, 16, 'TSeoA.jpg', 9),
('PD005', 'GR006', 'Gloomy Sunday', 7000, 5, 'GloomySunday.jpg', 9),
('PD006', 'GR003', 'Switched Girls', 9000, 11, 'SwitchedGirls.jpg', 9),
('PD007', 'GR001', 'Ecstasy Hearts', 11000, 9, 'EcstasyHearts.jpg', 8),
('PD008', 'GR004', 'Terlalu Tampan', 16000, 7, 'TerlaluTampan.jpg', 8),
('PD009', 'GR007', 'Slice of Life', 15000, 4, 'Kurma.jpg', 6),
('PD010', 'GR005', 'Dice', 18000, 5, 'Dice.jpg', 7),
('PD011', 'GR002', 'In a Dream', 17000, 4, 'InADream.jpg', 5),
('PD012', 'GR001', 'Honey Honey Wedding', 17000, 15, 'HHW.jpeg', 7);

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

CREATE TABLE `user` (
  `UserID` text NOT NULL,
  `UserName` text NOT NULL,
  `UserEmail` text NOT NULL,
  `UserPassword` text NOT NULL,
  `UserGender` text NOT NULL,
  `UserDOB` date NOT NULL,
  `UserPhoneNumber` text NOT NULL,
  `UserAddress` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`UserID`, `UserName`, `UserEmail`, `UserPassword`, `UserGender`, `UserDOB`, `UserPhoneNumber`, `UserAddress`) VALUES
('US001', 'devia', 'devialiany17@gmail.com', 'ikanlele12345', 'Female', '2000-01-17', '081297177655', 'Lembang Street'),
('US002', 'napoleon', 'napoleonwinston7@gmail.com', 'asdfghjkl123', 'Male', '2000-06-14', '085361132604', 'Jalur Sutera Barat Street'),
('US003', 'kirana', 'kiranaauliar@gmail.com', 'kirana12345', 'Female', '2000-01-01', '087839077253', 'Cendrawasih Street'),
('US004', 'kevin', 'kevinlin@hotmail.com', 'kepin12345678901', 'Male', '2000-04-21', '092114289281', 'pantai indah kapuk Street'),
('US005', 'keiji', 'kejibanget@tmail.com', 'qwertyuiop12', 'Male', '2000-01-02', '081221345267', 'simatupang Street'),
('US006', 'monica', 'mnctv@coldmail.com', 'asdfghjkl12', 'Female', '2001-06-10', '089764897601', 'kalimulung Street');
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
