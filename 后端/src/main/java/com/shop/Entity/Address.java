package com.shop.Entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@TableName("address")
@Data
public class Address {
    @TableId(type = IdType.AUTO)
    private Long address_id;

//    用户id
    private Long uid;

//    用户邮寄名字
    private String post_name;

//    用户邮寄联系方式
    private String post_mobile;

//    用户邮寄地址
    private String post_address;
}
