-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Aug 06, 2024 at 08:35 AM
-- Server version: 10.4.32-MariaDB
-- PHP Version: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `perpus_app`
--

-- --------------------------------------------------------

--
-- Table structure for table `anggota`
--

CREATE TABLE `anggota` (
  `id_anggota` varchar(8) NOT NULL,
  `nis` varchar(11) DEFAULT NULL,
  `nama` varchar(255) DEFAULT NULL,
  `jk` varchar(10) DEFAULT NULL,
  `id_tingkat` int(3) DEFAULT NULL,
  `id_jurusan` varchar(10) DEFAULT NULL,
  `id_kelas` int(2) DEFAULT NULL,
  `nope` int(13) DEFAULT NULL,
  `status` varchar(10) DEFAULT ''
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `anggota`
--

INSERT INTO `anggota` (`id_anggota`, `nis`, `nama`, `jk`, `id_tingkat`, `id_jurusan`, `id_kelas`, `nope`, `status`) VALUES
('A1100002', 'F1100089', 'Asep', 'Pria', 1, 'BKP', 1, 851012278, 'AKTIF'),
('A1100003', 'F120090', 'Supri', 'Pria', 3, 'BKP', 5, 9898009, 'AKTIF'),
('A1100005', 'F110099', 'Amel', 'Perempuan', 3, '3', 1, 82124214, 'AKTIF'),
('A1100006', 'F1200089', 'Budi', 'Pria', 3, 'DPIB', 4, 990877666, 'AKTIF'),
('A1100007', '1312323', 'Lusi', 'Perempuan', 2, 'TI', 1, 92102323, 'AKTIF'),
('A1100008', 'F1202991', 'Jeki', 'Pria', 2, 'DPIB', 6, 2193431, 'AKTIF'),
('A1100009', '777676', 'Salma', 'Perempuan', 1, 'BKP', 1, 5343434, 'AKTIF'),
('A1100010', 'F1200067', 'Syamsul', 'Pria', 2, 'DPIB', 3, 162544327, 'AKTIF');

-- --------------------------------------------------------

--
-- Table structure for table `buku`
--

CREATE TABLE `buku` (
  `kd_buku` varchar(10) NOT NULL,
  `nama_buku` varchar(255) DEFAULT '',
  `penulis` varchar(255) DEFAULT NULL,
  `penerbit` varchar(15) DEFAULT '',
  `tahun_terbit` varchar(10) DEFAULT NULL,
  `status` varchar(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `buku`
--

INSERT INTO `buku` (`kd_buku`, `nama_buku`, `penulis`, `penerbit`, `tahun_terbit`, `status`) VALUES
('1890', 'Napoleon II', 'Philip IV', 'France Media', '2020', 'tersedia'),
('1939', 'Kaiserreich', 'Winston Churchill', 'BBC', '1940', 'tersedia'),
('1965', '10 Dosa Besar Soeharto', 'Tidak Diketahui', 'IDN Media', '1991', 'tersedia'),
('1980', 'Burgundy', 'Himler', 'German Media', '2000', 'tersedia'),
('1989', 'Pem Web', 'Zudha', 'Udinus', '2019', 'tersedia');

-- --------------------------------------------------------

--
-- Table structure for table `jurusan`
--

CREATE TABLE `jurusan` (
  `id_jurusan` varchar(11) NOT NULL,
  `jurusan` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `jurusan`
--

INSERT INTO `jurusan` (`id_jurusan`, `jurusan`) VALUES
('BKP', 'Bisnis Konstruksi dan Properti'),
('DPIB', 'Desain Permodelan Informasi Bangunan'),
('TI', 'Teknik Informatika');

-- --------------------------------------------------------

--
-- Table structure for table `kelas`
--

CREATE TABLE `kelas` (
  `id_kelas` int(2) NOT NULL,
  `kelas` int(10) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `kelas`
--

INSERT INTO `kelas` (`id_kelas`, `kelas`) VALUES
(1, 1),
(2, 2),
(3, 3),
(4, 4),
(5, 5),
(6, 6),
(7, 7),
(8, 8),
(9, 9),
(10, 10);

-- --------------------------------------------------------

--
-- Table structure for table `peminjam`
--

CREATE TABLE `peminjam` (
  `id_anggota` varchar(11) NOT NULL DEFAULT '',
  `nama_anggota` varchar(255) DEFAULT '',
  `kd_buku` varchar(15) NOT NULL DEFAULT '',
  `nama_buku` varchar(255) DEFAULT '',
  `tanggal_pinjam` varchar(12) DEFAULT NULL,
  `tanggal_kembali` varchar(12) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `tingkat`
--

CREATE TABLE `tingkat` (
  `id` int(3) NOT NULL,
  `tingkat` varchar(30) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `tingkat`
--

INSERT INTO `tingkat` (`id`, `tingkat`) VALUES
(1, 'X'),
(2, 'XI'),
(3, 'XII');

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

CREATE TABLE `user` (
  `id` int(8) NOT NULL,
  `nama` char(100) DEFAULT NULL,
  `username` varchar(100) DEFAULT NULL,
  `password` int(8) DEFAULT NULL,
  `level` varchar(15) DEFAULT ''
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`id`, `nama`, `username`, `password`, `level`) VALUES
(1, 'Ali', 'Ali', 301222, 'admin'),
(2, 'Luna', 'Luna', 201222, 'pustakawan');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `anggota`
--
ALTER TABLE `anggota`
  ADD PRIMARY KEY (`id_anggota`);

--
-- Indexes for table `buku`
--
ALTER TABLE `buku`
  ADD PRIMARY KEY (`kd_buku`);

--
-- Indexes for table `jurusan`
--
ALTER TABLE `jurusan`
  ADD PRIMARY KEY (`id_jurusan`);

--
-- Indexes for table `kelas`
--
ALTER TABLE `kelas`
  ADD PRIMARY KEY (`id_kelas`);

--
-- Indexes for table `peminjam`
--
ALTER TABLE `peminjam`
  ADD PRIMARY KEY (`id_anggota`,`kd_buku`);

--
-- Indexes for table `tingkat`
--
ALTER TABLE `tingkat`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `kelas`
--
ALTER TABLE `kelas`
  MODIFY `id_kelas` int(2) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT for table `tingkat`
--
ALTER TABLE `tingkat`
  MODIFY `id` int(3) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `user`
--
ALTER TABLE `user`
  MODIFY `id` int(8) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
