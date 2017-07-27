package com.bjxiyang.zhinengshequ.shop.activity;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import com.bjxiyang.zhinengshequ.shop.R;
import com.bjxiyang.zhinengshequ.shop.util.MyUntil;


/**
 * Created by Administrator on 2017/7/1.
 */

public class DateActivity extends MySwipeBackActivity implements View.OnClickListener {
    private TextView startTime;
    private LinearLayout start;
    private LinearLayout end;
    private TextView endTime;
    private RelativeLayout rl_yingyeshijian_fanhui;
    private TextView tv__yingyeshijian_save;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.time_selector_yingyeshijian);
        initUI();
    }

    private void initUI() {
        startTime = (TextView) this.findViewById(R.id.tv1);
        endTime = (TextView) this.findViewById(R.id.tv2);
        start = (LinearLayout) findViewById(R.id.ll_qishishijian);
        end = (LinearLayout) findViewById(R.id.ll_jiezhishijian);
        tv__yingyeshijian_save= (TextView) findViewById(R.id.tv__yingyeshijian_save);
        rl_yingyeshijian_fanhui= (RelativeLayout) findViewById(R.id.rl_yingyeshijian_fanhui);
        rl_yingyeshijian_fanhui.setOnClickListener(this);
        tv__yingyeshijian_save.setOnClickListener(this);
        start.setOnClickListener(this);
        end.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            //返回的按键
            case R.id.rl_yingyeshijian_fanhui:
                finish();
                break;
            //起始时间的按键
            case R.id.ll_qishishijian:
                new TimePickerDialog(DateActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        startTime.setText(String.format("%d:%d", hourOfDay, minute));
                    }    //0,0指的是时间，true表示是否为24小时，true为24小时制
                }, 0, 0, true).show();
                break;
            //结束时间的按键
            case R.id.ll_jiezhishijian:
                new TimePickerDialog(DateActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        endTime.setText(String.format("%d:%d", hourOfDay, minute));
                    }
                }, 0, 0, true).show();
                break;
            //保存的按键
            case R.id.tv__yingyeshijian_save:
                MyUntil.show(DateActivity.this,String.valueOf(startTime.getText())+"---"+endTime.getText());


                break;
        }
    }
}
