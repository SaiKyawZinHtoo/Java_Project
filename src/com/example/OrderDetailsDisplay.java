package com.example;

public class OrderDetailsDisplay {

    public static void displayOrderDetails(Product orderedProduct, int quantity) {
        System.out.println("Order Details:");
        System.out.printf("%n");
        System.out.printf("-----------------------------------------------------------------------------------%n");
        System.out.printf("| %-11s | %-25s | %12s | %6s | %6s | %n", "Product ID", "Product Name", "Quantity", "Price", "Total");
        System.out.printf("-----------------------------------------------------------------------------------%n");
        System.out.printf("| %-11s | %-25s | %12d | %6.2f | %6.2f | %n",
                orderedProduct.getId(),
                orderedProduct.getName(),
                quantity,
                orderedProduct.getPrice(),
                orderedProduct.getPrice() * quantity
        );
        System.out.printf("-----------------------------------------------------------------------------------%n");
        System.out.printf("Remaining Stock: %d%n", orderedProduct.getStock());
    }
}
