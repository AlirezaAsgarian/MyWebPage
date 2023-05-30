package post.boundries;


import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Inheritance;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Value;

@JsonDeserialize(as = TestText.class)
@MappedSuperclass
@Embeddable
public abstract class TextBoxComponent implements Component{
}
