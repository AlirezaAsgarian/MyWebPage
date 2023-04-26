package appplay;

import login.interactors.LoginController;
import post.boundries.Component;
import post.boundries.TextBoxComponent;
import post.entity.Post;
import post.interactors.CommentController;
import post.interactors.PostController;
import util.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CommandInterpretor implements Interpreter {
    LoginController loginController;
    PostController postController;
    CommentController commentController;

    public CommandInterpretor(CommandInterpeterFactory factory) {
        this.loginController = factory.getLoginController();
        this.postController = factory.getPostController();
        this.commentController = factory.getCommentController();
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
            case "hidepost" -> response = hidePost(words);
            case "hidecomments" -> response = hideComments(words);
        }
        return response;
    }

    private Response hideComments(String[] words) {
       return new Response(this.commentController.hideCommentsByAdminNameeAndPostId(words[1],words[2]).getValue());
    }

    private Response addComment(String[] words, List<Component> components) {
        TextBoxComponent textBoxComponent = (TextBoxComponent) components.get(0);
        return new Response(this.commentController.addCommentWithUserName(words[1],words[2],textBoxComponent,words[3]).getValue());
    }

    private Response hidePost(String[] words) {
        return new Response(this.postController.hidePostByAdminNameAndPostId(words[1],words[2]).getValue());
    }

    private Response showComments(String[] words) {
        return new Response(this.postController.showCommentsOfPostByPostIdAndAdminName(words[1],words[2]).getValue());
    }

    private Response showPost(String[] words) {
        return new Response(postController.showPostByAdminNameAndPostId(words[1],words[2]).getValue());
    }

    private Response addPost(String[] words, List<Component> components) {
        Pair<Post, String> postStringPair = this.postController.addPost(new ArrayList<>(), words[1], components);
        String responseMessage = postStringPair.getValue();
        Post post = postStringPair.getKey();
        return new Response(responseMessage,new HashMap<>(Map.of("post",post)));
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
                return new Response(loginController.loginNormalUser(words[3], words[4]));
            }
            case "admin" -> {
                return new Response(loginController.loginAdminUser(words[3], words[4]));
            }
        }
        return null;
    }
}
