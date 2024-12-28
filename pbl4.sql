-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Máy chủ: 127.0.0.1
-- Thời gian đã tạo: Th12 28, 2024 lúc 06:04 PM
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
-- Cơ sở dữ liệu: `pbl4`
--

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `account`
--

CREATE TABLE `account` (
  `user` varchar(50) NOT NULL,
  `password` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `account`
--

INSERT INTO `account` (`user`, `password`) VALUES
('Sum', '123qwe!@#'),
('Sum2', '123456'),
('TestSignUp', '123123'),
('Trung', '123456');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `file`
--

CREATE TABLE `file` (
  `id` int(11) NOT NULL,
  `uuid` varchar(255) NOT NULL,
  `filename` varchar(255) NOT NULL,
  `path` varchar(255) NOT NULL,
  `status` enum('wait','process','done') DEFAULT 'wait',
  `created_at` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `file`
--

INSERT INTO `file` (`id`, `uuid`, `filename`, `path`, `status`, `created_at`) VALUES
(8, 'bf7c9f5a-98ba-4c09-9c2f-08b2c71f07a0', '1337UP - Web - Cat_Club.pdf', 'C:\\Users\\ASUS\\eclipse-workspace\\.metadata\\.plugins\\org.eclipse.wst.server.core\\tmp0\\wtpwebapps\\T10\\uploads\\bf7c9f5a-98ba-4c09-9c2f-08b2c71f07a0.docx', 'done', '2024-11-21 04:44:58'),
(9, '34ae1b09-a26b-4043-a2db-b957d7dec0e6', '1337UP - Web - Biocorp.pdf', 'C:\\Users\\ASUS\\eclipse-workspace\\.metadata\\.plugins\\org.eclipse.wst.server.core\\tmp0\\wtpwebapps\\T10\\uploads\\34ae1b09-a26b-4043-a2db-b957d7dec0e6.docx', 'done', '2024-11-22 04:21:49'),
(10, 'd7e23136-4f5d-4940-b1b2-650d84aaed5b', '1337UP - Web - Cat_Club.pdf', 'C:\\Users\\ASUS\\eclipse-workspace\\.metadata\\.plugins\\org.eclipse.wst.server.core\\tmp0\\wtpwebapps\\T10\\uploads\\d7e23136-4f5d-4940-b1b2-650d84aaed5b.docx', 'done', '2024-11-22 04:22:01'),
(11, '58ccbbd6-3817-4907-bc5f-6524c5834584', '1337UP - Web - Biocorp.pdf', 'C:\\Users\\ASUS\\eclipse-workspace\\.metadata\\.plugins\\org.eclipse.wst.server.core\\tmp0\\wtpwebapps\\T10\\uploads\\58ccbbd6-3817-4907-bc5f-6524c5834584.docx', 'done', '2024-11-22 04:22:13'),
(12, '07c12516-a857-4a9b-91ad-c961e8dbf500', '1337UP - Web - Biocorp.pdf', 'C:\\Users\\ASUS\\eclipse-workspace\\.metadata\\.plugins\\org.eclipse.wst.server.core\\tmp0\\wtpwebapps\\T10\\uploads\\07c12516-a857-4a9b-91ad-c961e8dbf500.docx', 'done', '2024-11-22 04:30:59'),
(13, '94990abb-4410-4687-aec1-9d1ac2ca1d29', '1337UP - Web - Cat_Club.pdf', 'C:\\Users\\ASUS\\eclipse-workspace\\.metadata\\.plugins\\org.eclipse.wst.server.core\\tmp0\\wtpwebapps\\T10\\uploads\\94990abb-4410-4687-aec1-9d1ac2ca1d29.docx', 'done', '2024-11-22 04:31:07'),
(14, '499bdfe8-aeaa-4cd1-8c78-adc59b032238', '1337UP - Web - Biocorp.pdf', 'C:\\Users\\ASUS\\eclipse-workspace\\.metadata\\.plugins\\org.eclipse.wst.server.core\\tmp0\\wtpwebapps\\T10\\uploads\\499bdfe8-aeaa-4cd1-8c78-adc59b032238.docx', 'done', '2024-11-22 04:31:23'),
(15, '7f704e9c-7f45-4788-85cc-45f1c34c901f', '1337UP - Web - Biocorp.pdf', 'C:\\Users\\ASUS\\eclipse-workspace\\.metadata\\.plugins\\org.eclipse.wst.server.core\\tmp0\\wtpwebapps\\T10\\uploads\\7f704e9c-7f45-4788-85cc-45f1c34c901f.docx', 'done', '2024-11-22 04:35:07'),
(16, 'a076ee48-513d-443f-8922-5220504074b4', '1337UP - Web - Biocorp.pdf', 'C:\\Users\\ASUS\\eclipse-workspace\\.metadata\\.plugins\\org.eclipse.wst.server.core\\tmp0\\wtpwebapps\\T10\\uploads\\a076ee48-513d-443f-8922-5220504074b4.docx', 'done', '2024-11-22 04:35:19'),
(17, '07f0be70-b3c0-4790-9497-f3bf19340e3c', '1337UP - Web - Biocorp.pdf', 'C:\\Users\\ASUS\\eclipse-workspace\\.metadata\\.plugins\\org.eclipse.wst.server.core\\tmp0\\wtpwebapps\\T10\\uploads\\07f0be70-b3c0-4790-9497-f3bf19340e3c.docx', 'done', '2024-11-22 04:35:44'),
(18, 'e00403cb-75a0-426a-b47c-49f3c0495e62', 'ACL Samples (1).pdf', 'C:\\Users\\ASUS\\eclipse-workspace\\.metadata\\.plugins\\org.eclipse.wst.server.core\\tmp0\\wtpwebapps\\T10\\uploads\\e00403cb-75a0-426a-b47c-49f3c0495e62.docx', 'done', '2024-11-22 04:36:17'),
(19, '9a69ebf9-a619-4cf4-b58c-426ed6fe2a90', '1337UP - Web - Biocorp.pdf', 'C:\\Users\\ASUS\\eclipse-workspace\\.metadata\\.plugins\\org.eclipse.wst.server.core\\tmp0\\wtpwebapps\\T10\\uploads\\9a69ebf9-a619-4cf4-b58c-426ed6fe2a90.docx', 'done', '2024-11-22 04:37:06'),
(20, '4809811e-f7aa-41fe-83f5-9d242e0d07bb', '1337UP - Web - Biocorp.pdf', 'C:\\Users\\ASUS\\eclipse-workspace\\.metadata\\.plugins\\org.eclipse.wst.server.core\\tmp0\\wtpwebapps\\T10\\uploads\\4809811e-f7aa-41fe-83f5-9d242e0d07bb.docx', 'done', '2024-11-22 04:37:19'),
(21, 'a0c261e8-6e6e-476b-bfc7-f8f780f1be47', 'Thong bao thu HP HK 1 nam 24-25 lan 3.pdf2_0001.pdf', 'C:\\Users\\ASUS\\eclipse-workspace\\.metadata\\.plugins\\org.eclipse.wst.server.core\\tmp0\\wtpwebapps\\T10\\uploads\\a0c261e8-6e6e-476b-bfc7-f8f780f1be47.docx', 'done', '2024-11-22 16:33:51'),
(22, '4edfcba9-424b-48ea-aa25-803bc5182b91', '1337UP - Web - Cat_Club.pdf', 'C:\\Users\\ASUS\\eclipse-workspace\\.metadata\\.plugins\\org.eclipse.wst.server.core\\tmp0\\wtpwebapps\\T10\\uploads\\4edfcba9-424b-48ea-aa25-803bc5182b91.docx', 'done', '2024-11-22 16:45:58'),
(23, '2920f9e8-6245-4ad5-8e18-afdae111712c', 'ACL Samples (1).pdf', 'C:\\Users\\ASUS\\eclipse-workspace\\.metadata\\.plugins\\org.eclipse.wst.server.core\\tmp0\\wtpwebapps\\T10\\uploads\\2920f9e8-6245-4ad5-8e18-afdae111712c.docx', 'done', '2024-11-22 16:46:02'),
(24, 'a65aef7d-fada-4065-8d01-e1c98bae2435', '1337UP - Web - Biocorp.pdf', 'C:\\Users\\ASUS\\eclipse-workspace\\.metadata\\.plugins\\org.eclipse.wst.server.core\\tmp0\\wtpwebapps\\T10\\uploads\\a65aef7d-fada-4065-8d01-e1c98bae2435.docx', 'done', '2024-11-22 16:46:14'),
(25, '340f97fe-03ed-472d-bf9b-df61eb005014', 'ACL Samples (1).pdf', 'C:\\Users\\ASUS\\eclipse-workspace\\.metadata\\.plugins\\org.eclipse.wst.server.core\\tmp0\\wtpwebapps\\T10\\uploads\\340f97fe-03ed-472d-bf9b-df61eb005014.docx', 'done', '2024-11-22 16:46:23'),
(26, 'a444a7cf-8bcf-4aba-898c-fb48f42bf7e4', '1337UP - Web - Cat_Club.pdf', 'C:\\Users\\ASUS\\eclipse-workspace\\.metadata\\.plugins\\org.eclipse.wst.server.core\\tmp0\\wtpwebapps\\T10\\uploads\\a444a7cf-8bcf-4aba-898c-fb48f42bf7e4.docx', 'done', '2024-11-22 16:46:53'),
(27, '8a6b418f-8302-4086-bf11-b7f68e328fad', 'Mô Hinh MVC-PHP.pdf', 'C:\\Users\\ASUS\\eclipse-workspace\\.metadata\\.plugins\\org.eclipse.wst.server.core\\tmp0\\wtpwebapps\\T10\\uploads\\8a6b418f-8302-4086-bf11-b7f68e328fad.docx', 'done', '2024-11-22 16:47:24'),
(28, 'c808caba-03e3-424b-b0db-ed5becc615f8', 'DeThi_01.pdf', 'C:\\Users\\ASUS\\eclipse-workspace\\.metadata\\.plugins\\org.eclipse.wst.server.core\\tmp0\\wtpwebapps\\T10\\uploads\\c808caba-03e3-424b-b0db-ed5becc615f8.docx', 'done', '2024-11-22 16:55:50'),
(29, '930b18a9-d619-4ed1-bb9f-45de1f1531c5', 'ACL Samples (1).pdf', 'C:\\Users\\ASUS\\eclipse-workspace\\.metadata\\.plugins\\org.eclipse.wst.server.core\\tmp0\\wtpwebapps\\T10\\uploads\\930b18a9-d619-4ed1-bb9f-45de1f1531c5.docx', 'done', '2024-11-22 16:58:17'),
(30, '8ae114f9-b913-4bbb-9d3d-b8545afb52e3', '1337UP - Web - Cat_Club.pdf', 'C:\\Users\\ASUS\\eclipse-workspace\\.metadata\\.plugins\\org.eclipse.wst.server.core\\tmp0\\wtpwebapps\\T10\\uploads\\8ae114f9-b913-4bbb-9d3d-b8545afb52e3.docx', 'done', '2024-11-22 16:59:49'),
(31, '408b6f5c-27b5-4ac4-8c0d-350b9f5ab0e3', 'DeThi_01.pdf', 'C:\\Users\\ASUS\\eclipse-workspace\\.metadata\\.plugins\\org.eclipse.wst.server.core\\tmp0\\wtpwebapps\\T10\\uploads\\408b6f5c-27b5-4ac4-8c0d-350b9f5ab0e3.docx', 'done', '2024-11-22 17:00:50'),
(32, 'af9daaca-94bf-413c-ae45-7342dbb04786', '1337UP - Web - Cat_Club.pdf', 'C:\\Users\\ASUS\\eclipse-workspace\\.metadata\\.plugins\\org.eclipse.wst.server.core\\tmp0\\wtpwebapps\\T10\\uploads\\af9daaca-94bf-413c-ae45-7342dbb04786.docx', 'done', '2024-11-22 17:07:07'),
(33, 'dfb48209-77ef-4367-8268-229997729f0f', '1337UP - Web - Cat_Club.pdf', 'C:\\Users\\ASUS\\eclipse-workspace\\.metadata\\.plugins\\org.eclipse.wst.server.core\\tmp0\\wtpwebapps\\T10\\uploads\\dfb48209-77ef-4367-8268-229997729f0f.docx', 'done', '2024-11-22 17:08:25'),
(34, '6132496f-34e0-4a3a-8409-a5e738605669', 'Thong bao thu HP HK 1 nam 24-25 lan 3.pdf2_0001.pdf', 'C:\\Users\\ASUS\\eclipse-workspace\\.metadata\\.plugins\\org.eclipse.wst.server.core\\tmp0\\wtpwebapps\\T10\\uploads\\6132496f-34e0-4a3a-8409-a5e738605669.docx', 'done', '2024-11-22 17:13:14'),
(35, '7a3fe891-c997-4e04-b696-aaa058f5ccb9', '1337UP - Web - Biocorp.pdf', 'C:\\Users\\ASUS\\eclipse-workspace\\.metadata\\.plugins\\org.eclipse.wst.server.core\\tmp0\\wtpwebapps\\T10\\uploads\\7a3fe891-c997-4e04-b696-aaa058f5ccb9.docx', 'done', '2024-11-22 17:15:59'),
(36, 'd832a2e5-46df-4303-b368-f191fe0708bb', '1337UP - Web - Biocorp.pdf', 'C:\\Users\\ASUS\\eclipse-workspace\\.metadata\\.plugins\\org.eclipse.wst.server.core\\tmp0\\wtpwebapps\\T10\\uploads\\d832a2e5-46df-4303-b368-f191fe0708bb.docx', 'done', '2024-11-22 17:21:17'),
(37, '35eff126-b87d-4519-9480-54c060b67ea8', '1337UP - Web - Biocorp.pdf', 'C:\\Users\\ASUS\\eclipse-workspace\\.metadata\\.plugins\\org.eclipse.wst.server.core\\tmp0\\wtpwebapps\\T10\\uploads\\35eff126-b87d-4519-9480-54c060b67ea8.docx', 'done', '2024-11-22 17:22:37'),
(38, 'b6e32b6c-8959-48b9-9e3d-8d2a55b4e718', 'trinh-bien-dich_hoang-anh-viet_c4_ptcp_phan-tich-cu-phap - [cuuduongthancong.com].pdf', 'C:\\Users\\ASUS\\eclipse-workspace\\.metadata\\.plugins\\org.eclipse.wst.server.core\\tmp0\\wtpwebapps\\T10\\uploads\\b6e32b6c-8959-48b9-9e3d-8d2a55b4e718.docx', 'done', '2024-11-25 07:41:27'),
(39, '541a7f75-71f9-4205-9ebd-57d8fef8fb26', 'Báo-cáo-PBL3.pdf', 'C:\\Users\\ASUS\\eclipse-workspace\\.metadata\\.plugins\\org.eclipse.wst.server.core\\tmp0\\wtpwebapps\\T10\\uploads\\541a7f75-71f9-4205-9ebd-57d8fef8fb26.docx', 'done', '2024-11-25 08:37:49'),
(40, '2345554d-f425-4d74-a0d5-25431953c984', 'Danh_Sach_De_Tai_PBL_HDH&MMT_HAMV.pdf', 'C:\\Users\\ASUS\\eclipse-workspace\\.metadata\\.plugins\\org.eclipse.wst.server.core\\tmp0\\wtpwebapps\\T10\\uploads\\2345554d-f425-4d74-a0d5-25431953c984.docx', 'done', '2024-11-25 08:41:06'),
(41, '9760973d-93f9-4c64-bb15-2ea189321a7a', 'ĐỀ TÀI PBL4 - Lê Trần Đức.pdf', 'C:\\Users\\ASUS\\eclipse-workspace\\.metadata\\.plugins\\org.eclipse.wst.server.core\\tmp0\\wtpwebapps\\T10\\uploads\\9760973d-93f9-4c64-bb15-2ea189321a7a.docx', 'done', '2024-11-25 08:41:19'),
(42, '5f3c5b47-574e-4633-a31a-ff5659041c49', 'Đề tài PBL4 - NTLQuyen.pdf', 'C:\\Users\\ASUS\\eclipse-workspace\\.metadata\\.plugins\\org.eclipse.wst.server.core\\tmp0\\wtpwebapps\\T10\\uploads\\5f3c5b47-574e-4633-a31a-ff5659041c49.docx', 'done', '2024-11-25 08:41:26'),
(43, '16a85986-fca7-4445-9e56-81aee0407726', 'Kế hoạch triển khai PBL4 SV Khóa 22T.pdf', 'C:\\Users\\ASUS\\eclipse-workspace\\.metadata\\.plugins\\org.eclipse.wst.server.core\\tmp0\\wtpwebapps\\T10\\uploads\\16a85986-fca7-4445-9e56-81aee0407726.docx', 'done', '2024-11-25 08:41:34'),
(44, '92a154ba-db35-4ed4-9080-ad7c7848190a', 'THTTien - Danh sach De tai PBL HeDieuHanh&MMT.pdf', 'C:\\Users\\ASUS\\eclipse-workspace\\.metadata\\.plugins\\org.eclipse.wst.server.core\\tmp0\\wtpwebapps\\T10\\uploads\\92a154ba-db35-4ed4-9080-ad7c7848190a.docx', 'done', '2024-11-25 08:41:50'),
(45, '20927c74-79e8-4fe4-8a08-cd3fb28c18cb', 'Danh sach de tai  PBL He dieu hanh&MMT-Nguyen.pdf', 'C:\\Users\\ASUS\\eclipse-workspace\\.metadata\\.plugins\\org.eclipse.wst.server.core\\tmp0\\wtpwebapps\\T10\\uploads\\20927c74-79e8-4fe4-8a08-cd3fb28c18cb.docx', 'done', '2024-11-25 08:48:44'),
(46, '696ebae4-0ea9-4d17-b6a6-b125652cfd44', 'Danh sach De tai HDH&MMT_Tuan.pdf', 'C:\\Users\\ASUS\\eclipse-workspace\\.metadata\\.plugins\\org.eclipse.wst.server.core\\tmp0\\wtpwebapps\\T10\\uploads\\696ebae4-0ea9-4d17-b6a6-b125652cfd44.docx', 'done', '2024-11-25 08:48:50'),
(47, '57d53e06-1934-4c95-ac7b-88ba7f02b063', 'Danh_Sach_De_Tai_PBL_HDH&MMT_HAMV.pdf', 'C:\\Users\\ASUS\\eclipse-workspace\\.metadata\\.plugins\\org.eclipse.wst.server.core\\tmp0\\wtpwebapps\\T10\\uploads\\57d53e06-1934-4c95-ac7b-88ba7f02b063.docx', 'done', '2024-11-25 08:48:56'),
(48, '13cdcd50-6d79-47c2-a915-910df40695d6', 'ĐỀ TÀI PBL4 - Lê Trần Đức.pdf', 'C:\\Users\\ASUS\\eclipse-workspace\\.metadata\\.plugins\\org.eclipse.wst.server.core\\tmp0\\wtpwebapps\\T10\\uploads\\13cdcd50-6d79-47c2-a915-910df40695d6.docx', 'done', '2024-11-25 08:49:03'),
(49, '97284acf-3717-4898-8cf5-73e63752f4ff', 'Đề tài PBL4 - NTLQuyen.pdf', 'C:\\Users\\ASUS\\eclipse-workspace\\.metadata\\.plugins\\org.eclipse.wst.server.core\\tmp0\\wtpwebapps\\T10\\uploads\\97284acf-3717-4898-8cf5-73e63752f4ff.docx', 'done', '2024-11-25 08:49:09'),
(50, '1a65a288-12c9-436a-bd01-58822b87b835', 'Kế hoạch triển khai PBL4 SV Khóa 22T.pdf', 'C:\\Users\\ASUS\\eclipse-workspace\\.metadata\\.plugins\\org.eclipse.wst.server.core\\tmp0\\wtpwebapps\\T10\\uploads\\1a65a288-12c9-436a-bd01-58822b87b835.docx', 'done', '2024-11-25 08:49:15'),
(51, 'ac9d2a3e-f7ab-4054-a7e6-e8f16f1ad772', 'THTTien - Danh sach De tai PBL HeDieuHanh&MMT.pdf', 'C:\\Users\\ASUS\\eclipse-workspace\\.metadata\\.plugins\\org.eclipse.wst.server.core\\tmp0\\wtpwebapps\\T10\\uploads\\ac9d2a3e-f7ab-4054-a7e6-e8f16f1ad772.docx', 'done', '2024-11-25 08:49:23'),
(52, '86eae8e0-089f-4707-89ae-e8c4d5823fe9', 'THTTien_Đề tài đồ án CSNM-HĐH.pdf', 'C:\\Users\\ASUS\\eclipse-workspace\\.metadata\\.plugins\\org.eclipse.wst.server.core\\tmp0\\wtpwebapps\\T10\\uploads\\86eae8e0-089f-4707-89ae-e8c4d5823fe9.docx', 'done', '2024-11-25 08:50:09'),
(53, '767a7dc7-180b-480f-bbb1-ffe790c0b397', '1.pdf', 'C:\\Users\\ASUS\\eclipse-workspace\\.metadata\\.plugins\\org.eclipse.wst.server.core\\tmp0\\wtpwebapps\\T10\\uploads\\767a7dc7-180b-480f-bbb1-ffe790c0b397.docx', 'done', '2024-11-25 08:52:01'),
(54, 'b311f5b1-2637-44ac-9971-c9341cd3ec73', '2.pdf', 'C:\\Users\\ASUS\\eclipse-workspace\\.metadata\\.plugins\\org.eclipse.wst.server.core\\tmp0\\wtpwebapps\\T10\\uploads\\b311f5b1-2637-44ac-9971-c9341cd3ec73.docx', 'done', '2024-11-25 09:01:52'),
(55, 'af684e88-01de-4027-a3fd-c2c8fc1e42f5', '3.pdf', 'C:\\Users\\ASUS\\eclipse-workspace\\.metadata\\.plugins\\org.eclipse.wst.server.core\\tmp0\\wtpwebapps\\T10\\uploads\\af684e88-01de-4027-a3fd-c2c8fc1e42f5.docx', 'done', '2024-11-25 09:02:00'),
(56, 'b99f9d10-bc67-470c-a1bd-d72d10e9e462', '4.pdf', 'C:\\Users\\ASUS\\eclipse-workspace\\.metadata\\.plugins\\org.eclipse.wst.server.core\\tmp0\\wtpwebapps\\T10\\uploads\\b99f9d10-bc67-470c-a1bd-d72d10e9e462.docx', 'done', '2024-11-25 09:02:07'),
(57, '2c731a34-5176-4748-b52c-dfca8425b26d', '5.pdf', 'C:\\Users\\ASUS\\eclipse-workspace\\.metadata\\.plugins\\org.eclipse.wst.server.core\\tmp0\\wtpwebapps\\T10\\uploads\\2c731a34-5176-4748-b52c-dfca8425b26d.docx', 'done', '2024-11-25 09:02:15'),
(58, 'e9be2a7f-c27b-404d-bc56-889729228fd9', '1.pdf', 'C:\\Users\\ASUS\\eclipse-workspace\\.metadata\\.plugins\\org.eclipse.wst.server.core\\tmp0\\wtpwebapps\\T10\\uploads\\e9be2a7f-c27b-404d-bc56-889729228fd9.docx', 'done', '2024-11-25 09:03:00'),
(59, 'c35e15dc-4b71-43f6-833f-168288e2ef48', 'ĐỀ TÀI PBL4 - Lê Trần Đức.pdf', 'C:\\Users\\ASUS\\eclipse-workspace\\.metadata\\.plugins\\org.eclipse.wst.server.core\\tmp0\\wtpwebapps\\T10\\uploads\\c35e15dc-4b71-43f6-833f-168288e2ef48.docx', 'done', '2024-11-25 09:25:01'),
(60, 'd3e983a8-2df2-4eb4-9967-298caf464ac8', 'Đề tài PBL4 - NTLQuyen.pdf', 'C:\\Users\\ASUS\\eclipse-workspace\\.metadata\\.plugins\\org.eclipse.wst.server.core\\tmp0\\wtpwebapps\\T10\\uploads\\d3e983a8-2df2-4eb4-9967-298caf464ac8.docx', 'done', '2024-11-25 09:25:12'),
(61, 'fba46d3f-6dcc-4b82-a8e7-30fba12cde28', 'Kế hoạch triển khai PBL4 SV Khóa 22T.pdf', 'C:\\Users\\ASUS\\eclipse-workspace\\.metadata\\.plugins\\org.eclipse.wst.server.core\\tmp0\\wtpwebapps\\T10\\uploads\\fba46d3f-6dcc-4b82-a8e7-30fba12cde28.docx', 'done', '2024-11-25 09:25:18'),
(62, '4bfa5006-4177-4bec-819c-92a04d14fc57', 'Mô Hinh MVC-PHP.pdf', 'C:\\Users\\ASUS\\eclipse-workspace\\.metadata\\.plugins\\org.eclipse.wst.server.core\\tmp0\\wtpwebapps\\T10\\uploads\\4bfa5006-4177-4bec-819c-92a04d14fc57.docx', 'done', '2024-11-25 09:26:04'),
(63, 'ecad7e43-f3d7-4c0d-902d-c3e3a3f6f692', 'Mô Hinh MVC-PHP.pdf', 'C:\\Users\\ASUS\\eclipse-workspace\\.metadata\\.plugins\\org.eclipse.wst.server.core\\tmp0\\wtpwebapps\\T10\\uploads\\ecad7e43-f3d7-4c0d-902d-c3e3a3f6f692.docx', 'done', '2024-11-25 09:26:44'),
(64, 'b42428bb-e9d6-467a-bdc9-ff628bf5f533', 'ĐỀ TÀI PBL4 - Lê Trần Đức.pdf', 'C:\\Users\\ASUS\\eclipse-workspace\\.metadata\\.plugins\\org.eclipse.wst.server.core\\tmp0\\wtpwebapps\\T10\\uploads\\b42428bb-e9d6-467a-bdc9-ff628bf5f533.docx', 'done', '2024-11-25 09:30:35'),
(65, 'ce2da095-fa55-4f52-b909-23d9654da6bd', 'Đề tài PBL4 - NTLQuyen.pdf', 'C:\\Users\\ASUS\\eclipse-workspace\\.metadata\\.plugins\\org.eclipse.wst.server.core\\tmp0\\wtpwebapps\\T10\\uploads\\ce2da095-fa55-4f52-b909-23d9654da6bd.docx', 'done', '2024-11-25 09:30:46'),
(66, '31015330-7488-45b6-a162-1f2a1261e688', 'Kế hoạch triển khai PBL4 SV Khóa 22T.pdf', 'C:\\Users\\ASUS\\eclipse-workspace\\.metadata\\.plugins\\org.eclipse.wst.server.core\\tmp0\\wtpwebapps\\T10\\uploads\\31015330-7488-45b6-a162-1f2a1261e688.docx', 'done', '2024-11-25 09:31:21'),
(67, '79425bd1-aa43-4112-a161-d3c1a2a6efdf', 'Kế hoạch triển khai PBL4 SV Khóa 22T.pdf', 'C:\\Users\\ASUS\\eclipse-workspace\\.metadata\\.plugins\\org.eclipse.wst.server.core\\tmp0\\wtpwebapps\\T10\\uploads\\79425bd1-aa43-4112-a161-d3c1a2a6efdf.docx', 'done', '2024-11-25 09:31:28'),
(68, '01444550-b8ce-4f1f-aa69-02fd791a7a00', 'THTTien - Danh sach De tai PBL HeDieuHanh&MMT.pdf', 'C:\\Users\\ASUS\\eclipse-workspace\\.metadata\\.plugins\\org.eclipse.wst.server.core\\tmp0\\wtpwebapps\\T10\\uploads\\01444550-b8ce-4f1f-aa69-02fd791a7a00.docx', 'done', '2024-11-25 09:31:35'),
(69, '53c14372-0cf4-4f12-a0fd-cd3775c33e37', '!@.pdf', 'C:\\Users\\ASUS\\eclipse-workspace\\.metadata\\.plugins\\org.eclipse.wst.server.core\\tmp0\\wtpwebapps\\T10\\uploads\\53c14372-0cf4-4f12-a0fd-cd3775c33e37.docx', 'done', '2024-11-25 09:33:32'),
(70, '65a05b8c-d736-4bbd-84c4-3c735a2b2aa6', 'Phạm Hoàng Tuấn Thành -22T_DT3;;.pdf', 'C:\\Users\\ASUS\\eclipse-workspace\\.metadata\\.plugins\\org.eclipse.wst.server.core\\tmp0\\wtpwebapps\\T10\\uploads\\65a05b8c-d736-4bbd-84c4-3c735a2b2aa6.docx', 'done', '2024-11-25 09:34:07'),
(71, 'c2e3c3f3-1069-45eb-9dcb-7b514eb51721', 'Phạm Hoàng Tuấn Thành -22T_DT3;;.pdf', 'C:\\Users\\ASUS\\eclipse-workspace\\.metadata\\.plugins\\org.eclipse.wst.server.core\\tmp0\\wtpwebapps\\T10\\uploads\\c2e3c3f3-1069-45eb-9dcb-7b514eb51721.docx', 'done', '2024-11-25 09:35:50'),
(72, '3ffb5574-34ec-49dc-8d79-5f428ab2e732', 'Mô Hinh MVC-PHP.pdf', 'C:\\Users\\ASUS\\eclipse-workspace\\.metadata\\.plugins\\org.eclipse.wst.server.core\\tmp0\\wtpwebapps\\T10\\uploads\\3ffb5574-34ec-49dc-8d79-5f428ab2e732.docx', 'done', '2024-11-25 09:37:06'),
(73, 'ff663b1d-3ac4-4c35-b37e-8b2c3228b0bb', 'đây nè.pdf', 'C:\\Users\\ASUS\\eclipse-workspace\\.metadata\\.plugins\\org.eclipse.wst.server.core\\tmp0\\wtpwebapps\\T10\\uploads\\ff663b1d-3ac4-4c35-b37e-8b2c3228b0bb.pdf', 'done', '2024-11-25 09:37:30'),
(74, '4f67969f-2a7a-4aab-8eda-28b5edb63ea8', 'ACL Samples (1).pdf', 'C:\\Users\\ASUS\\eclipse-workspace\\.metadata\\.plugins\\org.eclipse.wst.server.core\\tmp0\\wtpwebapps\\T10\\uploads\\4f67969f-2a7a-4aab-8eda-28b5edb63ea8.docx', 'done', '2024-11-25 09:41:16'),
(75, '0fafd4bd-1b84-4894-bca4-90cee0cb00aa', 'DHBK - Thuc hanh Quan tri Windows Server 2012 v1.pdf', 'C:\\Users\\ASUS\\eclipse-workspace\\.metadata\\.plugins\\org.eclipse.wst.server.core\\tmp0\\wtpwebapps\\T10\\uploads\\0fafd4bd-1b84-4894-bca4-90cee0cb00aa.docx', 'done', '2024-11-25 09:42:23'),
(76, 'e93aaabe-5eec-4321-8959-5c5ad43aadfe', 'ACL Samples (1).pdf', 'C:\\Users\\ASUS\\eclipse-workspace\\.metadata\\.plugins\\org.eclipse.wst.server.core\\tmp0\\wtpwebapps\\T10\\uploads\\e93aaabe-5eec-4321-8959-5c5ad43aadfe.docx', 'done', '2024-11-25 09:42:25'),
(77, '448a7e11-9784-4942-893a-2b5a281a600a', 'Danh sach de tai  PBL He dieu hanh&MMT-Nguyen.pdf', 'C:\\Users\\ASUS\\eclipse-workspace\\.metadata\\.plugins\\org.eclipse.wst.server.core\\tmp0\\wtpwebapps\\T10\\uploads\\448a7e11-9784-4942-893a-2b5a281a600a.docx', 'done', '2024-11-25 09:43:26'),
(78, '208d2e8e-a273-4d02-814b-4ae8d3c2ed98', 'Danh sach De tai HDH&MMT_Tuan.pdf', 'C:\\Users\\ASUS\\eclipse-workspace\\.metadata\\.plugins\\org.eclipse.wst.server.core\\tmp0\\wtpwebapps\\T10\\uploads\\208d2e8e-a273-4d02-814b-4ae8d3c2ed98.docx', 'done', '2024-11-25 09:43:30'),
(79, 'a42d6242-abc8-4c02-b5f7-290c428e6345', 'Danh_Sach_De_Tai_PBL_HDH&MMT_HAMV.pdf', 'C:\\Users\\ASUS\\eclipse-workspace\\.metadata\\.plugins\\org.eclipse.wst.server.core\\tmp0\\wtpwebapps\\T10\\uploads\\a42d6242-abc8-4c02-b5f7-290c428e6345.docx', 'done', '2024-11-25 09:43:35'),
(80, '5668a332-0783-4b8f-be9a-2d7ac52b854b', 'Đề tài PBL4 - NTLQuyen.pdf', 'C:\\Users\\ASUS\\eclipse-workspace\\.metadata\\.plugins\\org.eclipse.wst.server.core\\tmp0\\wtpwebapps\\T10\\uploads\\5668a332-0783-4b8f-be9a-2d7ac52b854b.docx', 'done', '2024-11-25 09:43:40'),
(81, '28f9d56c-bf47-453e-a20a-b56387292c81', 'Danh_Sach_De_Tai_PBL_HDH&MMT_HAMV.pdf', 'C:\\Users\\ASUS\\eclipse-workspace\\.metadata\\.plugins\\org.eclipse.wst.server.core\\tmp0\\wtpwebapps\\T10\\uploads\\28f9d56c-bf47-453e-a20a-b56387292c81.docx', 'done', '2024-11-25 09:43:47'),
(82, '1438055a-62f5-46e2-9ec0-cfcefee8004f', '1337UP - Web - Cat_Club.pdf', 'C:\\Users\\ASUS\\eclipse-workspace\\.metadata\\.plugins\\org.eclipse.wst.server.core\\tmp0\\wtpwebapps\\T10\\uploads\\1438055a-62f5-46e2-9ec0-cfcefee8004f.docx', 'done', '2024-11-25 09:43:50'),
(83, '94cb9d5e-16fd-454e-ae01-87002851b2ed', 'Đề tài PBL4 - NTLQuyen.pdf', 'C:\\Users\\ASUS\\eclipse-workspace\\.metadata\\.plugins\\org.eclipse.wst.server.core\\tmp0\\wtpwebapps\\T10\\uploads\\94cb9d5e-16fd-454e-ae01-87002851b2ed.docx', 'done', '2024-11-25 09:43:53'),
(84, '2506ac59-fb9e-415f-8a45-4a577f0558cf', 'Kế hoạch triển khai PBL4 SV Khóa 22T.pdf', 'C:\\Users\\ASUS\\eclipse-workspace\\.metadata\\.plugins\\org.eclipse.wst.server.core\\tmp0\\wtpwebapps\\T10\\uploads\\2506ac59-fb9e-415f-8a45-4a577f0558cf.docx', 'done', '2024-11-25 09:43:58'),
(85, '1b0bbf98-3e1c-4290-8367-725b8d74f5ab', 'THTTien_Đề tài đồ án CSNM-HĐH.pdf', 'C:\\Users\\ASUS\\eclipse-workspace\\.metadata\\.plugins\\org.eclipse.wst.server.core\\tmp0\\wtpwebapps\\T10\\uploads\\1b0bbf98-3e1c-4290-8367-725b8d74f5ab.docx', 'done', '2024-11-25 09:44:08'),
(86, '7cab3b4b-51bd-4cf9-8c0f-2cb82ccf2159', 'THTTien_Đề tài đồ án CSNM-HĐH.pdf', 'C:\\Users\\ASUS\\eclipse-workspace\\.metadata\\.plugins\\org.eclipse.wst.server.core\\tmp0\\wtpwebapps\\T10\\uploads\\7cab3b4b-51bd-4cf9-8c0f-2cb82ccf2159.docx', 'done', '2024-11-25 09:44:52'),
(87, '6d8fd23e-c9b4-4796-b6df-b37aaa1745c7', 'Danh sach de tai  PBL He dieu hanh&MMT-Nguyen.pdf', 'C:\\Users\\ASUS\\eclipse-workspace\\.metadata\\.plugins\\org.eclipse.wst.server.core\\tmp0\\wtpwebapps\\T10\\uploads\\6d8fd23e-c9b4-4796-b6df-b37aaa1745c7.docx', 'done', '2024-11-25 09:46:25'),
(88, '0b88b8e1-43f6-477e-9a19-cbb927033f9f', '!@.pdf', 'C:\\Users\\ASUS\\eclipse-workspace\\.metadata\\.plugins\\org.eclipse.wst.server.core\\tmp0\\wtpwebapps\\T10\\uploads\\0b88b8e1-43f6-477e-9a19-cbb927033f9f.docx', 'done', '2024-11-25 14:34:46'),
(89, '84fe6e62-35fa-4cda-85b6-d09b988c44ad', 'SECCON CTF 13 Quals - Web - Self SSRF (1).pdf', 'C:\\Users\\ASUS\\eclipse-workspace\\.metadata\\.plugins\\org.eclipse.wst.server.core\\tmp0\\wtpwebapps\\T10\\uploads\\84fe6e62-35fa-4cda-85b6-d09b988c44ad.docx', 'done', '2024-11-28 03:30:10'),
(90, 'b392b72f-a533-46b7-bf66-b6243ef16097', 'SECCON CTF 13 Quals - Web - Self SSRF (1).pdf', 'C:\\Users\\ASUS\\eclipse-workspace\\.metadata\\.plugins\\org.eclipse.wst.server.core\\tmp0\\wtpwebapps\\T10\\uploads\\b392b72f-a533-46b7-bf66-b6243ef16097.docx', 'done', '2024-11-28 03:58:20'),
(91, 'b3301a94-ce90-4be3-952b-8e315f851197', 'SECCON CTF 13 Quals - Web - Self SSRF (1).pdf', 'C:\\Users\\ASUS\\eclipse-workspace\\.metadata\\.plugins\\org.eclipse.wst.server.core\\tmp0\\wtpwebapps\\T10\\uploads\\b3301a94-ce90-4be3-952b-8e315f851197.docx', 'done', '2024-11-28 04:04:03'),
(92, '9d90f624-c9a3-456e-a065-9b7c99a59ee6', 'SECCON CTF 13 Quals - Web - Self SSRF (1).pdf', 'C:\\Users\\ASUS\\eclipse-workspace\\.metadata\\.plugins\\org.eclipse.wst.server.core\\tmp0\\wtpwebapps\\T10\\uploads\\9d90f624-c9a3-456e-a065-9b7c99a59ee6.docx', 'done', '2024-11-28 04:07:13'),
(93, 'dd0b3c23-7e47-479d-b016-27be5153fa3b', 'SECCON CTF 13 Quals - Web - Self SSRF (1).pdf', 'C:\\Users\\ASUS\\eclipse-workspace\\.metadata\\.plugins\\org.eclipse.wst.server.core\\tmp0\\wtpwebapps\\T10\\uploads\\dd0b3c23-7e47-479d-b016-27be5153fa3b.docx', 'done', '2024-11-28 04:08:52'),
(94, '21db0250-8b1e-4d3a-9e9a-f13930b4b318', 'SECCON CTF 13 Quals - Web - Self SSRF (1).pdf', 'C:\\Users\\ASUS\\eclipse-workspace\\.metadata\\.plugins\\org.eclipse.wst.server.core\\tmp0\\wtpwebapps\\T10\\uploads\\21db0250-8b1e-4d3a-9e9a-f13930b4b318.docx', 'done', '2024-11-28 04:09:38'),
(95, '043f5406-395e-4507-9065-36580b3c43c3', 'SECCON CTF 13 Quals - Web - Self SSRF (1).pdf', 'C:\\Users\\ASUS\\eclipse-workspace\\.metadata\\.plugins\\org.eclipse.wst.server.core\\tmp0\\wtpwebapps\\T10\\uploads\\043f5406-395e-4507-9065-36580b3c43c3.docx', 'done', '2024-11-28 04:10:35'),
(96, 'd654026b-5d37-43a5-a09f-d0f0765e9a7a', 'SECCON CTF 13 Quals - Web - Self SSRF (1).pdf', 'C:\\Users\\ASUS\\eclipse-workspace\\.metadata\\.plugins\\org.eclipse.wst.server.core\\tmp0\\wtpwebapps\\T10\\uploads\\d654026b-5d37-43a5-a09f-d0f0765e9a7a.docx', 'done', '2024-11-28 04:14:46'),
(97, '2cad5558-cccc-46c5-9d13-cd52ecf288da', 'SECCON CTF 13 Quals - Web - Self SSRF (1).pdf', 'C:\\Users\\ASUS\\eclipse-workspace\\.metadata\\.plugins\\org.eclipse.wst.server.core\\tmp0\\wtpwebapps\\T10\\uploads\\2cad5558-cccc-46c5-9d13-cd52ecf288da.docx', 'done', '2024-11-28 04:15:47'),
(98, '95d9a6f7-675b-4cf8-9a0d-ef8cfe7b72f7', 'SECCON CTF 13 Quals - Web - Self SSRF (1).pdf', 'C:\\Users\\ASUS\\eclipse-workspace\\.metadata\\.plugins\\org.eclipse.wst.server.core\\tmp0\\wtpwebapps\\T10\\uploads\\95d9a6f7-675b-4cf8-9a0d-ef8cfe7b72f7.docx', 'done', '2024-11-28 04:17:04'),
(99, 'e52d4602-166c-45df-9ac2-cd47a1027707', 'SECCON CTF 13 Quals - Web - Self SSRF (1).pdf', 'C:\\Users\\ASUS\\eclipse-workspace\\.metadata\\.plugins\\org.eclipse.wst.server.core\\tmp0\\wtpwebapps\\T10\\uploads\\e52d4602-166c-45df-9ac2-cd47a1027707.docx', 'done', '2024-11-28 04:18:16'),
(100, '9a5d3909-8467-48a6-b64d-6ff81d065094', 'SECCON CTF 13 Quals - Web - Self SSRF (1).pdf', 'C:\\Users\\ASUS\\eclipse-workspace\\.metadata\\.plugins\\org.eclipse.wst.server.core\\tmp0\\wtpwebapps\\T10\\uploads\\9a5d3909-8467-48a6-b64d-6ff81d065094.docx', 'done', '2024-11-28 04:20:46'),
(101, 'a4267f40-a539-4e6e-a4a5-35e4407691ea', 'SECCON CTF 13 Quals - Web - Self SSRF (1).pdf', 'C:\\Users\\ASUS\\eclipse-workspace\\.metadata\\.plugins\\org.eclipse.wst.server.core\\tmp0\\wtpwebapps\\T10\\uploads\\a4267f40-a539-4e6e-a4a5-35e4407691ea.docx', 'done', '2024-11-28 04:27:04'),
(102, 'b7da4e1a-3ffa-4c04-8db2-77ab3a827e85', 'SECCON CTF 13 Quals - Web - Self SSRF (1).pdf', 'C:\\Users\\ASUS\\eclipse-workspace\\.metadata\\.plugins\\org.eclipse.wst.server.core\\tmp0\\wtpwebapps\\T10\\uploads\\b7da4e1a-3ffa-4c04-8db2-77ab3a827e85.docx', 'done', '2024-11-28 04:35:41'),
(103, '9b1b9531-ea76-4a97-b9da-df403e554554', 'SECCON CTF 13 Quals - Web - Self SSRF (1).pdf', 'C:\\Users\\ASUS\\eclipse-workspace\\.metadata\\.plugins\\org.eclipse.wst.server.core\\tmp0\\wtpwebapps\\T10\\uploads\\9b1b9531-ea76-4a97-b9da-df403e554554.docx', 'done', '2024-11-28 10:15:07'),
(104, 'c4f4defc-6c44-46b5-b350-14f7c6a60b1b', 'SECCON CTF 13 Quals - Web - Self SSRF (1).pdf', 'C:\\Users\\ASUS\\eclipse-workspace\\.metadata\\.plugins\\org.eclipse.wst.server.core\\tmp0\\wtpwebapps\\T10\\uploads\\c4f4defc-6c44-46b5-b350-14f7c6a60b1b.docx', 'done', '2024-11-28 10:20:31');

--
-- Chỉ mục cho các bảng đã đổ
--

--
-- Chỉ mục cho bảng `account`
--
ALTER TABLE `account`
  ADD PRIMARY KEY (`user`);

--
-- Chỉ mục cho bảng `file`
--
ALTER TABLE `file`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT cho các bảng đã đổ
--

--
-- AUTO_INCREMENT cho bảng `file`
--
ALTER TABLE `file`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=105;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
