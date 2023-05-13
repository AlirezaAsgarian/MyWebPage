package CRUDpost;


import database.boundries.DataBaseApi;
import logintests.MotherLogin;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import post.boundries.MockPostPresenter;
import post.boundries.TestText;
import post.entity.Post;
import post.interactors.PostInteractor;
import post.interactors.PostUsecase;
import util.DateGetter;
import util.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class UpdatePostTest extends PostBase {

    private PostUsecase postUsecase;
    private Post post;
    private DataBaseApi dataBaseApi;
    private String currentDate;
    private Pair<Post, String> postStringPair;
    private String response;

    @BeforeEach
    void setUp() {
        dataBaseApi = MotherLogin.getFileDataBaseWithOneUserWithNameAliAndQXYZEEasAdmin();
        postUsecase = new PostInteractor(new MockPostPresenter(), dataBaseApi);
        currentDate = DateGetter.getCurrentDate();
        post = new Post(new ArrayList<>(List.of(TestText.builder().rank(1).build(),TestText.builder().rank(2).build())),new ArrayList<>(),"QXYZEE", UUID.randomUUID().toString(),"first Test Title");
        postStringPair = postUsecase.addPost(post.getComments(),post.getOwnerName(),post.getComponents(),post.getTitle());
        post = postStringPair.getKey();
    }

    @Test
    void updatePostTest(){
        postStringPair = postUsecase.updatePost(2,TestText.builder().rank(2).type("changed type").build(),post.getId(),post.getOwnerName());
        updatePostAndResponse();
        Assertions.assertEquals("changed type",post.getComponents().get(1).getType());
        assertResponseMessage("TestText2 of post with title " + post.getTitle() + " changed successfully", response);
    }

    private void updatePostAndResponse() {
        response = postStringPair.getValue();
        post = postStringPair.getKey();
    }

    @Test
    void testDateCreateAndUpdatePost() throws InterruptedException {
        postStringPair = postUsecase.addPost(post.getComments(),post.getOwnerName(),post.getComponents(),post.getTitle());
        updatePostAndResponse();
        updateCurrentDate();
        Assertions.assertTrue(currentDate.compareTo(post.getDate()) > 0);
        postStringPair = postUsecase.updatePost(2,TestText.builder().rank(2).type("changed type").build(),post.getId(),post.getOwnerName());
        updatePostAndResponse();
        Assertions.assertTrue(currentDate.compareTo(post.getDate()) < 0);
    }

    private void updateCurrentDate() throws InterruptedException {
        Thread.currentThread().sleep(10);
        currentDate = DateGetter.getCurrentDate();
    }


}
