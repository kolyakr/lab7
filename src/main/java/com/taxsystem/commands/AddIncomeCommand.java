package com.taxsystem.commands;

import com.taxsystem.models.TaxPayer;
import com.taxsystem.models.Income;

import java.util.InputMismatchException;
import java.util.Scanner;

public class AddIncomeCommand implements Command {
    private TaxPayer taxPayer;

    public AddIncomeCommand(TaxPayer taxPayer) {
        this.taxPayer = taxPayer;
    }

    @Override
    public void execute() {
        Scanner scanner = new Scanner(System.in);
        String source;
        String type;
        double amount = -1;

        System.out.print("Введіть джерело доходу: ");
        source = scanner.nextLine();

        System.out.print("Введіть тип доходу (основний, додатковий, авторські винагороди, тощо): ");
        type = scanner.nextLine();

        while (amount <= 0) {
            try {
                System.out.print("Введіть суму доходу: ");
                amount = scanner.nextDouble();
                if (amount <= 0) {
                    System.out.println("Сума має бути більшою за 0. Спробуйте ще раз.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Неправильний ввід. Будь ласка, введіть числове значення.");
                scanner.next(); // Очищуємо буфер
            }
        }

        taxPayer.addIncome(new Income(source, amount, type));
        System.out.println("Дохід успішно додано.");
    }
}
