package commands;

import models.TaxPayer;

import java.util.InputMismatchException;
import java.util.Scanner;

public class SetMaterialAidCommand implements Command {
    private TaxPayer taxPayer;

    public SetMaterialAidCommand(TaxPayer taxPayer) {
        this.taxPayer = taxPayer;
    }

    @Override
    public void execute() {
        Scanner scanner = new Scanner(System.in);
        double materialAid = -1;

        // Input material aid
        while (materialAid <= 0) {
            try {
                System.out.print("Enter the amount of material aid: ");
                materialAid = scanner.nextDouble();
                if (materialAid <= 0) {
                    System.out.println("The amount must be greater than 0.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a numerical value.");
                scanner.next(); // Clear the buffer after incorrect input
            }
        }

        taxPayer.setMaterialAid(materialAid);
        System.out.println("Material aid has been successfully set.");
    }
}
