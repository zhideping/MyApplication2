package com.bjxiyang.zhinengshequ.shop.model;

import java.util.List;

/**
 * Created by Administrator on 2017/7/6 0006.
 */

public class ImageUrl {

    /**
     * code : 100
     * msg : 上传图片成功
     * result : ["1499307206479.jpg"]
     */

    private String code;
    private String msg;
    private List<String> result;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<String> getResult() {
        return result;
    }

    public void setResult(List<String> result) {
        this.result = result;
    }
}
