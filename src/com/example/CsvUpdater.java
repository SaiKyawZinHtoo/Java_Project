package com.example;

import java.io.*;
import java.util.Base64;

public class CsvUpdater {

    public static void createUserOrAdmin(String filePath, String username, String password, String role) {
        if (doesUserExist("users.csv", username) || doesUserExist("admins.csv", username)) {
            System.out.println("Username already exists. Please choose a different username.");
            return;
        }

        byte[] salt = PasswordUtil.generateSalt();
        String hashedPassword = PasswordUtil.hashPassword(password, salt);
        String saltBase64 = Base64.getEncoder().encodeToString(salt);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
            writer.write(username + "," + hashedPassword + "," + saltBase64 + "," + role);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static boolean checkCredentials(String filePath, String username, String password, String role) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 4) {
                    String storedUsername = parts[0];
                    String storedPassword = parts[1];
                    byte[] salt = Base64.getDecoder().decode(parts[2]);
                    String storedRole = parts[3];

                    if (storedUsername.equals(username) && storedRole.equals(role)) {
                        return PasswordUtil.verifyPassword(password, storedPassword, salt);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean doesUserExist(String filePath, String username) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 1 && parts[0].equals(username)) {
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}
