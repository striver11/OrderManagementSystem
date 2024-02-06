package com.hexaware.ordermanagementsystem.dao;

import java.beans.Statement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.hexaware.ordermanagementsystem.exception.OrderNotFoundException;
import com.hexaware.ordermanagementsystem.exception.UserNotFoundException;
import com.hexaware.ordermanagementsystem.model.Clothing;
import com.hexaware.ordermanagementsystem.model.Electronics;
import com.hexaware.ordermanagementsystem.model.Order;
import com.hexaware.ordermanagementsystem.model.Product;
import com.hexaware.ordermanagementsystem.model.User;
import com.hexaware.ordermanagementsystem.util.DBUtil;

public class ServiceProviderDao implements IServiceProviderDao {
	Connection con;
    PreparedStatement ps;
    ResultSet rs;
	@Override
    public void addUser(User user) {
        try {
            con = DBUtil.getDBConn();
            ps = con.prepareStatement(
                    "INSERT INTO User (userId, username, password, role) VALUES (?, ?, ?, ?)");
            ps.setInt(1, user.getUserId());
            ps.setString(2, user.getUserName());
            ps.setString(3, user.getPassword());
            ps.setString(4, user.getRole());

            int noOfRows = ps.executeUpdate();
            System.out.println(noOfRows + " inserted Successfully !!!");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResources();
        }
    }
	
	
	  
	    
	@Override
    public List<Product> getAllProducts() {
        List<Product> productList = new ArrayList<>();

        try {
            con = DBUtil.getDBConn();
            String query = "SELECT " +
                           "p.productId, p.productName, p.description, p.price, p.quantityInStock, p.type, " +
                           "e.brand, e.warrantyPeriod, " +
                           "c.size, c.color " +
                           "FROM Product p " +
                           "LEFT JOIN Electronics e ON p.productId = e.productId " +
                           "LEFT JOIN Clothing c ON p.productId = c.productId";
            try (PreparedStatement ps = con.prepareStatement(query)) {
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        int productId = rs.getInt("productId");
                        String productName = rs.getString("productName");
                        String description = rs.getString("description");
                        double price = rs.getDouble("price");
                        int quantityInStock = rs.getInt("quantityInStock");
                        String type = rs.getString("type");

                        String brand = rs.getString("brand");
                        int warrantyPeriod = rs.getInt("warrantyPeriod");
                        String size = rs.getString("size");
                        String color = rs.getString("color");

                        if ("Electronics".equalsIgnoreCase(type)) {
                            productList.add(new Electronics(productId, productName, description, price, quantityInStock, type, brand, warrantyPeriod));
                        } else if ("Clothing".equalsIgnoreCase(type)) {
                            productList.add(new Clothing(productId, productName, description, price, quantityInStock, type, size, color));
                        } else {
                            productList.add(new Product(productId, productName, description, price, quantityInStock, type));
                        }
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResources();
        }

        return productList;
    }

	  
	  

	    private boolean userExists(int userId) throws SQLException {
	        String userExistsQuery = "SELECT * FROM User WHERE userId = ?";
	        try (PreparedStatement userExistsStatement = con.prepareStatement(userExistsQuery)) {
	            userExistsStatement.setInt(1, userId);
	            try (ResultSet resultSet = userExistsStatement.executeQuery()) {
	                return resultSet.next();
	            }
	        }
	    }
	    
	    @Override
	    public List<Order> getOrderByUser(int userId) {
	        List<Order> orders = new ArrayList<>();
	        try {
	            con = DBUtil.getDBConn();
	            String getOrderQuery = "SELECT * FROM orders WHERE userId = ?";
	            try (PreparedStatement getOrderStatement = con.prepareStatement(getOrderQuery)) {
	                getOrderStatement.setInt(1, userId);
	                try (ResultSet resultSet = getOrderStatement.executeQuery()) {
	                    while (resultSet.next()) {
	                        int orderId = resultSet.getInt("orderId");
	                        int productId = resultSet.getInt("productId");
	                        orders.add(new Order(orderId, productId, userId));
	                    }
	                }
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        } finally {
	            closeResources();
	        }
	        return orders;
	    }
	    @Override
	    public void cancelOrder(int userId, int orderId) throws UserNotFoundException, OrderNotFoundException {
	        try {
	            con = DBUtil.getDBConn();

	            if (!userExists(userId)) {
	                throw new UserNotFoundException("User with ID " + userId + " not found in the database.");
	            }

	            if (!orderExists(orderId)) {
	                throw new OrderNotFoundException("Order with ID " + orderId + " not found in the database.");
	            }

	            String cancelOrderQuery = "DELETE FROM orders WHERE userId = ? AND orderId = ?";
	            try (PreparedStatement cancelOrderStatement = con.prepareStatement(cancelOrderQuery)) {
	                cancelOrderStatement.setInt(1, userId);
	                cancelOrderStatement.setInt(2, orderId);
	                int rowsAffected = cancelOrderStatement.executeUpdate();
	                if (rowsAffected > 0) {
	                    System.out.println("Order canceled successfully!");
	                } else {
	                    System.out.println("No orders found for User ID " + userId + " and Order ID " + orderId);
	                }
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        } finally {
	            closeResources();
	        }
	    }
	    
	    private boolean orderExists(int orderId) throws SQLException {
	        String orderExistsQuery = "SELECT * FROM orders WHERE orderId = ?";
	        try (PreparedStatement orderExistsStatement = con.prepareStatement(orderExistsQuery)) {
	            orderExistsStatement.setInt(1, orderId);
	            try (ResultSet resultSet = orderExistsStatement.executeQuery()) {
	                return resultSet.next();
	            }
	        }
	    }
	    
	    
	    @Override
	    public void createOrder(int userId, List<Product> products) throws UserNotFoundException, OrderNotFoundException {
	        try {
	            con = DBUtil.getDBConn();

	            // Check if the user exists
	            if (!userExists(userId)) {
	                throw new UserNotFoundException("User with ID " + userId + " not found in the database.");
	            }

	            // Create the order
	            String insertOrderQuery = "INSERT INTO orders (productId, userId) VALUES (?, ?)";
	            try (PreparedStatement orderStatement = con.prepareStatement(insertOrderQuery)) {
	                for (Product product : products) {
	                    int productId = product.getProductId();
	                    orderStatement.setInt(1, productId);
	                    orderStatement.setInt(2, userId);
	                    orderStatement.executeUpdate();
	                }
	            }

	            System.out.println("Order created successfully!");

	        } catch (SQLException e) {
	            e.printStackTrace();
	        } finally {
	            closeResources();
	        }
	    }

    private void closeResources() {
        try {
            if (rs != null) {
                rs.close();
            }
            if (ps != null) {
                ps.close();
            }
            if (con != null) {
                con.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


		

	



}
