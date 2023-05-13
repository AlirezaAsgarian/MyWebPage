package post.interactors;


import database.boundries.PostDataBaseApi;
import login.entities.AdminUser;
import post.entity.Comment;
import post.boundries.Component;
import post.entity.Post;
import post.boundries.PostPresenter;
import util.Pair;

import java.util.List;
import java.util.UUID;

public class PostInteractor implements PostUsecase{
    PostPresenter postPresenter;
    PostDataBaseApi dataBaseApi;

    public PostInteractor(PostPresenter postPresenter, PostDataBaseApi dataBaseApi) {
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

    public Pair<Post,String> addPost(List<Comment> comments, String name, List<Component> components,String title) {
        AdminUser adminUser = this.dataBaseApi.getAdminUserByName(name);
        Post post = new Post(components,comments,adminUser, UUID.randomUUID().toString(),title);
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

    @Override
    public Pair<List<Post>, String> getPostsBySearchedTitle(String searchTitle) {
        List<Post> postsBySearchTitles = this.dataBaseApi.getPostsByTitles(searchTitle);
        return new Pair<>(postsBySearchTitles,postsBySearchTitles.size() + " posts found successfully");
    }

    @Override
    public Pair<Post, String> showPostByTitle(String title) {
        List<Post> posts = getPostsBySearchedTitle(title).getKey();
        if(posts.size() == 0){
            return new Pair<>(null,"no post founds with this title");
        }
        if(posts.size() > 1){
            return new Pair<>(null,"post title is not unique");
        }
        Post post = posts.get(0);
        String response = this.showPost(post);
        return new Pair<>(post,response);
    }

    public Pair<Post, String> updatePost(int rank, Component component,String postId,String ownerName) {
        AdminUser adminUser = this.dataBaseApi.getAdminUserByName(ownerName);
        if(adminUser == null){
            return new Pair<>(null,"no admin exists with this name");
        }
        Post post = adminUser.getPostById(postId);
        if (post == null){
            return new Pair<>(null,"no post exists with this id");
        }
        return new Pair<>(post,this.updateComponentByRank(rank,component,post));
    }

    @Override
    public Pair<List<Post>, String> getPostByLastDates(int i) {
        List<Post> posts = this.dataBaseApi.getPostsByDates(i);
        if(posts.size() < i){
            return new Pair<>(null,"this number of posts doesnt exists");
        }
        return new Pair<>(posts,i + " number of posts returned successfully");
    }

    private String updateComponentByRank(int rank, Component component,Post post) {
        post.updateComponentByRank(rank,component);
        return component.getClass().getSimpleName() + String.valueOf(rank) + " of post with title " + post.getTitle() + " changed successfully";
    }


}
