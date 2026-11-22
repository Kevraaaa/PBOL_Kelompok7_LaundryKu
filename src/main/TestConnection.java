
package main;

import config.DatabaseConnection;
import java.sql.Connection;

public class TestConnection {

    public static void main(String[] args) {

        try (Connection connection =
                DatabaseConnection.getConnection()) {

            System.out.println("================================");
            System.out.println("KONEKSI DATABASE BERHASIL!");
            System.out.println("Database: LaundryKu");
            System.out.println("================================");

        } catch (Exception e) {

            System.out.println("KONEKSI DATABASE GAGAL!");
            System.out.println(e.getMessage());

        }
    }
}
