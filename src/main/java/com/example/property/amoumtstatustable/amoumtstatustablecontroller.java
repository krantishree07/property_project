package com.example.property.amoumtstatustable;
import java.awt.*;

import java.io.File;
import java.io.FileWriter;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

import com.example.property.customerreg.mysqldbconnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;

public class amoumtstatustablecontroller {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ComboBox<String> statuscombo;

    @FXML
    private TableView<DealBean> tableview;

    Connection con;


    @FXML
    void doshowreccord(ActionEvent event) {
        String selectedStatus = statuscombo.getValue();
        ObservableList<DealBean> list = FXCollections.observableArrayList();

        if (selectedStatus == null || selectedStatus.trim().isEmpty()) {
            tableview.setItems(FXCollections.observableArrayList());
            return;
        }

        String query;
        switch (selectedStatus) {
            case "Paid":
                query = "SELECT * FROM dealsmatured WHERE balancedpayment = '0'";
                break;
            case "Unpaid":
                query = "SELECT * FROM dealsmatured WHERE balancedpayment != '0'";
                break;
            case "Show All":
            default:
                query = "SELECT * FROM dealsmatured";
                break;
        }

        try {
            PreparedStatement pst = con.prepareStatement(query);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                DealBean bean = new DealBean(
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
                );
                list.add(bean);
            }

            tableview.setItems(list);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void doexcel(ActionEvent event) {
        try {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Save CSV File");
            fileChooser.getExtensionFilters().add(
                    new FileChooser.ExtensionFilter("CSV Files", "*.csv")
            );

            File selectedFile = fileChooser.showSaveDialog(null);
            if (selectedFile != null) {
                if (!selectedFile.getName().toLowerCase().endsWith(".csv")) {
                    selectedFile = new File(selectedFile.getAbsolutePath() + ".csv");
                }

                System.out.println("Saving to: " + selectedFile.getAbsolutePath());

                FileWriter writer = new FileWriter(selectedFile);

                // 📝 Header row - based on your bean structure
                writer.write("RID,Seekers Contact,Builder Name,Traders Contact,Seeker Name," +
                        "Date Of Register,Date Of Deal,Total Amount,Advanced Payment," +
                        "Balanced Payment,Corporation No,Commission Engrossed\n");

                // 📦 Replace this with your actual TableView instance name
                ObservableList<DealBean> data = tableview.getItems();

                for (DealBean bean : data) {
                    writer.write("\"" + bean.getRid() + "\"," +
                            "\"" + bean.getSeekersContact() + "\"," +
                            "\"" + bean.getBname() + "\"," +
                            "\"" + bean.getTradersContact() + "\"," +
                            "\"" + bean.getSname() + "\"," +
                            "\"" + bean.getDateOfRegister() + "\"," +
                            "\"" + bean.getDateOfDeal() + "\"," +
                            "\"" + bean.getTotalAmount() + "\"," +
                            "\"" + bean.getAdvancedPayment() + "\"," +
                            "\"" + bean.getBalancedPayment() + "\"," +
                            "\"" + bean.getCorporationNo() + "\"," +
                            "\"" + bean.getCommissionEngrossed() + "\"\n");
                }

                writer.close();

                if (Desktop.isDesktopSupported()) {
                    Desktop.getDesktop().open(selectedFile);
                    System.out.println("Opening CSV...");
                } else {
                    System.out.println("Desktop actions not supported.");
                }
            } else {
                System.out.println("Export cancelled.");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }




    @FXML
    void initialize() {
        con = mysqldbconnection.MySQLDbconnection();
        if (con == null) {
            System.out.println("Connection error.");
            return;
        }

        statuscombo.getItems().addAll("Show All", "Paid", "Unpaid");

        // Define table columns
        TableColumn<DealBean, Integer> colRid = new TableColumn<>("RID");
        colRid.setCellValueFactory(new PropertyValueFactory<>("rid"));

        TableColumn<DealBean, String> colSC = new TableColumn<>("Seekers Contact");
        colSC.setCellValueFactory(new PropertyValueFactory<>("seekersContact"));

        TableColumn<DealBean, String> colBName = new TableColumn<>("B Name");
        colBName.setCellValueFactory(new PropertyValueFactory<>("bname"));

        TableColumn<DealBean, String> colTC = new TableColumn<>("Traders Contact");
        colTC.setCellValueFactory(new PropertyValueFactory<>("tradersContact"));

        TableColumn<DealBean, String> colSName = new TableColumn<>("S Name");
        colSName.setCellValueFactory(new PropertyValueFactory<>("sname"));

        TableColumn<DealBean, String> colDoR = new TableColumn<>("Date of Register");
        colDoR.setCellValueFactory(new PropertyValueFactory<>("dateOfRegister"));

        TableColumn<DealBean, String> colDoD = new TableColumn<>("Date of Deal");
        colDoD.setCellValueFactory(new PropertyValueFactory<>("dateOfDeal"));

        TableColumn<DealBean, String> colTotal = new TableColumn<>("Total Amount");
        colTotal.setCellValueFactory(new PropertyValueFactory<>("totalAmount"));

        TableColumn<DealBean, String> colAdvance = new TableColumn<>("Advanced Payment");
        colAdvance.setCellValueFactory(new PropertyValueFactory<>("advancedPayment"));

        TableColumn<DealBean, String> colBalance = new TableColumn<>("Balanced Payment");
        colBalance.setCellValueFactory(new PropertyValueFactory<>("balancedPayment"));

        TableColumn<DealBean, String> colCorp = new TableColumn<>("Corporation No");
        colCorp.setCellValueFactory(new PropertyValueFactory<>("corporationNo"));

        TableColumn<DealBean, String> colCommission = new TableColumn<>("Commission Engrossed");
        colCommission.setCellValueFactory(new PropertyValueFactory<>("commissionEngrossed"));

        tableview.getColumns().addAll(colRid, colSC, colBName, colTC, colSName, colDoR, colDoD, colTotal, colAdvance, colBalance, colCorp, colCommission);
    }

}
