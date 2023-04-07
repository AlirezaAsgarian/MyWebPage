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
}
