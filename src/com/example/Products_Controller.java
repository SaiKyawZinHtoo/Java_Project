package com.example;

import java.util.ArrayList;

public class Products_Controller implements Products_Controller_Interface {
    String filePath = "src/products.csv";

    @Override
    public void viewProducts(ArrayList<Product> products) {
        System.out.println("Product Details");
        System.out.printf("-----------------------------------------------------------------------------------%n");
        System.out.printf("| %-11s | %-25s | %-12s | %-12s | %-6s |%n", "Product Id", "Product Name", "Price", "Is Available", "Stock");
        System.out.printf("-----------------------------------------------------------------------------------%n");
        for (Product product : products) {
            System.out.printf("| %-11s | %-25s | %-12.2f | %-12s | %-6d |%n", product.getId(), product.getName(), product.getPrice(), product.isAvailable() ? "Yes" : "No", product.getStock());
        }
        System.out.printf("-----------------------------------------------------------------------------------%n");
    }
}
