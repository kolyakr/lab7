package models;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;

public class Income {
    private static final Logger fileLogger = LogManager.getLogger(Income.class); // Логер для загальних дій
    private static final Logger errorLogger = LogManager.getLogger("ErrorLogger"); // Логер для помилок
    private static final Marker ERROR_MARKER = MarkerManager.getMarker("ERROR"); // Маркер для помилок

    private String source;
    private double amount;
    private String type;  // Тип доходу (основний, додатковий, подарунок тощо)

    public Income(String source, double amount, String type) {
        this.source = source;
        this.amount = amount;
        this.type = type;
        fileLogger.info("Income created: Source: {}, Amount: {}, Type: {}", source, amount, type);
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

    // Метод для обчислення податку з доходу
    public double calculateTax() {
        double taxRate = 0.185;  // 18% податок на доходи + 1.5% військовий збір
        switch (type) {
            case "main":
            case "additional":
                taxRate = 0.185;  // Стандартний податок для основних і додаткових доходів
                break;
            case "gift":
                taxRate = 0.05;  // Знижений податок на подарунки
                break;
            // Інші варіанти можуть бути додані при необхідності
        }
        double calculatedTax = amount * taxRate;
        fileLogger.info("Calculating tax for Income: Source: {}, Amount: {}, Tax: {}", source, amount, calculatedTax);
        return calculatedTax;
    }
}
