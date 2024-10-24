package commands;

import models.TaxPayer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class SaveDataCommandTest {
    private TaxPayer taxPayer; // Змінна для податківця
    private SaveDataCommand saveDataCommand; // Змінна для команди збереження даних

    @BeforeEach
    void setUp() {
        taxPayer = mock(TaxPayer.class); // Створюємо мок для об'єкта TaxPayer
        saveDataCommand = new SaveDataCommand(taxPayer); // Ініціалізація команди збереження даних
    }

    @Test
    void testSaveDataValidInput() throws IOException {
        String input = "test_save.csv\n"; // Дійсна назва файлу
        System.setIn(new ByteArrayInputStream(input.getBytes())); // Налаштовуємо стандартний ввід

        assertDoesNotThrow(() -> saveDataCommand.execute()); // Перевіряємо, що виклик методу не викликає виключення
        verify(taxPayer).saveToFile("test_save.csv"); // Перевіряємо, що метод saveToFile був викликаний

        System.setIn(System.in); // Відновлюємо оригінальний ввід
    }

    @Test
    void testSaveDataIOException() throws IOException {
        doThrow(new IOException("File error")).when(taxPayer).saveToFile(any()); // Налаштовуємо, щоб виклик метод викликав виключення
        String input = "test_save.csv\n"; // Дійсна назва файлу
        System.setIn(new ByteArrayInputStream(input.getBytes())); // Налаштовуємо стандартний ввід

        assertDoesNotThrow(() -> saveDataCommand.execute()); // Перевіряємо, що виклик обробляє виключення коректно

        System.setIn(System.in); // Відновлюємо оригінальний ввід
    }

    @Test
    void testSaveDataInvalidFilename() throws IOException {
        String input = "invalid_file.txt\n"; // Невірна назва файлу
        System.setIn(new ByteArrayInputStream(input.getBytes())); // Налаштовуємо стандартний ввід

        assertDoesNotThrow(() -> saveDataCommand.execute()); // Перевіряємо, що виклик не викликає виключення
        verify(taxPayer).saveToFile("invalid_file.txt"); // Перевіряємо, що метод saveToFile був викликаний з невірною назвою файлу

        System.setIn(System.in); // Відновлюємо оригінальний ввід
    }

    // Додаткові тести для кращого покриття
    @Test
    void testSaveDataSpecialCharactersInFilename() throws IOException {
        String input = "test_save_@#$.csv\n"; // Назва файлу з спеціальними символами
        System.setIn(new ByteArrayInputStream(input.getBytes())); // Налаштовуємо стандартний ввід

        assertDoesNotThrow(() -> saveDataCommand.execute()); // Перевіряємо, що виклик не викликає виключення
        verify(taxPayer).saveToFile("test_save_@#$.csv"); // Перевіряємо, що метод saveToFile був викликаний з спеціальними символами

        System.setIn(System.in); // Відновлюємо оригінальний ввід
    }

    @Test
    void testSaveDataLongFilename() throws IOException {
        String input = "a_very_long_filename_that_exceeds_normal_length.csv\n"; // Дуже довга назва файлу
        System.setIn(new ByteArrayInputStream(input.getBytes())); // Налаштовуємо стандартний ввід

        assertDoesNotThrow(() -> saveDataCommand.execute()); // Перевіряємо, що виклик не викликає виключення
        verify(taxPayer).saveToFile("a_very_long_filename_that_exceeds_normal_length.csv"); // Перевіряємо, що метод saveToFile був викликаний

        System.setIn(System.in); // Відновлюємо оригінальний ввід
    }

    @Test
    void testSaveDataInvalidCharactersInFilename() throws IOException {
        String input = "inva|id*name.csv\n"; // Неприпустимі символи в назві файлу
        System.setIn(new ByteArrayInputStream(input.getBytes())); // Налаштовуємо стандартний ввід

        assertDoesNotThrow(() -> saveDataCommand.execute()); // Перевіряємо, що виклик не викликає виключення
        verify(taxPayer).saveToFile("inva|id*name.csv"); // Перевіряємо, що метод saveToFile був викликаний з невірною назвою файлу

        System.setIn(System.in); // Відновлюємо оригінальний ввід
    }

    @Test
    void testSaveDataWithNullFilename() throws IOException {
        String input = "null\n"; // Симуляція збереження файлу з назвою "null"
        System.setIn(new ByteArrayInputStream(input.getBytes())); // Налаштовуємо стандартний ввід

        assertDoesNotThrow(() -> saveDataCommand.execute()); // Перевіряємо, що виклик не викликає виключення
        verify(taxPayer).saveToFile("null"); // Перевіряємо, що метод saveToFile був викликаний з "null"

        System.setIn(System.in); // Відновлюємо оригінальний ввід
    }
}
