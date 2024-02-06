package com.hexaware.ordermanagementsystem.main;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.hexaware.ordermanagementsystem.controller.*;
import com.hexaware.ordermanagementsystem.model.Product;
import com.hexaware.ordermanagementsystem.model.User;

public class OrderManagement {

    public static void main(String[] args) {
    	IOrderManagementRepository orderProcessor = new OrderProcessor();
        Scanner scanner = new Scanner(System.in);

        String ch;
        do {
            System.out.println("Enter your choice:");
            System.out.println("1. Create User");
            System.out.println("2. Create Order");
            System.out.println("3. Cancel Order");
            System.out.println("4. Get All Products");
            System.out.println("5. Get Order by User");
            System.out.println("6. Exit");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    orderProcessor.createUser();
                    break;
                case 2:
                    System.out.println("Enter User Id");
                    int userIdForOrder=scanner.nextInt();                    
                    List<Product> products = new ArrayList<>();
                    // Adding some Product objects to the list
                    products.add(new Product(1, "Laptop", "High-performance laptop", 1200.00, 10, "Electronics"));
                    products.add(new Product(3, "T-Shirt", "Comfortable cotton T-shirt", 20.00, 50, "Clothing"));
                    products.add(new Product(5, "Headphones", "Wireless headphones", 80.00, 12, "Electronics"));
                    
                    orderProcessor.createOrder(userIdForOrder, products);
                    break;
                case 3:
                	System.out.println("please enter userId");
                	int userIdForRemoval=scanner.nextInt();
                	System.out.println("please enter orderId");
                	int orderIdForRemoval=scanner.nextInt();
                	
                    orderProcessor.cancelOrder(userIdForRemoval,orderIdForRemoval);
                    break;
                case 4:
                    orderProcessor.getAllProducts();
                    break;
                case 5:
                	System.out.println("enter UserId");
                	int userId=scanner.nextInt();
                    orderProcessor.getOrderbyUser(userId);
                    break;
                case 6:
                    System.out.println("Exiting...");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice. Please enter a valid option.");
                    break;
            }

            System.out.println("Do you want to continue? Enter 'Y' or 'y' to continue:");
            ch = scanner.nextLine();

        } while (ch.equalsIgnoreCase("Y"));
        
        System.out.println("Thanks for using our system!");
    }
}
