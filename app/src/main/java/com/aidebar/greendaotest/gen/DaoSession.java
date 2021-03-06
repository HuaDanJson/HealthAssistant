package com.aidebar.greendaotest.gen;

import java.util.Map;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.AbstractDaoSession;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.identityscope.IdentityScopeType;
import org.greenrobot.greendao.internal.DaoConfig;

import com.demo.bs.demoapp2.DBBean.DBLuRuShuJvBean;
import com.demo.bs.demoapp2.DBBean.DBUser;
import com.demo.bs.demoapp2.DBBean.DBUserInfoBean;

import com.aidebar.greendaotest.gen.DBLuRuShuJvBeanDao;
import com.aidebar.greendaotest.gen.DBUserDao;
import com.aidebar.greendaotest.gen.DBUserInfoBeanDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see org.greenrobot.greendao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig dBLuRuShuJvBeanDaoConfig;
    private final DaoConfig dBUserDaoConfig;
    private final DaoConfig dBUserInfoBeanDaoConfig;

    private final DBLuRuShuJvBeanDao dBLuRuShuJvBeanDao;
    private final DBUserDao dBUserDao;
    private final DBUserInfoBeanDao dBUserInfoBeanDao;

    public DaoSession(Database db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        dBLuRuShuJvBeanDaoConfig = daoConfigMap.get(DBLuRuShuJvBeanDao.class).clone();
        dBLuRuShuJvBeanDaoConfig.initIdentityScope(type);

        dBUserDaoConfig = daoConfigMap.get(DBUserDao.class).clone();
        dBUserDaoConfig.initIdentityScope(type);

        dBUserInfoBeanDaoConfig = daoConfigMap.get(DBUserInfoBeanDao.class).clone();
        dBUserInfoBeanDaoConfig.initIdentityScope(type);

        dBLuRuShuJvBeanDao = new DBLuRuShuJvBeanDao(dBLuRuShuJvBeanDaoConfig, this);
        dBUserDao = new DBUserDao(dBUserDaoConfig, this);
        dBUserInfoBeanDao = new DBUserInfoBeanDao(dBUserInfoBeanDaoConfig, this);

        registerDao(DBLuRuShuJvBean.class, dBLuRuShuJvBeanDao);
        registerDao(DBUser.class, dBUserDao);
        registerDao(DBUserInfoBean.class, dBUserInfoBeanDao);
    }
    
    public void clear() {
        dBLuRuShuJvBeanDaoConfig.clearIdentityScope();
        dBUserDaoConfig.clearIdentityScope();
        dBUserInfoBeanDaoConfig.clearIdentityScope();
    }

    public DBLuRuShuJvBeanDao getDBLuRuShuJvBeanDao() {
        return dBLuRuShuJvBeanDao;
    }

    public DBUserDao getDBUserDao() {
        return dBUserDao;
    }

    public DBUserInfoBeanDao getDBUserInfoBeanDao() {
        return dBUserInfoBeanDao;
    }

}
