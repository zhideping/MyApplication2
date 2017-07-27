package com.bjxiyang.zhinengshequ.shop.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.bjxiyang.zhinengshequ.shop.R;
import java.util.List;

/**
 * Created by Administrator on 2017/6/16 0016.
 */

public class MyRecyclerAdapter extends RecyclerView.Adapter<MyRecyclerAdapter.ViewHolder> {
    public List<String> mList;
    public static int isSelect=0;
    public MyRecyclerAdapter(List<String> mList) {
        this.mList = mList;
    }
    //创建新View，被LayoutManager所调用
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_recycler,viewGroup,false);
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }
    //将数据与界面进行绑定的操作
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {

        viewHolder.tv_item_recycler.setText(mList.get(position));
        if (isSelect==position){
            viewHolder.tv_item_recycler.setTextSize(30);
        }else {
            viewHolder.tv_item_recycler.setTextSize(15);
        }
    }
    //获取数据的数量
    @Override
    public int getItemCount() {
        return mList.size();
    }

    //自定义的ViewHolder，持有每个Item的的所有界面元素
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_item_recycler;
        public ViewHolder(View view){
            super(view);
            tv_item_recycler = (TextView) view.findViewById(R.id.tv_item_recycler);
        }
    }
}