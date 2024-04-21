package Services;

import Entities.Capital;
import Entities.Transaction;
import Utils.DataSource;

import java.sql.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;


public class ServiceTransaction implements Iservice<Transaction> {
    Connection cnx = DataSource.getInstance().getCnx();
    @Override
    public void ajouter(Transaction transaction) throws SQLException {
        // Retrieve current capital data from the database
        Capital currentCapital = retrieveCurrentCapitalFromDatabase();

        // Ensure currentCapital is not null before proceeding
        if (currentCapital == null) {
            System.out.println("Error: Unable to retrieve current capital data from the database.");
            return;
        }

        // Compare currentCapital data with the transaction details
        float totalAmount = transaction.getQuantity() * transaction.getCost();
        String type = transaction.getType();
        if ("Salary".equals(type)){
            if(currentCapital.getSalary() < totalAmount) {
                System.out.println("You cannot proceed. Insufficient salary.");
                return;
            } else {
                currentCapital.setSalary(currentCapital.getSalary() - totalAmount);
                currentCapital.setBig_capital(currentCapital.getBig_capital() - totalAmount);
            }
        } else if ("Expenses".equals(type)){
            if(currentCapital.getExepenses() < totalAmount) {
                System.out.println("You cannot proceed. Insufficient expenses.");
                return;
            } else {
                currentCapital.setExepenses(currentCapital.getExepenses() - totalAmount);
                currentCapital.setBig_capital(currentCapital.getBig_capital() - totalAmount);
            }
        } else {
            currentCapital.setProfits(currentCapital.getProfits() + totalAmount);
            currentCapital.setBig_capital(currentCapital.getBig_capital() + totalAmount);
        }

        // Update capital entity in the database
        String updateCapitalQuery = "UPDATE capital SET salary=?, expenses=?, profits=?, big_capital=? WHERE id=1";
        try (PreparedStatement updateStatement = cnx.prepareStatement(updateCapitalQuery)) {
            updateStatement.setFloat(1, currentCapital.getSalary());
            updateStatement.setFloat(2, currentCapital.getExepenses());
            updateStatement.setFloat(3, currentCapital.getProfits());
            updateStatement.setFloat(4, currentCapital.getBig_capital());

            int rowsAffected = updateStatement.executeUpdate();
            if (rowsAffected <= 0) {
                System.out.println("Failed to update capital entity.");
                return;
            }
        } catch (SQLException e) {
            System.err.println("Error while updating capital entity: " + e.getMessage());
            throw e;
        }

        // Insert the transaction into the database
        String insertTransactionQuery = "INSERT INTO expenses (Type, Quantity, Description, cost, totalamount, archived) VALUES (?, ?, ?, ?, ?, false)";
        try (PreparedStatement ps = cnx.prepareStatement(insertTransactionQuery)) {
            ps.setString(1, transaction.getType());
            ps.setInt(2, transaction.getQuantity());
            ps.setString(3, transaction.getDescription());
            ps.setFloat(4, transaction.getCost());
            ps.setFloat(5, totalAmount);

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Transaction added successfully!");
            } else {
                System.out.println("Failed to add transaction!");
            }
        } catch (SQLException e) {
            System.err.println("Error while adding transaction: " + e.getMessage());
            throw e;
        }
    }
    @Override
    public void modifier(Transaction transaction) throws SQLException {
        // Retrieve current capital data from the database
        Capital currentCapital = retrieveCurrentCapitalFromDatabase();
        // Ensure currentCapital is not null before proceeding
        if (currentCapital == null) {
            System.out.println("Error: Unable to retrieve current capital data from the database.");
            return;
        }
        // Compare currentCapital data with the transaction details
        float totalAmount = transaction.getQuantity() * transaction.getCost();
        String type = transaction.getType();
        if ("Salary".equals(type)){
            if(currentCapital.getSalary() < totalAmount) {
                System.out.println("You cannot proceed. Insufficient salary.");
                return;
            } else {
                currentCapital.setSalary(currentCapital.getSalary() - totalAmount);
                currentCapital.setBig_capital(currentCapital.getBig_capital() - totalAmount);
            }
        } else if ("Expenses".equals(type)){
            if(currentCapital.getExepenses() < totalAmount) {
                System.out.println("You cannot proceed. Insufficient expenses.");
                return;
            } else {
                currentCapital.setExepenses(currentCapital.getExepenses() - totalAmount);
                currentCapital.setBig_capital(currentCapital.getBig_capital() - totalAmount);
            }
        } else {
            currentCapital.setProfits(currentCapital.getProfits() + totalAmount);
            currentCapital.setBig_capital(currentCapital.getBig_capital() + totalAmount);
        }
        // Update capital entity in the database
        String updateCapitalQuery = "UPDATE capital SET salary=?, expenses=?, profits=?, big_capital=? WHERE id=1";
        try (PreparedStatement updateStatement = cnx.prepareStatement(updateCapitalQuery)) {
            updateStatement.setFloat(1, currentCapital.getSalary());
            updateStatement.setFloat(2, currentCapital.getExepenses());
            updateStatement.setFloat(3, currentCapital.getProfits());
            updateStatement.setFloat(4, currentCapital.getBig_capital());
            int rowsAffected = updateStatement.executeUpdate();
            if (rowsAffected <= 0) {
                System.out.println("Failed to update capital entity.");
                return;
            }
        } catch (SQLException e) {
            System.err.println("Error while updating capital entity: " + e.getMessage());
            throw e;
        }
        // querry for the updating
        String req = "UPDATE  expenses  SET type=? , Quantity=?, Description=?, cost=?, totalamount=? , archived=false  WHERE id=?";
        System.out.println("the id = "+ transaction.getId());
        try (PreparedStatement ps = cnx.prepareStatement(req)) {
            System.out.println(" the type is = "+transaction.getType());
            ps.setString(1, transaction.getType());
            ps.setInt(2, transaction.getQuantity());
            ps.setString(3, transaction.getDescription());
            ps.setFloat(4, transaction.getCost());
            ps.setFloat(5, totalAmount);
            ps.setInt(6, transaction.getId());
            System.out.println("i am AFTERRRRRR  ");
            System.out.println(" the type is = "+transaction.getType());
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Transaction updated successfully!");
            } else {
                System.out.println("Failed to update transaction!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }
    @Override
    public void supprimer(int id) throws SQLException {
            String req = "DELETE FROM `expenses` WHERE `id` = ?";
            try (PreparedStatement ps = cnx.prepareStatement(req)) {
                ps.setInt(1, id);

                int rowsAffected = ps.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("Transaction with ID " + id + " deleted successfully!");
                } else {
                    System.out.println("No transaction found with ID " + id);
                    // Vous pouvez jeter une exception ici ou retourner false pour indiquer l'échec.
                }
            } catch (SQLException e) {
                // Gérer correctement l'exception (par exemple, la journaliser, jeter une exception personnalisée).
                System.err.println("Error while deleting transaction: " + e.getMessage());
                throw e;
            }
    }

    @Override
    public void archiver(int id) throws SQLException {
        String req = "UPDATE `expenses` SET `archived` = true WHERE `id` = ?";
        try (PreparedStatement ps = cnx.prepareStatement(req)) {
            ps.setInt(1, id);

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Transaction with ID " + id + " archived successfully!");
            } else {
                System.out.println("No transaction found with ID " + id);
                // Vous pouvez jeter une exception ici ou retourner false pour indiquer l'échec.
            }
        } catch (SQLException e) {
            // Gérer correctement l'exception (par exemple, la journaliser, jeter une exception personnalisée).
            System.err.println("Error while archiving transaction: " + e.getMessage());
            throw e;
        }

    }

    @Override
    public Transaction getOneById(int id) {
        Transaction transaction = null;
        String query = "SELECT * FROM expenses WHERE id = ?";
        try {
            PreparedStatement ps = cnx.prepareStatement(query);
            ps.setInt(1, id);
            ResultSet res = ps.executeQuery();
            if (res.next()) {
                // Retrieve values from ResultSet
                int transactionId = res.getInt("id");
                String type = res.getString("type");
                int quantity = res.getInt("Quantity");
                float cost = res.getFloat("cost");
                String description = res.getString("Description");

                // Create Transaction object with retrieved values
                transaction = new Transaction(transactionId, type, description, quantity, cost);
            }
        } catch (SQLException e) {
            System.err.println("Error while fetching transaction by ID: " + e.getMessage());

        }
        return transaction;
}

    @Override
    public Set<Transaction> getAll()     {
        Set<Transaction> Trans = new HashSet<>();

        // dispaying only not archived data

        String req = " SELECT * FROM expenses WHERE archived=false";
        try {
            Statement st = cnx.createStatement();
            ResultSet res = st.executeQuery(req);
            while (res.next()){
                int id = res.getInt("id");
                java.sql.Date date = res.getDate("date");
                String type = res.getString("type");
                int Quantity = res.getInt("Quantity");
                float cost = res.getFloat("cost");
                String description  = res.getString("Description");
                float totalAmount = res.getFloat("totalamount");
                Transaction transaction = new Transaction (id,date,type,description,Quantity,cost,totalAmount);
                Trans.add(transaction);
            }
        } catch (SQLException e) {
           e.printStackTrace();
        }
        return Trans;
    }
    // Method to retrieve current capital data from the database
    public Capital retrieveCurrentCapitalFromDatabase() {
        String query = "SELECT salary, expenses, profits, big_capital FROM capital WHERE id = 1";
        Capital capitalEntity = null;

        try (Statement statement = cnx.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            if (resultSet.next()) {
                capitalEntity = new Capital();
                capitalEntity.setSalary(resultSet.getFloat("salary"));
                capitalEntity.setExepenses(resultSet.getFloat("expenses"));
                capitalEntity.setProfits(resultSet.getFloat("profits"));
                capitalEntity.setBig_capital(resultSet.getFloat("big_capital"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return capitalEntity;
    }

}
