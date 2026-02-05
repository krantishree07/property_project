package com.example.property.loginboard;

import java.net.URL;
import java.sql.Connection;
import java.util.ResourceBundle;

import com.example.property.HelloApplication;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class loginboardcontroller {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Label lblstatus;

    @FXML
    private PasswordField passwordtxt;

    @FXML
    private TextField passwordVisibleTxt;

    @FXML
    private Button togglePasswordBtn;

    @FXML
    private TextField usernametxt;

    private boolean showPassword = false;

    @FXML
    void togglePasswordVisibility(ActionEvent event) {
        showPassword = !showPassword;
        if (showPassword) {
            passwordVisibleTxt.setText(passwordtxt.getText());
            passwordVisibleTxt.setVisible(true);
            passwordVisibleTxt.setManaged(true);
            passwordtxt.setVisible(false);
            passwordtxt.setManaged(false);
            togglePasswordBtn.setText("\uD83D\uDD18");
        } else {
            passwordtxt.setText(passwordVisibleTxt.getText());
            passwordtxt.setVisible(true);
            passwordtxt.setManaged(true);
            passwordVisibleTxt.setVisible(false);
            passwordVisibleTxt.setManaged(false);
            togglePasswordBtn.setText("\uD83D\uDD18");
        }
    }

    @FXML
    void dologin(ActionEvent event) {
        String username = usernametxt.getText();
        String password = showPassword ? passwordVisibleTxt.getText() : passwordtxt.getText();

        if (username.equals("kranti") && password.equals("12345")) {
            try {
                Parent root = FXMLLoader.load(HelloApplication.class.getResource("propertydashboardd/propertydashboardView.fxml"));
                Stage stage = new Stage();
                stage.setTitle("Dashboard");
                stage.setScene(new Scene(root));
                stage.show();
                stage.setResizable(false);
                ((Stage) usernametxt.getScene().getWindow()).close();
            } catch (Exception e) {
                e.printStackTrace();
                lblstatus.setText("Failed to load dashboard.");
            }
        } else {
            lblstatus.setText("Invalid username or password.");
        }
    }

    Connection con;

    @FXML
    void initialize() {
        passwordVisibleTxt.setVisible(false);
        passwordVisibleTxt.setManaged(false);
    }
}
