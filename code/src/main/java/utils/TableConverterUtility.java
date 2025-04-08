package utils;


import model.Accounts.Account;

import java.lang.reflect.Field;
import java.util.List;

public class TableConverterUtility {

    /**
     * Converts a list of Account objects into a 2D array for use in a JTable.
     *
     * @param data        The list of Account objects.
     * @param columnNames The column names for the table.
     * @return A 2D array containing the account data.
     */
    public static Object[][] convertAccountsToTableData(List<Account> data, String[] columnNames) {
        // Convert ArrayList<Account> to Object[][]
        Object[][] tableData = new Object[data.size()][columnNames.length];
        for (int i = 0; i < data.size(); i++) {
            Account account = data.get(i);
            tableData[i][0] = account.getIdAccount();
            tableData[i][1] = account.getFirstName();
            tableData[i][2] = account.getLastName();
            tableData[i][3] = account.getPhoneNumber();
            tableData[i][4] = account.getEmail();
            tableData[i][5] = account.getPassword();
            tableData[i][6] = account.getDomain();
            tableData[i][7] = ""; // Placeholder for "Actions" (e.g., buttons)
        }

        return tableData;
    }
    public static Object[][] convertToTableData(List<?> data, String[] columnNames) {
        if (data == null || data.isEmpty() || columnNames == null || columnNames.length == 0) {
            return new Object[0][0]; // Return empty array if input is invalid
        }

        // Create the 2D array to hold the table data
        Object[][] tableData = new Object[data.size()][columnNames.length];

        // Iterate over each object in the list
        for (int i = 0; i < data.size(); i++) {
            Object obj = data.get(i);
            // Iterate over each column name
            for (int j = 0; j < columnNames.length; j++) {
                try {
                    // Convert the column name to start with a lowercase letter
                    String fieldName = columnNames[j].substring(0, 1).toLowerCase() + columnNames[j].substring(1);

                    // Use reflection to get the field value
                    Field field = obj.getClass().getDeclaredField(fieldName);
                    field.setAccessible(true); // Allow access to private fields
                    tableData[i][j] = field.get(obj); // Get the value of the field
                } catch (NoSuchFieldException | IllegalAccessException e) {
                    // If the field doesn't exist or cannot be accessed, set the value to an empty string
                    tableData[i][j] = "";
                }
            }
        }

        return tableData;
    }
}