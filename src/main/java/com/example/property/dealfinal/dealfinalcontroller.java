package com.example.property.dealfinal;
//import javax.mail.
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

import com.example.property.customerreg.mysqldbconnection;
        import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

public class dealfinalcontroller {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ComboBox<String> contactcombo;

    @FXML
    private Label lblstaus;

    @FXML
    private TextField nametxt;

    @FXML
    private TableView<bean> tableView;

    Connection con;

    @FXML
    void getname(ActionEvent event) {
        String selectedSeekerContact = contactcombo.getValue();

        if (selectedSeekerContact == null || selectedSeekerContact.trim().isEmpty()) {
            lblstaus.setText("Please select a seeker contact.");
            return;
        }

        String query = "SELECT bname FROM dealsmatured WHERE seekerscontact = ? AND balancedpayment != '0'";
        try (PreparedStatement pst = con.prepareStatement(query)) {
            pst.setString(1, selectedSeekerContact);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                nametxt.setText(rs.getString("bname"));
                lblstaus.setText("Seeker name loaded successfully.");
            } else {
                lblstaus.setText("No seeker found with pending balance.");
            }

        } catch (Exception e) {
            lblstaus.setText("Error fetching seeker name.");
            e.printStackTrace();
        }
    }

    @FXML
    void doshowpendinamount(ActionEvent event) {
        String selectedContact = contactcombo.getValue();
        if (selectedContact == null || selectedContact.trim().isEmpty()) {
            lblstaus.setText("Please select a seeker contact.");
            return;
        }

        ObservableList<bean> data = getPendingDeals(selectedContact);
        tableView.setItems(data);
        lblstaus.setText("Pending deals loaded for " + selectedContact);
    }

    ObservableList<bean> getPendingDeals(String seekerContact) {
        ObservableList<bean> list = FXCollections.observableArrayList();

        try {
            String query = "SELECT * FROM dealsmatured WHERE seekerscontact = ? AND balancedpayment != '0'";
            PreparedStatement pst = con.prepareStatement(query);
            pst.setString(1, seekerContact);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                list.add(new bean(
                        rs.getInt("rid"),
                        rs.getString("seekerscontact"),
                        rs.getString("bname"),
                        rs.getString("traderscontact"),
                        rs.getString("sname"),
                        rs.getString("dateofregistr"),
                        rs.getString("dateofdeal"),
                        rs.getString("totalamount"),
                        rs.getString("advancedpayment"),
                        rs.getString("balancedpayment"),
                        rs.getString("corporationno"),
                        rs.getString("commissionengrossed")
                ));
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return list;
    }

    @FXML
    void doerase(ActionEvent event) {
        contactcombo.setValue(null);
        nametxt.clear();
       tableView.getItems().clear();
        lblstaus.setText("");
    }
    @FXML
    void dopay(ActionEvent event) {
        bean selectedDeal = tableView.getSelectionModel().getSelectedItem();

        if (selectedDeal == null) {
            lblstaus.setText("Please select a deal to pay off.");
            return;
        }

        try {
            // Update balance to 0
            String updateQuery = "UPDATE dealsmatured SET balancedpayment = '0' WHERE rid = ?";
            PreparedStatement pst = con.prepareStatement(updateQuery);
            pst.setInt(1, selectedDeal.getRid());
            int rowsAffected = pst.executeUpdate();

            if (rowsAffected > 0) {
                // Remove from TableView
                tableView.getItems().remove(selectedDeal);

                // Check if contact should be removed from ComboBox
                String seekerContact = selectedDeal.getSeekerscontact();
                PreparedStatement checkStmt = con.prepareStatement(
                        "SELECT COUNT(*) FROM dealsmatured WHERE seekerscontact = ? AND balancedpayment != '0'"
                );
                checkStmt.setString(1, seekerContact);
                ResultSet rsCheck = checkStmt.executeQuery();

                if (rsCheck.next() && rsCheck.getInt(1) == 0) {
                    contactcombo.getItems().remove(seekerContact);
                    if (seekerContact.equals(contactcombo.getValue())) {
                        contactcombo.setValue(null);
                    }
                }

                // Show confirmation alert
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Payment Confirmation");
                alert.setHeaderText("Pending balance has been paid.");
                alert.setContentText("Deal finalized successfully.");
                alert.showAndWait();

                nametxt.clear();
                lblstaus.setText("Payment complete. Deal finalized.");
            } else {
                lblstaus.setText("Update failed. Please check the record.");
            }

        } catch (Exception ex) {
            lblstaus.setText("Error while processing payment.");
            ex.printStackTrace();
        }
    }









    @FXML
    void initialize() {
        con = mysqldbconnection.MySQLDbconnection();
        if (con == null) {
            System.out.println("Connection error.");
            return;
        }

        try {
            String query = "SELECT seekerscontact FROM dealsmatured";
            PreparedStatement pst = con.prepareStatement(query);
            ResultSet rs = pst.executeQuery();

            contactcombo.getItems().add("Show All"); //

            while (rs.next()) {
                contactcombo.getItems().add(rs.getString("seekerscontact"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }


        // Table columns setup
        TableColumn<bean, Integer> ridC = new TableColumn<>("RID");
        ridC.setCellValueFactory(new PropertyValueFactory<>("rid"));

        TableColumn<bean, String> seekerC = new TableColumn<>("Seeker Contact");
        seekerC.setCellValueFactory(new PropertyValueFactory<>("seekerscontact"));

        TableColumn<bean, String> bnameC = new TableColumn<>("Buyer Name");
        bnameC.setCellValueFactory(new PropertyValueFactory<>("bname"));

        TableColumn<bean, String> traderC = new TableColumn<>("Trader Contact");
        traderC.setCellValueFactory(new PropertyValueFactory<>("traderscontact"));

        TableColumn<bean, String> snameC = new TableColumn<>("Seller Name");
        snameC.setCellValueFactory(new PropertyValueFactory<>("sname"));

        TableColumn<bean, String> dorC = new TableColumn<>("Registration Date");
        dorC.setCellValueFactory(new PropertyValueFactory<>("dateofregistr"));

        TableColumn<bean, String> dodC = new TableColumn<>("Deal Date");
        dodC.setCellValueFactory(new PropertyValueFactory<>("dateofdeal"));

        TableColumn<bean, String> totalC = new TableColumn<>("Total Amount");
        totalC.setCellValueFactory(new PropertyValueFactory<>("totalamount"));

        TableColumn<bean, String> advanceC = new TableColumn<>("Advance Payment");
        advanceC.setCellValueFactory(new PropertyValueFactory<>("advancedpayment"));

        TableColumn<bean, String> balanceC = new TableColumn<>("Pending Balance");
        balanceC.setCellValueFactory(new PropertyValueFactory<>("balancedpayment"));

        TableColumn<bean, String> corpC = new TableColumn<>("Corporation No");
        corpC.setCellValueFactory(new PropertyValueFactory<>("corporationno"));

        TableColumn<bean, String> commC = new TableColumn<>("Commission");
        commC.setCellValueFactory(new PropertyValueFactory<>("commissionengrossed"));

        TableColumn<bean, String> statusC = new TableColumn<>("Status");
        statusC.setCellValueFactory(cellData -> new SimpleStringProperty("Pending"));

        tableView.getColumns().addAll(ridC, seekerC, bnameC, traderC, snameC, dorC,
                dodC, totalC, advanceC, balanceC, corpC, commC, statusC);


    }
}
