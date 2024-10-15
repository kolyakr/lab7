package main;

import commands.*;
import models.TaxPayer;

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
                cmd = new CalculateTaxesCommand(taxPayer);  // Обчислити податки
                break;
            case 4:
                cmd = new SortTaxesCommand(taxPayer);  // Відсортувати податки
                break;
            case 5:
                cmd = new FindTaxesCommand(taxPayer);  // Шукати податки
                break;
            case 6:
                cmd = new GenerateReportCommand(taxPayer);  // Згенерувати звіт
                break;
            case 7:
                cmd = new SetChildBenefitsCommand(taxPayer);  // Встановити дитячі допомоги
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
            default:
                System.out.println("Invalid command!");
                return;
        }
        cmd.execute(); // Виконати команду
    }
}
