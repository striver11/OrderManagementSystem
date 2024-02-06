package com.hexaware.ordermanagementsystem.model;

public class Clothing extends Product {
    private String size;
    private String color;

   
    public Clothing(int productId, String productName, String description, double price, int quantityInStock, String type, String size, String color) {
        super(productId, productName, description, price, quantityInStock, type);
        this.size = size;
        this.color = color;
    }


    @Override
    public String toString() {
        return "Clothing [productId=" + getProductId() +
               ", productName=" + getProductName() +
               ", description=" + getDescription() +
               ", price=" + getPrice() +
               ", quantityInStock=" + getQuantityInStock() +
               ", type=" + getType() +
               ", size=" + (size != null ? size : "Not available") +
               ", color=" + (color != null ? color : "Not available") + "]";
    }	

	public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
