package appplay;

import login.interactors.LoginUsecase;
import post.interactors.CommentUsecase;
import post.interactors.PostUsecase;

public interface CommandInterpeterFactory {


    LoginUsecase getLoginController();

    PostUsecase getPostController();

    CommentUsecase getCommentController();
}
