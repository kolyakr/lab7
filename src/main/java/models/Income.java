package models;

public class Income {
    private String source;
    private double amount;
    private String type;  // Adding income type (main, additional, gift, etc.)

    public Income(String source, double amount, String type) {
        this.source = source;
        this.amount = amount;
        this.type = type;
    }

    public double getAmount() {
        return amount;
    }

    public String getSource() {
        return source;
    }

    public String getType() {
        return type;
    }

    // Method to calculate tax from income
    public double calculateTax() {
        double taxRate = 0.185;  // 18% personal income tax + 1.5% military levy
        // Logic for different income types can be added if needed
        switch (type) {
            case "main":
            case "additional":
                taxRate = 0.185;  // Standard tax for main and additional incomes
                break;
            case "gift":
                taxRate = 0.05;  // Lower tax for gifts
                break;
            // Add other cases as needed
        }
        return amount * taxRate;
    }
}
