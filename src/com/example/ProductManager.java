package com.example;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ProductManager {
    private List<Product> products;
    private String filePath;

    public ProductManager(String filePath) {
        this.filePath = filePath;
        this.products = new ArrayList<>();
        loadProducts();
    }

    private void loadProducts() {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            boolean isFirstLine = true; // Flag to skip header
            while ((line = br.readLine()) != null) {
                if (isFirstLine) {
                    isFirstLine = false; // Skip the header line
                    continue;
                }
                String[] values = line.split(",");
                if (values.length == 4) {
                    String id = values[0];
                    String name = values[1];
                    double price = Double.parseDouble(values[2]);
                    int stock = Integer.parseInt(values[3]);
                    products.add(new Product(id, name, price, stock));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveProducts() {
        try (FileWriter writer = new FileWriter(filePath)) {
            writer.write("ID,Name,Price,Stock\n"); // Write the header
            for (Product product : products) {
                writer.write(product.getId() + "," + product.getName() + "," + product.getPrice() + "," + product.getStock() + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Product> getProducts() {
        return products;
    }

    public void displayProducts() {
        System.out.println("Product Detail");
        System.out.printf("-----------------------------------------------------------------------------------%n");
        System.out.printf("| %-11s | %-25s | %-12s | %12s| %6s| %n", "Product Id", "Product Name", "Price", "IsAvailable", "Stock");
        System.out.printf("-----------------------------------------------------------------------------------%n");
        for (Product product : products) {
            System.out.printf("| %-11s | %-25s | %-12s | %-12s| %-6s| %n",
                    product.getId(), product.getName(), product.getPrice(), product.isAvailable(), product.getStock());
        }
        System.out.printf("-----------------------------------------------------------------------------------%n");
    }

    public void addProduct(Product product) {
        products.add(product);
        saveProducts();
    }

    public void updateProduct(String id, Product updatedProduct) {
        for (int i = 0; i < products.size(); i++) {
            if (products.get(i).getId().equals(id)) {
                products.set(i, updatedProduct);
                saveProducts();
                return;
            }
        }
    }

    public void deleteProduct(String id) {
        products.removeIf(product -> product.getId().equals(id));
        saveProducts();
    }

    public boolean orderProduct(String id, int quantity) {
        for (Product product : products) {
            if (product.getId().equals(id) && product.getStock() >= quantity) {
                product.setStock(product.getStock() - quantity);
                saveProducts();
                return true;
            }
        }
        return false;
    }

    public void sortProductsByPrice() {
        products.sort(Comparator.comparingDouble(Product::getPrice));
    }

    public void sortProductsByStock() {
        products.sort(Comparator.comparingInt(Product::getStock));
    }

    public void sortProductsByName() {
        products.sort(Comparator.comparing(Product::getName));
    }
}
