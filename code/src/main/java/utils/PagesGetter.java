package utils;

import view.pages.AdminDashboard.AdminDashboard;
import view.pages.LoginPage;
import view.pages.UserDashboard.UserDashboard;

import javax.swing.*;


public class PagesGetter {
    static public JPanel LoginPage = new LoginPage();
    static public AdminDashboard AdminDashBoardPage = new AdminDashboard();
    static public UserDashboard userDashboardPage = new UserDashboard();

}
