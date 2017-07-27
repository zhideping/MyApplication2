package com.bjxiyang.zhinengshequ.shop.supermarket.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Paint;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baisi.imoocsdk.imageloader.ImageLoaderManager;
import com.baisi.myapplication.okhttp.listener.DisposeDataListener;
import com.bjxiyang.zhinengshequ.shop.R;
import com.bjxiyang.zhinengshequ.shop.status.BianLiDianStatus;
import com.bjxiyang.zhinengshequ.shop.fragment.ShoppFragment;
import com.bjxiyang.zhinengshequ.shop.model.ShangJiaOrXiaJia;
import com.bjxiyang.zhinengshequ.shop.model.ShangPinList;
import com.bjxiyang.zhinengshequ.shop.request.RequestURL;
import com.bjxiyang.zhinengshequ.shop.update.network.RequestCenter;
import com.bjxiyang.zhinengshequ.shop.update.util.GetHeaders;
import com.bjxiyang.zhinengshequ.shop.util.DialogUntil;
import com.bjxiyang.zhinengshequ.shop.util.LogOutUntil;
import com.bjxiyang.zhinengshequ.shop.util.MyUntil;
import com.bjxiyang.zhinengshequ.shop.util.ShangJiaOrXIaJiaStatus;

import java.text.DecimalFormat;
import java.util.List;

/**
 * Created by fengyongge on 2016/5/24 0024.
 */

public class GoodsAdapter extends BaseAdapter{
    private List<ShangPinList.Result.Products> list;
    private Context context;
    private CatograyAdapter catograyAdapter;
    private ShoppFragment supermarketfragment;
    DecimalFormat df=new DecimalFormat("0.##");




    public GoodsAdapter(Context context, ShoppFragment supermarketfragment, List<ShangPinList.Result.Products> list2, CatograyAdapter catograyAdapter) {
        this.supermarketfragment=supermarketfragment;
        this.context=context;
        this.list=list2;
        this.catograyAdapter=catograyAdapter;
    }



    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Viewholder viewholder;
        if (convertView==null){
            convertView= LayoutInflater.from(context).inflate(R.layout.item_shangpinguanli,null);
            viewholder=new Viewholder();
            viewholder.rl_item= (LinearLayout) convertView.findViewById(R.id.rl_item);
            viewholder.iv_item_guanli_shangpinimage= (ImageView) convertView.findViewById(R.id.iv_item_guanli_shangpinimage);
            viewholder.tv_name= (TextView) convertView.findViewById(R.id.tv_item_guanli_shangpinname);
            viewholder.tv_original_price= (TextView) convertView.findViewById(R.id.tv_item_guanli_shangpinguige);
            viewholder.tv_price= (TextView) convertView.findViewById(R.id.tv_item_guanli_money);
            viewholder.tv_item_shangjia= (TextView) convertView.findViewById(R.id.tv_item_shangjia);
            viewholder.iv_delete= (ImageView) convertView.findViewById(R.id.iv_delete);
            convertView.setTag(viewholder);
        }else {
            viewholder = (Viewholder) convertView.getTag();

        }
        viewholder.tv_name.setText(list.get(position).getName());

        ShangPinList.Result.Products products =list.get(position);

//        viewholder.tv_price.setText("￥"+df.format((double)products.getPrice()/100));
////        viewholder.tv_original_price.setText("￥"+list.get(position).getProducts().get(position).getDes());
//        viewholder.tv_price.getPaint().setFlags(Paint. STRIKE_THRU_TEXT_FLAG); //中划线
//        viewholder.tv_original_price.setText("￥"+df.format((double)products.getDiscountPrice()/100));

        if (list.get(position).getIfDiscount()==1){
            viewholder.tv_price.setText("￥"+df.format((double)products.getPrice()/100));
//        viewholder.tv_original_price.setText("￥"+list.get(position).getProducts().get(position).getDes());
            viewholder.tv_price.getPaint().setFlags(Paint. STRIKE_THRU_TEXT_FLAG); //中划线
            viewholder.tv_original_price.setText("￥"+df.format((double)products.getDiscountPrice()/100));

        }else {
            viewholder.tv_price.setVisibility(View.INVISIBLE);
            viewholder.tv_original_price.setText("￥"+df.format((double)products.getPrice()/100));
        }

        if (products.getStatus()== ShangJiaOrXIaJiaStatus.XIAJIA){
            viewholder.tv_item_shangjia.setText("上架");
        }
        if (products.getStatus()==ShangJiaOrXIaJiaStatus.SHANGJIA){
            viewholder.tv_item_shangjia.setText("下架");

        }
        //商品图片
        if(products.getLogo()!=null){
            ImageLoaderManager.getInstance(context).
                    displayImage(viewholder.iv_item_guanli_shangpinimage,products.getLogo());
        }

        final int position1=position;
        final ShangPinList.Result.Products products1 = list.get(position1);
        //删除的按键
        viewholder.iv_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new AlertDialog.Builder(context)
                        .setTitle("确定要删除该商品吗？")
                        .setIcon(R.mipmap.ic_launcher)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                DialogUntil.showLoadingDialog(context,"正在提交",true);
                                String url_delete=RequestURL.URL_PRODUCT_DELETE+"productId="+list.get(position1).getId();
                                RequestCenter.product_delete(url_delete, new DisposeDataListener() {
                                    @Override
                                    public void onSuccess(Object responseObj) {
                                        DialogUntil.closeLoadingDialog();
                                        ShangJiaOrXiaJia shangJiaOrXiaJia= (ShangJiaOrXiaJia) responseObj;
                                        if (shangJiaOrXiaJia.getCode()== BianLiDianStatus.STATUS_CODE_SUCCESS){
                                            list.remove(position1);
                                            notifyDataSetChanged();
                                        }else {
                                            MyUntil.show(context,shangJiaOrXiaJia.getMsg());
                                        }
                                    }
                                    @Override
                                    public void onFailure(Object reasonObj) {
                                        DialogUntil.closeLoadingDialog();

                                    }
                                });

                            }
                        })
                        .setNegativeButton("取消", null)
                        .show();




            }
        });
        //上架下架的按键

        viewholder.tv_item_shangjia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogUntil.showLoadingDialog(context,"正在加载",true);

                Log.i("YYYY",String.valueOf(products1.getStatus()));

                if (products1.getStatus()== ShangJiaOrXIaJiaStatus.SHANGJIA){

                    //下架的操作
                    String url_down= RequestURL.URL_PRODUCT_DOWN+"productId="+list.get(position1).getId();
                    RequestCenter.product_down(url_down, GetHeaders.getHeaders(), new DisposeDataListener() {
                        @Override
                        public void onSuccess(Object responseObj) {
                            DialogUntil.closeLoadingDialog();
                            ShangJiaOrXiaJia shangJiaOrXiaJia= (ShangJiaOrXiaJia) responseObj;
                            if (shangJiaOrXiaJia.getCode() == BianLiDianStatus.STATUS_CODE_SUCCESS){
                                products1.setStatus(ShangJiaOrXIaJiaStatus.XIAJIA);
                                notifyDataSetChanged();
//                                Log.i("YYYY","测试");
//                                supermarketfragment.initData();
                            }else {
                                MyUntil.show(context,shangJiaOrXiaJia.getMsg());
                            }
                        }

                        @Override
                        public void onFailure(Object reasonObj) {
                            DialogUntil.closeLoadingDialog();

                        }
                    });
                }
                if (products1.getStatus()==ShangJiaOrXIaJiaStatus.XIAJIA){
                    //上架的操作
                    String url_up= RequestURL.URL_PRODUCT_UP+"productId="+list.get(position1).getId();
                    RequestCenter.product_up(url_up, GetHeaders.getHeaders(), new DisposeDataListener() {
                        @Override
                        public void onSuccess(Object responseObj) {
                            DialogUntil.closeLoadingDialog();
                            ShangJiaOrXiaJia shangJiaOrXiaJia= (ShangJiaOrXiaJia) responseObj;
                            if (shangJiaOrXiaJia.getCode() == BianLiDianStatus.STATUS_CODE_SUCCESS){
                                products1.setStatus(ShangJiaOrXIaJiaStatus.SHANGJIA);
                                notifyDataSetChanged();
//                                Log.i("YYYY","测试");
//                                supermarketfragment.initData();
                            }else {
                                MyUntil.show(context,shangJiaOrXiaJia.getMsg());
                            }
                        }

                        @Override
                        public void onFailure(Object reasonObj) {
                            DialogUntil.closeLoadingDialog();

                        }
                    });
                }
            }
        });


//        viewholder.rl_item.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                if(list.get(position).getPackage_product_info()!=null&&list.get(position).getTitle()!=null){
//                catograyAdapter.notifyDataSetChanged();
//
//                Intent intent=new Intent(context, ShangPinXiangQingActivity.class);
//
//                context.startActivity(intent);
////                supermarketfragment.showDetailSheet(supermarketfragment.getListAll(),list.get(position).getTitle());
////                }else{
////                    Toast.makeText(context, "没有详情!", Toast.LENGTH_SHORT).show();
////                }
//            }
//        });
        return convertView;
    }


    class Viewholder{
        ImageView iv_item_guanli_shangpinimage;
        TextView tv_name,tv_original_price,tv_price;
        LinearLayout rl_item;
        TextView tv_item_shangjia;
        ImageView iv_delete;
    }

}
