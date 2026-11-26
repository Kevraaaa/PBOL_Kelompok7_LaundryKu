package dao;

import config.DatabaseConnection;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Pelanggan;

public class PelangganDAO {

    public List<Pelanggan> getAllPelanggan() {
        List<Pelanggan> list = new ArrayList<>();
        String sql = "SELECT id_pelanggan, nama, no_telepon, alamat " +
                     "FROM pelanggan " +
                     "ORDER BY id_pelanggan ASC";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Pelanggan pelanggan = new Pelanggan();
                pelanggan.setIdPelanggan(rs.getInt("id_pelanggan"));
                pelanggan.setNama(rs.getString("nama"));
                pelanggan.setNoTelepon(rs.getString("no_telepon"));
                pelanggan.setAlamat(rs.getString("alamat"));
                list.add(pelanggan);
            }
        } catch (SQLException | IOException e) {
            System.out.println("Error saat mengambil data pelanggan: " + e.getMessage());
        }

        return list;
    }

    public boolean insertPelanggan(Pelanggan pelanggan) {
        String sql = "INSERT INTO pelanggan (nama, no_telepon, alamat) VALUES (?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, pelanggan.getNama());
            stmt.setString(2, pelanggan.getNoTelepon());
            stmt.setString(3, pelanggan.getAlamat());

            return stmt.executeUpdate() > 0;
        } catch (SQLException | IOException e) {
            System.out.println("Error saat menambah pelanggan: " + e.getMessage());
        }

        return false;
    }

    public boolean updatePelanggan(Pelanggan pelanggan) {
        String sql = "UPDATE pelanggan SET nama = ?, no_telepon = ?, alamat = ? " +
                     "WHERE id_pelanggan = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, pelanggan.getNama());
            stmt.setString(2, pelanggan.getNoTelepon());
            stmt.setString(3, pelanggan.getAlamat());
            stmt.setInt(4, pelanggan.getIdPelanggan());

            return stmt.executeUpdate() > 0;
        } catch (SQLException | IOException e) {
            System.out.println("Error saat mengubah data pelanggan: " + e.getMessage());
        }

        return false;
    }

    public boolean deletePelanggan(int idPelanggan) {
        String sql = "DELETE FROM pelanggan WHERE id_pelanggan = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idPelanggan);

            return stmt.executeUpdate() > 0;
        } catch (SQLException | IOException e) {
            System.out.println("Error saat menghapus pelanggan: " + e.getMessage());
        }

        return false;
    }
}