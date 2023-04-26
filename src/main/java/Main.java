import database.FileDataBase;
import appplay.CentralEngine;
import appplay.CommandInterpetorNormalFactory;
import appplay.CommandInterpretor;
import appplay.TerminalConsole;
import post.boundries.MockPostPresenter;

import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
        FileDataBase fd = new FileDataBase(new ArrayList<>(), new ArrayList<>(),new ArrayList<>());
        CentralEngine centralEngine = new CentralEngine(new TerminalConsole(),
                new CommandInterpretor(new CommandInterpetorNormalFactory(fd,new MockPostPresenter())));
        centralEngine.play();
    }
}
