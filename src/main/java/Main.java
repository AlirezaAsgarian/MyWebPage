import DataBase.FileDataBase;
import Login.LoginController;
import appplay.CentralEngine;
import appplay.CommandInterpetorNormalFactory;
import appplay.CommandInterpretor;
import appplay.TerminalConsole;
import post.CommentController;
import post.MockPostPresenter;
import post.PostController;

import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
        FileDataBase fd = new FileDataBase(new ArrayList<>());
        CentralEngine centralEngine = new CentralEngine(new TerminalConsole(),
                new CommandInterpretor(new CommandInterpetorNormalFactory(fd,new MockPostPresenter())));
        centralEngine.play();
    }
}
