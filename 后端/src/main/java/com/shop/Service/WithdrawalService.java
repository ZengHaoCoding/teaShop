package com.shop.Service;

import com.shop.Entity.Withdrawal;
import com.shop.Utils.ResponseResult;

public interface WithdrawalService {

    ResponseResult selectAllWithdrawal();

    ResponseResult updateWithdrawal(Withdrawal withdrawal);

    ResponseResult createWithdrawal(Withdrawal withdrawal, String token) throws Exception;

    ResponseResult getMyWithdrawal(String token) throws Exception;

}
