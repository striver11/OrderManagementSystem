package com.hexaware.ordermanagementsystem.controller;

import java.util.List;

import com.hexaware.ordermanagementsystem.exception.OrderNotFoundException;
import com.hexaware.ordermanagementsystem.exception.UserNotFoundException;
import com.hexaware.ordermanagementsystem.model.Product;
import com.hexaware.ordermanagementsystem.model.User;

public interface IOrderManagementRepository {

	void createUser();







	void getAllProducts();



	void getOrderbyUser(int userId);



	void cancelOrder(int userId, int orderId);


	void createOrder(int userIdForOrder, List<Product> products);

}
