package commands;

import models.TaxPayer;

import java.util.InputMismatchException;
import java.util.Scanner;

public class SetChildBenefitsCommand implements Command {
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
                scanner.next(); // Clear the buffer after incorrect input
            }
        }
        taxPayer.setNumberOfChildren(numberOfChildren);

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
                scanner.next(); // Clear the buffer after incorrect input
            }
        }
        taxPayer.setChildBenefits(benefitPerChild);
        System.out.println("Child benefits have been successfully set.");
    }
}
