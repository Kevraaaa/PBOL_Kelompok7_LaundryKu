package dao;

import config.DatabaseConnection;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.DashboardStats;
import model.RecentTransaction;

public class DashboardDAO {
    public DashboardStats getStats() throws SQLException, IOException {
        String sql = "SELECT " +
                "(SELECT COUNT(*) FROM pelanggan) AS total_pelanggan, " +
                "(SELECT COUNT(*) FROM layanan) AS total_layanan, " +
                "(SELECT COUNT(*) FROM transaksi) AS total_transaksi, " +
                "(SELECT COUNT(*) FROM transaksi WHERE status_laundry = 'diproses') AS transaksi_diproses, " +
                "(SELECT COUNT(*) FROM transaksi WHERE status_laundry = 'selesai') AS transaksi_selesai";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet result = statement.executeQuery()) {
            if (!result.next()) {
                throw new SQLException("Query statistik dashboard tidak menghasilkan data.");
            }

            return new DashboardStats(
                    result.getLong("total_pelanggan"),
                    result.getLong("total_layanan"),
                    result.getLong("total_transaksi"),
                    result.getLong("transaksi_diproses"),
                    result.getLong("transaksi_selesai"));
        }
    }

    public List<RecentTransaction> getRecentTransactions() throws SQLException, IOException {
        String sql = "SELECT t.kode_transaksi, p.nama AS nama_pelanggan, t.tanggal_masuk, " +
                "t.status_laundry, t.total_harga " +
                "FROM transaksi t " +
                "JOIN pelanggan p ON p.id_pelanggan = t.id_pelanggan " +
                "ORDER BY t.tanggal_masuk DESC, t.id_transaksi DESC " +
                "LIMIT 5";
        List<RecentTransaction> transactions = new ArrayList<>();

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet result = statement.executeQuery()) {
            while (result.next()) {
                transactions.add(new RecentTransaction(
                        result.getString("kode_transaksi"),
                        result.getString("nama_pelanggan"),
                        result.getTimestamp("tanggal_masuk"),
                        result.getString("status_laundry"),
                        result.getBigDecimal("total_harga")));
            }
        }

        return transactions;
    }
}
