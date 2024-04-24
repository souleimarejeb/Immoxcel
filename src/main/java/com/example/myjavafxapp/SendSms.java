package com.example.myjavafxapp;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.multipart.FilePart;
import org.apache.commons.httpclient.methods.multipart.MultipartRequestEntity;
import org.apache.commons.httpclient.methods.multipart.Part;
import org.apache.commons.httpclient.util.EncodingUtil;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;
public class SendSms {

    @FXML
    private Button ClearButton;

    public static String url_str;

    @FXML
    private Button Editbtn;

    @FXML
    private Label LabelMessage;

    @FXML
    private TextField MessageTextField;

    @FXML
    private TextField PhoneNumberTextFiled;

    @FXML
    private Label idtrans;

    @FXML
    private Pane pane_112;

    public static void setvar (String argname, String argvalue) {



        if (argname != null) {

            if (argvalue != null) {

                url_str = url_str + "&" + argname + "=";

                try {

                    String encoded = URLEncoder.encode (argvalue, "UTF-8");

                    url_str = url_str + encoded;

                }

                catch (UnsupportedEncodingException e) {

                    url_str = url_str + argvalue;

                }

            }

        }



    }

    @FXML
    void setCancelButtonIDAction(ActionEvent event) {


        sendsms.server = "https://sample.smshosts.com/";

        sendsms.user = "username";

        sendsms.password = "password";

        sendsms.phonenumber = "+9999999999";

        sendsms.text = "This is a test message";

        sendsms.send();

    }





}
