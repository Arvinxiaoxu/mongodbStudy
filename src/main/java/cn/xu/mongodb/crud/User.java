package cn.xu.mongodb.crud;

public class User {
    private Integer _id;
    private String  name;
    private Integer age;

    public User() {
        super();
    }

    public User(Integer _id, String name, Integer age) {
        this._id = _id;
        this.name = name;
        this.age = age;
    }

    public Integer get_id() {

        return _id;
    }

    public void set_id(Integer _id) {
        this._id = _id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "User{" +
                "_id=" + _id +
                ", name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
