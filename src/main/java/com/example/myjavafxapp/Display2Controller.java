package com.example.myjavafxapp;

import Entities.Capital;
import Entities.Supplier;
import Entities.Transaction;
import Services.ServiceSupplier;
import Services.ServiceTransaction;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;

public class Display2Controller implements Initializable {

    @FXML
    private Button CancelButton;

    @FXML
    private VBox cardLayout;
    @FXML
    private Label typeylabel;

    @FXML
    private Label salaryLabel;
    @FXML
    private Label expensesLabel;

    @FXML
    private Label profitsLabel;

    @FXML
    private Button newAddbutton1;

    @FXML
    private ImageView imageView;

    @FXML
    private TextField text_search;

    @FXML
    void setCancelButtonIDAction(ActionEvent event) {
        Stage stage = (Stage) CancelButton.getScene().getWindow();
        stage.close();

    }
    private final ServiceSupplier sp = new ServiceSupplier();

    private final ServiceTransaction sptrans = new ServiceTransaction();
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        try {
            // displaying the data of capital entity
            Capital capital = sptrans.retrieveCurrentCapitalFromDatabase();
            salaryLabel.setText(String.valueOf(capital.getSalary()));
            expensesLabel.setText(String.valueOf(capital.getExepenses()));
            profitsLabel.setText(String.valueOf(capital.getProfits()));

            List<Transaction> recentlyAdded = recentlyAdded();
            for (Transaction transaction : recentlyAdded) {
                System.out.println(transaction.getId());
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("Table.fxml"));
                HBox cardBox = fxmlLoader.load();
                TableController cardController = fxmlLoader.getController();
                cardController.setData(transaction);
                cardLayout.getChildren().add(cardBox);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private List<Transaction> recentlyAdded() {
        List<Transaction> transactions = new ArrayList<>();
        ServiceTransaction sp = new ServiceTransaction();
        // Retrieve all transactions from the database
        Set<Transaction> allTransactions = sp.getAll();

        // Convert Set to List for easier manipulation
        transactions.addAll(allTransactions);
        return transactions;
    }
    public void swtichToMenu(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("Dispa.fxml"));
            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Error loading SupplierAdd.fxml");
            alert.showAndWait();
        }
    }
    public void listSuppliersGO(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Display.fxml"));
            Parent root = loader.load();
            DisplayController displayController = loader.getController();
            typeylabel.getScene().setRoot(root);
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Error loading Display.fxml");
            alert.showAndWait();
        }
    }
    public void newTransactionPopUp(ActionEvent event){try {

        Parent root = FXMLLoader.load(getClass().getResource("TransactionAdd.fxml"));
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.initStyle ( StageStyle.UTILITY);
        stage.show();
    } catch (IOException e) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText("Error loading TransactionAdd.fxml");
        alert.showAndWait();
    }
    }
}
