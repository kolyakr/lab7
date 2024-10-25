package commands;

import models.Income;
import models.TaxPayer;

public class DisplayTableCommand implements Command {
    private TaxPayer taxPayer;

    public DisplayTableCommand(TaxPayer taxPayer) {
        this.taxPayer = taxPayer;
    }

    @Override
    public void execute() {
        System.out.println("==============================================");
        System.out.println("|  Income Source   |   Income Amount   |  Tax     |");
        System.out.println("==============================================");
        for (Income income : taxPayer.getIncomes()) {
            double tax = income.calculateTax();
            System.out.printf("| %-16s | %-14.2f | %-8.2f |\n", income.getSource(), income.getAmount(), tax);
        }
        System.out.println("==============================================");
    }
}
