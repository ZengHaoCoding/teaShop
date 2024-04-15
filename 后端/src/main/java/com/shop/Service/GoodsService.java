package com.shop.Service;

import com.shop.Entity.Goods;
import com.shop.Utils.ResponseResult;
import org.springframework.web.multipart.MultipartFile;

public interface GoodsService {
    ResponseResult getAllGoods();

    ResponseResult searchGoods(String key_words);

    ResponseResult getDetailGoods(String goods_id);

//    以下方法需要权限1，2的用户调用
    ResponseResult addGoods(MultipartFile file, Goods goods, String token) throws Exception;

    ResponseResult updateGoods(Goods goods, String token) throws Exception;

    ResponseResult deleteGoods(Goods goods, String token) throws Exception;

    ResponseResult getMyGoods(String token) throws Exception;

    ResponseResult getAllGoodsByAdmin();
}
