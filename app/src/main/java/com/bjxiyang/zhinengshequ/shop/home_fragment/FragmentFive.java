package com.bjxiyang.zhinengshequ.shop.home_fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.baisi.myapplication.okhttp.listener.DisposeDataListener;
import com.bjxiyang.zhinengshequ.shop.R;
import com.bjxiyang.zhinengshequ.shop.adapter.HomeAdapter;
import com.bjxiyang.zhinengshequ.shop.status.BianLiDianStatus;
import com.bjxiyang.zhinengshequ.shop.model.DingDan;
import com.bjxiyang.zhinengshequ.shop.request.RequestURL;
import com.bjxiyang.zhinengshequ.shop.update.network.RequestCenter;
import com.bjxiyang.zhinengshequ.shop.util.MyUntil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/6/29 0029.
 */

public class FragmentFive extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private static final int TYPE_FIVE=4;
    private static final int TYPE_SIX=5;
    private List<DingDan.ResultBean> mList;
    private List<DingDan.ResultBean> mListAll;

    private View view;
    private ListView mListView;
    private HomeAdapter adapter;
    private Context mContext;
    private SwipeRefreshLayout swipe;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.home_fragment_select,container,false);
        mContext=getActivity();
        initUI();
        initData();
        return view;
    }
    private void initUI(){
        swipe= (SwipeRefreshLayout) view.findViewById(R.id.swipe);
        swipe.setOnRefreshListener(this);
        mListView= (ListView) view.findViewById(R.id.lv_home);
    }
    private void initData() {
        getmList();
    }
    public void getmList(){
        mListAll=new ArrayList<>();
        String url= RequestURL.URL_ORDER_LIST+"type="+TYPE_FIVE;
        RequestCenter.order_list(url, new DisposeDataListener() {
            @Override
            public void onSuccess(Object responseObj) {
                DingDan dingDan= (DingDan) responseObj;
                if (dingDan.getCode()== BianLiDianStatus.STATUS_CODE_SUCCESS){
                    mList=dingDan.getResult();
                    if (mList.size()>0){
                        mListAll.addAll(mList);
                    }

//                    adapter=new HomeAdapter(mContext,mList,TYPE_FIVE);
//                    mListView.setAdapter(adapter);
                    String url_six= RequestURL.URL_ORDER_LIST+"type="+TYPE_SIX;
                    RequestCenter.order_list(url_six, new DisposeDataListener() {
                        @Override
                        public void onSuccess(Object responseObj) {
                            DingDan dingDan= (DingDan) responseObj;
                            if (dingDan.getCode()== BianLiDianStatus.STATUS_CODE_SUCCESS){
                                mList=dingDan.getResult();
                                if (mList.size()>0){
                                    Log.i("YYYY","检测FragmentFive");
                                    mListAll.addAll(mList);
                                }
                                adapter=new HomeAdapter(mContext,mListAll,TYPE_FIVE);
                                mListView.setAdapter(adapter);
                            }else {
                                MyUntil.show(getContext(),dingDan.getMsg());
                            }

                        }
                        @Override
                        public void onFailure(Object reasonObj) {

                        }
                    });




                }else {
                    MyUntil.show(getContext(),dingDan.getMsg());
                }

            }
            @Override
            public void onFailure(Object reasonObj) {

            }
        });

    }
    @Override
    public void onRefresh() {
        initData();
        swipe.setRefreshing(false);
    }
}
