package model;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class RecentTransaction {
    private final String kodeTransaksi;
    private final String namaPelanggan;
    private final Timestamp tanggalMasuk;
    private final String statusLaundry;
    private final BigDecimal totalHarga;

    public RecentTransaction(String kodeTransaksi, String namaPelanggan, Timestamp tanggalMasuk,
                             String statusLaundry, BigDecimal totalHarga) {
        this.kodeTransaksi = kodeTransaksi;
        this.namaPelanggan = namaPelanggan;
        this.tanggalMasuk = tanggalMasuk;
        this.statusLaundry = statusLaundry;
        this.totalHarga = totalHarga;
    }

    public String getKodeTransaksi() {
        return kodeTransaksi;
    }

    public String getNamaPelanggan() {
        return namaPelanggan;
    }

    public Timestamp getTanggalMasuk() {
        return tanggalMasuk;
    }

    public String getStatusLaundry() {
        return statusLaundry;
    }

    public BigDecimal getTotalHarga() {
        return totalHarga;
    }
}
