package com.example.herve.ourschool.app;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;

import com.example.herve.ourschool.greendao.dao.DaoMaster;
import com.example.herve.ourschool.greendao.dao.DaoSession;

import cn.jpush.android.api.JPushInterface;

/**
 * Created           :Herve on 2016/9/27.
 *
 * @ Author          :Herve
 * @ e-mail          :lijianyou.herve@gmail.com
 * @ LastEdit        :2016/9/27
 * @ projectName     :OurSchool
 * @ version
 */

public class App extends Application {

    private static App app;
    private DaoMaster.DevOpenHelper mHelper;
    private SQLiteDatabase db;
    private DaoMaster mDaoMaster;
    private DaoSession mDaoSession;


    @Override
    public void onCreate() {
        super.onCreate();
        app = this;

        /*极光推送*/
        setJpush();

        /*GreenDao数据库*/
        setDatabase();
    }

    private void setJpush() {
        JPushInterface.setDebugMode(true);    // 设置开启日志,发布时请关闭日志
        JPushInterface.init(this);            // 初始化 JPush
    }

    public static App getApplication() {
        return app;
    }

    /**
     * 设置greenDao
     */

    private void setDatabase() {

        // 通过DaoMaster 的内部类 DevOpenHelper，你可以得到一个便利的SQLiteOpenHelper 对象。
        // 可能你已经注意到了，你并不需要去编写「CREATE TABLE」这样的 SQL 语句，因为greenDAO 已经帮你做了。
        // 注意：默认的DaoMaster.DevOpenHelper 会在数据库升级时，删除所有的表，意味着这将导致数据的丢失。
        // 所以，在正式的项目中，你还应该做一层封装，来实现数据库的安全升级。
        mHelper = new DaoMaster.DevOpenHelper(this, "GreenDao-db", null);
        db = mHelper.getWritableDatabase();
//        db = mHelper.getReadableDatabase().delete();
//        db = mHelper.getReadableDb().execSQL();
        // 注意：该数据库连接属于DaoMaster，所以多个 Session 指的是相同的数据库连接。
        mDaoMaster = new DaoMaster(db);
        mDaoSession = mDaoMaster.newSession();

//        QueryBuilder.LOG_SQL = true;
// 输出带有具体数值的sql日志
//        QueryBuilder.LOG_VALUES = true;

    }

    public DaoSession getDaoSession() {
        return mDaoSession;
    }


    public SQLiteDatabase getDb() {
        return db;
    }
}
