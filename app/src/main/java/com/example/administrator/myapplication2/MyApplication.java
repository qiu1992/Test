package com.example.administrator.myapplication2;

import android.app.Application;

import com.testin.agent.TestinAgent;
import com.testin.agent.TestinAgentConfig;

/**
 * Created by Administrator on 2016-6-24.
 */
public class MyApplication extends Application
{
    @Override
    public void onCreate ()
    {
        super.onCreate ();

//        TestinAgent.init (this,"800a7e0158998dc41ff42075809642e8");

        TestinAgentConfig config = new TestinAgentConfig.Builder(getApplicationContext ())
                .withAppKey("800a7e0158998dc41ff42075809642e8")   // 您的应用的 AppKey,如果已经在 Manifest 中配置则此处可略
                .withAppChannel("qiu")   // 发布应用的渠道,如果已经在 Manifest 中配置则此处可略
                .withUserInfo("qiu")   // 用户信息-崩溃分析根据用户记录崩溃信息
                .withDebugModel(true)   // 输出更多SDK的debug信息
                .withErrorActivity(true)   // 发生崩溃时采集Activity信息
                .withCollectNDKCrash(true)   // 收集NDK崩溃信息
                .withOpenCrash(true)   // 收集崩溃信息开关
                .withOpenEx(true)   // 是否收集异常信息
                .withReportOnlyWifi(true)   // 仅在 WiFi 下上报崩溃信息
                .withReportOnBack(true)   // 当APP在后台运行时,是否采集信息
                .withLogCat (true)// 是否系统操作信息
                .build();
        TestinAgent.init(config);
    }
}
