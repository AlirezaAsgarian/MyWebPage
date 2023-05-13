package post.interactors;

import database.boundries.PostDataBaseApi;
import login.entities.AdminUser;
import login.entities.NormalUser;
import post.entity.Comment;
import post.entity.Post;
import post.boundries.PostPresenter;
import post.boundries.TextBoxComponent;
import util.Pair;

import java.util.List;

public class CommentInteractor implements CommentUsecase {
    PostDataBaseApi dataBaseApi;
    PostPresenter postPresenter;

    public CommentInteractor(PostPresenter postPresenter, PostDataBaseApi dataBaseApi)
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


    public Pair<Post,String> showCommentsOfPostByPostIdAndAdminName(String postId, String adminName) {
        AdminUser adminUser = this.dataBaseApi.getAdminUserByName(adminName);
        if(adminUser == null){
            return new Pair<>(null,"no admin exists with this name");
        }
        Post post = adminUser.getPostById(postId);
        if (post == null){
            return new Pair<>(null,"no post exists with this id");
        }
        if(post.isShowingComments()){
            return new Pair<>(null,"comments has already shown");
        }
        return new Pair<>(post,this.showPostComments(post,post.getComments()));

    }

    @Override
    public Pair<Post, String> showCommentsOfPostByTitle(String title) {
        List<Post> posts = this.dataBaseApi.getPostsByTitles(title);
        if(posts.size() == 0){
            return new Pair<>(null,"no post founds with this title");
        }
        if(posts.size() > 1){
            return new Pair<>(null,"post title is not unique");
        }
        Post post = posts.get(0);
        return new Pair<>(post,this.showPostComments(post,post.getComments()));
    }

    public String showPostComments(Post post, List<Comment> comments) {
        if(!post.isShowing()){
            return "can't show comments of post which hasn't shown yet";
        }
        this.postPresenter.showComments(comments);
        post.setShowingComments(true);
        this.dataBaseApi.setShowingComment("true",post.getId());
        return "comments of post " + post.getId() + " is showing successfully";
    }
}
