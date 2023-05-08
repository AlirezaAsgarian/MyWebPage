package com.example.springui.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import post.entity.Post;
import post.interactors.PostUsecase;
import util.Pair;

@Service
public class PostService {
    @Qualifier("getPostUseCase")
    @Autowired
    PostUsecase postUsecase;


    public Pair<Pair<Post, String>, HttpStatus> addPost(Post post) {
        Pair<Post, String> response = postUsecase.addPost(post.getComments(), post.getOwnerName(), post.getComponents());
        if (response.getValue().equals("post with id " + response.getKey().getId() + " created and added successfully")) {
            return new Pair<>(response, HttpStatus.OK);
        } else {
            return null;
        }
    }

    public Pair<Pair<Post, String>, HttpStatus> showPost(String postId, String adminName) {
        Pair<Post, String> response = postUsecase.showPostByAdminNameAndPostId(postId, adminName);
        if (response.getValue().equals("post with id " + postId + " is showing successfully")) {
            return new Pair<>(response, HttpStatus.OK);
        } else if (response.getValue().equals("post is already showing")) {
            return new Pair<>(response, HttpStatus.CONFLICT);
        } else if (response.getValue().equals("no post exists with this id")) {
            return new Pair<>(response, HttpStatus.NOT_FOUND);
        } else if (response.getValue().equals("no admin exists with this name")) {
            return new Pair<>(response, HttpStatus.NOT_FOUND);
        } else {
            return null;
        }
    }
}
