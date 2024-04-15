package com.shop.Service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.shop.Entity.Goods;
import com.shop.Entity.User;
import com.shop.Mapper.GoodsMapper;
import com.shop.Mapper.SellerMapper;
import com.shop.Service.GoodsService;
import com.shop.Service.ImageService;
import com.shop.Utils.JwtUtil;
import com.shop.Utils.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Service
public class GoodsServiceImpl implements GoodsService {

    @Autowired
    private GoodsMapper goodsMapper;

    @Autowired
    private ImageService imageService;

    @Autowired
    private SellerMapper sellerMapper;

    private ResponseResult rr = new ResponseResult(200,"fail");

    @Value("${upload.goodsImageSrc}")
    private String goodsImageSrc;

    @Override
    public ResponseResult getAllGoods() {
        LambdaQueryWrapper<Goods> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Goods::getStatus, "1");
        List<Map<String, Object>> goods_maps = goodsMapper.selectMaps(queryWrapper);
        rr.setMsg("success");
        rr.setData(goods_maps);
        return rr;
    }

    @Override
    public ResponseResult searchGoods(String key_words) {
        LambdaQueryWrapper<Goods> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(Goods::getGoods_name, key_words);
        List<Map<String, Object>> goods_maps = goodsMapper.selectMaps(queryWrapper);
        rr.setMsg("success");
        rr.setData(goods_maps);
        return rr;
    }

    @Override
    public ResponseResult getDetailGoods(String goods_id) {
        LambdaQueryWrapper<Goods> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Goods::getGoods_id, goods_id);
        List<Map<String, Object>> mapList = goodsMapper.selectMaps(queryWrapper);
        mapList.get(0).put("seller_name", sellerMapper.selectById((Serializable) mapList.get(0).get("create_uid")).getSeller_name());
        rr.setMsg("success");
        rr.setData(mapList);
        return rr;
    }

    @Override
    public ResponseResult addGoods(MultipartFile file, Goods goods, String token) throws Exception {
        Long uid = JwtUtil.getUidByToken(token);
        goods.setCreate_uid(uid);
        goods.setStatus("1");
        if (goodsMapper.insert(goods) > 0){
            ResponseResult result = imageService.uploadFile(file, goodsImageSrc, String.valueOf(goods.getGoods_id()));
            if (result.getMsg().equals("success")){
                goods.setGoods_img(result.getData().toString());
                goodsMapper.updateById(goods);
                rr.setMsg("success");
            }
        }
        return rr;
    }

    @Override
    public ResponseResult updateGoods(Goods goods, String token) throws Exception {
        Long uid = JwtUtil.getUidByToken(token);
        goodsMapper.updateById(goods);
        rr.setMsg("success");
        return rr;
    }

    @Override
    public ResponseResult deleteGoods(Goods goods, String token) throws Exception {
        Long uid = JwtUtil.getUidByToken(token);
        LambdaQueryWrapper<Goods> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Goods::getGoods_id, goods.getGoods_id());
        queryWrapper.eq(Goods::getCreate_uid, uid);
        if (goodsMapper.delete(queryWrapper) > 0){
            rr.setMsg("success");
        }
        return rr;
    }

    @Override
    public ResponseResult getMyGoods(String token) throws Exception {
        Long uid = JwtUtil.getUidByToken(token);
        LambdaQueryWrapper<Goods> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Goods::getCreate_uid, uid);
        rr.setMsg("success");
        rr.setData(goodsMapper.selectMaps(queryWrapper));
        return rr;
    }

    @Override
    public ResponseResult getAllGoodsByAdmin() {
        LambdaQueryWrapper<Goods> queryWrapper = new LambdaQueryWrapper<>();
        List<Map<String, Object>> goods_maps = goodsMapper.selectMaps(queryWrapper);
        rr.setMsg("success");
        rr.setData(goods_maps);
        return rr;
    }

}
