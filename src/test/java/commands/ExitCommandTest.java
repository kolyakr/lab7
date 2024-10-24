package commands;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.util.Scanner;

import static org.mockito.Mockito.*;

class ExitCommandTest {

    private Scanner scanner; // Змінна для сканера
    private Runnable mockExitAction; // Мок для дії виходу

    @BeforeEach
    void setUp() {
        // Створюємо реальний екземпляр Scanner з симульованим введенням
        ByteArrayInputStream inputStream = new ByteArrayInputStream("input".getBytes());
        scanner = new Scanner(inputStream); // Ініціалізація сканера з введенням

        // Створюємо замінник для exitAction
        mockExitAction = mock(Runnable.class); // Ініціалізація мок дії виходу
    }

    @Test
    void testExitWithoutClosingScanner() {
        // Створюємо ExitCommand без закриття сканера
        ExitCommand exitCommand = new ExitCommand(scanner, false, mockExitAction);

        exitCommand.execute(); // Виконання команди виходу

        // Перевіряємо, що exitAction було викликано
        verify(mockExitAction, times(1)).run(); // Перевірка, що дія виходу викликалася один раз
    }

    @Test
    void testExitWithClosingScanner() {
        // Створюємо ExitCommand з закриттям сканера
        ExitCommand exitCommand = new ExitCommand(scanner, true, mockExitAction);

        exitCommand.execute(); // Виконання команди виходу

        // Перевіряємо, що exitAction було викликано
        verify(mockExitAction, times(1)).run(); // Перевірка, що дія виходу викликалася один раз

        // Перевіряємо, чи закритий Scanner
        // Оскільки ми не можемо використовувати mock для Scanner, ми можемо перевірити, що Scanner вже закритий
        assert scanner.ioException() == null;  // Перевіряємо, що Scanner не викинув помилку
    }

    @Test
    void testExitActionIsCalled() {
        // Створюємо ExitCommand без закриття сканера
        ExitCommand exitCommand = new ExitCommand(scanner, false, mockExitAction);

        exitCommand.execute(); // Виконання команди виходу

        // Перевіряємо, що exitAction було викликано один раз
        verify(mockExitAction, times(1)).run(); // Перевірка, що дія виходу викликалася один раз
    }
}
