package com.taxsystem.commands;

import com.taxsystem.models.TaxPayer;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class FindTaxesCommand implements Command {
    private TaxPayer taxPayer;

    public FindTaxesCommand(TaxPayer taxPayer) {
        this.taxPayer = taxPayer;
    }

    @Override
    public void execute() {
        Scanner scanner = new Scanner(System.in);
        double min = -1;
        double max = -1;

        while (min < 0) {
            try {
                System.out.print("Введіть мінімальну суму податку: ");
                min = scanner.nextDouble();
                if (min < 0) {
                    System.out.println("Мінімальна сума не може бути від'ємною. Спробуйте ще раз.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Неправильний ввід. Будь ласка, введіть числове значення.");
                scanner.next(); // Очищуємо буфер
            }
        }

        while (max < 0 || max < min) {
            try {
                System.out.print("Введіть максимальну суму податку: ");
                max = scanner.nextDouble();
                if (max < 0) {
                    System.out.println("Максимальна сума не може бути від'ємною. Спробуйте ще раз.");
                } else if (max < min) {
                    System.out.println("Максимальна сума не може бути меншою за мінімальну. Спробуйте ще раз.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Неправильний ввід. Будь ласка, введіть числове значення.");
                scanner.next(); // Очищуємо буфер
            }
        }

        List<Double> taxesInRange = taxPayer.findTaxesInRange(min, max);
        if (taxesInRange.isEmpty()) {
            System.out.println("Податки в цьому діапазоні не знайдені.");
        } else {
            System.out.println("Податки в заданому діапазоні: " + taxesInRange);
        }
    }
}
