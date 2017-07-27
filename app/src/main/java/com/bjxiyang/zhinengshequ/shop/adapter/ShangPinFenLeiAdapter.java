package com.bjxiyang.zhinengshequ.shop.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baisi.myapplication.okhttp.listener.DisposeDataListener;
import com.bjxiyang.zhinengshequ.shop.R;
import com.bjxiyang.zhinengshequ.shop.activity.ShangPinXiangQingActivity;
import com.bjxiyang.zhinengshequ.shop.status.BianLiDianStatus;
import com.bjxiyang.zhinengshequ.shop.model.AddFenLei;
import com.bjxiyang.zhinengshequ.shop.model.ShangJiaOrXiaJia;
import com.bjxiyang.zhinengshequ.shop.model.ShangPinFenLei;
import com.bjxiyang.zhinengshequ.shop.request.RequestURL;
import com.bjxiyang.zhinengshequ.shop.update.network.RequestCenter;
import com.bjxiyang.zhinengshequ.shop.util.LogOutUntil;
import com.bjxiyang.zhinengshequ.shop.util.MyUntil;

import java.util.List;

/**
 * Created by Administrator on 2017/6/29 0029.
 */

public class ShangPinFenLeiAdapter extends BaseAdapter {

    private Context mContext;
    private List<ShangPinFenLei.Result> mList;

    public ShangPinFenLeiAdapter(Context mContext, List<ShangPinFenLei.Result> mList) {
        this.mContext = mContext;
        this.mList = mList;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View view, final ViewGroup parent) {

        ViewHolder viewHolder;
        if (view==null){
            viewHolder=new ViewHolder();
            view= LayoutInflater.from(mContext).inflate(R.layout.item_shangpinfenlei,null);
            viewHolder.iv_item_shangpinfenlei_select= (RelativeLayout)
                    view.findViewById(R.id.iv_item_shangpinfenlei_select);
            viewHolder.tv_item_shangpinfenlei= (TextView)
                    view.findViewById(R.id.tv_item_shangpinfenlei);
            viewHolder.iv_xiaoqufuwu_wuyejiaofei_edit= (RelativeLayout)
                    view.findViewById(R.id.iv_xiaoqufuwu_wuyejiaofei_edit);
            view.setTag(viewHolder);
        }else {
            viewHolder= (ViewHolder) view.getTag();
        }
        //对View中的数据进行赋值
        viewHolder.tv_item_shangpinfenlei.setText(mList.get(position).getName());
//        if (mList.get(position).getSellerId()==0){
//            viewHolder.iv_item_shangpinfenlei_select.setImageResource(R.drawable.b_c_b_a_btn_pitchon_pre);
//        }else {
//            viewHolder.iv_item_shangpinfenlei_select.setImageResource(R.drawable.b_c_a_btn_no);
//        }
        final int position1=position;
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(mContext, ShangPinXiangQingActivity.class);
                intent.putExtra("TYPE",1);
                intent.putExtra("fenlei",mList.get(position1).getName());
                intent.putExtra("productTypeId",mList.get(position1).getId());
                mContext.startActivity(intent);
            }
        });

        viewHolder.iv_xiaoqufuwu_wuyejiaofei_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final View view1=LayoutInflater.from(mContext).inflate(R.layout.dialog_edittext_view,null);
                new AlertDialog.Builder(mContext)
                        .setTitle("请输入新的商品分类")
                        .setIcon(R.mipmap.ic_launcher)
//                        .setView(editText)
                        .setView(view1)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                EditText ev_name = (EditText) view1.findViewById(R.id.name);
                                EditText ev_paixu = (EditText) view1.findViewById(R.id.xuhao);
                                final String name=String.valueOf(ev_name.getText());
                                final String paixu=String.valueOf(ev_paixu.getText());
                                String xinzeng_url= RequestURL.URL_PRODUCT_PRODUCTTYPE_UPDATE+"name="+name+"&sort="+paixu+
                                        "&productTypeId="+mList.get(position1).getId();
                                RequestCenter.product_productType_update(xinzeng_url, new DisposeDataListener() {
                                    @Override
                                    public void onSuccess(Object responseObj) {
                                        AddFenLei addFenLei= (AddFenLei) responseObj;
                                        if (addFenLei.getCode()== BianLiDianStatus.STATUS_CODE_SUCCESS){
                                            mList.get(position1).setName(name);
                                            notifyDataSetChanged();
                                        }if (addFenLei.getCode()==BianLiDianStatus.STATUS_CODE_ERROR_USER_NOTLOGIN){
                                            LogOutUntil.logout(mContext);
                                        }else {
                                            MyUntil.show(mContext,addFenLei.getMsg());
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
            }
        });
        //13202610230罗城街道上章平56号
        viewHolder.iv_item_shangpinfenlei_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(mContext)
                        .setTitle("确定要删除该分类吗？")
                        .setIcon(R.mipmap.ic_launcher)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String xinzeng_url= RequestURL.URL_PRODUCT_PRODUCTTYPE_DELETE+
                                        "productTypeId="+mList.get(position1).getId();
                                Log.i("YYYY",xinzeng_url);
                                RequestCenter.product_productType_delete(xinzeng_url, new DisposeDataListener() {
                                    @Override
                                    public void onSuccess(Object responseObj) {
                                        ShangJiaOrXiaJia shangJiaOrXiaJia= (ShangJiaOrXiaJia) responseObj;
                                        if (shangJiaOrXiaJia.getCode()== BianLiDianStatus.STATUS_CODE_SUCCESS){
                                            mList.remove(position1);
                                            notifyDataSetChanged();
                                        }if (shangJiaOrXiaJia.getCode()==BianLiDianStatus.STATUS_CODE_ERROR_USER_NOTLOGIN){
                                            LogOutUntil.logout(mContext);
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
            }
        });

        return view;
    }
    class ViewHolder{
        RelativeLayout iv_item_shangpinfenlei_select;
        TextView tv_item_shangpinfenlei;
        RelativeLayout iv_xiaoqufuwu_wuyejiaofei_edit;



    }
}
