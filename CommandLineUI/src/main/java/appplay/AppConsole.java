package appplay;

public interface AppConsole {
    Command getCommandFromUser();

    void showResponse(Response response);
}
