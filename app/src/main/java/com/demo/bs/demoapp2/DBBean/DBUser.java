package com.demo.bs.demoapp2.DBBean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class DBUser {
    @Id(autoincrement = false)
    public String userNameAsId;
    @Property(nameInDb = "DBUser")
    public String password;
    @Generated(hash = 1800754562)
    public DBUser(String userNameAsId, String password) {
        this.userNameAsId = userNameAsId;
        this.password = password;
    }
    @Generated(hash = 138933025)
    public DBUser() {
    }
    public String getUserNameAsId() {
        return this.userNameAsId;
    }
    public void setUserNameAsId(String userNameAsId) {
        this.userNameAsId = userNameAsId;
    }
    public String getPassword() {
        return this.password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "DBUser{" +
                "userNameAsId='" + userNameAsId + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
