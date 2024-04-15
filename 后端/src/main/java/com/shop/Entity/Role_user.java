package com.shop.Entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@TableName("role_user")
@Data
public class Role_user {

    @TableId
    private Long id;

    private String name;

}
