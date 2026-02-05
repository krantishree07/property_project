package com.example.property.search;

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
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class searchcontroller {

    @FXML
    private ResourceBundle resources;
    @FXML
    private URL location;

    @FXML
    private ComboBox<String> areacombo;
    @FXML
    private ComboBox<String> citycombo;
    @FXML
    private ComboBox<String> naturecombo;
    @FXML
    private ComboBox<String> propertytypecombo;

    @FXML
    private TextField maxtxt;
    @FXML
    private TextField mintxt;

    @FXML
    private TableView<searchbean> tableview;


    Connection con;

    @FXML
    void doerase(ActionEvent event) {
        mintxt.clear();
        maxtxt.clear();
        areacombo.setValue(null);
        citycombo.setValue(null);
        propertytypecombo.setValue(null);
        naturecombo.setValue(null);
        tableview.getItems().clear();
    }

    @FXML
    void dosearch(ActionEvent event) {
        ObservableList<searchbean> list = FXCollections.observableArrayList();
        try {
            String query = "SELECT rid, price, facing, name, contact, address ,stauts FROM propertylist " +
                    "WHERE price BETWEEN ? AND ? AND area = ? AND city = ? AND type = ?";
            PreparedStatement pst = con.prepareStatement(query);

            int min = Integer.parseInt(mintxt.getText().trim());
            int max = Integer.parseInt(maxtxt.getText().trim());
            String area = areacombo.getValue();
            String city = citycombo.getValue();
            String type = propertytypecombo.getValue();

            pst.setInt(1, min);
            pst.setInt(2, max);
            pst.setString(3, area);
            pst.setString(4, city);
            pst.setString(5, type);

            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                searchbean obj = new searchbean();
                obj.setRid(rs.getInt("rid"));
                obj.setPrice(rs.getInt("price"));
                obj.setFacing(rs.getString("facing"));
                obj.setName(rs.getString("name"));
                obj.setContact(rs.getString("contact"));
                obj.setAddress(rs.getString("address"));
                obj.setStauts(rs.getInt("stauts")); // Correctly stores status

                list.add(obj);
            }
            tableview.setItems(list);

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

        // Populate combo boxes
        propertytypecombo.getItems().addAll("Building", "Plot");
        naturecombo.getItems().addAll("Private", "Public", "Common", "Residential", "Commercial", "Industrial", "Agriculture");
        citycombo.getItems().addAll(
                "Ludhiana", "Amritsar", "Jalandhar", "Patiala", "Bathinda",
                "Mohali", "Firozpur", "Gurdaspur", "Hoshiarpur", "Moga",
                "Fazilka", "Pathankot", "Barnala", "Malerkotla", "Sangrur",
                "Tarn Taran", "Muktsar", "Kapurthala", "Rupnagar", "Mansa"
        );
        areacombo.getItems().addAll(
                "Ghoda Chowk", "Silver Oaks Colony", "Model Town", "Green Avenue",
                "Civil Lines", "Kitchlu Nagar", "Rajguru Nagar", "Ajit Road",
                "Mall Road", "Ranjit Avenue", "Urban Estate", "Guru Teg Bahadur Nagar",
                "Sarai Road", "Bibiwala Road", "Sukhna Enclave", "Shivaji Park",
                "Shastri Nagar", "New Shakti Nagar", "Lamba Patti", "Gill Road"
        );

        // Column setup
        TableColumn<searchbean, Integer> ridC = new TableColumn<>("RID");
        ridC.setCellValueFactory(new PropertyValueFactory<>("rid"));

        TableColumn<searchbean, Integer> priceC = new TableColumn<>("Price");
        priceC.setCellValueFactory(new PropertyValueFactory<>("price"));

        TableColumn<searchbean, String> facingC = new TableColumn<>("Facing");
        facingC.setCellValueFactory(new PropertyValueFactory<>("facing"));

        TableColumn<searchbean, String> nameC = new TableColumn<>("Name");
        nameC.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<searchbean, String> contactC = new TableColumn<>("Contact");
        contactC.setCellValueFactory(new PropertyValueFactory<>("contact"));

        TableColumn<searchbean, String> addressC = new TableColumn<>("Address");
        addressC.setCellValueFactory(new PropertyValueFactory<>("address"));

        TableColumn<searchbean, String> statusCol = new TableColumn<>("Status");
        statusCol.setCellValueFactory(cellData -> {
            int rawStatus = cellData.getValue().getStauts();
            String readable = (rawStatus == 0) ? "Unavailable" : "Available";
            return new javafx.beans.property.SimpleStringProperty(readable);
        });


        tableview.getColumns().clear();
        tableview.getColumns().addAll(ridC, priceC, facingC, nameC, contactC, addressC, statusCol);
    }
}
