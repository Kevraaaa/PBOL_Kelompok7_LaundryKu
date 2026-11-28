package dao;

import config.DatabaseConnection;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Layanan;

public class LayananDAO {

    public List<Layanan> getAllLayanan() {
        List<Layanan> list = new ArrayList<>();
        String sql = "SELECT id_layanan, nama_layanan, harga_per_kg, estimasi_hari, deskripsi " +
                     "FROM layanan " +
                     "ORDER BY id_layanan ASC";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Layanan layanan = new Layanan();
                layanan.setIdLayanan(rs.getInt("id_layanan"));
                layanan.setNamaLayanan(rs.getString("nama_layanan"));
                layanan.setHargaPerKg(rs.getDouble("harga_per_kg"));
                layanan.setEstimasiHari(rs.getInt("estimasi_hari"));
                layanan.setDeskripsi(rs.getString("deskripsi"));
                list.add(layanan);
            }
        } catch (SQLException | IOException e) {
            System.out.println("Error saat mengambil data layanan: " + e.getMessage());
        }

        return list;
    }

    public boolean insertLayanan(Layanan layanan) {
        String sql = "INSERT INTO layanan (nama_layanan, harga_per_kg, estimasi_hari, deskripsi) " +
                     "VALUES (?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, layanan.getNamaLayanan());
            stmt.setDouble(2, layanan.getHargaPerKg());
            stmt.setInt(3, layanan.getEstimasiHari());
            stmt.setString(4, layanan.getDeskripsi());

            return stmt.executeUpdate() > 0;
        } catch (SQLException | IOException e) {
            System.out.println("Error saat menambah layanan: " + e.getMessage());
        }

        return false;
    }

    public boolean updateLayanan(Layanan layanan) {
        String sql = "UPDATE layanan " +
                     "SET nama_layanan = ?, harga_per_kg = ?, estimasi_hari = ?, deskripsi = ? " +
                     "WHERE id_layanan = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, layanan.getNamaLayanan());
            stmt.setDouble(2, layanan.getHargaPerKg());
            stmt.setInt(3, layanan.getEstimasiHari());
            stmt.setString(4, layanan.getDeskripsi());
            stmt.setInt(5, layanan.getIdLayanan());

            return stmt.executeUpdate() > 0;
        } catch (SQLException | IOException e) {
            System.out.println("Error saat mengubah data layanan: " + e.getMessage());
        }

        return false;
    }

    public boolean deleteLayanan(int idLayanan) {
        String sql = "DELETE FROM layanan WHERE id_layanan = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idLayanan);

            return stmt.executeUpdate() > 0;
        } catch (SQLException | IOException e) {
            System.out.println("Error saat menghapus layanan: " + e.getMessage());
        }

        return false;
    }
}