package cn.xu.mongodb.crud.utils;

import cn.xu.mongodb.crud.User;
import net.sf.json.JSONObject;
import org.junit.Test;

public class JSONUtils {

    public static Object json2Object(String jsonStr,Class clazz){
        JSONObject jsonObject = JSONObject.fromObject(jsonStr);
        Object obj = JSONObject.toBean(jsonObject,clazz);
        return obj;
    }

    public static String object2Json(Object obj){
        Object result  = JSONObject.fromObject(obj);
        return  result.toString();
    }

    @Test
    public void test(){
        User user = new User(1,"张三",20);
        String str = JSONUtils.object2Json(user);
        System.out.println(str);
    }
}
