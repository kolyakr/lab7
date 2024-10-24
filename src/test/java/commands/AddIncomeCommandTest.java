package commands;

import models.Income;
import models.TaxPayer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AddIncomeCommandTest {

    private TaxPayer taxPayer;
    private AddIncomeCommand addIncomeCommand;

    @BeforeEach
    void setUp() {
        // Мокаємо (імітуємо) об'єкт платника податків і команду додавання доходу
        taxPayer = mock(TaxPayer.class);
        addIncomeCommand = new AddIncomeCommand(taxPayer);
    }

    @Test
    void testExecuteValidInput() {
        // Емітуємо коректне введення користувачем
        String input = "Salary\nmain\n1000\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        addIncomeCommand.execute();

        // Перевіряємо, що метод addIncome викликається з правильними параметрами
        verify(taxPayer, times(1)).addIncome(argThat(income ->
                "Salary".equals(income.getSource()) &&    // Джерело доходу: "Зарплата"
                        "main".equals(income.getType()) &&  // Тип доходу: "Основний"
                        1000.0 == income.getAmount()));     // Сума: 1000
    }

    @Test
    void testExecuteInvalidAmount() {
        // Емітуємо некоректне введення (негативна сума), потім правильне
        String input = "Salary\nmain\n-1000\n1000\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        addIncomeCommand.execute();

        // Перевіряємо, що метод addIncome викликається з правильними значеннями після виправлення
        verify(taxPayer, times(1)).addIncome(argThat(income ->
                "Salary".equals(income.getSource()) &&    // Джерело доходу: "Зарплата"
                        "main".equals(income.getType()) &&  // Тип доходу: "Основний"
                        1000.0 == income.getAmount()));     // Сума: 1000
    }

    @Test
    void testExecuteInvalidInputHandling() {
        // Емітуємо некоректне введення (некоректна сума як текст), потім правильне
        String input = "Salary\nmain\ninvalid\n1000\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        addIncomeCommand.execute();

        // Перевіряємо, що метод addIncome викликається з правильними параметрами після виправлення
        verify(taxPayer, times(1)).addIncome(argThat(income ->
                "Salary".equals(income.getSource()) &&    // Джерело доходу: "Зарплата"
                        "main".equals(income.getType()) &&  // Тип доходу: "Основний"
                        1000.0 == income.getAmount()));     // Сума: 1000
    }
}
