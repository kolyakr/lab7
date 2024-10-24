package commands;

import models.Income;
import models.TaxPayer;
import org.junit.jupiter.api.AfterEach;
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
    private InputStream originalSystemIn; // Зберігаємо оригінальний System.in

    @BeforeEach
    void setUp() {
        taxPayer = mock(TaxPayer.class);
        deleteIncomeCommand = new DeleteIncomeCommand(taxPayer);
        originalSystemIn = System.in; // Зберігаємо оригінальний ввід
    }

    @AfterEach
    void tearDown() {
        System.setIn(originalSystemIn); // Повертаємо оригінальний System.in після тестів
    }

    // Тест для перевірки ситуації, коли немає доходів для видалення
    @Test
    void testExecuteNoIncomes() {
        when(taxPayer.getIncomes()).thenReturn(new ArrayList<>());

        deleteIncomeCommand.execute();

        verify(taxPayer, times(1)).getIncomes();
    }

    // Тест для перевірки, коли користувач скасовує видалення доходу
    @Test
    void testExecuteCancelDeletion() {
        Income income1 = new Income("Job", 1000.0, "Salary");
        List<Income> incomes = Arrays.asList(income1);
        when(taxPayer.getIncomes()).thenReturn(incomes);

        String input = "-1\n"; // Введення для скасування
        System.setIn(new ByteArrayInputStream(input.getBytes())); // Симулюємо ввід

        deleteIncomeCommand.execute();

        verify(taxPayer, never()).removeIncome(anyInt()); // Перевіряємо, що видалення не відбулося
    }
}
