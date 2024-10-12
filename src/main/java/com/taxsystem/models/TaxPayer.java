package com.taxsystem.models;

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

    // Метод для додавання доходу
    public void addIncome(Income income) {
        incomes.add(income);
    }

    // Метод для видалення доходу за індексом
    public boolean removeIncome(int index) {
        if (index >= 0 && index < incomes.size()) {
            incomes.remove(index);
            return true;
        }
        return false;
    }

    // Метод для сортування податків за зростанням
    public void sortTaxesAscending() {
        Collections.sort(incomes, Comparator.comparingDouble(Income::getAmount));
        System.out.println("Податки відсортовані за зростанням.");
    }

    // Метод для сортування податків за спаданням
    public void sortTaxesDescending() {
        Collections.sort(incomes, (i1, i2) -> Double.compare(i2.getAmount(), i1.getAmount()));
        System.out.println("Податки відсортовані за спаданням.");
    }

    // Метод для пошуку податків у заданому діапазоні
    public List<Double> findTaxesInRange(double min, double max) {
        List<Double> taxesInRange = new ArrayList<>();
        for (Income income : incomes) {
            double tax = income.getAmount() * 0.185;  // 18% ПДФО + 1.5% військовий збір
            if (tax >= min && tax <= max) {
                taxesInRange.add(tax);
            }
        }
        return taxesInRange;
    }

    // Метод для генерації звіту по податках
    public void generateTaxReport() {
        System.out.println("Звіт по податках:");
        for (Income income : incomes) {
            double tax = income.getAmount() * 0.185;
            System.out.println("Джерело: " + income.getSource() + ", Сума: " + income.getAmount() + ", Податок: " + tax);
        }
    }

    // Метод для встановлення кількості дітей
    public void setNumberOfChildren(int numberOfChildren) {
        this.numberOfChildren = numberOfChildren;
    }

    // Метод для встановлення статусу інвалідності
    public void setDisabled(boolean isDisabled) {
        this.isDisabled = isDisabled;
    }

    // Метод для встановлення пільг на дітей
    public void setChildBenefits(double childBenefits) {
        this.childBenefits = childBenefits;
    }

    // Метод для встановлення матеріальної допомоги
    public void setMaterialAid(double materialAid) {
        this.materialAid = materialAid;
    }

    // Метод для обчислення загальної суми податків з врахуванням пільг
    public double calculateTotalTaxes() {
        double totalIncome = 0;
        for (Income income : incomes) {
            totalIncome += income.getAmount();
        }

        double benefits = childBenefits + materialAid;

        // Додаткові пільги для багатодітних сімей та осіб з інвалідністю
        if (numberOfChildren > 2) {
            benefits += 500; // Додаткова пільга для багатодітних сімей
        }
        if (isDisabled) {
            benefits += 1000; // Додаткова пільга для осіб з інвалідністю
        }

        double taxableIncome = totalIncome - benefits;
        double totalTaxes = taxableIncome * 0.185; // 18% ПДФО + 1.5% військовий збір

        return totalTaxes > 0 ? totalTaxes : 0; // Податки не можуть бути від'ємними
    }

    // Метод для збереження даних у файл CSV
    public void saveToFile(String filename) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
            writer.println("source,amount,type");
            for (Income income : incomes) {
                writer.printf("%s,%.2f,%s%n", income.getSource(), income.getAmount(), income.getType());
            }
            writer.printf("benefits,%.2f,%.2f,%d,%b%n", childBenefits, materialAid, numberOfChildren, isDisabled);
        } catch (IOException e) {
            System.out.println("Помилка при збереженні даних: " + e.getMessage());
        }
    }

    // Метод для завантаження даних із файлу CSV
    public void loadFromFile(String filename) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;

            // Пропускаємо перший рядок з заголовками
            reader.readLine();  // Пропускаємо заголовки (source,amount,type)

            incomes.clear();  // Очищуємо попередні дані

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
            System.out.println("Дані успішно завантажені.");
        } catch (IOException e) {
            System.out.println("Помилка при завантаженні даних: " + e.getMessage());
        }
    }

    // Метод для отримання списку доходів
    public List<Income> getIncomes() {
        return incomes;
    }
}
