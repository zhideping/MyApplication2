package com.bjxiyang.zhinengshequ.shop.home_fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.baisi.myapplication.okhttp.listener.DisposeDataListener;
import com.bjxiyang.zhinengshequ.shop.R;
import com.bjxiyang.zhinengshequ.shop.activity.MainActivity;
import com.bjxiyang.zhinengshequ.shop.adapter.HomeAdapter;
import com.bjxiyang.zhinengshequ.shop.status.BianLiDianStatus;
import com.bjxiyang.zhinengshequ.shop.model.DingDan;
import com.bjxiyang.zhinengshequ.shop.request.RequestURL;
import com.bjxiyang.zhinengshequ.shop.update.network.RequestCenter;
import com.bjxiyang.zhinengshequ.shop.util.LogOutUntil;
import com.bjxiyang.zhinengshequ.shop.util.MyUntil;

import java.util.List;

/**
 * Created by Administrator on 2017/6/29 0029.
 */

public class FragmentOne extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private static final int TYPE_ONE=0;
    private SwipeRefreshLayout swipe;
    private View view;
    private ListView mListView;
    private HomeAdapter adapter;
    private Context mContext;
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
        mListView= (ListView) view.findViewById(R.id.lv_home);
        swipe.setOnRefreshListener(this);
    }
    private void initData() {
        getmList();
    }
    private List<DingDan.ResultBean> mList;
    public void getmList(){
        String url= RequestURL.URL_ORDER_LIST+"type="+TYPE_ONE;
        RequestCenter.order_list(url, new DisposeDataListener() {
            @Override
            public void onSuccess(Object responseObj) {
                DingDan dingDan= (DingDan) responseObj;
                if (dingDan.getCode()== BianLiDianStatus.STATUS_CODE_SUCCESS){
                    mList=dingDan.getResult();

                    for (int i=mList.size()-1;i>=0;i--){
                            switch (mList.get(i).getOrderInfo().getStatus()){
                                case 10:
                                case 91:
                                case 92:
                                case 93:
                                    mList.remove(i);
//                                    break;
//                                default:
//                                    break;
                            }
                    }
                    adapter=new HomeAdapter(mContext,mList,TYPE_ONE);
                    mListView.setAdapter(adapter);
                }else if (dingDan.getCode()==BianLiDianStatus.STATUS_CODE_ERROR_USER_NOTLOGIN){
                    LogOutUntil.logout(getContext());
                    MainActivity.instance.finish();
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
