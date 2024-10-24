package main;

import models.TaxPayer;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

class MainTest {
    @Test
    void testMain() {
        // Створення мок-об'єкта TaxPayer
        TaxPayer mockTaxPayer = mock(TaxPayer.class);

        // Створення меню з мок-об'єктом TaxPayer
        Menu mockMenu = mock(Menu.class);

        // Виклик конструктора Main
        Main main = new Main();

        // Встановлення mockMenu в Main через рефлексію
        try {
            java.lang.reflect.Field field = Main.class.getDeclaredField("menu");
            field.setAccessible(true);
            field.set(main, mockMenu);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }

        // Виклик методу display
        mockMenu.display();

        // Перевірка, чи був викликаний метод display() у меню
        verify(mockMenu).display();
    }
}
