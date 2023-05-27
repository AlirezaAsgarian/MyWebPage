package login.entities;


import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.*;

@Getter
@Setter

public class UserResponse {


    Long id;
    String normalName;
    public UserResponse(){
    }



    public String getType(){
        return null;
    }

    public String getResponse() {
        return null;
    }

    public String getSender() {
        return null;
    }
}
