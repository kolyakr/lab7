package commands;

import models.TaxPayer;

import java.io.IOException;
import java.util.Scanner;

public class SaveDataCommand implements Command {
    private TaxPayer taxPayer;

    public SaveDataCommand(TaxPayer taxPayer) {
        this.taxPayer = taxPayer;
    }

    @Override
    public void execute() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the filename to save: ");
        String filename = scanner.nextLine();

        try {
            taxPayer.saveToFile(filename);  // Call the method that may throw IOException
        } catch (IOException e) {
            System.err.println("Error saving data: " + e.getMessage());
        }
    }
}
