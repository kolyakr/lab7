package com.taxsystem.main;

import com.taxsystem.commands.*;

public class CommandInvoker {
    public static void invoke(int command) {
        Command cmd;
        switch(command) {
            case 1:
                cmd = new AddIncomeCommand();
                break;
            case 2:
                cmd = new DeleteIncomeCommand();
                break;
            case 3:
                cmd = new CalculateTaxesCommand();
                break;
            case 4:
                cmd = new SortTaxesCommand();
                break;
            case 5:
                cmd = new FindTaxesCommand();
                break;
            case 6:
                cmd = new GenerateReportCommand();
                break;
            case 7:
                System.out.println("Exiting program...");
                System.exit(0);
                return;
            default:
                System.out.println("Invalid command!");
                return;
        }
        cmd.execute();
    }
}
