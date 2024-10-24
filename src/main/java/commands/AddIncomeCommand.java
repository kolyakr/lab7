package commands;

import models.Income;
import models.TaxPayer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;

import java.util.InputMismatchException;
import java.util.Scanner;

public class AddIncomeCommand implements Command {
    private static final Logger fileLogger = LogManager.getLogger(AddIncomeCommand.class);
    private static final Logger errorLogger = LogManager.getLogger("ErrorLogger");
    private static final Marker ERROR_MARKER = MarkerManager.getMarker("ERROR");

    private TaxPayer taxPayer;

    public AddIncomeCommand(TaxPayer taxPayer) {
        this.taxPayer = taxPayer;
    }

    @Override
    public void execute() {
        Scanner scanner = new Scanner(System.in);
        String source;
        String type;
        double amount = -1;

        System.out.print("Enter the source of income: ");
        source = scanner.nextLine();

        System.out.print("Enter the type of income (main, additional, royalties, etc.): ");
        type = scanner.nextLine();

        while (amount <= 0) {
            try {
                System.out.print("Enter the amount of income: ");
                amount = scanner.nextDouble();
                if (amount <= 0) {
                    System.out.println("The amount must be greater than 0. Please try again.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a numerical value.");
                errorLogger.error(ERROR_MARKER, "Invalid input: {}", e.getMessage(), e);
                scanner.next(); // Clear the buffer
            }
        }

        taxPayer.addIncome(new Income(source, amount, type));
        System.out.println("Income successfully added.");
        fileLogger.info("Income added - Source: '{}', Amount: {}, Type: '{}'", source, amount, type);
    }
}
