package com.taxsystem.commands;

import com.taxsystem.models.TaxPayer;

import java.util.Scanner;

public class SaveDataCommand implements Command {
    private TaxPayer taxPayer;

    public SaveDataCommand(TaxPayer taxPayer) {
        this.taxPayer = taxPayer;
    }

    @Override
    public void execute() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Введіть назву файлу для збереження: ");
        String filename = scanner.nextLine();
        taxPayer.saveToFile(filename);
        System.out.println("Дані збережені.");
    }
}
