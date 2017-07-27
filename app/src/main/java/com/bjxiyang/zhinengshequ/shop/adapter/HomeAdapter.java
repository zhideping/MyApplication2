package com.bjxiyang.zhinengshequ.shop.adapter;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.net.Uri;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.baisi.myapplication.okhttp.listener.DisposeDataListener;
import com.bjxiyang.zhinengshequ.shop.R;
import com.bjxiyang.zhinengshequ.shop.bluetooth.PrinterConnectDialog;
import com.bjxiyang.zhinengshequ.shop.manager.SPManager;
import com.bjxiyang.zhinengshequ.shop.status.BianLiDianStatus;
import com.bjxiyang.zhinengshequ.shop.model.DingDan;
import com.bjxiyang.zhinengshequ.shop.model.ShangJiaOrXiaJia;
import com.bjxiyang.zhinengshequ.shop.request.RequestURL;
import com.bjxiyang.zhinengshequ.shop.update.network.RequestCenter;
import com.bjxiyang.zhinengshequ.shop.util.Utility;
import com.gprinter.aidl.GpService;
import com.gprinter.command.EscCommand;
import com.gprinter.command.GpCom;
import com.gprinter.command.LabelCommand;
import com.gprinter.io.GpDevice;
import com.gprinter.service.GpPrintService;

import org.apache.commons.lang.ArrayUtils;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

/**
 * Created by Administrator on 2017/6/29 0029.
 */

public class HomeAdapter extends BaseAdapter {
    private GpService mGpService = null;
    public static final String CONNECT_STATUS = "connect.status";
    private static final String DEBUG_TAG = "MainActivity";
    private PrinterServiceConnection conn = null;
    private int mPrinterIndex = 0;
    private int mTotalCopies = 0;
    private DingDan.ResultBean resultBean;
    double zongjia=0;
    DecimalFormat df=new DecimalFormat("0.##");


    private Context mContext;
    private List<DingDan.ResultBean> mList;
    private int type;
    private int orderId;
    private List<DingDan.ResultBean.OrderInfoProductsBean> list=new ArrayList<>();

    public HomeAdapter(Context mContext, List<DingDan.ResultBean> mList,int type) {
        this.mContext = mContext;
        this.mList = mList;
        this.type=type;
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
        ViewHolder viewHolder=null;
        if (view==null){
            viewHolder=new ViewHolder();
            view= LayoutInflater.from(mContext).inflate(R.layout.item_dingdanguanli,null);
            viewHolder.xutuikuan= (TextView) view.findViewById(R.id.xutuikuan);
            viewHolder.dianhua= (ImageView) view.findViewById(R.id.dianhua);
            viewHolder.lv_dingdanguanli_xiangqing= (ListView) view.findViewById(R.id.lv_dingdanguanli_xiangqing);
            viewHolder.dingdanbianhao= (TextView) view.findViewById(R.id.dingdanbianhao);
            viewHolder.name= (TextView) view.findViewById(R.id.name);
            viewHolder.xiadanshijian= (TextView) view.findViewById(R.id.xiadanshijian);
            viewHolder.dizhi= (TextView) view.findViewById(R.id.dizhi);
            viewHolder.shangpinshuliang= (TextView) view.findViewById(R.id.shangpinshuliang);
            viewHolder.beizhu= (TextView) view.findViewById(R.id.beizhu);
            viewHolder.xiaoji= (TextView) view.findViewById(R.id.xiaoji);
            viewHolder.youhui= (TextView) view.findViewById(R.id.youhui);
            viewHolder.zongjishouru= (TextView) view.findViewById(R.id.zongjishouru);
            viewHolder.dingdanhao= (TextView) view.findViewById(R.id.dingdanhao);
            viewHolder.songdashijian= (TextView) view.findViewById(R.id.songdashijian);
            viewHolder.dingdanzhuangtai= (TextView) view.findViewById(R.id.dingdanzhuangtai);
            viewHolder.ll_zongji= (LinearLayout) view.findViewById(R.id.ll_zongji);
            viewHolder.ll_tuikuanjindu= (LinearLayout) view.findViewById(R.id.ll_tuikuanjindu);
            viewHolder.button_one= (TextView) view.findViewById(R.id.button_one);
            viewHolder.button_two= (TextView) view.findViewById(R.id.button_two);
            view.setTag(viewHolder);
        }else {
            viewHolder= (ViewHolder) view.getTag();
        }
        viewHolder.ll_tuikuanjindu.setVisibility(View.GONE);
        int status=mList.get(position).getOrderInfo().getStatus();
        viewHolder.dingdanbianhao.setText(mList.get(position).getOrderInfo().getOrderNo());
        viewHolder.name.setText(mList.get(position).getOrderInfo().getReceiver());
        viewHolder.xiadanshijian.setText(String.valueOf(mList.get(position).getOrderInfo().getCreateTime()));
        viewHolder.dizhi.setText(mList.get(position).getOrderInfo().getReceiveAddress());
        viewHolder.beizhu.setText(mList.get(position).getOrderInfo().getRemark());

        viewHolder.dingdanhao.setText(mList.get(position).getOrderInfo().getOrderNo()+"");
        viewHolder.songdashijian.setText(String.valueOf(mList.get(position).getOrderInfo().getExpectSendTime()));
//        viewHolder.dingdanzhuangtai.setText(mList.get(position).getOrderInfo().getStatus()+"");
        int count=0;
        zongjia=0;
        double youhuijiage=0;
        for (int i=0;i<mList.get(position).getOrderInfoProducts().size();i++){
            count+=mList.get(position).getOrderInfoProducts().get(i).getNum();
            zongjia=mList.get(position).getOrderInfoProducts().get(i).getPrice()
                    *mList.get(position).getOrderInfoProducts().get(i).getNum();
            youhuijiage=mList.get(position).getOrderInfoProducts().get(i).getAfterDiscountPrice()
                    *mList.get(position).getOrderInfoProducts().get(i).getNum();
        }
        viewHolder.xiaoji.setText(df.format((double)zongjia/100)+"");
        viewHolder.youhui.setText(df.format((double)(zongjia-youhuijiage)/100)+"");
        viewHolder.zongjishouru.setText(df.format((double)youhuijiage/100)+"");


        viewHolder.shangpinshuliang.setText(String.valueOf(count));

        final int position1=position;

        orderId = mList.get(position1).getOrderInfo().getId();
//        switch (type) {
//            case 0:
                switch (status) {
//                    case 10:
//                        viewHolder.button_two.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//                                DialogUntil.showLoadingDialog(mContext,"正在提交",true);
//                                String url_jiadan=RequestURL.URL_ORDER_ACCEPT+"orderId="
//                                        +mList.get(position1).getOrderInfo().getId();
//
//                                RequestCenter.order_accept(url_jiadan, new DisposeDataListener() {
//                                    @Override
//                                    public void onSuccess(Object responseObj) {
//                                        DialogUntil.closeLoadingDialog();
//                                        ShangJiaOrXiaJia res= (ShangJiaOrXiaJia) responseObj;
//                                        if (res.getCode()==BianLiDianStatus.STATUS_CODE_SUCCESS){
//                                            //访问成功的逻辑操作
//                                            mList.remove(position1);
//                                            notifyDataSetChanged();
//                                        }else {
//                                            MyUntil.show(mContext,res.getMsg());
//                                        }
//                                    }
//
//                                    @Override
//                                    public void onFailure(Object reasonObj) {
//                                        DialogUntil.closeLoadingDialog();
//
//                                    }
//                                });
//                            }
//                        });
//                        viewHolder.button_one.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//                                Log.i("YYYY",mList.get(position1).getOrderInfo().getId()+"6666");
//                                DialogUntil.showLoadingDialog(mContext,"正在提交",true);
//                                String url_jiadan=RequestURL.URL_ORDER_REFUSE+"orderId="
//                                        +mList.get(position1).getOrderInfo().getId();
//                                RequestCenter.order_refuse(url_jiadan, new DisposeDataListener() {
//                                    @Override
//                                    public void onSuccess(Object responseObj) {
//                                        DialogUntil.closeLoadingDialog();
//                                        ShangJiaOrXiaJia res= (ShangJiaOrXiaJia) responseObj;
//                                        if (res.getCode()== BianLiDianStatus.STATUS_CODE_SUCCESS){
//                                            //访问成功的逻辑操作
//
//                                        }else {
//                                            MyUntil.show(mContext,res.getMsg());
//                                        }
//                                    }
//
//                                    @Override
//                                    public void onFailure(Object reasonObj) {
//                                        DialogUntil.closeLoadingDialog();
//
//                                    }
//                                });
//                            }
//                        });
//                        break;
                    case 10:
                        break;
                    case 20:
                        viewHolder.ll_zongji.setVisibility(View.VISIBLE);
                        viewHolder.button_one.setVisibility(View.VISIBLE);
                        viewHolder.ll_tuikuanjindu.setVisibility(View.GONE);
                        viewHolder.dingdanzhuangtai.setText("已支付");
                        if (mList.get(position1).getOrderInfo().getSubStatus() != 0) {
                            fenzhi(viewHolder, position1);
                        } else {
                            Log.i("YYYY", "测试状态");
                            viewHolder.button_one.setText("拒单");
                            viewHolder.button_two.setText("接单");
                            final ViewHolder finalViewHolder1 = viewHolder;


                            viewHolder.button_two.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    mList.get(position1);
                                    Log.i("YYYY", "测试接单");
//                                    mainActivity.printSendDaYin(mList.get(position1));
                                    String url_jiadan = RequestURL.URL_ORDER_ACCEPT + "orderId="
                                            + mList.get(position1).getOrderInfo().getId();
                                    RequestCenter.order_accept(url_jiadan, new DisposeDataListener() {
                                        @Override
                                        public void onSuccess(Object responseObj) {



                                            resultBean=mList.get(position1);
                                            connection();
                                            mList.get(position1).getOrderInfo().setStatus(30);
                                            notifyDataSetChanged();
                                            finalViewHolder1.dingdanzhuangtai.setText("代发货");
                                            finalViewHolder1.button_two.setText("发货");

                                        }

                                        @Override
                                        public void onFailure(Object reasonObj) {

                                        }
                                    });
                                }
                            });
                            viewHolder.button_one.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Log.i("YYYY", "测试拒单");
                                    String url_judan = RequestURL.URL_ORDER_REFUSE + "orderId="
                                            + mList.get(position1).getOrderInfo().getId();
                                    RequestCenter.order_refuse(url_judan, new DisposeDataListener() {
                                        @Override
                                        public void onSuccess(Object responseObj) {
                                            mList.get(position1).getOrderInfo().setStatus(93);
                                            notifyDataSetChanged();
                                        }

                                        @Override
                                        public void onFailure(Object reasonObj) {

                                        }
                                    });
                                }
                            });
                        }
                        break;
                    case 30:
                        viewHolder.ll_zongji.setVisibility(View.VISIBLE);
                        viewHolder.dingdanzhuangtai.setText("配送中");
                        if (mList.get(position1).getOrderInfo().getSubStatus() != 0) {
                            fenzhi(viewHolder, position1);
                        } else {
                            viewHolder.button_one.setVisibility(View.GONE);
                            viewHolder.button_two.setText("已送达");
                            final ViewHolder finalViewHolder = viewHolder;
                            viewHolder.button_two.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    String url_jiadan = RequestURL.URL_SELLER_ORDER_REACHED + "orderId="
                                            + mList.get(position1).getOrderInfo().getId();
                                    RequestCenter.order_accept(url_jiadan, new DisposeDataListener() {
                                        @Override
                                        public void onSuccess(Object responseObj) {
                                            mList.get(position1).getOrderInfo().setStatus(40);
                                            notifyDataSetChanged();
                                            finalViewHolder.dingdanzhuangtai.setText("已送达");
                                            finalViewHolder.button_two.setText("待确认收货");
                                        }

                                        @Override
                                        public void onFailure(Object reasonObj) {

                                        }
                                    });
                                }
                            });
                            viewHolder.button_one.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    String url_judan = RequestURL.URL_ORDER_REFUSE + "orderId="
                                            + mList.get(position1).getOrderInfo().getId();
                                    RequestCenter.order_refuse(url_judan, new DisposeDataListener() {
                                        @Override
                                        public void onSuccess(Object responseObj) {

                                        }

                                        @Override
                                        public void onFailure(Object reasonObj) {

                                        }
                                    });
                                }
                            });
                        }
                        break;
                    case 50:
                        viewHolder.ll_zongji.setVisibility(View.VISIBLE);
                        viewHolder.dingdanzhuangtai.setText("已完成");
                        if (mList.get(position1).getOrderInfo().getSubStatus() != 0) {
                            fenzhi(viewHolder, position1);
                        } else {
                            //打印小票的方法
                            viewHolder.button_one.setVisibility(View.GONE);
                            viewHolder.button_two.setText("打印小票");

                            viewHolder.button_two.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    mList.get(position1).getOrderInfo().setStatus(100);
                                    notifyDataSetChanged();
                                    resultBean=mList.get(position1);
                                    connection();
//                                    String url_jiadan = RequestURL.URL_ORDER_ACCEPT + "orderId="
//                                            + mList.get(position1).getOrderInfo().getId();
//                                    RequestCenter.order_accept(url_jiadan, new DisposeDataListener() {
//                                        @Override
//                                        public void onSuccess(Object responseObj) {
//
//                                        }
//
//                                        @Override
//                                        public void onFailure(Object reasonObj) {
//
//                                        }
//                                    });
                                }
                            });
                            viewHolder.button_one.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    String url_judan = RequestURL.URL_ORDER_REFUSE + "orderId="
                                            + mList.get(position1).getOrderInfo().getId();
                                    RequestCenter.order_refuse(url_judan, new DisposeDataListener() {
                                        @Override
                                        public void onSuccess(Object responseObj) {

                                        }

                                        @Override
                                        public void onFailure(Object reasonObj) {

                                        }
                                    });
                                }
                            });
                        }
                        break;
                    case 40:
                        viewHolder.ll_zongji.setVisibility(View.VISIBLE);
                        viewHolder.dingdanzhuangtai.setText("已送达");
                        viewHolder.button_one.setVisibility(View.GONE);
                        viewHolder.button_one.setClickable(false);
                        viewHolder.button_two.setClickable(false);
                        viewHolder.button_two.setText("待确认收货");
                        break;

                    case 91:
                        fenzhi(viewHolder,position1);
                        break;
                    case 92:
                        fenzhi(viewHolder, position1);
                        break;
                    case 93:
                        fenzhi(viewHolder, position1);
                        break;
                    case 100:
                        break;
//                    case 100:
//                        viewHolder.ll_zongji.setVisibility(View.GONE);
//                        viewHolder.ll_tuikuanjindu.setVisibility(View.VISIBLE);
//                        viewHolder.button_one.setText("拒绝");
//                        viewHolder.button_two.setText("退款");
//                        viewHolder.button_two.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//                                String url_jujue_tuikuan=RequestURL.URL_ORDER_REBACK_CONFIRM+"orderId="
//                                        +mList.get(position1).getOrderInfo().getId();
//                                RequestCenter.order_reback_confirm(url_jujue_tuikuan, new DisposeDataListener() {
//                                    @Override
//                                    public void onSuccess(Object responseObj) {
//
//                                    }
//
//                                    @Override
//                                    public void onFailure(Object reasonObj) {
//
//                                    }
//                                });
//                            }
//                        });
//                        viewHolder.button_one.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//                                String url_tuikuan=RequestURL.URL_ORDER_REBACK_REFUSE+"orderId="
//                                        +mList.get(position1).getOrderInfo().getId();
//                                RequestCenter.order_reback_refuse(url_tuikuan, new DisposeDataListener() {
//                                    @Override
//                                    public void onSuccess(Object responseObj) {
//
//                                    }
//
//                                    @Override
//                                    public void onFailure(Object reasonObj) {
//
//                                    }
//                                });
//                            }
//                        });
//        viewHolder.shangpinshuliang.setText(mList.get(position).shangpinshuliang);
//                        break;
                    default:

                        break;
                }
//                break;
//
//
//            case 1:
//                viewHolder.dingdanzhuangtai.setText("已支付");
//                if (mList.get(position1).getOrderInfo().getSubStatus() != 0) {
//                    fenzhi(viewHolder, position1);
//                }else {
//                    viewHolder.button_one.setText("拒单");
//                    viewHolder.button_two.setText("接单");
//                viewHolder.button_two.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        Log.i("YYYY", orderId + "6666");
//                        DialogUntil.showLoadingDialog(mContext, "正在提交", true);
//                        String url_jiadan = RequestURL.URL_ORDER_ACCEPT + "orderId="
//                                + orderId;
//                        RequestCenter.order_accept(url_jiadan, new DisposeDataListener() {
//                            @Override
//                            public void onSuccess(Object responseObj) {
//                                DialogUntil.closeLoadingDialog();
//                                ShangJiaOrXiaJia res = (ShangJiaOrXiaJia) responseObj;
//                                if (res.getCode() == BianLiDianStatus.STATUS_CODE_SUCCESS) {
//                                    //访问成功的逻辑操作
//                                    mList.remove(position1);
//                                    notifyDataSetChanged();
//                                } else {
//                                    MyUntil.show(mContext, res.getMsg());
//                                }
//                            }
//
//                            @Override
//                            public void onFailure(Object reasonObj) {
//                                DialogUntil.closeLoadingDialog();
//
//                            }
//                        });
//                    }
//                });
//                viewHolder.button_one.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        Log.i("YYYY", mList.get(position1).getOrderInfo().getId() + "6666");
//                        DialogUntil.showLoadingDialog(mContext, "正在提交", true);
//                        String url_jiadan = RequestURL.URL_ORDER_REFUSE + "orderId="
//                                + orderId;
//                        RequestCenter.order_refuse(url_jiadan, new DisposeDataListener() {
//                            @Override
//                            public void onSuccess(Object responseObj) {
//                                DialogUntil.closeLoadingDialog();
//                                ShangJiaOrXiaJia res = (ShangJiaOrXiaJia) responseObj;
//                                if (res.getCode() == BianLiDianStatus.STATUS_CODE_SUCCESS) {
//
////                                    mainActivity.printSendDaYin(mList.get(position1));
//
//                                    //访问成功的逻辑操作
//                                    mList.get(position1).getOrderInfo().setStatus(30);
//                                    notifyDataSetChanged();
//                                } else {
//                                    MyUntil.show(mContext, res.getMsg());
//                                }
//                            }
//
//                            @Override
//                            public void onFailure(Object reasonObj) {
//                                DialogUntil.closeLoadingDialog();
//
//                            }
//                        });
//                    }
//                });
//                }
//                break;
//            case 2:
//                viewHolder.dingdanzhuangtai.setText("配送中");
//                if (mList.get(position1).getOrderInfo().getSubStatus() != 0) {
//                    fenzhi(viewHolder, position1);
//                }else {
//                    viewHolder.button_one.setVisibility(View.GONE);
//                    viewHolder.button_two.setText("已送达");
//                    viewHolder.button_two.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            String url_yisongda = RequestURL.URL_SELLER_ORDER_REACHED + "orderId="
//                                    + orderId;
//                            RequestCenter.seller_order_reached(url_yisongda, new DisposeDataListener() {
//                                @Override
//                                public void onSuccess(Object responseObj) {
//                                    ShangJiaOrXiaJia shangJiaOrXiaJia = (ShangJiaOrXiaJia) responseObj;
//                                    if (shangJiaOrXiaJia.getCode() == BianLiDianStatus.STATUS_CODE_SUCCESS) {
//                                        mList.remove(position1);
//                                        notifyDataSetChanged();
//                                    }
//                                }
//
//                                @Override
//                                public void onFailure(Object reasonObj) {
//
//                                }
//                            });
//
//                            Log.i("YYYY", "已完成按键测试");
//                        }
//                    });
//                }
////                viewHolder.button_two.setOnClickListener(new View.OnClickListener() {
////                    @Override
////                    public void onClick(View v) {
////                        String url_jiadan=RequestURL.URL_ORDER_ACCEPT+"orderId="
////                                +mList.get(position1).getOrderInfo().getId();
////                        RequestCenter.order_accept(url_jiadan, new DisposeDataListener() {
////                            @Override
////                            public void onSuccess(Object responseObj) {
////                                ShangJiaOrXiaJia res= (ShangJiaOrXiaJia) responseObj;
////                                if (res.getCode()== BianLiDianStatus.STATUS_CODE_SUCCESS){
////                                    //访问成功的逻辑操作
////                                    mList.remove(position1);
////                                    notifyDataSetChanged();
////                                }else {
////                                    MyUntil.show(mContext,res.getMsg());
////                                }
////                            }
////
////                            @Override
////                            public void onFailure(Object reasonObj) {
////
////                            }
////                        });
////                    }
////                });
////                viewHolder.button_one.setOnClickListener(new View.OnClickListener() {
////                    @Override
////                    public void onClick(View v) {
////                        String url_judan=RequestURL.URL_ORDER_REFUSE+"orderId="
////                                +mList.get(position1).getOrderInfo().getId();
////                        RequestCenter.order_refuse(url_judan, new DisposeDataListener() {
////                            @Override
////                            public void onSuccess(Object responseObj) {
////                                ShangJiaOrXiaJia res= (ShangJiaOrXiaJia) responseObj;
////                                if (res.getCode()== BianLiDianStatus.STATUS_CODE_SUCCESS){
////                                    //访问成功的逻辑操作
////                                    mList.remove(position1);
////                                    notifyDataSetChanged();
////
////                                }else {
////                                    MyUntil.show(mContext,res.getMsg());
////                                }
////                            }
////
////                            @Override
////                            public void onFailure(Object reasonObj) {
////
////                            }
////                        });
////                    }
////                });
//                break;
//            case 3:
//                viewHolder.dingdanzhuangtai.setText("已完成");
//                if (mList.get(position1).getOrderInfo().getSubStatus() != 0) {
//                    break;
//                }else {
//                    //打印小票的方法
//                    viewHolder.button_one.setVisibility(View.GONE);
//                    viewHolder.button_two.setText("打印小票");
//                    final View finalView = view;
//                    viewHolder.button_two.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            Log.i("YYYY", "打印小票按键测试");
//                            resultBean=mList.get(position1);
//                          connection();
//                        }
//                    });
//                }
////                viewHolder.button_two.setOnClickListener(new View.OnClickListener() {
////                    @Override
////                    public void onClick(View v) {
////                        String url_jiadan=RequestURL.URL_ORDER_ACCEPT+"orderId="
////                                +mList.get(position1).getOrderInfo().getId();
////                        RequestCenter.order_accept(url_jiadan, new DisposeDataListener() {
////                            @Override
////                            public void onSuccess(Object responseObj) {
////
////                            }
////
////                            @Override
////                            public void onFailure(Object reasonObj) {
////
////                            }
////                        });
////                    }
////                });
////                viewHolder.button_one.setOnClickListener(new View.OnClickListener() {
////                    @Override
////                    public void onClick(View v) {
////                        String url_judan=RequestURL.URL_ORDER_REFUSE+"orderId="
////                                +mList.get(position1).getOrderInfo().getId();
////                        RequestCenter.order_refuse(url_judan, new DisposeDataListener() {
////                            @Override
////                            public void onSuccess(Object responseObj) {
////
////                            }
////
////                            @Override
////                            public void onFailure(Object reasonObj) {
////
////                            }
////                        });
////                    }
////                });
//                break;
//            case 4:
//                fenzhi(viewHolder,position1);
//                break;
//            default:
//                break;
//        }
        viewHolder.dianhua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                Uri data = Uri.parse("tel:" + mList.get(position1).getOrderInfo().getReceivePhone());
                intent.setData(data);
                mContext.startActivity(intent);
            }
        });


//        viewHolder.dingdanbianhao.setText(mList.get(position).getOrderInfo().getOrderNo());
//        viewHolder.name.setText(mList.get(position).getOrderInfo().getReceiver());
//        viewHolder.xiadanshijian.setText(String.valueOf(mList.get(position).getOrderInfo().getCreateTime()));
//        viewHolder.dizhi.setText(mList.get(position).getOrderInfo().getReceiveAddress());
////        viewHolder.shangpinshuliang.setText(mList.get(position).shangpinshuliang);
//        viewHolder.beizhu.setText(mList.get(position).getOrderInfo().getRemark());
//        viewHolder.xiaoji.setText(mList.get(position).getOrderInfo().getTotalAmount());
//        viewHolder.youhui.setText(mList.get(position).getOrderInfo().getAfterDiscountAmount());
//        viewHolder.zongjishouru.setText(mList.get(position).getOrderInfo().getAfterDiscountAmount());
//        viewHolder.dingdanhao.setText(mList.get(position).getOrderInfo().getOrderNo());
//        viewHolder.songdashijian.setText(String.valueOf(mList.get(position).getOrderInfo().getExpectSendTime()));
//        viewHolder.dingdanzhuangtai.setText(mList.get(position).getOrderInfo().getStatus());
        list=mList.get(position).getOrderInfoProducts();
        HomeInsertAdapter adapter3=new HomeInsertAdapter(view.getContext(),list);
        viewHolder.lv_dingdanguanli_xiangqing.setAdapter(adapter3);
        Utility.setListViewHeightBasedOnChildren(viewHolder.lv_dingdanguanli_xiangqing);

        return view;
    }

    class ViewHolder{
        TextView dingdanbianhao;
        TextView name;
        TextView xiadanshijian;
        TextView dizhi;
        TextView shangpinshuliang;
        TextView beizhu;
        TextView xiaoji;
        TextView youhui;
        TextView zongjishouru;
        TextView dingdanhao;
        TextView songdashijian;
        TextView dingdanzhuangtai;
        ListView lv_dingdanguanli_xiangqing;
        LinearLayout ll_zongji;
        LinearLayout ll_tuikuanjindu;
        TextView button_one;
        TextView button_two;
        ImageView dianhua;
        TextView xutuikuan;

    }

    private void sub100(final ViewHolder viewHolder, int position){
        final int  position1= position;
        viewHolder.ll_zongji.setVisibility(View.GONE);
        viewHolder.ll_tuikuanjindu.setVisibility(View.GONE);
        viewHolder.button_one.setText("确认拒绝");
        viewHolder.button_two.setText("确认退货");
        viewHolder.button_two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url_jujue_tuikuan=RequestURL.URL_ORDER_REBACK_CONFIRM+"orderId="
                        +mList.get(position1).getOrderInfo().getId();
                RequestCenter.order_reback_confirm(url_jujue_tuikuan, new DisposeDataListener() {
                    @Override
                    public void onSuccess(Object responseObj) {
                        viewHolder.button_two.setText("已退货");

                    }

                    @Override
                    public void onFailure(Object reasonObj) {

                    }
                });
            }
        });
        viewHolder.button_one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url_tuikuan=RequestURL.URL_ORDER_REBACK_REFUSE+"orderId="
                        +mList.get(position1).getOrderInfo().getId();
                RequestCenter.order_reback_refuse(url_tuikuan, new DisposeDataListener() {
                    @Override
                    public void onSuccess(Object responseObj) {

                    }

                    @Override
                    public void onFailure(Object reasonObj) {

                    }
                });
            }
        });
    }
    private void sub200(final ViewHolder viewHolder, int position){
        final int  position1= position;
        viewHolder.ll_zongji.setVisibility(View.GONE);
        viewHolder.ll_tuikuanjindu.setVisibility(View.GONE);
        viewHolder.button_one.setText("确认拒绝");
        viewHolder.button_two.setText("确认退款");
        viewHolder.button_two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url_jujue_tuikuan=RequestURL.URL_ORDER_REFUND_CONFIRM+"orderId="
                        +mList.get(position1).getOrderInfo().getId();
                RequestCenter.order_refund_confirm(url_jujue_tuikuan, new DisposeDataListener() {
                    @Override
                    public void onSuccess(Object responseObj) {
                        viewHolder.button_two.setText("退款中");
                    }

                    @Override
                    public void onFailure(Object reasonObj) {

                    }
                });
            }
        });
        viewHolder.button_one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url_tuikuan=RequestURL.URL_ORDER_REFUND_REFUSE+"orderId="
                        +mList.get(position1).getOrderInfo().getId();
                RequestCenter.order_refund_refuse(url_tuikuan, new DisposeDataListener() {
                    @Override
                    public void onSuccess(Object responseObj) {

                    }

                    @Override
                    public void onFailure(Object reasonObj) {

                    }
                });
            }
        });
    }
    private void sub110(ViewHolder viewHolder, int position){
        final int position1=position;
        viewHolder.ll_zongji.setVisibility(View.GONE);
        viewHolder.ll_tuikuanjindu.setVisibility(View.GONE);
        viewHolder.button_one.setText("退货中");
        viewHolder.button_two.setText("确认收货");
        viewHolder.button_two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url_shouhuo=RequestURL.URL_ORDER_REBACK_RECEIVE+ "orderId="
                        + mList.get(position1).getOrderInfo().getId();
                RequestCenter.order_reback_receive(url_shouhuo, new DisposeDataListener() {
                    @Override
                    public void onSuccess(Object responseObj) {
                        ShangJiaOrXiaJia shangJiaOrXiaJia= (ShangJiaOrXiaJia) responseObj;
                        if (shangJiaOrXiaJia.getCode()==BianLiDianStatus.STATUS_CODE_SUCCESS){
                            mList.remove(position1);
                            notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onFailure(Object reasonObj) {

                    }
                });
            }
        });
//        viewHolder.button_two.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String url_jujue_tuikuan=RequestURL.URL_ORDER_REFUND_CONFIRM+"orderId="
//                        +mList.get(position1).getOrderInfo().getId();
//                RequestCenter.order_refund_confirm(url_jujue_tuikuan, new DisposeDataListener() {
//                    @Override
//                    public void onSuccess(Object responseObj) {
//
//                    }
//
//                    @Override
//                    public void onFailure(Object reasonObj) {
//
//                    }
//                });
//            }
//        });
//        viewHolder.button_one.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String url_tuikuan=RequestURL.URL_ORDER_REBACK_REFUSE+"orderId="
//                        +mList.get(position1).getOrderInfo().getId();
//                RequestCenter.order_reback_refuse(url_tuikuan, new DisposeDataListener() {
//                    @Override
//                    public void onSuccess(Object responseObj) {
//
//                    }
//
//                    @Override
//                    public void onFailure(Object reasonObj) {
//
//                    }
//                });
//            }
//        });
    }
    private void sub210(ViewHolder viewHolder, int position1){
        viewHolder.ll_zongji.setVisibility(View.GONE);
        viewHolder.ll_tuikuanjindu.setVisibility(View.GONE);
        viewHolder.button_one.setVisibility(View.GONE);
        viewHolder.button_two.setText("退款中");
//        viewHolder.button_two.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String url_jujue_tuikuan=RequestURL.URL_ORDER_REFUND_CONFIRM+"orderId="
//                        +mList.get(position1).getOrderInfo().getId();
//                RequestCenter.order_refund_confirm(url_jujue_tuikuan, new DisposeDataListener() {
//                    @Override
//                    public void onSuccess(Object responseObj) {
//
//                    }
//
//                    @Override
//                    public void onFailure(Object reasonObj) {
//
//                    }
//                });
//            }
//        });
//        viewHolder.button_one.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String url_tuikuan=RequestURL.URL_ORDER_REBACK_REFUSE+"orderId="
//                        +mList.get(position1).getOrderInfo().getId();
//                RequestCenter.order_reback_refuse(url_tuikuan, new DisposeDataListener() {
//                    @Override
//                    public void onSuccess(Object responseObj) {
//
//                    }
//
//                    @Override
//                    public void onFailure(Object reasonObj) {
//
//                    }
//                });
//            }
//        });
    }
    private void sub120(ViewHolder viewHolder, int position1){
        viewHolder.ll_zongji.setVisibility(View.GONE);
        viewHolder.ll_tuikuanjindu.setVisibility(View.GONE);
        viewHolder.button_one.setVisibility(View.GONE);
        viewHolder.button_two.setText("已退货");
//        viewHolder.button_two.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String url_jujue_tuikuan=RequestURL.URL_ORDER_REFUND_CONFIRM+"orderId="
//                        +mList.get(position1).getOrderInfo().getId();
//                RequestCenter.order_refund_confirm(url_jujue_tuikuan, new DisposeDataListener() {
//                    @Override
//                    public void onSuccess(Object responseObj) {
//
//                    }
//
//                    @Override
//                    public void onFailure(Object reasonObj) {
//
//                    }
//                });
//            }
//        });
//        viewHolder.button_one.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String url_tuikuan=RequestURL.URL_ORDER_REBACK_REFUSE+"orderId="
//                        +mList.get(position1).getOrderInfo().getId();
//                RequestCenter.order_reback_refuse(url_tuikuan, new DisposeDataListener() {
//                    @Override
//                    public void onSuccess(Object responseObj) {
//
//                    }
//
//                    @Override
//                    public void onFailure(Object reasonObj) {
//
//                    }
//                });
//            }
//        });
    }
    private void sub220(ViewHolder viewHolder, int position1){
        viewHolder.ll_zongji.setVisibility(View.GONE);
        viewHolder.ll_tuikuanjindu.setVisibility(View.GONE);
        viewHolder.button_one.setVisibility(View.GONE);
        viewHolder.button_two.setText("已退款");
//        viewHolder.button_two.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String url_jujue_tuikuan=RequestURL.URL_ORDER_REFUND_CONFIRM+"orderId="
//                        +mList.get(position1).getOrderInfo().getId();
//                RequestCenter.order_refund_confirm(url_jujue_tuikuan, new DisposeDataListener() {
//                    @Override
//                    public void onSuccess(Object responseObj) {
//
//                    }
//
//                    @Override
//                    public void onFailure(Object reasonObj) {
//
//                    }
//                });
//            }
//        });
//        viewHolder.button_one.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String url_tuikuan=RequestURL.URL_ORDER_REBACK_REFUSE+"orderId="
//                        +mList.get(position1).getOrderInfo().getId();
//                RequestCenter.order_reback_refuse(url_tuikuan, new DisposeDataListener() {
//                    @Override
//                    public void onSuccess(Object responseObj) {
//
//                    }
//
//                    @Override
//                    public void onFailure(Object reasonObj) {
//
//                    }
//                });
//            }
//        });
    }
    private void sub130(ViewHolder viewHolder, int position1){
        viewHolder.ll_zongji.setVisibility(View.GONE);
        viewHolder.ll_tuikuanjindu.setVisibility(View.GONE);
        viewHolder.button_one.setVisibility(View.GONE);
        viewHolder.button_two.setText("退货拒绝");
//        viewHolder.button_two.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String url_jujue_tuikuan=RequestURL.URL_ORDER_REFUND_CONFIRM+"orderId="
//                        +mList.get(position1).getOrderInfo().getId();
//                RequestCenter.order_refund_confirm(url_jujue_tuikuan, new DisposeDataListener() {
//                    @Override
//                    public void onSuccess(Object responseObj) {
//
//                    }
//
//                    @Override
//                    public void onFailure(Object reasonObj) {
//
//                    }
//                });
//            }
//        });
//        viewHolder.button_one.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String url_tuikuan=RequestURL.URL_ORDER_REBACK_REFUSE+"orderId="
//                        +mList.get(position1).getOrderInfo().getId();
//                RequestCenter.order_reback_refuse(url_tuikuan, new DisposeDataListener() {
//                    @Override
//                    public void onSuccess(Object responseObj) {
//
//                    }
//
//                    @Override
//                    public void onFailure(Object reasonObj) {
//
//                    }
//                });
//            }
//        });
    }
    private void sub230(ViewHolder viewHolder, int position1){
        viewHolder.ll_zongji.setVisibility(View.GONE);
        viewHolder.ll_tuikuanjindu.setVisibility(View.GONE);
        viewHolder.button_one.setVisibility(View.GONE);
        viewHolder.button_two.setText("退款拒绝");
//        viewHolder.button_two.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String url_jujue_tuikuan=RequestURL.URL_ORDER_REFUND_CONFIRM+"orderId="
//                        +mList.get(position1).getOrderInfo().getId();
//                RequestCenter.order_refund_confirm(url_jujue_tuikuan, new DisposeDataListener() {
//                    @Override
//                    public void onSuccess(Object responseObj) {
//
//                    }
//
//                    @Override
//                    public void onFailure(Object reasonObj) {
//
//                    }
//                });
//            }
//        });
//        viewHolder.button_one.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String url_tuikuan=RequestURL.URL_ORDER_REBACK_REFUSE+"orderId="
//                        +mList.get(position1).getOrderInfo().getId();
//                RequestCenter.order_reback_refuse(url_tuikuan, new DisposeDataListener() {
//                    @Override
//                    public void onSuccess(Object responseObj) {
//
//                    }
//
//                    @Override
//                    public void onFailure(Object reasonObj) {
//
//                    }
//                });
//            }
//        });
    }
    private void fenzhi(ViewHolder viewHolder,int position1){
        viewHolder.xutuikuan.setText(String.valueOf(df.format(zongjia/100)));
        switch (mList.get(position1).getOrderInfo().getSubStatus()){
            case 100:
                viewHolder.dingdanzhuangtai.setText("退货申请");
                sub100(viewHolder,position1);
                break;
            case 200:
                viewHolder.dingdanzhuangtai.setText("退款申请");
                sub200(viewHolder,position1);
                break;
            case 110:
                viewHolder.dingdanzhuangtai.setText("退货中");
                sub110(viewHolder,position1);
                break;
            case 210:
                viewHolder.dingdanzhuangtai.setText("退款中");
                sub210(viewHolder,position1);
                break;
            case 120:
                viewHolder.dingdanzhuangtai.setText("已退货");
                sub120(viewHolder,position1);
                break;
            case 220:
                viewHolder.dingdanzhuangtai.setText("已退款");
                sub220(viewHolder,position1);
                break;
            case 130:
                viewHolder.dingdanzhuangtai.setText("退货拒绝");
                sub130(viewHolder,position1);
                break;
            case 230:
                viewHolder.dingdanzhuangtai.setText("退款拒绝");
                sub230(viewHolder,position1);
                break;
            case 0:
                break;
            default:
                break;
        }
    }
    class PrinterServiceConnection implements ServiceConnection {
        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.i("ServiceConnection", "onServiceDisconnected() called");
            mGpService = null;
        }

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mGpService = GpService.Stub.asInterface(service);
            printSendDaYin();
        }
    }
    private void connection() {
        conn = new PrinterServiceConnection();
        Intent intent = new Intent(mContext, GpPrintService.class);
        mContext.bindService(intent, conn, Context.BIND_AUTO_CREATE); // bindService
    }
    public void printSendDaYin() {
        try {
            int type = mGpService.getPrinterCommandType(mPrinterIndex);
            if (type == GpCom.ESC_COMMAND) {
                sendDaYin();
            }
        } catch (RemoteException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
    }
    public void sendDaYin(){
        EscCommand esc = new EscCommand();

        esc.addSelectJustification(EscCommand.JUSTIFICATION.CENTER);// 设置打印居中
        esc.addText("******智社区订单******\n\n");
        esc.addSelectJustification(EscCommand.JUSTIFICATION.CENTER);// 设置打印居中
        esc.addSelectPrintModes(EscCommand.FONT.FONTA, EscCommand.ENABLE.OFF, EscCommand.ENABLE.ON, EscCommand.ENABLE.ON, EscCommand.ENABLE.OFF);// 设置为倍高倍宽
        esc.addText(SPManager.getInstance().getString("ShopName","")+"\n");

        esc.addPrintAndFeedLines((byte) 1);
        esc.addSelectJustification(EscCommand.JUSTIFICATION.CENTER);// 设置打印居中
        esc.addSelectPrintModes(EscCommand.FONT.FONTA, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF, EscCommand.ENABLE.ON, EscCommand.ENABLE.OFF);// 设置为倍高倍宽
        esc.addText("---在线支付---\n"); // 打印文字
        esc.addPrintAndLineFeed();

		/* 打印文字 */
        esc.addSelectPrintModes(EscCommand.FONT.FONTA, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF);// 取消倍高倍宽
        esc.addSelectJustification(EscCommand.JUSTIFICATION.LEFT);// 设置打印左对齐
        esc.addText("下单时间:"+resultBean.getOrderInfo().getOrderNo()+"\n");
        esc.addText("下单时间:"+resultBean.getOrderInfo().getCreateTime()+"\n"); // 打印文字
        double allprice=0;
        for (int i=0;i<resultBean.getOrderInfoProducts().size();i++){
            esc.addText("商品名\t\t数量\t单价\n");
            esc.addText(resultBean.getOrderInfoProducts().get(i).getName()+"\t\t"
                    +"*"+resultBean.getOrderInfoProducts().get(i).getNum()+"\t"
                    +(double)resultBean.getOrderInfoProducts().get(i).getPrice()+"\n");

            allprice+=resultBean.getOrderInfoProducts().get(i).getNum()*resultBean.getOrderInfoProducts().get(i).getPrice();
        }
        esc.addText("总计\t"+allprice+"\n");
//        esc.addText("已付\t"+allprice+"\n");
        esc.addSelectJustification(EscCommand.JUSTIFICATION.CENTER);// 设置打印居中
        esc.addText("--------------------\n");
        esc.addSelectJustification(EscCommand.JUSTIFICATION.LEFT);// 设置打印左对齐
        esc.addText(resultBean.getOrderInfo().getReceiveAddress()+"\n");
        esc.addText("收货人:"+resultBean.getOrderInfo().getReceiver()+"\n");
        esc.addText("手机号:"+resultBean.getOrderInfo().getReceivePhone()+"\n\n");
        esc.addText("*********完********");
//        esc.addText(resultBean.getOrderInfo()"\n"); // 打印文字

		/* 打印繁体中文 需要打印机支持繁体字库 */
//        String message = Util.SimToTra("佳博票据打印机\n");
        // esc.addText(message,"BIG5");
//        esc.addText(message, "GB2312");
//        esc.addPrintAndLineFeed();

		/* 绝对位置 具体详细信息请查看GP58编程手册*/
//        esc.addText("你好啊");
//        esc.addSetHorAndVerMotionUnits((byte) 7, (byte) 0);
//        esc.addSetAbsolutePrintPosition((short) 6);
//        esc.addText("我不好");
//        esc.addSetAbsolutePrintPosition((short) 10);
//        esc.addText("我很好");
//        esc.addPrintAndLineFeed();

		/* 打印图片 */
//        esc.addText("Print bitmap!\n"); // 打印文字
//        Bitmap b = BitmapFactory.decodeResource(getResources(), R.drawable.gprinter);
//        esc.addRastBitImage(b, b.getWidth(), 0); // 打印图片

		/* 打印一维条码 */
//        esc.addText("Print code128\n"); // 打印文字
//        esc.addSelectPrintingPositionForHRICharacters(HRI_POSITION.BELOW);// 设置条码可识别字符位置在条码下方
//        esc.addSetBarcodeHeight((byte) 60); // 设置条码高度为60点
//        esc.addSetBarcodeWidth((byte) 1); // 设置条码单元宽度为1
//        esc.addCODE128(esc.genCodeB("Gprinter")); // 打印Code128码
//        esc.addPrintAndLineFeed();

		/*
         * QRCode命令打印 此命令只在支持QRCode命令打印的机型才能使用。 在不支持二维码指令打印的机型上，则需要发送二维条码图片
		 */
        // esc.addText("Print QRcode\n"); // 打印文字
        // esc.addSelectErrorCorrectionLevelForQRCode((byte)0x31); //设置纠错等级
        // esc.addSelectSizeOfModuleForQRCode((byte)3);//设置qrcode模块大小
        // esc.addStoreQRCodeData("www.gprinter.com.cn");//设置qrcode内容
        // esc.addPrintQRCode();//打印QRCode
        // esc.addPrintAndLineFeed();

		/* 打印文字 */
        esc.addSelectJustification(EscCommand.JUSTIFICATION.CENTER);// 设置打印左对齐
//        esc.addText("Completed!\r\n"); // 打印结束
//        esc.addGeneratePlus(LabelCommand.FOOT.F5, (byte) 255, (byte) 255);
        esc.addGeneratePluseAtRealtime(LabelCommand.FOOT.F2, (byte) 8);

        esc.addPrintAndFeedLines((byte) 8);

        Vector<Byte> datas = esc.getCommand(); // 发送数据
        Byte[] Bytes = datas.toArray(new Byte[datas.size()]);
        Log.d("diaonilaomu", datas.toString());
        byte[] bytes = ArrayUtils.toPrimitive(Bytes);
        String sss = Base64.encodeToString(bytes, Base64.DEFAULT);
        int rs;
        try {
            rs = mGpService.sendEscCommand(mPrinterIndex, sss);
            GpCom.ERROR_CODE r = GpCom.ERROR_CODE.values()[rs];
            if (r != GpCom.ERROR_CODE.SUCCESS) {

                Intent bluetooth = new Intent(mContext,PrinterConnectDialog.class);
                boolean[] state =getConnectState();
                bluetooth.putExtra(CONNECT_STATUS, state);
                mContext.startActivity(bluetooth);
//                Toast.makeText(mContext, GpCom.getErrorText(r), Toast.LENGTH_SHORT).show();
            }
        } catch (RemoteException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
    public boolean[] getConnectState() {
        boolean[] state = new boolean[GpPrintService.MAX_PRINTER_CNT];
        for (int i = 0; i < GpPrintService.MAX_PRINTER_CNT; i++) {
            state[i] = false;
        }
        for (int i = 0; i < GpPrintService.MAX_PRINTER_CNT; i++) {
            try {
                if (mGpService.getPrinterConnectStatus(i) == GpDevice.STATE_CONNECTED) {
                    state[i] = true;
                }
            } catch (RemoteException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return state;
    }

}
