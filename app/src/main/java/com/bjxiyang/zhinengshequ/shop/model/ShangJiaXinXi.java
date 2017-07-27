package com.bjxiyang.zhinengshequ.shop.model;

/**
 * Created by Administrator on 2017/7/7 0007.
 */

public class ShangJiaXinXi {

    /**
     * code : 0
     * time : 1499430432797
     * msg :
     * result : {"id":2,"loginName":"test1","logo":"","shopName":"商家1","communityId":1,"address":"","linkman":"test1","linkphone":"15812345678","des":"","accountTotal":0,"bankCard":"4200234577094030","bankName":"工商银行","bankSubName":"工商银行XX支行","bankUser":"test1","wxNo":"lisi","alipayNo":"lisi@qq.com","status":0,"serviceTime":0,"startTime":0,"endTime":0,"startPrice":0,"dispatchPrice":0,"loginKey":"dab0472bf73fe41748647ad15fac9275"}
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
         * id : 2
         * loginName : test1
         * logo :
         * shopName : 商家1
         * communityId : 1
         * address :
         * linkman : test1
         * linkphone : 15812345678
         * des :
         * accountTotal : 0
         * bankCard : 4200234577094030
         * bankName : 工商银行
         * bankSubName : 工商银行XX支行
         * bankUser : test1
         * wxNo : lisi
         * alipayNo : lisi@qq.com
         * status : 0
         * serviceTime : 0
         * startTime : 0
         * endTime : 0
         * startPrice : 0
         * dispatchPrice : 0
         * loginKey : dab0472bf73fe41748647ad15fac9275
         */

        private int id;
        private String loginName;
        private String logo;
        private String shopName;
        private int communityId;
        private String address;
        private String linkman;
        private String linkphone;
        private String des;
        private int accountTotal;
        private String bankCard;
        private String bankName;
        private String bankSubName;
        private String bankUser;
        private String wxNo;
        private String alipayNo;
        private int status;
        private int serviceTime;
        private int startTime;
        private int endTime;
        private int startPrice;
        private int dispatchPrice;
        private String loginKey;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getLoginName() {
            return loginName;
        }

        public void setLoginName(String loginName) {
            this.loginName = loginName;
        }

        public String getLogo() {
            return logo;
        }

        public void setLogo(String logo) {
            this.logo = logo;
        }

        public String getShopName() {
            return shopName;
        }

        public void setShopName(String shopName) {
            this.shopName = shopName;
        }

        public int getCommunityId() {
            return communityId;
        }

        public void setCommunityId(int communityId) {
            this.communityId = communityId;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getLinkman() {
            return linkman;
        }

        public void setLinkman(String linkman) {
            this.linkman = linkman;
        }

        public String getLinkphone() {
            return linkphone;
        }

        public void setLinkphone(String linkphone) {
            this.linkphone = linkphone;
        }

        public String getDes() {
            return des;
        }

        public void setDes(String des) {
            this.des = des;
        }

        public int getAccountTotal() {
            return accountTotal;
        }

        public void setAccountTotal(int accountTotal) {
            this.accountTotal = accountTotal;
        }

        public String getBankCard() {
            return bankCard;
        }

        public void setBankCard(String bankCard) {
            this.bankCard = bankCard;
        }

        public String getBankName() {
            return bankName;
        }

        public void setBankName(String bankName) {
            this.bankName = bankName;
        }

        public String getBankSubName() {
            return bankSubName;
        }

        public void setBankSubName(String bankSubName) {
            this.bankSubName = bankSubName;
        }

        public String getBankUser() {
            return bankUser;
        }

        public void setBankUser(String bankUser) {
            this.bankUser = bankUser;
        }

        public String getWxNo() {
            return wxNo;
        }

        public void setWxNo(String wxNo) {
            this.wxNo = wxNo;
        }

        public String getAlipayNo() {
            return alipayNo;
        }

        public void setAlipayNo(String alipayNo) {
            this.alipayNo = alipayNo;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public int getServiceTime() {
            return serviceTime;
        }

        public void setServiceTime(int serviceTime) {
            this.serviceTime = serviceTime;
        }

        public int getStartTime() {
            return startTime;
        }

        public void setStartTime(int startTime) {
            this.startTime = startTime;
        }

        public int getEndTime() {
            return endTime;
        }

        public void setEndTime(int endTime) {
            this.endTime = endTime;
        }

        public int getStartPrice() {
            return startPrice;
        }

        public void setStartPrice(int startPrice) {
            this.startPrice = startPrice;
        }

        public int getDispatchPrice() {
            return dispatchPrice;
        }

        public void setDispatchPrice(int dispatchPrice) {
            this.dispatchPrice = dispatchPrice;
        }

        public String getLoginKey() {
            return loginKey;
        }

        public void setLoginKey(String loginKey) {
            this.loginKey = loginKey;
        }
    }
}
