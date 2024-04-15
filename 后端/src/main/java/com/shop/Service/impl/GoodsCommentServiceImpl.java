package com.shop.Service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.shop.Entity.Goods;
import com.shop.Entity.GoodsComment;
import com.shop.Entity.Orders;
import com.shop.Mapper.GoodsCommentMapper;
import com.shop.Mapper.OrdersMapper;
import com.shop.Service.GoodsCommentService;
import com.shop.Utils.JwtUtil;
import com.shop.Utils.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class GoodsCommentServiceImpl implements GoodsCommentService {

    @Autowired
    private GoodsCommentMapper goodsCommentMapper;

    @Autowired
    private OrdersMapper ordersMapper;

    @Override
    public ResponseResult submitComment(GoodsComment goodsComment) {
        if (goodsCommentMapper.updateById(goodsComment) > 0){
            return new ResponseResult(200, "success");
        }
        return new ResponseResult(200, "fail");
    }

    @Override
    public ResponseResult selectCommentByUid(String token) throws Exception {
        Long uid = JwtUtil.getUidByToken(token);
        LambdaQueryWrapper<Orders> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Orders::getUid, uid);
        List<Map<String, Object>> ordersMapsList = ordersMapper.selectMaps(queryWrapper);
        List<GoodsComment> commentList = new ArrayList<>();
        for (Map<String, Object> oml: ordersMapsList) {
            LambdaQueryWrapper<GoodsComment> queryWrapper1 = new LambdaQueryWrapper<>();
            queryWrapper1.eq(GoodsComment::getOrder_id, oml.get("order_id"));
            commentList.add(goodsCommentMapper.selectOne(queryWrapper1));
        }
        if (commentList.size() > 0){
            return new ResponseResult(200, "success", commentList);
        }
        return new ResponseResult(200, "fail");
    }

    @Override
    public ResponseResult selectCommentByGoodsId(String goods_id) {
        LambdaQueryWrapper<GoodsComment> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(GoodsComment::getGoods_id, goods_id);
        queryWrapper.eq(GoodsComment::getStatus, "1");
        List<Map<String, Object>> mapList = goodsCommentMapper.selectMaps(queryWrapper);
        System.out.println(mapList);
        if (mapList.size() > 0){
            for (Map<String, Object> ml : mapList) {
                LambdaQueryWrapper<Orders> queryWrapper2 = new LambdaQueryWrapper<>();
                String order_id = (String) ml.get("order_id");
                queryWrapper2.eq(Orders::getOrder_id, order_id);
                queryWrapper2.select(Orders::getUid);
                Orders orders = ordersMapper.selectOne(queryWrapper2);
                ml.put("uid", orders.getUid());
            }
        }
        return new ResponseResult(200, "success", mapList);
    }
}
