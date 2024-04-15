package com.shop.Web;

import com.shop.Entity.Goods;
import com.shop.Service.ImageService;
import com.shop.Utils.ResponseResult;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Api("接口测试")
@RestController
public class WebTest {

    @Autowired
    private ImageService imageService;

    @Value("${upload.goodsImageSrc}")
    private String goodsImageSrc;

    @GetMapping("loginSuccess")
    @PreAuthorize("@check.hasAuthority(2)")
    public String loginSuccess(){
        return "hello";
    }


//    TODO 商品上架逻辑已跑通，文件与商品信息同时上架。
    @PostMapping("/test/uploadimg")
    public ResponseResult uploadimg(@RequestParam(value = "file") MultipartFile file, Goods goods){
        System.out.println(goods.getGoods_id());
        System.out.println(goods.getGoods_name());
        return imageService.uploadFile(file, goodsImageSrc, goods.getGoods_name());
    }
}
