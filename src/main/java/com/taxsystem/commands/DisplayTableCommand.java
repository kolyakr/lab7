package com.taxsystem.commands;

import com.taxsystem.models.Income;
import com.taxsystem.models.TaxPayer;

public class DisplayTableCommand implements Command {
    private TaxPayer taxPayer;

    public DisplayTableCommand(TaxPayer taxPayer) {
        this.taxPayer = taxPayer;
    }

    @Override
    public void execute() {
        System.out.println("==============================================");
        System.out.println("|  Джерело доходу  |   Сума доходу   |  Податок  |");
        System.out.println("==============================================");
        for (Income income : taxPayer.getIncomes()) {
            double tax = income.getAmount() * 0.185;
            System.out.printf("| %-16s | %-14.2f | %-8.2f |\n", income.getSource(), income.getAmount(), tax);
        }
        System.out.println("==============================================");
    }
}
