package main;

import dao.LayananDAO;
import dao.PelangganDAO;
import dao.UserDAO;
import model.User;

public class TestDao {
    public static void main(String[] args) {
        UserDAO userDAO = new UserDAO();
        PelangganDAO pelangganDAO = new PelangganDAO();
        LayananDAO layananDAO = new LayananDAO();

        User admin = userDAO.login("admin", "laundry123");
        if (admin != null) {
            System.out.println("Login sukses: " + admin.getNama() + " (" + admin.getRole() + ")");
        } else {
            System.out.println("Login gagal.");
        }

        System.out.println("Jumlah users: " + userDAO.getAllUsers().size());
        System.out.println("Jumlah pelanggan: " + pelangganDAO.getAllPelanggan().size());
        System.out.println("Jumlah layanan: " + layananDAO.getAllLayanan().size());

    model.Pelanggan dummy = new model.Pelanggan();
        dummy.setNama("Pelanggan Uji Coba");
        dummy.setNoTelepon("081200009999");
        dummy.setAlamat("Jl. Uji Coba No. 99");

        boolean inserted = pelangganDAO.insertPelanggan(dummy);
        System.out.println("Insert pelanggan: " + inserted);

        for (model.Pelanggan p : pelangganDAO.getAllPelanggan()) {
            if ("081200009999".equals(p.getNoTelepon())) {
                p.setNama("Pelanggan Uji Coba Edit");
                boolean updated = pelangganDAO.updatePelanggan(p);
                System.out.println("Update pelanggan: " + updated);

                boolean deleted = pelangganDAO.deletePelanggan(p.getIdPelanggan());
                System.out.println("Delete pelanggan: " + deleted);
            }
        }

        System.out.println("Jumlah pelanggan akhir: " + pelangganDAO.getAllPelanggan().size());
}}