package main;

import commands.*;
import models.TaxPayer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Menu {
    private static final Logger logger = LogManager.getLogger(Menu.class); // Logger for menu actions
    private static final Logger errorLogger = LogManager.getLogger("ErrorLogger"); // Logger for errors
    private static final Marker ERROR_MARKER = MarkerManager.getMarker("ERROR"); // Error marker

    // ANSI escape codes for styling
    public static final String RESET = "\u001B[0m";
    public static final String GREEN = "\u001B[32m";
    public static final String YELLOW = "\u001B[33m";
    public static final String CYAN = "\u001B[36m";

    private TaxPayer taxPayer;
    private Scanner scanner;

    public Menu(TaxPayer taxPayer) {
        this.taxPayer = taxPayer;
        this.scanner = new Scanner(System.in);
    }

    public void display() {
        while (true) {
            // Display the menu with styling and borders
            printLine('╔', '═', '╗', 40);
            printCenteredLine("Tax System", 40, GREEN);
            printLine('╠', '═', '╣', 40);
            printLeftAlignedLine("1. Add Income", 40, YELLOW);
            printLeftAlignedLine("2. Delete Income", 40, YELLOW);
            printLeftAlignedLine("3. Calculate Taxes", 40, YELLOW);
            printLeftAlignedLine("4. Sort Taxes", 40, YELLOW);
            printLeftAlignedLine("5. Find Taxes", 40, YELLOW);
            printLeftAlignedLine("6. Generate Report", 40, YELLOW);
            printLeftAlignedLine("7. Set Child Benefits", 40, YELLOW);
            printLeftAlignedLine("8. Set Material Aid", 40, YELLOW);
            printLeftAlignedLine("9. Save Data", 40, YELLOW);
            printLeftAlignedLine("10. Load Data", 40, YELLOW);
            printLeftAlignedLine("0. Exit", 40, YELLOW);
            printLine('╚', '═', '╝', 40);

            int choice = -1;
            while (choice < 0) {
                try {
                    System.out.print("Option: ");  // Prompt for input
                    choice = scanner.nextInt();
                    if (choice < 0) {
                        System.out.println("Please enter a valid option.");
                    }
                } catch (InputMismatchException e) {
                    System.out.println("Invalid input. Please enter a number.");
                    errorLogger.error(ERROR_MARKER, "Invalid input in menu choice: {}", e.getMessage(), e); // Log error
                    scanner.next(); // Clear the buffer
                }
            }

            // Execute user's choice
            switch (choice) {
                case 1:
                    logger.info("Executing Add Income command."); // Log action
                    new AddIncomeCommand(taxPayer).execute();
                    break;
                case 2:
                    logger.info("Executing Delete Income command."); // Log action
                    new DeleteIncomeCommand(taxPayer).execute();
                    break;
                case 3:
                    logger.info("Executing Calculate Taxes command."); // Log action
                    new CalculateTaxesCommand(taxPayer).execute();
                    break;
                case 4:
                    logger.info("Executing Sort Taxes command."); // Log action
                    new SortTaxesCommand(taxPayer).execute();
                    break;
                case 5:
                    logger.info("Executing Find Taxes command."); // Log action
                    new FindTaxesCommand(taxPayer).execute();
                    break;
                case 6:
                    logger.info("Executing Generate Report command."); // Log action
                    new GenerateReportCommand(taxPayer).execute();
                    break;
                case 7:
                    logger.info("Executing Set Child Benefits command."); // Log action
                    new SetChildBenefitsCommand(taxPayer).execute();
                    break;
                case 8:
                    logger.info("Executing Set Material Aid command."); // Log action
                    new SetMaterialAidCommand(taxPayer).execute();
                    break;
                case 9:
                    logger.info("Executing Save Data command."); // Log action
                    new SaveDataCommand(taxPayer).execute();
                    break;
                case 10:
                    logger.info("Executing Load Data command."); // Log action
                    new LoadDataCommand(taxPayer).execute();
                    break;
                case 0:
                    logger.info("Exiting the application..."); // Log exit
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Invalid command! Please try again.");
            }
        }
    }

    // Method to print the top and bottom border lines
    public static void printLine(char start, char fill, char end, int width) {
        System.out.print(CYAN + start);
        for (int i = 0; i < width; i++) {
            System.out.print(fill);
        }
        System.out.println(end + RESET);
    }

    // Method for left-aligning text within the menu
    public static void printLeftAlignedLine(String text, int width, String color) {
        System.out.print(CYAN + "║" + RESET);
        System.out.print(color + text + RESET);
        for (int i = text.length(); i < width; i++) {
            System.out.print(" ");
        }
        System.out.println(CYAN + "║" + RESET);
    }

    // Method for centering text within the menu
    public static void printCenteredLine(String text, int width, String color) {
        int padding = (width - text.length()) / 2;
        System.out.print(CYAN + "║" + RESET);
        for (int i = 0; i < padding; i++) {
            System.out.print(" ");
        }
        System.out.print(color + text + RESET);
        for (int i = 0; i < (width - padding - text.length()); i++) {
            System.out.print(" ");
        }
        System.out.println(CYAN + "║" + RESET);
    }
}
