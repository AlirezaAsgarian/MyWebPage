package com.example.addpost;


import com.example.springui.SpringUiApplication;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.google.gson.Gson;
import login.entities.AdminUser;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import post.boundries.Component;
import post.boundries.TextBoxComponent;
import post.entity.Comment;
import post.entity.Post;
import util.Pair;

import java.io.UnsupportedEncodingException;
import java.util.*;

@SpringBootTest(classes = SpringUiApplication.class)
public class AddPostTestEntity {
    @Autowired
    WebApplicationContext webApplicationContext;
    protected MockMvc mvc;


    @BeforeEach
    void setUp() {
        mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    void addPostTest() throws Exception  {
        AdminUser adminUser = new AdminUser("ali","password",new ArrayList<>());
        Post post = new Post(new ArrayList<>(),new ArrayList<>(),adminUser, UUID.randomUUID().toString());
        XmlMapper xmlMapper = new XmlMapper();
        String xml = xmlMapper.writeValueAsString(post);
        Post normalUser1 = xmlMapper.readValue(xml,Post.class);
        MvcResult result = mvc.perform(MockMvcRequestBuilders.post("/add-post").contentType(MediaType.APPLICATION_XML).content(xml)).andReturn();
        Pair response = xmlMapper.readValue(result.getResponse().getContentAsString(),Pair.class);
        String responseMessage = (String) response.getValue();
        LinkedHashMap<String,String> responsePost = (LinkedHashMap<String, String>) response.getKey();
        Assertions.assertEquals("post with id " + responsePost.get("id") + " created and added successfully",responseMessage);
        Assertions.assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
    }

    @Test
    void addCommentTest() throws Exception{
        AdminUser adminUser = new AdminUser("BBEX","password",new ArrayList<>());
        Post post = new Post(new ArrayList<>(),new ArrayList<>(),adminUser, UUID.randomUUID().toString());
        XmlMapper xmlMapper = new XmlMapper();
        String xml = xmlMapper.writeValueAsString(post);
        Post normalUser1 = xmlMapper.readValue(xml,Post.class);
        MvcResult result = mvc.perform(MockMvcRequestBuilders.post("/add-post").contentType(MediaType.APPLICATION_XML).content(xml)).andReturn();
        Pair response = xmlMapper.readValue(result.getResponse().getContentAsString(),Pair.class);
        LinkedHashMap<String,String> responsePost = (LinkedHashMap<String, String>) response.getKey();
        String responsePostId = responsePost.get("id");
        HashMap<String,Object> args = new HashMap<>();
        args.put("adminName",adminUser.getName());
        args.put("comment",new Comment("text","ali",responsePostId));
        xml = xmlMapper.writeValueAsString(args);
        Map<String,Object> objs = xmlMapper.readValue(xml, new TypeReference<Map<String, Object>>() {});

        result = mvc.perform(MockMvcRequestBuilders.post("/add-comment").contentType(MediaType.APPLICATION_XML).content(xml)).andReturn();
        Assertions.assertEquals("comment created successfully comment added succssessfully to post with id " +
                responsePostId,result.getResponse().getContentAsString());

    }




//    Post post1 = new Post(new ArrayList<Component>(),new ArrayList<Comment>(),this.adminUser, UUID.randomUUID().toString());
//        this.dataBaseApi.addPost(post1);
//    Pair<Comment,String> commentXmessage = commentController.addCommentWithUserName(post1.getId(),
//            this.adminUser.getName(),this.textBox,this.normalUser.getName());
//    String message = commentXmessage.getValue();
//        Assertions.assertEquals("comment created successfully comment added succssessfully to post with id " +
//                post1.getId(),message);
//        this.adminUser = this.dataBaseApi.getAdminUserByName(this.adminUser.getName());
//        Assertions.assertTrue(this.adminUser.getPosts().stream().map(e -> e.getId()).toList().contains(commentXmessage.getKey().getCommentsPostId()));

}
