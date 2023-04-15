package post;

import java.util.List;

public class MockPostPresenter implements PostPresenter {
    @Override
    public void showPost(List<Component> components) {
        for (Component c:
             components) {
            if(c instanceof  TextBoxComponent){
                System.out.println("text component");
            }else if(c instanceof  ImageComponent){
                System.out.println("image component");
            }else if(c instanceof  VideoComponent){
                System.out.println("video component");
            }
        }
    }

    @Override
    public void showComments(List<Comment> textBoxComponent) {
        System.out.println("comment is showing");
    }

    @Override
    public void hidePost(String post) {

    }

    @Override
    public void hideCommentsByPostID(String id) {

    }
}
