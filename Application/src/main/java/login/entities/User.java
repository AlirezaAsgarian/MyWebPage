package login.entities;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@MappedSuperclass
@Data
@Setter
public class User {
    @JacksonXmlProperty(localName = "name")
    @Id
    @Column(name = "name")
    String name;

    @JacksonXmlProperty(localName = "password")
    @Column(name = "password")
    String password;
    public boolean checkIfNamesAreIdentical(User user) {
        return this.name.equals(user.getName());
    }
    public boolean checkIfNamesAreIdentical(String name) {
        return this.name.equals(name);
    }
    @JacksonXmlProperty(localName = "usergraphics")
    @Transient
    UserGraphics userGraphics;

}
