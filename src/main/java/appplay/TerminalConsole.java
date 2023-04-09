package appplay;

import java.util.Scanner;

public class TerminalConsole implements AppConsole{

    Scanner scanner = new Scanner(System.in);
    @Override
    public Command getCommandFromUser() {
        return new Command(this.scanner.nextLine());
    }
}
