package com.taxsystem.commands;

import com.taxsystem.models.TaxPayer;
import java.util.InputMismatchException;
import java.util.Scanner;

public class SetChildBenefitsCommand implements Command {
    private TaxPayer taxPayer;

    public SetChildBenefitsCommand(TaxPayer taxPayer) {
        this.taxPayer = taxPayer;
    }

    @Override
    public void execute() {
        Scanner scanner = new Scanner(System.in);
        int numberOfChildren = -1;
        double benefitPerChild = -1;

        // Введення кількості дітей
        while (numberOfChildren < 0) {
            try {
                System.out.print("Введіть кількість дітей: ");
                numberOfChildren = scanner.nextInt();
                if (numberOfChildren < 0) {
                    System.out.println("Кількість дітей не може бути від'ємною.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Неправильний ввід. Введіть ціле число.");
                scanner.next(); // очищення буферу після некоректного вводу
            }
        }

        taxPayer.setNumberOfChildren(numberOfChildren);

        // Введення пільг на кожну дитину
        while (benefitPerChild <= 0) {
            try {
                System.out.print("Введіть суму пільг на одну дитину: ");
                benefitPerChild = scanner.nextDouble();
                if (benefitPerChild <= 0) {
                    System.out.println("Сума повинна бути більше 0.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Неправильний ввід. Введіть числове значення.");
                scanner.next(); // очищення буферу після некоректного вводу
            }
        }

        taxPayer.setChildBenefits(benefitPerChild);
        System.out.println("Пільги на дітей успішно встановлені.");
    }
}
