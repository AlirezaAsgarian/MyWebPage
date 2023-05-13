package getposts;

import CRUDpost.PostBase;
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
import util.Pair;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class SearchPostTest extends PostBase {

    private PostUsecase postUsecase;
    private Post post1;
    private DataBaseApi dataBaseApi;
    private Post post2;

    @BeforeEach
    void setUp() {
        dataBaseApi = MotherLogin.getFileDataBaseWithOneUserWithNameAliAndQXYZEEasAdmin();
        postUsecase = new PostInteractor(new MockPostPresenter(), dataBaseApi);
        post1 = new Post(new ArrayList<>(List.of(TestText.builder().rank(1).build(),TestText.builder().rank(2).build())),new ArrayList<>(),"QXYZEE", UUID.randomUUID().toString(),"first Test Title");
        post2 = new Post(new ArrayList<>(),new ArrayList<>(),"QXYZEE", UUID.randomUUID().toString(),"second Test Title");
        postUsecase.addPost(post1.getComments(),post1.getOwnerName(),post1.getComponents(),post1.getTitle());
        postUsecase.addPost(post2.getComments(), post2.getOwnerName(), post2.getComponents(), post2.getTitle());
    }




    @Test
    void getPostByTitle(){
        String searchTitle = "first";
        Pair<List<Post>,String> resultQuery = postUsecase.getPostsBySearchedTitle(searchTitle);
        List<Post> resultPosts = resultQuery.getKey();
        Assertions.assertTrue(resultPosts.stream().map(e -> e.getTitle()).toList().contains(post1.getTitle()));
        Assertions.assertFalse(resultPosts.stream().map(e -> e.getTitle()).toList().contains(post2.getTitle()));
        Assertions.assertEquals("1 posts found successfully",resultQuery.getValue());
    }

    @Test
    void testGetPostsByDates() throws InterruptedException {
        ArrayList<Post> posts = new ArrayList<>();
        initializePosts(posts);
        Collections.shuffle(posts);
        addPosts(posts);
        Pair<List<Post>,String> dateSortedPostsPair = postUsecase.getPostByLastDates(5);
        List<Post> dateSortedPosts = dateSortedPostsPair.getKey();
        assertPosts(posts, dateSortedPosts);
        String responseMessage = dateSortedPostsPair.getValue();
        assertResponseMessage("5 number of posts returned successfully",responseMessage);
    }

    private  void assertPosts(ArrayList<Post> posts, List<Post> dateSortedPosts) {
        for (int i = 0; i < 5; i++) {
            assertPostTitle(posts.get(9 - i).getTitle(), dateSortedPosts.get(i).getTitle());
        }
    }



    private void addPosts(ArrayList<Post> posts) {
        for (int i = 0; i < 10; i++) {
            Post p = posts.get(i);
            postUsecase.addPost(p.getComments(),p.getOwnerName(),p.getComponents(),p.getTitle());
        }
    }

    private static void initializePosts(ArrayList<Post> posts) throws InterruptedException {
        for (int i = 0; i < 10; i++) {
            posts.add(new Post(new ArrayList<>(List.of(TestText.builder().rank(1).build(),TestText.builder().rank(2).build())),new ArrayList<>(),"QXYZEE", UUID.randomUUID().toString(),
                    i + " Test Title"));
            Thread.currentThread().sleep(2);
        }
    }


}
