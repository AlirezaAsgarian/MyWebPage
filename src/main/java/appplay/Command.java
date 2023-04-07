package appplay;


import lombok.Getter;

public class Command {
    @Getter
    String command;
    public Command(String command) {
        this.command = command;
    }
}