package cn.xu.mongodb.crud.utils;

import cn.xu.mongodb.crud.User;
import sun.rmi.server.InactiveGroupException;

import java.util.List;

public class PageUtils {
    private  Integer curPgae;
    private  Integer perPageSize = 2;
    private  Integer totalRecordSize;
    private  Integer totalPageSize;
    private List<User> userList;

    //计算数据库的start
    public Integer getStartIndex(Integer curP){
        int start = (curP-1)*perPageSize;
        return start;
    }


    public void setCurPgae(Integer curPgae) {
        this.curPgae = curPgae;
    }

    public void setPerPageSize(Integer perPageSize) {
        this.perPageSize = perPageSize;
    }

    public void setTotalRecordSize(Integer totalRecordSize) {
        this.totalRecordSize = totalRecordSize;
    }



    public  Integer getCurPgae() {
        return curPgae;
    }

    public  Integer getPerPageSize() {
        return perPageSize;
    }

    public  Integer getTotalRecordSize() {
        return totalRecordSize;
    }

    public List<User> getUserList() {
        return userList;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }

    public  Integer getTotalPageSize() {
        Integer tps = totalRecordSize /perPageSize;
        totalPageSize = ((totalRecordSize % perPageSize)==0) ?tps:tps+1;
        return totalPageSize;
    }
}
