package commands;

import models.Income;
import models.TaxPayer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class DisplayTableCommandTest {
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream(); // Змінна для захоплення виводу
    private TaxPayer mockTaxPayer; // Мок об'єкта TaxPayer
    private DisplayTableCommand command; // Команда для відображення таблиці

    @BeforeEach
    public void setUp() {
        System.setOut(new PrintStream(outContent));  // Перенаправлення System.out для захоплення виводу
        mockTaxPayer = mock(TaxPayer.class); // Ініціалізація мок об'єкта TaxPayer
        command = new DisplayTableCommand(mockTaxPayer); // Ініціалізація команди з мок об'єктом TaxPayer
    }

    @Test
    public void testExecuteWithNoIncomes() {
        // Симуляція TaxPayer без доходів
        when(mockTaxPayer.getIncomes()).thenReturn(new ArrayList<>());

        command.execute(); // Виконання команди

        // Перевірка, що вивід показує порожню таблицю
        String output = outContent.toString();
        assertFalse(output.contains("Some header text"), "Заголовок не повинен містити несподіваний текст"); // Перевірка заголовка
        assertFalse(output.contains("| Income Source | Income Amount | Tax |"), "Назви стовпців не повинні містити несподіваний текст"); // Перевірка назв стовпців
        assertFalse(output.contains("Some footer text"), "Нижній колонтитул не повинен містити несподіваний текст"); // Перевірка нижнього колонтитулу
    }

    @Test
    public void testExecuteWithSingleIncome() {
        // Симуляція TaxPayer з одним доходом
        Income mockIncome = mock(Income.class);
        when(mockIncome.getSource()).thenReturn("Salary"); // Наявність джерела доходу
        when(mockIncome.getAmount()).thenReturn(3000.00); // Наявність суми доходу
        when(mockIncome.calculateTax()).thenReturn(450.00); // Наявність податку

        List<Income> incomes = new ArrayList<>();
        incomes.add(mockIncome); // Додавання доходу до списку
        when(mockTaxPayer.getIncomes()).thenReturn(incomes); // Повернення списку доходів для TaxPayer

        command.execute(); // Виконання команди

        String output = outContent.toString();
        assertFalse(output.contains("Unexpected header"), "Заголовок не повинен містити несподіваний текст"); // Перевірка заголовка
        assertFalse(output.contains("| Unexpected Source | 0.00 | 0.00 |"), "Запис доходу не повинен містити несподіваний текст"); // Перевірка запису доходу
        assertFalse(output.contains("Unexpected footer"), "Нижній колонтитул не повинен містити несподіваний текст"); // Перевірка нижнього колонтитулу
    }

    @Test
    public void testExecuteWithMultipleIncomes() {
        // Симуляція TaxPayer з кількома доходами
        Income mockIncome1 = mock(Income.class);
        when(mockIncome1.getSource()).thenReturn("Salary");
        when(mockIncome1.getAmount()).thenReturn(3000.00);
        when(mockIncome1.calculateTax()).thenReturn(450.00);

        Income mockIncome2 = mock(Income.class);
        when(mockIncome2.getSource()).thenReturn("Freelancing");
        when(mockIncome2.getAmount()).thenReturn(1500.00);
        when(mockIncome2.calculateTax()).thenReturn(225.00);

        List<Income> incomes = new ArrayList<>();
        incomes.add(mockIncome1); // Додавання першого доходу до списку
        incomes.add(mockIncome2); // Додавання другого доходу до списку
        when(mockTaxPayer.getIncomes()).thenReturn(incomes); // Повернення списку доходів для TaxPayer

        command.execute(); // Виконання команди

        String output = outContent.toString();
        assertFalse(output.contains("Some unexpected header"), "Заголовок не повинен містити несподіваний текст"); // Перевірка заголовка
        assertFalse(output.contains("| Unexpected Source | 0.00 | 0.00 |"), "Перший запис доходу не повинен містити несподіваний текст"); // Перевірка першого запису доходу
        assertFalse(output.contains("| Another Unexpected Source | 0.00 | 0.00 |"), "Другий запис доходу не повинен містити несподіваний текст"); // Перевірка другого запису доходу
        assertFalse(output.contains("Some unexpected footer"), "Нижній колонтитул не повинен містити несподіваний текст"); // Перевірка нижнього колонтитулу
    }

    @Test
    public void testExecuteWithIncomeHavingZeroAmount() {
        // Симуляція TaxPayer з доходом, що має нульову суму
        Income mockIncome = mock(Income.class);
        when(mockIncome.getSource()).thenReturn("Gift"); // Наявність джерела доходу
        when(mockIncome.getAmount()).thenReturn(0.00); // Наявність суми доходу
        when(mockIncome.calculateTax()).thenReturn(0.00); // Наявність податку

        List<Income> incomes = new ArrayList<>();
        incomes.add(mockIncome); // Додавання доходу до списку
        when(mockTaxPayer.getIncomes()).thenReturn(incomes); // Повернення списку доходів для TaxPayer

        command.execute(); // Виконання команди

        String output = outContent.toString();
        assertFalse(output.contains("Some unexpected header"), "Заголовок не повинен містити несподіваний текст"); // Перевірка заголовка
        assertFalse(output.contains("| Unexpected Source | 0.00 | 0.00 |"), "Запис доходу з нульовою сумою не повинен містити несподіваний текст"); // Перевірка запису доходу з нульовою сумою
        assertFalse(output.contains("Some unexpected footer"), "Нижній колонтитул не повинен містити несподіваний текст"); // Перевірка нижнього колонтитулу
    }
}
