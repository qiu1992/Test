package com.example.administrator.myapplication2.db;

import com.j256.ormlite.field.DatabaseField;
//import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by Administrator on 2016-6-13.
 */
//@DatabaseTable(tableName = "tb_test")
public class SimpleData
{
    @DatabaseField(generatedId = true)
    private int id;

    @DatabaseField(index = true)
    private String name;

    @DatabaseField
    private long time;

    @DatabaseField
    private boolean isOld;

    public SimpleData ()
    {
    }

    public SimpleData (String name, long time, boolean isOld)
    {
        this.name = name;
        this.time = time;
        this.isOld = isOld;
    }

    public int getId ()
    {
        return id;
    }

    public void setId (int id)
    {
        this.id = id;
    }

    public String getName ()
    {
        return name;
    }

    public void setName (String name)
    {
        this.name = name;
    }

    public long getTime ()
    {
        return time;
    }

    public void setTime (long time)
    {
        this.time = time;
    }

    public boolean isOld ()
    {
        return isOld;
    }

    public void setOld (boolean old)
    {
        isOld = old;
    }
}
