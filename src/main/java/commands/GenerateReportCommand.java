package commands;

import models.TaxPayer;

public class GenerateReportCommand implements Command {
    private TaxPayer taxPayer;

    public GenerateReportCommand(TaxPayer taxPayer) {
        this.taxPayer = taxPayer;
    }

    @Override
    public void execute() {
        try {
            taxPayer.generateTaxReport();
            System.out.println("Tax report generated successfully.");
        } catch (Exception e) {
            System.out.println("Error generating tax report: " + e.getMessage());
        }
    }
}
