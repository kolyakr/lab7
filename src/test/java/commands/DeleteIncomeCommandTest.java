package commands;

import models.Income;
import models.TaxPayer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;

class DeleteIncomeCommandTest {
    private TaxPayer taxPayer;
    private DeleteIncomeCommand deleteIncomeCommand;

    // Ініціалізуємо об'єкти перед кожним тестом
    @BeforeEach
    void setUp() {
        taxPayer = mock(TaxPayer.class);
        deleteIncomeCommand = new DeleteIncomeCommand(taxPayer);
    }

    // Тест для перевірки ситуації, коли немає доходів для видалення
    @Test
    void testExecuteNoIncomes() {
        // Повертаємо порожній список доходів
        when(taxPayer.getIncomes()).thenReturn(new ArrayList<>());

        // Виконуємо команду видалення доходу
        deleteIncomeCommand.execute();

        // Перевіряємо, що метод для отримання доходів було викликано
        verify(taxPayer, times(1)).getIncomes();
    }

    // Тест для перевірки, коли користувач скасовує видалення доходу
    @Test
    void testExecuteCancelDeletion() {
        // Створюємо фіктивний дохід
        Income income1 = new Income("Job", 1000.0, "Salary");
        List<Income> incomes = Arrays.asList(income1);
        when(taxPayer.getIncomes()).thenReturn(incomes);

        // Симулюємо введення користувачем значення для скасування видалення (-1)
        String input = "-1\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        // Виконуємо команду
        deleteIncomeCommand.execute();

        // Перевіряємо, що дохід не було видалено
        verify(taxPayer, never()).removeIncome(anyInt());
    }
}
