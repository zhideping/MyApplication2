package com.bjxiyang.zhinengshequ.shop.model;

/**
 * Created by Administrator on 2017/7/3 0003.
 */

public class Logout {
    /**
     * code : 1000
     * time : 1499052925912
     * msg : 用户未登录
     * result :
     */

    private int code;
    private long time;
    private String msg;
    private String result;

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

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
