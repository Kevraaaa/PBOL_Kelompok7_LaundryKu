
CREATE DATABASE IF NOT EXISTS laundryku;

USE laundryku;

-- 1. TABEL USERS
CREATE TABLE users (
    id_user INT AUTO_INCREMENT PRIMARY KEY,
    nama VARCHAR(100) NOT NULL,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role ENUM('admin', 'petugas') NOT NULL DEFAULT 'petugas',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB;


-- 2. TABEL PELANGGAN
CREATE TABLE pelanggan (
    id_pelanggan INT AUTO_INCREMENT PRIMARY KEY,
    nama VARCHAR(100) NOT NULL,
    no_telepon VARCHAR(20) NOT NULL,
    alamat TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB;


-- 3. TABEL LAYANAN
CREATE TABLE layanan (
    id_layanan INT AUTO_INCREMENT PRIMARY KEY,
    nama_layanan VARCHAR(100) NOT NULL,
    harga_per_kg DECIMAL(12,2) NOT NULL,
    estimasi_hari INT NOT NULL,
    deskripsi TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB;


-- 4. TABEL TRANSAKSI
CREATE TABLE transaksi (
    id_transaksi INT AUTO_INCREMENT PRIMARY KEY,
    kode_transaksi VARCHAR(30) NOT NULL UNIQUE,
    id_pelanggan INT NOT NULL,
    id_user INT NOT NULL,
    tanggal_masuk DATETIME DEFAULT CURRENT_TIMESTAMP,
    estimasi_selesai DATE,
    tanggal_selesai DATETIME,
    total_harga DECIMAL(12,2) NOT NULL DEFAULT 0,
    status_laundry ENUM(
        'menunggu',
        'diproses',
        'selesai',
        'diambil'
    ) NOT NULL DEFAULT 'menunggu',
    catatan TEXT,

    CONSTRAINT fk_transaksi_pelanggan
        FOREIGN KEY (id_pelanggan)
        REFERENCES pelanggan(id_pelanggan),

    CONSTRAINT fk_transaksi_user
        FOREIGN KEY (id_user)
        REFERENCES users(id_user)
) ENGINE=InnoDB;


-- 5. TABEL DETAIL TRANSAKSI
CREATE TABLE detail_transaksi (
    id_detail INT AUTO_INCREMENT PRIMARY KEY,
    id_transaksi INT NOT NULL,
    id_layanan INT NOT NULL,
    berat_kg DECIMAL(8,2) NOT NULL,
    harga_per_kg DECIMAL(12,2) NOT NULL,
    subtotal DECIMAL(12,2) NOT NULL,

    CONSTRAINT fk_detail_transaksi
        FOREIGN KEY (id_transaksi)
        REFERENCES transaksi(id_transaksi)
        ON DELETE CASCADE,

    CONSTRAINT fk_detail_layanan
        FOREIGN KEY (id_layanan)
        REFERENCES layanan(id_layanan)
) ENGINE=InnoDB;


-- 6. TABEL PEMBAYARAN
CREATE TABLE pembayaran (
    id_pembayaran INT AUTO_INCREMENT PRIMARY KEY,
    id_transaksi INT NOT NULL,
    tanggal_bayar DATETIME DEFAULT CURRENT_TIMESTAMP,
    jumlah_bayar DECIMAL(12,2) NOT NULL,
    metode_pembayaran ENUM(
        'tunai',
        'transfer',
        'qris'
    ) NOT NULL,
    status_pembayaran ENUM(
        'berhasil',
        'menunggu',
        'gagal'
    ) NOT NULL DEFAULT 'berhasil',

    CONSTRAINT fk_pembayaran_transaksi
        FOREIGN KEY (id_transaksi)
        REFERENCES transaksi(id_transaksi)
) ENGINE=InnoDB;
