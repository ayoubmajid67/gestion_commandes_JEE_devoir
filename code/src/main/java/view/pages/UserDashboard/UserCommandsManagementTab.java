package view.pages.UserDashboard;

import controller.uiControllers.UserDashboard.Tabs.UserCommandsManagementTabController;
import model.Commande;
import utils.TableConverterUtility;
import utils.ControllersGetter;
import view.components.ButtonRenderer;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class UserCommandsManagementTab extends JPanel {

    private JButton createButton = new JButton("Ajouter Nouvelle Commande");
    private ButtonRenderer buttonRenderer = new ButtonRenderer();
    private DefaultTableModel model;
    private JTable commandTable;
    private List<Commande> data;


    Color primaryColor = new Color(41, 128, 185);  // Blue primary color


    private UserCommandsManagementTabController commandsManagementTabController;
    private static String[] columnNamesCreateEdit = {"Date", "Montant"};

    public UserCommandsManagementTab() {

        commandsManagementTabController = new UserCommandsManagementTabController(this);
        setUpUi();
        data=ControllersGetter.CommandeRepo.getAllUserCommandesNoExp();
        refreshTable();


    }

    public static String[] getColumnNamesCreateEdit() {
        return columnNamesCreateEdit;
    }

    public JButton getCreateButton() {
        return createButton;
    }

    public JButton getEditButton() {
        return buttonRenderer.getEditButton();
    }

    public JButton getDeleteButton() {
        return buttonRenderer.getDeleteButton();
    }

    private void setUpUi() {
        // Set the layout manager for the panel
        this.setLayout(new BorderLayout());
        this.setBackground(new Color(236, 240, 241));
        this.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Create a button panel at the top
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        buttonPanel.setBackground(new Color(236, 240, 241));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));

        // Add "Create" button
        createButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        createButton.setBackground(new Color(52, 152, 219));
        createButton.setForeground(Color.WHITE);
        createButton.setFocusPainted(false);
        createButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        createButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        buttonPanel.add(createButton);

        // Add the button panel to the top of the tab
        this.add(buttonPanel, BorderLayout.NORTH);

        // Define column names
        String[] columnNames = {"ID", "Date", "Montant", "IdCompte", "Actions"};

        Object[][] tableData = TableConverterUtility.convertToTableData(data, columnNames);
        // Create table model
        model = new DefaultTableModel(tableData, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 4; // Only "Actions" column is editable
            }
        };

        commandTable = new JTable(model);
        commandTable.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        commandTable.setRowHeight(30);
        commandTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        commandTable.getTableHeader().setBackground(primaryColor);
        commandTable.getTableHeader().setForeground(Color.WHITE);
        commandTable.setFillsViewportHeight(true);

        // Add action buttons to each row
        TableColumn actionsColumn = commandTable.getColumnModel().getColumn(4);
        actionsColumn.setCellRenderer(buttonRenderer);
        actionsColumn.setCellEditor(new ButtonEditor(new JCheckBox(), commandTable,
                commandsManagementTabController.getIButtonEditorEventsHandler()));

        // Add table to scroll pane
        JScrollPane scrollPane = new JScrollPane(commandTable);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        this.add(scrollPane, BorderLayout.CENTER);
    }

    public void refreshTable() {


        System.out.println("hi");
        data = ControllersGetter.CommandeRepo.getAllUserCommandesNoExp();
        model.setRowCount(0);


        for (Commande commande : data) {
            Object[] rowData = {
                    commande.getId(),
                    commande.dateToString(),
                    commande.getmontant(),
                    commande.getidCompte(),
                    "Actions"
            };
            model.addRow(rowData);
        }

        // Refresh action buttons
        TableColumn actionsColumn = commandTable.getColumnModel().getColumn(4);
        commandTable.removeColumn(actionsColumn);

        actionsColumn = new TableColumn(4);
        actionsColumn.setHeaderValue("Actions");
        actionsColumn.setCellRenderer(new ButtonRenderer());
        actionsColumn.setCellEditor(new ButtonEditor(new JCheckBox(), commandTable,
                commandsManagementTabController.getIButtonEditorEventsHandler()));

        commandTable.addColumn(actionsColumn);
        commandTable.repaint();
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Commands Management");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1024, 768);
        frame.setLocationRelativeTo(null);

        UserCommandsManagementTab commandsTab = new UserCommandsManagementTab();
        frame.add(commandsTab);

        frame.setVisible(true);
    }
}