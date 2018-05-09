package com.demo.bs.demoapp2.DBBean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by Json on 2017/5/9.
 */
@Entity // 标识实体类，greenDAO会映射成sqlite的一个表，表名为实体类名的大写形式
public class DBUserInfoBean {
    @Id(autoincrement = false)
    public long userId;
    @Property(nameInDb = "DBUserInfoBean")
    public String name;
    public String nianLing;
    public String sex;
    public String shenGao;
    public String tiZhong;
    public String shouSuoYa;
    public String shuZhangYa;
    public String kongFuXueTang;
    public String FanHouXueTang;
    public String guoMinYaoWu;
    public String liShiJiBing;

    @Generated(hash = 160639814)
    public DBUserInfoBean(long userId, String name, String nianLing, String sex,
                          String shenGao, String tiZhong, String shouSuoYa, String shuZhangYa,
                          String kongFuXueTang, String FanHouXueTang, String guoMinYaoWu,
                          String liShiJiBing) {
        this.userId = userId;
        this.name = name;
        this.nianLing = nianLing;
        this.sex = sex;
        this.shenGao = shenGao;
        this.tiZhong = tiZhong;
        this.shouSuoYa = shouSuoYa;
        this.shuZhangYa = shuZhangYa;
        this.kongFuXueTang = kongFuXueTang;
        this.FanHouXueTang = FanHouXueTang;
        this.guoMinYaoWu = guoMinYaoWu;
        this.liShiJiBing = liShiJiBing;
    }

    @Generated(hash = 911723735)
    public DBUserInfoBean() {
    }

    public long getUserId() {
        return this.userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNianLing() {
        return this.nianLing;
    }

    public void setNianLing(String nianLing) {
        this.nianLing = nianLing;
    }

    public String getSex() {
        return this.sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getShenGao() {
        return this.shenGao;
    }

    public void setShenGao(String shenGao) {
        this.shenGao = shenGao;
    }

    public String getTiZhong() {
        return this.tiZhong;
    }

    public void setTiZhong(String tiZhong) {
        this.tiZhong = tiZhong;
    }

    public String getShouSuoYa() {
        return this.shouSuoYa;
    }

    public void setShouSuoYa(String shouSuoYa) {
        this.shouSuoYa = shouSuoYa;
    }

    public String getShuZhangYa() {
        return this.shuZhangYa;
    }

    public void setShuZhangYa(String shuZhangYa) {
        this.shuZhangYa = shuZhangYa;
    }

    public String getKongFuXueTang() {
        return this.kongFuXueTang;
    }

    public void setKongFuXueTang(String kongFuXueTang) {
        this.kongFuXueTang = kongFuXueTang;
    }

    public String getFanHouXueTang() {
        return this.FanHouXueTang;
    }

    public void setFanHouXueTang(String FanHouXueTang) {
        this.FanHouXueTang = FanHouXueTang;
    }

    public String getGuoMinYaoWu() {
        return this.guoMinYaoWu;
    }

    public void setGuoMinYaoWu(String guoMinYaoWu) {
        this.guoMinYaoWu = guoMinYaoWu;
    }

    public String getLiShiJiBing() {
        return this.liShiJiBing;
    }

    public void setLiShiJiBing(String liShiJiBing) {
        this.liShiJiBing = liShiJiBing;
    }

    @Override
    public String toString() {
        return "DBUserInfoBean{" +
                "userId=" + userId +
                ", name='" + name + '\'' +
                ", nianLing='" + nianLing + '\'' +
                ", sex='" + sex + '\'' +
                ", shenGao='" + shenGao + '\'' +
                ", tiZhong='" + tiZhong + '\'' +
                ", shouSuoYa='" + shouSuoYa + '\'' +
                ", shuZhangYa='" + shuZhangYa + '\'' +
                ", kongFuXueTang='" + kongFuXueTang + '\'' +
                ", FanHouXueTang='" + FanHouXueTang + '\'' +
                ", guoMinYaoWu='" + guoMinYaoWu + '\'' +
                ", liShiJiBing='" + liShiJiBing + '\'' +
                '}';
    }
}
