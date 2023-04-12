package centralenginetest;

import appplay.AppConsole;
import appplay.Command;
import appplay.Response;

import java.util.ArrayList;
import java.util.List;

public class TestConsole implements AppConsole {
    List<String> commandstrings;
    List<Command> commands;
    int counter;

    public TestConsole(List<String> strings) {
        commands = new ArrayList<>();
        this.commandstrings = strings;
        for (String s:
             strings) {
            commands.add(new Command(s));
        }
        this.counter = 0;
    }

    @Override
    public Command getCommandFromUser() {
        return commands.get(counter ++);
    }

    @Override
    public void showResponse(Response response) {
        System.out.println(response.getResponse());
    }
    public void addCommand(List<String> strings){
        for (String s :
        strings){
            this.commands.add(new Command(s));
        }
    }
    public void addCommand(Command command){
        this.commands.add(command);
    }
    public void clearCommands(){
        this.commandstrings.clear();
    }
}
