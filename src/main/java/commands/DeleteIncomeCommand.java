package commands;

import models.Income;
import models.TaxPayer;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class DeleteIncomeCommand implements Command {
    private TaxPayer taxPayer;

    public DeleteIncomeCommand(TaxPayer taxPayer) {
        this.taxPayer = taxPayer;
    }

    @Override
    public void execute() {
        Scanner scanner = new Scanner(System.in);
        List<Income> incomes = taxPayer.getIncomes();

        if (incomes.isEmpty()) {
            System.out.println("You have no added incomes.");
            return;
        }

        boolean isDeleting = true;

        while (isDeleting && !incomes.isEmpty()) { // Перевірка, чи список не порожній
            // Display the list of incomes with indices
            System.out.println("List of incomes:");
            for (int i = 0; i < incomes.size(); i++) {
                Income income = incomes.get(i);
                System.out.printf("%d. Source: %s, Amount: %.2f, Type: %s\n", i, income.getSource(), income.getAmount(), income.getType());
            }

            int index = -1;
            boolean validInput = false;

            // Prompt for index to delete with validation
            while (!validInput) {
                try {
                    System.out.print("Enter the index of the income to delete (or -1 to exit): ");
                    index = scanner.nextInt();

                    if (index == -1) {
                        isDeleting = false;
                        System.out.println("Deletion canceled.");
                        return;
                    }

                    if (index >= 0 && index < incomes.size()) {
                        Income deletedIncome = incomes.get(index); // Get the income to be deleted
                        taxPayer.removeIncome(index);
                        System.out.println("Income successfully deleted.");
                        validInput = true;
                    } else {
                        System.out.println("Invalid index. Please try again.");
                    }
                } catch (InputMismatchException e) {
                    System.out.println("Invalid input. Please enter a valid index.");
                    scanner.next(); // Clear the buffer
                }
            }

            // Update the list after deleting the income
            incomes = taxPayer.getIncomes();

            // If the list is empty after deletion, finish deleting
            if (incomes.isEmpty()) {
                System.out.println("All incomes have been deleted.");
                isDeleting = false;
            }
        }
    }
}
