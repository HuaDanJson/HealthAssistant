package com.demo.bs.demoapp2.DBBean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by Json on 2017/5/9.
 */
@Entity // 标识实体类，greenDAO会映射成sqlite的一个表，表名为实体类名的大写形式
public class DBLuRuShuJvBean {
    @Id(autoincrement = false)
    public long creatTimeAsId;//录入的具体数据的时间作为ID
    @Property(nameInDb = "DBLuRuShuJvBean")
    public String dataKind;//录入的数据的种类
    public String rusultData;//录入的具体数据
    public String HealState ;  //1代表健康 2 代表不健康 3代表亚健康
    @Generated(hash = 297257428)
    public DBLuRuShuJvBean(long creatTimeAsId, String dataKind, String rusultData,
            String HealState) {
        this.creatTimeAsId = creatTimeAsId;
        this.dataKind = dataKind;
        this.rusultData = rusultData;
        this.HealState = HealState;
    }
    @Generated(hash = 1294976464)
    public DBLuRuShuJvBean() {
    }
    public long getCreatTimeAsId() {
        return this.creatTimeAsId;
    }
    public void setCreatTimeAsId(long creatTimeAsId) {
        this.creatTimeAsId = creatTimeAsId;
    }
    public String getDataKind() {
        return this.dataKind;
    }
    public void setDataKind(String dataKind) {
        this.dataKind = dataKind;
    }
    public String getRusultData() {
        return this.rusultData;
    }
    public void setRusultData(String rusultData) {
        this.rusultData = rusultData;
    }
    public String getHealState() {
        return this.HealState;
    }
    public void setHealState(String HealState) {
        this.HealState = HealState;
    }

    @Override
    public String toString() {
        return "DBLuRuShuJvBean{" +
                "creatTimeAsId=" + creatTimeAsId +
                ", dataKind='" + dataKind + '\'' +
                ", rusultData='" + rusultData + '\'' +
                ", HealState='" + HealState + '\'' +
                '}';
    }
}
