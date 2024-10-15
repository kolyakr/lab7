package main;

import java.util.Scanner;

public class Menu {
    private CommandInvoker invoker;

    public Menu(CommandInvoker invoker) {
        this.invoker = invoker;
    }

    public void display() {
        Scanner scanner = new Scanner(System.in);
        boolean running = true; // Додана змінна для контролю циклу

        while (running) {
            System.out.println("=======================================");
            System.out.println("  TAX CALCULATION SYSTEM");
            System.out.println("=======================================");
            System.out.println("1. Add income");
            System.out.println("2. Remove income");
            System.out.println("3. Calculate taxes");
            System.out.println("4. Sort taxes");
            System.out.println("5. Search taxes by range");
            System.out.println("6. Generate report");
            System.out.println("7. Set child benefits");
            System.out.println("8. Set material aid");
            System.out.println("9. Save data");
            System.out.println("10. Load data");
            System.out.println("11. Exit");
            System.out.println("=======================================");
            System.out.print("Select an option: ");

            int choice = scanner.nextInt();
            if (choice == 11) {
                running = false; // Вихід з циклу при виборі 11
                System.out.println("Exiting program..."); // Повідомлення про вихід
            } else {
                invoker.invoke(choice); // Виклик команди
            }
        }
        scanner.close(); // Закриття сканера
    }

    public void exit() {
        System.out.println("Exiting program..."); // Повідомлення про вихід
    }
}
