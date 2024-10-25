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

    public void addIncome(Income income) {
        incomes.add(income);
    }

    public boolean removeIncome(int index) {
        if (index >= 0 && index < incomes.size()) {
            incomes.remove(index);
            return true;
        }
        return false;
    }

    public void sortTaxesAscending() {
        Collections.sort(incomes, Comparator.comparingDouble(Income::getAmount));
    }

    public void sortTaxesDescending() {
        Collections.sort(incomes, (i1, i2) -> Double.compare(i2.getAmount(), i1.getAmount()));
    }

    public List<Double> findTaxesInRange(double min, double max) {
        List<Double> taxesInRange = new ArrayList<>();
        for (Income income : incomes) {
            double tax = income.getAmount() * 0.185;
            if (tax >= min && tax <= max) {
                taxesInRange.add(tax);
            }
        }
        return taxesInRange;
    }

    public void generateTaxReport() {
        int widthSource = 20;
        int widthAmount = 15;
        int widthTax = 10;

        // Розділова лінія
        System.out.println("=".repeat(widthSource + widthAmount + widthTax + 2));
        // Заголовок звіту
        System.out.printf("%-" + widthSource + "s %" + widthAmount + "s %" + widthTax + "s%n", "Source", "Amount", "Tax");
        // Розділова лінія
        System.out.println("=".repeat(widthSource + widthAmount + widthTax + 2));

        for (Income income : incomes) {
            double tax = income.getAmount() * 0.185;
            System.out.printf("%-" + widthSource + "s %" + widthAmount + ".2f %" + widthTax + ".2f%n", income.getSource(), income.getAmount(), tax);
        }

        System.out.println("=".repeat(widthSource + widthAmount + widthTax + 2));
    }

    public double calculateTotalTaxes() {
        double totalIncome = 0;
        for (Income income : incomes) {
            totalIncome += income.getAmount();
        }

        double benefits = childBenefits + materialAid;

        if (numberOfChildren > 2) {
            benefits += 500;
        }
        if (isDisabled) {
            benefits += 1000;
        }

        double taxableIncome = totalIncome - benefits;
        double totalTaxes = taxableIncome * 0.185;

        return totalTaxes > 0 ? totalTaxes : 0;
    }

    public void saveToFile(String filename) throws IOException {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
            writer.println("source,amount,type");
            for (Income income : incomes) {
                writer.printf("%s,%.2f,%s%n", income.getSource(), income.getAmount(), income.getType());
            }
            writer.printf("benefits,%.2f,%.2f,%d,%b%n", childBenefits, materialAid, numberOfChildren, isDisabled);
        } catch (IOException e) {
            throw e;
        }
    }

    public void loadFromFile(String filename) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;

            reader.readLine();

            incomes.clear();

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
        } catch (IOException e) {
            throw e;
        }
    }

    public List<Income> getIncomes() {
        return incomes;
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

    public void setNumberOfChildren(int numberOfChildren) {
        this.numberOfChildren = numberOfChildren;
    }

    public void setDisabled(boolean isDisabled) {
        this.isDisabled = isDisabled;
    }

    public void setChildBenefits(double childBenefits) {
        this.childBenefits = childBenefits;
    }

    public void setMaterialAid(double materialAid) {
        this.materialAid = materialAid;
    }
}
