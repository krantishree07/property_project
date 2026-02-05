package com.example.property.customerreg;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;


public class customerregcontroller {

    @FXML
    private ImageView Imagepriview;

    @FXML
    private TextField addresstxt;

    @FXML
    private TextField adhaarnumbertxt;

    @FXML
    private TextField contactnumbertxt;

    @FXML
    private TextField emailidtxt;

    @FXML
    private TextField nametxt;

    @FXML
    private ComboBox<String> occupationcombobox;

    @FXML
    private TextField referredbytxt;
    private String picpath;

    @FXML
    private ComboBox<String> usertypecombo;

    @FXML
    void doclear(ActionEvent event) {
        nametxt.clear();
        contactnumbertxt.clear();
        addresstxt.clear();
        emailidtxt.clear();
        adhaarnumbertxt.clear();
        referredbytxt.clear();
        occupationcombobox.setValue(null);
        Imagepriview.setImage(null);
        picpath = null;
        usertypecombo.setValue(null);
    }

    @FXML
    void dodelete(ActionEvent event) {
        String query = "DELETE FROM customers WHERE contact=?";
        try {
            String contact = contactnumbertxt.getText();

            if (contact == null || contact.trim().isEmpty()) {
                System.out.println("No contact number provided for deletion.");
                return;
            }

            PreparedStatement pst = con.prepareStatement(query);
            pst.setString(1, contact);

            int count = pst.executeUpdate();
            if (count > 0) {
                System.out.println("Record deleted successfully.");
                doclear(event); // Clears the input fields
            } else {
                System.out.println("Deletion failed — no matching contact number found.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @FXML
    void dosave(ActionEvent event) {
        String query = "INSERT INTO customers (name, contact, address, emailid, picpath, adhaarno, occupation, refby ,usertype) VALUES (?, ?, ?, ?, ?, ?, ?, ?,?)";

        try (Connection con = mysqldbconnection.MySQLDbconnection();
             PreparedStatement ps = con.prepareStatement(query)) {

            ps.setString(1, nametxt.getText());
            ps.setString(2, contactnumbertxt.getText());
            ps.setString(3, addresstxt.getText());
            ps.setString(4, emailidtxt.getText());
            ps.setString(5, picpath);
            ps.setString(6, adhaarnumbertxt.getText());
            ps.setString(7, occupationcombobox.getValue());
            ps.setString(8, referredbytxt.getText());
            ps.setString(9,usertypecombo.getValue());

            ps.executeUpdate();
            System.out.println("Record saved.");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    void dosearch(ActionEvent event) {
        String query = "SELECT * FROM customers WHERE contact=?";

        try {
            String contactno = contactnumbertxt.getText();
            if (contactno == null || contactno.isEmpty()) {
                System.out.println("no contact no is selectd");
                return;
            }

            PreparedStatement pst = con.prepareStatement(query);
            pst.setString(1, contactno);  // Changed to setString because contact is VARCHAR

            ResultSet res = pst.executeQuery();

            if (res.next()) {
                nametxt.setText(res.getString("name"));
                contactnumbertxt.setText(res.getString("contact"));
                addresstxt.setText(res.getString("address"));
                adhaarnumbertxt.setText(res.getString("adhaarno"));
                emailidtxt.setText(res.getString("emailid"));
                referredbytxt.setText(res.getString("refby"));
                occupationcombobox.setValue(res.getString("occupation"));
                usertypecombo.setValue(res.getString("usertype"));

                String picp = res.getString("picpath");
                File imgFile = new File(picp);
                if (imgFile.exists()) {
                    Imagepriview.setImage(new Image(new FileInputStream(imgFile)));
                } else {
                    Imagepriview.setImage(null);
                    System.out.println("Image not found: " + picp);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void doupdate(ActionEvent event) {
            String query = "UPDATE customers SET name = ?, address = ?, adhaarno = ?, emailid = ?, picpath = ?, occupation = ?, refby = ?,usertype=? WHERE contact = ?";

            try {
                if (con == null) {
                    System.out.println("Database connection is not initialized.");
                    return;
                }

                if (nametxt.getText().trim().isEmpty()) {
                    System.out.println("Please enter the customer name to update.");
                    return;
                }

                PreparedStatement pst = con.prepareStatement(query);

                pst.setString(1, nametxt.getText().trim());
                pst.setString(2, addresstxt.getText().trim());
                pst.setString(3, adhaarnumbertxt.getText().trim());
                pst.setString(4, emailidtxt.getText().trim());
                pst.setString(5, picpath);
                pst.setString(6, occupationcombobox.getValue());
                pst.setString(7, referredbytxt.getText().trim());
                pst.setString(8,usertypecombo.getValue());
                pst.setString(9, contactnumbertxt.getText().trim());

                int count = pst.executeUpdate();
                if (count == 0) {
                    System.out.println("No customer found with name: " + contactnumbertxt.getText());
                } else {
                    System.out.println("Customer record updated successfully.");
                }

            } catch (Exception exp) {
                System.out.println("Error updating customer: " + exp.getMessage());
                exp.printStackTrace();
            }
        }



    @FXML
    void douploadpic(ActionEvent event) {
        FileChooser chooser = new FileChooser();
        chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.jpg", "*.png"));

        File file = chooser.showOpenDialog(null);
        if (file != null) {
            picpath = file.getAbsolutePath();
            try {
                Image image = new Image(new FileInputStream(file));
                Imagepriview.setImage(image);
                System.out.println("Image uploaded. Path: " + picpath);
            } catch (FileNotFoundException e) {
                System.out.println("Error loading image: " + e.getMessage());
                Imagepriview.setImage(null);
            }
        } else {
            System.out.println("No image selected.");
        }
    }

    Connection con;

    @FXML
    void initialize() {
        con = mysqldbconnection.MySQLDbconnection();
        if (con == null) {
            System.out.println("Connection error.");
        }
        occupationcombobox.getItems().addAll("Students","Teacher","Doctor","Advocate","Ceo"," Manager", "Dancer", "Singer","Painter" ,"Plumber","Model","Actor", " Engineer", "CA", "Police officer" );
   usertypecombo.getItems().addAll("Buyer","Seller","Buyer and Seller");
    }
}

