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
import java.util.Map;

@SpringBootTest(classes = SpringUiApplication.class)
public class HidePostTest extends PostTestBase{



    @Test
    void hidePost() throws Exception {
        XmlMapper xmlMapper = new XmlMapper();
        AdminUser adminUser = new AdminUser("BBEX","password",new ArrayList<>());
        Post post = new Post(new ArrayList<Component>(),new ArrayList<Comment>(),adminUser.getName(),"");
        Post post1 = showPostAndGetResponsePost(xmlMapper, adminUser, post);
        HashMap args = new HashMap(Map.of("adminName","BBEX","postId",post1.getId()));
        String xml = xmlMapper.writeValueAsString(args);
        Assertions.assertTrue(post1.isShowing());
        MvcResult result = mvc.perform(MockMvcRequestBuilders.post("/hide-post").contentType(MediaType.APPLICATION_XML).content(xml)).andReturn();
        Pair response = xmlMapper.readValue(result.getResponse().getContentAsString(),Pair.class);
        post1 = xmlMapper.convertValue(response.getKey(),Post.class);
        Assertions.assertFalse(post1.isShowing());
        String postMessage = xmlMapper.convertValue(response.getValue(),String.class);
        Assertions.assertEquals( postMessage,"post with id " + post1.getId() + " hided successfully");
        result = mvc.perform(MockMvcRequestBuilders.post("/hide-post").contentType(MediaType.APPLICATION_XML).content(xml)).andReturn();
        response = xmlMapper.readValue(result.getResponse().getContentAsString(),Pair.class);
        postMessage = xmlMapper.convertValue(response.getValue(),String.class);
        Assertions.assertEquals("post has already hided" , postMessage);
        Assertions.assertEquals(result.getResponse().getStatus(), HttpStatus.CONFLICT.value());
    }

        @Test
        void hidePostWithItsCommentssByAdminIdAndPostId() throws Exception {
            XmlMapper xmlMapper = new XmlMapper();
            AdminUser adminUser = new AdminUser("BBEX","password",new ArrayList<>());
            Post post = new Post(new ArrayList<Component>(),new ArrayList<Comment>(),adminUser.getName(),"");
            Post post1 = showPostAndGetResponsePost(xmlMapper, adminUser, post);
            HashMap args = new HashMap(Map.of("adminName","BBEX","postId",post1.getId()));
            String xml = xmlMapper.writeValueAsString(args);
            MvcResult result = showCommentsAndGetResults(xml);
            Pair response = xmlMapper.readValue(result.getResponse().getContentAsString(),Pair.class);
            post1 = xmlMapper.convertValue(response.getKey(),Post.class);
            Assertions.assertTrue(post1.isShowingComments());
            result = mvc.perform(MockMvcRequestBuilders.post("/hide-post").contentType(MediaType.APPLICATION_XML).content(xml)).andReturn();
            response = xmlMapper.readValue(result.getResponse().getContentAsString(),Pair.class);
            post1 = xmlMapper.convertValue(response.getKey(),Post.class);
            Assertions.assertFalse(post1.isShowing());
            String postMessage = xmlMapper.convertValue(response.getValue(),String.class);
            Assertions.assertEquals("comments closed successfully post with id "
                    + post1.getId() + " hided successfully",postMessage);
        }








}
