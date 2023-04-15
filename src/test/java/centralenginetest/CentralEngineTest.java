package centralenginetest;

import Login.AdminUser;
import Login.DataBaseApi;
import Login.NormalUser;
import appplay.*;
import logintests.MotherLogin;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import post.Component;
import post.PostPresenter;
import post.TextBoxComponent;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.mock;


public class CentralEngineTest {

    AppConsole appConsole;
    Interpreter interpretor;
    private ByteArrayOutputStream baos;
    private CentralEngine centralEngine;
    @Mock
    TextBoxComponent text;

    @BeforeEach
    public void setUp() {
        this.appConsole = new TestConsole(new ArrayList<>());
        this.text = mock(TextBoxComponent.class);
        PostPresenter postPresenter = new TestPostPresenter();
        DataBaseApi dataBaseApi = MotherLogin.getFileDataBaseWithOneUserWithNameAli();
        this.interpretor =
                new CommandInterpretor(new CommandInterpetorNormalFactory(dataBaseApi, postPresenter));
        baos = setByteArrayOutputStream();
        centralEngine = new CentralEngine(this.appConsole, this.interpretor);
    }

    @Test
    public void testExitTest() {
        addStringCommandToAppConsole("exit");
        CentralEngine centralEngine = new CentralEngine(this.appConsole, null); // todo mock interpretor
        centralEngine.play();
        Assertions.assertTrue(centralEngine.isAppFinished());
    }

    @Test
    void testGetOutput() {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(baos);
// IMPORTANT: Save the old System.out!
        PrintStream old = System.out;
// Tell Java to use your special stream
        System.setOut(ps);
// Print some output: goes to your special stream
        System.out.println("Foofoofoo!");
        System.setOut(old);
// Show what happened
        System.out.println("Here: " + baos.toString());
    }

    @Test
    void loginNormalUserTest() {
        addStringCommandToAppConsole("login normal user ali AliPassword", "exit");
        this.centralEngine.play();
        Assertions.assertEquals("ali logged in successfully\napp finished\n", this.baos.toString());
    }

    @Test
    void addNormalUserTest() {
        NormalUser user = new NormalUser("ali2", "AliPassword", new ArrayList<>());
        addStringCommandToAppConsole("login normal user ali2 AliPassword");
        addStringCommandToAppConsole("adduser normal " + user.getName() + "  " + user.getPassword());
        addStringCommandToAppConsole("login normal user ali2 AliPassword", "exit");
        this.centralEngine.play();
        Assertions.assertEquals("no user exists with this name\n" +
                        user.getName() + " added successfully\n" +
                        "ali2 logged in successfully\n" +
                        "app finished\n",
                this.baos.toString());
    }

    @Test
    void addAdminAndLogin() {
        AdminUser adminUser = new AdminUser("BBEX", "AliPassword", new ArrayList<>()); // allowed names : ["QXYZEE","BBEX","BWXA"]
        addStringCommandToAppConsole("login admin user BBEX AliPassword");
        addStringCommandToAppConsole("adduser admin BBEX AliPassword");
        addStringCommandToAppConsole("login admin user BBEX AliPassword", "exit");
        this.centralEngine.play();
        Assertions.assertEquals("no admin user exists with this name\n" +
                        adminUser.getName() + " added as admin successfully\n" +
                        "BBEX logged in as admin successfully\n" +
                        "app finished\n",
                this.baos.toString());
    }

    @Test
    void addPostToAdmin() {
        AdminUser adminUser = new AdminUser("BBEX", "AliPassword", new ArrayList<>()); // allowed names : ["QXYZEE","BBEX","BWXA"]
        loginAdminUser();
        addCommandToAppConsole("addpost BBEX", this.text);
        addStringCommandToAppConsole("exit");
        this.centralEngine.play();
        String[] results = baos.toString().split("\\r?\\n");
        String postId = results[2].split("\\s+")[3];
        Assertions.assertEquals(adminUser.getName() + " added as admin successfully\n" +
                        "BBEX logged in as admin successfully\n" +
                        "post with id " + postId + " created and added successfully\n" +
                        "app finished\n",
                this.baos.toString());
    }

    @Test
    void showPost() {
        AdminUser adminUser = new AdminUser("BBEX", "AliPassword", new ArrayList<>()); // allowed names : ["QXYZEE","BBEX","BWXA"]
        addPost();
        this.centralEngine.play();
        String[] results = baos.toString().split("\\r?\\n");
        String postId = results[2].split("\\s+")[3];
        this.centralEngine.setExit(false); //for restart in purpose of get PostId
        addStringCommandToAppConsole("showpost " + postId + " " + adminUser.getName(), "exit");
        this.centralEngine.play();
        Assertions.assertEquals(adminUser.getName() + " added as admin successfully\n" +
                        "BBEX logged in as admin successfully\n" +
                        "post with id " + postId + " created and added successfully\n" +
                        "app finished\n" +
                        "text component\n" +
                        "post with id " + postId + " is showing successfully\n" +
                        "app finished\n",
                this.baos.toString());
    }

    @Test
    void hidePostWithItsComments() {
        AdminUser adminUser = new AdminUser("BBEX", "AliPassword", new ArrayList<>()); // allowed names : ["QXYZEE","BBEX","BWXA"]
        String postId = showPost(adminUser);
        addStringCommandToAppConsole("showpost " + postId + " " + adminUser.getName());
        addStringCommandToAppConsole("hidepost " + postId + " " + adminUser.getName());
        addStringCommandToAppConsole("hidepost " + postId + " " + adminUser.getName(), "exit");
        this.centralEngine.play();
        Assertions.assertEquals(adminUser.getName() + " added as admin successfully\n" +
                        "BBEX logged in as admin successfully\n" +
                        "post with id " + postId + " created and added successfully\n" +
                        "app finished\n" +
                        "text component\n" +
                        "post with id " + postId + " is showing successfully\n" +
                        "app finished\n" +
                        "post is already showing\n" +
                        "post with id " + postId + " hided successfully\n" +
                        "post has already hided\n" +
                        "app finished\n",
                this.baos.toString());
    }

    @Test
    void showCommentTest() {
        String normalUsername = "ali";
        AdminUser adminUser = new AdminUser("BBEX", "AliPassword", new ArrayList<>()); // allowed names : ["QXYZEE","BBEX","BWXA"]
        String postId = showPost(adminUser);
        addCommandToAppConsole("addcomment " + postId + " " + adminUser.getName() + " ali", this.text);
        addStringCommandToAppConsole("showcomments " + postId + " " + adminUser.getName());
        addStringCommandToAppConsole("showcomments " + postId + " " + adminUser.getName());
        addStringCommandToAppConsole("hidecomments " + postId + " " + adminUser.getName());
        addStringCommandToAppConsole("hidecomments " + postId + " " + adminUser.getName(), "exit");
        this.centralEngine.play();
        Assertions.assertEquals(adminUser.getName() + " added as admin successfully\n" +
                        "BBEX logged in as admin successfully\n" +
                        "post with id " + postId + " created and added successfully\n" +
                        "app finished\n" +
                        "text component\n" +
                        "post with id " + postId + " is showing successfully\n" +
                        "app finished\n" +
                        "comment created successfully comment added succssessfully to post with id " + postId + "\n" +
                        "commment text component : ali\n" +
                        "comments of post " + postId + " is showing successfully\n" +
                        "comments has already shown\n" +
                        "comments of post " + postId + " hided successfully\n" +
                        "comments has already hided\n" +
                        "app finished\n",
                this.baos.toString());

    }


    private String showPost(AdminUser adminUser) {
        addPost();
        this.centralEngine.play();
        String[] results = baos.toString().split("\\r?\\n");
        String postId = results[2].split("\\s+")[3];
        this.centralEngine.setExit(false); //for restart in purpose of get PostId
        addStringCommandToAppConsole("showpost " + postId + " " + adminUser.getName(), "exit");
        this.centralEngine.play();
        this.centralEngine.setExit(false);
        return postId;
    }

    private void addPost() {
        loginAdminUser();
        addCommandToAppConsole("addpost BBEX", this.text);
        addStringCommandToAppConsole("exit");
    }

    private void loginAdminUser() {
        addStringCommandToAppConsole("adduser admin BBEX AliPassword");
        addStringCommandToAppConsole("login admin user BBEX AliPassword");
    }

    private void addStringCommandToAppConsole(String... command) {
        TestConsole testConsole = (TestConsole) this.appConsole;
        testConsole.addCommand(List.of(command));
    }

    private void addCommandToAppConsole(String command, Component... component) {
        TestConsole testConsole = (TestConsole) this.appConsole;
        testConsole.addCommand(new Command(command, component));
    }

    private ByteArrayOutputStream setByteArrayOutputStream() {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(baos);
        System.setOut(ps);
        return baos;
    }

    @AfterEach
    void afterSetUp() {
        TestConsole terminalConsole = (TestConsole) this.appConsole;
        terminalConsole.clearCommands();
    }


}
