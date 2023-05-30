package post.boundries;


import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import jakarta.persistence.*;
import lombok.*;
import post.entity.Comment;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@JacksonXmlRootElement(localName = "textcomponent")
@Embeddable
public class TestText {
    @JacksonXmlProperty(localName = "ownerPostId")
    @Column(name = "own_name")
    String ownerPostId;
    @JacksonXmlProperty(localName = "type")
    @Column(name = "component_type")
    String type = "text";
    @JacksonXmlProperty(localName = "path")
    @Column(name = "path")
    String path = "text path";
    @Transient
    int rank;


    public TestText(String postId) {
        this.ownerPostId = postId;
    }

    public String getPath() {
        return this.path;
    }

    public String getType() {
        return this.type;
    }

    public String getOwnerPostId() {
        return this.ownerPostId;
    }

    public int getRank() {
        return this.rank;
    }

}