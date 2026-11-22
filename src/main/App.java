
package main;

import javax.swing.*;
import java.awt.*;

public class App {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("LaundryKu");
            frame.setSize(900, 600);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setLocationRelativeTo(null);

            JLabel title = new JLabel(
                "Selamat Datang di LaundryKu",
                SwingConstants.CENTER
            );

            title.setFont(new Font("Arial", Font.BOLD, 28));

            frame.add(title);
            frame.setVisible(true);
        });
    }
}
