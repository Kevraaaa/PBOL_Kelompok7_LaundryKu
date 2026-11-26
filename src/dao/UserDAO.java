package dao;

import config.DatabaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.User;

public class UserDAO {

    public User login(String username, String password) {
        String sql = "SELECT id_user, nama, username, role " +
                     "FROM users " +
                     "WHERE username = ? AND password = SHA2(?, 256) " +
                     "LIMIT 1";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, username);
            stmt.setString(2, password);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    User user = new User();
                    user.setIdUser(rs.getInt("id_user"));
                    user.setNama(rs.getString("nama"));
                    user.setUsername(rs.getString("username"));
                    user.setRole(rs.getString("role"));
                    return user;
                }
            }
        } catch (Exception e) {
            System.out.println("Error saat login: " + e.getMessage());
        }

        return null;
    }

    public List<User> getAllUsers() {
        List<User> list = new ArrayList<>();
        String sql = "SELECT id_user, nama, username, role FROM users ORDER BY id_user ASC";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                User user = new User();
                user.setIdUser(rs.getInt("id_user"));
                user.setNama(rs.getString("nama"));
                user.setUsername(rs.getString("username"));
                user.setRole(rs.getString("role"));
                list.add(user);
            }
        } catch (SQLException | java.io.IOException e) {
            System.out.println("Error saat mengambil data users: " + e.getMessage());
        }

        return list;
    }
}