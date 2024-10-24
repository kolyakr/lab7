package commands;

import models.TaxPayer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CalculateTaxesCommand implements Command {
    private static final Logger fileLogger = LogManager.getLogger(CalculateTaxesCommand.class);
    private TaxPayer taxPayer;

    public CalculateTaxesCommand(TaxPayer taxPayer) {
        this.taxPayer = taxPayer;
    }

    @Override
    public void execute() {
        double totalTaxes = taxPayer.calculateTotalTaxes();
        System.out.println("Total amount of taxes: " + totalTaxes);
        fileLogger.info("Total taxes calculated: {}", totalTaxes);
    }
}
