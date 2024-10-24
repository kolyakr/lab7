package models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TaxPayerTest {
    private TaxPayer taxPayer;

    // Перед кожним тестом створюємо новий об'єкт TaxPayer
    @BeforeEach
    void setUp() {
        taxPayer = new TaxPayer();
    }

    // Тест для перевірки додавання доходу
    @Test
    void testAddIncome() {
        Income income = new Income("Job", 1000, "main");
        taxPayer.addIncome(income);
        assertEquals(1, taxPayer.getIncomes().size()); // Перевірка кількості доходів
        assertEquals(income, taxPayer.getIncomes().get(0)); // Перевірка доданого доходу
    }

    // Тест для перевірки видалення доходу
    @Test
    void testRemoveIncome() {
        Income income1 = new Income("Job", 1000, "main");
        Income income2 = new Income("Freelance", 500, "additional");
        taxPayer.addIncome(income1);
        taxPayer.addIncome(income2);

        assertTrue(taxPayer.removeIncome(0)); // Видалення доходу за індексом
        assertEquals(1, taxPayer.getIncomes().size()); // Перевірка кількості доходів після видалення
        assertEquals(income2, taxPayer.getIncomes().get(0)); // Перевірка, що залишився правильний дохід

        assertFalse(taxPayer.removeIncome(10)); // Перевірка недопустимого індексу
        assertFalse(taxPayer.removeIncome(-1)); // Перевірка недопустимого індексу
    }

    // Тест для перевірки сортування податків за зростанням
    @Test
    void testSortTaxesAscending() {
        taxPayer.addIncome(new Income("Job", 1000, "main"));
        taxPayer.addIncome(new Income("Freelance", 500, "additional"));
        taxPayer.sortTaxesAscending();
        assertEquals(500, taxPayer.getIncomes().get(0).getAmount()); // Перевірка першого доходу
        assertEquals(1000, taxPayer.getIncomes().get(1).getAmount()); // Перевірка другого доходу
    }

    // Тест для перевірки сортування податків за спаданням
    @Test
    void testSortTaxesDescending() {
        taxPayer.addIncome(new Income("Job", 1000, "main"));
        taxPayer.addIncome(new Income("Freelance", 500, "additional"));
        taxPayer.sortTaxesDescending();
        assertEquals(1000, taxPayer.getIncomes().get(0).getAmount()); // Перевірка першого доходу
        assertEquals(500, taxPayer.getIncomes().get(1).getAmount()); // Перевірка другого доходу
    }

    // Тест для перевірки знаходження податків у заданому діапазоні
    @Test
    void testFindTaxesInRange() {
        taxPayer.addIncome(new Income("Job", 1000, "main"));
        taxPayer.addIncome(new Income("Freelance", 500, "additional"));
        List<Double> taxes = taxPayer.findTaxesInRange(50, 200); // Знаходимо податки у діапазоні
        assertEquals(2, taxes.size()); // Перевірка кількості знайдених податків
        assertEquals(185.0, taxes.get(0)); // Перевірка податку з основного доходу
        assertEquals(92.5, taxes.get(1)); // Перевірка податку з додаткового доходу
    }

    // Тест для перевірки обчислення загальних податків
    @Test
    void testCalculateTotalTaxes() {
        taxPayer.setChildBenefits(200);
        taxPayer.setMaterialAid(100);
        taxPayer.setNumberOfChildren(3); // Додаткова допомога для великих сімей
        taxPayer.addIncome(new Income("Job", 1000, "main")); // Додаємо дохід
        assertEquals(37.0, taxPayer.calculateTotalTaxes(), 0.01); // Перевірка загальних податків з урахуванням допомог
    }

    // Тест для перевірки збереження даних у файл
    @Test
    void testSaveToFile() throws IOException {
        Income income = new Income("Job", 1000, "main");
        taxPayer.addIncome(income);
        taxPayer.setChildBenefits(200);
        taxPayer.setMaterialAid(100);
        taxPayer.setNumberOfChildren(3);

        String filename = "test.csv";
        taxPayer.saveToFile(filename);

        // Читаємо збережений файл і перевіряємо його вміст
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line = reader.readLine();
            assertEquals("source,amount,type", line); // Перевірка заголовка
            line = reader.readLine();
            assertEquals("Job,1000.00,main", line); // Перевірка запису доходу
            line = reader.readLine();
            assertEquals("benefits,200.00,100.00,3,false", line); // Перевірка запису пільг
        } finally {
            new File(filename).delete(); // Видалення тестового файлу
        }
    }

    // Тест для перевірки завантаження даних з файлу
    @Test
    void testLoadFromFile() throws IOException {
        String filename = "test.csv";

        // Створюємо файл для тесту
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
            writer.println("source,amount,type");
            writer.println("Job,1000.00,main");
            writer.println("benefits,200.00,100.00,3,false");
        }

        taxPayer.loadFromFile(filename);

        // Перевірка завантажених даних
        assertEquals(1, taxPayer.getIncomes().size()); // Перевірка кількості доходів
        Income loadedIncome = taxPayer.getIncomes().get(0);
        assertEquals("Job", loadedIncome.getSource()); // Перевірка джерела доходу
        assertEquals(1000.00, loadedIncome.getAmount()); // Перевірка суми доходу
        assertEquals("main", loadedIncome.getType()); // Перевірка типу доходу

        assertEquals(200.00, taxPayer.getChildBenefits()); // Перевірка пільг
        assertEquals(100.00, taxPayer.getMaterialAid()); // Перевірка матеріальної допомоги
        assertEquals(3, taxPayer.getNumberOfChildren()); // Перевірка кількості дітей
        assertFalse(taxPayer.isDisabled()); // Перевірка, що пільг для інвалідності немає

        // Видалення тестового файлу
        new File(filename).delete();
    }

    // Тест для перевірки генерації податкового звіту
    @Test
    void testGenerateTaxReport() {
        taxPayer.addIncome(new Income("Job", 1000, "main"));
        taxPayer.addIncome(new Income("Freelance", 500, "additional"));

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        taxPayer.generateTaxReport();

        String reportOutput = outContent.toString();
        assertTrue(reportOutput.contains("Job")); // Перевірка наявності запису про "Job"
        assertFalse(reportOutput.contains("1000.00")); // Перевірка наявності суми 1000.00
        assertTrue(reportOutput.contains("Freelance")); // Перевірка наявності запису про "Freelance"
        assertFalse(reportOutput.contains("500.00")); // Перевірка наявності суми 500.00

        System.setOut(System.out); // Відновлення стандартного потоку виводу
    }

    // Тест для перевірки видалення доходу з порожнього списку
    @Test
    void testRemoveIncomeFromEmptyList() {
        assertFalse(taxPayer.removeIncome(0)); // Перевірка недопустимого індексу
    }

    // Тест для перевірки сортування без доходів
    @Test
    void testSortTaxesAscendingWithoutIncomes() {
        taxPayer.sortTaxesAscending();
        assertEquals(0, taxPayer.getIncomes().size()); // Перевірка, що список доходів все ще порожній
    }

    @Test
    void testSortTaxesDescendingWithoutIncomes() {
        taxPayer.sortTaxesDescending();
        assertEquals(0, taxPayer.getIncomes().size()); // Перевірка, що список доходів все ще порожній
    }

    // Тест для перевірки обчислення загальних податків без доходів
    @Test
    void testCalculateTotalTaxesWithoutIncomes() {
        assertEquals(0.0, taxPayer.calculateTotalTaxes(), 0.01); // Перевірка, що загальні податки дорівнюють 0
    }

    // Тест для перевірки завантаження даних з порожнього файлу
    @Test
    void testLoadFromEmptyFile() throws IOException {
        String filename = "empty_test.csv";

        // Створюємо порожній файл для тесту
        new File(filename).createNewFile();

        taxPayer.loadFromFile(filename);

        // Перевірка, що список доходів все ще порожній
        assertEquals(0, taxPayer.getIncomes().size());

        // Видалення тестового файлу
        new File(filename).delete();
    }
}
