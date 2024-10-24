package commands;

import models.TaxPayer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;

public class GenerateReportCommand implements Command {
    // Логер для дій генерації звіту
    private static final Logger fileLogger = LogManager.getLogger(GenerateReportCommand.class);
    private static final Logger errorLogger = LogManager.getLogger("ErrorLogger");
    private static final Marker ERROR_MARKER = MarkerManager.getMarker("ERROR");
    private TaxPayer taxPayer;

    public GenerateReportCommand(TaxPayer taxPayer) {
        this.taxPayer = taxPayer;
    }

    @Override
    public void execute() {
        try {
            fileLogger.info("Generating tax report for the taxpayer..."); // Логування початку генерації звіту
            taxPayer.generateTaxReport(); // Виклик методу генерації звіту
            fileLogger.info("Tax report generated successfully."); // Логування успішної генерації звіту
        } catch (Exception e) {
            errorLogger.error(ERROR_MARKER, "Error generating tax report: {}", e.getMessage(), e); // Логування помилки
        }
    }
}
