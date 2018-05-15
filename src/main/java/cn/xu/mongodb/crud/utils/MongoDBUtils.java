package cn.xu.mongodb.crud.utils;

import cn.xu.mongodb.demo1.MongoHelper;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoDatabase;

import java.util.Arrays;

public class MongoDBUtils {

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
    public static MongoClient getMongoClient() {
        MongoClient client = null;

        //设置你要连接的 服务器端口
        com.mongodb.ServerAddress serverAddress = new ServerAddress(
                ServerAddress, PORT);
        //设置你连接的数据库 用户名和密码  连接的凭证
        MongoCredential credential = MongoCredential.createScramSha1Credential(
                USER, DBName, PASSWORD
        );
        //创建本地客户点
       // Arrays.asList(credential);
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
    public static MongoDatabase getMongoDataBase(MongoClient client) {
        MongoDatabase database = null;
        database = client.getDatabase(DBName);
        return database;
    }

    /**
     * 关闭 客户点和数据库
     *
     * @param client
     * @param database
     */
    public static void  closeMongoClient(MongoClient client, MongoDatabase database) {
        if (database != null) {
            database = null;
        }
        if (client != null) {
            client.close();
        }
    }







}
