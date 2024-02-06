package com.hexaware.ordermanagementsystem.util;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.hexaware.ordermanagementsystem.exception.*;

public class DBUtil {
	static Connection con;
    public static Connection getDBConn() throws DataBaseConnectionException {
       
        try {
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/OrderManagementSystem", "root", "root");           
        } catch (SQLException e) {

            throw new DataBaseConnectionException("Error connecting to the database");
        }

        return con;
    }

    public static void main(String[] args) {
        try {
            Connection connection = getDBConn();               
            System.out.println("Connected to the database successfully.");                
            connection.close();
            
        } catch (DataBaseConnectionException | SQLException e) {      
            System.out.println("Error: " + e.getMessage());
        }
    }
}
