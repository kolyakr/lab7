package com.taxsystem.models;

public class Income {
    private String source;
    private double amount;
    private String type;  // Додаємо тип доходу (основний, додатковий, подарунок тощо)

    public Income(String source, double amount, String type) {
        this.source = source;
        this.amount = amount;
        this.type = type;
    }

    // Публічний метод для отримання суми доходу
    public double getAmount() {
        return amount;
    }

    // Публічний метод для отримання джерела доходу
    public String getSource() {
        return source;
    }

    // Публічний метод для отримання типу доходу
    public String getType() {
        return type;
    }
}
