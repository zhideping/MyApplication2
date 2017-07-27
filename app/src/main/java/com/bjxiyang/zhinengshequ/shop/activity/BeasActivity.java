package com.bjxiyang.zhinengshequ.shop.activity;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;

import com.testin.agent.Bugout;
import com.testin.agent.BugoutConfig;

/**
 * Created by Administrator on 2017/5/26 0026.
 */

public class BeasActivity extends AppCompatActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        Bugout.init(this, "279c8c7a996a03f452e6804a1f23a5e5", "your channel ID");
        BugoutConfig config = new BugoutConfig.Builder(BeasActivity.this)
                .withAppKey("")     // 您的应用的项目ID,如果已经在 Manifest 中配置则此处可略
                .withAppChannel("")     // 发布应用的渠道,如果已经在 Manifest 中配置则此处可略
                .withUserInfo("d")    // 用户信息-崩溃分析根据用户记录崩溃信息
                .withDebugModel(true)    // 输出更多SDK的debug信息
                .withErrorActivity(true)    // 发生崩溃时采集Activity信息
                .withCollectNDKCrash(true) //  收集NDK崩溃信息
                .withOpenCrash(true)    // 收集崩溃信息开关
                .withOpenEx(true)     // 是否收集异常信息
                .withReportOnlyWifi(false)    // 仅在 WiFi 下上报崩溃信息
                .withReportOnBack(true)    // 当APP在后台运行时,是否采集信息
                .withCloseOption(false)   // 是否在摇一摇菜单展示‘关闭摇一摇选项’
                .withLogCat(true)  // 是否系统操作信息
                .build();
        Bugout.init(config);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //* 注：回调 1
        Bugout.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        //* 注：回调 2 
        Bugout.onPause(this);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        //* 注：回调 3 
        Bugout.onDispatchTouchEvent(this, event);
        return super.dispatchTouchEvent(event);
    }

}