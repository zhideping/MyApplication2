package com.bjxiyang.zhinengshequ.shop.util;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by Administrator on 2017/6/18 0018.
 */

public class MyUntil {

    public static void show(Context context,String message){
        Toast.makeText(context,message,Toast.LENGTH_LONG).show();
    }
}
