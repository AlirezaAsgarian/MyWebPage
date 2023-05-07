package login.entities;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import post.entity.Comment;
import post.entity.Post;
import post.boundries.TextBoxComponent;
import lombok.Getter;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;


import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JacksonXmlRootElement(localName = "normaluser")
public class NormalUser extends User{
    @Getter
    @JacksonXmlProperty(localName = "comments")
    List<Comment> comments;
    public NormalUser(String name, String password, List<Comment> comments) {
        super(name,password);
        this.comments = comments;
    }


    public Comment createCommment(TextBoxComponent textBox,Post commentPost) {
        return new Comment(textBox,this,commentPost);
    }
}
