package main;

import commands.*;
import models.TaxPayer;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Menu {

    private TaxPayer taxPayer;
    private Scanner scanner;

    public Menu(TaxPayer taxPayer) {
        this.taxPayer = taxPayer;
        this.scanner = new Scanner(System.in);
    }

    public void display() {
        while (true) {
            printLine('╔', '═', '╗', 40);
            printCenteredLine("Tax System", 40);
            printLine('╠', '═', '╣', 40);
            printLeftAlignedLine("1. Add Income", 40);
            printLeftAlignedLine("2. Delete Income", 40);
            printLeftAlignedLine("3. Calculate Taxes", 40);
            printLeftAlignedLine("4. Sort Taxes", 40);
            printLeftAlignedLine("5. Find Taxes", 40);
            printLeftAlignedLine("6. Generate Report", 40);
            printLeftAlignedLine("7. Set Child Benefits", 40);
            printLeftAlignedLine("8. Set Material Aid", 40);
            printLeftAlignedLine("9. Save Data", 40);
            printLeftAlignedLine("10. Load Data", 40);
            printLeftAlignedLine("0. Exit", 40);
            printLine('╚', '═', '╝', 40);

            int choice = -1;
            while (choice < 0) {
                try {
                    System.out.print("Option: ");
                    choice = scanner.nextInt();
                    if (choice < 0) {
                        System.out.println("Please enter a valid option.");
                    }
                } catch (InputMismatchException e) {
                    System.out.println("Invalid input. Please enter a number.");
                    scanner.next();
                }
            }

            switch (choice) {
                case 1:
                    new AddIncomeCommand(taxPayer).execute();
                    break;
                case 2:
                    new DeleteIncomeCommand(taxPayer).execute();
                    break;
                case 3:
                    new CalculateTaxesCommand(taxPayer).execute();
                    break;
                case 4:
                    new SortTaxesCommand(taxPayer).execute();
                    break;
                case 5:
                    new FindTaxesCommand(taxPayer).execute();
                    break;
                case 6:
                    new GenerateReportCommand(taxPayer).execute();
                    break;
                case 7:
                    new SetChildBenefitsCommand(taxPayer).execute();
                    break;
                case 8:
                    new SetMaterialAidCommand(taxPayer).execute();
                    break;
                case 9:
                    new SaveDataCommand(taxPayer).execute();
                    break;
                case 10:
                    new LoadDataCommand(taxPayer).execute();
                    break;
                case 0:
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Invalid command! Please try again.");
            }
        }
    }

    public static void printLine(char start, char fill, char end, int width) {
        System.out.print(start);
        for (int i = 0; i < width; i++) {
            System.out.print(fill);
        }
        System.out.println(end);
    }

    public static void printLeftAlignedLine(String text, int width) {
        System.out.print("║");
        System.out.print(text);
        for (int i = text.length(); i < width; i++) {
            System.out.print(" ");
        }
        System.out.println("║");
    }

    public static void printCenteredLine(String text, int width) {
        int padding = (width - text.length()) / 2;
        System.out.print("║");
        for (int i = 0; i < padding; i++) {
            System.out.print(" ");
        }
        System.out.print(text);
        for (int i = 0; i < (width - padding - text.length()); i++) {
            System.out.print(" ");
        }
        System.out.println("║");
    }
}
