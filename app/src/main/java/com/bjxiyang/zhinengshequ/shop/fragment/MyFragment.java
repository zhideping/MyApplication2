package com.bjxiyang.zhinengshequ.shop.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baisi.myapplication.okhttp.listener.DisposeDataListener;
import com.bjxiyang.zhinengshequ.shop.R;
import com.bjxiyang.zhinengshequ.shop.activity.DianZhuXinXiActivity;
import com.bjxiyang.zhinengshequ.shop.activity.ShangJiaXinXiActivity;
import com.bjxiyang.zhinengshequ.shop.bluetooth.MainActivity;
import com.bjxiyang.zhinengshequ.shop.bluetooth.PrinterConnectDialog;
import com.bjxiyang.zhinengshequ.shop.model.UpdateVersion;
import com.bjxiyang.zhinengshequ.shop.update.CommonDialog;
import com.bjxiyang.zhinengshequ.shop.update.network.RequestCenter;
import com.bjxiyang.zhinengshequ.shop.update.service.UpdateService;
import com.bjxiyang.zhinengshequ.shop.update.util.Util;
import com.bjxiyang.zhinengshequ.shop.util.DialogUntil;
import com.bjxiyang.zhinengshequ.shop.util.LogOutUntil;
import com.bjxiyang.zhinengshequ.shop.view.CircleImageView;

/**
 * Created by Administrator on 2017/6/29 0029.
 */

public class MyFragment extends MainActivity implements View.OnClickListener{
    private View view;

    /**
     * UI
     */
    private CircleImageView dianpu_touxiang;
    private ImageView iv_dianpu_yingyezhong;
    private ImageView iv_dianpu_connected;
    private TextView tv_dianpu_yingyezhong;
    private TextView tv_dianpu_connected;
    private LinearLayout ll_shangjiaxinxi;
    private LinearLayout ll_dianzhuxinxi;
    private Button siginoutbutton;
//    private Button jianchagengxin;
    private LinearLayout dianpu_update;


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_dianpu,container,false);
        initUi();
        return view;
    }

    private void initUi() {
        dianpu_update= (LinearLayout) view.findViewById(R.id.dianpu_update);
        dianpu_update.setOnClickListener(this);
//        jianchagengxin = (Button) view.findViewById(R.id.jianchagengxin);
        tv_dianpu_connected= (TextView) view.findViewById(R.id.tv_dianpu_connected);
        tv_dianpu_yingyezhong= (TextView) view.findViewById(R.id.tv_dianpu_yingyezhong);
        dianpu_touxiang= (CircleImageView) view.findViewById(R.id.dianpu_touxiang);
        iv_dianpu_yingyezhong= (ImageView) view.findViewById(R.id.iv_dianpu_yingyezhong);
        iv_dianpu_connected= (ImageView) view.findViewById(R.id.iv_dianpu_connected);
        ll_shangjiaxinxi= (LinearLayout) view.findViewById(R.id.ll_shangjiaxinxi);
        ll_dianzhuxinxi= (LinearLayout) view.findViewById(R.id.ll_dianzhuxinxi);
        siginoutbutton= (Button) view.findViewById(R.id.siginoutbutton);
//        jianchagengxin.setOnClickListener(this);
        iv_dianpu_yingyezhong.setOnClickListener(this);
        iv_dianpu_connected.setOnClickListener(this);
        ll_shangjiaxinxi.setOnClickListener(this);
        ll_dianzhuxinxi.setOnClickListener(this);
        siginoutbutton.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            //店铺营业状态的按键
            case R.id.iv_dianpu_yingyezhong:

                break;
            //店铺打印机的按键
            case R.id.iv_dianpu_connected:
                Intent bluetooth = new Intent(getContext(),PrinterConnectDialog.class);
                boolean[] state = getConnectState();
                bluetooth.putExtra(CONNECT_STATUS, state);
                startActivity(bluetooth);
                break;
            //商家信息的按键
            case R.id.ll_shangjiaxinxi:
                Intent intent=new Intent(getContext(), ShangJiaXinXiActivity.class);
                startActivity(intent);
                break;
            //店铺信息的按键
            case R.id.ll_dianzhuxinxi:
                Intent intent1=new Intent(getContext(), DianZhuXinXiActivity.class);
                startActivity(intent1);
                break;
            //检查更新的按键
//            case R.id.jianchagengxin:
//                checkVersion();
//                break;
            case R.id.dianpu_update:
                checkVersion();
                break;

            //退出的按键
            case R.id.siginoutbutton:
                LogOutUntil.logout(getContext());
                break;
        }
    }
    //检查更新代码
    private void checkVersion() {
        DialogUntil.showLoadingDialog(getContext(),"正在检查更新",true);
        RequestCenter.checkVersion(new DisposeDataListener() {
            @Override
            public void onSuccess(Object responseObj) {
                DialogUntil.closeLoadingDialog();

                final UpdateVersion updateModel = (UpdateVersion) responseObj;

                if (updateModel.getCode().equals("1000")) {
                    UpdateVersion.ObjBean obj=updateModel.getObj();
                    if (Double.valueOf(Util.getVersionCode(getActivity()))<Double.valueOf(obj.getVerNo())) {
                        //说明有新版本,开始下载
                        CommonDialog dialog = new CommonDialog(getActivity(),
                                getString(R.string.update_new_version),
                                obj.getVerDescript(),
                                getString(R.string.cancel),
                                getString(R.string.update_install),
                                new CommonDialog.DialogClickListener() {
                                    @Override
                                    public void onDialogClick() {
                                        Intent intent = new Intent(getActivity(), UpdateService.class);
                                        intent.putExtra("APPURL", updateModel.getObj().getVerUrl());
                                        getActivity().startService(intent);
                                    }
                                });
                        dialog.setCancelable(false);
                        dialog.show();
                    } else {
                        AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
                        dialog.setTitle("提示");
                        dialog.setCancelable(false);
                        dialog.setMessage("该版本已是最新版本");
                        dialog.setIcon(R.mipmap.ic_launcher);
                        dialog.setNegativeButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // TODO Auto-generated method stub
                            }
                        });
                        dialog.show();
                        //弹出一个toast提示当前已经是最新版本等处理
                    }
                }else {
//                    MyUntil.show(getContext(),updateModel.getMsg());
                }
            }

            @Override
            public void onFailure(Object reasonObj) {
                DialogUntil.closeLoadingDialog();

                AlertDialog.Builder builder=new AlertDialog.Builder(getContext());
                AlertDialog dialog=builder
                        .setIcon(R.mipmap.ic_launcher)
                        .setTitle("检查更新")
                        .setMessage("网络连接失败")
                        .setPositiveButton("确定", null)
                        .show();

            }
        });
    }
}
