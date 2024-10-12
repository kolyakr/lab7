package com.taxsystem.main;

import com.taxsystem.commands.*;
import com.taxsystem.models.TaxPayer;

public class CommandInvoker {
    private TaxPayer taxPayer;

    public CommandInvoker(TaxPayer taxPayer) {
        this.taxPayer = taxPayer;
    }

    public void invoke(int command) {
        Command cmd;
        switch (command) {
            case 1:
                cmd = new AddIncomeCommand(taxPayer);  // Додати дохід
                break;
            case 2:
                cmd = new DeleteIncomeCommand(taxPayer);  // Видалити дохід
                break;
            case 3:
                cmd = new CalculateTaxesCommand(taxPayer);  // Розрахувати податки
                break;
            case 4:
                cmd = new SortTaxesCommand(taxPayer);  // Сортувати податки
                break;
            case 5:
                cmd = new FindTaxesCommand(taxPayer);  // Пошук податків
                break;
            case 6:
                cmd = new GenerateReportCommand(taxPayer);  // Генерація звіту
                break;
            case 7:
                cmd = new SetChildBenefitsCommand(taxPayer);  // Встановити пільги на дітей
                break;
            case 8:
                cmd = new SetMaterialAidCommand(taxPayer);  // Встановити матеріальну допомогу
                break;
            case 9:
                cmd = new SaveDataCommand(taxPayer);  // Зберегти дані
                break;
            case 10:
                cmd = new LoadDataCommand(taxPayer);  // Завантажити дані
                break;
            case 11:
                System.out.println("Exiting program...");
                System.exit(0);
                return;
            default:
                System.out.println("Invalid command!");
                return;
        }
        cmd.execute();
    }
}
