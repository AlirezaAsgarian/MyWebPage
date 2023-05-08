package post.boundries;


import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.*;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JacksonXmlRootElement(localName = "textcomponent")
public class TestText implements TextBoxComponent {
    @JacksonXmlProperty(localName = "ownerPostId")
    String ownerPostId;
    @JacksonXmlProperty(localName = "type")
    String type = "text";
    @JacksonXmlProperty(localName = "path")
    String path = "text path";

    public TestText(String postId) {
        this.ownerPostId = postId;
    }

    @Override
    public String getPath() {
        return this.path;
    }

    @Override
    public String getType() {
        return this.type;
    }

    @Override
    public String getOwnerPostId() {
        return this.ownerPostId;
    }
}