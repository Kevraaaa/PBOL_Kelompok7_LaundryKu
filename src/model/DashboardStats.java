package model;

public class DashboardStats {
    private final long totalPelanggan;
    private final long totalLayanan;
    private final long totalTransaksi;
    private final long transaksiDiproses;
    private final long transaksiSelesai;

    public DashboardStats(long totalPelanggan, long totalLayanan, long totalTransaksi,
                          long transaksiDiproses, long transaksiSelesai) {
        this.totalPelanggan = totalPelanggan;
        this.totalLayanan = totalLayanan;
        this.totalTransaksi = totalTransaksi;
        this.transaksiDiproses = transaksiDiproses;
        this.transaksiSelesai = transaksiSelesai;
    }

    public long getTotalPelanggan() {
        return totalPelanggan;
    }

    public long getTotalLayanan() {
        return totalLayanan;
    }

    public long getTotalTransaksi() {
        return totalTransaksi;
    }

    public long getTransaksiDiproses() {
        return transaksiDiproses;
    }

    public long getTransaksiSelesai() {
        return transaksiSelesai;
    }
}
