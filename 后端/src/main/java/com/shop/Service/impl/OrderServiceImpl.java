package com.shop.Service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.shop.Entity.*;
import com.shop.Mapper.*;
import com.shop.Service.OrderService;
import com.shop.Utils.JwtUtil;
import com.shop.Utils.OrderUtil;
import com.shop.Utils.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrdersMapper ordersMapper;

    @Autowired
    private OrderDetailMapper orderdetailMapper;

    @Autowired
    private AddressMapper addressMapper;

    @Autowired
    private SellerMapper sellerMapper;

    @Autowired
    private GoodsMapper goodsMapper;

    @Autowired
    private GoodsCommentMapper goodsCommentMapper;

    private ResponseResult rr = new ResponseResult(200, "fail");

    @Override
    public ResponseResult getAllOrder(String token) throws Exception {
        Long uid = JwtUtil.getUidByToken(token);
        LambdaQueryWrapper<Orders> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Orders::getUid, uid);
        queryWrapper.orderByDesc(Orders::getCreate_time);
        List<Map<String, Object>> mapList = ordersMapper.selectMaps(queryWrapper);
        for (Map<String, Object> itemMap:mapList) {
//            根据order_id获取详细信息
            LambdaQueryWrapper<OrderDetail> queryWrapper2 = new LambdaQueryWrapper<>();
            queryWrapper2.eq(OrderDetail::getOrder_id, itemMap.get("order_id"));
            List<Map<String, Object>> orderDetailList = orderdetailMapper.selectMaps(queryWrapper2);
            for (Map<String, Object> od:orderDetailList) {
                LambdaQueryWrapper<Goods> queryWrapper3 = new LambdaQueryWrapper<>();
                queryWrapper3.eq(Goods::getGoods_id, od.get("goods_id"));
                Goods goods = goodsMapper.selectOne(queryWrapper3);
                od.put("goods_name", goods.getGoods_name());
            }
//            数据整合
            itemMap.put("order_detail", orderDetailList);
//            根据seller_id获取店铺信息
            Seller seller = sellerMapper.selectById((Serializable) itemMap.get("seller_uid"));
            itemMap.put("seller_name", seller.getSeller_name());
//            根据order_id获取评论状态
            LambdaQueryWrapper<GoodsComment> queryWrapper3 = new LambdaQueryWrapper<>();
            queryWrapper3.eq(GoodsComment::getOrder_id, itemMap.get("order_id"));
            List<Map<String, Object>> mapList1 = goodsCommentMapper.selectMaps(queryWrapper3);
//            此处comment_status仅判断有无完成评论(true/false)，当status为1时，即已完成
            if (!mapList1.isEmpty()){
                if (mapList1.get(0).get("status").equals("0")){
                    itemMap.put("comment_status", false);
                }else {
                    itemMap.put("comment_status", true);
                }
            }else {
                itemMap.put("comment_status", false);
            }
        }
        rr.setMsg("success");
        rr.setData(mapList);
        return rr;
    }

    @Override
    public ResponseResult getOrderDetail(String orders_id) {
        LambdaQueryWrapper<Orders> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Orders::getOrder_id, orders_id);
        List<Map<String, Object>> result = ordersMapper.selectMaps(queryWrapper);
        for (Map<String, Object> itemMap:result) {
            LambdaQueryWrapper<OrderDetail> queryWrapper2 = new LambdaQueryWrapper<>();
            queryWrapper2.eq(OrderDetail::getOrder_id, itemMap.get("order_id"));
            List<Map<String, Object>> orderDetailList = orderdetailMapper.selectMaps(queryWrapper2);
            for (Map<String, Object> od:orderDetailList) {
                LambdaQueryWrapper<Goods> queryWrapper3 = new LambdaQueryWrapper<>();
                queryWrapper3.eq(Goods::getGoods_id, od.get("goods_id"));
                Goods goods = goodsMapper.selectOne(queryWrapper3);
                od.put("goods_name", goods.getGoods_name());
                od.put("goods_img", goods.getGoods_img());
            }
            itemMap.put("order_detail", orderDetailList);
//            根据seller_id获取店铺信息
            Seller seller = sellerMapper.selectById((Serializable) itemMap.get("seller_uid"));
            itemMap.put("seller_name", seller.getSeller_name());
        }
        Address address = addressMapper.selectById((Serializable) result.get(0).get("address_id"));
        result.get(0).put("post_name", address.getPost_name());
        result.get(0).put("post_mobile", address.getPost_mobile());
        result.get(0).put("post_address", address.getPost_address());
        rr.setMsg("success");
        rr.setData(result);
        return rr;
    }

    @Override
    public Map<String, Object> createOrder(List<OrderDetail> ordersList, String token, Long seller_uid, String address_id) throws Exception {
        Long uid = JwtUtil.getUidByToken(token);
        String orderNo = OrderUtil.createOrderNo(uid);
        Double sumPrice = 0.00;
        Orders orders = new Orders();
        orders.setOrder_id(orderNo);
        orders.setSeller_uid(seller_uid);
        orders.setUid(uid);
        for (OrderDetail order: ordersList) {
//            获取某个商品详细信息，算钱
            LambdaQueryWrapper<Goods> goodsWrapper = new LambdaQueryWrapper<>();
            goodsWrapper.eq(Goods::getGoods_id, order.getGoods_id());
            Goods goods = goodsMapper.selectOne(goodsWrapper);

            order.setGoods_money(goods.getGoods_money());
            order.setWeight_unit(goods.getWeight_unit());
            order.setOrder_id(orderNo);

            sumPrice += Double.parseDouble(goods.getGoods_money()) * order.getNumber();

//            向sql存入订单细则
            orderdetailMapper.insert(order);
        }
        orders.setOrder_money(String.valueOf(sumPrice));
        orders.setAddress_id(Long.parseLong(address_id));
//        向sql存入订单梗概
        ordersMapper.insert(orders);
        Map<String, Object> result = new HashMap<>();
        result.put("order_id", orders.getOrder_id());
        rr.setMsg("success");
        rr.setData(null);
        return result;
    }

    @Override
    public ResponseResult cancelOrder(Orders orders) {
        orders.setStatus("4");
        System.out.println(orders.getOrder_id());
        if (ordersMapper.updateById(orders) > 0){
            rr.setMsg("success");
        }
        return rr;
    }

    @Override
    public ResponseResult payOrder(Orders orders) {
        orders.setStatus("1");
        System.out.println(orders.getOrder_id());
        if (ordersMapper.updateById(orders) > 0){
            rr.setMsg("success");
        }
        return rr;
    }

    @Override
    public ResponseResult snedOutGoods(Orders orders) {
        orders.setStatus("2");
        if (ordersMapper.updateById(orders) > 0){
            rr.setMsg("success");
        }
        return rr;
    }

    @Override
    public ResponseResult confirmGoods(Orders orders) {
        orders.setStatus("3");
        if (ordersMapper.updateById(orders) > 0){
            LambdaQueryWrapper<Orders> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(Orders::getOrder_id, orders.getOrder_id());
            queryWrapper.select(Orders::getSeller_uid, Orders::getOrder_money);
            List<Orders> ordersList = ordersMapper.selectList(queryWrapper);
            Seller seller = new Seller();
            seller.setUid(ordersList.get(0).getSeller_uid());
            seller.setBalance(ordersList.get(0).getOrder_money());
            if (sellerMapper.updateById(seller) > 0){
                // 创建待评价记录
                GoodsComment goods_comment = new GoodsComment();
                goods_comment.setOrder_id(orders.getOrder_id());
                goods_comment.setStatus("0");
                LambdaQueryWrapper<OrderDetail> queryWrapper1 = new LambdaQueryWrapper<>();
                queryWrapper1.eq(OrderDetail::getOrder_id, orders.getOrder_id());
                List<Map<String, Object>> mapList = orderdetailMapper.selectMaps(queryWrapper1);
                for (Map<String, Object> od: mapList) {
                    goods_comment.setGoods_id(Long.parseLong((String) od.get("goods_id")));
                    goodsCommentMapper.insert(goods_comment);
//                    在order_detail表里面关联评论
                    LambdaQueryWrapper<OrderDetail> queryWrapper2 = new LambdaQueryWrapper<>();
                    queryWrapper2.eq(OrderDetail::getOrder_id, orders.getOrder_id());
                    queryWrapper2.eq(OrderDetail::getGoods_id, od.get("goods_id"));
                    OrderDetail order_detail = new OrderDetail();
                    order_detail.setComment_id(goods_comment.getComment_id());
                    orderdetailMapper.update(order_detail, queryWrapper2);
                }
                rr.setMsg("success");
            }
        }
        return rr;
    }

    @Override
    public ResponseResult getSellOrder(String token) throws Exception {
        Long uid = JwtUtil.getUidByToken(token);
        LambdaQueryWrapper<Orders> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Orders::getSeller_uid, uid);
        queryWrapper.orderByDesc(Orders::getCreate_time);
        List<Map<String, Object>> mapList = ordersMapper.selectMaps(queryWrapper);
        for (Map<String, Object> itemMap:mapList) {
            LambdaQueryWrapper<OrderDetail> queryWrapper2 = new LambdaQueryWrapper<>();
            queryWrapper2.eq(OrderDetail::getOrder_id, itemMap.get("order_id"));
            List<Map<String, Object>> orderDetailList = orderdetailMapper.selectMaps(queryWrapper2);
            for (Map<String, Object> od:orderDetailList) {
                LambdaQueryWrapper<Goods> queryWrapper3 = new LambdaQueryWrapper<>();
                queryWrapper3.eq(Goods::getGoods_id, od.get("goods_id"));
                Goods goods = goodsMapper.selectOne(queryWrapper3);
                od.put("goods_name", goods.getGoods_name());
            }
            itemMap.put("order_detail", orderDetailList);
        }
        rr.setMsg("success");
        rr.setData(mapList);
        return rr;
    }

    @Override
    public ResponseResult selectAllOrdersByAdmin() {
        LambdaQueryWrapper<Orders> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByDesc(Orders::getCreate_time);
        List<Map<String, Object>> mapList = ordersMapper.selectMaps(queryWrapper);
        for (Map<String, Object> itemMap:mapList) {
//            根据order_id获取详细信息
            LambdaQueryWrapper<OrderDetail> queryWrapper2 = new LambdaQueryWrapper<>();
            queryWrapper2.eq(OrderDetail::getOrder_id, itemMap.get("order_id"));
            List<Map<String, Object>> orderDetailList = orderdetailMapper.selectMaps(queryWrapper2);
            for (Map<String, Object> od:orderDetailList) {
                LambdaQueryWrapper<Goods> queryWrapper3 = new LambdaQueryWrapper<>();
                queryWrapper3.eq(Goods::getGoods_id, od.get("goods_id"));
                Goods goods = goodsMapper.selectOne(queryWrapper3);
                od.put("goods_name", goods.getGoods_name());
            }
//            数据整合
            itemMap.put("order_detail", orderDetailList);
//            根据seller_id获取店铺信息
            Seller seller = sellerMapper.selectById((Serializable) itemMap.get("seller_uid"));
            itemMap.put("seller_name", seller.getSeller_name());
        }
        rr.setMsg("success");
        rr.setData(mapList);
        return rr;
    }
}
