package com.taxsystem.commands;

import com.taxsystem.models.TaxPayer;
import java.util.InputMismatchException;
import java.util.Scanner;

public class SetMaterialAidCommand implements Command {
    private TaxPayer taxPayer;

    public SetMaterialAidCommand(TaxPayer taxPayer) {
        this.taxPayer = taxPayer;
    }

    @Override
    public void execute() {
        Scanner scanner = new Scanner(System.in);
        double materialAid = -1;

        // Введення матеріальної допомоги
        while (materialAid <= 0) {
            try {
                System.out.print("Введіть суму матеріальної допомоги: ");
                materialAid = scanner.nextDouble();
                if (materialAid <= 0) {
                    System.out.println("Сума повинна бути більше 0.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Неправильний ввід. Введіть числове значення.");
                scanner.next(); // очищення буферу після некоректного вводу
            }
        }

        taxPayer.setMaterialAid(materialAid);
        System.out.println("Матеріальну допомогу успішно встановлено.");
    }
}
