package appplay;

import Login.LoginController;
import post.*;
import util.Pair;

import java.util.ArrayList;
import java.util.List;

public class CommandInterpretor implements Interpreter {
    LoginController loginController;
    PostController  postController;
    CommentController commentController;

    public CommandInterpretor(LoginController loginController, PostController postController,CommentController
                              commentController) {
        this.loginController = loginController;
        this.postController = postController;
        this.commentController = commentController;
    }


    public Response interpret(Command command) {
        String[] words = command.getCommand().split("\\s+");
        Response response = null;// split by whitespace
        switch (words[0]) {
            case "login" -> response = login(words);
            case "adduser" -> response = addUser(words);
            case "addpost" -> response = addPost(words,command.getComponents());
            case "addcomment" -> response = addComment(words,command.getComponents());
            case "showpost" -> response = showPost(words);
            case "showcomments" -> response = showComments(words);
            case  "hidepost" -> response = hidePost(words);
        }
        return response;
    }

    private Response addComment(String[] words, List<Component> components) {
        TextBoxComponent textBoxComponent = (TextBoxComponent) components.get(0);
        return new Response(this.commentController.addCommentWithUserName(words[1],words[2],textBoxComponent,words[3]).getValue());
    }

    private Response hidePost(String[] words) {
        return new Response(this.postController.hidePostByAdminNameeAndPostId(words[1],words[2]));
    }

    private Response showComments(String[] words) {
        return new Response(this.postController.showCommentsOfPostByPostIdAndAdminName(words[1],words[2]));
    }

    private Response showPost(String[] words) {
        return new Response(postController.showPostByAdminNameAndPostId(words[1],words[2]));
    }

    private Response addPost(String[] words, List<Component> components) {
        Pair<Post, String> postStringPair = this.postController.addPost(new ArrayList<>(), words[1], components);
        String responseMessage = postStringPair.getValue();
        return new Response(responseMessage);
    }

    private Response addUser(String[] words) {
        switch (words[1]) {
            case "normal" -> {
                return new Response(loginController.tryAddingNormalUser(words[2], words[3],new ArrayList<>()));
            }
            case "admin" -> {
                return new Response(loginController.tryAddingAdminUser(words[2], words[3],new ArrayList<>()));
            }
        }
        return null;
    }

    private Response login(String[] words) {
        switch (words[1]) {
            case "normal" -> {
                return new Response(loginController.loginUser(words[3], words[4]));
            }
        }
        return null;
    }
}
