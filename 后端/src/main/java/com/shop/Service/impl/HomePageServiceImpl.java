package com.shop.Service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.shop.Entity.HomePage;
import com.shop.Mapper.HomePageMapper;
import com.shop.Service.ImageService;
import com.shop.Service.HomePageService;
import com.shop.Utils.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@Service
public class HomePageServiceImpl implements HomePageService {

    @Autowired
    private HomePageMapper homepageMapper;

    @Autowired
    private ImageService imageService;

    @Value("${upload.recommendationPageImageSrc}")
    private String recommendationPageImageSrc;

    private ResponseResult rr = new ResponseResult(200,"fail");

    @Override
    public ResponseResult getRecommendationPage() {
        LambdaQueryWrapper<HomePage> queryWrapper = new LambdaQueryWrapper<>();
        List<Map<String, Object>> index_maps = homepageMapper.selectMaps(queryWrapper);
        rr.setMsg("success");
        rr.setData(index_maps);
        return rr;
    }

    @Override
    public ResponseResult setRecommendationPage(MultipartFile file, HomePage homepage) {
        if (homepageMapper.insert(homepage) > 0){
            ResponseResult result = imageService.uploadFile(file, recommendationPageImageSrc, String.format("%06d", homepage.getId()));
            if (result.getMsg().equals("success")){
                homepage.setImage((String) result.getData());
                if (homepageMapper.updateById(homepage) > 0){
                    rr.setMsg("success");
                    rr.setData(null);
                }
            }
        }
        return rr;
    }

    @Override
    public ResponseResult removeRecommendationPage(HomePage homepage) {
        LambdaQueryWrapper<HomePage> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(HomePage::getId, homepage.getId());
        if (homepageMapper.delete(queryWrapper) > 0){
            rr.setMsg("success");
            rr.setData(null);
        }
        return rr;
    }
}
