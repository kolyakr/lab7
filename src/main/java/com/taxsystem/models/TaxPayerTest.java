package com.taxsystem.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TaxPayerTest {

    private TaxPayer taxPayer;

    @BeforeEach
    void setUp() {
        taxPayer = new TaxPayer();
    }

    @Test
    void testAddIncome() {
        Income income = new Income("Основна робота", 100000, "Основний");
        taxPayer.addIncome(income);

        assertEquals(1, taxPayer.getIncomes().size(), "Кількість доходів повинна бути 1");
        assertEquals(100000, taxPayer.getIncomes().get(0).getAmount(), "Сума доходу повинна бути 100000");
    }

    @Test
    void testRemoveIncome() {
        Income income = new Income("Фріланс", 40000, "Додатковий");
        taxPayer.addIncome(income);

        boolean removed = taxPayer.removeIncome(0);
        assertTrue(removed, "Доход повинен бути успішно видалений");
        assertEquals(0, taxPayer.getIncomes().size(), "Список доходів повинен бути порожнім після видалення");
    }

    @Test
    void testCalculateTotalTaxes() {
        taxPayer.addIncome(new Income("Основна робота", 120000, "Основний"));
        taxPayer.addIncome(new Income("Фріланс", 40000, "Додатковий"));

        double totalTaxes = taxPayer.calculateTotalTaxes();
        assertEquals(31200, totalTaxes, 0.01, "Загальна сума податків повинна бути 31200 грн (19.5% від 160000)");
    }

    @Test
    void testCalculateTotalTaxesWithBenefits() {
        taxPayer.addIncome(new Income("Основна робота", 120000, "Основний"));
        taxPayer.setChildBenefits(3000);
        taxPayer.setMaterialAid(5000);

        double totalTaxes = taxPayer.calculateTotalTaxes();
        assertEquals(20520, totalTaxes, 0.01, "Сума податків повинна враховувати пільги (19.5% від 112000)");
    }

    @Test
    void testFindTaxesInRange() {
        taxPayer.addIncome(new Income("Основна робота", 120000, "Основний"));
        taxPayer.addIncome(new Income("Фріланс", 40000, "Додатковий"));

        var taxesInRange = taxPayer.findTaxesInRange(7000, 25000);
        assertEquals(1, taxesInRange.size(), "Повинен бути знайдений 1 податок у діапазоні");
    }
}
