package commands;

import models.TaxPayer;

import java.util.Scanner;

public class AddBenefitsCommand implements Command {
    private TaxPayer taxPayer;

    public AddBenefitsCommand(TaxPayer taxPayer) {
        this.taxPayer = taxPayer;
    }

    @Override
    public void execute() {
        Scanner scanner = new Scanner(System.in);

        try {
            System.out.print("Enter the amount of child benefits: ");
            double childBenefits = scanner.nextDouble();
            taxPayer.setChildBenefits(childBenefits);

            System.out.print("Enter the amount of material aid: ");
            double materialAid = scanner.nextDouble();
            taxPayer.setMaterialAid(materialAid);

            System.out.println("Benefits successfully added.");
        } catch (Exception e) {
            System.out.println("An error occurred while adding benefits. Please try again.");
        }
    }
}
