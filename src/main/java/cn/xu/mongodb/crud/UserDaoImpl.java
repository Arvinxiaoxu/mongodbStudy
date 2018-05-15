package cn.xu.mongodb.crud;

import cn.xu.mongodb.crud.utils.DocumentUtls;
import cn.xu.mongodb.crud.utils.JSONUtils;
import cn.xu.mongodb.crud.utils.MongoDBUtils;
import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.ArrayList;
import java.util.List;

public class UserDaoImpl implements IUserDao {

    public void update(User user) {
        MongoClient client = MongoDBUtils.getMongoClient();
        MongoDatabase db = MongoDBUtils.getMongoDataBase(client);
        MongoCollection collection = db.getCollection("t_user");

        Document userDocument = DocumentUtls.object2Document(user);
        userDocument.remove("_id");
        Document whereStr = new Document();
        whereStr.append("_id", user.get_id().toString());
        Document updateStr = new Document("$set", userDocument);
        System.out.println(updateStr);
        collection.updateOne(whereStr, updateStr);
        MongoDBUtils.closeMongoClient(client, db);
    }

    public User findById(Integer id) {
        MongoClient client = MongoDBUtils.getMongoClient();
        MongoDatabase db = MongoDBUtils.getMongoDataBase(client);
        MongoCollection collection = db.getCollection("t_user");
        FindIterable iterable = collection.find(new Document("_id",id.toString()));
        //
        User user = getUserList(iterable).get(0);
        MongoDBUtils.closeMongoClient(client, db);
        return user;
    }

    private List<User> getUserList(FindIterable iterable){
        MongoCursor<Document> cursor = iterable.iterator();
        List<User> documentList = new ArrayList<User>();
        while(cursor.hasNext()){
            String jsonStr = cursor.next().toJson();
            User user = (User) JSONUtils.json2Object(jsonStr,User.class);
            documentList.add(user);
        }
        return documentList;
    }

    public List<User> findAll() {
        MongoClient client = MongoDBUtils.getMongoClient();
        MongoDatabase db = MongoDBUtils.getMongoDataBase(client);
        MongoCollection collection = db.getCollection("t_user");
        FindIterable<Document> iterable = collection.find();
        List<User> userList =  getUserList(iterable);

        return userList;
    }

    public void insert(User user) {
        MongoClient client = MongoDBUtils.getMongoClient();
        MongoDatabase db = MongoDBUtils.getMongoDataBase(client);
        MongoCollection collection = db.getCollection("t_user");
        Document document = DocumentUtls.object2Document(user);
        collection.insertOne(document);
        MongoDBUtils.closeMongoClient(client, db);

    }


    public void inserMany(List<User> userList) {
        MongoClient client = MongoDBUtils.getMongoClient();
        MongoDatabase db = MongoDBUtils.getMongoDataBase(client);
        MongoCollection collection = db.getCollection("t_user");
        //将用户list转换为 document 的list
        List<Document> userDocuments = DocumentUtls.getListDocuments(userList);
        collection.insertMany(userDocuments);
        MongoDBUtils.closeMongoClient(client, db);

    }

    public void delete(Integer id) {
        MongoClient client = MongoDBUtils.getMongoClient();
        MongoDatabase db = MongoDBUtils.getMongoDataBase(client);
        MongoCollection collection = db.getCollection("t_user");
        collection.deleteOne(new Document("_id",id.toString()));
        MongoDBUtils.closeMongoClient(client, db);
    }

    //分页查询
    public List<User> pageQuery(Bson whereStr, Integer start, Integer size){
        MongoClient client = MongoDBUtils.getMongoClient();
        MongoDatabase db = MongoDBUtils.getMongoDataBase(client);
        MongoCollection collection = db.getCollection("t_user");
        FindIterable<Document> iterable =null;
        if(whereStr !=null){
            iterable = collection.find(whereStr).limit(size).skip(start);
        }else{
            iterable = collection.find().limit(size).skip(start);
        }

        List<User> userList = getUserList(iterable);
        return userList;
    }
}
