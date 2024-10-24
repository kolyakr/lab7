package main;

import models.TaxPayer;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MainTest {

    @Test
    void testCreateMenu() {
        Menu menu = Main.createMenu(); // Виклик методу створення меню
        assertNotNull(menu); // Перевіряємо, що об'єкт меню не є null
        assertTrue(menu instanceof Menu); // Перевіряємо, що це дійсно об'єкт Menu
    }
}
