package repo;

import model.Compte;
import utils.ControllersGetter;

import java.sql.*;
import java.util.ArrayList;
import java.util.Optional;

public class AccountsRepo {

    private final Connection connection;

    public AccountsRepo() {

        this.connection = ControllersGetter.dbConnexion.getConnection();

    }

    // Method to retrieve a Compte by email and password
    public Optional<Compte> getCompte(String email, String password) throws SQLException {
        if (connection == null) {
            throw new SQLException("Database connection is not available.");
        }

        String query = "SELECT * FROM Compte WHERE email = ? AND password = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, password);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                String id = resultSet.getString("id");
                String nom = resultSet.getString("nom");
                String compteType = resultSet.getString("compteType");

                Compte compte = new Compte(id, nom, email, password, compteType);
                return Optional.of(compte);
            }
        } catch (SQLException e) {

            throw new SQLException("An error occurred while retrieving account: " + e.getMessage(), e);


        }

        return Optional.empty();
    }


    public Optional<Compte> getCompteById(String id) throws SQLException {
        if (connection == null) {
            throw new SQLException("Database connection is not available.");
        }

        String query = "SELECT * FROM Compte WHERE id = ? ";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                String nom = resultSet.getString("nom");
                String email = resultSet.getString("email");
                String password = resultSet.getString("password");
                String compteType = resultSet.getString("compteType");

                Compte compte = new Compte(id, nom, email, password, compteType);
                return Optional.of(compte);
            }
        } catch (SQLException e) {
            throw new SQLException("An error occurred while retrieving account: " + e.getMessage(), e);
        }

        return Optional.empty();

    }

    // Method to retrieve a User (non-admin) by email and password
    public Optional<Compte> getUser(String email, String password) throws SQLException {
        Optional<Compte> compteOptional = getCompte(email, password);

        if (compteOptional.isPresent()) {
            Compte compte = compteOptional.get();

            if (!compte.isAdmin()) {
                return Optional.of(compte);
            }
        }

        throw new SQLException("No user account found with the provided credentials.");
    }

    // Method to retrieve an Admin by email and password
    public Optional<Compte> getAdmin(String email, String password) throws SQLException {
        Optional<Compte> compteOptional = getCompte(email, password);

        if (compteOptional.isPresent()) {
            Compte compte = compteOptional.get();

            if (compte.isAdmin()) {
                return Optional.of(compte);
            }
        }

        throw new SQLException("No admin account found with the provided credentials.");
    }

    // Method to retrieve all accounts from the database
    public ArrayList<Compte> getAccounts() throws SQLException {
        ArrayList<Compte> accounts = new ArrayList<>();

        if (connection == null) {
            throw new SQLException("Database connection is not available.");
        }

        String query = "SELECT * FROM Compte";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                String id = resultSet.getString("id");
                String nom = resultSet.getString("nom");
                String email = resultSet.getString("email");
                String password = resultSet.getString("password");
                String compteType = resultSet.getString("compteType");

                Compte account = new Compte(id, nom, email, password, compteType);
                accounts.add(account);
            }
        } catch (SQLException e) {
            throw new SQLException("An error occurred while retrieving accounts: " + e.getMessage(), e);
        }

        return accounts;
    }


    public ArrayList<Compte> getAccountsNoExp() {
        try {
            return getAccounts();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("An error occurred while retrieving accounts: " + e.getMessage());
            System.exit(1);

        }

        return null;
    }

    public boolean addAccount(Compte compte) throws SQLException {
        if (connection == null) {
            throw new SQLException("Database connection is not available.");
        }

        String query = "INSERT INTO Compte (nom, email, password, compteType) VALUES (?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, compte.getNom());
            preparedStatement.setString(2, compte.getEmail());
            preparedStatement.setString(3, compte.getPassword());
            preparedStatement.setString(4, compte.getCompteType());

            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0; // Return true if insertion was successful
        } catch (SQLException e) {
            throw new SQLException("An error occurred while adding account: " + e.getMessage(), e);
        }
    }


    public boolean addUser(Compte user) throws SQLException {
        return addAccount(user);
    }


    public boolean addAdmin(Compte admin) throws SQLException {
        return addAccount(admin);
    }

    // Method to update an account in the database
    public boolean updateAccount(String accountId, Compte updatedAccount) throws SQLException {
        if (connection == null) {
            throw new SQLException("Database connection is not available.");
        }

        String query = "UPDATE Compte SET nom = ?, email = ?, password = ?, compteType = ? WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, updatedAccount.getNom());
            preparedStatement.setString(2, updatedAccount.getEmail());
            preparedStatement.setString(3, updatedAccount.getPassword());
            preparedStatement.setString(4, updatedAccount.getCompteType());
            preparedStatement.setString(5, accountId);

            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0; // Return true if update was successful
        } catch (SQLException e) {
            throw new SQLException("An error occurred while updating account: " + e.getMessage(), e);
        }
    }

    // Method to delete an account from the database
    public boolean deleteAccount(String accountId) throws SQLException {
        if (connection == null) {
            throw new SQLException("Database connection is not available.");
        }

        System.out.println("\n the account id " + accountId);

        String query = "DELETE FROM Compte WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, accountId);

            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0; // Return true if deletion was successful
        } catch (SQLException e) {
            System.out.println("An error occurred while deleting account: " + e.getMessage());
            throw new SQLException("An error occurred while deleting account: " + e.getMessage(), e);
        }
    }

    // Method to edit an account in the database
    public boolean editAccount(String accountId, Compte updatedAccount) throws SQLException {
        if (connection == null) {
            throw new SQLException("Database connection is not available.");
        }

        String query = "UPDATE Compte SET nom = ?, email = ?, password = ?, compteType = ? WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, updatedAccount.getNom());
            preparedStatement.setString(2, updatedAccount.getEmail());
            preparedStatement.setString(3, updatedAccount.getPassword());
            preparedStatement.setString(4, updatedAccount.getCompteType());
            preparedStatement.setString(5, accountId);

            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0; // Return true if edit was successful
        } catch (SQLException e) {
            throw new SQLException("An error occurred while editing account: " + e.getMessage(), e);
        }
    }
}