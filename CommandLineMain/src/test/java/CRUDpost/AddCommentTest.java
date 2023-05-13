package CRUDpost;

import database.boundries.DataBaseApi;
import login.entities.AdminUser;
import login.entities.NormalUser;
import org.mockito.Mockito;
import post.boundries.Component;
import post.boundries.PostPresenter;
import post.boundries.TextBoxComponent;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import post.entity.Comment;
import post.entity.Post;
import post.interactors.CommentInteractor;
import util.Pair;

import java.util.ArrayList;


class AddCommentTest extends PostBase {
    CommentInteractor commentInteractor;
    @Mock
    TextBoxComponent textBox;
    @Mock
    AdminUser adminUser;
    @Mock
    DataBaseApi dataBaseApi;
    @Mock
    PostPresenter postPresenter;
    private NormalUser normalUser;
    private Post post;

    @BeforeEach
    public void setup() {
        this.textBox = Mockito.mock(TextBoxComponent.class);
        this.adminUser = Mockito.mock(AdminUser.class);
        this.dataBaseApi = Mockito.mock(DataBaseApi.class);
        this.postPresenter = Mockito.mock(PostPresenter.class);
        this.commentInteractor = new CommentInteractor(this.postPresenter,this.dataBaseApi);
        this.normalUser = new NormalUser("ali","password",new ArrayList<>());
        this.post = new Post(new ArrayList<Component>(),new ArrayList<Comment>(), this.adminUser,"");
    }
    @Test
     void canAddingTextBoxToComment(){
        Comment comment = new Comment(this.normalUser,this.post);
        comment.setTextBoxComponent(this.textBox);
        Assertions.assertEquals(comment.getTextBoxComponent(),this.textBox);
    }
    @Test
     void canCreateComment() {
        Pair<Comment,String> commentXmessage = commentInteractor.addComment(this.textBox, post, normalUser);
        Comment comment = commentXmessage.getKey();
        assertName(normalUser.getName(), normalUser.getComments().get(0).getOwnerName());
        assertPostId(normalUser.getComments().get(0).getPostId(), post.getId());
        Assertions.assertEquals(post.getComments().get(0),comment);
    }



    @Test
    void addCommentByNameOfNormalUser(){
        Mockito.when(this.dataBaseApi.getNormalUserByName(normalUser.getName())).thenReturn(normalUser);
        Mockito.when(this.dataBaseApi.getAdminUserByName(this.adminUser.getName())).thenReturn(this.adminUser);
        Mockito.when(this.adminUser.getPostById(post.getId())).thenReturn(this.post);
        Pair<Comment,String> commentXmessage = commentInteractor.addCommentWithUserName(post.getId(),
                this.adminUser.getName(),this.textBox, normalUser.getName());
        String message = commentXmessage.getValue();
        String expected = "comment created successfully comment added succssessfully to post with id " +
                post.getId();
        assertResponseMessage(expected,message);
    }


}
