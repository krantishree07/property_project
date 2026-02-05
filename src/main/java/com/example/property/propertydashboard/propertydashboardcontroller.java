package com.example.property.propertydashboard;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.util.ResourceBundle;

import com.example.property.HelloApplication;
import com.example.property.customerreg.mysqldbconnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class propertydashboardcontroller {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    Connection con;

    private void loadScene(String fxmlPath, String title) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource(fxmlPath));
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = new Stage();
            stage.setTitle(title);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void amountstatustable(ActionEvent event) {
        loadScene("amoumtstatustablee/amoumtstatustableview.fxml", "Amount Status Table");
    }

    @FXML
    void areachart(ActionEvent event) {
        loadScene("areachartt/areachartview.fxml", "Area Chart");
    }

    @FXML
    void buyinterest(ActionEvent event) {
        loadScene("buyinterestt/buyinterestview.fxml", "Buy Interest");
    }

    @FXML
    void chartss(ActionEvent event) {
        loadScene("chartss/chartsview.fxml", "Charts");
    }

    @FXML
    void customerreg(ActionEvent event) {
        loadScene("customerregg/customerregview.fxml", "Customer Registration");
    }

    @FXML
    void customertable(ActionEvent event) {
        loadScene("customertablee/customertableview.fxml", "Customer Table");
    }

    @FXML
    void dealfinal(ActionEvent event) {
        loadScene("dealfinall/dealfinalview.fxml", "Deal Finalization");
    }

    @FXML
    void dealmatured(ActionEvent event) {
        loadScene("dealss/dealsview.fxml", "Matured Deals");
    }

    @FXML
    void pricechart(ActionEvent event) {
        loadScene("pricechartt/pricechartview.fxml", "Price Chart");
    }

    @FXML
    void propertylist(ActionEvent event) {
        loadScene("listpropertyy/listpropertyview.fxml", "Property List");
    }

    @FXML
    void propertylisttable(ActionEvent event) {
        loadScene("propertylisttablee/propertylisttableview.fxml", "Property List Table");
    }

    @FXML
    void search(ActionEvent event) {
        loadScene("searchh/searchview.fxml", "Search");
    }

    @FXML
    void initialize() {
        con = mysqldbconnection.MySQLDbconnection();
        if (con == null) {
            System.out.println("Connection error.");
        }
    }
}
