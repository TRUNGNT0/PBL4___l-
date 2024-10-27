-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Máy chủ: 127.0.0.1
-- Thời gian đã tạo: Th10 18, 2024 lúc 05:50 AM
-- Phiên bản máy phục vụ: 10.4.32-MariaDB
-- Phiên bản PHP: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Cơ sở dữ liệu: `table1`
--

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `bang1`
--

CREATE TABLE `bang1` (
  `maso` varchar(5) NOT NULL,
  `hoten` varchar(15) NOT NULL,
  `ngaysinh` date NOT NULL,
  `nghenghiep` varchar(5) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_vietnamese_ci;

--
-- Đang đổ dữ liệu cho bảng `bang1`
--

INSERT INTO `bang1` (`maso`, `hoten`, `ngaysinh`, `nghenghiep`) VALUES
('nv01', 'le A', '2004-10-05', 'sv'),
('nv02', 'tran b', '2004-10-02', 'sv'),
('nv03', 'nguyen p', '2014-10-01', 'ks'),
('nv04', 'mac c', '2024-10-10', 'ks');

--
-- Chỉ mục cho các bảng đã đổ
--

--
-- Chỉ mục cho bảng `bang1`
--
ALTER TABLE `bang1`
  ADD PRIMARY KEY (`maso`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
