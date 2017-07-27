package com.bjxiyang.zhinengshequ.shop.activity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bjxiyang.zhinengshequ.shop.R;

/**
 * Created by Administrator on 2017/6/30 0030.
 */

public class ShangJiaGongGaoActivity extends MySwipeBackActivity {

    private RelativeLayout iv_shangjiagonggao_fanhui;
    private EditText et_shangjiagonggao;
    private TextView tv_shangjiagonggao_queding;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.avtivity_shangjiagonggao);
        initUI();
    }

    private void initUI() {
        iv_shangjiagonggao_fanhui= (RelativeLayout) findViewById(R.id.iv_shangjiagonggao_fanhui);
        et_shangjiagonggao= (EditText) findViewById(R.id.et_shangjiagonggao);
        tv_shangjiagonggao_queding= (TextView) findViewById(R.id.tv_shangjiagonggao_queding);
    }
}
