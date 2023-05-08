package post.interactors;


import login.entities.AdminUser;
import database.boundries.DataBaseApi;
import post.entity.Comment;
import post.boundries.Component;
import post.entity.Post;
import post.boundries.PostPresenter;
import util.Pair;

import java.util.List;
import java.util.UUID;

public class PostInteractor implements PostUsecase{
    PostPresenter postPresenter;
    DataBaseApi dataBaseApi;

    public PostInteractor(PostPresenter postPresenter, DataBaseApi dataBaseApi) {
        this.postPresenter = postPresenter;
        this.dataBaseApi = dataBaseApi;
    }


    public String showPost(Post post) {
        postPresenter.showPost(post.getComponents());
        post.setShowing(true);
        this.dataBaseApi.setPostShowing("true",post.getId());
        return "post with id " + post.getId() + " is showing successfully";
    }

    public Pair<Post,String> addPost(List<Comment> comments, String name, List<Component> components) {
        AdminUser adminUser = this.dataBaseApi.getAdminUserByName(name);
        Post post = new Post(components,comments,adminUser, UUID.randomUUID().toString());
        this.dataBaseApi.addPost(post);
        return new Pair<Post, String>(post,"post with id " + post.getId() + " created and added successfully");
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

    public String hidePost(Post post) {
        String result = "";
        if(!post.isShowing()){
            return "post has already hided";
        }
        if(post.isShowingComments()){
            this.postPresenter.hideCommentsByPostID(post.getId());
            post.setShowingComments(false);
            this.dataBaseApi.setShowingComment("false",post.getId());
            result += "comments closed successfully ";
        }
        this.postPresenter.hidePost(post.getId());
        post.setShowing(false);
        this.dataBaseApi.setPostShowing("false",post.getId());
        result += "post with id " + post.getId() + " hided successfully";
        return result;
    }


    public Pair<Post,String> showPostByAdminNameAndPostId(String postId, String adminName) {
        AdminUser adminUser = this.dataBaseApi.getAdminUserByName(adminName);
        if(adminUser == null){
            return new Pair<>(null,"no admin exists with this name");
        }
        Post post = adminUser.getPostById(postId);
        if (post == null){
            return new Pair<>(null,"no post exists with this id");
        }
        if (post.isShowing()){
            return new Pair<>(null,"post is already showing");
        }
        return new Pair<>(post,this.showPost(post));

    }

    public Pair<Post,String> hidePostByAdminNameAndPostId(String postId, String adminName) {
        AdminUser adminUser = this.dataBaseApi.getAdminUserByName(adminName);
        if(adminUser == null){
            return new Pair<>(null,"no admin exists with this name");
        }
        Post post = adminUser.getPostById(postId);
        if (post == null){
            return new Pair<>(null,"no post exists with this id");
        }
        return new Pair<>(post,this.hidePost(post));
    }




}
