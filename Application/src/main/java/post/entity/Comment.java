package post.entity;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import login.entities.NormalUser;
import lombok.*;
import post.boundries.TestText;
import post.boundries.TextBoxComponent;

@Setter
@Getter
@NoArgsConstructor
@JacksonXmlRootElement(localName = "comment")
public class Comment {

    @JacksonXmlProperty(localName = "textBoxComponent")
    TextBoxComponent textBoxComponent;
    @JacksonXmlProperty(localName = "postId")
    String postId;
    @JacksonXmlProperty(localName = "ownerName")
    String ownerName;


    public Comment(TextBoxComponent textBoxComponent, NormalUser normalUser, Post commentsPost) {
        this.postId = commentsPost.getId();
        this.ownerName = normalUser.getName();
        this.textBoxComponent = textBoxComponent;
    }

    public Comment(String text, String normalUserName, String postId) {
        this.postId = postId;
        this.ownerName = normalUserName;
        this.textBoxComponent = new TestText(this.postId);
    }

    public Comment(NormalUser normalUser, Post commentsPost) {
        this.postId = commentsPost.getId();
        this.ownerName = normalUser.getName();
    }



}



