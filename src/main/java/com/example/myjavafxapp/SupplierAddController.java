package com.example.myjavafxapp;

import Entities.Supplier;
import Entities.Transaction;
import Services.ServiceSupplier;
import Services.ServiceTransaction;
import Utils.DataSource;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.image.ImageView;


import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SupplierAddController {

    @FXML
    private Button CancelButton;

    @FXML
    private Label LabelMessage;

    @FXML
    private TextField PhoneNumberTextFiled;

    @FXML
    private TextField PatentTextField;

    @FXML
    private TextField ProductTextField;

    @FXML
    private TextField addressTextFiled;

    @FXML
    private TextField companyNameTextFiled;

    @FXML
    private Button saveButton;
    @FXML
    private ImageView ImageView;
    @FXML
    private Pane pane_112;
    ServiceSupplier sup = new ServiceSupplier();
    private Image image;

    private Alert alert;

    Connection cnx = DataSource.getInstance().getCnx();


    public void supplierInsertImage() {
        FileChooser open = new FileChooser();
        File file = open.showOpenDialog(pane_112.getScene().getWindow());
        if (file != null) {
            String imagePath = file.toURI().toString();
            image = new Image(imagePath);
            ImageView.setImage(image);
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
    public void saveSupplierButtonOnAction(ActionEvent event) {

//       LabelMessage.setText("You Try to add a Supplier  ");
        System.out.println("hell i am inside the add function");
        //String companyName = companyNameTextFiled.getText();
        //String address = addressTextFiled.getText();
        //String Products = ProductTextField.getText();
        //String patentRef = PatentTextField.getText();
       // int phone = Integer.parseInt(PhoneNumberTextFiled.getText());
        String imagePath = "";
        System.out.println("i am just after getting the data from the fields");
        if (image != null) {
            imagePath = image.getUrl();
        }
        if (companyNameTextFiled.getText().isEmpty() || addressTextFiled.getText().isEmpty() || ProductTextField.getText().isEmpty() || PhoneNumberTextFiled.getText().isEmpty() || PatentTextField.getText().isEmpty()) {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("You need to fill blank field ");
            alert.showAndWait();
        }
        else {
            if (!isNumeric(PhoneNumberTextFiled.getText())) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setContentText("Requires numbers" + "Check The Quantity and cost Field ");
                alert.showAndWait();
            } else {
                if (companyNameTextFiled.getText().length() < 3 || addressTextFiled.getText().length() < 3 || ProductTextField.getText().length() < 3 || PhoneNumberTextFiled.getText().length() < 8 || PatentTextField.getText().length() < 8) {
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setContentText("fields requires more than 3 caracteres ");
                    alert.showAndWait();
                } else {
                    try {
                        String companyName = companyNameTextFiled.getText();
                        String address = addressTextFiled.getText();
                        String Products = ProductTextField.getText();
                        String patentRef = PatentTextField.getText();
                         int phone = Integer.parseInt(PhoneNumberTextFiled.getText());
                        String check = "SELECT phone_number FROM supplier WHERE phone_number=?";
                        PreparedStatement statement = cnx.prepareStatement(check);
                        statement.setString(1, PhoneNumberTextFiled.getText());
                        ResultSet res = statement.executeQuery();
                        if (res.next()) {
                            alert = new Alert(Alert.AlertType.ERROR);
                            alert.setTitle("Error Message");
                            alert.setContentText("Supplier :" + companyNameTextFiled.getText() + " was already created");
                            alert.showAndWait();
                        } else {
                            sup.ajouter(new Supplier(companyName, address, Products, phone, patentRef, imagePath));
                            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                            alert.setTitle("Success");
                            alert.show();}
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

    @FXML
    void setCancelButtonIDAction(ActionEvent event) {
        Stage stage = (Stage)  CancelButton.getScene().getWindow();
        stage.close();
    }

}
