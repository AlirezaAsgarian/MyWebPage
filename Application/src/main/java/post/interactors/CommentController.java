package post.interactors;

import database.boundries.DataBaseApi;
import login.entities.AdminUser;
import login.entities.NormalUser;
import post.entity.Comment;
import post.entity.Post;
import post.boundries.PostPresenter;
import post.boundries.TextBoxComponent;
import util.Pair;

public class CommentController implements CommentUsecase {
    DataBaseApi dataBaseApi;
    PostPresenter postPresenter;

    public CommentController(PostPresenter postPresenter, DataBaseApi dataBaseApi)
    {
        this.dataBaseApi = dataBaseApi;
        this.postPresenter = postPresenter;
    }

    public Pair<Comment,String> addComment(TextBoxComponent textBox, Post post, NormalUser normalUser) {
        String result = "";
        Comment comment = createComment(textBox, post, normalUser);
        result += "comment created successfully ";
        addCommentToPostAndItsSender(post, normalUser, comment);
        result += "comment added succssessfully to post with id " + post.getId();
        return new Pair<Comment,String>(comment,result);
    }


    private void addCommentToPostAndItsSender(Post post, NormalUser normalUser, Comment comment) {
        post.getComments().add(comment);
        normalUser.getComments().add(comment);
        this.dataBaseApi.addComment(comment);
    }

    private static Comment createComment(TextBoxComponent textBox, Post post, NormalUser normalUser) {
        Comment comment = new Comment(textBox, normalUser, post);
        return comment;
    }


    public Pair<Comment,String> addCommentWithUserName(String postId,String adminName, TextBoxComponent textBox,String userName) {
        NormalUser nu = this.dataBaseApi.getNormalUserByName(userName);
        if(nu == null){
            return new Pair<>(null,"no user exist with this name");
        }
        Post post = getPostById(postId, adminName);
        if(post == null){
            return new Pair<>(null,"no post exists with this id");
        }
        return this.addComment(textBox,post,nu);
    }
    public Pair<Post,String> hideCommentsByAdminNameeAndPostId(String postId, String adminName) {
        AdminUser adminUser = this.dataBaseApi.getAdminUserByName(adminName);
        if(adminUser == null){
            return new Pair<>(null,"no admin exists with this name");
        }
        Post post = adminUser.getPostById(postId);
        if (post == null){
            return new Pair<>(null,"no post exists with this id");
        }
        if (!post.isShowingComments()){
            return new Pair<>(null,"comments has already hided");
        }
        return new Pair<>(post,this.hideComments(post));
    }

    private String hideComments(Post post) {
        this.postPresenter.hideCommentsByPostID(post.getId());
        post.setShowingComments(false);
        this.dataBaseApi.setShowingComment("false",post.getId());
        return "comments of post " + post.getId() + " hided successfully";
    }

    private Post getPostById(String postId, String adminName) {
        AdminUser adminUser = this.dataBaseApi.getAdminUserByName(adminName);
        return adminUser.getPostById(postId);
    }
}
