package com.bjxiyang.zhinengshequ.shop.dialog;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.bjxiyang.zhinengshequ.shop.R;
import com.bjxiyang.zhinengshequ.shop.adapter.MyRecyclerAdapter;
import com.bjxiyang.zhinengshequ.shop.view.WheelView;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Administrator on 2017/7/1 0001.
 */

public class MyFuWuTimeDialog extends AlertDialog implements View.OnClickListener{
    private RecyclerView re_fuwutime_dialog;
    private WheelView wheelView;
    private Context mContext;
    private MyRecyclerAdapter adapter;
    private List<String> mList;
    private LinearLayoutManager mLayoutManager;
    private TextView canal;
    private TextView ok;
    private static final int[] PLANETS_int = new int[]{0,1,2,3,4,5,6,7};
    private static final String[] PLANETS = new String[]{"尽快送达", "1小时", "2小时", "3小时", "4小时", "5小时", "6小时", "7小时"};
    private int selectTime_int=PLANETS_int[0];
    private String selectTime=PLANETS[0];

    private OnGetSelectTime onGetSelectTime;

    public MyFuWuTimeDialog(@NonNull Context context) {
        super(context);
        this.mContext=context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_songdashijian);
        initUI();
    }
    public void setOnGetSelectTime(OnGetSelectTime onGetSelectTime){
        this.onGetSelectTime=onGetSelectTime;
    }

    private void initUI() {
        ok= (TextView) findViewById(R.id.ok);
        ok.setOnClickListener(this);
        canal= (TextView) findViewById(R.id.canal);
        canal.setOnClickListener(this);
        wheelView= (WheelView) findViewById(R.id.re_fuwutime_dialog);
        wheelView.setOffset(1);
        wheelView.setItems(Arrays.asList(PLANETS));
        wheelView.setOnWheelViewListener(new WheelView.OnWheelViewListener() {
            @Override
            public void onSelected(int selectedIndex, String item) {
                selectTime_int=PLANETS_int[selectedIndex-1];
                selectTime=PLANETS[selectedIndex-1];
            }
        });
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.canal:
                cancel();
                break;
            //选择完成的请求提交selectTime
            case R.id.ok:
//                selectTime
                Log.i("selectTime",selectTime);
                onGetSelectTime.getSelectTime(selectTime);
                onGetSelectTime.getSelectTime_int(selectTime_int);
                cancel();
                break;
        }
    }
    public interface OnGetSelectTime{
        public void getSelectTime(String selectTime);
        public void getSelectTime_int(int selectTime_int);
    }
}
