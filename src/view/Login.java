package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.Arrays;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

public class Login extends JPanel {
    public interface LoginListener {
        void onLogin(String username, char[] password);
    }

    private static final Color NAVY = new Color(20, 52, 94);
    private static final Color BLUE = new Color(38, 112, 190);
    private static final Color MUTED = new Color(105, 121, 142);
    private static final Color BORDER = new Color(222, 230, 239);

    private final JTextField usernameField = new JTextField(22);
    private final JPasswordField passwordField = new JPasswordField(22);
    private final JButton loginButton = new JButton("Masuk ke Dashboard");
    private final JLabel messageLabel = new JLabel(" ", SwingConstants.CENTER);

    public Login(LoginListener listener) {
        setLayout(new BorderLayout());
        setBackground(new Color(241, 246, 252));
        setBorder(new EmptyBorder(35, 24, 35, 24));

        JPanel card = new JPanel(new GridBagLayout());
        card.setBackground(Color.WHITE);
        card.setBorder(new EmptyBorder(42, 48, 40, 48));
        card.setPreferredSize(new Dimension(430, 470));

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.weightx = 1;
        constraints.insets = new Insets(6, 0, 6, 0);

        JLabel brand = new JLabel("LaundryKu", SwingConstants.CENTER);
        brand.setFont(new Font("SansSerif", Font.BOLD, 32));
        brand.setForeground(NAVY);
        addRow(card, constraints, brand, 0);

        JLabel subtitle = new JLabel("Masuk untuk mengelola operasional laundry", SwingConstants.CENTER);
        subtitle.setFont(new Font("SansSerif", Font.PLAIN, 14));
        subtitle.setForeground(MUTED);
        constraints.insets = new Insets(0, 0, 28, 0);
        addRow(card, constraints, subtitle, 1);

        constraints.insets = new Insets(0, 0, 4, 0);
        addRow(card, constraints, fieldLabel("Username"), 2);
        styleField(usernameField);
        constraints.insets = new Insets(0, 0, 16, 0);
        addRow(card, constraints, usernameField, 3);

        constraints.insets = new Insets(0, 0, 4, 0);
        addRow(card, constraints, fieldLabel("Password"), 4);
        styleField(passwordField);
        constraints.insets = new Insets(0, 0, 12, 0);
        addRow(card, constraints, passwordField, 5);

        messageLabel.setFont(new Font("SansSerif", Font.PLAIN, 12));
        messageLabel.setForeground(new Color(184, 55, 55));
        constraints.insets = new Insets(0, 0, 10, 0);
        addRow(card, constraints, messageLabel, 6);

        loginButton.setFont(new Font("SansSerif", Font.BOLD, 14));
        loginButton.setForeground(Color.WHITE);
        loginButton.setBackground(BLUE);
        loginButton.setFocusPainted(false);
        loginButton.setBorder(BorderFactory.createEmptyBorder(13, 16, 13, 16));
        loginButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        loginButton.addActionListener(event -> {
            String username = usernameField.getText().trim();
            char[] password = passwordField.getPassword();
            if (username.isEmpty() || password.length == 0) {
                Arrays.fill(password, '\0');
                showMessage("Username dan password wajib diisi.");
                return;
            }
            showMessage(" ");
            listener.onLogin(username, password);
        });
        addRow(card, constraints, loginButton, 7);

        JPanel center = new JPanel(new GridBagLayout());
        center.setOpaque(false);
        center.add(card);
        add(center, BorderLayout.CENTER);
    }

    private static JLabel fieldLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("SansSerif", Font.BOLD, 13));
        label.setForeground(NAVY);
        return label;
    }

    private static void styleField(JTextField field) {
        field.setFont(new Font("SansSerif", Font.PLAIN, 14));
        field.setForeground(NAVY);
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER),
                BorderFactory.createEmptyBorder(10, 12, 10, 12)));
    }

    private static void addRow(JPanel panel, GridBagConstraints constraints, java.awt.Component component, int row) {
        constraints.gridy = row;
        panel.add(component, constraints);
    }

    public void setLoading(boolean loading) {
        loginButton.setEnabled(!loading);
        loginButton.setText(loading ? "Memeriksa..." : "Masuk ke Dashboard");
    }

    public void showMessage(String message) {
        messageLabel.setText(message);
    }

    public void clearPassword() {
        passwordField.setText("");
    }
}
