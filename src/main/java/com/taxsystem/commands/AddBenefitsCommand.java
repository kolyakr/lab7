package com.taxsystem.commands;

import com.taxsystem.models.TaxPayer;

import java.util.Scanner;

public class AddBenefitsCommand implements Command {
    private TaxPayer taxPayer;

    public AddBenefitsCommand(TaxPayer taxPayer) {
        this.taxPayer = taxPayer;
    }

    @Override
    public void execute() {
        Scanner scanner = new Scanner(System.in);

        // Запит на введення пільг на дітей
        System.out.print("Введіть суму пільг на дітей: ");
        double childBenefits = scanner.nextDouble();
        taxPayer.setChildBenefits(childBenefits);

        // Запит на введення матеріальної допомоги
        System.out.print("Введіть суму матеріальної допомоги: ");
        double materialAid = scanner.nextDouble();
        taxPayer.setMaterialAid(materialAid);

        System.out.println("Пільги успішно додані.");
    }
}
