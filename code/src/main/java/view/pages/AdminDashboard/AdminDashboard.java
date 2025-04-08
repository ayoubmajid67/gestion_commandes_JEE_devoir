package view.pages.AdminDashboard;

import controller.uiControllers.adminDashboard.AdminDashboardController;
import view.pages.UserDashboard.UserDashboard;

import javax.swing.*;
import javax.swing.plaf.basic.BasicTabbedPaneUI;
import java.awt.*;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class AdminDashboard extends JPanel {

    private AdminDashboardController adminDashboardController;
    private JButton logoutButton;
    private AccountsManagementTab accountsManagementTab ;
    private AdminCommandsManagementTab adminCommandsManagementTab ;

    public AdminDashboard() {

    }
    public void reloadTaps(){
        adminCommandsManagementTab= new AdminCommandsManagementTab();
        accountsManagementTab= new AccountsManagementTab();
        setUpUi();
        adminDashboardController = new AdminDashboardController(this);

    }

    public JButton getLogoutButton() {
        return logoutButton;
    }

    void setUpUi() {
        // Main panel setup
        this.setLayout(new BorderLayout());
        this.setBackground(new Color(248, 249, 250)); // Light gray background

        // Modern color scheme
        Color primaryColor = new Color(41, 128, 185);  // Blue primary color
        Color secondaryColor = new Color(52, 152, 219); // Lighter blue
        Color accentColor = new Color(52, 107, 143);
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

        // Logo placeholder (replace with your actual logo)
        JLabel logo = new JLabel("ADMIN", SwingConstants.CENTER);
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
        }};

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setOpaque(false);
        buttonPanel.add(logoutButton);
        headerPanel.add(buttonPanel, BorderLayout.EAST);

        // Modern tabbed pane with material design
        JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setColor(getBackground());
                g2d.fillRect(0, 0, getWidth(), getHeight());
                g2d.dispose();
            }
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

        // Custom tab UI
        UIManager.put("TabbedPane.selected", primaryColor);
        UIManager.put("TabbedPane.borderHightlightColor", Color.WHITE);
        UIManager.put("TabbedPane.contentAreaColor", new Color(248, 249, 250));

        // Add modern tabs with icons (replace with your actual icons)
        addModernTab(tabbedPane, "Accounts", accountsManagementTab, UIManager.getIcon("FileView.computerIcon"));
        addModernTab(tabbedPane, "Commands", adminCommandsManagementTab, UIManager.getIcon("FileView.hardDriveIcon"));

        // Add subtle shadow below header
        headerPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 2, 0, new Color(220, 220, 220)),
                BorderFactory.createEmptyBorder(0, 30, 0, 30)
        ));

        // Add components to main panel
        this.add(headerPanel, BorderLayout.NORTH);
        this.add(tabbedPane, BorderLayout.CENTER);

        // Add subtle padding around tabs
        tabbedPane.setBorder(BorderFactory.createEmptyBorder(5, 10, 10, 10));
    }

    private void addModernTab(JTabbedPane pane, String title, JComponent component, Icon icon) {
        JPanel tabPanel = new JPanel(new BorderLayout());
        tabPanel.setOpaque(false);

        JLabel tabLabel = new JLabel(title, icon, SwingConstants.CENTER);
        tabLabel.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 14));
        tabLabel.setForeground(pane.getForeground());
        tabLabel.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        tabLabel.setHorizontalTextPosition(SwingConstants.RIGHT);
        tabLabel.setIconTextGap(10);

        tabPanel.add(tabLabel, BorderLayout.CENTER);
        pane.addTab(title, component);
        pane.setTabComponentAt(pane.getTabCount() - 1, tabPanel);
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


}