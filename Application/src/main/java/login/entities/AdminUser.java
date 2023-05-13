package login.entities;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import post.entity.Post;

import lombok.Getter;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JacksonXmlRootElement(localName = "adminuser")
public class AdminUser extends User{

    List<String> followers;
    List<String> followingRequests;

    @JacksonXmlProperty(localName = "posts")
    List<Post> posts;
    public AdminUser(String name, String password, List<Post> posts) {
        super(name,password);
        this.posts = posts;
    }

    public AdminUser(String name, String password, List<Post> posts,List<String> followers,List<String> followingRequests) {
        super(name,password);
        this.posts = posts;
        this.followers = followers;
        this.followingRequests = followingRequests;
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
