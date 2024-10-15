package commands;

import main.Menu; // Імпорт меню, якщо потрібно

public class ExitCommand implements Command {
    private final Menu menu; // Додано поле для меню

    public ExitCommand(Menu menu) {
        this.menu = menu; // Ініціалізація меню через конструктор
    }

    @Override
    public void execute() {
        menu.exit(); // Виклик методу виходу з меню
    }
}
