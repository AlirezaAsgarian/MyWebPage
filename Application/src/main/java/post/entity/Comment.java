package post.entity;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import login.entities.NormalUser;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import post.boundries.TextBoxComponent;

@Getter
@NoArgsConstructor
@JacksonXmlRootElement(localName = "comment")
public class Comment {

    @JacksonXmlProperty(localName = "text-component")
    TextBoxComponent textBoxComponent;
    @JacksonXmlProperty(localName = "post-id")
    String postId;
    @JacksonXmlProperty(localName = "owner-name")
    String ownerName;


    public Comment(TextBoxComponent textBoxComponent, NormalUser normalUser, Post commentsPost) {
        this.postId = commentsPost.getId();
        this.ownerName = normalUser.getName();
        this.textBoxComponent = textBoxComponent;
    }

    public Comment(String text, String normalUserName, String postId) {
        this.postId = postId;
        this.ownerName = normalUserName;
        this.textBoxComponent = new TextBoxComponent() {

            @Override
            public String getPath() {
                return "text path";
            }

            @Override
            public String getType() {
                return "text";
            }

            @Override
            public String getOwnerPostId() {
                return postId;
            }
        }; // todo : create text component with text
    }

    public Comment(NormalUser normalUser, Post commentsPost) {
        this.postId = commentsPost.getId();
        this.ownerName = normalUser.getName();
    }

    public void setTextBox(TextBoxComponent textBox) {
        this.textBoxComponent = textBox;
    }

    public String getCommentsPostId() {
        return this.postId;
    }
}
