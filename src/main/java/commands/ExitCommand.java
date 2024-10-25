package commands;

import java.util.Scanner;

public class ExitCommand implements Command {
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
        System.out.println("Exiting...");
        if (shouldCloseScanner) {
            scanner.close();
        }
        exitAction.run();
    }
}
