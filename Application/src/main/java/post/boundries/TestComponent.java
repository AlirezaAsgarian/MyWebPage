package post.boundries;

public class TestComponent implements Component {
    String path;
    String type;
    String postId;

    public TestComponent(String path, String type, String postId) {
        this.path = path;
        this.type = type;
        this.postId = postId;
    }

    @Override
    public String getPath() {
        return path;
    }

    @Override
    public String getType() {
        return type;
    }

    @Override
    public String getOwnerPostId() {
        return postId;
    }
}
