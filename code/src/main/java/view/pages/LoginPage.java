package view.pages;

import javax.swing.*;
import java.awt.*;
import controller.uiControllers.LoginPageController;

public class LoginPage extends JPanel {
    private final LoginPageController loginPageController;
    private JButton loginButton;
    private JLabel welcomeLabel;
    private JTextField emailField;
    private JPasswordField passwordField;
    Color primaryColor = new Color(41, 128, 185);  // Blue primary color
    Color secondaryColor = new Color(52, 152, 219); // Lighter blue
    Color accentColor = new Color(231, 76, 60);     // Red for logout

    public LoginPage() {
        setUpUi();
        loginPageController = new LoginPageController(this);
    }

    void setUpUi() {
        // Set the layout manager for the panel
        this.setLayout(new BorderLayout());

        // Create a panel to hold the form elements
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridBagLayout()); // Use GridBagLayout for precise alignment
        formPanel.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40)); // Add padding
        formPanel.setBackground(new Color(245, 245, 245)); // Light gray background

        // GridBagConstraints for layout control
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Add spacing between components
        gbc.anchor = GridBagConstraints.WEST; // Align components to the left

        // Welcome label
        welcomeLabel = new JLabel("Welcome! Please log in.");
        welcomeLabel.setFont(new Font("Segoe UI", Font.BOLD, 24)); // Modern font
        welcomeLabel.setForeground(primaryColor); // Light blue text
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2; // Span across two columns
        gbc.anchor = GridBagConstraints.CENTER; // Center the welcome label
        formPanel.add(welcomeLabel, gbc);

        // Email label
        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1; // Reset gridwidth
        gbc.anchor = GridBagConstraints.WEST; // Align to the left
        formPanel.add(emailLabel, gbc);

        // Email field
        emailField = new JTextField(20);
        emailField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        emailField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)), // Light gray border
                BorderFactory.createEmptyBorder(5, 10, 5, 10) // Padding
        ));
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL; // Expand horizontally
        formPanel.add(emailField, gbc);

        // Password label
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.fill = GridBagConstraints.NONE; // Reset fill
        formPanel.add(passwordLabel, gbc);

        // Password field
        passwordField = new JPasswordField(20);
        passwordField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        passwordField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)), // Light gray border
                BorderFactory.createEmptyBorder(5, 10, 5, 10) // Padding
        ));
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL; // Expand horizontally
        formPanel.add(passwordField, gbc);

        // Login button
        loginButton = new JButton("Log In");
        loginButton.setFont(new Font("Segoe UI", Font.BOLD, 16));
        loginButton.setBackground(secondaryColor); // Light blue background
        loginButton.setForeground(Color.WHITE); // White text
        loginButton.setFocusPainted(false);
        loginButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20)); // Padding
        loginButton.setCursor(new Cursor(Cursor.HAND_CURSOR)); // Hand cursor on hover
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2; // Span across two columns
        gbc.anchor = GridBagConstraints.CENTER; // Center the button
        gbc.fill = GridBagConstraints.NONE; // Reset fill
        formPanel.add(loginButton, gbc);

        // Add the form panel to the center of the main panel
        this.add(formPanel, BorderLayout.CENTER);

        // Set a gradient background for the main panel
        this.setBackground(new Color(245, 245, 245)); // Light gray background
    }

    public JButton getLoginButton() {
        return loginButton;
    }

    public JTextField getEmailField() {
        return emailField;
    }

    public JPasswordField getPasswordField() {
        return passwordField;
    }

    public static void main(String[] args) {
        // Create a JFrame to display the LoginPage
        JFrame frame = new JFrame("Login Page");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 400);
        frame.setLocationRelativeTo(null); // Center the frame

        // Add the LoginPage panel to the frame
        LoginPage loginPage = new LoginPage();
        frame.add(loginPage);

        // Display the frame
        frame.setVisible(true);
    }
}