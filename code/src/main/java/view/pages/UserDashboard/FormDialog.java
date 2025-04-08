package view.pages.UserDashboard;

import controller.uiControllers.FormDialogController;
import utils.interfaces.IFormDialogEventHandler;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class FormDialog extends JDialog {
    Color secondaryColor = new Color(52, 152, 219); // Lighter blue
    String id;
    private final JButton saveButton = new JButton("Save");
    private final JButton cancelButton = new JButton("Cancel");
    private final FormDialogController formDialogController;
    private final Map<String, String> formData = new HashMap<>();
    private final Map<String, JTextField> fields = new HashMap<>();
    public FormDialog(String title, String[] fieldNames, IFormDialogEventHandler iFormDialogEventHandler) {
        super((JFrame) null, title, true); // Modal dialog
        setUpUI(fieldNames, null);
        formDialogController = new FormDialogController(this, iFormDialogEventHandler);

        this.setVisible(true);
    }

    public FormDialog(String title, String[] fieldNames, Object[] data, IFormDialogEventHandler iFormDialogEventHandler, String id) {
        super((JFrame) null, title, true); // Modal dialog
        setUpUI(fieldNames, data);


        this.id = id;


        formDialogController = new FormDialogController(this, iFormDialogEventHandler);
        this.setVisible(true);
    }

    public String getId() {
        return id;
    }

    public Map<String, String> getFormData() {
        collectFormData(); // Ensure data is collected before returning

        return formData;
    }

    public void collectFormData() {
        formData.clear();
        for (Map.Entry<String, JTextField> entry : fields.entrySet()) {
            String fieldName = entry.getKey();
            String fieldValue = entry.getValue().getText();
            formData.put(fieldName, fieldValue);
        }
    }

    public JButton getSaveButton() {
        return saveButton;
    }

    public JButton getCancelButton() {
        return cancelButton;
    }

    void setUpUI(String[] fieldNames, Object[] data) {
        setSize(500, 400);
        setLocationRelativeTo(null); // Center the dialog on the screen
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(236, 240, 241)); // Light gray background

        // Create form panel
        JPanel formPanel = new JPanel(new GridLayout(fieldNames.length + 1, 2, 10, 10)); // +1 for buttons
        formPanel.setBackground(new Color(236, 240, 241));
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Add fields dynamically based on fieldNames
        for (int i = 0; i < fieldNames.length; i++) {
            String fieldName = fieldNames[i];

            // Create a label for the field
            JLabel label = new JLabel(fieldName + ":");
            label.setFont(new Font("Segoe UI", Font.BOLD, 14));
            label.setForeground(new Color(98, 78, 136)); // Dark blue text
            formPanel.add(label);

            // Create a text field for user input
            JTextField textField = new JTextField();
            textField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            textField.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(189, 195, 199), 1), // Light gray border
                    BorderFactory.createEmptyBorder(5, 10, 5, 10) // Padding
            ));

            // Set a fixed size for the text field
            textField.setPreferredSize(new Dimension(100, 30)); // Width: 200, Height: 30

            // If data is provided and has a value for this field, pre-fill the text field
            if (data != null && i < data.length && data[i] != null) {
                textField.setText(data[i].toString());
            }

            fields.put(fieldName, textField); // Store the field in the map
            formPanel.add(textField);
        }

        // Save button
        saveButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        saveButton.setBackground(new Color(52, 152, 219)); // Blue color
        saveButton.setForeground(Color.WHITE);
        saveButton.setFocusPainted(false);
        saveButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        saveButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        saveButton.setPreferredSize(new Dimension(60, 30)); // Fixed size for Save button

        // Cancel button
        cancelButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        cancelButton.setBackground(secondaryColor); // Red color
        cancelButton.setForeground(Color.WHITE);
        cancelButton.setFocusPainted(false);
        cancelButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        cancelButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        cancelButton.setPreferredSize(new Dimension(60, 30)); // Fixed size for Cancel button

        // Add buttons to the form
        formPanel.add(saveButton);
        formPanel.add(cancelButton);

        // Add the form panel to the dialog
        add(formPanel, BorderLayout.CENTER);
    }

    public boolean validateForm() {
        for (Map.Entry<String, JTextField> entry : fields.entrySet()) {
            String fieldValue = entry.getValue().getText().trim();
            if (fieldValue.isEmpty()) {
                return false;
            }
        }
        return true;
    }
}