// AdminCommandsManagementTab.java
package view.pages.AdminDashboard;

import controller.uiControllers.adminDashboard.Tabs.AdminCommandsManagementTabController;
import model.Commande;
import utils.ControllersGetter;
import utils.TableConverterUtility;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class AdminCommandsManagementTab extends JPanel {
    private static final String[] columnNamesViewOnly = {"ID", "Date", "Montant", "IdCompte"};
    Color primaryColor = new Color(41, 128, 185);  // Blue primary color
    DefaultTableModel model;
    JTable CommandeTable;
    private List<Commande> data = ControllersGetter.CommandeRepo.getAllCommandesNoExp();
    private final AdminCommandsManagementTabController commandsManagementTabController;

    public AdminCommandsManagementTab() {
        commandsManagementTabController = new AdminCommandsManagementTabController(this);
        setUpUi();
        refreshTable();
    }

    public static String[] getColumnNamesViewOnly() {
        return columnNamesViewOnly;
    }

    private void setUpUi() {
        // Set the layout manager for the panel
        this.setLayout(new BorderLayout());
        this.setBackground(new Color(236, 240, 241));
        this.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Define column names
        String[] columnNames = columnNamesViewOnly;

        Object[][] tableData = TableConverterUtility.convertToTableData(data, columnNames);

        // Create and return the table model
        model = new DefaultTableModel(tableData, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                // Make all cells non-editable
                return false;
            }
        };

        CommandeTable = new JTable(model);
        CommandeTable.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        CommandeTable.setRowHeight(30);
        CommandeTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        CommandeTable.getTableHeader().setBackground(primaryColor);
        CommandeTable.getTableHeader().setForeground(Color.WHITE);
        CommandeTable.setFillsViewportHeight(true);

        // Add the table to a scroll pane
        JScrollPane scrollPane = new JScrollPane(CommandeTable);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        this.add(scrollPane, BorderLayout.CENTER);
    }

    public void refreshTable() {
        // Fetch the latest data
        data = ControllersGetter.CommandeRepo.getAllCommandesNoExp();

        // Clear the existing table data
        model.setRowCount(0);

        // Add the new data to the table
        for (Commande Commande : data) {
            Object[] rowData = {
                    Commande.getId(),
                    Commande.getFormattedDate(),
                    Commande.getmontant(),
                    Commande.getidCompte()
            };
            model.addRow(rowData);
        }

        // Repaint the table to reflect the changes
        CommandeTable.repaint();
    }
}