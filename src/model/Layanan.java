package model;

public class Layanan {
    private int idLayanan;
    private String namaLayanan;
    private double hargaPerKg;
    private int estimasiHari;
    private String deskripsi;

    public Layanan() {
    }

    public Layanan(int idLayanan, String namaLayanan, double hargaPerKg, int estimasiHari, String deskripsi) {
        this.idLayanan = idLayanan;
        this.namaLayanan = namaLayanan;
        this.hargaPerKg = hargaPerKg;
        this.estimasiHari = estimasiHari;
        this.deskripsi = deskripsi;
    }

    public int getIdLayanan() {
        return idLayanan;
    }

    public void setIdLayanan(int idLayanan) {
        this.idLayanan = idLayanan;
    }

    public String getNamaLayanan() {
        return namaLayanan;
    }

    public void setNamaLayanan(String namaLayanan) {
        this.namaLayanan = namaLayanan;
    }

    public double getHargaPerKg() {
        return hargaPerKg;
    }

    public void setHargaPerKg(double hargaPerKg) {
        this.hargaPerKg = hargaPerKg;
    }

    public int getEstimasiHari() {
        return estimasiHari;
    }

    public void setEstimasiHari(int estimasiHari) {
        this.estimasiHari = estimasiHari;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }
}