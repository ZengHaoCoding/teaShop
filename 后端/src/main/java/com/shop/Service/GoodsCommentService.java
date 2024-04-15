package com.shop.Service;

import com.shop.Entity.Goods;
import com.shop.Entity.GoodsComment;
import com.shop.Utils.ResponseResult;

public interface GoodsCommentService {
//    创建待评价记录已于商品确认收货的方法里面整合完成

    ResponseResult submitComment(GoodsComment goodsComment);

    ResponseResult selectCommentByUid(String token) throws Exception;

    ResponseResult selectCommentByGoodsId(String goods_id);

}
