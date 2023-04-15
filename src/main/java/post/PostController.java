package post;


import Login.AdminUser;
import Login.DataBaseApi;
import util.Pair;

import java.util.List;
import java.util.UUID;

public class PostController {
    PostPresenter postPresenter;
    DataBaseApi dataBaseApi;

    public PostController(PostPresenter postPresenter, DataBaseApi dataBaseApi) {
        this.postPresenter = postPresenter;
        this.dataBaseApi = dataBaseApi;
    }


    public String showPost(Post post) {
        postPresenter.showPost(post.getComponents());
        post.setShowing(true);
        return "post with id " + post.getId() + " is showing successfully";
    }
    public Pair<Post,String> addPost(List<Comment> comments, String name, List<Component> components) {
        AdminUser adminUser = this.dataBaseApi.getAdminUserByName(name);
        Post post = new Post(components,comments,adminUser, UUID.randomUUID().toString());
        adminUser.addPost(post);
        return new Pair<Post, String>(post,"post with id " + post.getId() + " created and added successfully");
    }

    public String showPostComments(Post post, List<Comment> comments) {
        if(!post.isShowing()){
            return "can't show comments of post which hasn't shown yet";
        }
        this.postPresenter.showComments(comments);
        post.setShowingComments(true);
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
            result += "comments closed successfully ";
        }
        this.postPresenter.hidePost(post.getId());
        post.setShowing(false);
        result += "post with id " + post.getId() + " hided successfully";
        return result;
    }


    public String showPostByAdminNameAndPostId(String postId, String adminName) {
        AdminUser adminUser = this.dataBaseApi.getAdminUserByName(adminName);
        if(adminUser == null){
            return "no admin exists with this name";
        }
        Post post = adminUser.getPostById(postId);
        if (post == null){
            return "no post exists with this id";
        }
        if (post.isShowing()){
            return "post is already showing";
        }
        return this.showPost(post);

    }

    public String hidePostByAdminNameAndPostId(String postId, String adminName) {
        AdminUser adminUser = this.dataBaseApi.getAdminUserByName(adminName);
        if(adminUser == null){
            return "no admin exists with this name";
        }
        Post post = adminUser.getPostById(postId);
        if (post == null){
            return "no post exists with this id";
        }
        return this.hidePost(post);
    }

    public String showCommentsOfPostByPostIdAndAdminName(String postId, String adminName) {
        AdminUser adminUser = this.dataBaseApi.getAdminUserByName(adminName);
        if(adminUser == null){
            return "no admin exists with this name";
        }
        Post post = adminUser.getPostById(postId);
        if (post == null){
            return "no post exists with this id";
        }
        if(post.isShowingComments()){
            return "comments has already shown";
        }
        return this.showPostComments(post,post.getComments());

    }


}
