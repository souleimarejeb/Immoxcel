package com.example.myjavafxapp;

import Entities.Supplier;
import Services.ServiceSupplier;
import Services.ServiceTransaction;
import Entities.Transaction;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import javafx.stage.StageStyle;

import java.util.*;
import java.util.stream.Collectors;

public class DisplayController implements Initializable {

        @FXML
        private HBox cardLayout;
        @FXML
        private Button CancelButton;
        @FXML
        private TextField text_search;

        @FXML
        private GridPane supplierContainer;

        @FXML
        private Label id;

        @FXML
        private ImageView imageView;

        private final ServiceSupplier sp = new ServiceSupplier();

        private final ServiceSupplier sup = new ServiceSupplier();

    public void setCancelButtonAction(ActionEvent event) {
        Stage stage = (Stage) CancelButton.getScene().getWindow();
        stage.close();
    }
    public  void search(ActionEvent event) {
        supplierContainer.getChildren().clear();
        //cardLayout.getChildren().addAll(SearchList(text_search.getText(), recentlyAdded()));*/

        String searchQuery = text_search.getText().toLowerCase().trim();
        List<Supplier> filteredSupplier= filterSupplier(searchQuery);

        if (filteredSupplier.isEmpty()) {
            // Display a message when no data is found
            supplierContainer.getChildren().clear();
            Label noDataLabel = new Label("   No data found.");
            supplierContainer.getChildren().add(noDataLabel);
        } else {
            // Display the filtered transactions
            supplierContainer.getChildren().clear();
            supplierContainer.getChildren().addAll(createCardBoxesForSupplier(filteredSupplier));
        }
    }

    private List<Pane> createCardBoxesForSupplier(List<Supplier> supplier) {
        return supplier.stream()
                .map(this::PaneBoxForSupplier)
                .collect(Collectors.toList());
    }
    // collect to the words or the attribute that I am looking for  by passing SearchQuery into the paraeters of this function
    // searchQuery the text that contains the keyword to get the texts that I am looking for using the otyher function that it loooks for the search>WOrds
    private List<Supplier> filterSupplier(String searchQuery) {
        return recentlyAdeddSupplier().stream()
                .filter(supplier -> supplierContainsSearchWords(supplier, searchQuery))
                .collect(Collectors.toList());
    }

    private boolean supplierContainsSearchWords(Supplier supplier, String searchQuery) {
        String supplierText = supplierToString(supplier).toLowerCase();
        return supplierText.contains(searchQuery);
    }
    // convert the attributes to a string
    private String supplierToString(Supplier supplier) {
        // Implement this method based on how you want to represent a transaction as a string
        return supplier.getCompany_name() + supplier.getAddress() + supplier.getPatent_ref() + supplier.getPhone_number()+supplier.getMaterails_s();
    }
    private Pane PaneBoxForSupplier(Supplier supplier) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("hello-view.fxml"));
            Pane cardBox = fxmlLoader.load();
            SupplierContainerController cardController = fxmlLoader.getController();
            cardController.setData(supplier);
            return cardBox;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
        public void initialize(URL url, ResourceBundle resourceBundle) {
        supplierContainer.getChildren().clear();
        search(null); // Initially display all supplier

                try {
                        List<Supplier> recentlyAdeddSupplier = recentlyAdeddSupplier();
                        int column = 0 ;
                        int row=1;
                       for (Supplier supplier : recentlyAdeddSupplier){
                                FXMLLoader fxmlLoader = new FXMLLoader();
                                fxmlLoader.setLocation(getClass().getResource("hello-view.fxml"));
                                Pane Pane112Container = fxmlLoader.load();
                                SupplierContainerController supplierContainerController = fxmlLoader.getController();
                           supplierContainerController.setData(supplier);
                                if(column == 3) {
                                        column = 0 ;
                                        ++row;
                                }
                                supplierContainer.add(Pane112Container,column++,row);
                                GridPane.setMargin(Pane112Container , new Insets(20));
                        }
                } catch (IOException e) {
                        e.printStackTrace();
                }
        }
        private List<Supplier> recentlyAdeddSupplier() {
                List<Supplier> suppliers = new ArrayList<>();
                ServiceSupplier sp = new ServiceSupplier();
                // Retrieve all transactions from the database
                Set<Supplier> allSuppliers = sp.getAll();
                // Convert Set to List for easier manipulation
                suppliers.addAll(allSuppliers);
                return suppliers;
        }
    public void menuGO(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Dispa.fxml"));
            Parent root = loader.load();
            Display2Controller display2Controller = loader.getController();
            text_search.getScene().setRoot(root);
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Error loading Dispa.fxml");
            alert.showAndWait();
        }
    }
    public void newSupplierPopUp(ActionEvent event){
            try {
        Parent root = FXMLLoader.load(getClass().getResource("SupplierAdd.fxml"));
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
