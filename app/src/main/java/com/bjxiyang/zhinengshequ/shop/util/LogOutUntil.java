package com.bjxiyang.zhinengshequ.shop.util;

import android.content.Context;
import android.content.Intent;

import com.baisi.myapplication.okhttp.listener.DisposeDataListener;
import com.bjxiyang.zhinengshequ.shop.activity.DengLuActivity;
import com.bjxiyang.zhinengshequ.shop.activity.MainActivity;
import com.bjxiyang.zhinengshequ.shop.dialog.MyDialog;
import com.bjxiyang.zhinengshequ.shop.manager.SPManager;
import com.bjxiyang.zhinengshequ.shop.request.RequestURL;
import com.bjxiyang.zhinengshequ.shop.update.network.RequestCenter;

/**
 * Created by Administrator on 2017/7/17 0017.
 */

public class LogOutUntil {

    public static void logout(final Context context){
        String url= RequestURL.URL_LOGOUT;

        RequestCenter.logout(url, new DisposeDataListener() {
            @Override
            public void onSuccess(Object responseObj) {
                DialogUntil.closeLoadingDialog();
                SPManager.getInstance().remove("loginKey");
                Intent intent=new Intent(context, DengLuActivity.class);
                context.startActivity(intent);
                MyUntil.show(context,"当前账号已在其他设备登陆");
                MainActivity.instance.finish();
            }
            @Override
            public void onFailure(Object reasonObj) {
                DialogUntil.closeLoadingDialog();
                MyDialog.showDialog(context);
            }
        });





    }


}
