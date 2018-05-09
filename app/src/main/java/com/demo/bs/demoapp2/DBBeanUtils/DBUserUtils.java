package com.demo.bs.demoapp2.DBBeanUtils;

import android.content.Context;

import com.aidebar.greendaotest.gen.DBUserDao;
import com.aidebar.greendaotest.gen.DaoManager;
import com.demo.bs.demoapp2.DBBean.DBUser;

import java.util.List;

public class DBUserUtils {

    private DBUserDao dbUserInfoBeanDao;
    private static DBUserUtils dbUserInvestmentUtils = null;

    public DBUserUtils(Context context) {
        dbUserInfoBeanDao = DaoManager.getInstance(context).getNewSession().getDBUserDao();
    }

    public static DBUserUtils getInstance() {
        return dbUserInvestmentUtils;
    }

    public static void Init(Context context) {
        if (dbUserInvestmentUtils == null) {
            dbUserInvestmentUtils = new DBUserUtils(context);
        }
    }

    /**
     * 完成对数据库中插入一条数据操作
     *
     * @param
     * @return
     */
    public boolean insertOneData(DBUser dbUserInvestment) {

        boolean flag = false;
        try {
            dbUserInfoBeanDao.insertOrReplace(dbUserInvestment);
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    /**
     * 完成对数据库中插入多条数据操作
     *
     * @param dbUserInvestmentList
     * @return
     */
    public boolean insertManyData(List<DBUser> dbUserInvestmentList) {
        boolean flag = false;
        try {
            dbUserInfoBeanDao.insertOrReplaceInTx(dbUserInvestmentList);
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    /**
     * 完成对数据库中删除一条数据操作
     *
     * @param dbUserInvestment
     * @return
     */
    public boolean deleteOneData(DBUser dbUserInvestment) {
        boolean flag = false;
        try {
            dbUserInfoBeanDao.delete(dbUserInvestment);
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    /**
     * 完成对数据库中删除一条数据 ByKey操作
     *
     * @return
     */
    public boolean deleteOneDataByKey(String id) {
        boolean flag = false;
        try {
            dbUserInfoBeanDao.deleteByKey(id);
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    /**
     * 完成对数据库中批量删除数据操作
     *
     * @return
     */
    public boolean deleteManData(List<DBUser> dbUserInvestmentList) {
        boolean flag = false;
        try {
            dbUserInfoBeanDao.deleteInTx(dbUserInvestmentList);
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    /**
     * 完成对数据库更新数据操作
     *
     * @return
     */
    public boolean updateData(DBUser dbUserInvestment) {
        boolean flag = false;
        try {
            dbUserInfoBeanDao.update(dbUserInvestment);
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    /**
     * 完成对数据库批量更新数据操作
     *
     * @return
     */
    public boolean updateManData(List<DBUser> dbUserInvestmentList) {
        boolean flag = false;
        try {
            dbUserInfoBeanDao.updateInTx(dbUserInvestmentList);
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    /**
     * 完成对数据库查询数据操作
     *
     * @return
     */
    public DBUser queryOneData(String id) {
        return dbUserInfoBeanDao.load(id);
    }

    /**
     * 完成对数据库查询所有数据操作
     *
     * @return
     */
    public List<DBUser> queryAllData() {
        return dbUserInfoBeanDao.loadAll();
    }
}


