package com.taxsystem.models;

public class TaxPayer {
    private String name;
    private double totalIncome;

    // Constructors, getters and setters
    public TaxPayer(String name, double totalIncome) {
        this.name = name;
        this.totalIncome = totalIncome;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getTotalIncome() {
        return totalIncome;
    }

    public void setTotalIncome(double totalIncome) {
        this.totalIncome = totalIncome;
    }
}
