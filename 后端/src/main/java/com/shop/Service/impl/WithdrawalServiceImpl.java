package com.shop.Service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.shop.Entity.Seller;
import com.shop.Entity.Withdrawal;
import com.shop.Mapper.SellerMapper;
import com.shop.Mapper.WithdrawalMapper;
import com.shop.Service.WithdrawalService;
import com.shop.Utils.JwtUtil;
import com.shop.Utils.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Service
public class WithdrawalServiceImpl implements WithdrawalService {

    @Autowired
    private WithdrawalMapper withdrawalMapper;

    @Autowired
    private SellerMapper sellerMapper;

    @Override
    public ResponseResult selectAllWithdrawal() {
        ResponseResult rr = new ResponseResult(200, "fail");
        LambdaQueryWrapper<Withdrawal> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByAsc(Withdrawal::getStatus);
        List<Map<String, Object>> mapList = withdrawalMapper.selectMaps(queryWrapper);
        for (Map<String, Object> ml:mapList) {
            LambdaQueryWrapper<Seller> queryWrapper1 = new LambdaQueryWrapper<>();
            queryWrapper1.eq(Seller::getUid, ml.get("seller_uid"));
            Seller seller = sellerMapper.selectOne(queryWrapper1);
            ml.put("seller_name", seller.getSeller_name());
            ml.put("real_name", seller.getReal_name());
            ml.put("card_number", seller.getCard_number());
            ml.put("contact_info", seller.getContact_info());
        }
        rr.setData(mapList);
        rr.setMsg("success");
        return rr;
    }

    @Override
    public ResponseResult updateWithdrawal(Withdrawal withdrawal){
        ResponseResult rr = new ResponseResult(200, "fail");
        if (withdrawal.getStatus().equals("2")){
            Withdrawal withdrawal1 = withdrawalMapper.selectOne(new LambdaQueryWrapper<Withdrawal>().eq(Withdrawal::getId, withdrawal.getId()));
            Seller seller = sellerMapper.selectById(withdrawal1.getSeller_uid());
            seller.setBalance(String.valueOf(Double.parseDouble(seller.getBalance()) + Double.parseDouble(withdrawal1.getMoney())));
            sellerMapper.updateById(seller);
        }
        if (withdrawalMapper.updateById(withdrawal) > 0){
            rr.setMsg("success");
        }
        return rr;
    }

    @Override
    public ResponseResult createWithdrawal(Withdrawal withdrawal, String token) throws Exception {
        ResponseResult rr = new ResponseResult(200, "fail");
        Long uid = JwtUtil.getUidByToken(token);
        withdrawal.setSeller_uid(uid);
        Seller seller = sellerMapper.selectById(uid);
        Double now_balance = Double.parseDouble(seller.getBalance()) - Double.parseDouble(withdrawal.getMoney());
        if (now_balance < 0.00){
            now_balance = 0.00;
        }
        seller.setBalance(String.valueOf(now_balance));
        if (sellerMapper.updateById(seller) > 0) {
            if (withdrawalMapper.insert(withdrawal) > 0){
                rr.setMsg("success");
            }
        }
        return rr;
    }

    @Override
    public ResponseResult getMyWithdrawal(String token) throws Exception {
        ResponseResult rr = new ResponseResult(200, "fail");
        Long uid = JwtUtil.getUidByToken(token);
        LambdaQueryWrapper<Withdrawal> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Withdrawal::getSeller_uid, uid);
        queryWrapper.orderByAsc(Withdrawal::getStatus);
        rr.setMsg("success");
        rr.setData(withdrawalMapper.selectMaps(queryWrapper));
        return rr;
    }
}
