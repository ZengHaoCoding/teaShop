package com.shop.Entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@TableName("orderDetail")
@Data
public class OrderDetail {

    @TableId(type = IdType.AUTO)
    private Long id;
//    订单id
    private String order_id;

//    商品id
    private String goods_id;

//    商品单价
    private String goods_money;

//    下单商品数量
    private Long number;

//    商品规格
    private String weight_unit;

//    关联评论id
    private Long comment_id;
}
