package models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class IncomeTest {
    private Income mainIncome; // Головний дохід
    private Income additionalIncome; // Додатковий дохід
    private Income giftIncome; // Дохід у вигляді подарунка

    @BeforeEach
    public void setUp() {
        // Ініціалізація доходів перед кожним тестом
        mainIncome = new Income("Main Job", 10000, "main");
        additionalIncome = new Income("Freelance", 5000, "additional");
        giftIncome = new Income("Gift from Relative", 2000, "gift");
    }

    @Test
    public void testGetAmount() {
        // Перевірка правильності отримання суми доходу
        assertEquals(10000, mainIncome.getAmount());
        assertEquals(5000, additionalIncome.getAmount());
        assertEquals(2000, giftIncome.getAmount());
    }

    @Test
    public void testGetSource() {
        // Перевірка правильності отримання джерела доходу
        assertEquals("Main Job", mainIncome.getSource());
        assertEquals("Freelance", additionalIncome.getSource());
        assertEquals("Gift from Relative", giftIncome.getSource());
    }

    @Test
    public void testGetType() {
        // Перевірка правильності отримання типу доходу
        assertEquals("main", mainIncome.getType());
        assertEquals("additional", additionalIncome.getType());
        assertEquals("gift", giftIncome.getType());
    }

    @Test
    public void testCalculateTaxForMainIncome() {
        // Перевірка розрахунку податку для головного доходу: 10000 * 0.185 = 1850
        assertEquals(1850.0, mainIncome.calculateTax(), 0.001);
    }

    @Test
    public void testCalculateTaxForAdditionalIncome() {
        // Перевірка розрахунку податку для додаткового доходу: 5000 * 0.185 = 925
        assertEquals(925.0, additionalIncome.calculateTax(), 0.001);
    }

    @Test
    public void testCalculateTaxForGiftIncome() {
        // Перевірка розрахунку податку для подарунка: 2000 * 0.05 = 100
        assertEquals(100.0, giftIncome.calculateTax(), 0.001);
    }

    @Test
    public void testCalculateTaxForNegativeAmount() {
        // Перевірка розрахунку податку для негативної суми доходу
        Income negativeIncome = new Income("Negative Job", -5000, "main");
        assertEquals(-925.0, negativeIncome.calculateTax(), 0.001); // Очікуємо, що податок буде негативним
    }

    @Test
    public void testCalculateTaxForZeroAmount() {
        // Перевірка розрахунку податку для нульової суми доходу
        Income zeroIncome = new Income("Zero Job", 0, "main");
        assertEquals(0.0, zeroIncome.calculateTax(), 0.001); // Очікуємо, що податок буде нульовим
    }

    // Нові тести

    @Test
    public void testCalculateTaxForEdgeIncomeAmounts() {
        // Перевірка розрахунку податку для крайніх значень доходу
        Income edgeIncome1 = new Income("Edge Job", 1, "main");
        assertEquals(0.185, edgeIncome1.calculateTax(), 0.001); // 1 * 0.185

        Income edgeIncome2 = new Income("High Edge Job", 1000000, "main");
        assertEquals(185000.0, edgeIncome2.calculateTax(), 0.001); // 1000000 * 0.185
    }

    @Test
    public void testCalculateTaxForAdditionalIncomeEdge() {
        // Перевірка розрахунку податку для крайніх значень додаткового доходу
        Income edgeAdditionalIncome = new Income("Edge Freelance", 0.01, "additional");
        assertEquals(0.00185, edgeAdditionalIncome.calculateTax(), 0.0001); // 0.01 * 0.185
    }

    @Test
    public void testCalculateTaxForGiftIncomeEdge() {
        // Перевірка розрахунку податку для крайніх значень подарунка
        Income edgeGiftIncome = new Income("Edge Gift", 0.01, "gift");
        assertEquals(0.0005, edgeGiftIncome.calculateTax(), 0.0001); // 0.01 * 0.05
    }

    @Test
    public void testCalculateTaxForHighGiftIncome() {
        // Перевірка розрахунку податку для високого подарунка
        Income highGiftIncome = new Income("High Gift", 20000, "gift");
        assertEquals(1000.0, highGiftIncome.calculateTax(), 0.001); // 20000 * 0.05
    }

    @Test
    public void testCalculateTaxForUnrecognizedIncomeType() {
        // Перевірка розрахунку податку для невідомого типу доходу
        Income unknownIncome = new Income("Unknown Type", 10000, "unknown");
        assertEquals(1850.0, unknownIncome.calculateTax(), 0.001); // Слід обробити як головний дохід
    }

    @Test
    public void testCalculateTaxWithWhitespaceType() {
        // Перевірка розрахунку податку для типу з пробілами
        Income incomeWithWhitespace = new Income("Job", 10000, " main "); // Додаткові пробіли
        assertEquals(1850.0, incomeWithWhitespace.calculateTax(), 0.001); // Має розраховуватись правильно
    }

    // Додаткові тести для подальшого покриття
    @Test
    public void testCalculateTaxForEdgeCaseWithNullSource() {
        // Перевірка розрахунку податку для доходу з нульовим джерелом
        Income incomeWithNullSource = new Income(null, 1000, "main");
        assertEquals(185.0, incomeWithNullSource.calculateTax(), 0.001); // Джерело не повинно впливати на розрахунок податку
    }

    @Test
    public void testCalculateTaxForMaxDoubleValue() {
        // Перевірка розрахунку податку для максимального значення Double
        Income maxIncome = new Income("Max Income", Double.MAX_VALUE, "main");
        assertTrue(maxIncome.calculateTax() > 0); // Має розрахувати позитивний податок, але значення буде дуже великим
    }

    @Test
    public void testCalculateTaxForMinPositiveDoubleValue() {
        // Перевірка розрахунку податку для найменшого позитивного значення Double
        Income minPositiveIncome = new Income("Min Positive Income", Double.MIN_VALUE, "main");
        assertEquals(0.0, minPositiveIncome.calculateTax(), 0.001); // Має розрахуватись до 0 для дуже малих значень
    }
}
