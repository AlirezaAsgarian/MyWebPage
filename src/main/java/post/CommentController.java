package post;

import Login.AdminUser;
import Login.DataBaseApi;
import Login.NormalUser;
import appplay.Response;
import util.Pair;

public class CommentController {
    DataBaseApi dataBaseApi;

    public CommentController(DataBaseApi dataBaseApi) {
        this.dataBaseApi = dataBaseApi;
    }

    public Pair<Comment,String> addComment(TextBoxComponent textBox, Post post, NormalUser normalUser) {
        String result = "";
        Comment comment = createComment(textBox, post, normalUser);
        result += "comment created successfully ";
        addCommentToPostAndItsSender(post, normalUser, comment);
        result += "comment added succssessfully to post with id " + post.getId();
        return new Pair<Comment,String>(comment,result);
    }


    private static void addCommentToPostAndItsSender(Post post, NormalUser normalUser, Comment comment) {
        post.getComments().add(comment);
        normalUser.getComments().add(comment);
    }

    private static Comment createComment(TextBoxComponent textBox, Post post, NormalUser normalUser) {
        Comment comment = new Comment(textBox, normalUser, post);
        return comment;
    }

    public Comment addComment(Post post, NormalUser normalUser) {
        return new Comment(normalUser,post);
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

    private Post getPostById(String postId, String adminName) {
        AdminUser adminUser = this.dataBaseApi.getAdminUserByName(adminName);
        return adminUser.getPostById(postId);
    }
}
