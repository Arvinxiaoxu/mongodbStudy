package cn.xu.mongodb.crud;

import com.mongodb.client.model.Filters;
import org.bson.conversions.Bson;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.gte;

public class TestUseDao {


    @Test
    public void test_insert(){
        IUserDao userDao = new UserDaoImpl();
        userDao.insert(new User(2,"a2",21));
    }

    @Test
    public void test_inserMany(){
        IUserDao userDao = new UserDaoImpl();
        List<User> userList = new ArrayList<User>();
        userList.add(new User(6, "a6", 25));
        userList.add(new User(7, "a7", 18));
        userList.add(new User(8, "a8", 20));
        userDao.inserMany(userList);
    }

    @Test
    public void test_findById(){
        IUserDao userDao = new UserDaoImpl();
        User user = userDao.findById(1);
        System.out.println(user);
    }

    @Test
    public void test_update(){
        IUserDao userDao = new UserDaoImpl();
        User user = new User(3, "a66", 25);
        userDao.update(user);
    }

    @Test
    public void test_pageQuery(){
        IUserDao userDao = new UserDaoImpl();
      //  Bson whereStr = Filters.gte("age","20");
        Bson whereStr = Filters.and(Filters.gte("age","20"),Filters.lte("age","28"));
        List<User> userList = userDao.pageQuery(whereStr,0,2);
        System.out.println(userList);
    }




}
