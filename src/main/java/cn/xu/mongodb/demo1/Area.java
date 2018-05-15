package cn.xu.mongodb.demo1;

public class Area {
    private Integer _id;
    private String city;

    public Area(Integer _id, String city) {
        this._id = _id;
        this.city = city;
    }

    public Area() {
        super();
    }

    public Integer get_id() {
        return _id;
    }

    public void set_id(Integer _id) {
        this._id = _id;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Override
    public String toString() {
        return "Area{" +
                "_id=" + _id +
                ", city='" + city + '\'' +
                '}';
    }
}
