package main;

import models.TaxPayer;

public class Main {

    public static Menu createMenu() {
        TaxPayer taxPayer = new TaxPayer(); // створення нового об'єкта TaxPayer
        return new Menu(taxPayer); // передача taxPayer у меню
    }

    public static void main(String[] args) {
        Menu menu = createMenu(); // отримання меню через окремий метод
        menu.display(); // показ меню
    }
}
