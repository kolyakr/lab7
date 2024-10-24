package commands;

import models.TaxPayer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import static org.mockito.Mockito.*;

class SetChildBenefitsCommandTest {

    private TaxPayer taxPayer; // Змінна для податківця
    private SetChildBenefitsCommand setChildBenefitsCommand; // Змінна для команди встановлення допомоги на дітей

    @BeforeEach
    void setUp() {
        taxPayer = mock(TaxPayer.class); // Створюємо мок для об'єкта TaxPayer
        setChildBenefitsCommand = new SetChildBenefitsCommand(taxPayer); // Ініціалізація команди встановлення допомоги на дітей
    }

    @Test
    void testExecuteValidInput() {
        String input = "2\n500\n"; // Введення для 2 дітей з 500 одиницями допомоги на дитину
        InputStream in = new ByteArrayInputStream(input.getBytes()); // Налаштовуємо стандартний ввід
        System.setIn(in);

        setChildBenefitsCommand.execute(); // Виконуємо команду

        verify(taxPayer, times(1)).setNumberOfChildren(2); // Перевіряємо, що метод setNumberOfChildren викликаний з 2
        verify(taxPayer, times(1)).setChildBenefits(500.0); // Перевіряємо, що метод setChildBenefits викликаний з 500.0
    }

    @Test
    void testExecuteInvalidNumberOfChildren() {
        String input = "-1\n3\n500\n"; // Неправильний ввід (негативне число), потім правильний
        InputStream in = new ByteArrayInputStream(input.getBytes()); // Налаштовуємо стандартний ввід
        System.setIn(in);

        setChildBenefitsCommand.execute(); // Виконуємо команду

        verify(taxPayer, times(1)).setNumberOfChildren(3); // Перевіряємо, що метод setNumberOfChildren викликаний з 3
        verify(taxPayer, times(1)).setChildBenefits(500.0); // Перевіряємо, що метод setChildBenefits викликаний з 500.0
    }

    @Test
    void testExecuteInvalidInputHandlingForChildren() {
        String input = "invalid\n2\n500\n"; // Неправильний ввід, потім правильний
        InputStream in = new ByteArrayInputStream(input.getBytes()); // Налаштовуємо стандартний ввід
        System.setIn(in);

        setChildBenefitsCommand.execute(); // Виконуємо команду

        verify(taxPayer, times(1)).setNumberOfChildren(2); // Перевіряємо, що метод setNumberOfChildren викликаний з 2
        verify(taxPayer, times(1)).setChildBenefits(500.0); // Перевіряємо, що метод setChildBenefits викликаний з 500.0
    }

    @Test
    void testExecuteInvalidBenefitAmount() {
        String input = "2\n-100\n300\n"; // Неправильний ввід (негативне число), потім правильний
        InputStream in = new ByteArrayInputStream(input.getBytes()); // Налаштовуємо стандартний ввід
        System.setIn(in);

        setChildBenefitsCommand.execute(); // Виконуємо команду

        verify(taxPayer, times(1)).setNumberOfChildren(2); // Перевіряємо, що метод setNumberOfChildren викликаний з 2
        verify(taxPayer, times(1)).setChildBenefits(300.0); // Перевіряємо, що метод setChildBenefits викликаний з 300.0
    }

    @Test
    void testExecuteInvalidInputHandlingForBenefits() {
        String input = "2\ninvalid\n300\n"; // Неправильний ввід, потім правильний
        InputStream in = new ByteArrayInputStream(input.getBytes()); // Налаштовуємо стандартний ввід
        System.setIn(in);

        setChildBenefitsCommand.execute(); // Виконуємо команду

        verify(taxPayer, times(1)).setNumberOfChildren(2); // Перевіряємо, що метод setNumberOfChildren викликаний з 2
        verify(taxPayer, times(1)).setChildBenefits(300.0); // Перевіряємо, що метод setChildBenefits викликаний з 300.0
    }
}
