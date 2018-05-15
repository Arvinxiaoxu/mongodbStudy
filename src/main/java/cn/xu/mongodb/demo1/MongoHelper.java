package cn.xu.mongodb.demo1;

import com.mongodb.*;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.ArrayList;
import java.util.List;

/**
 * mongodb的crud
 */
public class MongoHelper {

    static final String USER = "root";
    static final char[] PASSWORD = "123456".toCharArray();
    static final String ServerAddress = "127.0.0.1";
    static final String DBName = "db_mongo1";
    static final int PORT = 27017;


    /**
     * 得到一个本地的 mongo客户端
     *
     * @return
     */
    public MongoClient getMongoClient() {
        MongoClient client = null;

        //设置你要连接的 服务器端口
        com.mongodb.ServerAddress serverAddress = new ServerAddress(
                MongoHelper.ServerAddress, MongoHelper.PORT);
        //设置你连接的数据库 用户名和密码  连接的凭证
        MongoCredential credential = MongoCredential.createScramSha1Credential(
                MongoHelper.USER, MongoHelper.DBName, MongoHelper.PASSWORD
        );
        //创建本地客户点
        client = new MongoClient(serverAddress, credential,
                new MongoClientOptions.Builder().build());

        return client;
    }


    /**
     * 得到数据库的连接
     *
     * @param client
     * @return
     */
    public MongoDatabase getMongoDataBase(MongoClient client) {
        MongoDatabase database = null;
        database = client.getDatabase(MongoHelper.DBName);
        return database;
    }

    /**
     * 关闭 客户点和数据库
     *
     * @param client
     * @param database
     */
    public void closeMongoClient(MongoClient client, MongoDatabase database) {
        if (database != null) {
            database = null;
        }
        if (client != null) {
            client.close();
        }
    }

    public Area queryByID(MongoDatabase db, String table, Object id) {
        MongoCollection<Document> collection = db.getCollection(table);
        //  DBObject接口和BasicDBObject对象：表示一个具体的记录，
        // BasicDBObject实现了DBObject，是key-value的数据结构，用起来和HashMap是基本一致的。
        //并且该对象实现Bson接口
        BasicDBObject query = new BasicDBObject("_id", id);
        FindIterable<Document> iterable = collection.find(query);
        Area area = null;
        //cursor： 游标
        MongoCursor<Document> cursor = iterable.iterator();
        while (cursor.hasNext()) {
            Document document = cursor.next();
            String jsonString = document.toJson();
            System.out.println(jsonString);
            area = (Area)JsonStrToMap.json2Object(jsonString,Area.class);
        }
        return area;

    }

    /**
     * 根据一个doc来检索 当doc为空的时候检索全部
     *
     * @param db
     * @param table
     * @param doc
     * @return
     */
    public List<Area> queryByDoc(MongoDatabase db,
                                 String table, Bson doc) {
        MongoCollection<Document> collection = db.getCollection(table);
        FindIterable<Document> iterable = collection.find(doc);
        //返回游标
        MongoCursor<Document> cursor = iterable.iterator();
        List<Area> list = new ArrayList<Area>();
        Area area = null;
        while (cursor.hasNext()) {
            Document document = cursor.next();
            String str = document.toJson();
            //将查出来的json字符串 转换为 对象
            area = (Area)JsonStrToMap.json2Object(str,Area.class);
            list.add(area);
        }
        return list;
    }

    /**
     * 检索全部并返回迭代器
     *
     * @param db
     * @param table
     * @return
     */
    public List<Area> queryAll(MongoDatabase db, String table) {
        MongoCollection<Document> collection = db.getCollection(table);
        FindIterable<Document> iterable = collection.find();
        //返回游标
        MongoCursor<Document> cursor = iterable.iterator();
        List<Area> list = new ArrayList<Area>();
        Area area = null;
        while (cursor.hasNext()) {
            Document document = cursor.next();
            String str = document.toJson();
            area = (Area)JsonStrToMap.json2Object(str,Area.class);
            list.add(area);
        }
        return list;
    }

    /**
     * 遍历迭代器
     *
     * @param iterable
     */
    public void parintFindIterable(FindIterable<Document> iterable) {
        MongoCursor<Document> cursor = iterable.iterator();
        while (cursor.hasNext()) {
            Document doc = cursor.next();
            String str = doc.toJson();
            System.out.println(str);
        }
        //游标关闭
        cursor.close();
    }

    /**
     * 插入一条数据
     *
     * @param db
     * @param table
     * @param document
     * @return
     */
    public void insert(MongoDatabase db, String table, Document document) {
        MongoCollection collection = db.getCollection(table);
        collection.insertOne(document);
        //这个count 是 得到该集合的 数据记录的个数
        long count = collection.count();
        System.out.println("count:" + count);
        System.out.println("文档插入成功");
    }

    /**
     * 插入多条记录
     *
     * @param db
     * @param table
     * @param documents
     * @return
     */
    public boolean insertMany(MongoDatabase db, String table, List<Document> documents) {
        MongoCollection<Document> collection = db.getCollection(table);
        long perCount = collection.count();
        System.out.println("preCount:" + perCount);
        collection.insertMany(documents);
        //得到插入后的集合的数量
        long nowCount = collection.count();
        System.out.println("nowCount:" + nowCount);
        System.out.println("插入的数量：" + (nowCount - perCount));
        if ((nowCount - perCount) == documents.size()) {
            System.out.println("文档插入成功");
            return true;
        } else {
            System.out.println("文档插入失败");
            return false;
        }
    }

    /**
     * 删除一个
     *
     * @param db
     * @param table
     * @param document
     * @return
     */
    public boolean deleteOne(MongoDatabase db, String table, BasicDBObject document) {
        MongoCollection collection = db.getCollection(table);
        //BasicDBObject 是 bosn的子类
        DeleteResult deleteResult = collection.deleteOne(document);
        long deletedCount = deleteResult.getDeletedCount();
        System.out.println("删除的数量：" + deletedCount);
        if (deletedCount == 1) {
            System.out.println("文档删除成功");
            return true;
        } else {
            System.out.println("文档删除失败");
            return false;
        }
    }

    /**
     * 删除多条记录
     * @param db
     * @param table
     * @param document
     * @return
     */
    public boolean delete(MongoDatabase db, String table, BasicDBObject document) {
        MongoCollection collection = db.getCollection(table);
        //BasicDBObject 是 bosn的子类
        DeleteResult deleteResult = collection.deleteMany(document);
        long deletedCount = deleteResult.getDeletedCount();
        System.out.println("删除的数量：" + deletedCount);
        if (deletedCount >0) {
            System.out.println("文档删除成功");
            return true;
        } else {
            System.out.println("文档删除失败");
            return false;
        }
    }

    /**
     * 更新多个文档
     *
     * @param db
     * @param table
     * @param whereDoc
     * @param updateDoc
     * @return
     */
    public boolean update(MongoDatabase db, String table
            , BasicDBObject whereDoc, BasicDBObject updateDoc) {
        MongoCollection<Document> collection = db.getCollection(table);
        // whereDoc:更新的跳江  updateDoc：更新的内容
        UpdateResult updateResult = collection.updateMany(whereDoc, new Document("$set", updateDoc));
        long modifiedCount = updateResult.getModifiedCount();
        System.out.println("修改的数量:" + modifiedCount);
        if (modifiedCount > 0) {
            System.out.println("文档更新多个成功");
            return true;
        } else {
            System.out.println("文档更新失败");
            return false;
        }
    }

    /**
     * 跟新一个文档
     *
     * @param db
     * @param table
     * @param whereDoc
     * @param updateDoc
     * @return
     */
    public boolean updateOne(MongoDatabase db, String table
            , BasicDBObject whereDoc, BasicDBObject updateDoc) {
        //如果不写 泛型Document 会怎么样
        MongoCollection<Document> collection = db.getCollection(table);
        UpdateResult updateResult = collection.updateOne(whereDoc,
                new Document("$set", updateDoc));
        long modifiedCount = updateResult.getModifiedCount();
        if (modifiedCount == 1) {
            System.out.println("文档更新一个成功");
            return true;
        } else {
            System.out.println("文档更新失败");
            return false;
        }
    }


    /**
     * 创建一个 集合：即表
     *
     * @param db
     * @param table
     */
    public void createClo(MongoDatabase db, String table) {
        db.createCollection(table);
    }

    /**
     * 删除一个 集合
     *
     * @param db
     * @param table
     */
    public void dorpCol(MongoDatabase db, String table) {
        db.getCollection(table).drop();
        System.out.println("'删除结合成功");
    }





}
