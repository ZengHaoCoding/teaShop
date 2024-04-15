package com.shop.Web;

import com.shop.Entity.Address;
import com.shop.Entity.Cart;
import com.shop.Entity.Seller;
import com.shop.Entity.User;
import com.shop.Service.UserService;
import com.shop.Utils.ResponseResult;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@Api("用户及购物车管理")
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/user/getUserInfo")
    public ResponseResult getUserInfo(@RequestHeader("token") String token) throws Exception {
        return userService.getUserInfo(token);
    }

    @GetMapping("/cart/getCartInfo")
    public ResponseResult getCartInfo(@RequestHeader("token") String token) throws Exception {
        return userService.getCartInfo(token);
    }

    @PostMapping("/user/register")
    public ResponseResult register(@RequestBody User user){
        return userService.register(user);
    }

    @PostMapping("/cart/add")
    public ResponseResult addCart(@RequestBody Cart cart, @RequestHeader("token") String token) throws Exception {
        return userService.addCart(cart, token);
    }

    @PostMapping("/cart/dec")
    public ResponseResult decCart(@RequestBody Cart cart, @RequestHeader("token") String token) throws Exception {
        return userService.decCart(cart, token);
    }

    @PostMapping("/cart/remove")
    public ResponseResult removeCart(@RequestBody Cart cart){
        return userService.removeCart(cart);
    }

    @PostMapping("/user/addSeller")
    public ResponseResult addSeller(@RequestBody Seller seller, @RequestHeader("token") String token) throws Exception {
        return userService.addSeller(seller, token);
    }

    @GetMapping("/user/getAddress")
    public ResponseResult getAddress(@RequestHeader("token") String token) throws Exception {
        return userService.getAddress(token);
    }

    @PostMapping("/user/addAddress")
    public ResponseResult addAddress(@RequestBody Address address, @RequestHeader("token") String token) throws Exception {
        return userService.insertAddress(address, token);
    }

    @PostMapping("/user/removeAddress")
    public ResponseResult removeAddress(@RequestBody Address address, @RequestHeader("token") String token) throws Exception {
        return userService.removeAddress(address, token);
    }

}
