package commands;

import models.TaxPayer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class SetMaterialAidCommandTest {
    private TaxPayer taxPayer; // Змінна для податківця
    private SetMaterialAidCommand setMaterialAidCommand; // Змінна для команди встановлення матеріальної допомоги

    @BeforeEach
    void setUp() {
        taxPayer = mock(TaxPayer.class); // Створюємо мок для об'єкта TaxPayer
        setMaterialAidCommand = new SetMaterialAidCommand(taxPayer); // Ініціалізація команди
    }

    @Test
    void testSetMaterialAidValidInput() {
        String input = "100\n"; // Дійсний ввід суми
        System.setIn(new ByteArrayInputStream(input.getBytes())); // Налаштовуємо стандартний ввід

        assertDoesNotThrow(() -> setMaterialAidCommand.execute()); // Перевіряємо, що виклик методу не викликає виняток
        verify(taxPayer).setMaterialAid(100); // Перевіряємо, що метод викликаний з правильною сумою

        System.setIn(System.in); // Відновлюємо стандартний ввід
    }

    @Test
    void testSetMaterialAidLargeInput() {
        String input = "1000000\n"; // Дуже велика сума
        System.setIn(new ByteArrayInputStream(input.getBytes())); // Налаштовуємо стандартний ввід

        assertDoesNotThrow(() -> setMaterialAidCommand.execute()); // Перевіряємо, що виклик методу не викликає виняток
        verify(taxPayer).setMaterialAid(1000000); // Перевіряємо, що метод викликаний з великою сумою

        System.setIn(System.in); // Відновлюємо стандартний ввід
    }

    @Test
    void testSetMaterialAidMultipleInvalidInputs() {
        String input = "abc\n-100\n\n200\n"; // Змішаний ввід з дійсними та недійсними даними
        System.setIn(new ByteArrayInputStream(input.getBytes())); // Налаштовуємо стандартний ввід

        assertDoesNotThrow(() -> setMaterialAidCommand.execute()); // Перевіряємо, що виклик методу не викликає виняток
        verify(taxPayer, never()).setMaterialAid(anyInt()); // Переконуємося, що метод не був викликаний з недійсними значеннями

        System.setIn(System.in); // Відновлюємо стандартний ввід
    }

    @Test
    void testSetMaterialAidWithMultipleWhitespaceInputs() {
        String input = "   \n   \n200\n"; // Багато пробілів перед дійсним ввідом
        System.setIn(new ByteArrayInputStream(input.getBytes())); // Налаштовуємо стандартний ввід

        assertDoesNotThrow(() -> setMaterialAidCommand.execute()); // Перевіряємо, що виклик методу не викликає виняток
        verify(taxPayer).setMaterialAid(200); // Перевіряємо, що метод викликаний з дійсним останнім значенням

        System.setIn(System.in); // Відновлюємо стандартний ввід
    }
}
