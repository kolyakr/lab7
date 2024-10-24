package commands;

import models.TaxPayer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;

import java.util.InputMismatchException;
import java.util.Scanner;

public class SortTaxesCommand implements Command {
    private static final Logger fileLogger = LogManager.getLogger(SortTaxesCommand.class); // Логер для загальних дій
    private static final Logger errorLogger = LogManager.getLogger("ErrorLogger"); // Логер для помилок
    private static final Marker ERROR_MARKER = MarkerManager.getMarker("ERROR"); // Маркер для помилок

    private TaxPayer taxPayer;

    public SortTaxesCommand(TaxPayer taxPayer) {
        this.taxPayer = taxPayer;
    }

    @Override
    public void execute() {
        Scanner scanner = new Scanner(System.in);
        int choice = -1;

        while (choice != 1 && choice != 2) {
            try {
                System.out.println("Sort in ascending order (1) or descending order (2)?");
                choice = scanner.nextInt();

                if (choice == 1) {
                    taxPayer.sortTaxesAscending();
                    fileLogger.info("Taxes sorted in ascending order."); // Логування успішного сортування
                } else if (choice == 2) {
                    taxPayer.sortTaxesDescending();
                    fileLogger.info("Taxes sorted in descending order."); // Логування успішного сортування
                } else {
                    System.out.println("Invalid choice. Please try again.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter 1 or 2.");
                errorLogger.error(ERROR_MARKER, "Invalid input for sorting choice: {}", e.getMessage(), e); // Логування помилки
                scanner.next(); // Очищення буфера після неправильного вводу
            }
        }
    }
}
