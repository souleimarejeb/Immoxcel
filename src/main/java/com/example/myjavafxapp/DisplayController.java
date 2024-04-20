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

        public void setCancelButtonIDAction(ActionEvent event) {
                Stage stage = (Stage) CancelButton.getScene().getWindow();
                stage.close();
        }
        @Override
        public void initialize(URL url, ResourceBundle resourceBundle) {

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
