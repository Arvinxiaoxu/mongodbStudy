package cn.xu.mongodb.one2many;

import cn.xu.mongodb.crud.utils.MongoDBUtils;
import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import org.bson.Document;
import org.junit.Test;

public class MongoDBTest1 {

    //假设数据库的一对多的关系已经建立好了
    @Test
    public void test(){
        MongoClient client = MongoDBUtils.getMongoClient();
        MongoDatabase db = MongoDBUtils.getMongoDataBase(client);
        MongoCollection classColl = db.getCollection("t_class");


        FindIterable<Document> iterable = classColl.find(new Document("_id",1D));

        Document document = null;
        MongoCursor<Document> cursor = iterable.iterator();
        while(cursor.hasNext()){
            document = cursor.next();
            System.out.println(document.toJson());
        }
        String stuIds = document.get("stus").toString();
        stuIds = stuIds.substring(1,stuIds.length()-1);
        String[] ids = stuIds.split(",");
        MongoCollection stuColl = db.getCollection("t_stu");
        for(String id : ids){
            Double idValue = Double.valueOf(id);
            FindIterable<Document> iterable1 = stuColl.find(Filters.in("_id",idValue));
            print(iterable1);
        }
    }

    private void print(FindIterable<Document> iterable){
        MongoCursor<Document> cursor = iterable.iterator();
        Document document =null;
        while(cursor.hasNext()){
            document = cursor.next();
            System.out.println(document.toJson());
        }
    }
}
