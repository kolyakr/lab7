package commands;

import models.TaxPayer;

public class CalculateTaxesCommand implements Command {
    private TaxPayer taxPayer;

    public CalculateTaxesCommand(TaxPayer taxPayer) {
        this.taxPayer = taxPayer;
    }

    @Override
    public void execute() {
        double totalTaxes = taxPayer.calculateTotalTaxes();
        System.out.println("Total amount of taxes: " + totalTaxes);
    }
}
