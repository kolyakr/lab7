package commands;

import models.TaxPayer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Arrays;

import static org.mockito.Mockito.*;

class FindTaxesCommandTest {
    private TaxPayer taxPayer;
    private FindTaxesCommand findTaxesCommand;

    @BeforeEach
    void setUp() {
        taxPayer = mock(TaxPayer.class); // Створюємо мок для податківця
        findTaxesCommand = new FindTaxesCommand(taxPayer); // Ініціалізація команди пошуку податків
    }

    @Test
    void testExecuteValidInput() {
        String input = "150\n300\n"; // Мінімум = 150, максимум = 300
        InputStream in = new ByteArrayInputStream(input.getBytes()); // Симульоване введення
        System.setIn(in);

        when(taxPayer.findTaxesInRange(150, 300)).thenReturn(Arrays.asList(175.0, 250.0));

        findTaxesCommand.execute(); // Виконання команди пошуку податків

        // Перевіряємо, що метод findTaxesInRange був викликаний з правильними параметрами
        verify(taxPayer, times(1)).findTaxesInRange(150, 300);
    }

    @Test
    void testExecuteInvalidMinAmount() {
        String input = "-50\n200\n300\n"; // Некоректний мінімум, коректний максимум
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        when(taxPayer.findTaxesInRange(200, 300)).thenReturn(Arrays.asList()); // Налаштування поведінки для коректного діапазону

        findTaxesCommand.execute();

        verify(taxPayer, times(1)).findTaxesInRange(200, 300);
    }

    @Test
    void testExecuteValidRange() {
        String input = "200\n400\n"; // Коректний діапазон
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        when(taxPayer.findTaxesInRange(200, 400)).thenReturn(Arrays.asList(250.0, 350.0)); // Налаштування поведінки мок-об'єкта

        findTaxesCommand.execute(); // Виконання команди пошуку податків

        // Перевіряємо, що метод findTaxesInRange був викликаний з правильними параметрами
        verify(taxPayer, times(1)).findTaxesInRange(200, 400);
    }

    @Test
    void testExecuteMaxLessThanMin() {
        String input = "200\n100\n300\n"; // Мінімум = 200, максимум спочатку = 100 (некоректний), потім = 300 (коректний)
        InputStream in = new ByteArrayInputStream(input.getBytes()); // Симульоване введення
        System.setIn(in); // Встановлюємо симульоване введення в System.in

        when(taxPayer.findTaxesInRange(200, 300)).thenReturn(Arrays.asList(250.0)); // Налаштування поведінки мок-об'єкта

        findTaxesCommand.execute(); // Виконання команди пошуку податків

        // Перевіряємо, що метод пошуку викликано з коректними параметрами
        verify(taxPayer, times(1)).findTaxesInRange(200, 300);
    }

    @Test
    void testExecuteInvalidNumericalInput() {
        String input = "abc\n150\n300\n"; // Спочатку некоректний ввід ("abc"), потім коректний мінімум і максимум
        InputStream in = new ByteArrayInputStream(input.getBytes()); // Симульоване введення
        System.setIn(in); // Встановлюємо симульоване введення в System.in

        when(taxPayer.findTaxesInRange(150, 300)).thenReturn(Arrays.asList(175.0, 250.0)); // Налаштування поведінки мок-об'єкта

        findTaxesCommand.execute(); // Виконання команди пошуку податків

        // Перевіряємо, що метод пошуку викликано з коректними параметрами
        verify(taxPayer, times(1)).findTaxesInRange(150, 300);
    }

    @Test
    void testExecuteNoTaxesFound() {
        String input = "100\n200\n"; // Коректний діапазон
        InputStream in = new ByteArrayInputStream(input.getBytes()); // Симульоване введення
        System.setIn(in); // Встановлюємо симульоване введення в System.in

        when(taxPayer.findTaxesInRange(100, 200)).thenReturn(Arrays.asList()); // Налаштування мок-об'єкта, що податків немає

        findTaxesCommand.execute(); // Виконання команди пошуку податків

        // Перевіряємо, що метод findTaxesInRange був викликаний
        verify(taxPayer, times(1)).findTaxesInRange(100, 200);
    }
}
