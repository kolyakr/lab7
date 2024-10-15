package main;

import models.TaxPayer;

public class Main {
    public static void main(String[] args) {
        TaxPayer taxPayer = new TaxPayer();  // Створення платника податків
        CommandInvoker invoker = new CommandInvoker(taxPayer);  // Передача його командному інвокеру

        Menu menu = new Menu(invoker);  // Створення меню
        menu.display();
    }
}
