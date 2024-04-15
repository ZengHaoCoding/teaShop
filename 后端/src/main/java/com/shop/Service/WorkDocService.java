package com.shop.Service;

import com.shop.Entity.WorkDoc;
import com.shop.Entity.WorkDocDetail;
import com.shop.Utils.ResponseResult;
import org.springframework.web.multipart.MultipartFile;

public interface WorkDocService {

    ResponseResult selectWordDoc(String order_id);

    ResponseResult selectWordDocBySellerUid(String token) throws Exception;


    ResponseResult createWorkDoc(WorkDoc workDoc, String token) throws Exception;

    ResponseResult sendMessage(WorkDocDetail workDocDetail, String token) throws Exception;

    ResponseResult sendMessageAndFile(MultipartFile file, WorkDocDetail workDocDetail, String token) throws Exception;
}
