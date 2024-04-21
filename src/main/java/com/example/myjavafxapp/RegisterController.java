package com.example.myjavafxapp;
import Entities.Capital;
import Entities.Supplier;
import Entities.Transaction;
import Services.ServiceTransaction;
import Services.ServiceSupplier;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.event.ActionEvent;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.*;

public class RegisterController  implements Initializable {

    @FXML
    private Button CancelButton ;
    @FXML
    private Label LabelMessage;
    @FXML
    private ComboBox<String> typeTextField;
    @FXML
    private ComboBox<String> SupplierComboBox;
    @FXML
    private TextField DescrptionTextField;
    @FXML
    private TextField CostTextField;
    @FXML
    private TextField QuantityTextField;
    @FXML
    private Label idtrans;

    ServiceTransaction sp = new ServiceTransaction();
    private String [] suppliername =companyNames();

    private String[] companyNames(){
        List<String> names = new ArrayList<>();
        ServiceSupplier sup = new ServiceSupplier();
        // Retrieve all suppliers from the database
        Set<Supplier> allSuppliers = sup.getName();
        // Iterate over each supplier and extract their names
        for (Supplier supplier : allSuppliers) {
            names.add(supplier.getCompany_name());
        }
        String[] namesArray = names.toArray(new String[0]);
        return namesArray;
    }

    private String [] type ={"Income","Salary","Expenses"};
    private Alert alert;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        typeTextField.getItems().addAll(type);
        SupplierComboBox.getItems().addAll(suppliername);
    }
    public void saveButtonOnAction(ActionEvent event){
        if (QuantityTextField.getText().isEmpty() || DescrptionTextField.getText().isEmpty() || CostTextField.getText().isEmpty()) {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("You need to fill blank field ");
            alert.showAndWait();

        }else {
            if(!isNumeric(QuantityTextField.getText()) || !isNumeric(CostTextField.getText()) ){
                alert=new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setContentText("Requires numbers"+ "Check The Quantity and cost Field ");
                alert.showAndWait();
            }
            else {
                if (DescrptionTextField.getText().length() < 3) {
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setContentText(" Description field requires more than 3 caracteres ");
                    alert.showAndWait();

                } else {

                    LabelMessage.setText("You Try to do a Transaction ");
                    int quantity = Integer.parseInt(QuantityTextField.getText());

// Convert CostTextField input to a float
                    float cost = Float.parseFloat(CostTextField.getText());
                    try {
                        sp.ajouter(new Transaction(typeTextField.getValue(), DescrptionTextField.getText(), quantity, cost));
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Success");
                        alert.setContentText("GG");
                        alert.show();
                    } catch (SQLException e) {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("SQL Exception");
                        alert.setContentText(e.getMessage());
                        alert.showAndWait();
                    }
                }
            }
        }
    }
    public void setCancelButtonIDAction(ActionEvent event ){
        Stage stage = (Stage)  CancelButton.getScene().getWindow();
        stage.close();
    }
    public void setFields(int id,String Type, int q, String Description , float c  ){
        System.out.println("///////////////////////////////////////////////////////////");
        try {
            String idyo = String.valueOf(id);
            System.out.println("////////////  THE ID = "+ idyo);
            String Quantity = String.valueOf(q);
            String cost = String.valueOf(c);
            typeTextField.setValue(Type);
            QuantityTextField.setText(Quantity);
            DescrptionTextField.setText(Description);
            CostTextField.setText(cost);
            idtrans.setText(idyo);
            System.out.println("quantity:"+Quantity+"cost"+cost);
        } catch (Exception e) {
            // Handle any exceptions that might occur during setting text fields
            System.err.println("Error occurred while setting text fields: " + e.getMessage());
            e.printStackTrace();
        }
    }
    private boolean isNumeric (String str ){
        try {
            double d = Double.parseDouble(str);

        }catch (NumberFormatException | NullPointerException e ){
            return false ;
        }
        return true;
    }

    public void EditOnClickOn(ActionEvent event){
        if (QuantityTextField.getText().isEmpty() || DescrptionTextField.getText().isEmpty() || CostTextField.getText().isEmpty()) {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("You need to fill blank field ");
            alert.showAndWait();

        }else {
            if(!isNumeric(QuantityTextField.getText()) || !isNumeric(CostTextField.getText()) ){
                alert=new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setContentText("Requires numbers"+ "Check The Quantity and cost Field ");
                alert.showAndWait();
            }
            else {
                if (DescrptionTextField.getText().length() < 3) {
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setContentText(" Description field requires more than 3 caracteres ");
                    alert.showAndWait();

                } else {

                    LabelMessage.setText("You Try to do a Transaction ");
                    int quantity = Integer.parseInt(QuantityTextField.getText());

// Convert CostTextField input to a float
                    float cost = Float.parseFloat(CostTextField.getText());
                    int id = Integer.parseInt(idtrans.getText());
                    try {
                        sp.modifier(new Transaction(id,typeTextField.getValue(), DescrptionTextField.getText(), quantity, cost));
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Success");
                        alert.setContentText("GG");
                        alert.show();
                    } catch (SQLException e) {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("SQL Exception");
                        alert.setContentText(e.getMessage());
                        alert.showAndWait();
                    }
                }
            }
        }

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







}
