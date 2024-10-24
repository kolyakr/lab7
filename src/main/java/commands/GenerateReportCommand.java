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
            taxPayer.generateTaxReport(); // Виклик методу генерації звіту
            System.out.println("Tax report generated successfully."); // Повідомлення про успішну генерацію
        } catch (Exception e) {
            System.out.println("Error generating tax report: " + e.getMessage()); // Повідомлення про помилку
        }
    }
}
