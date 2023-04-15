package centralenginetest;

import post.*;

import java.util.List;

public class TestPostPresenter implements PostPresenter {
    @Override
    public void showPost(List<Component> components) {
            for (Component c :
            components){
                if (c instanceof TextBoxComponent){
                    System.out.println("text component");
                }else if(c instanceof ImageComponent){
                    System.out.println("image component");
                }else {
                    System.out.println("video component");
                }
            }
    }

    @Override
    public void showComments(List<Comment> comments) {
              for (Comment c :
              comments){
                  System.out.println("commment text component : " + c.getOwner().getName());
              }
    }

    @Override
    public void hidePost(String post) {

    }

    @Override
    public void hideCommentsByPostID(String id) {

    }
}
