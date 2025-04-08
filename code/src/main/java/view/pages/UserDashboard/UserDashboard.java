package view.pages.UserDashboard;

import controller.uiControllers.UserDashboard.UserDashboardController;
import utils.PageSwitcher;
import javax.swing.*;
import javax.swing.plaf.basic.BasicTabbedPaneUI;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class UserDashboard extends JPanel {
    private UserDashboardController userDashboardController;
    private JButton logoutButton;
    private UserCommandsManagementTab userCommandsManagementTab;

    public UserDashboard() {}

    public UserCommandsManagementTab getUserCommandsManagementTab() {
        return userCommandsManagementTab;
    }

    public void reloadTabs() {
        userCommandsManagementTab = new UserCommandsManagementTab();
        setUpUi();
        userDashboardController = new UserDashboardController(this);
        userCommandsManagementTab.refreshTable();
    }

    public JButton getLogoutButton() {
        return logoutButton;
    }

    void setUpUi() {
        // Main panel setup
        this.setLayout(new BorderLayout());
        this.setBackground(new Color(248, 249, 250)); // Light gray background

        // Modern color scheme (matching AdminDashboard)
        Color primaryColor = new Color(41, 128, 185);  // Blue primary color
        Color secondaryColor = new Color(52, 152, 219); // Lighter blue
        Color accentColor = new Color(52, 107, 143);     // Red for logout

        // Create modern header with gradient
        JPanel headerPanel = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                GradientPaint gradient = new GradientPaint(
                        0, 0, primaryColor,
                        getWidth(), 0, secondaryColor);
                g2d.setPaint(gradient);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        headerPanel.setPreferredSize(new Dimension(getWidth(), 80));
        headerPanel.setBorder(BorderFactory.createEmptyBorder(0, 30, 0, 30));

        // Add logo and title with modern typography
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 0));
        titlePanel.setOpaque(false);

        // Logo placeholder
        JLabel logo = new JLabel("USER", SwingConstants.CENTER);
        logo.setFont(new Font("Segoe UI", Font.BOLD, 24));
        logo.setForeground(Color.WHITE);
        logo.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 20));

        // Title with subtle shadow
        JLabel titleLabel = new JLabel("Dashboard");
        titleLabel.setFont(new Font("Segoe UI Light", Font.PLAIN, 28));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(5, 0, 0, 0));

        titlePanel.add(logo);
        titlePanel.add(new JSeparator(SwingConstants.VERTICAL) {{
            setForeground(new Color(255, 255, 255, 120));
            setPreferredSize(new Dimension(1, 40));
        }});
        titlePanel.add(titleLabel);

        headerPanel.add(titlePanel, BorderLayout.WEST);

        // Modern logout button with hover effects
        logoutButton = new JButton("LOGOUT") {{
            setFont(new Font("Segoe UI Semibold", Font.PLAIN, 14));
            setForeground(Color.WHITE);
            setBackground(accentColor);
            setFocusPainted(false);
            setBorder(BorderFactory.createEmptyBorder(10, 25, 10, 25));
            setCursor(new Cursor(Cursor.HAND_CURSOR));
            setContentAreaFilled(false);
            setOpaque(true);

            // Hover effect
            addMouseListener(new MouseAdapter() {
                public void mouseEntered(MouseEvent e) {
                    setBackground(new Color(38, 80, 108));
                }
                public void mouseExited(MouseEvent e) {
                    setBackground(accentColor);
                }
            });

            // Logout action
            addActionListener(e -> PageSwitcher.switchPage("login"));
        }};

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setOpaque(false);
        buttonPanel.add(logoutButton);
        headerPanel.add(buttonPanel, BorderLayout.EAST);


        JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP) {
            @Override
            public void updateUI() {
                super.updateUI();
                setUI(new UnderlineTabbedPaneUI());
            }
        };

        // Tab styling
        tabbedPane.setBackground(new Color(248, 249, 250));
        tabbedPane.setForeground(new Color(60, 60, 60));
        tabbedPane.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 14));

        tabbedPane.setBorder(BorderFactory.createEmptyBorder());

        // Custom tab UI with underline support
        UIManager.put("TabbedPane.selected", new Color(248, 249, 250)); // Transparent background
        UIManager.put("TabbedPane.borderHightlightColor", Color.WHITE);
        UIManager.put("TabbedPane.contentAreaColor", new Color(248, 249, 250));

        // Add modern tab with icon and underline
        addModernTabWithUnderline(tabbedPane, "Commandes Management",
                userCommandsManagementTab, UIManager.getIcon("FileView.hardDriveIcon"));

        // Add subtle shadow below header
        headerPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 2, 0, new Color(220, 220, 220)),
                BorderFactory.createEmptyBorder(0, 30, 0, 30)
        ));

        // Add components to main panel
        this.add(headerPanel, BorderLayout.NORTH);
        this.add(tabbedPane, BorderLayout.CENTER);
    }

    private void addModernTabWithUnderline(JTabbedPane pane, String title, JComponent component, Icon icon) {
        JPanel tabPanel = new JPanel(new BorderLayout());
        tabPanel.setOpaque(false);

        // Create panel for icon and label
        JPanel contentPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        contentPanel.setOpaque(false);

        JLabel iconLabel = new JLabel(icon);
        JLabel textLabel = new JLabel(title);
        textLabel.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 14));

        contentPanel.add(iconLabel);
        contentPanel.add(textLabel);

        // Create underline panel
        JPanel underline = new JPanel();
        underline.setPreferredSize(new Dimension(0, 3));
        underline.setBackground(new Color(255, 255, 255, 255)); // Primary color
        underline.setVisible(false);

        // Combine components
        tabPanel.add(contentPanel, BorderLayout.CENTER);
        tabPanel.add(underline, BorderLayout.SOUTH);

        pane.addTab(title, component);
        pane.setTabComponentAt(pane.getTabCount() - 1, tabPanel);

        // Add change listener to handle underline visibility
        pane.addChangeListener(e -> {
            int selIndex = pane.getSelectedIndex();
            for (int i = 0; i < pane.getTabCount(); i++) {
                Component tab = pane.getTabComponentAt(i);
                if (tab instanceof JPanel) {
                    Component[] comps = ((JPanel) tab).getComponents();
                    for (Component c : comps) {
                        if (c instanceof JPanel && c.getHeight() == 3) { // Our underline
                            c.setVisible(i == selIndex);
                        }
                    }
                }
            }
        });
    }

    class UnderlineTabbedPaneUI extends BasicTabbedPaneUI {
        @Override
        protected void installDefaults() {
            super.installDefaults();
            tabAreaInsets.left = 10;
            contentBorderInsets = new Insets(0, 0, 0, 0);
        }

        @Override
        protected void paintTabBorder(Graphics g, int tabPlacement,
                                      int tabIndex, int x, int y, int w, int h,
                                      boolean isSelected) {
            // No border - we use our custom underline instead
        }

        @Override
        protected void paintContentBorder(Graphics g, int tabPlacement, int selectedIndex) {
            // No content border
        }
    }

    public static void main(String[] args) {
        // Create a JFrame to display the UserDashboard
        JFrame frame = new JFrame("User Dashboard");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1024, 768);
        frame.setLocationRelativeTo(null); // Center the frame

        // Add the UserDashboard panel to the frame
        UserDashboard userDashboard = new UserDashboard();
        frame.add(userDashboard);

        // Display the frame
        frame.setVisible(true);
    }
}