package com.shop.Web;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.shop.Entity.Goods;
import com.shop.Entity.GoodsComment;
import com.shop.Entity.OrderDetail;
import com.shop.Entity.Orders;
import com.shop.Mapper.GoodsMapper;
import com.shop.Service.GoodsCommentService;
import com.shop.Service.OrderService;
import com.shop.Utils.ResponseResult;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Api("订单管理")
@RestController
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private GoodsMapper goodsMapper;

    @Autowired
    private GoodsCommentService goodsCommentService;

    @GetMapping("order/getAllOrders")
    public ResponseResult getAllOrders(@RequestHeader("token") String token) throws Exception {
        return orderService.getAllOrder(token);
    }

    @GetMapping("order/getOrderDetail/{orders_id}")
    public ResponseResult getOrderDetail(@PathVariable String orders_id) throws Exception {
        return orderService.getOrderDetail(orders_id);
    }

    @PostMapping("order/create/{address_id}")
    public ResponseResult createOrder(@RequestHeader("token") String token, @RequestBody List<OrderDetail> ordersList, @PathVariable String address_id) throws Exception {
        Map<Long, List<OrderDetail>> tmp = new HashMap<>();
        for (OrderDetail od : ordersList) {
            LambdaQueryWrapper<Goods> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(Goods::getGoods_id, od.getGoods_id());
            queryWrapper.select(Goods::getCreate_uid);
            Long create_uid = goodsMapper.selectOne(queryWrapper).getCreate_uid();
            if (tmp.getOrDefault(create_uid, null) == null){
                tmp.put(create_uid, new ArrayList<OrderDetail>());
            }
            tmp.get(create_uid).add(od);
        }
        List<Map<String, Object>> result = new ArrayList<>();
        for (Long key : tmp.keySet()) {
            result.add(orderService.createOrder(tmp.get(key), token, key, address_id));
        }
        return new ResponseResult(200, "success", result);
    }

    @PostMapping("order/cancelOrder")
    public ResponseResult cancelOrder(@RequestBody Orders orders){
        return orderService.cancelOrder(orders);
    }

    @PostMapping("order/payOrder")
    public ResponseResult payOrder(@RequestBody Orders orders){
        return orderService.payOrder(orders);
    }

    @PostMapping("order/snedOutGoods")
    @PreAuthorize("@check.hasAuthority(2)")
    public ResponseResult sendOutGoods(@RequestBody Orders orders){
        return orderService.snedOutGoods(orders);
    }

    @PostMapping("order/confirmOrder")
    public ResponseResult confirmOrder(@RequestBody Orders orders){
        return orderService.confirmGoods(orders);
    }

    @GetMapping("order/comment")
    public ResponseResult getCommentByGoodsId(@RequestHeader("token") String token) throws Exception {
        return goodsCommentService.selectCommentByUid(token);
    }

    @PostMapping("order/sumbitComment")
    public ResponseResult sumbitComment(@RequestBody List<GoodsComment> goodsCommentList){
        for (GoodsComment goodsComment: goodsCommentList) {
            goodsCommentService.submitComment(goodsComment);
        }
        return new ResponseResult(200, "success");
    }

}
