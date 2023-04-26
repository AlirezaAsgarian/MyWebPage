package appplay;

import login.interactors.LoginController;
import post.interactors.CommentController;
import post.interactors.PostController;

public interface CommandInterpeterFactory {


    LoginController getLoginController();

    PostController getPostController();

    CommentController getCommentController();
}
