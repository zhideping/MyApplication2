package com.bjxiyang.zhinengshequ.shop.model;

/**
 * Created by Administrator on 2017/7/6 0006.
 */

public class ShangPinAdd {

    /**
     * code : 0
     * time : 1499308047183
     * msg :
     * result : {"id":3,"sellerId":1,"logo":"null","name":"哦哦哦","des":"西服革履一滴血","productTypeId":5,"price":778464600,"ifDiscount":0,"discountPrice":0,"stockNum":888,"status":1}
     */

    private int code;
    private long time;
    private String msg;
    private ResultBean result;

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

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public static class ResultBean {
        /**
         * id : 3
         * sellerId : 1
         * logo : null
         * name : 哦哦哦
         * des : 西服革履一滴血
         * productTypeId : 5
         * price : 778464600
         * ifDiscount : 0
         * discountPrice : 0
         * stockNum : 888
         * status : 1
         */

        private int id;
        private int sellerId;
        private String logo;
        private String name;
        private String des;
        private int productTypeId;
        private int price;
        private int ifDiscount;
        private int discountPrice;
        private int stockNum;
        private int status;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getSellerId() {
            return sellerId;
        }

        public void setSellerId(int sellerId) {
            this.sellerId = sellerId;
        }

        public String getLogo() {
            return logo;
        }

        public void setLogo(String logo) {
            this.logo = logo;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getDes() {
            return des;
        }

        public void setDes(String des) {
            this.des = des;
        }

        public int getProductTypeId() {
            return productTypeId;
        }

        public void setProductTypeId(int productTypeId) {
            this.productTypeId = productTypeId;
        }

        public int getPrice() {
            return price;
        }

        public void setPrice(int price) {
            this.price = price;
        }

        public int getIfDiscount() {
            return ifDiscount;
        }

        public void setIfDiscount(int ifDiscount) {
            this.ifDiscount = ifDiscount;
        }

        public int getDiscountPrice() {
            return discountPrice;
        }

        public void setDiscountPrice(int discountPrice) {
            this.discountPrice = discountPrice;
        }

        public int getStockNum() {
            return stockNum;
        }

        public void setStockNum(int stockNum) {
            this.stockNum = stockNum;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }
    }
}
