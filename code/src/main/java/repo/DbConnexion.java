package repo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnexion {

    private Connection connection;

    public DbConnexion() {
        try {

            Class.forName("com.mysql.cj.jdbc.Driver");

            connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/project1_java_4iir_s2",
                    "root",
                    "majid077179_mysql"
            );

            System.out.println("Database connection established successfully.");

        } catch (ClassNotFoundException | SQLException e) {
            // Log the error message
            System.err.println("Error establishing database connection: " + e.getMessage());
            e.printStackTrace();

            // Exit the program if the connection fails
            System.exit(1);
        }
    }

    public Connection getConnection() {
        return connection;
    }

    // Method to close the database connection
    public void close() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("Database connection closed successfully.");
            }
        } catch (SQLException e) {
            System.err.println("Error closing database connection: " + e.getMessage());
            e.printStackTrace();
        }
    }

}