package com.bjxiyang.zhinengshequ.shop.model;

/**
 * Created by Administrator on 2017/7/7 0007.
 */

public class GetDianZhu {


    /**
     * code : 0
     * time : 1499430050942
     * msg :
     * result : {"id":2,"sellerId":2,"logo":"http://47.92.106.249:8088/img/1499429982027.jpg","name":"aaaa","phone":"12345678912","sex":1,"age":23,"businessLicense":"http://47.92.106.249:8088/img/1499429984559.jpg","healthLicense":"http://47.92.106.249:8088/img/1499429986496.jpg"}
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
         * sellerId : 2
         * logo : http://47.92.106.249:8088/img/1499429982027.jpg
         * name : aaaa
         * phone : 12345678912
         * sex : 1
         * age : 23
         * businessLicense : http://47.92.106.249:8088/img/1499429984559.jpg
         * healthLicense : http://47.92.106.249:8088/img/1499429986496.jpg
         */

        private int id;
        private int sellerId;
        private String logo;
        private String name;
        private String phone;
        private int sex;
        private int age;
        private String businessLicense;
        private String healthLicense;

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

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public int getSex() {
            return sex;
        }

        public void setSex(int sex) {
            this.sex = sex;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public String getBusinessLicense() {
            return businessLicense;
        }

        public void setBusinessLicense(String businessLicense) {
            this.businessLicense = businessLicense;
        }

        public String getHealthLicense() {
            return healthLicense;
        }

        public void setHealthLicense(String healthLicense) {
            this.healthLicense = healthLicense;
        }
    }
}
