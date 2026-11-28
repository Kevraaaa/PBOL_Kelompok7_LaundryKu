package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;
import model.DashboardStats;
import model.RecentTransaction;
import model.User;

public class Dashboard extends JPanel {
    private static final Color BACKGROUND = new Color(244, 247, 251);
    private static final Color NAVY = new Color(20, 52, 94);
    private static final Color MUTED = new Color(105, 121, 142);
    private final User user;
    private final Runnable onLogout;
    private final JPanel content = new JPanel(new BorderLayout(0, 18));
    private final JPanel page = new JPanel(new BorderLayout());
    private Sidebar sidebar;
    private DashboardStats stats;
    private List<RecentTransaction> transactions;
    private String activePage = "Dashboard";
    private JLabel loadMessage;
    private String loadErrorMessage;

    public Dashboard(User user, Runnable onLogout) {
        this.user = user;
        this.onLogout = onLogout;
        setLayout(new BorderLayout());
        setBackground(BACKGROUND);
        sidebar = new Sidebar(activePage, this::navigate);
        add(sidebar, BorderLayout.WEST);
        content.setBackground(BACKGROUND);
        content.setBorder(BorderFactory.createEmptyBorder(30, 34, 30, 34));
        add(content, BorderLayout.CENTER);
        showDashboard();
    }

    private void navigate(String menu) {
        if ("Logout".equals(menu)) {
            onLogout.run();
            return;
        }
        activePage = menu;
        sidebar.setActive(menu);
        if ("Dashboard".equals(menu)) {
            showDashboard();
        } else {
            showPlaceholder(menu);
        }
    }

    private void showDashboard() {
        content.removeAll();
        content.setBorder(BorderFactory.createEmptyBorder(30, 34, 30, 34));
        JPanel heading = new JPanel(new BorderLayout());
        heading.setOpaque(false);
        JLabel title = new JLabel("Dashboard");
        title.setFont(new Font("SansSerif", Font.BOLD, 27));
        title.setForeground(NAVY);
        JLabel greeting = new JLabel("Selamat datang, " + user.getNama());
        greeting.setFont(new Font("SansSerif", Font.PLAIN, 14));
        greeting.setForeground(MUTED);
        JPanel titles = new JPanel(new GridLayout(2, 1, 0, 5));
        titles.setOpaque(false);
        titles.add(title);
        titles.add(greeting);
        heading.add(titles, BorderLayout.WEST);

        JPanel main = new JPanel(new BorderLayout(0, 18));
        main.setOpaque(false);
        JPanel summaryPanel = new JPanel(new BorderLayout(0, 12));
        summaryPanel.setOpaque(false);
        JLabel summaryTitle = new JLabel("Ringkasan operasional");
        summaryTitle.setFont(new Font("SansSerif", Font.BOLD, 16));
        summaryTitle.setForeground(NAVY);
        summaryPanel.add(summaryTitle, BorderLayout.NORTH);

        JPanel cards = new JPanel(new GridLayout(2, 3, 12, 12));
        cards.setOpaque(false);
        cards.add(createStatCard("Total pelanggan", value(stats == null ? null : stats.getTotalPelanggan())));
        cards.add(createStatCard("Total layanan", value(stats == null ? null : stats.getTotalLayanan())));
        cards.add(createStatCard("Total transaksi", value(stats == null ? null : stats.getTotalTransaksi())));
        cards.add(createStatCard("Sedang diproses", value(stats == null ? null : stats.getTransaksiDiproses())));
        cards.add(createStatCard("Transaksi selesai", value(stats == null ? null : stats.getTransaksiSelesai())));
        loadMessage = new JLabel(getLoadMessage());
        loadMessage.setFont(new Font("SansSerif", Font.PLAIN, 12));
        loadMessage.setForeground(loadErrorMessage == null ? MUTED : new Color(184, 55, 55));
        JPanel statContent = new JPanel(new BorderLayout(0, 7));
        statContent.setOpaque(false);
        statContent.add(cards, BorderLayout.CENTER);
        statContent.add(loadMessage, BorderLayout.SOUTH);
        summaryPanel.add(statContent, BorderLayout.CENTER);
        main.add(summaryPanel, BorderLayout.NORTH);

        main.add(createRecentPanel(), BorderLayout.CENTER);
        content.add(heading, BorderLayout.NORTH);
        content.add(main, BorderLayout.CENTER);
        content.revalidate();
        content.repaint();
    }

    private JPanel createRecentPanel() {
        JPanel panel = new JPanel(new BorderLayout(0, 10));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(227, 233, 241)),
                BorderFactory.createEmptyBorder(16, 18, 16, 18)));
        JLabel title = new JLabel("Transaksi terbaru");
        title.setFont(new Font("SansSerif", Font.BOLD, 16));
        title.setForeground(NAVY);
        panel.add(title, BorderLayout.NORTH);

        String[] columns = {"Kode transaksi", "Pelanggan", "Tanggal masuk", "Status", "Total"};
        DefaultTableModel tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        if (transactions != null) {
            SimpleDateFormat dateFormat = new SimpleDateFormat(
                    "dd MMM yyyy, HH:mm", java.util.Locale.forLanguageTag("id-ID"));
            NumberFormat currency = NumberFormat.getCurrencyInstance(
                    java.util.Locale.forLanguageTag("id-ID"));
            for (RecentTransaction transaction : transactions) {
                tableModel.addRow(new Object[] {
                        transaction.getKodeTransaksi(),
                        transaction.getNamaPelanggan(),
                        transaction.getTanggalMasuk() == null
                                ? "-" : dateFormat.format(transaction.getTanggalMasuk()),
                        transaction.getStatusLaundry(),
                        currency.format(transaction.getTotalHarga())
                });
            }
        }
        JTable table = new JTable(tableModel);
        table.setRowHeight(30);
        table.setFont(new Font("SansSerif", Font.PLAIN, 12));
        table.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 12));
        table.setFillsViewportHeight(true);
        panel.add(new JScrollPane(table), BorderLayout.CENTER);
        return panel;
    }

    private JPanel createStatCard(String title, String value) {
        JPanel card = new JPanel(new BorderLayout(0, 8));
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(227, 233, 241)),
                BorderFactory.createEmptyBorder(15, 16, 15, 16)));
        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("SansSerif", Font.PLAIN, 12));
        titleLabel.setForeground(MUTED);
        JLabel valueLabel = new JLabel(value, SwingConstants.LEFT);
        valueLabel.setFont(new Font("SansSerif", Font.BOLD, 25));
        valueLabel.setForeground(NAVY);
        card.add(titleLabel, BorderLayout.NORTH);
        card.add(valueLabel, BorderLayout.CENTER);
        return card;
    }

    private static String value(Long value) {
        return value == null ? "-" : String.valueOf(value);
    }

    private String getLoadMessage() {
        if (loadErrorMessage != null) {
            return loadErrorMessage;
        }
        if (stats == null) {
            return "Memuat statistik...";
        }
        if (stats.getTotalPelanggan() == 0
                && stats.getTotalLayanan() == 0
                && stats.getTotalTransaksi() == 0) {
            return "Database terhubung, tetapi belum ada data operasional.";
        }
        return " ";
    }

    private void showPlaceholder(String menu) {
        content.removeAll();
        JPanel placeholder = new JPanel(new GridLayout(2, 1, 0, 8));
        placeholder.setBackground(Color.WHITE);
        placeholder.setBorder(BorderFactory.createEmptyBorder(30, 34, 30, 34));
        JLabel title = new JLabel(menu);
        title.setFont(new Font("SansSerif", Font.BOLD, 25));
        title.setForeground(NAVY);
        JLabel message = new JLabel("Halaman ini belum tersedia.");
        message.setFont(new Font("SansSerif", Font.PLAIN, 14));
        message.setForeground(MUTED);
        placeholder.add(title);
        placeholder.add(message);
        page.removeAll();
        page.setBackground(BACKGROUND);
        page.setBorder(BorderFactory.createEmptyBorder(30, 34, 30, 34));
        page.add(placeholder, BorderLayout.CENTER);
        content.setBorder(BorderFactory.createEmptyBorder());
        content.add(page, BorderLayout.CENTER);
        content.revalidate();
        content.repaint();
    }

    public void showData(DashboardStats stats, List<RecentTransaction> transactions) {
        this.stats = stats;
        this.transactions = transactions;
        loadErrorMessage = null;
        if ("Dashboard".equals(activePage)) {
            showDashboard();
        }
    }

    public void showLoadError() {
        loadErrorMessage = "Data dashboard gagal dimuat. Periksa koneksi database.";
        if ("Dashboard".equals(activePage)) {
            if (loadMessage == null) {
                showDashboard();
            } else {
                loadMessage.setText(loadErrorMessage);
                loadMessage.setForeground(new Color(184, 55, 55));
            }
        }
    }
}
