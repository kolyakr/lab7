package commands;

import models.TaxPayer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;

import java.util.InputMismatchException;
import java.util.Scanner;

public class SetChildBenefitsCommand implements Command {
    private static final Logger fileLogger = LogManager.getLogger(SetChildBenefitsCommand.class);
    private static final Logger errorLogger = LogManager.getLogger("ErrorLogger");
    private static final Marker ERROR_MARKER = MarkerManager.getMarker("ERROR");

    private final TaxPayer taxPayer;

    public SetChildBenefitsCommand(TaxPayer taxPayer) {
        this.taxPayer = taxPayer;
    }

    @Override
    public void execute() {
        Scanner scanner = new Scanner(System.in);
        int numberOfChildren = -1;
        double benefitPerChild = -1;

        // Input the number of children
        while (numberOfChildren < 0) {
            try {
                System.out.print("Enter the number of children: ");
                numberOfChildren = scanner.nextInt();
                if (numberOfChildren < 0) {
                    System.out.println("The number of children cannot be negative.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter an integer.");
                errorLogger.error(ERROR_MARKER, "Invalid input for number of children: {}", e.getMessage(), e); // Логування помилки
                scanner.next(); // Clear the buffer after incorrect input
            }
        }
        taxPayer.setNumberOfChildren(numberOfChildren);
        fileLogger.info("Number of children set to: {}", numberOfChildren); // Логування успішного встановлення

        // Input benefits per child
        while (benefitPerChild <= 0) {
            try {
                System.out.print("Enter the amount of benefits per child: ");
                benefitPerChild = scanner.nextDouble();
                if (benefitPerChild <= 0) {
                    System.out.println("The amount must be greater than 0.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a numerical value.");
                errorLogger.error(ERROR_MARKER, "Invalid input for benefit per child: {}", e.getMessage(), e); // Логування помилки
                scanner.next(); // Clear the buffer after incorrect input
            }
        }
        taxPayer.setChildBenefits(benefitPerChild);
        fileLogger.info("Child benefits set to: {}", benefitPerChild); // Логування успішного встановлення
        System.out.println("Child benefits have been successfully set.");
    }
}
