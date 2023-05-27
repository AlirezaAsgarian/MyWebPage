package com.example.showpost;

import com.example.springui.SpringUiApplication;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import login.entities.AdminUser;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import post.boundries.Component;
import post.entity.Comment;
import post.entity.Post;
import util.Pair;

import java.util.ArrayList;
import java.util.HashMap;

@SpringBootTest(classes = SpringUiApplication.class)
public class ShowCommentTestEntity extends PostTestBaseEntity {





    @Test
    void showCommentsOfPostByAdminNameAndPostId() throws Exception{
        XmlMapper xmlMapper = new XmlMapper();
        AdminUser adminUser = new AdminUser("BBEX","password",new ArrayList<>());
        Post post = new Post(new ArrayList<Component>(),new ArrayList<Comment>(),adminUser.getName(),"");
        Pair response = addPost(post,xmlMapper);
        HashMap<String,Object> args = new HashMap<>();
        Post responsePost = xmlMapper.convertValue(response.getKey(),Post.class);
        MvcResult result = showPost(xmlMapper,args,responsePost,adminUser.getName());
        response = xmlMapper.readValue(result.getResponse().getContentAsString(),Pair.class);
        String responseMessage = xmlMapper.convertValue(response.getValue(),String.class);
        responsePost = xmlMapper.convertValue(response.getKey(),Post.class);
        args.clear();
        args.put("postId",responsePost.getId());
        args.put("adminName",adminUser.getName());
        String xml = xmlMapper.writeValueAsString(args);
        result = mvc.perform(MockMvcRequestBuilders.post("/show-comment").contentType(MediaType.APPLICATION_XML).content(xml)).andReturn();
        response = xmlMapper.readValue(result.getResponse().getContentAsString(),Pair.class);
        responseMessage = xmlMapper.convertValue(response.getValue(),String.class);
        responsePost = xmlMapper.convertValue(response.getKey(),Post.class);
        Assertions.assertTrue(responsePost.isShowingComments());
        Assertions.assertEquals("comments of post " + responsePost.getId() + " is showing successfully", responseMessage);
        Assertions.assertEquals(HttpStatus.OK.value(),result.getResponse().getStatus());
    }





}
