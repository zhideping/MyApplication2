package com.bjxiyang.zhinengshequ.shop.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.baisi.myapplication.okhttp.listener.DisposeDataListener;
import com.baisi.myapplication.okhttp.request.RequestParams;
import com.bjxiyang.zhinengshequ.shop.R;
import com.bjxiyang.zhinengshequ.shop.status.BianLiDianStatus;
import com.bjxiyang.zhinengshequ.shop.dialog.MyDialog;
import com.bjxiyang.zhinengshequ.shop.manager.SPManager;
import com.bjxiyang.zhinengshequ.shop.manager.UserManager;
import com.bjxiyang.zhinengshequ.shop.model.Login;
import com.bjxiyang.zhinengshequ.shop.request.RequestURL;
import com.bjxiyang.zhinengshequ.shop.update.network.RequestCenter;
import com.baisi.myapplication.adutil.APP_ID;
import com.bjxiyang.zhinengshequ.shop.util.DialogUntil;
import com.bjxiyang.zhinengshequ.shop.util.MyUntil;

/**
 * Created by Administrator on 2017/7/2 0002.
 */

public class DengLuActivity extends BeasActivity implements View.OnClickListener {

//    private Timer mtimer;
    private EditText et_login_username;
    private EditText et_login_pwd;
//    private Button bt_get_smscode;
    private Button btn_login;
    private String name;
    private String pass;

//    private int timeCount;

//    private Handler timerHandler = new Handler(){
//        @Override
//        public void handleMessage(Message msg) {
//            super.handleMessage(msg);
//            if (msg.what == 1){
//                timeCount --;
//                if (timeCount >= 0){
//                    changeSmsButton();
//                }else{
//                    mtimer.cancel();
//                    bt_get_smscode.setEnabled(true);
//                    bt_get_smscode.setText(R.string.getsmsversion);
//                }
//            }
//        }
//    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sdlogin);

        if (SPManager.getInstance().getString("loginKey","")!=null&&!
                SPManager.getInstance().getString("loginKey","").equals("")){
            Intent intent=new Intent(DengLuActivity.this,MainActivity.class);
            startActivity(intent);
            finish();
        }

        getQuanxian();
        //判断有没有用户的信息,如果有,直接跳转到主界面
//        if (SPManager.getInstance().getBoolean("isOne",false)){
//            //跳转到主页面
//            Intent intent=new Intent(this,MainActivity.class);
//            startActivity(intent);
//            //关闭当前页面
//            finish();
//        }
        getIMEI();
        initUI();
    }

    private void initUI() {
        et_login_username= (EditText) findViewById(R.id.et_login_username);
        et_login_pwd= (EditText) findViewById(R.id.et_login_pwd);
//        bt_get_smscode= (Button) findViewById(R.id.bt_get_smscode);
        btn_login= (Button) findViewById(R.id.btn_login);
        btn_login.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            //发送验证码的按键
//            case R.id.bt_get_smscode:
//                timeCount=60;
//                //正常来说请求到网络以后回调中倒计时方法
//                startCountdown();
//                break;
            //登陆的按键
            case R.id.btn_login:
                DialogUntil.showLoadingDialog(DengLuActivity.this,"正在登陆",true);
                name= String.valueOf(et_login_username.getText());
                pass=String.valueOf(et_login_pwd.getText());
//                String url= RequestURL.URL_LOGIN+"loginName="+name+"&password="+pass;
                String url= RequestURL.URL_LOGIN+"loginName="+name+"&password="+pass;
                RequestParams headers=new RequestParams();
                headers.put("user-agent", ""+"appId="+APP_ID.APP_ID+"");
                RequestCenter.login(url,headers, new DisposeDataListener() {
                    @Override
                    public void onSuccess(Object responseObj) {
                        DialogUntil.closeLoadingDialog();
                        Login login= (Login) responseObj;
                        UserManager.getInstance().setUser(login);
                        if (login.getCode() == BianLiDianStatus.STATUS_CODE_SUCCESS){

                            setSP(login);
                            Intent intent=new Intent(DengLuActivity.this,MainActivity.class);
                            startActivity(intent);
                            finish();
                        }else {
                            MyUntil.show(DengLuActivity.this,login.getMsg());
                        }
                    }

                    @Override
                    public void onFailure(Object reasonObj) {
                        DialogUntil.closeLoadingDialog();
                        MyDialog.showDialog(DengLuActivity.this);
                    }
                });


                break;

        }
    }
    //开始倒计时
//    public void startCountdown(){
//        btn_login.setEnabled(true);
//        changeSmsButton();
//        bt_get_smscode.setEnabled(false);
//        setTimerTask();
//    }
    //在倒计时的时候,对按钮上的字进行赋值
//    public void changeSmsButton(){
//        bt_get_smscode.setText(timeCount + "秒重发");
//    }
//    //倒计时的方法
//    public void setTimerTask(){
//        mtimer = new Timer();
//        mtimer.schedule(new TimerTask() {
//            @Override
//            public void run() {
//                Message message = new Message();
//                message.what = 1;
//                timerHandler.sendMessage(message);
//            }
//        },1000,1000);
//    }
    //得到设备码
    private void getIMEI(){
        TelephonyManager telephonyManager=(TelephonyManager)
                this.getSystemService(Context.TELEPHONY_SERVICE);
        APP_ID.APP_ID = telephonyManager.getDeviceId();

    }

    public void getQuanxian() {
        if (Build.VERSION.SDK_INT >= 23) {
            if(ContextCompat.checkSelfPermission(this,
                    Manifest.permission.WRITE_SETTINGS)!= PackageManager.PERMISSION_GRANTED
                    ||ContextCompat.checkSelfPermission(this,
                    Manifest.permission.READ_PHONE_STATE)!= PackageManager.PERMISSION_GRANTED
                    ||ContextCompat.checkSelfPermission(this,
                    Manifest.permission.READ_CONTACTS)!= PackageManager.PERMISSION_GRANTED){
                android.support.v4.app.ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.WRITE_SETTINGS,
                                Manifest.permission.READ_PHONE_STATE,
                                Manifest.permission.READ_CONTACTS},1);

            }

        }
    }
    private void setSP(Login login){
        SPManager.getInstance().putString("ShopName",login.getResult().getShopName());
        SPManager.getInstance().putString("name",login.getResult().getLoginName());
        SPManager.getInstance().putString("loginKey",login.getResult().getLoginKey());
    }
}
