package cn.xu.mongodb.demo1;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.*;
import com.mongodb.client.model.Filters;
import org.bson.Document;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class MongoDBTest1 {


    /**
     * 数据库连接使用没有用户名和密码的数据库
     */
    @Test
    public void testMongoDB_NoPassword(){
        //连接 mongodb 服务
        MongoClient mongoClient = new MongoClient("localhost",27017);
        //连接到数据库
        MongoDatabase mongoDatabase = mongoClient.getDatabase("db_demo1");
        System.out.println("connect to database successfully");
        System.out.println(mongoDatabase);
    }

    /**
     * 数据连接 使用了不推荐使用的方法
     */
    @Test
    public void testMongoDB_havaPassword2(){
        //连接Mongodb服务， 如果是远程连接可以替换hosthost为服务器所在的id地址
        //ServerAddress（）两个参数分别为服务器地址和端口
        ServerAddress serverAddress = new ServerAddress("localhost",27017);
        List<ServerAddress> addressList = new ArrayList<ServerAddress>();
        addressList.add(serverAddress);
        //MongoCredential.createScramSha1Credential()
        //连接要用的 用户名 数据库名 和密码 Credential：证书 凭证  Scram：急停
        MongoCredential credential = MongoCredential.createScramSha1Credential(
                "root","db_mongo1","123456".toCharArray());
        List<MongoCredential> credentialList = new ArrayList<MongoCredential>();
        credentialList.add(credential);

        //通过连接认证获取MongoDB连接
        MongoClient mongoClient = new MongoClient(addressList,credentialList);
        //连接到数据库
        MongoDatabase mongoDatabase = mongoClient.getDatabase("db_mongo1");
        System.out.println("连接数据成功");
        System.out.println(mongoDatabase);

    }

    /**
     * 数据库连接有 用户名和密码  使用有效的方法
     */
    @Test
    public void testMongoDB_havaPassword3(){
        //连接Mongodb服务， 如果是远程连接可以替换hosthost为服务器所在的id地址
        //ServerAddress（）两个参数分别为服务器地址和端口
        ServerAddress serverAddress = new ServerAddress("localhost",27017);

        //MongoCredential.createScramSha1Credential()
        //连接要用的 用户名 数据库名 和密码 Credential：证书 凭证  Scram：急停
        MongoCredential credential = MongoCredential.createScramSha1Credential(
                "root","db_mongo1","123456".toCharArray());

       // MongoClientOptions
        //通过连接认证获取MongoDB连接
        MongoClient mongoClient = new MongoClient(
                serverAddress,credential, (new MongoClientOptions.Builder()).build());
        //连接到数据库
        MongoDatabase mongoDatabase = mongoClient.getDatabase("db_mongo1");
        System.out.println("连接数据成功");
        System.out.println(mongoDatabase);

    }

    /**
     * 测试查询数据 然而并没有封装成bean
     */
    @Test
    public void test_getCollection(){
        //你要连接服务器的端口
        ServerAddress serverAddress = new ServerAddress("localhost",27017);
        //设置要连接上的数据库的 用户名和密码
        MongoCredential mongoCredential =MongoCredential.createScramSha1Credential(
                "root","db_mongo1","123456".toCharArray()
        );
        //new一个本地的mongodb的客户端
        MongoClient mongoClient = new MongoClient(
                serverAddress,mongoCredential,new MongoClientOptions.Builder().build());
        //得到这个数据库
        MongoDatabase mongoDatabase = mongoClient.getDatabase("db_mongo1");
        MongoCollection<Document> mongoCollection = mongoDatabase.getCollection("demo1");
        FindIterable<Document> findIterable = mongoCollection.find();
        MongoCursor<Document> mongoCursor = findIterable.iterator();
        while(mongoCursor.hasNext()){
            Document document = mongoCursor.next();
            String value = document.toJson();
            System.out.println(value);
        }
        //System.out.println(mongoCollection);

    }

    /**
     * 测试向数据库中插入数据
     */
    @Test
    public void test_insertMany(){
        //你要连接服务器的端口
        ServerAddress serverAddress = new ServerAddress("localhost",27017);
        //设置要连接上的数据库的 用户名和密码
        MongoCredential mongoCredential =MongoCredential.createScramSha1Credential(
                "root","db_mongo1","123456".toCharArray()
        );
        //new一个本地的mongodb的客户端
        MongoClient mongoClient = new MongoClient(
                serverAddress,mongoCredential,new MongoClientOptions.Builder().build());
        //得到这个数据库
        MongoDatabase mongoDatabase = mongoClient.getDatabase("db_mongo1");
        /*
        插入文档
        1. 创建文档 org.bson.Document 参数为key-value的格式
        2. 创建文档集合List<Document>
        3. 将文档集合插入数据库集合中 mongoCollection.insertMany(List<Document>)
        插入单个文档可以用 mongoCollection.insertOne(Document)
         */
        //可以通过构造器的添加一个 key--value的document
        Document document = new Document();
        document.append("name","张三");
        document.append("age",25);
        document.append("gender","男");

        Document document2 = new Document();
        document2.append("name","王五");
        document2.append("age",25);
        document2.append("gender","女");

        List<Document> documentList = new ArrayList<Document>();
        documentList.add(document);
        documentList.add(document2);

        //得到这个表 并向该表中添加数据
        MongoCollection<Document> mongoCollection = mongoDatabase.getCollection("demo1");
        mongoCollection.insertMany(documentList);
        System.out.println("多个文档添加成功");
    }

    /**
     * 测试更新数据库
     */
    @Test
    public void test_update(){
        //你要连接服务器的端口
        ServerAddress serverAddress = new ServerAddress("localhost",27017);
        //设置要连接上的数据库的 用户名和密码
        MongoCredential mongoCredential =MongoCredential.createScramSha1Credential(
                "root","db_mongo1","123456".toCharArray()
        );
        //new一个本地的mongodb的客户端
        MongoClient mongoClient = new MongoClient(
                serverAddress,mongoCredential,new MongoClientOptions.Builder().build());
        //得到这个数据库
        MongoDatabase mongoDatabase = mongoClient.getDatabase("db_mongo1");
        //得到到该 文档集合
        MongoCollection mongoCollection = mongoDatabase.getCollection("demo1");

        //updateMany 可以更新多个文档
        //更新文档
        mongoCollection.updateOne(Filters.eq("name","张三")
                ,new Document("$set",new Document("age",33))
                );
        System.out.println("更新成功");
    }

    /**
     * 数据库中的数据的删除
     */
    @Test
    public void test_delete(){
        //你要连接服务器的端口
        ServerAddress serverAddress = new ServerAddress("localhost",27017);
        //设置要连接上的数据库的 用户名和密码
        MongoCredential mongoCredential =MongoCredential.createScramSha1Credential(
                "root","db_mongo1","123456".toCharArray()
        );
        //new一个本地的mongodb的客户端
        MongoClient mongoClient = new MongoClient(
                serverAddress,mongoCredential,new MongoClientOptions.Builder().build());
        //得到这个数据库
        MongoDatabase mongoDatabase = mongoClient.getDatabase("db_mongo1");
        //得到到该 文档集合
        MongoCollection collection = mongoDatabase.getCollection("demo1");

        //开始删除文档
        collection.deleteOne(Filters.eq("age",33));
        System.out.println("删除成功");
    }








}
