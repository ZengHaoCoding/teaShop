package com.shop.Entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
@TableName("cart")
public class Cart {
    @TableId(type = IdType.AUTO)
//    购物车id
    private Long cart_id;

//    添加购物车的用户uid
    private Long uid;

//    添加购物车时间
    private Date create_time;

//    添加购物车的商品id
    private String goods_id;

//    添加购物车的商品数量
    private Long number;
}
