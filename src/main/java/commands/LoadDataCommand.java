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
            taxPayer.loadFromFile(filename); // Виклик методу, що може кинути IOException
            System.out.println("Data successfully loaded from file: " + filename); // Повідомлення про успішне завантаження
        } catch (IOException e) {
            System.err.println("Error loading data: " + e.getMessage()); // Повідомлення про помилку
        }
    }
}
