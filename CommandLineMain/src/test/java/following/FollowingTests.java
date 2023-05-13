package following;

import database.boundries.LoginDataBaseApi;
import follow.FollowInteractor;
import login.entities.AdminUser;
import login.entities.NormalUser;
import logintests.MotherLogin;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

public class FollowingTests {


    private NormalUser normalUser;
    private AdminUser adminUser;
    FollowInteractor followInteractor;
    private LoginDataBaseApi loginDataBaseApi;
    private String response;


    @BeforeEach
    public void setUp(){
        loginDataBaseApi = MotherLogin.getFileDataBaseWithOneUserWithNameAliAndQXYZEEasAdmin();
        followInteractor = new FollowInteractor(loginDataBaseApi);
    }
    @AfterEach
    public void afterEach(){
        if(this.adminUser != null) {
            this.adminUser.getFollowingRequests().clear();
            this.adminUser.getFollowers().clear();
        }
    }

    @Test
    void addFolowers_adminNameIsWrong(){
        response = followInteractor.addFollowerRequest("ali","afqa4e");
        Assertions.assertEquals(response,"no admin exists with this name");
    }

    @Test
    void addFolowers_NormalNameIsWrong(){
        response = followInteractor.addFollowerRequest("afwwf","QXYZEE");
        Assertions.assertEquals(response,"no normal user exists with this name");
    }

    @Test
    void addFollower(){
        String response = followInteractor.addFollowerRequest("ali","QXYZEE");
        Assertions.assertEquals(response,"ali sends request to be folowers of QXYZEE");
        this.normalUser = this.loginDataBaseApi.getNormalUserByName("ali");
        this.adminUser = this.loginDataBaseApi.getAdminUserByName("QXYZEE");
        Assertions.assertTrue(this.adminUser.getFollowingRequests().contains(this.normalUser.getName()));
    }

    @Test
    void acceptFollower_wrongAdminName(){
        followInteractor.addFollowerRequest("ali","QXYZEE");
        response = followInteractor.responseFollowingRequest("","ali",false);
        Assertions.assertEquals("no admin exists with this name",response);

    }

    @Test
    void acceptFollower_wrongNormalName(){
        followInteractor.addFollowerRequest("ali","QXYZEE");
        response = followInteractor.responseFollowingRequest("QXYZEE","",false);
        Assertions.assertEquals("no normal user exists with this name",response);
    }

    @Test
    void acceptFollower_NormalUserDoesntExistsInRequests(){
        this.loginDataBaseApi.addNormalUser(new NormalUser("ali2","pass",new ArrayList<>()));
        followInteractor.addFollowerRequest("ali","QXYZEE");
        response = followInteractor.responseFollowingRequest("QXYZEE","ali2",false);
        Assertions.assertEquals("no normal user exists with this name in QXYZEE requests",response);
        this.loginDataBaseApi.deleteNormalUserByName("ali2");
    }

    @Test
    void acceptFollower_acceptRequest(){
        followInteractor.addFollowerRequest("ali","QXYZEE");
        response = followInteractor.responseFollowingRequest("QXYZEE","ali",true);
        Assertions.assertEquals("QXYZEE accept ali and ali added to QXYZEE followers",response);
        adminUser = this.loginDataBaseApi.getAdminUserByName("QXYZEE");
        normalUser = this.loginDataBaseApi.getNormalUserByName("ali");
        Assertions.assertTrue(adminUser.getFollowers().contains("ali"));
        Assertions.assertTrue(this.normalUser.getFollowing().contains("QXYZEE"));
        Assertions.assertEquals(normalUser.getResponses().get("QXYZEE"),"Accepted");
    }

    @Test
    void acceptFollower_rejectRequest(){
        followInteractor.addFollowerRequest("ali","QXYZEE");
        response = followInteractor.responseFollowingRequest("QXYZEE","ali",false);
        Assertions.assertEquals("QXYZEE reject ali :)",response);
        adminUser = this.loginDataBaseApi.getAdminUserByName("QXYZEE");
        normalUser = this.loginDataBaseApi.getNormalUserByName("ali");
        Assertions.assertFalse(adminUser.getFollowers().contains("ali"));
        Assertions.assertEquals(normalUser.getResponses().get("QXYZEE"),"Rejected");
    }

    @Test
    void followingResponseSeenAndRemove_wrongAdminAndNormalName(){
        followInteractor.addFollowerRequest("ali","QXYZEE");
        followInteractor.responseFollowingRequest("QXYZEE","ali",false);
        response = followInteractor.responseSeenedAndRemove("ali","");
        Assertions.assertEquals("no admin exists with this name",response);
        response = followInteractor.responseSeenedAndRemove("","QXYZEE");
        Assertions.assertEquals("no normal user exists with this name",response);
    }
    @Test
    void followingResponseSeenAndRemove_wrongAdminWhichIsNotExistsInResponses(){
        this.loginDataBaseApi.addAdminUser(new AdminUser("BBEX","pass",new ArrayList<>()));
        followInteractor.addFollowerRequest("ali","QXYZEE");
        followInteractor.responseFollowingRequest("QXYZEE","ali",false);
        response = followInteractor.responseSeenedAndRemove("ali","BBEX");
        Assertions.assertEquals("BBEX has no response for ali",response);
        this.loginDataBaseApi.deleteNormalUserByName("BBEX");
    }

    @Test
    void followingResponseSeenAndRemove_removeResponseSuccessfully(){
        followInteractor.addFollowerRequest("ali","QXYZEE");
        followInteractor.responseFollowingRequest("QXYZEE","ali",false);
        response = followInteractor.responseSeenedAndRemove("ali","QXYZEE");
        this.normalUser = this.loginDataBaseApi.getNormalUserByName("ali");
        Assertions.assertFalse(this.normalUser.getResponses().containsKey("QXYZEE"));
        Assertions.assertEquals("response has seen and removed",response);
    }







}
