package addPost;

import login.entities.AdminUser;
import database.boundries.DataBaseApi;
import login.entities.NormalUser;
import post.boundries.Component;
import post.boundries.PostPresenter;
import post.boundries.TextBoxComponent;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import post.entity.Comment;
import post.entity.Post;
import post.interactors.CommentController;
import util.Pair;

import java.util.ArrayList;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class AddCommentTest {
    CommentController commentController;
    @Mock
    TextBoxComponent textBox;
    @Mock
    NormalUser normalUser;
    @Mock
    Post post;
    @Mock
    AdminUser adminUser;
    @Mock
    DataBaseApi dataBaseApi;
    @Mock
    PostPresenter postPresenter;
    @BeforeEach
    public void setup() {
        this.textBox = mock(TextBoxComponent.class);
        this.normalUser = mock(NormalUser.class);
        this.post = mock(Post.class);
        this.adminUser = mock(AdminUser.class);
        this.dataBaseApi = mock(DataBaseApi.class);
        this.postPresenter = mock(PostPresenter.class);
        this.commentController = new CommentController(this.postPresenter,this.dataBaseApi);
    }
    @Test
     void canAddingTextBoxToComment(){
        Comment comment = new Comment(this.normalUser,this.post);
        comment.setTextBox(this.textBox);
        Assertions.assertEquals(comment.getTextBoxComponent(),this.textBox);
    }
    @Test
     void canCreateComment() {
        NormalUser normalUser1 = new NormalUser("ali","password",new ArrayList<>());
        Post post1 = new Post(new ArrayList<Component>(),new ArrayList<Comment>(),this.adminUser,"");
        Pair<Comment,String> commentXmessage = commentController.addComment(this.textBox,post1,normalUser1);
        Comment comment = commentXmessage.getKey();
        String message = commentXmessage.getValue();
        Assertions.assertEquals(normalUser1.getComments().get(0).getOwnerName(),normalUser1.getName());
        Assertions.assertEquals(normalUser1.getComments().get(0).getCommentsPostId(),post1.getId());
        Assertions.assertEquals(post1.getComments().get(0),comment);
    }
    @Test
    void addCommentByNameOfNormalUser(){
        NormalUser normalUser1 = new NormalUser("ali","password",new ArrayList<>());
        Post post1 = new Post(new ArrayList<Component>(),new ArrayList<Comment>(),this.adminUser,"");
        when(this.dataBaseApi.getNormalUserByName(normalUser1.getName())).thenReturn(normalUser1);
        when(this.dataBaseApi.getAdminUserByName(this.adminUser.getName())).thenReturn(this.adminUser);
        when(this.adminUser.getPostById(post1.getId())).thenReturn(this.post);
        Pair<Comment,String> commentXmessage = commentController.addCommentWithUserName(post1.getId(),
                this.adminUser.getName(),this.textBox,normalUser1.getName());
        String message = commentXmessage.getValue();
        Assertions.assertEquals("comment created successfully comment added succssessfully to post with id " +
                post.getId(),message);
    }



}
