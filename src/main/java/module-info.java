module com.example.property {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;
    requires java.sql;
    requires jdk.jdi;
    requires mysql.connector.java;
    requires java.desktop;

    opens com.example.property to javafx.fxml;
    exports com.example.property;

    exports com.example.property.customerreg;
    opens com.example.property.customerreg to javafx.fxml;
    opens com.example.property.customerregg to javafx.fxml;

    exports com.example.property.listproperty;
    opens com.example.property.listproperty to javafx.fxml;
    opens com.example.property.listpropertyy to javafx.fxml;

    exports com.example.property.propertydashboard;
    opens com.example.property.propertydashboard to javafx.fxml;
    opens com.example.property.propertydashboardd to javafx.fxml;

    exports com.example.property.loginboard;
    opens com.example.property.loginboard to javafx.fxml;
    opens com.example.property.loginboardd to javafx.fxml;

    exports com.example.property.buyinterest;
    opens com.example.property.buyinterest to javafx.fxml;
    opens com.example.property.buyinterestt to javafx.fxml;

    exports com.example.property.deals;
    opens com.example.property.deals to javafx.fxml;
    opens com.example.property.dealss to javafx.fxml;

    exports com.example.property.dealfinal;
    opens com.example.property.dealfinal to javafx.fxml;
    opens com.example.property.dealfinall to javafx.fxml;


    exports com.example.property.charts;
    opens com.example.property.charts to javafx.fxml;
    opens com.example.property.chartss to javafx.fxml;

    exports com.example.property.search;
    opens com.example.property.search to javafx.fxml;
    opens com.example.property.searchh to javafx.fxml;

    exports com.example.property.customertable;
    opens com.example.property.customertable to javafx.fxml;
    opens com.example.property.customertablee to javafx.fxml;

    exports com.example.property.amoumtstatustable;
    opens com.example.property.amoumtstatustable to javafx.fxml;
    opens com.example.property.amoumtstatustablee to javafx.fxml;

    exports com.example.property.propertylisttable;
    opens com.example.property.propertylisttable to javafx.fxml;
    opens com.example.property.propertylisttablee to javafx.fxml;

    exports com.example.property.pricechart;
    opens com.example.property.pricechart to javafx.fxml;
    opens com.example.property.pricechartt to javafx.fxml;

    exports com.example.property.areachart;
    opens com.example.property.areachart to javafx.fxml;
    opens com.example.property.areachartt to javafx.fxml;



}