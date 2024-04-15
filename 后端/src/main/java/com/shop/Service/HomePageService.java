package com.shop.Service;

import com.shop.Entity.HomePage;
import com.shop.Utils.ResponseResult;
import org.springframework.web.multipart.MultipartFile;

public interface HomePageService {
    ResponseResult getRecommendationPage();
    ResponseResult setRecommendationPage(MultipartFile file, HomePage homepage);
    ResponseResult removeRecommendationPage(HomePage homepage);
}
