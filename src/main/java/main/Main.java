package main;

import models.TaxPayer;

public class Main {
    public static void main(String[] args) {
        TaxPayer taxPayer = new TaxPayer(); // створення нового об'єкта TaxPayer
        Menu menu = new Menu(taxPayer); // передача taxPayer у меню

        menu.display(); // показ меню
    }
}
