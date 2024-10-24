package commands;

import models.TaxPayer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;

import java.util.InputMismatchException;
import java.util.Scanner;

public class SetMaterialAidCommand implements Command {
    private static final Logger fileLogger = LogManager.getLogger(SetMaterialAidCommand.class); // Логер для загальних дій
    private static final Logger errorLogger = LogManager.getLogger("ErrorLogger"); // Логер для помилок
    private static final Marker ERROR_MARKER = MarkerManager.getMarker("ERROR"); // Маркер для помилок

    private TaxPayer taxPayer;

    public SetMaterialAidCommand(TaxPayer taxPayer) {
        this.taxPayer = taxPayer;
    }

    @Override
    public void execute() {
        Scanner scanner = new Scanner(System.in);
        double materialAid = -1;

        // Input material aid
        while (materialAid <= 0) {
            try {
                System.out.print("Enter the amount of material aid: ");
                materialAid = scanner.nextDouble();
                if (materialAid <= 0) {
                    System.out.println("The amount must be greater than 0.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a numerical value.");
                errorLogger.error(ERROR_MARKER, "Invalid input for material aid: {}", e.getMessage(), e); // Логування помилки
                scanner.next(); // Clear the buffer after incorrect input
            }
        }

        taxPayer.setMaterialAid(materialAid);
        fileLogger.info("Material aid set to: {}", materialAid); // Логування успішного встановлення
        System.out.println("Material aid has been successfully set.");
    }
}
