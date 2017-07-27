package com.bjxiyang.zhinengshequ.shop.model;

import java.util.List;

/**
 * Created by Administrator on 2017/7/3 0003.
 */

public class DingDan {

    /**
     * code : 0
     * time : 1499653021498
     * msg :
     * result : [{"orderInfo":{"id":45,"orderNo":"1002017071045","userId":1,"sellerId":1,"totalAmount":1,"afterDiscountAmount":1,"couponUserId":0,"payType":3,"userAddressId":12,"communityId":1,"nperId":1,"floorId":1,"unitId":1,"doorId":0,"receiver":"张","receiveAddress":"华彩国际一期一栋3单元207","receivePhone":"18513558770","remark":"","status":30,"subStatus":0,"sendTime":"","expectSendTime":"","createTime":"2017-07-10 02:01:14","payLimitTime":"2017-07-10 02:11:14","cancelRemark":"","tradeNo":"2017071021001004440221230248","receiveTime":""},"orderInfoProducts":[{"id":137,"afterDiscountPrice":1,"num":1,"price":1,"name":"god哦哦","orderId":45,"productId":19}],"subStatusLogs":[]},{"orderInfo":{"id":42,"orderNo":"1002017070842","userId":1,"sellerId":1,"totalAmount":3,"afterDiscountAmount":3,"couponUserId":0,"payType":3,"userAddressId":9,"communityId":2,"nperId":4,"floorId":10,"unitId":0,"doorId":0,"receiver":"磊磊","receiveAddress":"","receivePhone":"18818888888","remark":"","status":30,"subStatus":0,"sendTime":"","expectSendTime":"","createTime":"2017-07-08 03:01:41","payLimitTime":"2017-07-08 03:11:41","cancelRemark":"","tradeNo":"2017070821001004110263791017","receiveTime":""},"orderInfoProducts":[{"id":130,"afterDiscountPrice":1,"num":1,"price":1,"name":"god哦哦","orderId":42,"productId":19},{"id":131,"afterDiscountPrice":1,"num":1,"price":1,"name":"123456","orderId":42,"productId":24},{"id":132,"afterDiscountPrice":1,"num":1,"price":1,"name":"中央音乐学院","orderId":42,"productId":26}],"subStatusLogs":[]}]
     */

    private int code;
    private long time;
    private String msg;
    private List<ResultBean> result;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<ResultBean> getResult() {
        return result;
    }

    public void setResult(List<ResultBean> result) {
        this.result = result;
    }

    public static class ResultBean {
        /**
         * orderInfo : {"id":45,"orderNo":"1002017071045","userId":1,"sellerId":1,"totalAmount":1,"afterDiscountAmount":1,"couponUserId":0,"payType":3,"userAddressId":12,"communityId":1,"nperId":1,"floorId":1,"unitId":1,"doorId":0,"receiver":"张","receiveAddress":"华彩国际一期一栋3单元207","receivePhone":"18513558770","remark":"","status":30,"subStatus":0,"sendTime":"","expectSendTime":"","createTime":"2017-07-10 02:01:14","payLimitTime":"2017-07-10 02:11:14","cancelRemark":"","tradeNo":"2017071021001004440221230248","receiveTime":""}
         * orderInfoProducts : [{"id":137,"afterDiscountPrice":1,"num":1,"price":1,"name":"god哦哦","orderId":45,"productId":19}]
         * subStatusLogs : []
         */

        private OrderInfoBean orderInfo;
        private List<OrderInfoProductsBean> orderInfoProducts;
        private List<?> subStatusLogs;

        public OrderInfoBean getOrderInfo() {
            return orderInfo;
        }

        public void setOrderInfo(OrderInfoBean orderInfo) {
            this.orderInfo = orderInfo;
        }

        public List<OrderInfoProductsBean> getOrderInfoProducts() {
            return orderInfoProducts;
        }

        public void setOrderInfoProducts(List<OrderInfoProductsBean> orderInfoProducts) {
            this.orderInfoProducts = orderInfoProducts;
        }

        public List<?> getSubStatusLogs() {
            return subStatusLogs;
        }

        public void setSubStatusLogs(List<?> subStatusLogs) {
            this.subStatusLogs = subStatusLogs;
        }

        public static class OrderInfoBean {
            /**
             * id : 45
             * orderNo : 1002017071045
             * userId : 1
             * sellerId : 1
             * totalAmount : 1
             * afterDiscountAmount : 1
             * couponUserId : 0
             * payType : 3
             * userAddressId : 12
             * communityId : 1
             * nperId : 1
             * floorId : 1
             * unitId : 1
             * doorId : 0
             * receiver : 张
             * receiveAddress : 华彩国际一期一栋3单元207
             * receivePhone : 18513558770
             * remark :
             * status : 30
             * subStatus : 0
             * sendTime :
             * expectSendTime :
             * createTime : 2017-07-10 02:01:14
             * payLimitTime : 2017-07-10 02:11:14
             * cancelRemark :
             * tradeNo : 2017071021001004440221230248
             * receiveTime :
             */

            private int id;
            private String orderNo;
            private int userId;
            private int sellerId;
            private int totalAmount;
            private int afterDiscountAmount;
            private int couponUserId;
            private int payType;
            private int userAddressId;
            private int communityId;
            private int nperId;
            private int floorId;
            private int unitId;
            private int doorId;
            private String receiver;
            private String receiveAddress;
            private String receivePhone;
            private String remark;
            private int status;
            private int subStatus;
            private String sendTime;
            private String expectSendTime;
            private String createTime;
            private String payLimitTime;
            private String cancelRemark;
            private String tradeNo;
            private String receiveTime;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getOrderNo() {
                return orderNo;
            }

            public void setOrderNo(String orderNo) {
                this.orderNo = orderNo;
            }

            public int getUserId() {
                return userId;
            }

            public void setUserId(int userId) {
                this.userId = userId;
            }

            public int getSellerId() {
                return sellerId;
            }

            public void setSellerId(int sellerId) {
                this.sellerId = sellerId;
            }

            public int getTotalAmount() {
                return totalAmount;
            }

            public void setTotalAmount(int totalAmount) {
                this.totalAmount = totalAmount;
            }

            public int getAfterDiscountAmount() {
                return afterDiscountAmount;
            }

            public void setAfterDiscountAmount(int afterDiscountAmount) {
                this.afterDiscountAmount = afterDiscountAmount;
            }

            public int getCouponUserId() {
                return couponUserId;
            }

            public void setCouponUserId(int couponUserId) {
                this.couponUserId = couponUserId;
            }

            public int getPayType() {
                return payType;
            }

            public void setPayType(int payType) {
                this.payType = payType;
            }

            public int getUserAddressId() {
                return userAddressId;
            }

            public void setUserAddressId(int userAddressId) {
                this.userAddressId = userAddressId;
            }

            public int getCommunityId() {
                return communityId;
            }

            public void setCommunityId(int communityId) {
                this.communityId = communityId;
            }

            public int getNperId() {
                return nperId;
            }

            public void setNperId(int nperId) {
                this.nperId = nperId;
            }

            public int getFloorId() {
                return floorId;
            }

            public void setFloorId(int floorId) {
                this.floorId = floorId;
            }

            public int getUnitId() {
                return unitId;
            }

            public void setUnitId(int unitId) {
                this.unitId = unitId;
            }

            public int getDoorId() {
                return doorId;
            }

            public void setDoorId(int doorId) {
                this.doorId = doorId;
            }

            public String getReceiver() {
                return receiver;
            }

            public void setReceiver(String receiver) {
                this.receiver = receiver;
            }

            public String getReceiveAddress() {
                return receiveAddress;
            }

            public void setReceiveAddress(String receiveAddress) {
                this.receiveAddress = receiveAddress;
            }

            public String getReceivePhone() {
                return receivePhone;
            }

            public void setReceivePhone(String receivePhone) {
                this.receivePhone = receivePhone;
            }

            public String getRemark() {
                return remark;
            }

            public void setRemark(String remark) {
                this.remark = remark;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public int getSubStatus() {
                return subStatus;
            }

            public void setSubStatus(int subStatus) {
                this.subStatus = subStatus;
            }

            public String getSendTime() {
                return sendTime;
            }

            public void setSendTime(String sendTime) {
                this.sendTime = sendTime;
            }

            public String getExpectSendTime() {
                return expectSendTime;
            }

            public void setExpectSendTime(String expectSendTime) {
                this.expectSendTime = expectSendTime;
            }

            public String getCreateTime() {
                return createTime;
            }

            public void setCreateTime(String createTime) {
                this.createTime = createTime;
            }

            public String getPayLimitTime() {
                return payLimitTime;
            }

            public void setPayLimitTime(String payLimitTime) {
                this.payLimitTime = payLimitTime;
            }

            public String getCancelRemark() {
                return cancelRemark;
            }

            public void setCancelRemark(String cancelRemark) {
                this.cancelRemark = cancelRemark;
            }

            public String getTradeNo() {
                return tradeNo;
            }

            public void setTradeNo(String tradeNo) {
                this.tradeNo = tradeNo;
            }

            public String getReceiveTime() {
                return receiveTime;
            }

            public void setReceiveTime(String receiveTime) {
                this.receiveTime = receiveTime;
            }
        }

        public static class OrderInfoProductsBean {
            /**
             * id : 137
             * afterDiscountPrice : 1
             * num : 1
             * price : 1
             * name : god哦哦
             * orderId : 45
             * productId : 19
             */

            private int id;
            private int afterDiscountPrice;
            private int num;
            private int price;
            private String name;
            private int orderId;
            private int productId;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getAfterDiscountPrice() {
                return afterDiscountPrice;
            }

            public void setAfterDiscountPrice(int afterDiscountPrice) {
                this.afterDiscountPrice = afterDiscountPrice;
            }

            public int getNum() {
                return num;
            }

            public void setNum(int num) {
                this.num = num;
            }

            public int getPrice() {
                return price;
            }

            public void setPrice(int price) {
                this.price = price;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public int getOrderId() {
                return orderId;
            }

            public void setOrderId(int orderId) {
                this.orderId = orderId;
            }

            public int getProductId() {
                return productId;
            }

            public void setProductId(int productId) {
                this.productId = productId;
            }
        }
    }
}
