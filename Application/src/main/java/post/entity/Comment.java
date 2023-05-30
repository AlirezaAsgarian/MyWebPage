package post.entity;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import jakarta.persistence.*;
import login.entities.NormalUser;
import lombok.*;
import post.boundries.TestText;

@Setter
@Getter
@NoArgsConstructor
@JacksonXmlRootElement(localName = "comment")
@Entity
@Table(name = "user_comment")
public class Comment {

    @JacksonXmlProperty(localName = "textBoxComponent")
    @Embedded
    TestText textBoxComponent;
    @JacksonXmlProperty(localName = "postId")
    @Column(name = "post_id")
    String postId;
    @JacksonXmlProperty(localName = "ownerName")
    @Column(name = "owne_name")
    String ownerName = "default";
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id = 20L;


    public Comment(TestText text, NormalUser normalUser, Post commentsPost) {
        this.postId = commentsPost.getId();
        this.ownerName = normalUser.getName();
        this.textBoxComponent = (TestText) textBoxComponent;
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


    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}



