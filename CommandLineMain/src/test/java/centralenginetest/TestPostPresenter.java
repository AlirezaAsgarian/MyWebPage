package centralenginetest;

import post.boundries.Component;
import post.boundries.ImageComponent;
import post.boundries.PostPresenter;
import post.boundries.TextBoxComponent;
import post.entity.Comment;

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
                  System.out.println("commment text component : " + c.getOwnerName());
              }
    }

    @Override
    public void hidePost(String post) {

    }

    @Override
    public void hideCommentsByPostID(String id) {

    }
}
