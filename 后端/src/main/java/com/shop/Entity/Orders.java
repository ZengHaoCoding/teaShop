package com.shop.Entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
@TableName("orders")
public class Orders {

    @TableId
//    订单号id
    private String order_id;

//    下单的用户id
    private Long uid;

//    下单地址
    private Long address_id;

//    该订单的总金额
    private String order_money;

//    订单生成时间
    private Date create_time;

//    与该订单关联的商户uid
    private Long seller_uid;

//    订单状态，0：待支付，1：已付款，2：已发货，3：确认收货订单完成，4：取消订单
    private String status;

//    快递单号
    private String tracking_number;
}
