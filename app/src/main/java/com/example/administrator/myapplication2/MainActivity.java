package com.example.administrator.myapplication2;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.myapplication2.db.DBHelper;
import com.example.administrator.myapplication2.db.SimpleData;
import com.google.gson.Gson;
import com.loopj.android.http.*;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

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

        findViewById (R.id.http_btn).setOnClickListener (new View.OnClickListener ()
        {
            @Override
            public void onClick (View v)
            {
                httpRequest ();
            }
        });

        findViewById (R.id.time_btn).setOnClickListener (new View.OnClickListener ()
        {
            @Override
            public void onClick (View v)
            {
                SimpleDateFormat dff = new SimpleDateFormat("HH:mm", Locale.getDefault ());
                dff.setTimeZone(TimeZone.getTimeZone("GMT-04"));
                String ee = dff.format(new Date ());
                resTv.setText (ee);
            }
        });
    }

    /**
     * 网络请求
     */
    private void httpRequest ()
    {
        AsyncHttpClient httpClient = new AsyncHttpClient ();
        httpClient.get ("http://www.baidu.com", new AsyncHttpResponseHandler ()
        {
            @Override
            public void onSuccess (int i, cz.msebera.android.httpclient.Header[] headers, byte[] bytes)
            {
                String res = new String (bytes);
                resTv.setText (res);
                Log.d ("qiuqiu",res);
            }

            @Override
            public void onFailure (int i, cz.msebera.android.httpclient.Header[] headers, byte[] bytes, Throwable throwable)
            {

            }
        });
    }

    private void writeData ()
    {
        SimpleData simpleData = new SimpleData ("ni",1111,true);
        DBHelper dbHelper = DBHelper.getInstance (this);

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
            DBHelper dbHelper = DBHelper.getInstance (MainActivity.this);
            List<SimpleData> simpleDataList = dbHelper.getDao ().queryForAll ();
            resTv.setText (new Gson ().toJson (simpleDataList));
        }
        catch (SQLException e)
        {
            e.printStackTrace ();
        }
    }
}
