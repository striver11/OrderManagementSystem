package com.hexaware.ordermanagementsystem.dao;

import java.util.List;

import com.hexaware.ordermanagementsystem.exception.OrderNotFoundException;
import com.hexaware.ordermanagementsystem.exception.UserNotFoundException;
import com.hexaware.ordermanagementsystem.model.Order;
import com.hexaware.ordermanagementsystem.model.Product;
import com.hexaware.ordermanagementsystem.model.User;

public interface IServiceProviderDao {

	void addUser(User user);

	




	List<Product> getAllProducts();



	List<Order> getOrderByUser(int userId);



	void cancelOrder(int userId, int orderId) throws UserNotFoundException, OrderNotFoundException;






	void createOrder(int userId, List<Product> products) throws UserNotFoundException, OrderNotFoundException;






	

}
