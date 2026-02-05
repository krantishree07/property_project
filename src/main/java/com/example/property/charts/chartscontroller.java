package com.example.property.charts;

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

public class chartscontroller {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private PieChart bschart;

    private Connection con;

    void FillChart() {
        ObservableList<PieChart.Data> list = FXCollections.observableArrayList();

        try {
            String query = "SELECT usertype, COUNT(*) AS count FROM customers GROUP BY usertype";
            PreparedStatement stmt = con.prepareStatement(query);
            ResultSet res = stmt.executeQuery();

            while (res.next()) {
                String area = res.getString("usertype");
                int count = res.getInt("count");

                PieChart.Data slice = new PieChart.Data(area, count);
                list.add(slice);
            }

            bschart.setTitle("Distribution by Usertype");
            bschart.setData(list);

        } catch (Exception exp) {
            exp.printStackTrace();
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
