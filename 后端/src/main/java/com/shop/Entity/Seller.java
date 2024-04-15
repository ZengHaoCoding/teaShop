package com.shop.Entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@TableName("seller")
@Data
public class Seller {

    @TableId
//    商户的用户uid
    public Long uid;

//    商户名称
    public String seller_name;

//    商户的余额
    public String balance;

    private String contact_info;

    private String card_number;

    private String real_name;

//    用户申请出售权限审核状态，"1"审核通过，"0"审核中
    public String status;
}
