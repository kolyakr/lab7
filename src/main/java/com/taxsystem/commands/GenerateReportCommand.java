package com.taxsystem.commands;

import com.taxsystem.models.TaxPayer;

public class GenerateReportCommand implements Command {
    private TaxPayer taxPayer;

    public GenerateReportCommand(TaxPayer taxPayer) {
        this.taxPayer = taxPayer;
    }

    @Override
    public void execute() {
        taxPayer.generateTaxReport();
    }
}
