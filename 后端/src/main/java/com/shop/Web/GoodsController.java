package com.shop.Web;

import com.shop.Entity.Goods;
import com.shop.Service.GoodsService;
import com.shop.Service.GoodsCommentService;
import com.shop.Utils.ResponseResult;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Api("商品管理")
@RestController
public class GoodsController {

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private GoodsCommentService goodsCommentService;

    @GetMapping("/goods")
    public ResponseResult goods(){
        return goodsService.getAllGoods();
    }

    @GetMapping("/goods/search/{key_words}")
    public ResponseResult serachGoods(@PathVariable String key_words){
        return goodsService.searchGoods(key_words);
    }

    @GetMapping("/goods/{goods_id}")
    public ResponseResult getDetailGoods(@PathVariable String goods_id){
        return goodsService.getDetailGoods(goods_id);
    }

    @GetMapping("/goods/comment/{goods_id}")
    public ResponseResult getCommentByGoodsId(@PathVariable String goods_id){
        return goodsCommentService.selectCommentByGoodsId(goods_id);
    }


}
