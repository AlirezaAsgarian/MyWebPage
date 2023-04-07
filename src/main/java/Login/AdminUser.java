package Login;

import post.Post;

import lombok.Getter;

import java.util.List;

public class AdminUser extends User{

    @Getter
    List<Post> posts;
    public AdminUser(String name, String password, List<Post> posts) {
        super(name,password);
        this.posts = posts;
    }


    public void addPost(Post post) {
        this.posts.add(post);
    }

    public Post getPostById(String postId) {
        for (Post post:
             posts) {
            if(postId.equals(post.getId())){
                return post;
            }
        }
        return null;
    }
}
