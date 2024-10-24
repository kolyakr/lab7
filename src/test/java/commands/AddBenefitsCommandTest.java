package commands;

import models.TaxPayer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AddBenefitsCommandTest {
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final InputStream originalIn = System.in;
    private final PrintStream originalOut = System.out;
    private TaxPayer mockTaxPayer;
    private AddBenefitsCommand command;

    @BeforeEach
    public void setUp() {
        // Перенаправляємо System.out для захоплення виводу
        System.setOut(new PrintStream(outContent));
        mockTaxPayer = mock(TaxPayer.class);
        command = new AddBenefitsCommand(mockTaxPayer);
    }

    @Test
    public void testExecuteWithValidInput() {
        // Емітуємо введення користувачем коректних даних для пільг
        String userInput = "1000\n500\n"; // Пільги для дітей і матеріальна допомога
        ByteArrayInputStream in = new ByteArrayInputStream(userInput.getBytes());
        System.setIn(in);

        command.execute();

        // Перевіряємо, що методи setChildBenefits і setMaterialAid викликалися з правильними значеннями
        verify(mockTaxPayer).setChildBenefits(1000);
        verify(mockTaxPayer).setMaterialAid(500);
        assertTrue(outContent.toString().contains("Benefits successfully added."), "Повідомлення про успішне додавання має відобразитися");
    }

    @Test
    public void testExecuteWithZeroInput() {
        // Емітуємо введення користувачем нульових пільг
        String userInput = "0\n0\n";
        ByteArrayInputStream in = new ByteArrayInputStream(userInput.getBytes());
        System.setIn(in);

        command.execute();

        // Перевіряємо, що методи setChildBenefits і setMaterialAid викликалися з нульовими значеннями
        verify(mockTaxPayer).setChildBenefits(0);
        verify(mockTaxPayer).setMaterialAid(0);
        assertTrue(outContent.toString().contains("Benefits successfully added."), "Повідомлення про успішне додавання має відобразитися");
    }

    @Test
    public void testExecuteWithNegativeInput() {
        // Емітуємо введення користувачем негативних значень для пільг
        String userInput = "-100\n-50\n";
        ByteArrayInputStream in = new ByteArrayInputStream(userInput.getBytes());
        System.setIn(in);

        command.execute();

        // Перевіряємо, що методи setChildBenefits і setMaterialAid викликалися з негативними значеннями
        verify(mockTaxPayer).setChildBenefits(-100);
        verify(mockTaxPayer).setMaterialAid(-50);
        assertTrue(outContent.toString().contains("Benefits successfully added."), "Повідомлення про успішне додавання має відобразитися");
    }

    @Test
    public void testExecuteMultipleValidInputs() {
        // Емітуємо кілька правильних введень користувачем
        String userInput = "2000\n1000\n"; // Перше введення
        ByteArrayInputStream in = new ByteArrayInputStream(userInput.getBytes());
        System.setIn(in);

        command.execute();

        // Перевіряємо перший набір пільг
        verify(mockTaxPayer).setChildBenefits(2000);
        verify(mockTaxPayer).setMaterialAid(1000);
        assertTrue(outContent.toString().contains("Benefits successfully added."), "Повідомлення про успішне додавання має відобразитися");

        // Скидаємо вивід для другого виклику
        outContent.reset();

        // Емітуємо друге введення
        userInput = "1500\n750\n"; // Друге введення
        in = new ByteArrayInputStream(userInput.getBytes());
        System.setIn(in);

        command.execute();

        // Перевіряємо другий набір пільг
        verify(mockTaxPayer).setChildBenefits(1500);
        verify(mockTaxPayer).setMaterialAid(750);
        assertTrue(outContent.toString().contains("Benefits successfully added."), "Повідомлення про успішне додавання має відобразитися");
    }
}
