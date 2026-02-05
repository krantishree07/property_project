package com.example.property;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        //FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("customerregg/customerregview.fxml"));
       // FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("listpropertyy/listpropertyview.fxml"));
      //  FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("propertydashboardd/propertydashboardview.fxml"));
       FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("loginboardd/loginboardview.fxml"));
      //  FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("buyinterestt/buyinterestview.fxml"));
     //6 FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("dealss/dealsview.fxml"));
     //  FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("dealfinall/dealfinalview.fxml"));
     //8   FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("chartss/chartsview.fxml"));
     //9   FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("searchh/searchview.fxml"));
     //   FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("customertablee/customertableview.fxml"));
// 11       FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("amoumtstatustablee/amoumtstatustableview.fxml"));
     //   FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("propertylisttablee/propertylisttableview.fxml"));
      //  FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("pricechartt/pricechartview.fxml"));
     //   FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("areachartt/areachartview.fxml"));

        Scene scene = new Scene(fxmlLoader.load(), 646, 556);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}