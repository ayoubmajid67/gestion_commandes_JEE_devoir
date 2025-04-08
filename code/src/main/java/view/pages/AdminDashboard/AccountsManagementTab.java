package view.pages.AdminDashboard;

import controller.uiControllers.adminDashboard.Tabs.AccountsManagementTabController;
import model.Compte;
import utils.ControllersGetter;
import utils.TableConverterUtility;
import view.components.ButtonRenderer;
import view.pages.UserDashboard.ButtonEditor;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.util.ArrayList;


public class AccountsManagementTab extends JPanel {

    private static final String[] columnNamesCreateEdit = {"Nom", "Email", "Password", "CompteType"};
    Color primaryColor = new Color(41, 128, 185);  // Blue primary color
    private final JButton createButton = new JButton("Create New Compte");
    private ButtonRenderer buttonRenderer = new ButtonRenderer();
    private DefaultTableModel model;
    private JTable accountTable;
    private ArrayList<Compte> data = ControllersGetter.accountsRepo.getAccountsNoExp();
    private final AccountsManagementTabController accountsManagementTabController;


    public AccountsManagementTab() {
        accountsManagementTabController = new AccountsManagementTabController(this);
        setUpUi();
        buttonRenderer = new ButtonRenderer();


    }

    public static String[] getColumnNamesCreateEdit() {
        return columnNamesCreateEdit;
    }

    public JButton getCreateButton() {
        return createButton;
    }

    public JButton getEditeButton() {
        return buttonRenderer.getEditButton();
    }

    public JButton getDeleteButton() {
        return buttonRenderer.getDeleteButton();
    }


    private void setUpUi() {
        // Set the layout manager for the panel
        this.setLayout(new BorderLayout());
        this.setBackground(new Color(236, 240, 241)); // Light gray background
        this.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Create a button panel at the top
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        buttonPanel.setBackground(new Color(236, 240, 241)); // Light gray background
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));

        // Add "Create" button
        createButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        createButton.setBackground(new Color(52, 152, 219)); // Blue color
        createButton.setForeground(Color.WHITE);
        createButton.setFocusPainted(false);
        createButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        createButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        buttonPanel.add(createButton);

        // Add the button panel to the top of the tab
        this.add(buttonPanel, BorderLayout.NORTH);

        // Define column names
        String[] columnNames = {"Id", "Nom", "Email", "Password", "CompteType", "Actions"};

        Object[][] tableData = TableConverterUtility.convertToTableData(data, columnNames);


        // Create and return the table model
        model = new DefaultTableModel(tableData, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                // Only the "Actions" column is editable
                return column == 5;
            }
        };

        accountTable = new JTable(model);
        accountTable.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        accountTable.setRowHeight(30);
        accountTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        accountTable.getTableHeader().setBackground(primaryColor); // Dark blue header
        accountTable.getTableHeader().setForeground(Color.WHITE);
        accountTable.setFillsViewportHeight(true);

        // Add action buttons (Edit and Delete) to each row
        TableColumn actionsColumn = accountTable.getColumnModel().getColumn(5);
        actionsColumn.setCellRenderer(buttonRenderer);

        actionsColumn.setCellEditor(new ButtonEditor(new JCheckBox(), accountTable, accountsManagementTabController.getIButtonEditorEventsHandler()));

        // Add the table to a scroll pane
        JScrollPane scrollPane = new JScrollPane(accountTable);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        this.add(scrollPane, BorderLayout.CENTER);
    }

    public void refreshTable() {


        data = ControllersGetter.accountsRepo.getAccountsNoExp();
        // Clear the existing table data
        model.setRowCount(0);

        // Add the new data to the table
        for (Compte account : data) {
            Object[] rowData = {
                    account.getId(),
                    account.getNom(),
                    account.getEmail(),
                    account.getPassword(),
                    account.getCompteType(),
                    "Actions"
            };
            model.addRow(rowData);


        }

        // Remove the "Actions" column
        TableColumn actionsColumn = accountTable.getColumnModel().getColumn(5);
        accountTable.removeColumn(actionsColumn);

        // Recreate the "Actions" column with a new ButtonRenderer and ButtonEditor
        actionsColumn = new TableColumn(5);
        actionsColumn.setHeaderValue("Actions");
        actionsColumn.setCellRenderer(new ButtonRenderer());
        actionsColumn.setCellEditor(new ButtonEditor(new JCheckBox(), accountTable, accountsManagementTabController.getIButtonEditorEventsHandler()));

        // Re-add the "Actions" column to the table
        accountTable.addColumn(actionsColumn);


        // Repaint the table to reflect the changes
        accountTable.repaint();
    }

}