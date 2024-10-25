package commands;

import models.TaxPayer;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class FindTaxesCommand implements Command {

    private TaxPayer taxPayer;

    public FindTaxesCommand(TaxPayer taxPayer) {
        this.taxPayer = taxPayer;
    }

    @Override
    public void execute() {
        Scanner scanner = new Scanner(System.in);
        double min = -1;
        double max = -1;

        while (min < 0) {
            try {
                System.out.print("Enter the minimum tax amount: ");
                min = scanner.nextDouble();
                if (min < 0) {
                    System.out.println("The minimum amount cannot be negative. Please try again.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a numerical value.");
                scanner.next();
            }
        }

        while (true) {
            try {
                System.out.print("Enter the maximum tax amount: ");
                max = scanner.nextDouble();
                if (max < 0) {
                    System.out.println("The maximum amount cannot be negative. Please try again.");
                } else if (max < min) {
                    System.out.println("The maximum amount cannot be less than the minimum. Please try again.");
                } else {
                    break;
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a numerical value.");
                scanner.next();
            }
        }

        List<Double> taxesInRange = taxPayer.findTaxesInRange(min, max);
        if (taxesInRange.isEmpty()) {
            System.out.println("No taxes found in this range.");
        } else {
            System.out.println("Taxes in the specified range: " + taxesInRange);
        }
    }
}
