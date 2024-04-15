package com.shop.Entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@TableName("goodsComment")
@Data
public class GoodsComment {

    @TableId(type = IdType.AUTO)
//    评论id
    private Long comment_id;

//    关联订单id
    private String order_id;

//    关联商品id
    private Long goods_id;

//    评论状态，待评论与已评论
    private String status;

//    评论的满意度，0-5
    private Long start;

//    评论内容
    private String content;

//    评论时间
    private Date create_time;
}
