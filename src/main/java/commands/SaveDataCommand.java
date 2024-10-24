package commands;

import models.TaxPayer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;

import java.io.IOException;
import java.util.Scanner;

public class SaveDataCommand implements Command {
    private static final Logger fileLogger = LogManager.getLogger(SaveDataCommand.class);
    private static final Logger errorLogger = LogManager.getLogger("ErrorLogger");
    private static final Marker ERROR_MARKER = MarkerManager.getMarker("ERROR");
    private TaxPayer taxPayer;

    public SaveDataCommand(TaxPayer taxPayer) {
        this.taxPayer = taxPayer;
    }

    @Override
    public void execute() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the filename to save: ");
        String filename = scanner.nextLine();

        try {
            taxPayer.saveToFile(filename);  // Виклик методу, що може викликати IOException
            fileLogger.info("Data successfully saved to file: {}", filename); // Логування успішного збереження
        } catch (IOException e) {
            errorLogger.error(ERROR_MARKER, "Error saving data to file '{}': {}", filename, e.getMessage(), e); // Логування помилки
            System.err.println("Error saving data: " + e.getMessage());
        }
    }
}
