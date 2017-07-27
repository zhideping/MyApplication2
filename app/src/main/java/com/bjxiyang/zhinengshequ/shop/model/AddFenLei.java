package com.bjxiyang.zhinengshequ.shop.model;

/**
 * Created by Administrator on 2017/7/3 0003.
 */

public class AddFenLei {

    /**
     * code : 0
     * time : 1499072034328
     * msg :
     * result : {"id":4,"name":"å\u0093\u0088å\u0093\u0088å\u0093\u0088","sellerId":1}
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
         * id : 4
         * name : ååå
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
