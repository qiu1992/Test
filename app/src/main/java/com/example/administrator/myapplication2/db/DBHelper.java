package com.example.administrator.myapplication2.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

/**
 * Created by Administrator on 2016-6-13.
 */
public class DBHelper extends OrmLiteSqliteOpenHelper
{
    private static final String DATA_BASE_NAME = "hello.db";
    private static final int DATA_VERSION = 1;
    private Dao<SimpleData, Integer> simpleDatas;
    /**
     * 这个是applicationContext
     */
    private static Context mContext;

    public DBHelper (Context context)
    {
        super (context, DATA_BASE_NAME, null, DATA_VERSION);
    }

    @Override
    public void onCreate (SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource)
    {
        try
        {
            TableUtils.createTable (connectionSource, SimpleData.class);
        }
        catch (SQLException e)
        {
            e.printStackTrace ();
        }
    }

    @Override
    public void onUpgrade (SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource, int i, int i1)
    {
        try
        {
            TableUtils.dropTable (connectionSource, SimpleData.class, true);
            onCreate (sqLiteDatabase, connectionSource);
        }
        catch (Exception e)
        {
            e.printStackTrace ();
        }
    }


    private static class LazyHelper
    {
        private static DBHelper helper = new DBHelper (mContext);
    }

    public static DBHelper getInstance (Context context)
    {
        if (null == mContext)
        {
            mContext = context.getApplicationContext ();
        }
        return LazyHelper.helper;
    }

//    private static DBHelper instance;
//
//    /**
//     * 单例获取该Helper
//     *
//     * @param context
//     * @return
//     */
//    public static synchronized DBHelper getHelper (Context context)
//    {
//        if (instance == null)
//        {
//            synchronized (DBHelper.class)
//            {
//                if (instance == null)
//                    instance = new DBHelper (context.getApplicationContext ());
//            }
//        }
//
//        return instance;
//    }

    public Dao<SimpleData, Integer> getDao () throws SQLException
    {
        if (simpleDatas == null)
        {
            simpleDatas = getDao (SimpleData.class);
        }
        return simpleDatas;
    }

    @Override
    public void close ()
    {
        super.close ();
        simpleDatas = null;
        mContext = null;
    }
}
