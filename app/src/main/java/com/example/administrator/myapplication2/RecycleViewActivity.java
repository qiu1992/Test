package com.example.administrator.myapplication2;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016-6-29.
 */
public class RecycleViewActivity extends AppCompatActivity
{
    private RecyclerView myRecycleView;
    private MyAdapter myAdapter;
    private List<String> dataList;

    @Override
    protected void onCreate (@Nullable Bundle savedInstanceState)
    {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_recycle_view);

        initDataList ();
        myRecycleView = (RecyclerView) findViewById (R.id.recycle_view);
//        myRecycleView.setLayoutManager (new LinearLayoutManager (this));
//        myRecycleView.setLayoutManager (new GridLayoutManager (this,3));
        myRecycleView.setLayoutManager (new StaggeredGridLayoutManager (3,StaggeredGridLayoutManager.VERTICAL));
        myRecycleView.setItemAnimator (new DefaultItemAnimator ());
//        myRecycleView.addItemDecoration (new DividerItemDecoration (this, LinearLayoutManager.VERTICAL));
        myRecycleView.addItemDecoration (new DividerGridItemDecoration (this));
        myAdapter = new MyAdapter ();
        myAdapter.setStringList (dataList);
        myRecycleView.setAdapter (myAdapter);
//        myRecycleView.addItemDecoration (new ListItemDecoration(this, LinearLayoutManager.VERTICAL));
    }

    private void initDataList ()
    {
        if (null == dataList)
        {
            dataList = new ArrayList<> ();
        }
        dataList.clear ();
        for (int i = 0; i < 56; i++)
        {
            dataList.add (i + 1 + ":" + System.currentTimeMillis ());
        }
    }
}
