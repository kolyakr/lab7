package commands;

import models.TaxPayer;

import java.util.InputMismatchException;
import java.util.Scanner;

public class SortTaxesCommand implements Command {
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
                } else if (choice == 2) {
                    taxPayer.sortTaxesDescending();
                } else {
                    System.out.println("Invalid choice. Please try again.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter 1 or 2.");
                scanner.next(); // Clear the buffer
            }
        }
    }
}
