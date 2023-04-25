package appplay;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;

public class Response {

    @Setter
    @Getter
    String response;
    @Getter
    HashMap<String,Object> objects;

    public Response(String response) {
        this.response = response;
    }

    public Response(String response, HashMap<String, Object> objects) {
        this.response = response;
        this.objects = objects;
    }
}
