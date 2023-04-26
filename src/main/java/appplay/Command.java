package appplay;


import lombok.Getter;
import post.boundries.Component;

import java.util.List;

public class Command {
    @Getter
    String command;
    @Getter
    List<Component> components;
    public Command(String command) {
        this.command = command;
    }

    public Command(String command, Component... components) {
        this.command = command;
        this.components = List.of(components);
    }
}