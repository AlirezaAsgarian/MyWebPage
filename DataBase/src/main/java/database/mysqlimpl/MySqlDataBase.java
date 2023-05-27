package database.mysqlimpl;

import database.boundries.LoginDataBaseApi;
import database.boundries.PostDataBaseApi;
import login.entities.AdminUser;
import login.entities.NormalUser;
import login.entities.User;
import post.entity.Comment;
import post.boundries.Component;
import post.entity.Post;
import post.boundries.TestComponent;
import util.Pair;

import java.sql.*;
import java.util.*;


public class MySqlDataBase implements LoginDataBaseApi, PostDataBaseApi {

    static final String DB_URL = "jdbc:mysql://localhost/mywebpageDB";
    static final String USER = "mywebpage";
    static final String PASS = "amir4639$";
    private final Statement stm;
    QueryFormatter queryFormatter;

    public MySqlDataBase(QueryFormatter queryFormatter) {
        try {
            this.stm = createStatement();
            Class.forName("com.mysql.cj.jdbc.Driver");
            this.queryFormatter = queryFormatter;
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Boolean checkNormalUserIfExistWithThisNameAndReturn(NormalUser user) {
        try {
            Statement stm = createStatement();
            String query = queryFormatter.selectUserByColumnsNames( "NormalUser", List.of("username"), List.of(user.getName()));
            ResultSet rs = stm.executeQuery(query);
            if(rs.next()) {
                return true;
            }
            return false;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void addNormalUser(NormalUser user) {
        try {
            NormalUser normalUser = (NormalUser) user;
            Statement stm = createStatement();
            String userType = user.getClass().getSimpleName();
            String query;
            query = createInsertUserQuery(user, normalUser, userType);
            stm.execute(query);
        } catch (SQLException e) {
        }
    }

    private String createInsertUserQuery(User user, User normalUser, String userType) {
        String query;
        if (normalUser.getUserGraphics().imageComponent != null) {
            query = queryFormatter.createInsertIntoQuery(userType, List.of("username", "userpassword", "imagepath"),
                    List.of(user.getName(), user.getPassword(), user.getUserGraphics().imageComponent.toString()));
        } else {
            query = queryFormatter.createInsertIntoQuery(userType, List.of("username", "userpassword"), List.of(user.getName(), user.getPassword()));
        }
        return query;
    }

    @Override
    public void addAdminUser(User user) {
        try {
            AdminUser adminUser = (AdminUser) user;
            Statement stm = createStatement();
            String userType = user.getClass().getSimpleName();
            String query;
            query = createInsertUserQuery(user, adminUser, userType);
            stm.execute(query);
        } catch (SQLException e) {
        }
    }

    public ResultSet getResultSet() {
        try {
            Statement stm = createStatement();
            ResultSet resultSet = stm.executeQuery("SELECT * FROM alireza.user_table");
            return resultSet;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Statement createStatement() throws SQLException {
        Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
        return conn.createStatement();
    }



    @Override
    public Pair<Boolean, AdminUser> checkAdminUserIfExistWithThisNameAndReturn(String name) {
        try {
                Statement stm = createStatement();
                String query = queryFormatter.selectUserByJoinAndColumnsNames("LEFT JOIN", "AdminUser", "postTable", "username", "ownername",
                    List.of("username"), List.of(name));
                ResultSet rs = stm.executeQuery(query);
                AdminUser adminUser = getAdminUser(rs);
                if(adminUser == null) {
                    return new Pair<>(false,null);
                }
                return new Pair<>(true,adminUser);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
    }

    @Override
    public Boolean checkAdminUserIfExistWithThisName(String name) {
        return null;
    }

    @Override
    public AdminUser getAdminUserByName(String name) {
        try {
            String userType = "AdminUser";
            String query;
            query = queryFormatter.selectUserByJoinAndColumnsNames("LEFT JOIN", userType, "postTable", "username", "ownername",
                    List.of("username"), List.of(name));
            ResultSet rs = stm.executeQuery(query);
            return getAdminUser(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private AdminUser getAdminUser(ResultSet rs) throws SQLException {
        AdminUser adminUser = null;
        if(rs.next()) {
            String username = rs.getString("username");
            adminUser = new AdminUser(username, rs.getString("userpassword"),
                    new ArrayList<>());
            do {
                String postId = rs.getString("postId");
                if (postId != null) {
                    adminUser.addPost(getPost(postId, username, Boolean.valueOf(rs.getString("isShowing"))
                            ,Boolean.valueOf(rs.getString("isShowingcomments"))));
                }
            } while (rs.next());
        }
        return adminUser;
    }

    private Post getPost(String postId, String username, Boolean isShowing, Boolean isShowingComments) throws SQLException {
        if (postId == null) return null;
        Statement stm = createStatement();
        Post post = new Post(new ArrayList<>(), new ArrayList<>(), username, postId);
        post.setShowing(isShowing);
        post.setShowingComments(isShowingComments);
        String query = queryFormatter.selectUserByJoinAndColumnsNames("LEFT JOIN", "postTable", "commentTable", "postId", "commentPostId",
                List.of("postId"), List.of(postId));
        ResultSet rs = stm.executeQuery(query);
        while (rs.next()) {
            post.getComments().add(new Comment(rs.getString("textComponent"), rs.getString("ownername"), post.getId()));
        }
        query = queryFormatter.selectUserByJoinAndColumnsNames(" INNER JOIN", "postTable", "postComponents", "postId", "ownerPostId",
                List.of("postId"), List.of(postId));
        rs = stm.executeQuery(query);
        while (rs.next()){
            post.getComponents().add(new TestComponent(rs.getString("path"),rs.getString("type"),rs.getString("ownerPostId")));
        }
        return post;
    }

    @Override
    public Pair<Boolean, NormalUser> checkNormalUserIfExistWithThisNameAndReturn(String name) {
        NormalUser normalUser = this.getNormalUserByName(name);
        if(normalUser == null){
            return new Pair<>(false,null);
        }
        return new Pair<>(true,normalUser);
    }

    @Override
    public Boolean checkNormalUserIfExistWithThisName(String name) {
        return null;
    }

    @Override
    public boolean checkAdminUserIfAllowedWithThisName(String name) {
        try {
            Statement stm = createStatement();
            String query = queryFormatter.selectUserByColumnsNames("allowedAdminNamesTable",List.of("allowedAdminNames"),List.of(name));
            ResultSet rs = stm.executeQuery(query);
            if(rs.next()){
                return true;
            }
            return false;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public NormalUser getNormalUserByName(String name) {
        try {
            Statement stm = createStatement();
            String userType = "NormalUser";
            String query;
            query = queryFormatter.selectUserByJoinAndColumnsNames("LEFT JOIN",
                    userType, "commentTable", "username", "ownername",
                    List.of("username"), List.of(name));
            ResultSet rs = stm.executeQuery(query);
            return getNormalUser(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private  NormalUser getNormalUser(ResultSet rs) throws SQLException {
        NormalUser normalUser = null;
        if (rs.next()) {
            String username = rs.getString("username");
            normalUser = new NormalUser(rs.getString("username"), rs.getString("userpassword"),
                    new ArrayList<>());
            do {
                String ownername = rs.getString("ownername");
                if (ownername != null) {
                    normalUser.getComments().add(new Comment(rs.getString("textComponent"), ownername
                            , rs.getString("commentPostId")));
                }
            } while (rs.next());
        }
        return normalUser;
    }


    @Override
    public void addPost(Post post) {
        try {
            String query;
            query = queryFormatter.createInsertIntoQuery("postTable",
                    List.of("postId", "isShowing", "isShowingComments", "ownername"),
                    List.of(post.getId(), String.valueOf(post.isShowing()), String.valueOf(post.isShowingComments()), post.getOwnerName()));
            stm.execute(query);
          if(post.getComponents() != null) {
              addComponents(post);
          }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    private void addComponents(Post post) throws SQLException {
        for (Component c :
                post.getComponents()) {
            stm.execute(queryFormatter.createInsertIntoQuery("postComponents", List.of("ownerPostId", "path", "type"), List.of(post.getId(),
                    c.getPath(), c.getType())));
        }
    }

    @Override
    public void addComment(Comment comment) {
        try {
            Statement stm = createStatement();
            String query;
            if(comment.getTextBoxComponent() != null) {
                query = queryFormatter.createInsertIntoQuery("commentTable",
                        List.of("ownername", "commentPostId", "textComponent"),
                        List.of(comment.getOwnerName(), comment.getPostId(), comment.getTextBoxComponent().toString()));
            }else {
                query = queryFormatter.createInsertIntoQuery("commentTable",
                        List.of("ownername", "commentPostId"),
                        List.of(comment.getOwnerName(), comment.getPostId()));
            }
            stm.execute(query);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void setPostShowing(String bool,String postId) {
        try {
            Statement stm = createStatement();
            String query = queryFormatter.createUpdateTable("postTable",List.of("isShowing"),List.of(bool),List.of("postId"),List.of(postId));
            stm.execute(query);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void setShowingComment(String bool, String postId) {
        try {
            Statement stm = createStatement();
            String query = queryFormatter.createUpdateTable("postTable",List.of("isShowingComments"),List.of(bool),List.of("postId"),List.of(postId));
            stm.execute(query);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteNormalUserByName(String username) {
        try {
            String query = queryFormatter.deleteQuery("NormalUser",List.of("username"),List.of(username));
            stm.execute(query);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteAdminUserByName(String name) {
        try {
            disableForeignKeyCheck();
            String query = queryFormatter.deleteQuery("AdminUser",List.of("username"),List.of(name));
            stm.execute(query);
            enableForeignKeyCheck();
        } catch (SQLException e) {

        }
    }

    @Override
    public List<Post> getPostsByTitles(String searchTitle) {
        return null;
    }

    @Override
    public List<Post> getPostsByDates(int i) {
        return null;
    }

    private void enableForeignKeyCheck() throws SQLException {
        stm.execute("SET FOREIGN_KEY_CHECKS=1");
    }

    private void disableForeignKeyCheck() throws SQLException {
        stm.execute("SET FOREIGN_KEY_CHECKS=0");
    }

    @Override
    public void addRequestForFollowing(String normalName, String adminName) {

    }

    @Override
    public void acceptFollowingRequest(String adminName, String normalName) {

    }

    @Override
    public void rejectFollowingRequest(String adminName, String normalName) {

    }

    @Override
    public void removeResponseForNormalUser(String normalName, String adminName, String type) {

    }
}
