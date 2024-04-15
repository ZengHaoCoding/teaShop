package com.shop.Web;

import com.shop.Entity.Address;
import com.shop.Entity.Seller;
import com.shop.Entity.Withdrawal;
import com.shop.Service.GoodsService;
import com.shop.Service.OrderService;
import com.shop.Service.UserService;
import com.shop.Service.WithdrawalService;
import com.shop.Utils.ResponseResult;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Api("管理员相关接口")
@RestController
public class AdminController {

    @Autowired
    private UserService userService;

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private WithdrawalService withdrawalService;

    @PostMapping("/admin/passSeller")
    @PreAuthorize("@check.hasAuthority(1)")
    public ResponseResult passAuditing(@RequestBody Seller seller){
        return userService.passAuditing(seller);
    }

    @GetMapping("/admin/getApplySeller")
    @PreAuthorize("@check.hasAuthority(1)")
    public ResponseResult getAlllApplySeller(){
        return userService.selectAllApplySeller();
    }

    @GetMapping("/admin/getAllUser")
    @PreAuthorize("@check.hasAuthority(1)")
    public ResponseResult getAlllUser(){
        return userService.selectAllUser();
    }

    @GetMapping("/admin/getAllOrder")
    @PreAuthorize("@check.hasAuthority(1)")
    public ResponseResult getAllOrder(){
        return orderService.selectAllOrdersByAdmin();
    }

    @PostMapping("/admin/getSellerInfo")
    @PreAuthorize("@check.hasAuthority(1)")
    public ResponseResult getSellerInfo(@RequestBody Seller seller){
        return userService.getSellerInfoByAdmin(seller);
    }

    @PostMapping("/admin/selectAddressByUid")
    @PreAuthorize("@check.hasAuthority(1)")
    public ResponseResult selectAddressByUid(@RequestBody Address address){
        return userService.selectAddressByUid(address);
    }

    @GetMapping("/admin/getAllGoods")
    @PreAuthorize("@check.hasAuthority(1)")
    public ResponseResult getAllGoodsByAdmin(){
        return goodsService.getAllGoodsByAdmin();
    }

    @GetMapping("/admin/getAllWithdrawal")
    @PreAuthorize("@check.hasAuthority(1)")
    public ResponseResult getAllWithdrawal(){
        return withdrawalService.selectAllWithdrawal();
    }

    @PostMapping("/admin/updateWithdrawal")
    @PreAuthorize("@check.hasAuthority(1)")
    public ResponseResult updateWithdrawal(@RequestBody Withdrawal withdrawal){
        return withdrawalService.updateWithdrawal(withdrawal);
    }
}
