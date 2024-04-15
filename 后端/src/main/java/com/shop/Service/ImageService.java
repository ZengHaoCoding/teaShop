package com.shop.Service;

import com.shop.Utils.ResponseResult;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public interface ImageService {

    ResponseResult uploadFile(MultipartFile file, String filePath, String goodsImgName);
}
