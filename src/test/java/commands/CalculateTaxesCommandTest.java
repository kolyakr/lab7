package commands;

import models.TaxPayer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CalculateTaxesCommandTest {
    private TaxPayer taxPayer;
    private CalculateTaxesCommand calculateTaxesCommand;

    @BeforeEach
    void setUp() {
        // Імітуємо об'єкт платника податків і створюємо об'єкт команди для обчислення податків
        taxPayer = mock(TaxPayer.class);
        calculateTaxesCommand = new CalculateTaxesCommand(taxPayer);
    }

    @Test
    void testCalculateTaxesValid() {
        // Імітуємо обчислення податків, яке повертає 100.0
        when(taxPayer.calculateTotalTaxes()).thenReturn(100.0);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        // Виконуємо команду
        calculateTaxesCommand.execute();

        // Перевіряємо, чи містить вивід правильне повідомлення про загальну суму податків
        assertTrue(outputStream.toString().contains("Total amount of taxes: 100.0"));
    }

    @Test
    void testCalculateTaxesZero() {
        // Імітуємо обчислення податків, яке повертає 0.0
        when(taxPayer.calculateTotalTaxes()).thenReturn(0.0);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        // Виконуємо команду
        calculateTaxesCommand.execute();

        // Перевіряємо, чи містить вивід правильне повідомлення про нульові податки
        assertTrue(outputStream.toString().contains("Total amount of taxes: 0.0"));
    }

    @Test
    void testCalculateTaxesNegative() {
        // Імітуємо обчислення податків, яке повертає -50.0
        when(taxPayer.calculateTotalTaxes()).thenReturn(-50.0);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        // Виконуємо команду
        calculateTaxesCommand.execute();

        // Перевіряємо, чи містить вивід правильне повідомлення про негативні податки
        assertTrue(outputStream.toString().contains("Total amount of taxes: -50.0"));
    }

    @Test
    void testCalculateTaxesHighValue() {
        // Імітуємо обчислення податків на велику суму 1 000 000
        when(taxPayer.calculateTotalTaxes()).thenReturn(1_000_000.0);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        // Виконуємо команду
        calculateTaxesCommand.execute();

        // Перевіряємо, чи містить вивід правильне повідомлення для великої суми податків
        assertTrue(outputStream.toString().contains("Total amount of taxes: 1000000.0"));
    }

    @Test
    void testCalculateTaxesLowValue() {
        // Імітуємо обчислення податків на низьку суму 0.01
        when(taxPayer.calculateTotalTaxes()).thenReturn(0.01);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        // Виконуємо команду
        calculateTaxesCommand.execute();

        // Перевіряємо, чи містить вивід правильне повідомлення для низької суми податків
        assertTrue(outputStream.toString().contains("Total amount of taxes: 0.01"));
    }

    @Test
    void testCalculateTaxesNegativeEdge() {
        // Імітуємо обчислення податків на малу негативну суму -0.01
        when(taxPayer.calculateTotalTaxes()).thenReturn(-0.01);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        // Виконуємо команду
        calculateTaxesCommand.execute();

        // Перевіряємо, чи містить вивід правильне повідомлення для малих негативних податків
        assertTrue(outputStream.toString().contains("Total amount of taxes: -0.01"));
    }

    @Test
    void testCalculateTaxesMultipleCalls() {
        // Імітуємо обчислення податків для двох викликів команди з різними результатами
        when(taxPayer.calculateTotalTaxes()).thenReturn(200.0, 300.0);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        // Виконуємо команду двічі
        calculateTaxesCommand.execute();
        calculateTaxesCommand.execute();

        String output = outputStream.toString();
        // Перевіряємо, що обидва повідомлення для обчислення податків присутні
        assertTrue(output.contains("Total amount of taxes: 200.0"));
        assertTrue(output.contains("Total amount of taxes: 300.0"));
        assertEquals(2, output.split("Total amount of taxes:").length - 1); // Перевіряємо, що обидва повідомлення є
    }

    // Інші тести залишаються з аналогічними коментарями для пояснення кожного тестового випадку
}
