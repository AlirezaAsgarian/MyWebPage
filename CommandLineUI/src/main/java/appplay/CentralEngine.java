package appplay;

import lombok.Setter;

public class CentralEngine {

    AppConsole appConsole;
    Interpreter interpreter;
    @Setter
    boolean exit;

    public CentralEngine(AppConsole appConsole,Interpreter interpreter) {
        this.appConsole = appConsole;
        this.interpreter = interpreter;
        this.exit = false;
    }

    public void play() {
        while (!exit){
            Command commandFromUser = appConsole.getCommandFromUser();
            Response response = execute(commandFromUser);
           this.appConsole.showResponse(response);
        }
    }

    public Response execute(Command command){
        if(command.getCommand().equals("exit")){
            this.exit = true;
            return new Response("app finished");
        }else {
           return interpreter.interpret(command);
        }
    }

    public boolean isAppFinished() {
        return this.exit;
    }

}
