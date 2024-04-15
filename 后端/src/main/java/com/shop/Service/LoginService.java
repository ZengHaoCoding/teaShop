package com.shop.Service;

import com.shop.Utils.ResponseResult;
import com.shop.Entity.User;

public interface LoginService {
    ResponseResult login(User user);

    ResponseResult logout();
}
