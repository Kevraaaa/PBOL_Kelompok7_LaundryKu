package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Consumer;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

public class Sidebar extends JPanel {
    private static final Color NAVY = new Color(20, 52, 94);
    private static final Color ACTIVE = new Color(38, 112, 190);
    private final Map<String, JButton> menuButtons = new LinkedHashMap<>();

    public Sidebar(String activeMenu, Consumer<String> onNavigate) {
        setLayout(new BorderLayout());
        setBackground(NAVY);
        setBorder(new EmptyBorder(24, 14, 18, 14));
        setPreferredSize(new Dimension(230, 0));

        JLabel brand = new JLabel("LaundryKu", SwingConstants.LEFT);
        brand.setFont(new Font("SansSerif", Font.BOLD, 23));
        brand.setForeground(Color.WHITE);
        brand.setBorder(new EmptyBorder(0, 12, 28, 0));
        add(brand, BorderLayout.NORTH);

        JPanel menuPanel = new JPanel();
        menuPanel.setOpaque(false);
        menuPanel.setLayout(new javax.swing.BoxLayout(menuPanel, javax.swing.BoxLayout.Y_AXIS));
        String[] menus = {"Dashboard", "Data Pelanggan", "Data Layanan", "Transaksi", "Pembayaran", "Laporan"};
        for (String menu : menus) {
            JButton button = createMenuButton(menu);
            button.addActionListener(event -> onNavigate.accept(menu));
            menuButtons.put(menu, button);
            menuPanel.add(button);
            menuPanel.add(javax.swing.Box.createVerticalStrut(5));
        }
        add(menuPanel, BorderLayout.CENTER);

        JButton logoutButton = createMenuButton("Logout");
        logoutButton.setForeground(new Color(255, 215, 215));
        logoutButton.addActionListener(event -> onNavigate.accept("Logout"));
        menuButtons.put("Logout", logoutButton);
        add(logoutButton, BorderLayout.SOUTH);
        setActive(activeMenu);
    }

    private JButton createMenuButton(String label) {
        JButton button = new JButton(label);
        button.setHorizontalAlignment(SwingConstants.LEFT);
        button.setFont(new Font("SansSerif", Font.PLAIN, 14));
        button.setForeground(new Color(220, 231, 244));
        button.setBackground(NAVY);
        button.setBorder(BorderFactory.createEmptyBorder(12, 13, 12, 13));
        button.setFocusPainted(false);
        button.setOpaque(true);
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        button.setMaximumSize(new Dimension(Integer.MAX_VALUE, 45));
        return button;
    }

    public void setActive(String activeMenu) {
        for (Map.Entry<String, JButton> entry : menuButtons.entrySet()) {
            boolean active = entry.getKey().equals(activeMenu);
            entry.getValue().setBackground(active ? ACTIVE : NAVY);
            if (!"Logout".equals(entry.getKey())) {
                entry.getValue().setForeground(active ? Color.WHITE : new Color(220, 231, 244));
            }
            entry.getValue().setFont(new Font("SansSerif", active ? Font.BOLD : Font.PLAIN, 14));
        }
    }
}
