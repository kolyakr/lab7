package commands;

import models.TaxPayer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;

import java.io.IOException;
import java.util.Scanner;

public class LoadDataCommand implements Command {
    private static final Logger fileLogger = LogManager.getLogger(LoadDataCommand.class);
    private static final Logger errorLogger = LogManager.getLogger("ErrorLogger");
    private static final Marker ERROR_MARKER = MarkerManager.getMarker("ERROR");

    private TaxPayer taxPayer;

    public LoadDataCommand(TaxPayer taxPayer) {
        this.taxPayer = taxPayer;
    }

    @Override
    public void execute() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the filename to load: ");
        String filename = scanner.nextLine();

        try {
            taxPayer.loadFromFile(filename);  // Call the method that may throw IOException
            fileLogger.info("Data successfully loaded from file: {}", filename); // Log success
        } catch (IOException e) {
            errorLogger.error(ERROR_MARKER, "Error loading data from file: {}", filename, e); // Log error
            System.err.println("Error loading data: " + e.getMessage());
        }
    }
}
