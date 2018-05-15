package cn.xu.mongodb.demo1;

import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.junit.Test;

import java.util.List;

public class TestMongoHelper {

    /**
     * 测试数据库的连接
     */
    @Test
    public void testConnect() {
        MongoHelper helper = new MongoHelper();
        MongoClient client = helper.getMongoClient();
        MongoDatabase database = helper.getMongoDataBase(client);
        System.out.println(database);
    }


    /**
     * 测试创建集合
     */
    @Test
    public void test_crateCol(){
        MongoHelper helper = new MongoHelper();
        MongoClient client = helper.getMongoClient();
        MongoDatabase db = helper.getMongoDataBase(client);
        helper.createClo(db,"t_areaMap");
    }

    /**
     * 测试一条数据的插入
     */
    @Test
    public void test_insert(){
        MongoHelper helper = new MongoHelper();
        MongoClient client = helper.getMongoClient();
        MongoDatabase db = helper.getMongoDataBase(client);
        String table = "t_areaMap";
        Document doc = new Document();
        doc.append("_id",2);
        doc.append("city","上海");
        helper.insert(db,table, doc);
    }

    /**
     * 根据id来查找数据并返回 查找的对象
     */
    @Test
    public void test_queryByID(){
        MongoHelper helper = new MongoHelper();
        MongoClient client = helper.getMongoClient();
        MongoDatabase db = helper.getMongoDataBase(client);
        String table = "t_areaMap";
        //应该返回该记录
        Area area = helper.queryByID(db,table,1);
        System.out.println(area);
    }

    /**
     * 检索doc,可以根据doc(key,value)来查找,当doc是空的时候，检索全部
     */
    @Test
    public void test_queryByDoc(){
        MongoHelper helper = new MongoHelper();
        MongoClient client = helper.getMongoClient();
        MongoDatabase db = helper.getMongoDataBase(client);
        String table = "t_areaMap";

        BasicDBObject doc = new BasicDBObject();
       // doc.append("_id",1);
       // doc.append("city","北京");
        List<Area> areaList = helper.queryByDoc(db,table,doc);
        System.out.println(areaList);
    }

    /**
     * 查询全部
     */
    @Test
    public void test_queryAll(){
        MongoHelper helper = new MongoHelper();
        MongoClient client = helper.getMongoClient();
        MongoDatabase db = helper.getMongoDataBase(client);
        String table = "t_areaMap";
        List<Area> reslut = helper.queryAll(db,table);
        System.out.println(reslut);
    }

    ////删除doc 的全部信息，当doc 是空，则删除全部

    /**
     * 删除多条记录 和删除一个类似
     */
    @Test
    public void test_delete(){
        MongoHelper helper = new MongoHelper();
        MongoClient client = helper.getMongoClient();
        MongoDatabase db = helper.getMongoDataBase(client);
        String table = "t_areaMap";
        BasicDBObject doc = new BasicDBObject();
        doc.append("_id",3);
        helper.delete(db,table,doc);
    }


    /**
     *更新记录
     */
    @Test
    public void update(){
        MongoHelper helper = new MongoHelper();
        MongoClient client = helper.getMongoClient();
        MongoDatabase db = helper.getMongoDataBase(client);
        String table = "t_areaMap";
        BasicDBObject whereDoc = new BasicDBObject();
        whereDoc.append("_id",1);
        BasicDBObject updateDoc = new BasicDBObject();
        updateDoc.append("city","上海");
        helper.update(db,table,whereDoc,updateDoc);
    }





}
