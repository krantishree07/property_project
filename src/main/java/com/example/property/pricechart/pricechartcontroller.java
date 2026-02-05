package com.example.property.pricechart;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

import com.example.property.customerreg.mysqldbconnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

public class pricechartcontroller {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private BarChart<String, Number> barchart;

    private Connection con;

    void FillChart() {
        ObservableList<XYChart.Data<String, Number>> dataList = FXCollections.observableArrayList();

        int range1 = 0, range2 = 0, range3 = 0, range4 = 0;

        try {
            String query = "SELECT price FROM propertylist";
            PreparedStatement stmt = con.prepareStatement(query);
            ResultSet res = stmt.executeQuery();

            while (res.next()) {
                int price = res.getInt("price");
                if (price <= 2000000) range1++;
                else if (price <= 5000000) range2++;
                else if (price <= 7500000) range3++;
                else range4++;
            }


            NumberAxis yAxis = (NumberAxis) barchart.getYAxis();
            yAxis.setTickUnit(1);
            yAxis.setMinorTickCount(0);
            yAxis.setForceZeroInRange(true);
            yAxis.setAutoRanging(false);
            yAxis.setLowerBound(0);

            int maxCount = Math.max(Math.max(range1, range2), Math.max(range3, range4));
            yAxis.setUpperBound(maxCount + 2);


            dataList.add(new XYChart.Data<>("₹0–20L", range1));
            dataList.add(new XYChart.Data<>("₹20–50L", range2));
            dataList.add(new XYChart.Data<>("₹50–75L", range3));
            dataList.add(new XYChart.Data<>("₹75L+", range4));

            XYChart.Series<String, Number> series = new XYChart.Series<>();
            series.setName("Price Distribution");
            series.getData().addAll(dataList);

            barchart.setTitle("Properties by Price Range");
            barchart.getData().add(series);

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
