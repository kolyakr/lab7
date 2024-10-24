package models;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class TaxPayer {
    private static final Logger fileLogger = LogManager.getLogger(TaxPayer.class); // Логер для загальних дій
    private static final Logger errorLogger = LogManager.getLogger("ErrorLogger"); // Логер для помилок
    private static final Marker ERROR_MARKER = MarkerManager.getMarker("ERROR"); // Маркер для помилок

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

    // Метод для додавання доходу
    public void addIncome(Income income) {
        incomes.add(income);
        fileLogger.info("Income added: Source: {}, Amount: {}, Type: {}", income.getSource(), income.getAmount(), income.getType());
    }

    // Метод для видалення доходу за індексом
    public boolean removeIncome(int index) {
        if (index >= 0 && index < incomes.size()) {
            Income removedIncome = incomes.remove(index);
            fileLogger.info("Income removed: Source: {}, Amount: {}", removedIncome.getSource(), removedIncome.getAmount());
            return true;
        }
        errorLogger.error(ERROR_MARKER, "Error removing income: Invalid index {}", index);
        return false;
    }

    // Метод для сортування податків у зростаючому порядку
    public void sortTaxesAscending() {
        Collections.sort(incomes, Comparator.comparingDouble(Income::getAmount));
        fileLogger.info("Taxes sorted in ascending order.");
    }

    // Метод для сортування податків у спадаючому порядку
    public void sortTaxesDescending() {
        Collections.sort(incomes, (i1, i2) -> Double.compare(i2.getAmount(), i1.getAmount()));
        fileLogger.info("Taxes sorted in descending order.");
    }

    // Метод для знаходження податків у заданому діапазоні
    public List<Double> findTaxesInRange(double min, double max) {
        List<Double> taxesInRange = new ArrayList<>();
        for (Income income : incomes) {
            double tax = income.getAmount() * 0.185;
            if (tax >= min && tax <= max) {
                taxesInRange.add(tax);
            }
        }
        fileLogger.info("Taxes found in range {} - {}: {}", min, max, taxesInRange);
        return taxesInRange;
    }

    // Метод для генерації податкового звіту
    public void generateTaxReport() {
        fileLogger.info("Generating tax report.");
        int widthSource = 20; // Ширина стовпця для джерела
        int widthAmount = 15;  // Ширина стовпця для суми
        int widthTax = 10;     // Ширина стовпця для податку

        // Розділова лінія
        System.out.println("=".repeat(widthSource + widthAmount + widthTax + 2));
        // Заголовок звіту
        System.out.printf("%-" + widthSource + "s %" + widthAmount + "s %" + widthTax + "s%n", "Source", "Amount", "Tax");
        // Розділова лінія
        System.out.println("=".repeat(widthSource + widthAmount + widthTax + 2));

        for (Income income : incomes) {
            double tax = income.getAmount() * 0.185; // Обчислення податку
            // Вивід інформації про дохід у консоль у форматі таблиці
            System.out.printf("%-" + widthSource + "s %" + widthAmount + ".2f %" + widthTax + ".2f%n", income.getSource(), income.getAmount(), tax);

            // Логування інформації
            fileLogger.info("Source: {}, Amount: {}, Tax: {}", income.getSource(), income.getAmount(), tax);
        }

        // Розділова лінія
        System.out.println("=".repeat(widthSource + widthAmount + widthTax + 2));
        fileLogger.info("Tax report generated successfully.");
    }


    // Метод для обчислення загальних податків з урахуванням пільг
    public double calculateTotalTaxes() {
        double totalIncome = 0;
        for (Income income : incomes) {
            totalIncome += income.getAmount();
        }

        double benefits = childBenefits + materialAid;

        // Додаткові пільги для великих сімей і осіб з інвалідністю
        if (numberOfChildren > 2) {
            benefits += 500; // Додаткова пільга для великих сімей
        }
        if (isDisabled) {
            benefits += 1000; // Додаткова пільга для осіб з інвалідністю
        }

        double taxableIncome = totalIncome - benefits;
        double totalTaxes = taxableIncome * 0.185; // 18% податок на доходи + 1.5% військовий збір

        return totalTaxes > 0 ? totalTaxes : 0; // Податки не можуть бути негативними
    }

    // Метод для збереження даних у CSV файл
    public void saveToFile(String filename) throws IOException {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
            writer.println("source,amount,type");
            for (Income income : incomes) {
                writer.printf("%s,%.2f,%s%n", income.getSource(), income.getAmount(), income.getType());
            }
            writer.printf("benefits,%.2f,%.2f,%d,%b%n", childBenefits, materialAid, numberOfChildren, isDisabled);
            fileLogger.info("Data saved to file: {}", filename);
        } catch (IOException e) {
            errorLogger.error(ERROR_MARKER, "Error saving data to file: {}", filename, e);
            throw e; // Перекидання виключення після логування
        }
    }

    // Метод для завантаження даних з CSV файлу
    public void loadFromFile(String filename) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;

            // Пропустити перший рядок з заголовками
            reader.readLine();  // Пропустити заголовки (source,amount,type)

            incomes.clear();  // Очищення попередніх даних

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
            fileLogger.info("Data successfully loaded from file: {}", filename);
        } catch (IOException e) {
            errorLogger.error(ERROR_MARKER, "Error loading data from file: {}", filename, e);
            throw e; // Перекидання виключення після логування
        }
    }

    // Геттери та сеттери для інших атрибутів
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
