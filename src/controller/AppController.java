package controller;

import dao.DashboardDAO;
import dao.UserDAO;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingWorker;
import model.DashboardStats;
import model.RecentTransaction;
import model.User;
import view.Dashboard;
import view.Login;

public class AppController {
    private static final Logger LOGGER = Logger.getLogger(AppController.class.getName());
    private static final String LOGIN_CARD = "login";
    private static final String DASHBOARD_CARD = "dashboard";

    private final JFrame frame = new JFrame("LaundryKu");
    private final JPanel cards = new JPanel(new CardLayout());
    private final CardLayout cardLayout = (CardLayout) cards.getLayout();
    private final UserDAO userDAO = new UserDAO();
    private final DashboardDAO dashboardDAO = new DashboardDAO();
    private final Login loginView = new Login(this::authenticate);
    private Dashboard dashboardView;

    public void start() {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setMinimumSize(new Dimension(900, 600));
        frame.setSize(1180, 760);
        cards.add(loginView, LOGIN_CARD);
        frame.setContentPane(cards);
        frame.setLocationRelativeTo(null);
        cardLayout.show(cards, LOGIN_CARD);
        frame.setVisible(true);
    }

    private void authenticate(String username, char[] enteredPassword) {
        char[] password = enteredPassword.clone();
        Arrays.fill(enteredPassword, '\0');
        loginView.clearPassword();
        loginView.setLoading(true);

        new SwingWorker<User, Void>() {
            @Override
            protected User doInBackground() throws Exception {
                try {
                    return userDAO.loginOrThrow(username, new String(password));
                } finally {
                    Arrays.fill(password, '\0');
                }
            }

            @Override
            protected void done() {
                loginView.setLoading(false);
                try {
                    User user = get();
                    if (user == null) {
                        loginView.showMessage("Username atau password tidak sesuai.");
                        loginView.clearPassword();
                        return;
                    }
                    openDashboard(user);
                } catch (InterruptedException exception) {
                    Thread.currentThread().interrupt();
                    LOGGER.log(Level.SEVERE, "Proses autentikasi terhenti.", exception);
                    loginView.showMessage("Proses login terhenti. Silakan coba lagi.");
                } catch (ExecutionException exception) {
                    LOGGER.log(Level.SEVERE, "Autentikasi gagal karena masalah database.", exception.getCause());
                    loginView.showMessage("Tidak dapat mengakses database. Periksa konfigurasi koneksi.");
                    loginView.clearPassword();
                }
            }
        }.execute();
    }

    private void openDashboard(User user) {
        if (dashboardView != null) {
            cards.remove(dashboardView);
        }
        dashboardView = new Dashboard(user, this::logout);
        cards.add(dashboardView, DASHBOARD_CARD);
        cardLayout.show(cards, DASHBOARD_CARD);
        loadDashboardData(dashboardView);
    }

    private void loadDashboardData(Dashboard target) {
        new SwingWorker<DashboardData, Void>() {
            @Override
            protected DashboardData doInBackground() throws Exception {
                return new DashboardData(
                        dashboardDAO.getStats(),
                        dashboardDAO.getRecentTransactions());
            }

            @Override
            protected void done() {
                try {
                    DashboardData data = get();
                    target.showData(data.stats, data.transactions);
                } catch (InterruptedException exception) {
                    Thread.currentThread().interrupt();
                    LOGGER.log(Level.SEVERE, "Pemuatan dashboard terhenti.", exception);
                    target.showLoadError();
                } catch (ExecutionException exception) {
                    LOGGER.log(Level.SEVERE, "Gagal memuat data dashboard.", exception.getCause());
                    target.showLoadError();
                }
            }
        }.execute();
    }

    private void logout() {
        if (dashboardView != null) {
            cards.remove(dashboardView);
            dashboardView = null;
        }
        loginView.clearPassword();
        loginView.showMessage(" ");
        cardLayout.show(cards, LOGIN_CARD);
    }

    private static final class DashboardData {
        private final DashboardStats stats;
        private final List<RecentTransaction> transactions;

        private DashboardData(DashboardStats stats, List<RecentTransaction> transactions) {
            this.stats = stats;
            this.transactions = transactions;
        }
    }
}
