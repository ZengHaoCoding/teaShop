package com.shop.Service.impl;

import cn.hutool.core.io.FileUtil;
import com.shop.Service.ImageService;
import com.shop.Utils.ResponseResult;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

@Service
public class ImageServiceImpl implements ImageService {

    private static final List<String> ALLOW_TYPES = Arrays.asList("image/png", "image/jpg", "image/jpeg", "image/bmg");

    private ResponseResult rr = new ResponseResult(200, "fail");

    @Override
    public ResponseResult uploadFile(MultipartFile file, String filePath, String goodsImgName) {
        try {
            //校验文件类型
            String contentType = file.getContentType();
            if(ALLOW_TYPES.contains(contentType)){
                InputStream inputStream = file.getInputStream();
                //校验文件的内容
                BufferedImage image = ImageIO.read(inputStream);
                if(true){
                    // 获取文件名
                    String fileName = file.getOriginalFilename();
                    //准备目标路径
                    File dest = new File(filePath, fileName);
                    file.transferTo(dest);
                    // 获取文件后缀
                    String suffixName = fileName.substring(fileName.lastIndexOf("."));
                    FileUtil.rename(dest, goodsImgName, true, true);
                    rr.setMsg("success");
                    rr.setData(goodsImgName + suffixName);
                }else {
                    System.out.println("image为空");
                }
                inputStream.close();
            }else {
                System.out.println("文件类型不对");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rr;
    }
}
