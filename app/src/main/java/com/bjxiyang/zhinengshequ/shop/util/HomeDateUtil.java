package com.bjxiyang.zhinengshequ.shop.util;

import com.bjxiyang.zhinengshequ.shop.model.Home;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/6/29 0029.
 */

public class HomeDateUtil {
    public static List<Home> getData(){

        List<Home> list=new ArrayList<>();
        for (int i=1;i<=3;i++){
            Home home=new Home();
            home.dingdanbianhao="#"+i;
            home.name="希洋便利店";
            home.xiadanshijian="12:12";
            home.dizhi="鹏景阁";
            home.shangpinshuliang=""+i;
            home.beizhu="我是备注"+i;
            home.xiaoji="11"+i;
            home.youhui="22"+i;
            home.zongjishouru="33"+i;
            home.dingdanhao="123456456"+i;
            home.songdashijian="12:12";
            home.dingdanzhuangtai="已接单"+i;
            list.add(home);
        }


       return list;

    }
}
