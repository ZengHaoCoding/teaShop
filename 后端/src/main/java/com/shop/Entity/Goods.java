package com.shop.Entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@TableName("goods")
@Data
public class Goods {

    @TableId(type = IdType.AUTO)
//    商品id
    private Long goods_id;

//    商品名称
    private String goods_name;

//    商品图片路径
    private String goods_img;

//    商品规格
    private String weight_unit;

//    商品价格
    private String goods_money;

//    商品上下架状态
    private String status;

//    商品上架时间
    private Date create_time;

//    商品创建者/所属商户uid
    private Long create_uid;

}
