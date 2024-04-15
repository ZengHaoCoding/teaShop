package com.shop.Entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@TableName("workdocdetail")
@Data
public class WorkDocDetail {

    @TableId(type = IdType.AUTO)
    private Long id;

    private Long wd_id;

    private String wd_contant;

    private String wd_img;

    private Long create_uid;

    private Date create_time;

}
