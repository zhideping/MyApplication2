package com.bjxiyang.zhinengshequ.shop.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.bjxiyang.zhinengshequ.shop.R;
import com.bjxiyang.zhinengshequ.shop.fragment.HomeFragment;
import com.bjxiyang.zhinengshequ.shop.fragment.MyFragment;
import com.bjxiyang.zhinengshequ.shop.fragment.ShoppFragment;
import com.bjxiyang.zhinengshequ.shop.manager.SPManager;
import com.bjxiyang.zhinengshequ.shop.receiver.MyReceiver;

import java.util.Set;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;

/**
 * Created by gll on 17-5-20.
 */

public class MainActivity extends BeasActivity implements View.OnClickListener{

    private RelativeLayout mHomeLayout;
    private RelativeLayout mShangPingLayout;
    private RelativeLayout mMyLayout;

    private TextView mHomeView;
    private TextView mShoppView;
    private TextView mMyView;

    private FragmentManager fm;
    private Fragment mHomeFragment;
    private Fragment mMyFragment;
    private Fragment mShoppFragment;
    FragmentTransaction fragmentTransaction;
    public static LinearLayout linearLayout;
    private MessageReceiver mMessageReceiver;
    public static MainActivity instance = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        instance=this;
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);
        setAlias();
        JPushInterface.resumePush(MainActivity.this);
        registerMessageReceiver();
        initDate();
        initUi();
    }
    private void initUi() {
        fm=getSupportFragmentManager();
        //获取事务
        fragmentTransaction= fm.beginTransaction();
        //显示首页的Fragment
        mHomeFragment=new HomeFragment();
        fragmentTransaction.add(R.id.content_layout,mHomeFragment).show(mHomeFragment);
        fragmentTransaction.commit();

        mHomeLayout= (RelativeLayout) findViewById(R.id.dingdan_layout_view);
        mHomeLayout.setOnClickListener(this);
        mShangPingLayout=(RelativeLayout) findViewById(R.id.shangpin_layout_view);
        mShangPingLayout.setOnClickListener(this);
        mMyLayout= (RelativeLayout) findViewById(R.id.shangjia_layout_view);
        mMyLayout.setOnClickListener(this);
        mHomeView= (TextView) findViewById(R.id.home_image_view);
        mShoppView= (TextView) findViewById(R.id.fish_image_view);
        mMyView= (TextView) findViewById(R.id.mine_image_view);
        mHomeView.setBackgroundResource(R.drawable.z_icon_dingdan);

        linearLayout= (LinearLayout) findViewById(R.id.linearLayout);
    }

//    @Override
//    protected void onResume() {
//        if (fragmentTransaction!=null){
//            if (mHomeFragment!=null){
//                hideFragment(mHomeFragment,fragmentTransaction);
//            }
//            if (mMyFragment!=null){
//                hideFragment(mMyFragment,fragmentTransaction);
//            }
//            if (mShoppFragment!=null){
//                hideFragment(mShoppFragment,fragmentTransaction);
//            }
//        }
//        super.onResume();
//    }

    private void initDate() {

    }
    //四个按键的监听事件，以及对四个Fragment的操作
    public void onClick(View view) {
        fragmentTransaction= fm.beginTransaction();
        switch (view.getId()) {
            //主页
            case R.id.dingdan_layout_view:
                mHomeView.setBackgroundResource(R.drawable.z_icon_dingdan);
                mShoppView.setBackgroundResource(R.drawable.z_icon_shangpin_a);
                mMyView.setBackgroundResource(R.drawable.z_icon_dianpu_a);
                hideFragment(mHomeFragment,fragmentTransaction);
                hideFragment(mMyFragment,fragmentTransaction);
                hideFragment(mShoppFragment,fragmentTransaction);
//                将我们的HomeFragment显示出来
                if (mHomeFragment == null) {
                    mHomeFragment = new HomeFragment();
                    fragmentTransaction.add(R.id.content_layout, mHomeFragment).show(mHomeFragment).commit();
                } else {
                    fragmentTransaction.show(mHomeFragment).commit();
                }
                break;

            case R.id.shangpin_layout_view:
                mHomeView.setBackgroundResource(R.drawable.z_icon_dingdan_a);
                mShoppView.setBackgroundResource(R.drawable.z_icon_shangpin);
                mMyView.setBackgroundResource(R.drawable.z_icon_dianpu_a);
                hideFragment(mHomeFragment, fragmentTransaction);
                hideFragment(mMyFragment, fragmentTransaction);
                hideFragment(mShoppFragment, fragmentTransaction);
                //将我们的HomeFragment显示出来
                if (mShoppFragment == null) {
                    mShoppFragment = new ShoppFragment();
                    fragmentTransaction.add(R.id.content_layout, mShoppFragment).show(mShoppFragment).commit();
                } else {
                    fragmentTransaction.show(mShoppFragment).commit();
                }
                break;
            //我的
            case R.id.shangjia_layout_view:
                mHomeView.setBackgroundResource(R.drawable.z_icon_dingdan_a);
                mShoppView.setBackgroundResource(R.drawable.z_icon_shangpin_a);
                mMyView.setBackgroundResource(R.drawable.z_icon_dianpu);
                hideFragment(mHomeFragment, fragmentTransaction);
                hideFragment(mShoppFragment, fragmentTransaction);
                hideFragment(mMyFragment, fragmentTransaction);
                //将我们的HomeFragment显示出来
                if (mMyFragment == null) {
                    mMyFragment = new MyFragment();
                    fragmentTransaction.add(R.id.content_layout, mMyFragment).show(mMyFragment).commit();
                } else {
                    fragmentTransaction.show(mMyFragment).commit();
                }
                break;
        }
    }
        /**
         * 用来隐藏具体的Fragment
         * @param fragment
         * @param ft
         */
    private void hideFragment(Fragment fragment, FragmentTransaction ft) {
        if (fragment != null) {
            ft.hide(fragment);
        }
    }

    private static final String[] PHONES_PROJECTION = new String[] {
            ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
            ContactsContract.CommonDataKinds.Phone.NUMBER};
    //动态权限
//    private void quanxian(){
//        if (Build.VERSION.SDK_INT >= 23) {
//            getQuanXian(Manifest.permission.WRITE_EXTERNAL_STORAGE,1);
//            getQuanXian(Manifest.permission.READ_EXTERNAL_STORAGE,1);
//            getQuanXian(Manifest.permission.READ_CONTACTS,10);
//            getQuanXian(Manifest.permission.READ_PHONE_STATE,1);
//            getQuanXian(Manifest.permission.CALL_PHONE,3);
//            getQuanXian(Manifest.permission.GET_ACCOUNTS,4);
//            getQuanXian(Manifest.permission.WRITE_CALENDAR,5);
//            getQuanXian(Manifest.permission.RECORD_AUDIO,7);
//            getQuanXian(Manifest.permission.READ_SMS,8);
//        }
//        if (ContextCompat.checkSelfPermission(this,
//                Manifest.permission.WRITE_CONTACTS) == PackageManager.PERMISSION_GRANTED){
//            if (!SPManager.getInstance().getBoolean("isShangchuan",false)){
//                getPhoneNumberFromMobile();
//            }
//        }
//    }
    //动态权限的封装
    private void getQuanXian(String permission,int code){
        int permissionInt= ContextCompat.checkSelfPermission(this,
                permission);
        if(permissionInt != PackageManager.PERMISSION_GRANTED){
            android.support.v4.app.ActivityCompat.requestPermissions(this,
                    new String[]{permission},code);
            return;
        }else{

        }

    }
    private void setAlias() {

        String alias= SPManager.getInstance().getString("name",null);
        // 调用 Handler 来异步设置别名
        mHandler.sendMessage(mHandler.obtainMessage(MSG_SET_ALIAS, alias));
    }

    private final TagAliasCallback mAliasCallback = new TagAliasCallback() {
        @Override
        public void gotResult(int code, String alias, Set<String> tags) {
            switch (code) {
                case 0:
                    // 建议这里往 SharePreference 里写一个成功设置的状态。成功设置一次后，以后不必再次设置了。
                    break;
                case 6002:
                    // 延迟 60 秒来调用 Handler 设置别名
                    mHandler.sendMessageDelayed(mHandler.obtainMessage(MSG_SET_ALIAS, alias), 1000 * 60);
                    break;
                default:
            }
        }
    };
    private static final int MSG_SET_ALIAS = 1001;
    private final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(android.os.Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MSG_SET_ALIAS:
                    // 调用 JPush 接口来设置别名。
//                    JPushInterface.setAlias(MainActivity.this,UserManager.getInstance().getUser().getObj().getMobilePhone(),mAliasCallback);

                    JPushInterface.setAliasAndTags(getApplicationContext(),
                            (String) msg.obj,
                            null,
                            mAliasCallback);
                    break;
                default:
            }
        }
    };
    public void registerMessageReceiver() {
        mMessageReceiver = new MessageReceiver();
        IntentFilter filter = new IntentFilter();
        filter.setPriority(IntentFilter.SYSTEM_HIGH_PRIORITY);
        filter.addAction(MyReceiver.MESSAGE_RECEIVED_ACTION);
        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver, filter);
    }
    public class MessageReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            try {

//                if (MESSAGE_RECEIVED_ACTION.equals(intent.getAction())) {
                String messge = intent.getStringExtra(MyReceiver.KEY_MESSAGE);
                String title=intent.getStringExtra(MyReceiver.KEY_TITLE);
                String extras = intent.getStringExtra(MyReceiver.KEY_EXTRAS);

            } catch (Exception e){
            }
        }
    }
}

