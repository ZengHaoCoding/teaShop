package com.shop.Service;

import com.shop.Entity.OrderDetail;
import com.shop.Entity.Orders;
import com.shop.Utils.ResponseResult;

import java.util.List;
import java.util.Map;

public interface OrderService {
    ResponseResult getAllOrder(String token) throws Exception;

    ResponseResult getOrderDetail(String orders_id);

    Map<String, Object> createOrder(List<OrderDetail> ordersList, String token, Long seller_uid, String address_id) throws Exception;

    ResponseResult cancelOrder(Orders orders);

    ResponseResult payOrder(Orders orders);

    ResponseResult snedOutGoods(Orders orders);

    ResponseResult confirmGoods(Orders orders);

    ResponseResult getSellOrder(String token) throws Exception;

    ResponseResult selectAllOrdersByAdmin();
}
