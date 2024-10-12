package com.taxsystem.main;

import java.util.Scanner;

public class Menu {
    private CommandInvoker invoker;

    public Menu(CommandInvoker invoker) {
        this.invoker = invoker;
    }

    public void display() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("=======================================");
            System.out.println("  СИСТЕМА ОБРАХУВАННЯ ПОДАТКІВ");
            System.out.println("=======================================");
            System.out.println("1. Додати дохід");
            System.out.println("2. Видалити дохід");
            System.out.println("3. Розрахувати податки");
            System.out.println("4. Сортувати податки");
            System.out.println("5. Пошук податків за діапазоном");
            System.out.println("6. Генерація звіту");
            System.out.println("7. Встановити пільги на дітей");
            System.out.println("8. Встановити матеріальну допомогу");
            System.out.println("9. Зберегти дані");
            System.out.println("10. Завантажити дані");
            System.out.println("11. Вихід");
            System.out.println("=======================================");
            System.out.print("Оберіть опцію: ");

            int choice = scanner.nextInt();
            invoker.invoke(choice);
        }
    }
}
