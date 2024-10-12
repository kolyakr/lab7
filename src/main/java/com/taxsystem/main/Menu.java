package com.taxsystem.main;

import java.util.Scanner;

public class Menu {
    public void display() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\n1. Add Income");
            System.out.println("2. Delete Income");
            System.out.println("3. Calculate Taxes");
            System.out.println("4. Sort Taxes");
            System.out.println("5. Find Taxes");
            System.out.println("6. Generate Report");
            System.out.println("7. Exit");
            System.out.print("Select an option: ");

            int choice = scanner.nextInt();
            CommandInvoker.invoke(choice);
        }
    }
}
