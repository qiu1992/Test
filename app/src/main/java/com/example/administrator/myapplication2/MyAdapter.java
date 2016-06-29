package com.example.administrator.myapplication2;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016-6-29.
 */
public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyHolder>
{
    private List<String> stringList;
    private List<Integer> heightList;

    public void setStringList (List<String> stringList)
    {
        this.stringList = stringList;

        heightList = new ArrayList<Integer> ();
        for (int i = 0; i < stringList.size(); i++)
        {
            heightList.add( (int) (100 + Math.random() * 300));
        }
    }

    @Override
    public MyHolder onCreateViewHolder (ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from (parent.getContext ())
                .inflate (R.layout.item_layout, parent, false);
        return new MyHolder (view);
    }

    @Override
    public void onBindViewHolder (MyHolder holder, int position)
    {
        ViewGroup.LayoutParams params = holder.nameTv.getLayoutParams ();
        params.height = heightList.get (position);
        holder.nameTv.setLayoutParams (params);

        holder.nameTv.setText (stringList.get (position));
    }

    @Override
    public int getItemCount ()
    {
        return null == stringList ? 0 : stringList.size ();
    }

    class MyHolder extends RecyclerView.ViewHolder
    {
        private TextView nameTv;
        private ImageView nameIv;

        public MyHolder (View itemView)
        {
            super (itemView);
            nameTv = (TextView) itemView.findViewById (R.id.name_tv);
            nameIv = (ImageView) itemView.findViewById (R.id.name_iv);
        }
    }
}
