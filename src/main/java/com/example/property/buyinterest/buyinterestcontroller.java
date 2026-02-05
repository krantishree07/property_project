package com.example.property.buyinterest;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

import com.example.property.customerreg.mysqldbconnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class buyinterestcontroller {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ComboBox<String> lccombo;

    @FXML
    private ComboBox<String> areacombo;

    @FXML
    private TextField budgetfrom;

    @FXML
    private TextField budgettotxt;

    @FXML
    private ComboBox<String> citycombo;

    @FXML
    private ComboBox<String> facingcombo;

    @FXML
    private ComboBox<String> mobilecombo;

    @FXML
    private TextField nametxt;

    @FXML
    private ComboBox<String> naturecombo;

    @FXML
    private ComboBox<String> propertytypecombo;

    @FXML
    private TextField sizefromtxt;

    @FXML
    private TextField sizetotxt;


    @FXML
    private Label lblstatus;
    @FXML
    void doaddinterst(ActionEvent event) {
        String query = "INSERT INTO buyerinterest (contact, name, type, nature, facing, area, city, sizefrom, sizeto, budgetfrom, budgetto, budgetunit) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection con = mysqldbconnection.MySQLDbconnection();
             PreparedStatement ps = con.prepareStatement(query)) {

            ps.setString(1, mobilecombo.getValue());
            ps.setString(2, nametxt.getText());
            ps.setString(3, propertytypecombo.getValue());
            ps.setString(4, naturecombo.getValue());
            ps.setString(5, facingcombo.getValue());
            ps.setString(6, areacombo.getValue());
            ps.setString(7, citycombo.getValue());
            ps.setFloat(8, Float.parseFloat(sizefromtxt.getText()));
            ps.setFloat(9, Float.parseFloat(sizetotxt.getText()));
            ps.setFloat(10, Float.parseFloat(budgetfrom.getText()));
            ps.setFloat(11, Float.parseFloat(budgettotxt.getText()));
            ps.setString(12, lccombo.getValue());
         int count=   ps.executeUpdate();
            if(count>0){
                System.out.println("data saved");
                lblstatus.setText("Interest Added");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            lblstatus.setText(ex.getMessage());
        }
    }

    @FXML
    void doclose(ActionEvent event) {
        mobilecombo.setValue(null);
        nametxt.clear();
        propertytypecombo.setValue(null);
        naturecombo.setValue(null);
        facingcombo.setValue(null);
        areacombo.setValue(null);
        citycombo.setValue(null);
        sizefromtxt.clear();
        sizetotxt.clear();
        budgetfrom.clear();
        budgettotxt.clear();
        lccombo.setValue(null);
    }
    @FXML
    void getname(ActionEvent event) {
        String selItem = mobilecombo.getSelectionModel().getSelectedItem();

        if (selItem == null || selItem.trim().isEmpty()) {
            System.out.println("No mobile number selected.");
            return;
        }

        try {

            PreparedStatement pst = con.prepareStatement(
                    "SELECT name FROM customers WHERE contact = ? AND usertype IN ('Buyer', 'Buyer and Seller')");

            pst.setString(1, selItem);

            // Execute the query
            ResultSet rst = pst.executeQuery();

            // If a matching record is found, set the name to the text field
            if (rst.next()) {
                String name = rst.getString("name");
                nametxt.setText(name);
            } else {
                System.out.println("No matching name found for selected contact.");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }


    }


    Connection con;
    @FXML
    void initialize() {
        con = mysqldbconnection.MySQLDbconnection();
        if (con == null) {
            System.out.println("Connection error.");
            return;
        }
        try {
            PreparedStatement stmt = con.prepareStatement(
                    "SELECT DISTINCT contact FROM customers WHERE usertype IN ('Buyer', 'Buyer and Seller')"
            );
            ResultSet res = stmt.executeQuery();

            while (res.next()) {
                String contact = res.getString("contact");
                mobilecombo.getItems().add(contact);
            }

        } catch (Exception exp) {
            exp.printStackTrace();
        }

        propertytypecombo.getItems().addAll("Building","Plot");
        naturecombo.getItems().addAll("Private","Public","Common","Residentila","Commercial","Industrial","Agriculture");
       facingcombo.getItems().addAll("North","South","East","West","North-East","North-West","South-East","South-West");
        citycombo.getItems().addAll("Ludhiana", "Amritsar", "Jalandhar", "Patiala", "Bathinda",
                "Mohali", "Firozpur", "Gurdaspur", "Hoshiarpur", "Moga",
                "Fazilka", "Pathankot", "Barnala", "Malerkotla", "Sangrur",
                "Tarn Taran", "Muktsar", "Kapurthala", "Rupnagar", "Mansa");
        areacombo.getItems().addAll(
                "Ghoda Chowk", "Silver Oaks Colony", "Model Town", "Green Avenue",
                "Civil Lines", "Kitchlu Nagar", "Rajguru Nagar", "Ajit Road",
                "Mall Road", "Ranjit Avenue", "Urban Estate", "Guru Teg Bahadur Nagar",
                "Sarai Road", "Bibiwala Road", "Sukhna Enclave", "Shivaji Park",
                "Shastri Nagar", "New Shakti Nagar", "Lamba Patti", "Gill Road"
        );
        lccombo.getItems().addAll("Lakhs","Crore");


    }
}
