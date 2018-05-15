package cn.xu.mongodb.demo1;





import com.mongodb.util.JSON;
import net.sf.json.JSONObject;
import net.sf.json.JSONString;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class JsonStrToMap {

    /**
     * json字符串转化为 map格式
     * @return
     */
    public static Map<String,Integer> jsonStrToMap(String jsonStr){
        /*
            Map<String,Integer> { "_id" : 1 , "city" : "北京"} 这个不会报错吗
         */
        Object parseObj = JSON.parse(jsonStr);//反序列化 把jons转变为对象
        Map<String,Integer> map = (HashMap<String,Integer>)parseObj;
        return map;
    }

    public static Object json2Object(String jsonStr,Class clazz){
        String str = "{ \"_id\" : 1, \"city\" : \"北京\" }";
        JSONObject jsonObject = JSONObject.fromObject(jsonStr);

        Object obj = JSONObject.toBean(jsonObject,clazz);
        return obj;
    }

}
