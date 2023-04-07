package centralenginetest;

import appplay.AppConsole;
import appplay.CentralEngine;
import appplay.Command;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


public class CentralEngineTest {

    @Mock
    AppConsole appConsole;
    @BeforeEach
    public void setUp(){
        this.appConsole = mock(AppConsole.class);

    }

    @Test
    public void testExitTest(){
        when(this.appConsole.getCommandFromUser()).thenReturn(new Command("exit"));
        CentralEngine centralEngine = new CentralEngine(this.appConsole);
        centralEngine.play();
        Assertions.assertTrue(centralEngine.isAppFinished());
    }

    @Test
    public void testInputLoginTest(){

    }






}
