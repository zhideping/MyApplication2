package com.bjxiyang.zhinengshequ.shop.dialog;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.bjxiyang.zhinengshequ.shop.R;

/**
 * Created by Administrator on 2017/7/1 0001.
 */

public class MyEditTextDialog extends AlertDialog implements View.OnClickListener{
    private TextView no;
    private TextView yes;
    private EditText editText;

    private String addfenlei;
    public MyEditTextDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_edittext);
        initUI();
    }

    private void initUI() {
        setCanceledOnTouchOutside(false);
        no= (TextView) findViewById(R.id.no);
        yes= (TextView) findViewById(R.id.yes);
        editText= (EditText) findViewById(R.id.edit_shangjiaorxiajia_title);
        no.setOnClickListener(this);
        yes.setOnClickListener(this);

        showKeyboard();
    }
    public void showKeyboard() {
        if(editText!=null){
            //设置可获得焦点
            editText.setFocusable(true);
            editText.setFocusableInTouchMode(true);
            //请求获得焦点
            editText.requestFocus();
            //调用系统输入法
//            InputMethodManager inputManager = (InputMethodManager) editText
//                    .getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
//            inputManager.showSoftInput(editText, 0);
        }
    }

    @Override
    public void onClick(View v) {

            switch (v.getId()){
                //取消的按键
                case R.id.no:
                    cancel();
                    break;
                //确定的按键，需要提交网络   提交完成后关闭dialog
                case R.id.yes:
                    addfenlei= String.valueOf(editText.getText());



                    break;
            }
    }
}
