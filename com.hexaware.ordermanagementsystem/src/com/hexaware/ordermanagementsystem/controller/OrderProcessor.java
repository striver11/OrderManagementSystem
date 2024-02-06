package com.hexaware.ordermanagementsystem.controller;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import com.hexaware.ordermanagementsystem.dao.*;
import com.hexaware.ordermanagementsystem.exception.OrderNotFoundException;
import com.hexaware.ordermanagementsystem.exception.UserNotFoundException;
import com.hexaware.ordermanagementsystem.model.User;
import com.hexaware.ordermanagementsystem.model.*;

public class OrderProcessor implements IOrderManagementRepository {
	
	IServiceProviderDao dao = new ServiceProviderDao();
	
	@Override
	public void createUser() {
	        Scanner scanner = new Scanner(System.in);

	        System.out.println("Enter username: ");
	        String username = scanner.nextLine();

	        System.out.println("Enter password: ");
	        String password = scanner.nextLine();

	        System.out.println("Enter role (Admin/User): ");
	        String role = scanner.next();

	      User user=new User(0, username, password, role);
	        dao.addUser(user);

	        System.out.println("User added successfully!");
	    }


	 
	public void getAllProducts() {
	    List<Product> productList = dao.getAllProducts();

	    if (productList.isEmpty()) {
	        System.out.println("No products found.");
	    } else {
	        System.out.println("List of all products:");
	        for (Product product : productList) {
	            System.out.println(product);
	        }
	    }
	}
	 
	 @Override
	 public void getOrderbyUser(int userId) {
	     try {
	         List<Order> orders = dao.getOrderByUser(userId);
	         if (!orders.isEmpty()) {
	             System.out.println("Orders for User ID " + userId + ":");
	             for (Order order : orders) {
	                 System.out.println("Order ID: " + order.getOrderId() + ", Product ID: " + order.getProductId());
	             }
	         } else {
	             System.out.println("No orders found for User ID " + userId);
	         }
	     } catch (InputMismatchException e) {
	         System.out.println("Invalid input. Please enter a valid User ID.");
	     }
	 }
	 @Override
	 public void cancelOrder(int userId, int orderId) {
	     try {
	         dao.cancelOrder(userId, orderId);
	       
	     } catch (UserNotFoundException | OrderNotFoundException e) {
	         System.out.println(e.getMessage());
	     }
	 }
	 public void createOrder(int userId, List<Product> products) {
		    try {
		    	dao.createOrder(userId, products);
		    } catch (UserNotFoundException | OrderNotFoundException e) {
		        System.out.println(e.getMessage());
		    }
		}





}
