package com.shop.Utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class OrderUtil {
//    订单号生成
    public static String createOrderNo(Long uid){
//        获取时间精确到秒，并格式化成长度14的字符串
        String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").
                format(new Date()).
                replaceAll("[[\\s-:punct:]]", "");
//        产生六位随机数
        String randomStr = Integer.toString(new Random().nextInt(900000)+100000);
//        订单号长度：14+8+6=28
        return time + String.format("%06d", uid) + randomStr;
    }
}
