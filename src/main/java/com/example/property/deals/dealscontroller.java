package com.example.property.deals;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

import com.example.property.customerreg.mysqldbconnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class dealscontroller {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField advancetxt;

    @FXML
    private TextField balancetxt;

    @FXML
    private ComboBox<String> bmobilecombo;

    @FXML
    private TextField bnametxt;

    @FXML
    private TextField commissiontxt;

    @FXML
    private DatePicker dod;

    @FXML
    private Label lblstatus;

    @FXML
    private ComboBox<String> pidcombo;

    @FXML
    private DatePicker rd;

    @FXML
    private TextField smobiletxt;

    @FXML
    private TextField snametxt;

    @FXML
    private TextField totaltxt;

    @FXML
    private TextField corporationtxt;

    @FXML
    void doclear(ActionEvent event) {
        pidcombo.setValue(null);
        bmobilecombo.setValue(null);
        bnametxt.clear();
        smobiletxt.clear();
        snametxt.clear();
        rd.setValue(null);
        dod.setValue(null);
        totaltxt.clear();
        advancetxt.clear();
        balancetxt.clear();
        corporationtxt.clear();
        commissiontxt.clear();
        lblstatus.setText(""); // optional: clear status message
    }



    @FXML
    void dodone(ActionEvent event) {
        String query = "INSERT INTO dealsmatured (rid, seekerscontact, bname, traderscontact, sname, dateofregistr, dateofdeal, totalamount, advancedpayment, balancedpayment, corporationno, commissionengrossed) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement pst = con.prepareStatement(query)) {
            pst.setInt(1, Integer.parseInt(pidcombo.getValue()));                          // rid
            pst.setString(2, bmobilecombo.getValue());                                     // seekerscontact
            pst.setString(3, bnametxt.getText());                                          // bname
            pst.setString(4, smobiletxt.getText());                                        // traderscontact
            pst.setString(5, snametxt.getText());                                          // sname
            pst.setString(6, rd.getValue().toString());                                    // dateofregistr
            pst.setString(7, dod.getValue().toString());                                   // dateofdeal
            pst.setString(8, totaltxt.getText());                                          // totalamount
            pst.setString(9, advancetxt.getText());                                        // advancedpayment
            pst.setString(10, balancetxt.getText());                                       // balancedpayment
            pst.setString(11, corporationtxt.getText());                                   // corporationno
            pst.setString(12, commissiontxt.getText());                                    // commissionengrossed

            int result = pst.executeUpdate();

            if (result > 0) {
                lblstatus.setText(" Deal recorded successfully.");
                System.out.println("Inserted into dealsmatured.");

                String updateQuery = "UPDATE propertylist SET stauts = 0 WHERE rid = ? AND stauts = 1";
                try (PreparedStatement updatePst = con.prepareStatement(updateQuery)) {
                    updatePst.setInt(1, Integer.parseInt(pidcombo.getValue()));

                    int rowsAffected = updatePst.executeUpdate();
                    System.out.println(rowsAffected + " rows updated in propertylist.");


                }

            }
            else {
                lblstatus.setText(" Failed to record the deal.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            lblstatus.setText(" Error occurred while recording deal.");
        }
    }


    @FXML
    void getbname(ActionEvent event) {
        String selectedContact = bmobilecombo.getValue();

        if (selectedContact == null || selectedContact.trim().isEmpty()) {
            lblstatus.setText("Please select a buyer contact.");
            System.out.println("No buyer contact selected.");
            return;
        }

        String query = "SELECT name FROM buyerinterest WHERE contact = ?";

        try (PreparedStatement pst = con.prepareStatement(query)) {
            pst.setString(1, selectedContact);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                bnametxt.setText(rs.getString("name"));
                lblstatus.setText("Buyer name loaded successfully.");
            } else {
                lblstatus.setText("No buyer found for selected contact.");
            }

        } catch (Exception e) {
            lblstatus.setText("Error fetching buyer name.");
            e.printStackTrace();
        }

    }

    private void calculateBalance() {
        try {
            double totalAmount = Double.parseDouble(totaltxt.getText());
            double advancePay = Double.parseDouble(advancetxt.getText());
            double balance = totalAmount - advancePay;

            balancetxt.setText(String.format("%.2f", balance));
        } catch (NumberFormatException e) {
            balancetxt.setText("0.00"); // fallback if values are not valid
        }
    }


    @FXML
    void selectrid(ActionEvent event) {
        String selectedRid = pidcombo.getValue();

        if (selectedRid == null || selectedRid.trim().isEmpty()) {
            lblstatus.setText("Please select a valid RID.");
            return;
        }

        try {
            //  Check if deal is already recorded for this RID
            PreparedStatement checkStmt = con.prepareStatement(
                    "SELECT COUNT(*) FROM dealsmatured WHERE rid = ?"
            );
            checkStmt.setInt(1, Integer.parseInt(selectedRid));
            ResultSet checkResult = checkStmt.executeQuery();

            if (checkResult.next() && checkResult.getInt(1) > 0) {
                lblstatus.setText(" Deal is already done for this property.");
                return; // Skip further loading
            }

            //  Load property details
            PreparedStatement pst = con.prepareStatement(
                    "SELECT contact, name, price, corporationno FROM propertylist WHERE rid = ?"
            );
            pst.setInt(1, Integer.parseInt(selectedRid));
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                String contact = rs.getString("contact");
                String name = rs.getString("name");
                int price = rs.getInt("price");
                String corporationno = rs.getString("corporationno");

                float commission = price * 0.06f;
                float totalWithCommission = price + commission;

                smobiletxt.setText(contact);
                snametxt.setText(name);
                totaltxt.setText(String.valueOf(totalWithCommission));
                commissiontxt.setText(String.format("%.2f", commission));
                corporationtxt.setText(corporationno);

                lblstatus.setText("Seller and pricing info updated.");
            } else {
                lblstatus.setText("Property record not found.");
            }

        } catch (Exception e) {
            e.printStackTrace();
            lblstatus.setText("Error checking deal status.");
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
            PreparedStatement stmt = con.prepareStatement("SELECT DISTINCT contact FROM buyerinterest");
            ResultSet res = stmt.executeQuery();
            while (res.next()) {
                bmobilecombo.getItems().add(res.getString("contact"));
            }
        } catch (Exception exp) {
            exp.printStackTrace();
        }

        try {
            PreparedStatement stmt = con.prepareStatement("SELECT DISTINCT rid FROM propertylist");
            ResultSet res = stmt.executeQuery();
            while (res.next()) {
                pidcombo.getItems().add(res.getString("rid"));
            }
        } catch (Exception exp) {
            exp.printStackTrace();
        }

        //  Real-time balance calculation
        advancetxt.textProperty().addListener((obs, oldVal, newVal) -> calculateBalance());
        totaltxt.textProperty().addListener((obs, oldVal, newVal) -> calculateBalance());
    }
}

