package com.example;

import java.util.Scanner;

public class Test7 {

    private static final String USER_FILE_PATH = "users.csv";
    private static final String ADMIN_FILE_PATH = "admins.csv";
    private static final String PRODUCT_FILE_PATH = "products.csv";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Choose an option:");
            System.out.println("1. Create User");
            System.out.println("2. Create Admin");
            System.out.println("3. Login as User");
            System.out.println("4. Login as Admin");
            System.out.println("5. Exit");
            int option = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (option) {
                case 1:
                    createUser(scanner, USER_FILE_PATH, "user");
                    break;
                case 2:
                    createUser(scanner, ADMIN_FILE_PATH, "admin");
                    break;
                case 3:
                    loginUser(scanner, USER_FILE_PATH);
                    break;
                case 4:
                    loginAdmin(scanner, ADMIN_FILE_PATH);
                    break;
                case 5:
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private static void createUser(Scanner scanner, String filePath, String role) {
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();
        CsvUpdater.createUserOrAdmin(filePath, username, password, role);
        System.out.println(role + " created successfully.");
    }

    private static void loginUser(Scanner scanner, String filePath) {
        loginAccount(scanner, filePath, "user");
    }

    private static void loginAdmin(Scanner scanner, String filePath) {
        loginAccount(scanner, filePath, "admin");
    }

    private static void loginAccount(Scanner scanner, String filePath, String role) {
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        boolean isAuthenticated = CsvUpdater.checkCredentials(filePath, username, password, role);

        if (isAuthenticated) {
            System.out.println("Logged in as " + role + ".");
            if ("admin".equals(role)) {
                handleAdminActions(scanner);
            } else {
                handleUserActions(scanner);
            }
        } else {
            System.out.println("Invalid credentials. Please try again.");
        }
    }

    private static void handleAdminActions(Scanner scanner) {
        ProductManager productManager = new ProductManager(PRODUCT_FILE_PATH);

        while (true) {
            System.out.println("Admin Menu:");
            System.out.println("1. View Products");
            System.out.println("2. Add Product");
            System.out.println("3. Update Product");
            System.out.println("4. Delete Product");
            System.out.println("5. Sort Products by Price");
            System.out.println("6. Sort Products by Stock");
            System.out.println("7. Sort Products by Name");
            System.out.println("8. Logout");
            int option = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (option) {
                case 1:
                    productManager.displayProducts();
                    break;
                case 2:
                    addProduct(scanner, productManager);
                    break;
                case 3:
                    updateProduct(scanner, productManager);
                    break;
                case 4:
                    deleteProduct(scanner, productManager);
                    break;
                case 5:
                    productManager.sortProductsByPrice();
                    productManager.displayProducts();
                    break;
                case 6:
                    productManager.sortProductsByStock();
                    productManager.displayProducts();
                    break;
                case 7:
                    productManager.sortProductsByName();
                    productManager.displayProducts();
                    break;
                case 8:
                    return; // Logout
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private static void handleUserActions(Scanner scanner) {
        ProductManager productManager = new ProductManager(PRODUCT_FILE_PATH);

        while (true) {
            System.out.println("User Menu:");
            System.out.println("1. View Products");
            System.out.println("2. Order Product");
            System.out.println("3. Logout");
            int option = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (option) {
                case 1:
                    productManager.displayProducts();
                    break;
                case 2:
                    orderProduct(scanner, productManager);
                    break;
                case 3:
                    return; // Logout
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private static void addProduct(Scanner scanner, ProductManager productManager) {
        System.out.print("Enter product ID: ");
        String id = scanner.nextLine();
        System.out.print("Enter product name: ");
        String name = scanner.nextLine();
        System.out.print("Enter product price: ");
        double price = scanner.nextDouble();
        System.out.print("Enter product stock: ");
        int stock = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        Product product = new Product(id, name, price, stock);
        productManager.addProduct(product);
        System.out.println("Product added successfully.");
    }

    private static void updateProduct(Scanner scanner, ProductManager productManager) {
        System.out.print("Enter product ID to update: ");
        String id = scanner.nextLine();
        System.out.print("Enter new product name: ");
        String name = scanner.nextLine();
        System.out.print("Enter new product price: ");
        double price = scanner.nextDouble();
        System.out.print("Enter new product stock: ");
        int stock = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        Product updatedProduct = new Product(id, name, price, stock);
        productManager.updateProduct(id, updatedProduct);
        System.out.println("Product updated successfully.");
    }

    private static void deleteProduct(Scanner scanner, ProductManager productManager) {
        System.out.print("Enter product ID to delete: ");
        String id = scanner.nextLine();
        productManager.deleteProduct(id);
        System.out.println("Product deleted successfully.");
    }

    private static void orderProduct(Scanner scanner, ProductManager productManager) {
        System.out.print("Enter product ID to order: ");
        String id = scanner.nextLine();
        System.out.print("Enter quantity to order: ");
        int quantity = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        boolean success = productManager.orderProduct(id, quantity);
        if (success) {
            System.out.println("Order placed successfully.");

            // Fetch the product details for the ordered product
            Product orderedProduct = productManager.getProducts().stream()
                    .filter(product -> product.getId().equals(id))
                    .findFirst()
                    .orElse(null);

            if (orderedProduct != null) {
                // Use the OrderDetailsDisplay class to print the order details
                OrderDetailsDisplay.displayOrderDetails(orderedProduct, quantity);
            }
        } else {
            System.out.println("Failed to place order. Check availability and stock.");
        }
    }
}
