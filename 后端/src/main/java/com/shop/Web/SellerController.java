package com.shop.Web;

import com.shop.Entity.Goods;
import com.shop.Entity.Withdrawal;
import com.shop.Mapper.OrdersMapper;
import com.shop.Service.GoodsService;
import com.shop.Service.OrderService;
import com.shop.Service.UserService;
import com.shop.Service.WithdrawalService;
import com.shop.Utils.ResponseResult;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Api("卖家相关接口")
@RestController
public class SellerController {

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private UserService userService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private WithdrawalService withdrawalService;

    @PostMapping("/seller/goods/add")
    @PreAuthorize("@check.hasAuthority(2)")
    public ResponseResult addGoods(@RequestHeader("token") String token, @RequestParam(value = "file") MultipartFile file, Goods goods) throws Exception {
        return goodsService.addGoods(file,goods,token);
    }

    @GetMapping("/seller/getSellerInfo")
    public ResponseResult getSellerInfo(@RequestHeader("token") String token) throws Exception {
        return userService.getSellerInfo(token);
    }

    @GetMapping("/seller/getSellOrder")
    public ResponseResult getSellOrder(@RequestHeader("token") String token) throws Exception {
        return orderService.getSellOrder(token);
    }

    //    此接口承担两个作用
//    1.更改商品信息（json可传入，goods_name，goods_money）
//    2.商品上下架（json可传入，goods_id，goods_status）
    @PostMapping("/seller/goods/update")
    @PreAuthorize("@check.hasAuthority(2)")
    public ResponseResult updateGoods(@RequestHeader("token") String token, @RequestBody Goods goods) throws Exception {
        return goodsService.updateGoods(goods,token);
    }

    @PostMapping("/seller/goods/delete")
    @PreAuthorize("@check.hasAuthority(2)")
    public ResponseResult deleteGoods(@RequestHeader("token") String token, @RequestBody Goods goods) throws Exception {
        return goodsService.deleteGoods(goods,token);
    }

    @GetMapping("/seller/goods/getGoods")
    @PreAuthorize("@check.hasAuthority(2)")
    public ResponseResult getMineGoods(@RequestHeader("token") String token) throws Exception {
        return goodsService.getMyGoods(token);
    }

    @GetMapping("/seller/getWithdrawal")
    @PreAuthorize("@check.hasAuthority(2)")
    public ResponseResult getWithdrawal(@RequestHeader("token") String token) throws Exception {
        return withdrawalService.getMyWithdrawal(token);
    }

    @PostMapping("/seller/createWithdrawal")
    @PreAuthorize("@check.hasAuthority(2)")
    public ResponseResult createWithdrawal(@RequestBody Withdrawal withdrawal, @RequestHeader("token") String token) throws Exception {
        return withdrawalService.createWithdrawal(withdrawal,token);
    }

    @PostMapping("/seller/cancelWithdrawal")
    @PreAuthorize("@check.hasAuthority(2)")
    public ResponseResult cancelWithdrawal(@RequestBody Withdrawal withdrawal){
        withdrawal.setStatus("2");
        return withdrawalService.updateWithdrawal(withdrawal);
    }
}
