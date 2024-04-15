package com.shop.Entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@TableName("user")
@Data
public class User {

    @TableId(type = IdType.AUTO)
//    用户uid
    private Long uid;

//    登入账号
    private String username;

//    登入密码
    private String password;

//    联系方式
    private String contact_info;

//    用户权限
    private String role;

}
