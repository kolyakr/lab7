package commands;

import models.TaxPayer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class LoadDataCommandTest {
    private TaxPayer taxPayer; // Змінна для податківця
    private LoadDataCommand loadDataCommand; // Змінна для команди завантаження даних

    @BeforeEach
    void setUp() {
        taxPayer = mock(TaxPayer.class); // Створюємо мок для об'єкта TaxPayer
        loadDataCommand = new LoadDataCommand(taxPayer); // Ініціалізація команди завантаження даних
    }

    @Test
    void testLoadDataValidInput() throws IOException {
        String input = "test_load.csv\n"; // Дійсна назва файлу
        System.setIn(new ByteArrayInputStream(input.getBytes())); // Налаштовуємо стандартний ввід

        assertDoesNotThrow(() -> loadDataCommand.execute()); // Перевіряємо, що виклик методу не викликає виключення
        verify(taxPayer).loadFromFile("test_load.csv"); // Перевіряємо, що метод loadFromFile був викликаний

        System.setIn(System.in); // Відновлюємо оригінальний ввід
    }

    @Test
    void testLoadDataIOException() throws IOException {
        doThrow(new IOException("File error")).when(taxPayer).loadFromFile(any()); // Налаштовуємо, щоб виклик метод викликав виключення
        String input = "test_load.csv\n"; // Дійсна назва файлу
        System.setIn(new ByteArrayInputStream(input.getBytes())); // Налаштовуємо стандартний ввід

        assertDoesNotThrow(() -> loadDataCommand.execute()); // Перевіряємо, що виклик обробляє виключення коректно

        System.setIn(System.in); // Відновлюємо оригінальний ввід
    }

    @Test
    void testLoadDataInvalidFilename() throws IOException {
        String input = "invalid_file.txt\n"; // Невірна назва файлу
        System.setIn(new ByteArrayInputStream(input.getBytes())); // Налаштовуємо стандартний ввід

        assertDoesNotThrow(() -> loadDataCommand.execute()); // Перевіряємо, що виклик не викликає виключення
        verify(taxPayer).loadFromFile("invalid_file.txt"); // Перевіряємо, що метод loadFromFile був викликаний з невірною назвою файлу

        System.setIn(System.in); // Відновлюємо оригінальний ввід
    }

    // Додаткові тести для кращого покриття
    @Test
    void testLoadDataSpecialCharactersInFilename() throws IOException {
        String input = "test_load_@#$.csv\n"; // Назва файлу з спеціальними символами
        System.setIn(new ByteArrayInputStream(input.getBytes())); // Налаштовуємо стандартний ввід

        assertDoesNotThrow(() -> loadDataCommand.execute()); // Перевіряємо, що виклик не викликає виключення
        verify(taxPayer).loadFromFile("test_load_@#$.csv"); // Перевіряємо, що метод loadFromFile був викликаний з спеціальними символами

        System.setIn(System.in); // Відновлюємо оригінальний ввід
    }

    @Test
    void testLoadDataLongFilename() throws IOException {
        String input = "a_very_long_filename_that_exceeds_normal_length.csv\n"; // Дуже довга назва файлу
        System.setIn(new ByteArrayInputStream(input.getBytes())); // Налаштовуємо стандартний ввід

        assertDoesNotThrow(() -> loadDataCommand.execute()); // Перевіряємо, що виклик не викликає виключення
        verify(taxPayer).loadFromFile("a_very_long_filename_that_exceeds_normal_length.csv"); // Перевіряємо, що метод loadFromFile був викликаний

        System.setIn(System.in); // Відновлюємо оригінальний ввід
    }

    @Test
    void testLoadDataInvalidCharactersInFilename() throws IOException {
        String input = "inva|id*name.csv\n"; // Неприпустимі символи в назві файлу
        System.setIn(new ByteArrayInputStream(input.getBytes())); // Налаштовуємо стандартний ввід

        assertDoesNotThrow(() -> loadDataCommand.execute()); // Перевіряємо, що виклик не викликає виключення
        verify(taxPayer).loadFromFile("inva|id*name.csv"); // Перевіряємо, що метод loadFromFile був викликаний з невірною назвою файлу

        System.setIn(System.in); // Відновлюємо оригінальний ввід
    }

    @Test
    void testLoadDataWithNullFilename() throws IOException {
        String input = "null\n"; // Симуляція завантаження файлу з назвою "null"
        System.setIn(new ByteArrayInputStream(input.getBytes())); // Налаштовуємо стандартний ввід

        assertDoesNotThrow(() -> loadDataCommand.execute()); // Перевіряємо, що виклик не викликає виключення
        verify(taxPayer).loadFromFile("null"); // Перевіряємо, що метод loadFromFile був викликаний з "null"

        System.setIn(System.in); // Відновлюємо оригінальний ввід
    }
}
