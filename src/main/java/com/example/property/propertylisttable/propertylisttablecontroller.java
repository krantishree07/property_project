package com.example.property.propertylisttable;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;
import java.io.File;
import java.io.FileWriter;
import java.awt.Desktop;
import javafx.stage.FileChooser;

import com.example.property.customerreg.mysqldbconnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class propertylisttablecontroller {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ComboBox<String> propertytypecombo;

    @FXML
    private TableView<propertybean> tableview;

    Connection con;

    @FXML
    void doshowrecords(ActionEvent event) {
        String selectedType = propertytypecombo.getValue();
        ObservableList<propertybean> list = FXCollections.observableArrayList();
        String query;

        if ("Building".equals(selectedType)) {
            query = "SELECT * FROM propertylist WHERE type = 'Building'";
        } else if ("Plot".equals(selectedType)) {
            query = "SELECT * FROM propertylist WHERE type = 'Plot'";
        } else {
            query = "SELECT * FROM propertylist";
        }

        try {
            PreparedStatement pst = con.prepareStatement(query);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                String status = rs.getInt("stauts") == 1 ? "Available" : "Unavailable";
                propertybean bean = new propertybean(
                        rs.getInt("rid"),
                        rs.getString("contact"),
                        rs.getString("name"),
                        rs.getString("type"),
                        rs.getString("nature"),
                        rs.getString("facing"),
                        rs.getFloat("rightdim"),
                        rs.getFloat("leftdim"),
                        rs.getFloat("frontdim"),
                        rs.getFloat("backdim"),
                        rs.getString("address"),
                        rs.getString("area"),
                        rs.getString("city"),
                        rs.getString("corporationno"),
                        rs.getFloat("size"),
                        rs.getInt("price"),
                        status
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

                // Write CSV header
                writer.write("RID,Contact,Name,Type,Nature,Facing,RightDim,LeftDim,FrontDim,BackDim,Address,Area,City,CorporationNo,Size,Price,Status\n");

                // Write data rows
                ObservableList<propertybean> data = tableview.getItems();
                for (propertybean bean : data) {
                    writer.write("\"" + bean.getRid() + "\"," +
                            "\"" + bean.getContact() + "\"," +
                            "\"" + bean.getName() + "\"," +
                            "\"" + bean.getType() + "\"," +
                            "\"" + bean.getNature() + "\"," +
                            "\"" + bean.getFacing() + "\"," +
                            "\"" + bean.getRightdim() + "\"," +
                            "\"" + bean.getLeftdim() + "\"," +
                            "\"" + bean.getFrontdim() + "\"," +
                            "\"" + bean.getBackdim() + "\"," +
                            "\"" + bean.getAddress() + "\"," +
                            "\"" + bean.getArea() + "\"," +
                            "\"" + bean.getCity() + "\"," +
                            "\"" + bean.getCorporationno() + "\"," +
                            "\"" + bean.getSize() + "\"," +
                            "\"" + bean.getPrice() + "\"," +
                            "\"" + bean.getStauts() + "\"\n");
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


    private <T> TableColumn<propertybean, T> createColumn(String title, String property, Class<T> type) {
        TableColumn<propertybean, T> col = new TableColumn<>(title);
        col.setCellValueFactory(new PropertyValueFactory<>(property));
        return col;
    }

    @FXML
    void initialize() {
        con = mysqldbconnection.MySQLDbconnection();
        if (con == null) {
            System.out.println("Connection error.");
            return;
        }

        propertytypecombo.getItems().addAll("Building", "Plot", "Show All");

        tableview.getColumns().clear();
        tableview.getColumns().addAll(
                createColumn("RID", "rid", Integer.class),
                createColumn("Contact", "contact", String.class),
                createColumn("Name", "name", String.class),
                createColumn("Type", "type", String.class),
                createColumn("Nature", "nature", String.class),
                createColumn("Facing", "facing", String.class),
                createColumn("Right", "rightdim", Float.class),
                createColumn("Left", "leftdim", Float.class),
                createColumn("Front", "frontdim", Float.class),
                createColumn("Back", "backdim", Float.class),
                createColumn("Address", "address", String.class),
                createColumn("Area", "area", String.class),
                createColumn("City", "city", String.class),
                createColumn("Corporation No", "corporationno", String.class),
                createColumn("Size", "size", Float.class),
                createColumn("Price", "price", Integer.class),
                createColumn("Status", "stauts", String.class)
        );
    }
}
