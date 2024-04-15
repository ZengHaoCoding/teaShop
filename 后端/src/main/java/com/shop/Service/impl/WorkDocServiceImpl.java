package com.shop.Service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.shop.Entity.WorkDoc;
import com.shop.Entity.WorkDocDetail;
import com.shop.Mapper.WorkDocDetailMapper;
import com.shop.Mapper.WorkDocMapper;
import com.shop.Service.ImageService;
import com.shop.Service.WorkDocService;
import com.shop.Utils.JwtUtil;
import com.shop.Utils.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

@Service
public class WorkDocServiceImpl implements WorkDocService {

    @Autowired
    private WorkDocMapper workDocMapper;

    @Autowired
    private WorkDocDetailMapper workDocDetailMapper;

    @Autowired
    private ImageService imageService;

    @Value("${upload.workDocSrc}")
    private String workDocSrc;

    @Override
    public ResponseResult selectWordDoc(String order_id) {
        ResponseResult responseResult = new ResponseResult(200, "fail");
        LambdaQueryWrapper<WorkDoc> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(WorkDoc::getOrder_id, order_id);
        WorkDoc workDoc = workDocMapper.selectOne(queryWrapper);
        if (workDoc != null){
            LambdaQueryWrapper<WorkDocDetail> queryWrapper2 = new LambdaQueryWrapper<>();
            queryWrapper2.eq(WorkDocDetail::getWd_id, workDoc.getId());
            List<Map<String, Object>> mapList2 = workDocDetailMapper.selectMaps(queryWrapper2);
            Map<String, Object> mapList = new HashMap<>();
            mapList.put("id",workDoc.getId());
            mapList.put("buyer_id",workDoc.getBuyer_uid());
            mapList.put("seller_id",workDoc.getSeller_uid());
            mapList.put("status",workDoc.getStatus());
            mapList.put("detail", mapList2);
            responseResult.setData(mapList);
        }else {
            responseResult.setData(null);
        }
        responseResult.setMsg("success");
        return responseResult;
    }

    @Override
    public ResponseResult selectWordDocBySellerUid(String token) throws Exception {
        Long uid = JwtUtil.getUidByToken(token);
        ResponseResult responseResult = new ResponseResult(200, "fail");
        LambdaQueryWrapper<WorkDoc> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(WorkDoc::getSeller_uid, uid);
        queryWrapper.orderByDesc(WorkDoc::getStatus);
        responseResult.setMsg("success");
        responseResult.setData(workDocMapper.selectMaps(queryWrapper));
        return responseResult;
    }

    @Override
    public ResponseResult createWorkDoc(WorkDoc workDoc, String token) throws Exception {
        Long uid = JwtUtil.getUidByToken(token);
        workDoc.setBuyer_uid(uid);
        ResponseResult responseResult = new ResponseResult(200, "fail");
        if (workDocMapper.insert(workDoc) > 0){
            responseResult.setMsg("success");
        }
        return responseResult;
    }

    @Override
    public ResponseResult sendMessage(WorkDocDetail workDocDetail, String token) throws Exception {
        Long uid = JwtUtil.getUidByToken(token);
        workDocDetail.setCreate_uid(uid);
        LambdaQueryWrapper<WorkDoc> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(WorkDoc::getId, workDocDetail.getWd_id());
        WorkDoc workDoc = workDocMapper.selectOne(queryWrapper);
        if (Objects.equals(workDoc.getBuyer_uid(), uid)){
            workDoc.setStatus("1");
            workDocMapper.updateById(workDoc);
        }else if (Objects.equals(workDoc.getSeller_uid(), uid)){
            workDoc.setStatus("0");
            workDocMapper.updateById(workDoc);
        }
        ResponseResult responseResult = new ResponseResult(200, "fail");
        if (workDocDetailMapper.insert(workDocDetail) > 0){
            responseResult.setMsg("success");
        }
        return responseResult;
    }

    @Override
    public ResponseResult sendMessageAndFile(MultipartFile file, WorkDocDetail workDocDetail, String token) throws Exception {
        Long uid = JwtUtil.getUidByToken(token);
        workDocDetail.setCreate_uid(uid);
        LambdaQueryWrapper<WorkDoc> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(WorkDoc::getId, workDocDetail.getWd_id());
        WorkDoc workDoc = workDocMapper.selectOne(queryWrapper);
        if (Objects.equals(workDoc.getBuyer_uid(), uid)){
            workDoc.setStatus("1");
            workDocMapper.updateById(workDoc);
        }else if (Objects.equals(workDoc.getSeller_uid(), uid)){
            workDoc.setStatus("0");
            workDocMapper.updateById(workDoc);
        }
        ResponseResult responseResult = new ResponseResult(200, "fail");
        if (workDocDetailMapper.insert(workDocDetail) > 0){
            ResponseResult result = imageService.uploadFile(file, workDocSrc, String.valueOf(workDocDetail.getId()));
            if (result.getMsg().equals("success")){
                workDocDetail.setWd_img(result.getData().toString());
                workDocDetailMapper.updateById(workDocDetail);
                responseResult.setMsg("success");
            }
        }
        return responseResult;
    }
}
