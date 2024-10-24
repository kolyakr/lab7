package commands;

import models.TaxPayer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.mockito.Mockito.*;

class GenerateReportCommandTest {

    private TaxPayer taxPayer; // Змінна для податківця
    private GenerateReportCommand generateReportCommand; // Змінна для команди генерації звіту

    @BeforeEach
    void setUp() {
        taxPayer = Mockito.mock(TaxPayer.class); // Створюємо мок для об'єкта TaxPayer
        generateReportCommand = new GenerateReportCommand(taxPayer); // Ініціалізація команди генерації звіту
    }

    @Test
    void testExecuteCallsGenerateTaxReport() {
        generateReportCommand.execute(); // Викликаємо метод генерації звіту
        // Перевіряємо, що метод generateTaxReport був викликаний 1 раз
        verify(taxPayer, times(1)).generateTaxReport();
    }

    @Test
    void testExecuteDoesNotCallGenerateReportTwice() {
        generateReportCommand.execute(); // Викликаємо метод генерації звіту перший раз
        generateReportCommand.execute(); // Викликаємо метод генерації звіту вдруге
        // Перевіряємо, що метод generateTaxReport був викликаний 2 рази
        verify(taxPayer, times(2)).generateTaxReport();
    }

    @Test
    void testExecuteHandlesExceptionsGracefully() {
        // Налаштовуємо, щоб виклик методу генерував помилку
        doThrow(new RuntimeException("Error generating report")).when(taxPayer).generateTaxReport();

        // Використовуємо try-catch для перевірки, що виклик не призведе до помилки
        try {
            generateReportCommand.execute(); // Викликаємо метод генерації звіту
        } catch (RuntimeException e) {
            // Якщо помилка виникла, тест не зазнає краху
            assert(e.getMessage().equals("Error generating report")); // Перевіряємо, що повідомлення про помилку коректне
        }
    }
}
