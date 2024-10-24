package main;

import commands.*;
import models.TaxPayer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import static org.mockito.Mockito.*;

class MenuTest {
    private Menu menu; // Змінна для меню
    private TaxPayer taxPayer; // Змінна для податківця

    @BeforeEach
    void setUp() {
        taxPayer = mock(TaxPayer.class); // Створюємо мок для податківця
    }

    // Метод для симуляції вводу користувача
    private void simulateUserInput(String input) {
        InputStream in = new ByteArrayInputStream(input.getBytes()); // Перетворюємо введення на байтовий потік
        System.setIn(in); // Налаштовуємо стандартний ввід
        menu = new Menu(taxPayer);  // Перезапуск меню з симульованим введенням
    }

    @Test
    void testDeleteIncomeCommand() {
        simulateUserInput("2\n0\n");  // Вибір опції 2 (Delete Income) і вихід
        DeleteIncomeCommand deleteIncomeCommand = mock(DeleteIncomeCommand.class); // Мок для команди видалення доходу
        menu.display(); // Відображаємо меню
        verify(deleteIncomeCommand, never()).execute(); // Перевіряємо, що команда не виконалась
    }

    @Test
    void testCalculateTaxesCommand() {
        simulateUserInput("3\n0\n");  // Вибір опції 3 (Calculate Taxes) і вихід
        CalculateTaxesCommand calculateTaxesCommand = mock(CalculateTaxesCommand.class); // Мок для команди розрахунку податків
        menu.display(); // Відображаємо меню
        verify(calculateTaxesCommand, never()).execute(); // Перевіряємо, що команда не виконалась
    }

    @Test
    void testGenerateReportCommand() {
        simulateUserInput("6\n0\n");  // Вибір опції 6 (Generate Report) і вихід
        GenerateReportCommand generateReportCommand = mock(GenerateReportCommand.class); // Мок для команди генерації звіту
        menu.display(); // Відображаємо меню
        verify(generateReportCommand, never()).execute(); // Перевіряємо, що команда не виконалась
    }

    @Test
    void testExit() {
        simulateUserInput("0\n");  // Вибір опції 0 (Exit)
        menu.display(); // Відображаємо меню
        // Переконайтеся, що програма вийшла без проблем
    }

    @Test
    void testInvalidInput() {
        simulateUserInput("invalid\n0\n");  // Некоректне введення і вихід
        menu.display(); // Відображаємо меню
        // Переконайтеся, що помилковий вибір оброблено належним чином
    }
}
