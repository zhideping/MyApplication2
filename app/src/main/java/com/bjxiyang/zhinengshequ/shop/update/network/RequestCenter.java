package com.bjxiyang.zhinengshequ.shop.update.network;


import com.baisi.myapplication.okhttp.CommonOkHttpClient;
import com.baisi.myapplication.okhttp.listener.DisposeDataHandle;
import com.baisi.myapplication.okhttp.listener.DisposeDataListener;
import com.baisi.myapplication.okhttp.listener.DisposeDownloadListener;
import com.baisi.myapplication.okhttp.request.CommonRequest;
import com.baisi.myapplication.okhttp.request.RequestParams;
import com.bjxiyang.zhinengshequ.shop.model.AddFenLei;
import com.bjxiyang.zhinengshequ.shop.model.CreateDianZhu;
import com.bjxiyang.zhinengshequ.shop.model.DianZhuUpdate;
import com.bjxiyang.zhinengshequ.shop.model.DingDan;
import com.bjxiyang.zhinengshequ.shop.model.GetDianZhu;
import com.bjxiyang.zhinengshequ.shop.model.ImageUrl;
import com.bjxiyang.zhinengshequ.shop.model.Login;
import com.bjxiyang.zhinengshequ.shop.model.Logout;
import com.bjxiyang.zhinengshequ.shop.model.ShangJiaOrXiaJia;
import com.bjxiyang.zhinengshequ.shop.model.ShangJiaSave;
import com.bjxiyang.zhinengshequ.shop.model.ShangJiaXinXi;
import com.bjxiyang.zhinengshequ.shop.model.ShangPinAdd;
import com.bjxiyang.zhinengshequ.shop.model.ShangPinFenLei;
import com.bjxiyang.zhinengshequ.shop.model.ShangPinList;
import com.bjxiyang.zhinengshequ.shop.model.ShangPinXiangQing;
import com.bjxiyang.zhinengshequ.shop.model.UpdateVersion;
import com.bjxiyang.zhinengshequ.shop.request.RequestURL;
import com.bjxiyang.zhinengshequ.shop.update.util.GetHeaders;

import java.util.Map;

/**
 * Created by gll on 17-3-9.
 * 存放应用中所有的请求
 */
public class RequestCenter {

    public static void postRequest(String url, RequestParams params,
                                   DisposeDataListener disposeDataListener, Class<?> clazz){
        CommonOkHttpClient.post(CommonRequest.createPostRequest(url,params)
                ,new DisposeDataHandle(disposeDataListener,clazz));
    }
    public static void postRequest1(String url, RequestParams params,RequestParams headers,
                                   DisposeDataListener disposeDataListener, Class<?> clazz){
        CommonOkHttpClient.post(CommonRequest.createPostRequest(url,params,headers)
                ,new DisposeDataHandle(disposeDataListener,clazz));
    }


    public static void uploadPictures(String url, Map<String, Object> map,DisposeDataListener listener){
        CommonOkHttpClient.uploadImgAndParameter(map,url,
                new DisposeDataHandle(listener,ImageUrl.class));
    }


//    public static void uploadPictures(String url, Map<String, Object> map,DisposeDataListener listener){
//       CommonOkHttpClient.uploadImgAndParameter(map,url,new DisposeDataHandle(listener,FanHui.class));
//    }
//
    public static void uploadPictures2(String url,RequestParams params,DisposeDataListener listener){
        CommonOkHttpClient.uploadPictures2(
                CommonRequest.createMultiPostRequest(url,params),new DisposeDataHandle(listener,ShangJiaOrXiaJia.class));
    }
    public static void cancel(){
        CommonOkHttpClient.breakLink();
    }
    /**
     * 应用版本号请求
     *
     * @param listener
     */
//    public static void checkVersion(DisposeDataListener listener) {
//        RequestCenter.postRequest(XY_Response.URL_UPDATEVERSION +"cmemberId="+
//                        UserManager.getInstance().getUser().getObj().getC_memberId(),
//                null, listener, UpdateVersion.class);
//    }

    public static void downloadFile(String url, String path, DisposeDownloadListener listener) {
        CommonOkHttpClient.downloadFile(CommonRequest.createGetRequest(url, null),
                new DisposeDataHandle(listener, path));
    }
    //获取JSON并转化为实体类的请求。
    public static void requestRecommandData(DisposeDataListener listener){
        //第一个参数为请求的地址
        //第二个参数为上传的参数。请求的时候为null
        //第三个参数为监听事件
        //第四个参数为JSON的实体类
        RequestParams params=new RequestParams("TEXT","我是测试数据");
        RequestCenter.postRequest(HttpConstants.TEXT,
                params,listener, null);
    }

    public static void login(String url,RequestParams headers,DisposeDataListener listener){
        RequestCenter.postRequest1(url,null,headers,listener, Login.class);
    }
    public static void logout(String url,DisposeDataListener listener){
        RequestCenter.postRequest1(url,null,GetHeaders.getHeaders(),listener, Logout.class);
    }

    public static void product_up(String url,RequestParams headers,DisposeDataListener listener){
        RequestCenter.postRequest1(url,null,GetHeaders.getHeaders(),listener, ShangJiaOrXiaJia.class);
    }
    public static void product_down(String url,RequestParams headers,DisposeDataListener listener){
        RequestCenter.postRequest1(url,null,GetHeaders.getHeaders(),listener, ShangJiaOrXiaJia.class);
    }

    public static void product_upall(String url,DisposeDataListener listener){
        RequestCenter.postRequest1(url,null, GetHeaders.getHeaders(),listener, ShangJiaOrXiaJia.class);
    }
    public static void product_downall(String url,DisposeDataListener listener){
        RequestCenter.postRequest1(url,null, GetHeaders.getHeaders(),listener, ShangJiaOrXiaJia.class);
    }

    public static void product_delete(String url,DisposeDataListener listener){
        RequestCenter.postRequest1(url,null, GetHeaders.getHeaders(),listener, ShangJiaOrXiaJia.class);
    }
    public static void product_detail(String url,DisposeDataListener listener){
        RequestCenter.postRequest1(url,null, GetHeaders.getHeaders(),listener, ShangPinXiangQing.class);
    }
    public static void product_list(String url,DisposeDataListener listener){
        RequestCenter.postRequest1(url,null, GetHeaders.getHeaders(),listener, ShangPinList.class);
    }
    public static void product_add(String url,DisposeDataListener listener){
        RequestCenter.postRequest1(url,null, GetHeaders.getHeaders(),listener, ShangPinAdd.class);
    }
    public static void product_update(String url,DisposeDataListener listener){
        RequestCenter.postRequest1(url,null, GetHeaders.getHeaders(),listener, ShangPinAdd.class);
    }
    public static void product_productType_list(String url,DisposeDataListener listener){
        RequestCenter.postRequest1(url,null, GetHeaders.getHeaders(),listener, ShangPinFenLei.class);
    }
    public static void product_productType_add(String url,DisposeDataListener listener){
        RequestCenter.postRequest1(url,null, GetHeaders.getHeaders(),listener, AddFenLei.class);
    }
    public static void product_productType_update(String url,DisposeDataListener listener){
        RequestCenter.postRequest1(url,null, GetHeaders.getHeaders(),listener, AddFenLei.class);
    }
    public static void product_productType_delete(String url,DisposeDataListener listener){
        RequestCenter.postRequest1(url,null, GetHeaders.getHeaders(),listener, ShangJiaOrXiaJia.class);
    }
    public static void order_detail(String url,DisposeDataListener listener){
        RequestCenter.postRequest1(url,null, GetHeaders.getHeaders(),listener, Logout.class);
    }
    public static void order_accept(String url,DisposeDataListener listener){
        RequestCenter.postRequest1(url,null, GetHeaders.getHeaders(),listener, ShangJiaOrXiaJia.class);
    }
    public static void order_refuse(String url,DisposeDataListener listener){
        RequestCenter.postRequest1(url,null, GetHeaders.getHeaders(),listener, ShangJiaOrXiaJia.class);
    }
    public static void order_list(String url,DisposeDataListener listener){
        RequestCenter.postRequest1(url,null, GetHeaders.getHeaders(),listener, DingDan.class);
    }
    public static void text_url(String url,DisposeDataListener listener){
        RequestCenter.postRequest(url,null,listener, ShangJiaOrXiaJia.class);
    }
    public static void order_reback_confirm(String url,DisposeDataListener listener){
        RequestCenter.postRequest1(url,null, GetHeaders.getHeaders(),listener, DingDan.class);
    }
    public static void order_reback_refuse(String url,DisposeDataListener listener){
        RequestCenter.postRequest1(url,null, GetHeaders.getHeaders(),listener, DingDan.class);
    }
    public static void order_refund_confirm(String url,DisposeDataListener listener){
        RequestCenter.postRequest1(url,null, GetHeaders.getHeaders(),listener, DingDan.class);
    }
    public static void order_refund_refuse(String url,DisposeDataListener listener){
        RequestCenter.postRequest1(url,null, GetHeaders.getHeaders(),listener, DingDan.class);
    }

    public static void order_reback_receive(String url,DisposeDataListener listener){
        RequestCenter.postRequest1(url,null, GetHeaders.getHeaders(),listener, ShangJiaOrXiaJia.class);
    }

    public static void shop_create(String url,DisposeDataListener listener){
        RequestCenter.postRequest1(url,null, GetHeaders.getHeaders(),listener, CreateDianZhu.class);
    }

    public static void shop_update(String url,DisposeDataListener listener){
        RequestCenter.postRequest1(url,null, GetHeaders.getHeaders(),listener, DianZhuUpdate.class);
    }
    public static void shop_get(String url,DisposeDataListener listener){
        RequestCenter.postRequest1(url,null, GetHeaders.getHeaders(),listener, GetDianZhu.class);
    }
    public static void seller_get(String url,DisposeDataListener listener){
        RequestCenter.postRequest1(url,null, GetHeaders.getHeaders(),listener, ShangJiaXinXi.class);
    }
    public static void seller_save(String url,DisposeDataListener listener){
        RequestCenter.postRequest1(url,null, GetHeaders.getHeaders(),listener, ShangJiaSave.class);
    }

    public static void seller_order_reached(String url,DisposeDataListener listener){
        RequestCenter.postRequest1(url,null, GetHeaders.getHeaders(),listener, ShangJiaOrXiaJia.class);
    }




    /**
     * 应用版本号请求
     *
     * @param listener
     */
    public static void checkVersion(DisposeDataListener listener) {
        RequestCenter.postRequest(RequestURL.URL_UPDATEVERSION+"vertype="+1,
                null, listener, UpdateVersion.class);
    }




}
