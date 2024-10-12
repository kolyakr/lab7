package com.taxsystem.commands;

import com.taxsystem.models.TaxPayer;

public class CalculateTaxesCommand implements Command {
    private TaxPayer taxPayer;

    public CalculateTaxesCommand(TaxPayer taxPayer) {
        this.taxPayer = taxPayer;
    }

    @Override
    public void execute() {
        double totalTaxes = taxPayer.calculateTotalTaxes();
        System.out.println("Загальна сума податків: " + totalTaxes);
    }
}
