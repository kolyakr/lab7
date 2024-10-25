package main;

import models.TaxPayer;

public class Main {

    public static Menu createMenu() {
        TaxPayer taxPayer = new TaxPayer();
        return new Menu(taxPayer);
    }

    public static void main(String[] args) {
        Menu menu = createMenu();
        menu.display();
    }
}
