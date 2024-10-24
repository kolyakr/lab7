package commands;

import models.TaxPayer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import static org.mockito.Mockito.*;

class SortTaxesCommandTest {

    private TaxPayer taxPayer; // Змінна для податківця
    private SortTaxesCommand sortTaxesCommand; // Змінна для команди сортування податків

    @BeforeEach
    void setUp() {
        taxPayer = mock(TaxPayer.class); // Створюємо мок для об'єкта TaxPayer
        sortTaxesCommand = new SortTaxesCommand(taxPayer); // Ініціалізація команди
    }

    @Test
    void testExecuteSortAscending() {
        String input = "1\n"; // Введення для сортування за зростанням
        InputStream in = new ByteArrayInputStream(input.getBytes()); // Налаштовуємо стандартний ввід
        System.setIn(in);

        sortTaxesCommand.execute(); // Виконуємо команду

        verify(taxPayer, times(1)).sortTaxesAscending(); // Перевіряємо, що метод викликано один раз
        verify(taxPayer, never()).sortTaxesDescending(); // Переконаємось, що не викликано сортировку за спаданням
    }

    @Test
    void testExecuteSortDescending() {
        String input = "2\n"; // Введення для сортування за спаданням
        InputStream in = new ByteArrayInputStream(input.getBytes()); // Налаштовуємо стандартний ввід
        System.setIn(in);

        sortTaxesCommand.execute(); // Виконуємо команду

        verify(taxPayer, never()).sortTaxesAscending(); // Переконаємось, що не викликано сортировку за зростанням
        verify(taxPayer, times(1)).sortTaxesDescending(); // Перевіряємо, що метод викликано один раз
    }

    @Test
    void testExecuteInvalidChoice() {
        String input = "3\n1\n"; // Неправильний ввід, потім правильний
        InputStream in = new ByteArrayInputStream(input.getBytes()); // Налаштовуємо стандартний ввід
        System.setIn(in);

        sortTaxesCommand.execute(); // Виконуємо команду

        verify(taxPayer, times(1)).sortTaxesAscending(); // Перевірка, що викликано сортировку за зростанням після виправлення
        verify(taxPayer, never()).sortTaxesDescending(); // Переконаємось, що не викликано сортировку за спаданням
    }

    @Test
    void testExecuteInvalidInputHandling() {
        String input = "invalid\n2\n"; // Неправильний ввід, потім правильний
        InputStream in = new ByteArrayInputStream(input.getBytes()); // Налаштовуємо стандартний ввід
        System.setIn(in);

        sortTaxesCommand.execute(); // Виконуємо команду

        verify(taxPayer, times(1)).sortTaxesDescending(); // Перевірка, що викликано сортировку за спаданням
        verify(taxPayer, never()).sortTaxesAscending(); // Переконаємось, що не викликано сортировку за зростанням
    }
}
