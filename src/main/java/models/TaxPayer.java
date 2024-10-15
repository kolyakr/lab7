package models;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class TaxPayer {
    private List<Income> incomes;
    private double childBenefits;
    private double materialAid;
    private int numberOfChildren;
    private boolean isDisabled;

    public TaxPayer() {
        this.incomes = new ArrayList<>();
        this.childBenefits = 0;
        this.materialAid = 0;
        this.numberOfChildren = 0;
        this.isDisabled = false;
    }

    // Method to add income
    public void addIncome(Income income) {
        incomes.add(income);
    }

    public void setIncomes(List<Income> incomes) {
        this.incomes = incomes;
    }

    public double getChildBenefits() {
        return childBenefits;
    }

    public int getNumberOfChildren() {
        return numberOfChildren;
    }

    public boolean isDisabled() {
        return isDisabled;
    }

    public double getMaterialAid() {
        return materialAid;
    }

    // Method to remove income by index
    public boolean removeIncome(int index) {
        if (index >= 0 && index < incomes.size()) {
            incomes.remove(index);
            return true;
        }
        return false;
    }

    // Method to sort taxes in ascending order
    public void sortTaxesAscending() {
        Collections.sort(incomes, Comparator.comparingDouble(Income::getAmount));
        System.out.println("Taxes sorted in ascending order.");
    }

    // Method to sort taxes in descending order
    public void sortTaxesDescending() {
        Collections.sort(incomes, (i1, i2) -> Double.compare(i2.getAmount(), i1.getAmount()));
        System.out.println("Taxes sorted in descending order.");
    }

    // Method to find taxes in a specified range
    public List<Double> findTaxesInRange(double min, double max) {
        List<Double> taxesInRange = new ArrayList<>();
        for (Income income : incomes) {
            double tax = income.getAmount() * 0.185;  // 18% personal income tax + 1.5% military levy
            if (tax >= min && tax <= max) {
                taxesInRange.add(tax);
            }
        }
        return taxesInRange;
    }

    // Method to generate a tax report
    public void generateTaxReport() {
        System.out.println("Tax report:");
        for (Income income : incomes) {
            double tax = income.getAmount() * 0.185;
            System.out.println("Source: " + income.getSource() + ", Amount: " + income.getAmount() + ", Tax: " + tax);
        }
    }

    // Method to set the number of children
    public void setNumberOfChildren(int numberOfChildren) {
        this.numberOfChildren = numberOfChildren;
    }

    // Method to set disability status
    public void setDisabled(boolean isDisabled) {
        this.isDisabled = isDisabled;
    }

    // Method to set child benefits
    public void setChildBenefits(double childBenefits) {
        this.childBenefits = childBenefits;
    }

    // Method to set material aid
    public void setMaterialAid(double materialAid) {
        this.materialAid = materialAid;
    }

    // Method to calculate total taxes considering benefits
    public double calculateTotalTaxes() {
        double totalIncome = 0;
        for (Income income : incomes) {
            totalIncome += income.getAmount();
        }

        double benefits = childBenefits + materialAid;

        // Additional benefits for large families and disabled individuals
        if (numberOfChildren > 2) {
            benefits += 500; // Additional benefit for large families
        }
        if (isDisabled) {
            benefits += 1000; // Additional benefit for disabled individuals
        }

        double taxableIncome = totalIncome - benefits;
        double totalTaxes = taxableIncome * 0.185; // 18% personal income tax + 1.5% military levy

        return totalTaxes > 0 ? totalTaxes : 0; // Taxes cannot be negative
    }

    // Method to save data to a CSV file
    public void saveToFile(String filename) throws IOException {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
            writer.println("source,amount,type");
            for (Income income : incomes) {
                writer.printf("%s,%.2f,%s%n", income.getSource(), income.getAmount(), income.getType());
            }
            writer.printf("benefits,%.2f,%.2f,%d,%b%n", childBenefits, materialAid, numberOfChildren, isDisabled);
        }
    }

    // Method to load data from a CSV file
    public void loadFromFile(String filename) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;

            // Skip the first line with headers
            reader.readLine();  // Skip headers (source,amount,type)

            incomes.clear();  // Clear previous data

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts[0].equals("benefits")) {
                    this.childBenefits = Double.parseDouble(parts[1]);
                    this.materialAid = Double.parseDouble(parts[2]);
                    this.numberOfChildren = Integer.parseInt(parts[3]);
                    this.isDisabled = Boolean.parseBoolean(parts[4]);
                } else {
                    String source = parts[0];
                    double amount = Double.parseDouble(parts[1]);
                    String type = parts[2];
                    incomes.add(new Income(source, amount, type));
                }
            }
            System.out.println("Data successfully loaded.");
        }
    }

    // Method to get the list of incomes
    public List<Income> getIncomes() {
        return incomes;
    }
}
