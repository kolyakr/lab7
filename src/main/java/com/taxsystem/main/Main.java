package com.taxsystem.main;

import com.taxsystem.models.TaxPayer;

public class Main {
    public static void main(String[] args) {
        TaxPayer taxPayer = new TaxPayer();  // Створюємо платника податків
        CommandInvoker invoker = new CommandInvoker(taxPayer);  // Передаємо його в командний інвокер

        Menu menu = new Menu(invoker);  // Створюємо меню
        menu.display();
    }
}
