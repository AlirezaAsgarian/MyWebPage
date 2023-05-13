package com.example.springui.controllers.post;


import com.example.springui.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import post.entity.Post;
import util.Pair;

import java.util.Map;

@RestController
public class PostController {

    @Autowired
    PostService postService;


    @RequestMapping(method = RequestMethod.POST,value = "/add-post",produces = "application/xml")
    public ResponseEntity<Pair<Post,String>> addPost(@RequestBody Post post){
        Pair<Pair<Post,String>, HttpStatus> response = postService.addPost(post);
        return new ResponseEntity<>(response.getKey(),response.getValue());
    }

    @RequestMapping(method = RequestMethod.POST,value = "/show-post",produces = "application/xml")
    public ResponseEntity<Pair<Post,String>> showPost(@RequestBody Map<String,String> args){
        String postId = args.get("postId");
        String adminName = args.get("adminName");
        Pair<Pair<Post,String>, HttpStatus> response = postService.showPost(postId,adminName);
        return new ResponseEntity<>(response.getKey(),response.getValue());
    }

    @RequestMapping(method = RequestMethod.POST,value = "/hide-post",produces = "application/xml")
    public ResponseEntity<Pair<Post,String>> hidePost(@RequestBody Map<String,String> args){
        String adminName = args.get("adminName");
        String postId = args.get("postId");
        Pair<Pair<Post,String>, HttpStatus> response = postService.hidePost(postId,adminName);
        return new ResponseEntity<>(response.getKey(),response.getValue());
    }



}
