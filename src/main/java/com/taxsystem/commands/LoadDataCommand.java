package com.taxsystem.commands;

import com.taxsystem.models.TaxPayer;

import java.util.Scanner;

public class LoadDataCommand implements Command {
    private TaxPayer taxPayer;

    public LoadDataCommand(TaxPayer taxPayer) {
        this.taxPayer = taxPayer;
    }

    @Override
    public void execute() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Введіть назву файлу для завантаження: ");
        String filename = scanner.nextLine();
        taxPayer.loadFromFile(filename);
    }
}
