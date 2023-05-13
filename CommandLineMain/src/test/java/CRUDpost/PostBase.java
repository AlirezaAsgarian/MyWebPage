package CRUDpost;

import org.junit.jupiter.api.Assertions;
import org.mockito.Mockito;
import post.boundries.Component;
import post.entity.Post;

public class PostBase {
    protected void assertName(String expectedName, String actualName) {
        Assertions.assertEquals(expectedName, actualName);
    }

    protected void assertPathComponent(String expectedPath, String actualPath) {
        Assertions.assertEquals(expectedPath, actualPath);
    }

    protected void assertPostId(String expectedPostId, String actualId) {
        Assertions.assertEquals(expectedPostId, actualId);
    }

    protected void assertResponseMessage(String expected, String message) {
        Assertions.assertEquals(expected, message);
    }
    protected void assertPost(Post post, Post post1) {
        Assertions.assertEquals(post.getId(), post1.getId());
        Assertions.assertEquals(post.getTitle(),post1.getTitle());
    }

    protected void assertPostTitle(String expectedTitle, String actualTitle) {
        Assertions.assertEquals(expectedTitle, actualTitle);
    }
    protected   void mockComponent(Component component, String imagePath) {
        Mockito.when(component.getType()).thenReturn(component.getClass().getSimpleName());
        Mockito.when(component.getPath()).thenReturn(imagePath);
    }
}
