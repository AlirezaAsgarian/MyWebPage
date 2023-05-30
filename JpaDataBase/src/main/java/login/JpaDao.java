package login;

import com.mysql.cj.log.Log;
import database.boundries.LoginDataBaseApi;
import jakarta.persistence.*;
import login.entities.AdminUser;
import login.entities.NormalUser;
import login.entities.TestEntity;
import org.hibernate.Session;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import util.Pair;
public class JpaDao implements LoginDataBaseApi,AutoCloseable {


    private EntityManagerFactory emfactory;
    private EntityManager entityManager;
    private Session session;

    public JpaDao() {
        emfactory = Persistence.createEntityManagerFactory("mywebpage_server");
        entityManager = emfactory.createEntityManager();
        session = entityManager.unwrap(Session.class);
    }



    public Boolean checkNormalUserIfExistWithThisNameAndReturn(NormalUser user) {
        return null;
    }



    public void addNormalUser(NormalUser user) {
        session.getTransaction().begin();
//        String s = "SET FOREIGN_KEY_CHECKS=0";
//        Query q = entityManager.createQuery(s);
//        q.executeUpdate();
        session.persist(user);
        session.getTransaction().commit();
    }

    public void addAdminUser(AdminUser user) {

    }



    @Override
    public Pair<Boolean, AdminUser> checkAdminUserIfExistWithThisNameAndReturn(String name) {
        return null;
    }

    @Override
    public Boolean checkAdminUserIfExistWithThisName(String name) {
        return null;
    }

    @Override
    public AdminUser getAdminUserByName(String name) {
        return null;
    }

    @Override
    public Pair<Boolean, NormalUser> checkNormalUserIfExistWithThisNameAndReturn(String name) {
        return null;
    }

    @Override
    public Boolean checkNormalUserIfExistWithThisName(String name) {
        return null;
    }

    @Override
    public boolean checkAdminUserIfAllowedWithThisName(String name) {
        return false;
    }

    public NormalUser getNormalUserByName(String name) {
        session.getTransaction().begin();
        NormalUser nu = session.get(NormalUser.class,name);
        session.getTransaction().commit();
        return nu;
    }

    @Override
    public void deleteNormalUserByName(String name) {
        session.getTransaction().begin();
        session.remove(session.get(NormalUser.class,name));
        session.getTransaction().commit();
    }

    @Override
    public void deleteAdminUserByName(String adminName) {

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

    @Override
    public void close() throws Exception {
       entityManager.close();
       emfactory.close();
    }
}
