package com.bjxiyang.zhinengshequ.shop.request;

/**
 * Created by Administrator on 2017/7/2 0002.
 */

public class RequestURL {
    public static final String URL="http://47.92.106.249:18088/zsq/api/seller/";
//    public static final String URL_XY="http://192.168.1.139:8080/zsq/";
//    public static final String URL="http://192.168.1.254:8080/zsq/";
    public static final String URL_XY="http://47.92.106.249:8088//zsq/";

//    public static final String URL_IMAGE="http://192.168.1.139:8080/zsq/image/addpic?";
    public static final String URL_IMAGE="http://47.92.106.249:8088/zsq/image/addpic?";

    public static final String URL_UPDATEVERSION=URL_XY+"usercenter/queryLastVersion?";

//    public static final String URL="http://101.200.75.233/zsq/api/seller/";


    public static final String URL_LOGIN=URL+"login?";
    public static final String URL_LOGOUT=URL+"logout?";

    public static final String URL_PRODUCT_UP=URL+"product/up?";
    public static final String URL_PRODUCT_DOWN=URL+"product/down?";

    public static final String URL_PRODUCT_UPALL=URL+"product/upAll?";
    public static final String URL_PRODUCT_DOWNALL=URL+"product/downAll?";
    public static final String URL_PRODUCT_DELETE=URL+"product/delete?";
    public static final String URL_PRODUCT_DETAIL=URL+"product/detail?";
    public static final String URL_PRODUCT_LIST=URL+"product/list?";
    public static final String URL_PRODUCT_ADD=URL+"product/add?";
    public static final String URL_PRODUCT_UPDATE=URL+"product/update?";
    public static final String URL_PRODUCT_PRODUCTTYPE_LIST=URL+"product/productType/list?";
    public static final String URL_PRODUCT_PRODUCTTYPE_ADD=URL+"product/productType/add?";
    public static final String URL_PRODUCT_PRODUCTTYPE_UPDATE=URL+"product/productType/update?";
    public static final String URL_PRODUCT_PRODUCTTYPE_DELETE=URL+"product/productType/delete?";

    public static final String URL_ORDER_DETAIL=URL+"order/detail?";

    public static final String URL_ORDER_ACCEPT=URL+"order/accept?";

    public static final String URL_ORDER_REFUSE=URL+"order/refuse?";
    public static final String URL_ORDER_LIST=URL+"order/list?";
    public static final String URL_ORDER_REBACK_CONFIRM=URL+"order/reback/confirm?";
    public static final String URL_ORDER_REBACK_REFUSE=URL+"order/reback/refuse?";
    public static final String URL_ORDER_REFUND_CONFIRM=URL+"order/refund/confirm?";
    public static final String URL_ORDER_REFUND_REFUSE=URL+"order/refund/refuse?";


    public static final String URL_SHOP_CREATE=URL+"shop/create?";

    public static final String URL_SHOP_UPDATE=URL+"shop/update?";

    public static final String URL_SHOP_GET=URL+"shop/get?";

    public static final String URL_SELLER_GET=URL+"seller/get?";

    public static final String URL_SELLER_SAVE=URL+"seller/save?";

    public static final String URL_SELLER_ORDER_REACHED=URL+"order/reached?";

    public static final String URL_ORDER_REBACK_RECEIVE=URL+"order/reback/receive?";


}
