package cn.xu.mongodb.crud;

import com.mongodb.BasicDBObject;
import org.bson.conversions.Bson;

import java.util.List;

public interface IUserDao {

    //添加用户
    public void insert(User user);

    //
    public void inserMany(List<User> userList);

    //删除用户
    public void delete(Integer id);

    //更新用户
    public void update(User user);

    //查找用户
    public User findById(Integer id);

    //查找所有的用户
    public List<User> findAll();

    //分页查询
    public List<User> pageQuery(Bson whereStr, Integer start, Integer size);

}
