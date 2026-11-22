
USE laundryku;

-- =====================================
-- 1. DATA USERS (10 DATA)
-- Password contoh: laundry123
-- Disimpan sebagai SHA-256 untuk demo
-- =====================================

INSERT INTO users (nama, username, password, role) VALUES
('Administrator', 'admin', SHA2('laundry123', 256), 'admin'),
('Kevin', 'kevin', SHA2('laundry123', 256), 'petugas'),
('Hadziq', 'hadziq', SHA2('laundry123', 256), 'petugas'),
('Chris', 'chris', SHA2('laundry123', 256), 'petugas'),
('Naufal', 'naufal', SHA2('laundry123', 256), 'petugas'),
('Petugas 6', 'petugas6', SHA2('laundry123', 256), 'petugas'),
('Petugas 7', 'petugas7', SHA2('laundry123', 256), 'petugas'),
('Petugas 8', 'petugas8', SHA2('laundry123', 256), 'petugas'),
('Petugas 9', 'petugas9', SHA2('laundry123', 256), 'petugas'),
('Petugas 10', 'petugas10', SHA2('laundry123', 256), 'petugas');


-- =====================================
-- 2. DATA PELANGGAN (10 DATA)
-- =====================================

INSERT INTO pelanggan (nama, no_telepon, alamat) VALUES
('Andi Saputra', '081234567801', 'Jl. Merdeka No. 1'),
('Budi Santoso', '081234567802', 'Jl. Sudirman No. 2'),
('Citra Amelia', '081234567803', 'Jl. Diponegoro No. 3'),
('Dina Putri', '081234567804', 'Jl. Gatot Subroto No. 4'),
('Eko Prasetyo', '081234567805', 'Jl. Ahmad Yani No. 5'),
('Fajar Ramadhan', '081234567806', 'Jl. Melati No. 6'),
('Gita Lestari', '081234567807', 'Jl. Mawar No. 7'),
('Hendra Wijaya', '081234567808', 'Jl. Kenanga No. 8'),
('Indah Permata', '081234567809', 'Jl. Anggrek No. 9'),
('Joko Susilo', '081234567810', 'Jl. Pahlawan No. 10');


-- =====================================
-- 3. DATA LAYANAN (10 DATA)
-- =====================================

INSERT INTO layanan
(nama_layanan, harga_per_kg, estimasi_hari, deskripsi)
VALUES
('Cuci Kering', 7000, 2, 'Layanan cuci dan pengeringan'),
('Cuci Setrika', 10000, 3, 'Layanan cuci dan setrika'),
('Setrika Saja', 5000, 1, 'Layanan setrika pakaian'),
('Cuci Express', 15000, 1, 'Layanan cuci cepat'),
('Cuci Komplit', 12000, 3, 'Layanan cuci, kering, dan setrika'),
('Cuci Bed Cover', 20000, 3, 'Layanan pencucian bed cover'),
('Cuci Selimut', 15000, 2, 'Layanan pencucian selimut'),
('Cuci Karpet', 25000, 4, 'Layanan pencucian karpet'),
('Cuci Sepatu', 30000, 3, 'Layanan pencucian sepatu'),
('Cuci Tas', 25000, 3, 'Layanan pencucian tas');


-- =====================================
-- SELESAI
-- =====================================
