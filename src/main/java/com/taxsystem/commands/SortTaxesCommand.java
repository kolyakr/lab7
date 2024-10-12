package com.taxsystem.commands;

import com.taxsystem.models.TaxPayer;

import java.util.InputMismatchException;
import java.util.Scanner;

public class SortTaxesCommand implements Command {
    private TaxPayer taxPayer;

    public SortTaxesCommand(TaxPayer taxPayer) {
        this.taxPayer = taxPayer;
    }

    @Override
    public void execute() {
        Scanner scanner = new Scanner(System.in);
        int choice = -1;

        while (choice != 1 && choice != 2) {
            try {
                System.out.println("Сортувати за зростанням (1) чи спаданням (2)?");
                choice = scanner.nextInt();

                if (choice == 1) {
                    taxPayer.sortTaxesAscending();
                } else if (choice == 2) {
                    taxPayer.sortTaxesDescending();
                } else {
                    System.out.println("Невірний вибір. Спробуйте ще раз.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Неправильний ввід. Будь ласка, введіть 1 або 2.");
                scanner.next(); // Очищуємо буфер
            }
        }
    }
}
