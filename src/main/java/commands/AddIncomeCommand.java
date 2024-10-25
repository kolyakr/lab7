package commands;

import models.Income;
import models.TaxPayer;

import java.util.InputMismatchException;
import java.util.Scanner;

public class AddIncomeCommand implements Command {
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
                scanner.next();
            }
        }

        taxPayer.addIncome(new Income(source, amount, type));
        System.out.println("Income successfully added.");
    }
}
