package com.shop.Entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@TableName("homePage")
@Data
public class HomePage {

    @TableId(type = IdType.AUTO)
//    推荐页id
    private Long id;

//    推荐页标题
    private String title;

//    推荐图片
    private String image;

//    关联商品id
    private Long goods_id;
}
