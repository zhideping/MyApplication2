package com.bjxiyang.zhinengshequ.shop.model;

import java.util.List;

/**
 * Created by Administrator on 2017/7/3 0003.
 */

public class ShangPinXiangQing {


    /**
     * code : 0
     * time : 1499065240623
     * msg :
     * result : {"product":{"id":1,"sellerId":1,"logo":"logoURL","name":"商品一号","des":"","productTypeId":1,"price":1000,"ifDiscount":0,"discountPrice":0,"stockNum":100,"status":2},"productTypes":[{"id":1,"name":"特价","sellerId":1},{"id":2,"name":"推荐","sellerId":1},{"id":3,"name":"饮料酒水","sellerId":1}]}
     */

    private int code;
    private long time;
    private String msg;
    private Result result;

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

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public static class Result {
        /**
         * product : {"id":1,"sellerId":1,"logo":"logoURL","name":"商品一号","des":"","productTypeId":1,"price":1000,"ifDiscount":0,"discountPrice":0,"stockNum":100,"status":2}
         * productTypes : [{"id":1,"name":"特价","sellerId":1},{"id":2,"name":"推荐","sellerId":1},{"id":3,"name":"饮料酒水","sellerId":1}]
         */

        private Product product;
        private List<ProductTypes> productTypes;

        public Product getProduct() {
            return product;
        }

        public void setProduct(Product product) {
            this.product = product;
        }

        public List<ProductTypes> getProductTypes() {
            return productTypes;
        }

        public void setProductTypes(List<ProductTypes> productTypes) {
            this.productTypes = productTypes;
        }

        public static class Product {
            /**
             * id : 1
             * sellerId : 1
             * logo : logoURL
             * name : 商品一号
             * des :
             * productTypeId : 1
             * price : 1000
             * ifDiscount : 0
             * discountPrice : 0
             * stockNum : 100
             * status : 2
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

        public static class ProductTypes {
            /**
             * id : 1
             * name : 特价
             * sellerId : 1
             */

            private int id;
            private String name;
            private int sellerId;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public int getSellerId() {
                return sellerId;
            }

            public void setSellerId(int sellerId) {
                this.sellerId = sellerId;
            }
        }
    }
}
