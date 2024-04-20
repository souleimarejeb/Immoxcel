package com.example.myjavafxapp;

import Entities.Supplier;
import Entities.Transaction;
import Services.ServiceSupplier;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

public class UpdateSupplierController {
    @FXML
    private Button CancelButton;

    @FXML
    private Label LabelMessage;
    @FXML
    private Label idSup;

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
    private javafx.scene.image.ImageView ImageView;
    @FXML
    private Pane pane_112;
    ServiceSupplier sp = new ServiceSupplier();
    private Image image;
    private Alert alert;
    String imagePath = "";

    public void supplierInsertImage() {
        FileChooser open = new FileChooser();
        File file = open.showOpenDialog(pane_112.getScene().getWindow());
        if (file != null) {
            String imagePath = file.toURI().toString();
            image = new Image(imagePath);
            ImageView.setImage(image);
        }
    }

    public void EditOnClickButton(ActionEvent event){

        if (companyNameTextFiled.getText().isEmpty() || addressTextFiled.getText().isEmpty() || ProductTextField.getText().isEmpty() || PhoneNumberTextFiled.getText().isEmpty() || PatentTextField.getText().isEmpty()) {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("You need to fill blank field ");
            alert.showAndWait();

        }else {
            if(!isNumeric(PhoneNumberTextFiled.getText())  ){
                alert=new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setContentText("Requires numbers"+ "Check The Quantity and cost Field ");
                alert.showAndWait();
            }
            else {
                if (companyNameTextFiled.getText().length() < 3 || addressTextFiled.getText().length() < 3 || ProductTextField.getText().length() < 3|| PhoneNumberTextFiled.getText().length() < 8 || PatentTextField.getText().length() < 8)

                {
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setContentText("fields requires more than 3 caracteres ");
                    alert.showAndWait();

                } else {

                    LabelMessage.setText("You Try to add a supplier  ");
                    int phone = Integer.parseInt(PhoneNumberTextFiled.getText());

// Convert CostTextField input to a float
                    int id = Integer.parseInt(idSup.getText());
                    try {
                        sp.modifier(new Supplier(id,companyNameTextFiled.getText(), addressTextFiled.getText(),ProductTextField.getText(),phone,PatentTextField.getText(),"hellooo"));
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
    public void setFields(int id,String companyName,String address, String product , int c,String PatentRef ,String Image ){
        System.out.println("///////////////////////////////////////////////////////////");
        try {
            String idSupp = String.valueOf(id);
            System.out.println("////////////  THE ID = "+ idSupp);

            String phonenumber = String.valueOf(c);
            companyNameTextFiled.setText(companyName);
            addressTextFiled.setText(address);
            ProductTextField.setText(product);
            PhoneNumberTextFiled.setText(phonenumber);
            PatentTextField.setText(PatentRef);try {
                String imagePath = Image;
                // Remove the "file:\" or "file:/" prefix if it exists
                if (imagePath.startsWith("file:\\")) {
                    imagePath = imagePath.substring(6); // Removing "file:\" prefix
                } else if (imagePath.startsWith("file:/")) {
                    imagePath = imagePath.substring(5); // Removing "file:/" prefix
                }
                File file = new File(imagePath);
                System.out.println("Image file path: " + file.getAbsolutePath()); // Debug output
                if (file.exists()) {
                    Image image = new Image(file.toURI().toString());
                    ImageView.setImage(image);
                } else {
                    System.err.println("Image file not found: " + file.getAbsolutePath()); // Error message
                }
            } catch (Exception e) {
                e.printStackTrace(); // Handle the exception appropriately
            }

            System.out.println("Phone Number inside the setfunction just to verify the data :"+phonenumber);
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
}
