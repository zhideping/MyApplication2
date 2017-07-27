package com.bjxiyang.zhinengshequ.shop.app;

import android.app.Activity;
import android.app.Application;
import android.content.SharedPreferences;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sander on 2017/4/7.
 */

public class GuardApplication extends Application {
    private static List<Activity> con_list = new ArrayList<Activity>();
    private SharedPreferences sp;

    public static GuardApplication instance;

    public GuardApplication(){
        instance = this;
    }

    public static GuardApplication getContent(){
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
//
//        Fresco.initialize(this);
////
//        CustomActivityOnCrash.install(this);
//
//
        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
                .cacheInMemory(true).cacheOnDisc(true).build();
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
                getApplicationContext()).defaultDisplayImageOptions(
                defaultOptions).build();
        ImageLoader.getInstance().init(config);

    }



}
