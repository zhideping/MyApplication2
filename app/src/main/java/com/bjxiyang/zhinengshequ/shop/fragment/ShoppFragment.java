package com.bjxiyang.zhinengshequ.shop.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baisi.myapplication.okhttp.listener.DisposeDataListener;
import com.bjxiyang.zhinengshequ.shop.R;
import com.bjxiyang.zhinengshequ.shop.activity.MainActivity;
import com.bjxiyang.zhinengshequ.shop.activity.ShangPinFenLeiActivity;
import com.bjxiyang.zhinengshequ.shop.activity.ShangPinXiangQingActivity;
import com.bjxiyang.zhinengshequ.shop.app.GuardApplication;
import com.bjxiyang.zhinengshequ.shop.status.BianLiDianStatus;
import com.bjxiyang.zhinengshequ.shop.dialog.MyShangJiaRoXiaJiaDialog;
import com.bjxiyang.zhinengshequ.shop.model.ShangPinList;
import com.bjxiyang.zhinengshequ.shop.request.RequestURL;
import com.bjxiyang.zhinengshequ.shop.supermarket.adapter.CatograyAdapter;
import com.bjxiyang.zhinengshequ.shop.supermarket.adapter.GoodsAdapter;
import com.bjxiyang.zhinengshequ.shop.supermarket.adapter.GoodsDetailAdapter;
import com.bjxiyang.zhinengshequ.shop.supermarket.adapter.ProductAdapter;
import com.bjxiyang.zhinengshequ.shop.supermarket.bean.CatograyBean;
import com.bjxiyang.zhinengshequ.shop.supermarket.bean.GoodsBean;
import com.bjxiyang.zhinengshequ.shop.supermarket.bean.ItemBean;
import com.bjxiyang.zhinengshequ.shop.supermarket.view.MyListView;
import com.bjxiyang.zhinengshequ.shop.update.network.RequestCenter;
import com.bjxiyang.zhinengshequ.shop.util.DialogUntil;
import com.bjxiyang.zhinengshequ.shop.util.LogOutUntil;
import com.bjxiyang.zhinengshequ.shop.util.MyUntil;
import com.flipboard.bottomsheet.BottomSheetLayout;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import static android.os.Looper.getMainLooper;

/**
 * Created by Administrator on 2017/6/29 0029.
 */

public class ShoppFragment extends Fragment implements
        View.OnClickListener,AdapterView.OnItemClickListener,SwipeRefreshLayout.OnRefreshListener{
    private ListView lv_catogary, lv_good;
    private ImageView iv_logo;
    private TextView tv_car;
    private TextView tv_count,tv_totle_money,tv_totle_money2;
    Double totleMoney = 0.00;
    private TextView bv_unm;
    private RelativeLayout rl_bottom;
    //分类和商品
    private List<CatograyBean> list = new ArrayList<CatograyBean>();
    private List<GoodsBean> list2 = new ArrayList<GoodsBean>();
    private GuardApplication myApp;
    private CatograyAdapter catograyAdapter;//分类的adapter
    private GoodsAdapter goodsAdapter;//分类下商品adapter
    ProductAdapter productAdapter;//底部购物车的adapter

    GoodsDetailAdapter goodsDetailAdapter;//套餐详情的adapter
    private static DecimalFormat df;
    private LinearLayout ll_shopcar;
    //底部数据
    private BottomSheetLayout bottomSheetLayout;
    private View bottomSheet;
    private SparseArray<GoodsBean> selectedList;
    //套餐
    private View bottomDetailSheet;
    private List<GoodsBean> list3 = new ArrayList<GoodsBean>();
    private List<GoodsBean> list4 = new ArrayList<GoodsBean>();
    private List<GoodsBean> list5 = new ArrayList<GoodsBean>();

    private Handler mHanlder;
    private ViewGroup anim_mask_layout;//动画层
    private Context mContext;

    private ImageView fanhui;
    private TextView tv_spName;
    private TextView tv_biaoqian1;
    private TextView tv_biaoqian2;
    private TextView tv_danjia;
    private ImageView iv_jian;
    private ImageView iv_jia;
    private TextView tv_shuliang;
    private TextView tv_dianming;
    private TextView tv_spjieshao;
    private TextView tv_shuliang1;
    private TextView tv_xuanhaole;
    private boolean isShow=false;
    private View view;
    private SwipeRefreshLayout swipeRefreshLayout1;
    private SwipeRefreshLayout swipeRefreshLayout2;

    private ImageView iv_tianjiashangpin;
    private RelativeLayout all_shangjia;
    private RelativeLayout all_xiajia;
    private String fenlei;

//保存数据
private List<ShangPinList.Result.Products> list_shangpin1;
    private List<ShangPinList.Result> list_fenlei;
    private List<ShangPinList.Result.Products> list_shangpin;
//    private List<ShangPinFenLei.Result> list_fenlei;
//    private List<ShangPinList.Result> list_shangpin;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_supermarket,container,false);
        mContext=getActivity();
        myApp = GuardApplication.getContent();
        mHanlder = new Handler(getMainLooper());
        initView();
        initData();
        addListener();
//        ll_shopcar.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                showBottomSheet();
//            }
//        });
        return view;
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        if (!hidden){
            initData();
        }
        super.onHiddenChanged(hidden);
    }

    @Override
    public void onResume() {
        initData();
        super.onResume();
    }

    public void initView() {
        swipeRefreshLayout1= (SwipeRefreshLayout) view.findViewById(R.id.swipeRefreshLayout1);
        swipeRefreshLayout2= (SwipeRefreshLayout) view.findViewById(R.id.swipeRefreshLayout2);
        swipeRefreshLayout1.setOnRefreshListener(this);
        swipeRefreshLayout2.setOnRefreshListener(this);
        all_xiajia= (RelativeLayout) view.findViewById(R.id.all_xiajia);
        all_shangjia= (RelativeLayout) view.findViewById(R.id.all_shangjia);
        iv_tianjiashangpin= (ImageView) view.findViewById(R.id.iv_tianjiashangpin);
        iv_tianjiashangpin.setOnClickListener(this);
        all_xiajia.setOnClickListener(this);
        all_shangjia.setOnClickListener(this);
        lv_catogary = (ListView) view.findViewById(R.id.lv_catogary);
        lv_good = (ListView) view.findViewById(R.id.lv_good);
        tv_car = (TextView) view.findViewById(R.id.tv_car);
        //底部控件
        rl_bottom = (RelativeLayout) view.findViewById(R.id.rl_bottom);
        bv_unm = (TextView) view.findViewById(R.id.bv_unm);
//        tv_totle_money= (TextView) view.findViewById(R.id.tv_totle_money);
        ll_shopcar= (LinearLayout) view.findViewById(R.id.ll_shopcar);
        selectedList = new SparseArray<>();
        df = new DecimalFormat("0.00");
//        lv_catogary.setOnItemClickListener(this);
        lv_good.setOnItemClickListener(this);
    }
    //填充数据
    public void initData() {
//
//        //商品
//        for (int j=30;j<45;j++){
//            GoodsBean goodsBean = new GoodsBean();
//            goodsBean.setTitle("胡辣汤"+j);
//            goodsBean.setProduct_id(j);
//            goodsBean.setCategory_id(j);
//            goodsBean.setIcon("http://c.hiphotos.baidu.com/image/h%3D200/sign=5992ce78530fd9f9bf175269152cd42b/4ec2d5628535e5dd557b44db74c6a7efce1b625b.jpg");
//            goodsBean.setOriginal_price("200");
//            goodsBean.setPrice("100");
//            list3.add(goodsBean);
//        }
//
//        //商品
//        for (int j=5;j<10;j++){
//            GoodsBean goodsBean = new GoodsBean();
//            goodsBean.setTitle("胡辣汤"+j);
//            goodsBean.setProduct_id(j);
//            goodsBean.setCategory_id(j);
//            goodsBean.setIcon("http://e.hiphotos.baidu.com/image/h%3D200/sign=c898bddf19950a7b6a3549c43ad0625c/14ce36d3d539b600be63e95eed50352ac75cb7ae.jpg");
//            goodsBean.setOriginal_price("80");
//            goodsBean.setPrice("60");
//            list4.add(goodsBean);
//        }
//
//        //商品
//        for (int j=10;j<15;j++){
//            GoodsBean goodsBean = new GoodsBean();
//            goodsBean.setTitle("胡辣汤"+j);
//            goodsBean.setProduct_id(j);
//            goodsBean.setCategory_id(j);
//            goodsBean.setIcon("http://g.hiphotos.baidu.com/image/pic/item/03087bf40ad162d9ec74553b14dfa9ec8a13cd7a.jpg");
//            goodsBean.setOriginal_price("40");
//            goodsBean.setPrice("20");
//            list5.add(goodsBean);
//        }
//
//        CatograyBean catograyBean3 = new CatograyBean();
//        catograyBean3.setCount(3);
//        catograyBean3.setKind("江湖餐品"+3);
//        catograyBean3.setList(list3);
//        list.add(catograyBean3);
//
//        CatograyBean catograyBean4 = new CatograyBean();
//        catograyBean4.setCount(4);
//        catograyBean4.setKind("江湖餐品"+4);
//        catograyBean4.setList(list4);
//        list.add(catograyBean4);
//
//        CatograyBean catograyBean5 = new CatograyBean();
//        catograyBean5.setCount(5);
//        catograyBean5.setKind("江湖餐品"+5);
//        catograyBean5.setList(list5);
//        list.add(catograyBean5);
//
//        bottomSheetLayout = (BottomSheetLayout) view.findViewById(R.id.bottomSheetLayout);
//        //默认值
//        list2.clear();
//        list2.addAll(list.get(0).getList());
//
//        DialogUntil.showLoadingDialog(getContext(),"正在加载",true);
//        list_fenlei=new ArrayList<>();
//        String url= RequestURL.URL_PRODUCT_PRODUCTTYPE_LIST;
//        RequestCenter.product_productType_list(url, new DisposeDataListener() {
//            @Override
//            public void onSuccess(Object responseObj) {
//                ShangPinFenLei shangPinFenLei= (ShangPinFenLei) responseObj;
//                if (shangPinFenLei.getCode()== BianLiDianStatus.STATUS_CODE_SUCCESS){
//                    list_fenlei=shangPinFenLei.getResult();
//                    //分类
//                    catograyAdapter = new CatograyAdapter(mContext, list_fenlei);
//                    lv_catogary.setAdapter(catograyAdapter);
//                    catograyAdapter.notifyDataSetChanged();
//                }
//            }
//            @Override
//            public void onFailure(Object reasonObj) {
//
//            }
//        });
        DialogUntil.showLoadingDialog(getContext(),"正在加载中",true);
        list_shangpin=new ArrayList<>();
        String url_shangpin=RequestURL.URL_PRODUCT_LIST;
        RequestCenter.product_list(url_shangpin, new DisposeDataListener() {
            @Override
            public void onSuccess(Object responseObj) {
                DialogUntil.closeLoadingDialog();
                ShangPinList shangPinList= (ShangPinList) responseObj;
                if (shangPinList.getCode()==BianLiDianStatus.STATUS_CODE_SUCCESS){
                    //分类
                    list_fenlei=shangPinList.getResult();
                    if (list_fenlei.size()>0) {
                        fenlei = list_fenlei.get(0).getName();
                        catograyAdapter = new CatograyAdapter(mContext, list_fenlei);
                        lv_catogary.setAdapter(catograyAdapter);
                        catograyAdapter.notifyDataSetChanged();
                        //商品
                        list_shangpin = list_fenlei.get(0).getProducts();
                        goodsAdapter = new GoodsAdapter(mContext, ShoppFragment.this, list_shangpin, catograyAdapter);
                        lv_good.setAdapter(goodsAdapter);
                        goodsAdapter.notifyDataSetChanged();
                        showList();
                    }else {
                        showWuShuJu();
//                        MyUntil.show(getContext(),"请添加商品");
                    }
                }else if (shangPinList.getCode()==BianLiDianStatus.STATUS_CODE_ERROR_USER_NOTLOGIN){
                    LogOutUntil.logout(getContext());
                    MainActivity.instance.finish();
                }
            }
            @Override
            public void onFailure(Object reasonObj) {
                DialogUntil.closeLoadingDialog();
                showWuShuJu();

            }
        });
    }
    //添加监听
    private void addListener() {
        lv_catogary.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                fenlei = list_fenlei.get(position).getName();
                catograyAdapter.setSelection(position);
                catograyAdapter.notifyDataSetChanged();
//                list_shangpin.clear();
                list_shangpin=list_fenlei.get(position).getProducts();
                goodsAdapter=new GoodsAdapter(mContext,ShoppFragment.this, list_fenlei.get(position).getProducts(),catograyAdapter);
                lv_good.setAdapter(goodsAdapter);
            }
        });
    }
    //创建套餐详情view
    public void showDetailSheet(List<ItemBean> listItem, String mealName){

        bottomDetailSheet = createMealDetailView(listItem,mealName);
        if(bottomSheetLayout.isSheetShowing()){
            bottomSheetLayout.dismissSheet();
        }else {
            if(listItem.size()!=0){
                bottomSheetLayout.showWithSheetView(bottomDetailSheet);
            }
        }
        bottomSheetLayout.setPeekSheetTranslation(1700);
        bottomSheetLayout.setMinimumHeight(1500);
    }
    //查看套餐详情
    private View createMealDetailView(List<ItemBean> listItem, String mealName){
        isShow=true;
        onAttach(getActivity());
        View view = LayoutInflater.from(getContext()).inflate(R.layout.activity_super_goods_detail,(ViewGroup) getActivity().getWindow().getDecorView(),false);
        fanhui= (ImageView) view.findViewById(R.id.iv_shangminxiangqing_fanhui);
        tv_spName= (TextView) view.findViewById(R.id.tv_shangpinxiangqing_shangpinming);
        tv_biaoqian1= (TextView) view.findViewById(R.id.tv_shangpinxiangqing_xinpin);
        tv_biaoqian2= (TextView) view.findViewById(R.id.tv_shangpinxiangqing_xinpinguige);
        tv_danjia= (TextView) view.findViewById(R.id.tv_shangpinxiangqing_jiage);
        iv_jian= (ImageView) view.findViewById(R.id.iv_shangpinxiangqing_jian);
        iv_jia= (ImageView) view.findViewById(R.id.iv_shangpinxiangqing_jia);
        tv_shuliang= (TextView) view.findViewById(R.id.tv_shangpinxiangqing_count);
        tv_dianming= (TextView) view.findViewById(R.id.tv_dianming);
        tv_spjieshao= (TextView) view.findViewById(R.id.tv_shangpinxiangqing_shangpinjieshao);
        tv_shuliang1= (TextView) view.findViewById(R.id.tv_shangpinxiangqing_yuanjiaocount);
        tv_xuanhaole= (TextView) view.findViewById(R.id.tv_shangpinxiangqing_xuanhaole);
        tv_xuanhaole.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent=new Intent(mContext, PlaceOrderActivity.class);
//                startActivity(intent);
            }
        });


        tv_spjieshao.setText(R.string.jieshao);
        fanhui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetLayout.dismissSheet();
            }
        });
        tv_shuliang.setText(String.valueOf(list3.get(0).getNum()));
        tv_shuliang1.setText("sdasd");
        tv_shuliang1.setText(String.valueOf(list3.get(0).getNum()));

        iv_jia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handlerCarNum(1,list3.get(0),true);
                tv_shuliang.setText(String.valueOf(list3.get(0).getNum()));
                tv_shuliang1.setText(String.valueOf(list3.get(0).getNum()));
            }
        });
        iv_jian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handlerCarNum(0,list3.get(0),true);
                if (list3.get(0).getNum()>0){
                    tv_shuliang.setText(String.valueOf(list3.get(0).getNum()));
                    tv_shuliang1.setText(String.valueOf(list3.get(0).getNum()));
                }

            }
        });







//        ListView lv_product = (MyListView) view.findViewById(R.id.lv_product);
//        TextView tv_meal = (TextView) view.findViewById(R.id.tv_meal);
//        TextView tv_num = (TextView) view.findViewById(R.id.tv_num);
//        int count=0;
//        for(int i=0;i<listItem.size();i++){
//            count = count+ Integer.parseInt(listItem.get(i).getNote2());
//        }
//        tv_meal.setText(mealName);
//        tv_num.setText("(共"+count+"件)");
//        goodsDetailAdapter = new GoodsDetailAdapter(mContext,listItem);
//        lv_product.setAdapter(goodsDetailAdapter);
//        goodsDetailAdapter.notifyDataSetChanged();
        return view;
    }
    //创建购物车view
    private void showBottomSheet(){
        isShow=true;
        onAttach(getActivity());
        bottomSheet = createBottomSheetView();
        if(bottomSheetLayout.isSheetShowing()){
            bottomSheetLayout.dismissSheet();
        }else {
            if(selectedList.size()!=0){
                bottomSheetLayout.showWithSheetView(bottomSheet);
            }
        }
    }
    //查看购物车布局
    private View createBottomSheetView(){
        View view = LayoutInflater.from(mContext).inflate(R.layout.super_layout_bottom_sheet,(ViewGroup) getActivity().getWindow().getDecorView(),false);
        MyListView lv_product = (MyListView) view.findViewById(R.id.lv_product);
        tv_totle_money2= (TextView) view.findViewById(R.id.tv_totle_money2);

        tv_count= (TextView) view.findViewById(R.id.tv_count);
        tv_count.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent=new Intent(mContext,PlaceOrderActivity.class);
//                startActivity(intent);
            }
        });
        TextView clear = (TextView) view.findViewById(R.id.clear);
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearCart();
            }
        });
        productAdapter = new ProductAdapter(getContext(),ShoppFragment.this,goodsAdapter, selectedList);

        int size = selectedList.size();
        for(int i=0;i<size;i++){
            GoodsBean item = selectedList.valueAt(i);
            totleMoney += item.getNum()* Double.parseDouble(item.getPrice());
        }
        tv_totle_money2.setText("￥"+ String.valueOf(df.format(totleMoney)));
        totleMoney = 0.00;

        lv_product.setAdapter(productAdapter);
        return view;
    }
    //清空购物车
    public void clearCart(){
        selectedList.clear();
        list2.clear();
        if (list.size() > 0) {
            for (int j=0;j<list.size();j++){
                list.get(j).setCount(0);
                for(int i=0;i<list.get(j).getList().size();i++){
                    list.get(j).getList().get(i).setNum(0);
                }
            }
            list2.addAll(list.get(0).getList());
            catograyAdapter.setSelection(0);
            //刷新不能删
            catograyAdapter.notifyDataSetChanged();
            goodsAdapter.notifyDataSetChanged();
        }
        tv_totle_money.setText("￥"+ String.valueOf(0.00));
        totleMoney = 0.00;
        update(true);
    }
    //根据商品id获取当前商品的采购数量
    public int getSelectedItemCountById(int id){
        GoodsBean temp = selectedList.get(id);
        if(temp==null){
            return 0;
        }
        return temp.getNum();
    }
    public void handlerCarNum(int type, GoodsBean goodsBean, boolean refreshGoodList){
        if (type == 0) {
            GoodsBean temp = selectedList.get(goodsBean.getProduct_id());
            if(temp!=null){
                if(temp.getNum()<2){
                    goodsBean.setNum(0);
                    selectedList.remove(goodsBean.getProduct_id());

                }else{
                    int i =  goodsBean.getNum();
                    goodsBean.setNum(--i);
                }
            }

        } else if (type == 1) {
            GoodsBean temp = selectedList.get(goodsBean.getProduct_id());
            if(temp==null){
                goodsBean.setNum(1);
                selectedList.append(goodsBean.getProduct_id(), goodsBean);
            }else{
                int i= goodsBean.getNum();
                goodsBean.setNum(++i);
            }
        }

        update(refreshGoodList);

    }
    //刷新布局 总价、购买数量等
    private void update(boolean refreshGoodList){
        int size = selectedList.size();
        int count =0;
        for(int i=0;i<size;i++){
            GoodsBean item = selectedList.valueAt(i);
            count += item.getNum();
            totleMoney += item.getNum()* Double.parseDouble(item.getPrice());
        }
        tv_totle_money.setText("￥"+ String.valueOf(df.format(totleMoney)));
        if (tv_totle_money2!=null){
            tv_totle_money2.setText("￥"+ String.valueOf(df.format(totleMoney)));
        }
        totleMoney = 0.00;
        if(count<1){
            bv_unm.setVisibility(View.GONE);
        }else{
            bv_unm.setVisibility(View.VISIBLE);
        }

        bv_unm.setText(String.valueOf(count));

        if(productAdapter!=null){
            productAdapter.notifyDataSetChanged();
        }

        if(goodsAdapter!=null){
            goodsAdapter.notifyDataSetChanged();
        }

        if(catograyAdapter!=null){
            catograyAdapter.notifyDataSetChanged();
        }

//        if(bottomSheetLayout.isSheetShowing() && selectedList.size()<1){
//            bottomSheetLayout.dismissSheet();
//        }
    }
    /**
     * @Description: 创建动画层
     * @param
     * @return void
     * @throws
     */


    private ViewGroup createAnimLayout() {
        ViewGroup rootView = (ViewGroup) getActivity().getWindow().getDecorView();
        LinearLayout animLayout = new LinearLayout(mContext);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        animLayout.setLayoutParams(lp);
        animLayout.setId(Integer.MAX_VALUE-1);
        animLayout.setBackgroundResource(android.R.color.transparent);
        rootView.addView(animLayout);
        return animLayout;
    }

    private View addViewToAnimLayout(final ViewGroup parent, final View view,
                                     int[] location) {
        int x = location[0];
        int y = location[1];
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        lp.leftMargin = x;
        lp.topMargin = y;
        view.setLayoutParams(lp);
        return view;
    }

    public void setAnim(final View v, int[] startLocation) {
        anim_mask_layout = null;
        anim_mask_layout = createAnimLayout();
        anim_mask_layout.addView(v);//把动画小球添加到动画层
        final View view = addViewToAnimLayout(anim_mask_layout, v, startLocation);
        int[] endLocation = new int[2];// 存储动画结束位置的X、Y坐标
        tv_car.getLocationInWindow(endLocation);
        // 计算位移
        int endX = 0 - startLocation[0] + 40;// 动画位移的X坐标
        int endY = endLocation[1] - startLocation[1];// 动画位移的y坐标

        TranslateAnimation translateAnimationX = new TranslateAnimation(0,0, 0, 0);
        translateAnimationX.setInterpolator(new LinearInterpolator());
        translateAnimationX.setRepeatCount(0);// 动画重复执行的次数
        translateAnimationX.setFillAfter(true);

        TranslateAnimation translateAnimationY = new TranslateAnimation(0, 0, 0, endY);
        translateAnimationY.setInterpolator(new AccelerateInterpolator());
        translateAnimationY.setRepeatCount(0);// 动画重复执行的次数
        translateAnimationY.setFillAfter(true);

        AnimationSet set = new AnimationSet(false);
        set.setFillAfter(false);
        set.addAnimation(translateAnimationY);
        set.addAnimation(translateAnimationX);
        set.setDuration(800);// 动画的执行时间
        view.startAnimation(set);
        // 动画监听事件
        set.setAnimationListener(new Animation.AnimationListener() {
            // 动画的开始
            @Override
            public void onAnimationStart(Animation animation) {
                v.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                // TODO Auto-generated method stub
            }

            // 动画的结束
            @Override
            public void onAnimationEnd(Animation animation) {
                v.setVisibility(View.GONE);
            }
        });

    }

    public List<ItemBean> getListAll(){

        List<ItemBean> list_all=new ArrayList<ItemBean>();
        ItemBean itemBean=new ItemBean();
        itemBean.setKey("1");
        itemBean.setNote1("2");
        itemBean.setNote2("3");
        itemBean.setValue("4");
        list_all.add(itemBean);
        return list_all;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            //添加商品的按钮
            case R.id.iv_tianjiashangpin:
                Intent intent=new Intent(getContext(), ShangPinFenLeiActivity.class);
                startActivity(intent);
                break;
            //全部上架的按钮
            case R.id.all_shangjia:
                MyShangJiaRoXiaJiaDialog shangjiadialog=
                        new MyShangJiaRoXiaJiaDialog(getContext()
                                ,MyShangJiaRoXiaJiaDialog.SHANGJIA,goodsAdapter,list_shangpin);
                shangjiadialog.show();
                break;
            //全部下架的按钮
            case R.id.all_xiajia:
                MyShangJiaRoXiaJiaDialog xiajiadialog=
                        new MyShangJiaRoXiaJiaDialog(getContext()
                                ,MyShangJiaRoXiaJiaDialog.XIAJIA,goodsAdapter,list_shangpin);
                xiajiadialog.show();
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent=new Intent(getContext(), ShangPinXiangQingActivity.class);
        ShangPinList.Result.Products products = list_shangpin.get(position);
        Bundle bundle = new Bundle();
        bundle.putSerializable("products", products);
        intent.putExtras(bundle);
        intent.putExtra("TYPE",2);
        intent.putExtra("fenlei",fenlei);
        intent.putExtra("productTypeId",list_shangpin.get(position).getProductTypeId());

//        intent.putExtra("productTypeId",list_fenlei.get(position).getProducts().get(0).getId());
        Log.i("YYY",list_shangpin.get(position).getProductTypeId()+"");
        getContext().startActivity(intent);

        //商品
//        list_shangpin=list_fenlei.get(position).getProducts();
//        goodsAdapter = new GoodsAdapter(mContext,ShoppFragment.this,list_shangpin, catograyAdapter);
//        lv_good.setAdapter(goodsAdapter);
//        goodsAdapter.notifyDataSetChanged();
    }

    @Override
    public void onRefresh() {
        initData();
        swipeRefreshLayout1.setRefreshing(false);
        swipeRefreshLayout2.setRefreshing(false);
    }
    private void showList(){
        swipeRefreshLayout1.setVisibility(View.VISIBLE);
        swipeRefreshLayout2.setVisibility(View.GONE);
    }
    private void showWuShuJu(){
        swipeRefreshLayout1.setVisibility(View.GONE);
        swipeRefreshLayout2.setVisibility(View.VISIBLE);
    }

}
