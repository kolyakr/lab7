package commands;

import models.TaxPayer;

import java.io.IOException;
import java.util.Scanner;

public class LoadDataCommand implements Command {
    private TaxPayer taxPayer;

    public LoadDataCommand(TaxPayer taxPayer) {
        this.taxPayer = taxPayer;
    }

    @Override
    public void execute() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the filename to load: ");
        String filename = scanner.nextLine();

        try {
            taxPayer.loadFromFile(filename);  // Call the method that may throw IOException
        } catch (IOException e) {
            System.err.println("Error loading data: " + e.getMessage());
        }
    }
}
