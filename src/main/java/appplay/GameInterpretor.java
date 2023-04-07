package appplay;

import Login.LoginController;
import Login.NormalUser;

import java.util.ArrayList;

public class GameInterpretor implements Interpreter {
    LoginController loginController;

    public GameInterpretor(LoginController loginController) {
        this.loginController = loginController;
    }

    public Response interpret(Command command) {
        String[] words = command.getCommand().split("\\s+");
        Response response = null;// split by whitespace
        switch (words[0]) {
            case "login" -> response = login(words);
            case "adduser" -> response = addUser(words);
        }
        return response;
    }

    private Response addUser(String[] words) {
        switch (words[1]) {
            case "normal" -> {
                return new Response(loginController.tryAddingNormalUser(words[2], words[3],new ArrayList<>()));
            }
            case "admin" -> {
                return new Response(loginController.tryAddingAdminUser(words[2], words[3],new ArrayList<>()));
            }
        }
        return null;
    }

    private Response login(String[] words) {
        switch (words[1]) {
            case "normal" -> {
                return new Response(loginController.loginUser(words[3], words[4]));
            }
        }
        return null;
    }
}
