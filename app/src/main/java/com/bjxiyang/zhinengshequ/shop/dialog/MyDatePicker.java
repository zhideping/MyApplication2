package com.bjxiyang.zhinengshequ.shop.dialog;

import android.app.DatePickerDialog;
import android.content.Context;
import android.widget.Button;
import android.widget.TextView;


import java.util.Calendar;

/**
 * Created by Administrator on 2017/7/1 0001.
 */

public class MyDatePicker {
    int mYear, mMonth, mDay;
    Button btn;
    TextView dateDisplay;
    final int DATE_DIALOG = 1;

    public DatePickerDialog showDialog(Context context){
        final Calendar ca = Calendar.getInstance();
        mYear = ca.get(Calendar.YEAR);
        mMonth = ca.get(Calendar.MONTH);
        mDay = ca.get(Calendar.DAY_OF_MONTH);

        return new DatePickerDialog(context, mdateListener, mYear, mMonth, mDay);
    }

//    final Calendar ca = Calendar.getInstance();
//    mYear = ca.get(Calendar.YEAR);
//    mMonth = ca.get(Calendar.MONTH);
//    mDay = ca.get(Calendar.DAY_OF_MONTH);

//    @Override
//    protected Dialog onCreateDialog(int id) {
//        switch (id) {
//            case DATE_DIALOG:
//                return
//        }
//        return null;
//    }

    /**
     * 设置日期 利用StringBuffer追加
     */
    public void display() {
        dateDisplay.setText(new StringBuffer().append(mMonth + 1).append("-").append(mDay).append("-").append(mYear).append(" "));
    }

    private DatePickerDialog.OnDateSetListener mdateListener = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(android.widget.DatePicker view, int year, int month, int dayOfMonth) {
            mYear = year;
            mMonth = month;
            mDay = dayOfMonth;
            display();
        }
    };
}
