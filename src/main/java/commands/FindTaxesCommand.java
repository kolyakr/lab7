package commands;

import models.TaxPayer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class FindTaxesCommand implements Command {

    private static final Logger fileLogger = LogManager.getLogger(FindTaxesCommand.class);
    private static final Logger errorLogger = LogManager.getLogger("ErrorLogger");
    private static final Marker ERROR_MARKER = MarkerManager.getMarker("ERROR");

    private TaxPayer taxPayer;

    public FindTaxesCommand(TaxPayer taxPayer) {
        this.taxPayer = taxPayer;
    }

    @Override
    public void execute() {
        Scanner scanner = new Scanner(System.in);
        double min = -1;
        double max = -1;

        // Зчитування мінімального значення
        while (min < 0) {
            try {
                System.out.print("Enter the minimum tax amount: ");
                min = scanner.nextDouble();
                if (min < 0) {
                    System.out.println("The minimum amount cannot be negative. Please try again.");
                    fileLogger.warn("Введено від'ємне мінімальне значення податку: {}", min); // Логування попередження
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a numerical value.");
                scanner.next(); // Очищення буфера
                errorLogger.error(ERROR_MARKER, "Error: Invalid input for minimum tax amount. " + e.getMessage(), e); // Логування помилки
            }
        }

        // Зчитування максимального значення
        while (true) {
            try {
                System.out.print("Enter the maximum tax amount: ");
                max = scanner.nextDouble();
                if (max < 0) {
                    System.out.println("The maximum amount cannot be negative. Please try again.");
                    fileLogger.warn("Введено від'ємне максимальне значення податку: {}", max); // Логування попередження
                } else if (max < min) {
                    System.out.println("The maximum amount cannot be less than the minimum. Please try again.");
                    fileLogger.warn("Максимальне значення {} менше мінімального {}", max, min); // Логування попередження
                } else {
                    break; // Вихід з циклу, якщо вхід коректний
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a numerical value.");
                scanner.next(); // Очищення буфера
                errorLogger.error(ERROR_MARKER, "Error: Invalid input for maximum tax amount. " + e.getMessage(), e); // Логування помилки
            }
        }

        // Знаходження податків в заданому діапазоні
        List<Double> taxesInRange = taxPayer.findTaxesInRange(min, max);
        if (taxesInRange.isEmpty()) {
            System.out.println("No taxes found in this range.");
            fileLogger.info("Не знайдено податків у заданому діапазоні ({}, {}).", min, max); // Логування інформації
        } else {
            System.out.println("Taxes in the specified range: " + taxesInRange);
            fileLogger.info("Знайдено податки у заданому діапазоні ({}, {}): {}", min, max, taxesInRange); // Логування інформації
        }
    }
}
