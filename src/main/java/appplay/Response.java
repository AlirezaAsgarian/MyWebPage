package appplay;

import lombok.Getter;
import lombok.Setter;

public class Response {

    @Setter
    @Getter
    String response;

    public Response(String response) {
        this.response = response;
    }
}
