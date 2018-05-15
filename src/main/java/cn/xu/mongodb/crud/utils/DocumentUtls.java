package cn.xu.mongodb.crud.utils;

import cn.xu.mongodb.crud.User;
import org.bson.Document;
import org.junit.Test;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class DocumentUtls {


    /**
     * 将一个对象变为 document对象
     *
     * @param obj
     * @return
     */
    public static Document object2Document(Object obj) {
        Document document = new Document();
        try {
            Class clazz = obj.getClass();
            Field[] fields = clazz.getDeclaredFields();
            for (Field field : fields) {
                //设置字段的允许范围的权限
                field.setAccessible(true);
                String name = field.getName();
                String nameFirst = name.substring(0, 1).toUpperCase();
                String methodName = "get" + nameFirst + name.substring(1);
                Method method = clazz.getDeclaredMethod(methodName, null);
                String value = method.invoke(obj, null).toString();
                document.append(name, value);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return document;
    }

    public static List<Document> getListDocuments(List<User> objList) {
        List<Document> documentList = new ArrayList<Document>();
        for (User user : objList) {
            documentList.add(object2Document(user));
        }
        return documentList;
    }

    @Test
    public void test_object2Document() {
        DocumentUtls.object2Document(new User());
    }

    @Test
    public void test_getListDocuments() {
        List<User> userList = new ArrayList<User>();
        userList.add(new User(1, "a1", 20));
        userList.add(new User(2, "a1", 20));
        userList.add(new User(3, "a1", 20));
        System.out.println(getListDocuments(userList));
    }
}
