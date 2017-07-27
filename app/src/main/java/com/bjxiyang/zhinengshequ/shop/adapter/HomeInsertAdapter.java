package com.bjxiyang.zhinengshequ.shop.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.bjxiyang.zhinengshequ.shop.R;
import com.bjxiyang.zhinengshequ.shop.model.DingDan;

import java.text.DecimalFormat;
import java.util.List;

/**
 * Created by Administrator on 2017/6/30 0030.
 */

public class HomeInsertAdapter extends BaseAdapter {
    DecimalFormat df=new DecimalFormat("0.##");
    private Context mContext;
    private List<DingDan.ResultBean.OrderInfoProductsBean> mList;

    public HomeInsertAdapter(Context mContext, List<DingDan.ResultBean.OrderInfoProductsBean> mList) {
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
    public View getView(int position, View view, ViewGroup parent) {
        ViewHolder viewHolder;
        if (view==null){
            viewHolder=new ViewHolder();
            view= LayoutInflater.from(mContext).inflate(R.layout.item_dingdanguanli_item,null);
            viewHolder.tv_goodsname= (TextView) view.findViewById(R.id.tv_goodsname);
            viewHolder.tv_goodscount= (TextView) view.findViewById(R.id.tv_goodscount);
            viewHolder.tv_goodsprice= (TextView) view.findViewById(R.id.tv_goodsprice);
            view.setTag(viewHolder);

        }else {
            viewHolder= (ViewHolder) view.getTag();
        }
        viewHolder.tv_goodsname.setText(mList.get(position).getName());
        viewHolder.tv_goodscount.setText(mList.get(position).getNum()+"");
        viewHolder.tv_goodsprice.setText(df.format((double) mList.get(position).getPrice()/100)+"");

        return view;
    }
    class ViewHolder{
        TextView tv_goodsname;
        TextView tv_goodscount;
        TextView tv_goodsprice;

    }
}
