package com.shop.Web;


import com.shop.Entity.WorkDoc;
import com.shop.Entity.WorkDocDetail;
import com.shop.Service.UserService;
import com.shop.Service.WorkDocService;
import com.shop.Utils.ResponseResult;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Api("工单系统api")
@RestController
public class WorkDocController {

    @Autowired
    private WorkDocService workDocService;

    @GetMapping("/workdoc/{order_id}")
    public ResponseResult selectMsgByOrderId(@PathVariable String order_id){
        return workDocService.selectWordDoc(order_id);
    }

    @PostMapping("/workdoc/create")
    public ResponseResult createWorkDoc(@RequestBody WorkDoc workDoc, @RequestHeader("token") String token) throws Exception {
        return workDocService.createWorkDoc(workDoc,token);
    }

//    此接口需要上传文件
    @PostMapping("/workdoc/sendandfile")
    public ResponseResult sendMessage2(@RequestParam(value = "file") MultipartFile file, WorkDocDetail workDocDetail, @RequestHeader("token") String token) throws Exception {
        return workDocService.sendMessageAndFile(file,workDocDetail,token);
    }

//    此接口无须上传文件
    @PostMapping("/workdoc/send")
    public ResponseResult sendMessage(WorkDocDetail workDocDetail, @RequestHeader("token") String token) throws Exception {
        return workDocService.sendMessage(workDocDetail,token);
    }

    @GetMapping("/workdoc/list")
    @PreAuthorize("@check.hasAuthority(2)")
    public ResponseResult selectWorkDocList(@RequestHeader("token") String token) throws Exception {
        return workDocService.selectWordDocBySellerUid(token);
    }

}
