package com.example.springui.controllers.comment;

import com.example.springui.services.CommentService;
import com.example.springui.services.PostService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import post.entity.Comment;
import post.entity.Post;
import post.interactors.PostUsecase;
import util.Pair;

import java.util.Map;

@RestController
public class CommentController {

    @Autowired
    CommentService commentService;




    @RequestMapping(method = RequestMethod.POST,value = "/add-comment",produces = "application/xml")
    public ResponseEntity<String> addComment(@RequestBody Map<String,Object> arguments){ // arguments : admin name , comment
        ObjectMapper objectMapper = new ObjectMapper();
        String adminName = objectMapper.convertValue(arguments.get("adminName"),String.class);
        Comment comment = objectMapper.convertValue(arguments.get("comment"),Comment.class);
        Pair<Pair<Comment,String>,HttpStatus> response = commentService.addComment(adminName,comment,comment.getTextBoxComponent());
        return new ResponseEntity<>(response.getKey().getValue(),response.getValue());
    }

    @RequestMapping(method = RequestMethod.POST,value = "/show-comment",produces = "application/xml")
    public ResponseEntity<Pair<Post,String>> showComment(@RequestBody Map<String,Object> arguments){ // arguments : admin name , comment
        ObjectMapper objectMapper = new ObjectMapper();
        String adminName = objectMapper.convertValue(arguments.get("adminName"),String.class);
        String postId = objectMapper.convertValue(arguments.get("postId"),String.class);
        Pair<Pair<Post,String>,HttpStatus> response = commentService.showComment(postId,adminName);
        return new ResponseEntity<>(response.getKey(),response.getValue());
    }

    @RequestMapping(method = RequestMethod.POST,value = "/hide-comment",produces = "application/xml")
    public ResponseEntity<Pair<Post,String>> hideComment(@RequestBody Map<String,Object> arguments){ // arguments : admin name , comment
        ObjectMapper objectMapper = new ObjectMapper();
        String adminName = objectMapper.convertValue(arguments.get("adminName"),String.class);
        String postId = objectMapper.convertValue(arguments.get("postId"),String.class);
        Pair<Pair<Post,String>,HttpStatus> response = commentService.hideComment(postId,adminName);
        return new ResponseEntity<>(response.getKey(),response.getValue());
    }

}
