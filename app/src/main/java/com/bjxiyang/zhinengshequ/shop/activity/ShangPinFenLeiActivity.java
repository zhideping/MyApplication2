package com.bjxiyang.zhinengshequ.shop.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baisi.myapplication.okhttp.listener.DisposeDataListener;
import com.bjxiyang.zhinengshequ.shop.R;
import com.bjxiyang.zhinengshequ.shop.adapter.ShangPinFenLeiAdapter;
import com.bjxiyang.zhinengshequ.shop.status.BianLiDianStatus;
import com.bjxiyang.zhinengshequ.shop.model.AddFenLei;
import com.bjxiyang.zhinengshequ.shop.model.ShangPinFenLei;
import com.bjxiyang.zhinengshequ.shop.request.RequestURL;
import com.bjxiyang.zhinengshequ.shop.update.network.RequestCenter;
import com.bjxiyang.zhinengshequ.shop.util.DialogUntil;
import com.bjxiyang.zhinengshequ.shop.util.LogOutUntil;
import com.bjxiyang.zhinengshequ.shop.util.MyUntil;

import java.util.List;

/**
 * Created by Administrator on 2017/6/29 0029.
 */

public class ShangPinFenLeiActivity extends MySwipeBackActivity implements View.OnClickListener{
    private RelativeLayout iv_shangpinfenlei_fanhui;
    private ListView lv_shangpinfenlei;
    private TextView tv_xinzeng;
    private ShangPinFenLeiAdapter adapter;

    private List<ShangPinFenLei.Result> mList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.avtivity_shangpinfenlei);
        instance=this;
        initUI();
        initData();
    }
    public static ShangPinFenLeiActivity instance = null;

    public void initData() {
        DialogUntil.showLoadingDialog(ShangPinFenLeiActivity.this,"正在加载",true);
        String url= RequestURL.URL_PRODUCT_PRODUCTTYPE_LIST;
        RequestCenter.product_productType_list(url, new DisposeDataListener() {
            @Override
            public void onSuccess(Object responseObj) {
                DialogUntil.closeLoadingDialog();
                ShangPinFenLei fenlei= (ShangPinFenLei) responseObj;
                if (fenlei.getCode()== BianLiDianStatus.STATUS_CODE_SUCCESS){
                    mList=fenlei.getResult();
                    adapter=new ShangPinFenLeiAdapter(ShangPinFenLeiActivity.this,mList);
                    lv_shangpinfenlei.setAdapter(adapter);
                }else if (fenlei.getCode()==BianLiDianStatus.STATUS_CODE_ERROR_USER_NOTLOGIN){
                    LogOutUntil.logout(ShangPinFenLeiActivity.this);
                    finish();
                }else {
                    MyUntil.show(ShangPinFenLeiActivity.this,fenlei.getMsg());
                }
            }
            @Override
            public void onFailure(Object reasonObj) {
                DialogUntil.closeLoadingDialog();

            }
        });
    }

    private void initUI() {
        iv_shangpinfenlei_fanhui= (RelativeLayout) findViewById(R.id.iv_shangpinfenlei_fanhui);
        lv_shangpinfenlei= (ListView) findViewById(R.id.lv_shangpinfenlei);
        tv_xinzeng= (TextView) findViewById(R.id.tv_xinzeng);
        tv_xinzeng.setOnClickListener(this);
        iv_shangpinfenlei_fanhui.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            //新增的按键
            case R.id.tv_xinzeng:

                final View view1= LayoutInflater.from(ShangPinFenLeiActivity.this).inflate(R.layout.dialog_edittext_view,null);
                new AlertDialog.Builder(this)
                        .setTitle("请输入商品分类")
                        .setIcon(R.mipmap.ic_launcher)
                        .setView(view1)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                EditText ev_name = (EditText) view1.findViewById(R.id.name);
                                EditText ev_paixu = (EditText) view1.findViewById(R.id.xuhao);
                                final String name=String.valueOf(ev_name.getText());
                                final String paixu=String.valueOf(ev_paixu.getText());

                                String xinzeng_url=RequestURL.URL_PRODUCT_PRODUCTTYPE_ADD+"name="+name+"&sort="+paixu;
                                RequestCenter.product_productType_add(xinzeng_url, new DisposeDataListener() {
                                    @Override
                                    public void onSuccess(Object responseObj) {
                                        AddFenLei addFenLei= (AddFenLei) responseObj;
                                        if (addFenLei.getCode()==BianLiDianStatus.STATUS_CODE_SUCCESS){
                                            initData();
                                            adapter.notifyDataSetChanged();
                                        }if (addFenLei.getCode()==BianLiDianStatus.STATUS_CODE_ERROR_USER_NOTLOGIN){
                                            LogOutUntil.logout(ShangPinFenLeiActivity.this);
                                            finish();
                                        }else {
                                            MyUntil.show(ShangPinFenLeiActivity.this,addFenLei.getMsg());
                                        }
                                    }
                                    @Override
                                    public void onFailure(Object reasonObj) {

                                    }
                                });

                            }
                        })

                        .setNegativeButton("取消", null)
                        .show();
//                final MyEditTextDialog dialog=new MyEditTextDialog(ShangPinFenLeiActivity.this);
//                dialog.show();
//                Timer timer = new Timer();
//                timer.schedule(new TimerTask() {
//                    @Override
//                    public void run() {
//                        dialog.showKeyboard();
//                    }
//                }, 200);

                break;
            case R.id.iv_shangpinfenlei_fanhui:
                finish();
                break;
        }
    }
}
