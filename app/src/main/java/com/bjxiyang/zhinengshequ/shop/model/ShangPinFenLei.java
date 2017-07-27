package com.bjxiyang.zhinengshequ.shop.model;

import java.util.List;

/**
 * Created by Administrator on 2017/7/3 0003.
 */

public class ShangPinFenLei {

    /**
     * code : 0
     * time : 1499068927683
     * msg :
     * result : [{"id":1,"name":"特价","sellerId":1},{"id":2,"name":"推荐","sellerId":1},{"id":3,"name":"饮料酒水","sellerId":1}]
     */

    private int code;
    private long time;
    private String msg;
    private List<Result> result;

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

    public List<Result> getResult() {
        return result;
    }

    public void setResult(List<Result> result) {
        this.result = result;
    }

    public static class Result {
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
