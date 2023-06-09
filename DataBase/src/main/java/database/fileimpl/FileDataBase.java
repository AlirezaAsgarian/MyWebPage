package database.fileimpl;

import database.boundries.LoginDataBaseApi;
import database.boundries.PostDataBaseApi;
import login.entities.*;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import post.entity.Comment;
import post.entity.Post;
import util.Pair;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class FileDataBase implements LoginDataBaseApi, PostDataBaseApi {

    List<User> users;
    List<Post> posts;
    List<Comment> comments;

    public FileDataBase(List<User> users,List<Post> posts,List<Comment> comments)  {
        this.posts = posts;
        this.comments = comments;
        this.users = (List<User>) readFromGson(getGsonFile("users.txt"),new TypeToken<List<User>>() {}.getType());
        if(this.users == null)
            this.users = new ArrayList<>(users);
        else
            this.users.addAll(users);
    }

    public Object readFromGson(File file, Type REVIEW_TYPE){
        Gson gson = new Gson();
        JsonReader jsonReader = getGsonReader(file);
        return gson.fromJson(jsonReader,REVIEW_TYPE);
    }

    public JsonReader getGsonReader(File file){
        try {
            JsonReader reader = new JsonReader(new FileReader(file));
            return reader;
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private File getGsonFile(String filename) {
        try {
            File file = new File(filename);
            if (file.exists()) {
                return file;
            } else {
                file.createNewFile();
                return file;
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Boolean checkNormalUserIfExistWithThisNameAndReturn(User user) {
        for (User u:
             this.users) {
            if(u.checkIfNamesAreIdentical(user) && u instanceof NormalUser){
                 return true;
            }
        }
        return false;
    }

    @Override
    public void addNormalUser(User user) {
        this.users.add(user);
    }

    @Override
    public void addAdminUser(AdminUser  user) {
        this.users.add(user);
    }





    @Override
    public Pair<Boolean, AdminUser> checkAdminUserIfExistWithThisNameAndReturn(String user) {
        for (User u:
                this.users) {
            if(u.checkIfNamesAreIdentical(user) && u instanceof AdminUser au){
                return new Pair<>(true,au);
            }
        }
        return new Pair<>(false,null);
    }

    @Override
    public Boolean checkAdminUserIfExistWithThisName(String name) {
        return checkAdminUserIfExistWithThisNameAndReturn(name).getKey();
    }

    @Override
    public Pair<Boolean, NormalUser> checkNormalUserIfExistWithThisNameAndReturn(String name) {
        for (User u:
                this.users) {
            if(u.checkIfNamesAreIdentical(name) && u instanceof NormalUser nu){
                return new Pair<Boolean,NormalUser>(true,nu);
            }
        }
        return new Pair<>(false,null);
    }

    @Override
    public Boolean checkNormalUserIfExistWithThisName(String name) {
        return checkNormalUserIfExistWithThisNameAndReturn(name).getKey();
    }

    @Override
    public boolean checkAdminUserIfAllowedWithThisName(String name) {
        List<String> allowedNames = (List<String>)readFromGson(getGsonFile("allowedNames.txt"),new TypeToken<List<String>>() {}.getType());
        if(allowedNames.contains(name)){
            return true;
        }else {
            return false;
        }
    }

    @Override
    public AdminUser getAdminUserByName(String name) {
        for (User u:
                this.users) {
            if(u.checkIfNamesAreIdentical(name) && u instanceof AdminUser au){
                return au;
            }
        }
        return null;
    }

    private User findUserByName(User user) {
        for (User u:
                this.users) {
            if(u.checkIfNamesAreIdentical(user)){
                return u;
            }
        }
        throw new RuntimeException();
    }

    @Override
    public NormalUser getNormalUserByName(String name) {
        for (User u:
                this.users) {
            if(u.checkIfNamesAreIdentical(name) && u instanceof NormalUser nu){
                return nu;
            }
        }
        return null;
    }

    @Override
    public void addPost(Post post) {
         this.posts.add(post);
         AdminUser adminUser = this.getAdminUserByName(post.getOwnerName());
         adminUser.getPosts().add(post);
    }

    @Override
    public void addComment(Comment comment) {
      this.comments.add(comment);
    }

    @Override
    public void setPostShowing(String bool, String postId) {
        Post post = getPostById(postId);
        if(bool.equals("true")) {
            post.setShowing(true);
        }else {
            post.setShowing(false);
        }
    }

    private Post getPostById(String postId) {
        for (Post post:
             this.posts) {
         if(post.getId().equals(postId)){
             return post;
         }
        }
        return null;
    }

    @Override
    public void setShowingComment(String bool, String id) {
        Post post = getPostById(id);
        if(bool.equals("true")) {
            post.setShowingComments(true);
        }else {
            post.setShowingComments(false);
        }
    }

    @Override
    public void deleteNormalUserByName(String name) {
        for (int i = users.size() - 1; i >= 0; --i) {
            User us = users.get(i);
            if(us instanceof NormalUser && us.getName().equals(name)){
                this.users.remove(us);
            }
        }
    }

    @Override
    public void deleteAdminUserByName(String bbex) {

    }

    @Override
    public List<Post> getPostsByTitles(String searchTitle) {
        return posts.stream().filter(p -> p.getTitle().startsWith(searchTitle)).toList();
    }

    @Override
    public List<Post> getPostsByDates(int i) {
        if(i > posts.size()){
            return new ArrayList<>();
        }else {
            return posts.stream().sorted((e1,e2) -> e2.getDate().compareTo(e1.getDate())).toList().subList(0,i);
        }
    }

    @Override
    public void addRequestForFollowing(String normalName, String adminName) {
          NormalUser normalUser = getNormalUserByName(normalName);
          AdminUser adminUser = getAdminUserByName(adminName);
          adminUser.getFollowingRequests().add(new FollowingRequest(normalUser.getName(),adminUser.getName()));
    }

    @Override
    public void acceptFollowingRequest(String adminName, String normalName) {
        NormalUser normalUser = getNormalUserByName(normalName);
        AdminUser adminUser = getAdminUserByName(adminName);
        adminUser.getFollowers().add(normalName);
        normalUser.getFollowing().add(adminName);
        normalUser.getResponses().add(new FollowingResponse(true,adminName,normalName));
    }

    @Override
    public void rejectFollowingRequest(String adminName, String normalName) {
        NormalUser normalUser = getNormalUserByName(normalName);
        normalUser.getResponses().add(new FollowingResponse(false,adminName,normalName));
    }

    @Override
    public void removeResponseForNormalUser(String normalName, String adminName, String type) {
        NormalUser normalUser = getNormalUserByName(normalName);
        UserResponse ur = normalUser.getResponseByTypeAndName(adminName,type);
        normalUser.getResponses().remove(ur);
    }
}
