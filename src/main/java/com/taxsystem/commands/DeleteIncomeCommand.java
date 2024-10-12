package com.taxsystem.commands;

import com.taxsystem.models.TaxPayer;
import com.taxsystem.models.Income;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class DeleteIncomeCommand implements Command {
    private TaxPayer taxPayer;

    public DeleteIncomeCommand(TaxPayer taxPayer) {
        this.taxPayer = taxPayer;
    }

    @Override
    public void execute() {
        Scanner scanner = new Scanner(System.in);
        List<Income> incomes = taxPayer.getIncomes();

        if (incomes.isEmpty()) {
            System.out.println("У вас немає доданих доходів.");
            return;
        }

        boolean isDeleting = true;

        while (isDeleting) {
            // Виводимо список доходів з індексами
            System.out.println("Список доходів:");
            for (int i = 0; i < incomes.size(); i++) {
                Income income = incomes.get(i);
                System.out.printf("%d. Джерело: %s, Сума: %.2f, Тип: %s\n", i, income.getSource(), income.getAmount(), income.getType());
            }

            int index = -1;
            boolean validInput = false;

            // Запитуємо індекс для видалення з перевіркою
            while (!validInput) {
                try {
                    System.out.print("Введіть індекс доходу для видалення (або -1 для виходу): ");
                    index = scanner.nextInt();

                    if (index == -1) {
                        isDeleting = false;
                        System.out.println("Видалення скасовано.");
                        return;
                    }

                    if (index >= 0 && index < incomes.size()) {
                        taxPayer.removeIncome(index);
                        System.out.println("Дохід успішно видалено.");
                        validInput = true;
                    } else {
                        System.out.println("Невірний індекс. Спробуйте ще раз.");
                    }
                } catch (InputMismatchException e) {
                    System.out.println("Неправильний ввід. Будь ласка, введіть правильний індекс.");
                    scanner.next(); // Очищуємо буфер
                }
            }

            // Оновлюємо список після видалення доходу
            incomes = taxPayer.getIncomes();

            // Якщо після видалення список порожній, завершити видалення
            if (incomes.isEmpty()) {
                System.out.println("Усі доходи видалено.");
                isDeleting = false;
            }
        }
    }
}
