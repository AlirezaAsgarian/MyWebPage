package appplay;

public class CentralEngine {

    AppConsole appConsole;
    Interpreter interpreter;
    boolean exit;

    public CentralEngine(AppConsole appConsole) {
        this.appConsole = appConsole;
        this.exit = false;
    }

    public void play() {
        while (!exit){
           execute(appConsole.getCommandFromUser());
        }
    }

    public Response execute(Command command){
        if(command.getCommand().equals("exit")){
            this.exit = true;
            return new Response("game ended");
        }else {
           return interpreter.interpret(command);
        }
    }

    public boolean isAppFinished() {
        return this.exit;
    }
}
