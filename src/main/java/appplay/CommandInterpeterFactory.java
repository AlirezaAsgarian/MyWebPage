package appplay;

import Login.LoginController;
import post.CommentController;
import post.PostController;

public interface CommandInterpeterFactory {


    LoginController getLoginController();

    PostController getPostController();

    CommentController getCommentController();
}
