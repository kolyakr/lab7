package commands;

import models.TaxPayer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;

import java.util.Scanner;

public class AddBenefitsCommand implements Command {
    private static final Logger fileLogger = LogManager.getLogger(AddBenefitsCommand.class);
    private static final Logger errorLogger = LogManager.getLogger("ErrorLogger");
    private static final Marker ERROR_MARKER = MarkerManager.getMarker("ERROR");

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
            fileLogger.info("Child benefits set to: {}", childBenefits);

            System.out.print("Enter the amount of material aid: ");
            double materialAid = scanner.nextDouble();
            taxPayer.setMaterialAid(materialAid);
            fileLogger.info("Material aid set to: {}", materialAid);

            System.out.println("Benefits successfully added.");
            fileLogger.info("Benefits successfully added for taxpayer.");
        } catch (Exception e) {
            errorLogger.error(ERROR_MARKER, "Error while adding benefits: {}", e.getMessage(), e);
            System.out.println("An error occurred while adding benefits. Please try again.");
        }
    }
}
