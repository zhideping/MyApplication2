package com.bjxiyang.zhinengshequ.shop.dialog;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.TextView;

import com.baisi.myapplication.okhttp.listener.DisposeDataListener;
import com.bjxiyang.zhinengshequ.shop.R;
import com.bjxiyang.zhinengshequ.shop.status.BianLiDianStatus;
import com.bjxiyang.zhinengshequ.shop.model.ShangJiaOrXiaJia;
import com.bjxiyang.zhinengshequ.shop.model.ShangPinList;
import com.bjxiyang.zhinengshequ.shop.request.RequestURL;
import com.bjxiyang.zhinengshequ.shop.supermarket.adapter.GoodsAdapter;
import com.bjxiyang.zhinengshequ.shop.update.network.RequestCenter;
import com.bjxiyang.zhinengshequ.shop.util.DialogUntil;
import com.bjxiyang.zhinengshequ.shop.util.MyUntil;
import com.bjxiyang.zhinengshequ.shop.util.ShangJiaOrXIaJiaStatus;

import java.util.List;

/**
 * Created by Administrator on 2017/7/1 0001.
 */

public class MyShangJiaRoXiaJiaDialog extends AlertDialog implements View.OnClickListener{
    private TextView no;
    private TextView yes;
    private TextView title;
    private Context mContext;
    private GoodsAdapter adapter;
    private List<ShangPinList.Result.Products> list_shangpin;

    public static final String XIAJIACONTENT="是否下架全部商品？";
    public static final String SHANGJIACONTENT="是否上架全部商品？";
    public static final int  XIAJIA=0;
    public static final int  SHANGJIA=1;

    private int type;

    public MyShangJiaRoXiaJiaDialog(@NonNull Context context, int type, GoodsAdapter adapter,List<ShangPinList.Result.Products> list_shangpin) {
        super(context);
        this.type=type;
        this.mContext=context;
        this.adapter=adapter;
        this.list_shangpin=list_shangpin;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_dialog);
        initUI();
    }

    private void initUI() {
        setCanceledOnTouchOutside(false);
        no= (TextView) findViewById(R.id.no);
        yes= (TextView) findViewById(R.id.yes);
        title= (TextView) findViewById(R.id.dialog_shangjiaorxiajia_title);
        no.setOnClickListener(this);
        yes.setOnClickListener(this);
        if (type==XIAJIA) {
            title.setText(XIAJIACONTENT);
        }else if (type==SHANGJIA) {
            title.setText(SHANGJIACONTENT);
        }
    }

    @Override
    public void onClick(View v) {
        //全部下架的逻辑操作
        if (type==XIAJIA){
            switch (v.getId()){
                case R.id.no:
                    cancel();
                    break;
                //全部下架的请求
                case R.id.yes:
                    DialogUntil.showLoadingDialog(mContext,"正在加载",true);
                    String url_xiajiaall = RequestURL.URL_PRODUCT_DOWNALL;
                    RequestCenter.product_downall(url_xiajiaall, new DisposeDataListener() {
                        @Override
                        public void onSuccess(Object responseObj) {
                            DialogUntil.closeLoadingDialog();
                            ShangJiaOrXiaJia shangJiaOrXiaJia= (ShangJiaOrXiaJia) responseObj;
                            if (shangJiaOrXiaJia.getCode()== BianLiDianStatus.STATUS_CODE_SUCCESS){
                                for (int i=0;i<list_shangpin.size();i++){
                                    list_shangpin.get(i).setStatus(ShangJiaOrXIaJiaStatus.XIAJIA);
                                }
                                MyUntil.show(mContext,"全部下架成功");
                                adapter.notifyDataSetChanged();
                                cancel();
                            }else {
                                MyUntil.show(mContext,shangJiaOrXiaJia.getMsg());
                            }
                        }

                        @Override
                        public void onFailure(Object reasonObj) {
                            DialogUntil.closeLoadingDialog();

                        }
                    });

                    break;
            }
        }
        //全部上架的逻辑操作
        if (type==SHANGJIA){
            switch (v.getId()){
                case R.id.no:
                    cancel();
                    break;
                //全部上架的请求
                case R.id.yes:
                    DialogUntil.showLoadingDialog(mContext,"正在加载",true);
                    String url_shangjiaall= RequestURL.URL_PRODUCT_UPALL;
                    RequestCenter.product_upall(url_shangjiaall, new DisposeDataListener() {
                        @Override
                        public void onSuccess(Object responseObj) {
                            DialogUntil.closeLoadingDialog();
                            ShangJiaOrXiaJia shangJiaOrXiaJia= (ShangJiaOrXiaJia) responseObj;
                            if (shangJiaOrXiaJia.getCode()== BianLiDianStatus.STATUS_CODE_SUCCESS){
                                for (int i=0;i<list_shangpin.size();i++){
                                    list_shangpin.get(i).setStatus(ShangJiaOrXIaJiaStatus.SHANGJIA);
                                }
                                MyUntil.show(mContext,"全部上架成功");
                                adapter.notifyDataSetChanged();
                                cancel();
                            }else {
                                MyUntil.show(mContext,shangJiaOrXiaJia.getMsg());
                            }
                        }

                        @Override
                        public void onFailure(Object reasonObj) {
                            DialogUntil.closeLoadingDialog();
                        }
                    });

                    break;
            }
        }
    }
}
