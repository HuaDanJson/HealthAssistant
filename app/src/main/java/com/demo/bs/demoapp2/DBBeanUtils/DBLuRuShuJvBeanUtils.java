package com.demo.bs.demoapp2.DBBeanUtils;

import android.content.Context;

import com.aidebar.greendaotest.gen.DBLuRuShuJvBeanDao;
import com.aidebar.greendaotest.gen.DaoManager;
import com.demo.bs.demoapp2.DBBean.DBLuRuShuJvBean;

import java.util.List;

/**
 * Created by Json on 2017/5/9.
 */

public class DBLuRuShuJvBeanUtils {

    private DBLuRuShuJvBeanDao dbLuRuShuJvBeanDao ;
    private static DBLuRuShuJvBeanUtils dbLuRuShuJvBeanUtils=null;

    public DBLuRuShuJvBeanUtils  (Context context){
        dbLuRuShuJvBeanDao = DaoManager.getInstance(context).getNewSession().getDBLuRuShuJvBeanDao();
    }

    public static DBLuRuShuJvBeanUtils getInstance(){
        return dbLuRuShuJvBeanUtils;
    }
    public static void Init(Context context){
        if(dbLuRuShuJvBeanUtils == null){
            dbLuRuShuJvBeanUtils=new  DBLuRuShuJvBeanUtils(context);
        }
    }

    /**
     * 完成对数据库中插入一条数据操作
     * @param
     * @return
     */
    public void insertOneData(DBLuRuShuJvBean dbUserInvestment){
        dbLuRuShuJvBeanDao.insertOrReplace(dbUserInvestment);
    }

    /**
     * 完成对数据库中插入多条数据操作
     * @param dbUserInvestmentList
     * @return
     */
    public boolean insertManyData(List<DBLuRuShuJvBean> dbUserInvestmentList){
        boolean flag = false;
        try{
            dbLuRuShuJvBeanDao.insertOrReplaceInTx(dbUserInvestmentList);
            flag = true;
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return flag;
    }

    /**
     * 完成对数据库中删除一条数据操作
     * @param dbUserInvestment
     * @return
     */
    public boolean deleteOneData(DBLuRuShuJvBean dbUserInvestment){
        boolean flag = false;
        try{
            dbLuRuShuJvBeanDao.delete(dbUserInvestment);
            flag = true;
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return flag;
    }

    /**
     * 完成对数据库中删除一条数据 ByKey操作
     * @return
     */
    public boolean deleteOneDataByKey(long id){
        boolean flag = false;
        try{
            dbLuRuShuJvBeanDao.deleteByKey(id);
            flag = true;
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return flag;
    }

    /**
     * 完成对数据库中批量删除数据操作
     * @return
     */
    public boolean deleteManData(List<DBLuRuShuJvBean> dbUserInvestmentList){
        boolean flag = false;
        try{
            dbLuRuShuJvBeanDao.deleteInTx(dbUserInvestmentList);
            flag = true;
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return flag;
    }

    /**
     * 完成对数据库更新数据操作
     * @return
     */
    public boolean updateData(DBLuRuShuJvBean dbUserInvestment){
        boolean flag = false;
        try{
            dbLuRuShuJvBeanDao.update(dbUserInvestment);
            flag = true;
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return flag;
    }

    /**
     * 完成对数据库批量更新数据操作
     * @return
     */
    public boolean updateManData(List<DBLuRuShuJvBean> dbUserInvestmentList){
        boolean flag = false;
        try{
            dbLuRuShuJvBeanDao.updateInTx(dbUserInvestmentList);
            flag = true;
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return flag;
    }

    /**
     * 完成对数据库查询数据操作
     * @return
     */
    public DBLuRuShuJvBean queryOneData(long id) {
        return dbLuRuShuJvBeanDao.load(id);
    }

    /**
     * 完成对数据库查询所有数据操作
     * @return
     */
    public List<DBLuRuShuJvBean> queryData() {
        return dbLuRuShuJvBeanDao.loadAll();
    }

}

