package com.example.property.customertable;

import java.awt.Desktop;
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
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;

public class customertablecontroller {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ComboBox<String> usercombo;

    @FXML
    private TableView<customerbean> tableview;

    Connection con;

    @FXML
    void doshowrecord(ActionEvent event) {
        String selectedType = usercombo.getValue();
        ObservableList<customerbean> list = FXCollections.observableArrayList();

        if (selectedType == null || selectedType.trim().isEmpty()) {
            tableview.setItems(FXCollections.observableArrayList());
            return;
        }

        String query = "Show All".equals(selectedType)
                ? "SELECT * FROM customers"
                : "SELECT * FROM customers WHERE usertype = ?";

        try {
            PreparedStatement pst = con.prepareStatement(query);
            if (!"Show All".equals(selectedType)) {
                pst.setString(1, selectedType);
            }

            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                list.add(new customerbean(
                        rs.getString("contact"),
                        rs.getString("name"),
                        rs.getString("emailid"),
                        rs.getString("address"),
                        rs.getString("adhaarno"),
                        rs.getString("refby"),
                        rs.getString("picpath"),
                        rs.getString("occupation"),
                        rs.getString("usertype")
                ));
            }

            tableview.setItems(list);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void doexporttoexcel(ActionEvent event) {
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

                writer.write("Contact,Name,Email ID,Address,Adhaar No,Ref By,Pic Path,Occupation,User Type\n");

                ObservableList<customerbean> data = tableview.getItems();
                for (customerbean bean : data) {
                    writer.write("\"" + bean.getContact() + "\","
                            + "\"" + bean.getName() + "\","
                            + "\"" + bean.getEmailid() + "\","
                            + "\"" + bean.getAddress() + "\","
                            + "\"" + bean.getAdhaarno() + "\","
                            + "\"" + bean.getRefby() + "\","
                            + "\"" + bean.getPicpath() + "\","
                            + "\"" + bean.getOccupation() + "\","
                            + "\"" + bean.getUsertype() + "\"\n");
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

        usercombo.getItems().addAll("Buyer", "Seller", "Buyer and Seller", "Show All");

        tableview.getColumns().clear();

        TableColumn<customerbean, String> ccontact = new TableColumn<>("Contact");
        ccontact.setCellValueFactory(new PropertyValueFactory<>("contact"));

        TableColumn<customerbean, String> cname = new TableColumn<>("Name");
        cname.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<customerbean, String> cemail = new TableColumn<>("Email ID");
        cemail.setCellValueFactory(new PropertyValueFactory<>("emailid"));

        TableColumn<customerbean, String> caddress = new TableColumn<>("Address");
        caddress.setCellValueFactory(new PropertyValueFactory<>("address"));

        TableColumn<customerbean, String> cadhaar = new TableColumn<>("Adhaar No");
        cadhaar.setCellValueFactory(new PropertyValueFactory<>("adhaarno"));

        TableColumn<customerbean, String> crefby = new TableColumn<>("Ref By");
        crefby.setCellValueFactory(new PropertyValueFactory<>("refby"));

        TableColumn<customerbean, ImageView> cphoto = new TableColumn<>("Photo");
        cphoto.setCellValueFactory(new PropertyValueFactory<>("imageView"));

        TableColumn<customerbean, String> coccupation = new TableColumn<>("Occupation");
        coccupation.setCellValueFactory(new PropertyValueFactory<>("occupation"));

        TableColumn<customerbean, String> cusertype = new TableColumn<>("User Type");
        cusertype.setCellValueFactory(new PropertyValueFactory<>("usertype"));

        tableview.getColumns().addAll(ccontact, cname, cemail, caddress, cadhaar, crefby,
                cphoto, coccupation, cusertype);
    }
}
