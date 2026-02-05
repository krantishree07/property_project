package com.example.property.customerreg;
import java.sql.Connection;
import java.sql.DriverManager;

public class mysqldbconnection
{
    public static Connection MySQLDbconnection()
    {
        Connection con=null;
        try
        {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con= DriverManager.getConnection("jdbc:mysql://localhost/property2025java","root","pass123");
            System.out.println("Network established");
        }
        catch(Exception exp)
        {
            System.out.println(exp.toString());
        }
        return con;
    }
}