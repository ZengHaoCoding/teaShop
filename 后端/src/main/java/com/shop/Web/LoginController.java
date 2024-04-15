package com.shop.Web;

import com.shop.Utils.ResponseResult;
import com.shop.Entity.User;
import com.shop.Service.LoginService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Api("用户登入与退出")
@RestController
public class LoginController {

    @Autowired
    private LoginService loginService;

//    只需传入username，password
    @PostMapping("/user/login")
    public ResponseResult login(@RequestBody User user) {
        //登录
        return loginService.login(user);
    }

    @GetMapping("/user/loginout")
    public ResponseResult loginout(){
        return loginService.logout();
    }

}
