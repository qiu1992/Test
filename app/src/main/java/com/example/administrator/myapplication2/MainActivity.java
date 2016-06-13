package com.example.administrator.myapplication2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.myapplication2.db.DBHelper;
import com.example.administrator.myapplication2.db.SimpleData;
import com.google.gson.Gson;

import java.sql.SQLException;
import java.util.List;

/**
 * dev分支提交代码
 */
public class MainActivity extends AppCompatActivity
{
    private TextView resTv;

    @Override
    protected void onCreate (Bundle savedInstanceState)
    {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_main);

        AutoCompleteTextView autoCompleteTextView = (AutoCompleteTextView) findViewById (R.id.auto_complete_tv);
        final String[] arr = {"abc", "apple", "admin", "addd", "你好", "你", "你的"};
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String> (this, android.R.layout.simple_list_item_1, arr);
        autoCompleteTextView.setAdapter (arrayAdapter);
        autoCompleteTextView.setThreshold (1);
        autoCompleteTextView.setOnItemClickListener (new AdapterView.OnItemClickListener ()
        {
            @Override
            public void onItemClick (AdapterView<?> parent, View view, int position, long id)
            {
                Toast.makeText (MainActivity.this, arr[position], Toast.LENGTH_SHORT).show ();
            }
        });

        resTv = (TextView) findViewById (R.id.res_tv);
        findViewById (R.id.write_data_btn).setOnClickListener (new View.OnClickListener ()
        {
            @Override
            public void onClick (View v)
            {
                writeData ();
            }
        });

        findViewById (R.id.read_data_btn).setOnClickListener (new View.OnClickListener ()
        {
            @Override
            public void onClick (View v)
            {
                readData ();
            }
        });
    }

    private void writeData ()
    {
        SimpleData simpleData = new SimpleData ("ni",1111,true);
        DBHelper dbHelper = DBHelper.getHelper (this);

        try
        {
            dbHelper.getDao ().create (simpleData);

            simpleData = new SimpleData ("n",222,false);
            dbHelper.getDao ().create (simpleData);

            simpleData = new SimpleData ("3421",88,false);
            dbHelper.getDao ().create (simpleData);

            simpleData = new SimpleData ("----",223,true);
            dbHelper.getDao ().create (simpleData);

            simpleData = new SimpleData ("6666633",88,false);
            dbHelper.getDao ().create (simpleData);

            simpleData = new SimpleData ("nidnof",1,false);
            dbHelper.getDao ().create (simpleData);
        }
        catch (SQLException e)
        {
            e.printStackTrace ();
        }
    }

    private void readData ()
    {
        try
        {
            DBHelper dbHelper = DBHelper.getHelper (this);
            List<SimpleData> simpleDataList = dbHelper.getDao ().queryForAll ();
            resTv.setText (new Gson ().toJson (simpleDataList));
        }
        catch (SQLException e)
        {
            e.printStackTrace ();
        }
    }
}
