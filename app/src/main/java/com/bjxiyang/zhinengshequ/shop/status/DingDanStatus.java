package com.bjxiyang.zhinengshequ.shop.status;

/**
 * Created by Administrator on 2017/7/6 0006.
 */

public class DingDanStatus {
    public static final int order_status_wait_pay=10;//未支付
    public static final int order_status_paid=20;//已支付
    public static final int order_status_dispatch=30;//配送中
    public static final int order_status_reach=40;//已送达
    public static final int order_status_received=50;//已收货
    public static final int order_status_cancel_unpay=91;//未支付取消
    public static final int order_status_cancel_paid=92;//已支付取消
    public static final int order_status_cancel_refused=93;//拒单取消
    public static final int order_status_doned=100;//已完结
    public static final int order_sub_status_reback_apply=100;//退货申请
    public static final int order_sub_status_refund_apply=200;//退款申请
    public static final int order_sub_status_rebacking=110;//退货中
    public static final int order_sub_status_refunding=210;//退款中
    public static final int order_sub_status_rebacked=120;//已退货
    public static final int order_sub_status_refunded=220;//已退款
    public static final int order_sub_status_reback_refused=130;//退货拒绝
    public static final int order_sub_status_refund_refused=230;//退款拒绝


}
