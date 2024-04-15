package com.shop.Filter;

import com.shop.Entity.LoginUser;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component("check")
public class SGExpressionRoot {

//    本tea-shop系统，将权限分为三个等级，1，2，3，按照优先级排列，数字越小，权限越高
//    传入参数leastPrivilege，为该方法允许执行的最低权限
    public boolean hasAuthority(int leastPrivilege){
        //获取当前用户的权限
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        int role_num = Integer.parseInt(loginUser.getUser().getRole());
        //判断用户权限是否允许调用此方法
        return role_num <= leastPrivilege;
    }
}
