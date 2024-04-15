package com.shop.Web;

import com.shop.Entity.HomePage;
import com.shop.Service.HomePageService;
import com.shop.Utils.ResponseResult;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Api("首页推荐页管理")
@RestController
public class HomePageController {

    @Autowired
    private HomePageService homepageService;

    @GetMapping("home/recommandPage")
    public ResponseResult getRecommendationPage(){
        return homepageService.getRecommendationPage();
    }

    @PostMapping("home/setPage")
    @PreAuthorize("@check.hasAuthority(1)")
    public ResponseResult setRecommendationPage(@RequestParam(value = "file") MultipartFile file, HomePage homepage){
        return homepageService.setRecommendationPage(file, homepage);
    }

    @PostMapping("home/removePage")
    @PreAuthorize("@check.hasAuthority(1)")
    public ResponseResult removeRecommendationPage(@RequestBody HomePage homepage){
        return homepageService.removeRecommendationPage(homepage);
    }

}
