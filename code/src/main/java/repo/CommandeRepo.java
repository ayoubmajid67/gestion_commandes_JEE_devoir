package repo;

import model.Commande;
import utils.ControllersGetter;

import java.sql.*;
import java.util.ArrayList;
import java.util.Optional;

public class CommandeRepo {

    private final Connection connection;

    public CommandeRepo() {
        this.connection = ControllersGetter.dbConnexion.getConnection();
    }

    // Create a new commande
    public boolean createCommande(Commande commande) throws SQLException {
        if (connection == null) {
            throw new SQLException("Database connection is not available.");
        }


        String query = "INSERT INTO Commande (date, montant, id_Compte) VALUES ( ?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setDate(1, new java.sql.Date(commande.getDate().getTime()));
            preparedStatement.setDouble(2, commande.getmontant());
            preparedStatement.setString(3, commande.getidCompte());

            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            throw new SQLException("Error creating commande: " + e.getMessage(), e);
        }
    }

    // Get commande by ID
    public Optional<Commande> getCommandeById(String id) throws SQLException {
        if (connection == null) {
            throw new SQLException("Database connection is not available.");
        }

        String query = "SELECT * FROM Commande WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                Date date = resultSet.getDate("date");
                double montant = resultSet.getDouble("montant");
                String idCompte = resultSet.getString("id_Compte");

                Commande commande = new Commande(id, date, montant, idCompte);
                return Optional.of(commande);
            }
        } catch (SQLException e) {
            throw new SQLException("Error retrieving commande: " + e.getMessage(), e);
        }
        return Optional.empty();
    }

    // Get User commandes
    public ArrayList<Commande> getUserCommandes() throws SQLException {
        ArrayList<Commande> commandes = new ArrayList<>();

        if (connection == null) {
            throw new SQLException("Database connection is not available.");
        }

        String query = "SELECT id, DATE_FORMAT(date, '%d/%m/%Y') as date, montant, id_Compte  from commande where id_Compte = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            // Set the parameter value

            statement.setString(1, ControllersGetter.currentAccountSession.getIdAccountToken());

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {

                    String id = resultSet.getString("id");
                    String date = resultSet.getString("date");
                    double montant = resultSet.getDouble("montant");
                    String idCompte = resultSet.getString("id_Compte");

                    commandes.add(new Commande(id, date, montant, idCompte));
                }
            }
        } catch (SQLException e) {
            throw new SQLException("Error retrieving commandes: " + e.getMessage(), e);
        }

        return commandes;
    }

    // Get all commandes
    public ArrayList<Commande> getAllCommandes() throws SQLException {
        ArrayList<Commande> commandes = new ArrayList<>();

        if (connection == null) {
            throw new SQLException("Database connection is not available.");
        }

        String query = "SELECT id, DATE_FORMAT(date, '%d/%m/%Y') as date, montant, id_Compte FROM Commande";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                String id = resultSet.getString("id");
                String date = resultSet.getString("date");
                double montant = resultSet.getDouble("montant");
                String idCompte = resultSet.getString("id_Compte");

                commandes.add(new Commande(id, date, montant, idCompte));
            }
        } catch (SQLException e) {
            throw new SQLException("Error retrieving commandes: " + e.getMessage(), e);
        }

        return commandes;
    }

    // Get commandes by account ID
    public ArrayList<Commande> getCommandesByCompte(String idCompte) throws SQLException {
        ArrayList<Commande> commandes = new ArrayList<>();

        if (connection == null) {
            throw new SQLException("Database connection is not available.");
        }

        String query = "SELECT * FROM Commande WHERE idCompte = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, idCompte);

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String id = resultSet.getString("id");
                String date = resultSet.getString("date");
                double montant = resultSet.getDouble("montant");

                commandes.add(new Commande(id, date, montant, idCompte));
            }
        } catch (SQLException e) {
            throw new SQLException("Error retrieving commandes by compte: " + e.getMessage(), e);
        }

        return commandes;
    }

    // Update a commande
    public boolean updateCommande(Commande commande) throws SQLException {
        if (connection == null) {
            throw new SQLException("Database connection is not available.");
        }

        String query = "UPDATE Commande SET date = ?, montant = ?, idCompte = ? WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setDate(1, new java.sql.Date(commande.getDate().getTime()));
            preparedStatement.setDouble(2, commande.getmontant());
            preparedStatement.setString(3, commande.getidCompte());
            preparedStatement.setString(4, commande.getId());

            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            throw new SQLException("Error updating commande: " + e.getMessage(), e);
        }
    }

    // Delete a commande
    public boolean deleteCommande(String id) throws SQLException {
        if (connection == null) {
            throw new SQLException("Database connection is not available.");
        }

        String query = "DELETE FROM Commande WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, id);

            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            throw new SQLException("Error deleting commande: " + e.getMessage(), e);
        }
    }

    public void editCommande(String id, Commande updatedCommande) throws Exception {
        String query = "UPDATE  commande  SET date = ?, montant = ?, id_Compte = ? WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            // Convert java.util.Date to java.sql.Date
            java.sql.Date sqlDate = new java.sql.Date(updatedCommande.getDate().getTime());
            preparedStatement.setDate(1, sqlDate);
            preparedStatement.setDouble(2, updatedCommande.getmontant());
            preparedStatement.setString(3, updatedCommande.getidCompte());
            preparedStatement.setString(4, id);
            int rowsAffected = preparedStatement.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Error editing commande: " + e.getMessage());
            throw new Exception("Error edition commande :" + e.getMessage());

        }
    }

    // Get commandes within a date range
    public ArrayList<Commande> getCommandesByDateRange(Date startDate, Date endDate) throws SQLException {
        ArrayList<Commande> commandes = new ArrayList<>();

        if (connection == null) {
            throw new SQLException("Database connection is not available.");
        }

        String query = "SELECT * FROM Commande WHERE date BETWEEN ? AND ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setDate(1, new java.sql.Date(startDate.getTime()));
            preparedStatement.setDate(2, new java.sql.Date(endDate.getTime()));

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String id = resultSet.getString("id");
                String date = resultSet.getString("date");
                double montant = resultSet.getDouble("montant");
                String idCompte = resultSet.getString("id_Compte");

                commandes.add(new Commande(id, date, montant, idCompte));
            }
        } catch (SQLException e) {
            throw new SQLException("Error retrieving commandes by date range: " + e.getMessage(), e);
        }

        return commandes;
    }

    // Non-throwing version for getAllCommandes
    public ArrayList<Commande> getAllCommandesNoExp() {
        try {
            return getAllCommandes();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error retrieving commandes: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    public ArrayList<Commande> getAllUserCommandesNoExp() {
        try {
            return getUserCommandes();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error retrieving commandes: " + e.getMessage());
            return new ArrayList<>();
        }
    }
}