package login.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Setter
@Getter
@NoArgsConstructor
public class FollowingResponse extends UserResponse {

    Long id;
    boolean isAccept;
    String adminName;
    String response;
    String type;
    public FollowingResponse(boolean isAccept, String adminName, String normalName) {
        super();
        this.type = this.getClass().getSimpleName();
        this.isAccept = isAccept;
        this.adminName = adminName;
        this.normalName = normalName;
        if(isAccept){
            response = "Accepted";
        }else {
            response = "Rejected";
        }
    }

    @Override
    public String getResponse() {
        return response;
    }

    @Override
    public String getSender() {
        return this.adminName;
    }
}
