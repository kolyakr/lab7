package commands;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Scanner;

public class ExitCommand implements Command {
    // Логер для дій виходу
    private static final Logger logger = LogManager.getLogger(ExitCommand.class);
    private Scanner scanner;
    private Runnable exitAction;
    private boolean shouldCloseScanner;

    public ExitCommand(Scanner scanner, boolean shouldCloseScanner, Runnable exitAction) {
        this.scanner = scanner;
        this.shouldCloseScanner = shouldCloseScanner;
        this.exitAction = exitAction != null ? exitAction : () -> System.exit(0);
    }

    @Override
    public void execute() {
        logger.info("Exiting the application..."); // Логування дій виходу
        System.out.println("Exiting...");
        if (shouldCloseScanner) {
            scanner.close();
            logger.info("Scanner closed."); // Логування закриття сканера
        }
        exitAction.run(); // Виклик замість System.exit(0)
    }
}
