package utils;


import controller.businessControllers.account.AccountSessionHandler;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;


public class PageSwitcher {
    static private JPanel mainPanel;
    static private CardLayout cardLayout;
    static private Map<String, JPanel> pages; // Map to store pages by name
    private final JFrame frame;


    public PageSwitcher() {
        // Initialize CardLayout and main panel
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);
        pages = new HashMap<>();

        AccountSessionHandler.loadCurrentAccountSession();


        frame = new JFrame(" orders management");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 600);


        // Add pages
        addPage("login", PagesGetter.LoginPage);
        addPage("adminDashboard", PagesGetter.AdminDashBoardPage);
        addPage("userDashboard", PagesGetter.userDashboardPage);


        // Add the main panel to the frame
        frame.add(mainPanel);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        handleFirstPageLoading();
    }

    // Method to switch between pages
    static public void switchPage(String pageName) {
        if (pages.containsKey(pageName)) {
            cardLayout.show(mainPanel, pageName);

        } else {
            System.out.println("Page not found: " + pageName);
        }
    }

    public static void main(String[] args) {
        PageSwitcher pageSwitcher = new PageSwitcher();
//        SwingUtilities.invokeLater(PageSwitcher::new);
    }

    private void handleFirstPageLoading() {
        // Set the initial page
        if (ControllersGetter.currentAccountSession == null) {
            switchPage("login");
        } else if (ControllersGetter.currentAccountSession.isAdmin()) {

            switchPage("adminDashboard");
            PagesGetter.AdminDashBoardPage.reloadTaps();
        } else {
            switchPage("userDashboard");
            PagesGetter.userDashboardPage.reloadTabs();
        }

    }

    // Method to add a page
    private void addPage(String name, JPanel page) {
        pages.put(name, page);
        mainPanel.add(page, name);
    }
}
