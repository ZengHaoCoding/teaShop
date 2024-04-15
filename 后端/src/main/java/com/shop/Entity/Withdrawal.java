package com.shop.Entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@TableName("withdrawal")
@Data
public class Withdrawal {

    @TableId(type = IdType.AUTO)
    private Long id;

    private Long seller_uid;

    private String money;

//    0审核中，1已通过，2:未通过
    private String status;

    private Date create_time;

}
