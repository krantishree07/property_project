package com.example.property.areachart;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

import com.example.property.customerreg.mysqldbconnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;

public class areachartcontroller {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private PieChart chart;

    private Connection con;

    void FillChart() {
        ObservableList<PieChart.Data> pieData = FXCollections.observableArrayList();

        try {
            String query = "SELECT area, city, COUNT(*) AS count FROM propertylist GROUP BY area, city";
            PreparedStatement pst = con.prepareStatement(query);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                String area = rs.getString("area");
                String city = rs.getString("city");
                int count = rs.getInt("count");

                // Label format: Area (City)
                String label = area + " (" + city + ")";
                PieChart.Data slice = new PieChart.Data(label, count);
                pieData.add(slice);
            }

            chart.setTitle("Property Distribution by Area");
            chart.setData(pieData);
            chart.setLabelsVisible(true);

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

        FillChart();
    }
}
