package view.components;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

public class ButtonRenderer extends JPanel implements TableCellRenderer {
    private JButton editButton ;
    private JButton deleteButton ;


    public JButton getEditButton() {
        return editButton;
    }

    public JButton getDeleteButton() {
        return deleteButton;
    }

    public ButtonRenderer() {
        setLayout(new FlowLayout(FlowLayout.CENTER, 5, 0)); // Add spacing between buttons
        setBackground(new Color(236, 240, 241)); // Light gray background

        // Edit button
        editButton = new JButton("Edit");
        editButton.setFont(new Font("Segoe UI", Font.BOLD, 12));
        editButton.setBackground(new Color(52, 152, 219)); // Blue color
        editButton.setForeground(Color.WHITE);
        editButton.setFocusPainted(false);
        editButton.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

        // Delete button
        deleteButton = new JButton("Delete");
        deleteButton.setFont(new Font("Segoe UI", Font.BOLD, 12));
        deleteButton.setBackground(new Color(128, 108, 166)); // Red color
        deleteButton.setForeground(Color.WHITE);
        deleteButton.setFocusPainted(false);
        deleteButton.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

        // Add buttons to the panel
        add(editButton);
        add(deleteButton);
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        return this;
    }
}