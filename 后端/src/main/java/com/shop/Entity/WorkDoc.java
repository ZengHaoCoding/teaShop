package com.shop.Entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@TableName("workdoc")
@Data
public class WorkDoc {

    @TableId(type = IdType.AUTO)
    private Long id;

    private String order_id;

    private Long buyer_uid;

    private Long seller_uid;

    private String status;

    private Date create_time;
}
