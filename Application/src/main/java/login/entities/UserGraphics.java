package login.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import post.boundries.ImageComponent;

@Getter
@Builder
@NoArgsConstructor
@JacksonXmlRootElement(localName = "usergraphic")
public class UserGraphics {
    @JsonIgnore
    public ImageComponent imageComponent;
    @JacksonXmlProperty(localName = "image-name")
    public String name;

    public UserGraphics(ImageComponent imageComponent, String name) {
        this.imageComponent = imageComponent;
        this.name = name;
    }
}
