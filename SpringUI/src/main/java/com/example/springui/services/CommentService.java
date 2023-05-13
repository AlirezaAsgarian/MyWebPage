package com.example.springui.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import post.boundries.TextBoxComponent;
import post.entity.Comment;
import post.entity.Post;
import post.interactors.CommentUsecase;
import util.Pair;

@Service
public class CommentService {

    @Qualifier("getCommentUseCase")
    @Autowired
    CommentUsecase commentUsecase;


    public Pair<Pair<Comment,String>, HttpStatus> addComment(String adminname, Comment comment, TextBoxComponent textBoxComponent) {
        Pair<Comment,String> response = commentUsecase.addCommentWithUserName(comment.getPostId(), adminname, textBoxComponent,comment.getOwnerName());
        if(response.getValue().equals("comment created successfully comment added succssessfully to post with id " +
                comment.getPostId())) {
            return new Pair<>(response, HttpStatus.OK);
        }
        return null;
    }

    public Pair<Pair<Post, String>, HttpStatus> showComment(String postId, String adminName) {
        Pair<Post,String> response = commentUsecase.showCommentsOfPostByPostIdAndAdminName(postId, adminName);
        if(response.getValue().equals("comments of post " + postId + " is showing successfully")) {
            return new Pair<>(response, HttpStatus.OK);
        }
        return null;

    }

    public Pair<Pair<Post, String>, HttpStatus> hideComment(String postId, String adminName) {
        Pair<Post,String> response = commentUsecase.hideCommentsByAdminNameeAndPostId(postId, adminName);
        if(response.getValue().equals("comments of post " + postId + " hided successfully")) {
            return new Pair<>(response, HttpStatus.OK);
        }else if(response.getValue().equals("comments has already hided")){
            return new Pair<>(response, HttpStatus.CONFLICT);
        }else if(response.getValue().equals("no admin exists with this name")){
            return new Pair<>(response,HttpStatus.NOT_FOUND);
        }else if(response.getValue().equals("no post exists with this id")){
            return new Pair<>(response,HttpStatus.NOT_FOUND);
        }
        return null;

    }
}


