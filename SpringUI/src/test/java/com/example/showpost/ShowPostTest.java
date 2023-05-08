package com.example.showpost;

import com.example.springui.SpringUiApplication;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import login.entities.AdminUser;
import org.jboss.logging.annotations.Pos;
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
import post.entity.Comment;
import post.entity.Post;
import util.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@SpringBootTest(classes = SpringUiApplication.class)
public class ShowPostTest {

    @Autowired
    WebApplicationContext webApplicationContext;
    protected MockMvc mvc;


    @BeforeEach
    void setUp() {
        mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }


    @Test
    void showPostTest() throws Exception{
        XmlMapper xmlMapper = new XmlMapper();
        AdminUser adminUser = new AdminUser("BBEX","password",new ArrayList<>());
        Post post = new Post(new ArrayList<Component>(),new ArrayList<Comment>(),adminUser.getName(),"");
        Pair response = addPost(post,xmlMapper);
        HashMap<String,Object> args = new HashMap<>();
        Post responsePost = xmlMapper.convertValue(response.getKey(),Post.class);
        args.put("postId", responsePost.getId());
        args.put("adminName","BBEX");
        String xml = xmlMapper.writeValueAsString(args);
        MvcResult result = mvc.perform(MockMvcRequestBuilders.post("/show-post").contentType(MediaType.APPLICATION_XML).content(xml)).andReturn();
        response  = xmlMapper.readValue(result.getResponse().getContentAsString(),Pair.class);
        String responseMessage = xmlMapper.convertValue(response.getValue(),String.class);
        Assertions.assertEquals("post with id " + responsePost.getId() + " is showing successfully", responseMessage);
        Assertions.assertEquals(HttpStatus.OK.value(),result.getResponse().getStatus());
    }



    @Test
    void showPostByAdminNameAndPostIdAndItsAlreadyShowing() throws Exception{
            XmlMapper xmlMapper = new XmlMapper();
            AdminUser adminUser = new AdminUser("BBEX","password",new ArrayList<>());
            Post post = new Post(new ArrayList<Component>(),new ArrayList<Comment>(),adminUser.getName(),"");
            Pair response = addPost(post,xmlMapper);
            HashMap<String,Object> args = new HashMap<>();
            Post responsePost = xmlMapper.convertValue(response.getKey(),Post.class);
            showPost(xmlMapper, args, responsePost,adminUser.getName());
            MvcResult result = showPost(xmlMapper,args,responsePost,adminUser.getName());
            response = xmlMapper.readValue(result.getResponse().getContentAsString(),Pair.class);
            String responseMessage = xmlMapper.convertValue(response.getValue(),String.class);
            Assertions.assertEquals("post is already showing", responseMessage);
            Assertions.assertEquals(HttpStatus.CONFLICT.value(),result.getResponse().getStatus());
    }


        @Test
        void showPostByAdminButPostIdDoesntExists() throws Exception{
            XmlMapper xmlMapper = new XmlMapper();
            AdminUser adminUser = new AdminUser("BBEX","password",new ArrayList<>());
            Post post = new Post(new ArrayList<Component>(),new ArrayList<Comment>(),adminUser.getName(),"");
            HashMap<String,Object> args = new HashMap<>();
            MvcResult result = showPost(xmlMapper, args, post,adminUser.getName());
            Pair response = xmlMapper.readValue(result.getResponse().getContentAsString(),Pair.class);
            String responseMessage = xmlMapper.convertValue(response.getValue(),String.class);
            Assertions.assertEquals("no post exists with this id", responseMessage);
            Assertions.assertEquals(HttpStatus.NOT_FOUND.value(),result.getResponse().getStatus());
        }

//    @Test
//    void showPostByAdminButAdminNameDoesntExists() {
//        AdminUser adminUser = new AdminUser("ali", "password", new ArrayList<>());
//        this.dataBaseApi.addAdminUser(adminUser);
//        Pair<Post, String> postXmessage = this.postInteractor.addPost(new ArrayList<Comment>(), adminUser.getName(), List.of(image, video, textBox));
//        String postId = postXmessage.getKey().getId();
//        Pair<Post, String> postXMessage = this.postInteractor.showPostByAdminNameAndPostId(postId, "wrong name");
//        Assertions.assertEquals("no admin exists with this name", postXMessage.getValue());
//    }

    @Test
    void showPostByAdminButAdminNameDoesntExists() throws Exception{
        XmlMapper xmlMapper = new XmlMapper();
        AdminUser adminUser = new AdminUser("BBEX","password",new ArrayList<>());
        Post post = new Post(new ArrayList<Component>(),new ArrayList<Comment>(),adminUser.getName(),"");
        Pair response = addPost(post,xmlMapper);
        Post responsePost = xmlMapper.convertValue(response.getKey(),Post.class);
        HashMap<String,Object> args = new HashMap<>();
        MvcResult result = showPost(xmlMapper, args, post,"wrong name");
        response = xmlMapper.readValue(result.getResponse().getContentAsString(),Pair.class);
        String responseMessage = xmlMapper.convertValue(response.getValue(),String.class);
        Assertions.assertEquals("no admin exists with this name", responseMessage);
        Assertions.assertEquals(HttpStatus.NOT_FOUND.value(),result.getResponse().getStatus());
    }

//    @Test
//    void showCommentsOfPostByAdminNameAndPostId(){
//        AdminUser adminUser = new AdminUser("ali","password",new ArrayList<>());
//        this.dataBaseApi.addAdminUser(adminUser);
//        Pair<Post,String> postXmessage = this.postInteractor.addPost(new ArrayList<Comment>(),adminUser.getName(),List.of(image,video,textBox));
//        String postId = postXmessage.getKey().getId();
//        Pair<Post,String> postXMessage = this.postInteractor.showPostByAdminNameAndPostId(postId,adminUser.getName());
//        String postMessage = postXMessage.getValue();
//        adminUser = this.dataBaseApi.getAdminUserByName("ali");
//        Assertions.assertTrue(adminUser.getPostById(postId).isShowing());
//        Assertions.assertFalse(adminUser.getPostById(postId).isShowingComments());
//        Assertions.assertEquals("post with id " + postId + " is showing successfully",postMessage);
//        postMessage = this.postInteractor.showCommentsOfPostByPostIdAndAdminName(postId,adminUser.getName()).getValue();
//        adminUser = this.dataBaseApi.getAdminUserByName("ali");
//        Assertions.assertTrue(adminUser.getPostById(postId).isShowingComments());
//        Assertions.assertEquals("comments of post " + postId + " is showing successfully",postMessage);
//    }

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
        Assertions.assertEquals("comments of post " + responsePost.getId() + " is showing successfully", responseMessage);
        Assertions.assertEquals(HttpStatus.OK.value(),result.getResponse().getStatus());
    }




    private MvcResult showPost(XmlMapper xmlMapper, HashMap<String, Object> args, Post responsePost,String adminName) throws Exception {
        args.put("postId", responsePost.getId());
        args.put("adminName",adminName);
        String xml = xmlMapper.writeValueAsString(args);
        MvcResult result = mvc.perform(MockMvcRequestBuilders.post("/show-post").contentType(MediaType.APPLICATION_XML).content(xml)).andReturn();
        return result;
    }


    private Pair addPost(Post post,XmlMapper xmlMapper) throws Exception {
        String xml = xmlMapper.writeValueAsString(post);
        Post normalUser1 = xmlMapper.readValue(xml,Post.class);
        MvcResult result = mvc.perform(MockMvcRequestBuilders.post("/add-post").contentType(MediaType.APPLICATION_XML).content(xml)).andReturn();
        return xmlMapper.readValue(result.getResponse().getContentAsString(),Pair.class);
}
}
