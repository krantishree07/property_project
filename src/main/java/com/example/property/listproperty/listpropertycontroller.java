package com.example.property.listproperty;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

import com.example.property.customerreg.mysqldbconnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

public class listpropertycontroller {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML private TextField adresstxt;
    @FXML private ComboBox<String> areatxt;
    @FXML private TextField backtxt;
    @FXML private ComboBox<String> citytxt;
    @FXML
    private ComboBox<String> corporationcombo;
    @FXML private ComboBox<String> facingcomobo;
    @FXML private TextField fronttxt;
    @FXML private TextField lefttxt;
    @FXML private ComboBox<String> mobilenocombo;
    @FXML private TextField nametxt;
    @FXML private ComboBox<String> naturecombo;
    @FXML private ComboBox<String> propertytypecombo;
    @FXML private TextField righttxt;
    @FXML private TextField sizetxt;
    @FXML private TextField pricetxt;
    @FXML private ComboBox<String> ridcoombo;



    Connection con;

    @FXML
    void clickname(ActionEvent event) {
        {
            String selItem=mobilenocombo.getSelectionModel().getSelectedItem();
           try{
               PreparedStatement pst= con.prepareStatement("select *from customers where contact=? ");
               pst.setString(1,selItem);
               ResultSet rst= pst.executeQuery();
               while(rst.next()){
                   String name = rst.getString("name");
                   nametxt.setText(name);

               }
           } catch (Exception e) {
               e.printStackTrace();
           }
        }
    }

    @FXML
    void doclear(ActionEvent event) {
        mobilenocombo.setValue(null);
        propertytypecombo.setValue(null);
        naturecombo.setValue(null);
        facingcomobo.setValue(null);
        areatxt.setValue(null);
        citytxt.setValue(null);
        nametxt.setText(null);
        nametxt.setDisable(false);
        righttxt.setText(null);
        righttxt.setDisable(false);
        lefttxt.setText(null);
        lefttxt.setDisable(false);
        fronttxt.setText(null);
        fronttxt.setDisable(false);
        backtxt.setText(null);
        backtxt.setDisable(false);
        adresstxt.setText(null);
       adresstxt.setDisable(false);
      corporationcombo.setValue(null);
       corporationcombo.setDisable(false);
        sizetxt.setText(null);
        pricetxt.setText(null);
        ridcoombo.setValue(null);


    }

    @FXML
    void dodelete(ActionEvent event) {
        String query = "DELETE FROM propertylist WHERE rid=?";
        try {
            String rid = ridcoombo.getValue();
            if (ridcoombo== null) {
                System.out.println("No  RID selected for deletion.");
                return;
            }

            PreparedStatement pst = con.prepareStatement(query);
            pst.setString(1, rid);

            int count = pst.executeUpdate();
            if (count > 0) {
                System.out.println(" record deleted successfully.");
                ridcoombo.getItems().remove(rid);
                doclear(event);
            } else {
                System.out.println("Deletion failed — no matching RID number.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void dosave(ActionEvent event) {
        String query = "INSERT INTO propertylist (contact, name, type, nature, facing, rightdim, leftdim, frontdim, backdim, address, area, city, corporationno, size, price, stauts) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection con = mysqldbconnection.MySQLDbconnection();
             PreparedStatement ps = con.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, mobilenocombo.getValue());
            ps.setString(2, nametxt.getText());
            ps.setString(3, propertytypecombo.getValue());
            ps.setString(4, naturecombo.getValue());
            ps.setString(5, facingcomobo.getValue());
            ps.setFloat(6, Float.parseFloat(righttxt.getText()));
            ps.setFloat(7, Float.parseFloat(lefttxt.getText()));
            ps.setFloat(8, Float.parseFloat(fronttxt.getText()));
            ps.setFloat(9, Float.parseFloat(backtxt.getText()));
            ps.setString(10, adresstxt.getText());
            ps.setString(11, areatxt.getValue());
            ps.setString(12, citytxt.getValue());
            ps.setString(13, corporationcombo.getValue());
            ps.setFloat(14, Float.parseFloat(sizetxt.getText()));
            ps.setFloat(15, Float.parseFloat(pricetxt.getText()));
            ps.setInt(16, 1); // stauts

            ps.executeUpdate();

            ResultSet keys = ps.getGeneratedKeys();
            if (keys.next()) {
                int generatedRid = keys.getInt(1);

                javafx.application.Platform.runLater(() -> {
                    javafx.scene.control.Alert alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.INFORMATION);
                    alert.setTitle("Record Saved");
                    alert.setHeaderText("Property ID Generated");
                    alert.setContentText("Record saved successfully.\nAuto-generated ID (rid): " + generatedRid);
                    alert.showAndWait();
                });

                ridcoombo.getItems().add(String.valueOf(generatedRid));
                System.out.println("Record saved. Generated ID: " + generatedRid);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }



    @FXML
    void doupdate(ActionEvent event) {
        String query = "UPDATE propertylist SET contact=?, name=?, type=?, nature=?, facing=?, rightdim=?, leftdim=?, frontdim=?, backdim=?, address=?, area=?, city=?, corporationno=?, size=?, price=?, stauts=? WHERE rid=?";

        try {
            if (con == null) {
                System.out.println("Database connection is not initialized.");
                return;
            }

            String ridStr = ridcoombo.getValue();
            if (ridStr == null || ridStr.trim().isEmpty()) {
                System.out.println("Please select a valid RID to update.");
                return;
            }

            int rid = Integer.parseInt(ridStr);

            PreparedStatement pst = con.prepareStatement(query);

            pst.setString(1, mobilenocombo.getValue());
            pst.setString(2, nametxt.getText().trim());
            pst.setString(3, propertytypecombo.getValue());
            pst.setString(4, naturecombo.getValue());
            pst.setString(5, facingcomobo.getValue());
            pst.setFloat(6, Float.parseFloat(righttxt.getText()));
            pst.setFloat(7, Float.parseFloat(lefttxt.getText()));
            pst.setFloat(8, Float.parseFloat(fronttxt.getText()));
            pst.setFloat(9, Float.parseFloat(backtxt.getText()));
            pst.setString(10, adresstxt.getText().trim());
            pst.setString(11, areatxt.getValue());
            pst.setString(12, citytxt.getValue());
            pst.setString(13, corporationcombo.getValue());
            pst.setFloat(14, Float.parseFloat(sizetxt.getText()));
            pst.setInt(15, Integer.parseInt(pricetxt.getText()));
            pst.setInt(16, 1); // status
            pst.setInt(17, rid); // WHERE condition

            int count = pst.executeUpdate();
            if (count == 0) {
                System.out.println("No property found with RID: " + rid);
            } else {
                System.out.println("Property record updated successfully.");
            }

        } catch (Exception exp) {
            System.out.println("Error updating property: " + exp.getMessage());
            exp.printStackTrace();
        }
    }

    @FXML
    void dosearch(ActionEvent event) {
        String query = "SELECT * FROM propertylist WHERE rid=?";

        try {
            String ridStr = ridcoombo.getValue();
            if (ridStr == null || ridStr.isEmpty()) {
                System.out.println("No RID entered");
                return;
            }

            int rid = Integer.parseInt(ridStr);

            PreparedStatement pst = con.prepareStatement(query);
            pst.setInt(1, rid);

            ResultSet res = pst.executeQuery();

            if (res.next()) {
                mobilenocombo.setValue(res.getString("contact"));
                nametxt.setText(res.getString("name"));
                propertytypecombo.setValue(res.getString("type"));
                naturecombo.setValue(res.getString("nature"));
                facingcomobo.setValue(res.getString("facing"));
                righttxt.setText(String.valueOf(res.getFloat("rightdim")));
                lefttxt.setText(String.valueOf(res.getFloat("leftdim")));
                fronttxt.setText(String.valueOf(res.getFloat("frontdim")));
                backtxt.setText(String.valueOf(res.getFloat("backdim")));
                adresstxt.setText(res.getString("address"));
                areatxt.setValue(res.getString("area"));
                citytxt.setValue(res.getString("city"));
                corporationcombo.setValue(res.getString("corporationno"));
                sizetxt.setText(String.valueOf(res.getFloat("size")));
                pricetxt.setText(String.valueOf(res.getFloat("price")));
            } else {
                System.out.println("No property found with RID: " + rid);
            }
        } catch (NumberFormatException nfe) {
            System.out.println("Invalid RID format");
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
        try {
            PreparedStatement stmt = con.prepareStatement( "SELECT DISTINCT contact FROM customers WHERE usertype IN ('Seller', 'Buyer and Seller')"
            );
            ResultSet res = stmt.executeQuery();
            while (res.next()) {
                String contact = res.getString("contact");
                mobilenocombo.getItems().add(contact);
            }
            PreparedStatement ridStmt = con.prepareStatement("SELECT rid FROM propertylist ORDER BY rid ASC");
            ResultSet ridRes = ridStmt.executeQuery();
            while (ridRes.next()) {
                ridcoombo.getItems().add(String.valueOf(ridRes.getInt("rid")));
            }

        } catch (Exception exp) {
            exp.printStackTrace();
        }
        propertytypecombo.getItems().addAll("Building","Plot");
        naturecombo.getItems().addAll("Private","Public","Common","Residentila","Commercial","Industrial","Agriculture");
        facingcomobo.getItems().addAll("North","South","East","West","North-East","North-West","South-East","South-West");
        citytxt.getItems().addAll(
                "Ludhiana", "Amritsar", "Jalandhar", "Patiala", "Bathinda",
                "Mohali", "Firozpur", "Gurdaspur", "Hoshiarpur", "Moga",
                "Fazilka", "Pathankot", "Barnala", "Malerkotla", "Sangrur",
                "Tarn Taran", "Muktsar", "Kapurthala", "Rupnagar", "Mansa");
        areatxt.getItems().addAll(
                "Ghoda Chowk", "Silver Oaks Colony", "Model Town", "Green Avenue",
                "Civil Lines", "Kitchlu Nagar", "Rajguru Nagar", "Ajit Road",
                "Mall Road", "Ranjit Avenue", "Urban Estate", "Guru Teg Bahadur Nagar",
                "Sarai Road", "Bibiwala Road", "Sukhna Enclave", "Shivaji Park",
                "Shastri Nagar", "New Shakti Nagar", "Lamba Patti", "Gill Road"
        );
        corporationcombo.getItems().addAll(
                "2345763245",
                "9876543210",
                "8765432109",
                "7654321098"
        );




    }
}
