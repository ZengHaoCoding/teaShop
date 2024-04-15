package com.shop.Service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.shop.Entity.*;
import com.shop.Mapper.*;
import com.shop.Service.UserService;
import com.shop.Utils.JwtUtil;
import com.shop.Utils.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private CartMapper cartMapper;

    @Autowired
    private SellerMapper sellerMapper;

    @Autowired
    private GoodsMapper goodsMapper;

    @Autowired
    private AddressMapper addressMapper;

    private ResponseResult rr = new ResponseResult(200, "fail");


    @Override
    public ResponseResult register(User user) {
        user.setRole("3");
        BCryptPasswordEncoder bc = new BCryptPasswordEncoder();
        user.setPassword(bc.encode(user.getPassword()));
        if (userMapper.insert(user) > 0){
            rr.setMsg("success");
            rr.setData(null);
        }
        return rr;
    }

    @Override
    public ResponseResult getUserInfo(String token) throws Exception {
        Long uid = JwtUtil.getUidByToken(token);
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUid, uid);
        rr.setMsg("success");
        rr.setData(userMapper.selectMaps(queryWrapper));
        return rr;
    }

    @Override
    public ResponseResult getCartInfo(String token) throws Exception {
        Long uid = JwtUtil.getUidByToken(token);
        LambdaQueryWrapper<Cart> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Cart::getUid, uid);
        List<Map<String, Object>> mapList = cartMapper.selectMaps(queryWrapper);
        rr.setMsg("success");
        if (mapList.size() > 0){
            for (Map<String, Object> ml:mapList) {
                LambdaQueryWrapper<Goods> queryWrapper2 = new LambdaQueryWrapper<>();
                queryWrapper2.eq(Goods::getGoods_id, ml.get("goods_id"));
                List<Map<String, Object>> mapList1 = goodsMapper.selectMaps(queryWrapper2);
                ml.put("goods_img", mapList1.get(0).get("goods_img"));
                ml.put("goods_name", mapList1.get(0).get("goods_name"));
                ml.put("goods_money", mapList1.get(0).get("goods_money"));
            }
            rr.setData(mapList);
        }else {
            rr.setData(null);
        }
        return rr;
    }

    @Override
    public ResponseResult addCart(Cart cart, String token) throws Exception {
        Long uid = JwtUtil.getUidByToken(token);
        cart.setUid(uid);
        LambdaQueryWrapper<Cart> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Cart::getUid, uid);
        queryWrapper.eq(Cart::getGoods_id, cart.getGoods_id());
        if (cartMapper.selectCount(queryWrapper) > 0){
            Cart cart1 = cartMapper.selectOne(queryWrapper);
            cart1.setCart_id(Long.valueOf(cart.getGoods_id()));
            cart1.setNumber(cart1.getNumber() + cart.getNumber());
            if (cartMapper.update(cart1, queryWrapper) > 0){
                rr.setMsg("success");
            }
        }else {
            if (cartMapper.insert(cart) > 0){
                rr.setMsg("success");
            }
        }
        rr.setData(null);
        return rr;
    }

    @Override
    public ResponseResult decCart(Cart cart, String token) throws Exception {
        Long uid = JwtUtil.getUidByToken(token);
        cart.setUid(uid);
        LambdaQueryWrapper<Cart> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Cart::getUid, uid);
        queryWrapper.eq(Cart::getGoods_id, cart.getGoods_id());
        Cart cart1 = cartMapper.selectOne(queryWrapper);
        if (cart1.getNumber() > 1){
            cart1.setNumber(cart1.getNumber() - 1);
            cartMapper.update(cart1, queryWrapper);
        }else {
            cartMapper.delete(queryWrapper);
        }
        rr.setData(null);
        return rr;
    }


    @Override
    public ResponseResult removeCart(Cart cart) {
        LambdaQueryWrapper<Cart> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Cart::getCart_id, cart.getGoods_id());
        int result = cartMapper.delete(queryWrapper);
        if (result > 0){
            rr.setMsg("success");
            rr.setData(null);
        }
        return rr;
    }

    @Override
    public ResponseResult addSeller(Seller seller, String token) throws Exception {
        Long uid = JwtUtil.getUidByToken(token);
        seller.setUid(uid);
        sellerMapper.insert(seller);
        rr.setMsg("success");
        rr.setData(null);
        return rr;
    }

    @Override
    public ResponseResult getSellerInfo(String token) throws Exception {
        Long uid = JwtUtil.getUidByToken(token);
        LambdaQueryWrapper<Seller> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Seller::getUid, uid);
        rr.setMsg("success");
        rr.setData(sellerMapper.selectMaps(queryWrapper));
        return rr;
    }

    @Override
    public ResponseResult getSellerInfoByAdmin(Seller seller) {
        LambdaQueryWrapper<Seller> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Seller::getUid, seller.getUid());
        rr.setMsg("success");
        rr.setData(sellerMapper.selectOne(queryWrapper));
        return rr;
    }

    @Override
    public ResponseResult passAuditing(Seller seller) {
        seller.setStatus("1");
        User user = new User();
        user.setUid(seller.getUid());
        user.setRole("2");
        userMapper.updateById(user);
        if (sellerMapper.updateById(seller) > 0){
            rr.setMsg("success");
        }else {
            rr.setMsg("fail");
        }
        rr.setData(null);
        return rr;
    }

    @Override
    public ResponseResult getAddress(String token) throws Exception {
        Long uid = JwtUtil.getUidByToken(token);
        LambdaQueryWrapper<Address> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Address::getUid, uid);
        rr.setMsg("success");
        rr.setData(addressMapper.selectMaps(queryWrapper));
        return rr;
    }

    @Override
    public ResponseResult removeAddress(Address address, String token) throws Exception {
        Long uid = JwtUtil.getUidByToken(token);
        address.setUid(uid);
        LambdaQueryWrapper<Address> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Address::getUid, address.getUid());
        queryWrapper.eq(Address::getAddress_id, address.getAddress_id());
        if (addressMapper.delete(queryWrapper) > 0){
            rr.setMsg("success");
            rr.setData(null);
        }
        return rr;
    }

    @Override
    public ResponseResult insertAddress(Address address, String token) throws Exception {
        Long uid = JwtUtil.getUidByToken(token);
        address.setUid(uid);
        if (addressMapper.insert(address) > 0){
            rr.setMsg("success");
            rr.setData(null);
        }
        return rr;
    }

    @Override
    public ResponseResult selectAddressByUid(Address address) {
        LambdaQueryWrapper<Address> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Address::getUid, address.getUid());
        rr.setMsg("success");
        rr.setData(addressMapper.selectMaps(queryWrapper));
        return rr;
    }

    @Override
    public ResponseResult selectAllApplySeller() {
        LambdaQueryWrapper<Seller> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Seller::getStatus, "0");
        rr.setMsg("success");
        rr.setData(sellerMapper.selectMaps(queryWrapper));
        return rr;
    }

    @Override
    public ResponseResult selectAllUser() {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        List<Map<String, Object>> mapList = userMapper.selectMaps(queryWrapper);
        for (Map<String, Object> ml:mapList) {
            Seller seller = new Seller();
            seller.setBalance(null);
            seller.setSeller_name(null);
            seller.setStatus(null);
            if (!ml.get("role").equals("3")){
                LambdaQueryWrapper<Seller> queryWrapper1 = new LambdaQueryWrapper<>();
                queryWrapper1.eq(Seller::getUid, ml.get("uid"));
                seller = sellerMapper.selectOne(queryWrapper1);
            }
            ml.put("balance", seller.getBalance());
            ml.put("seller_name", seller.getSeller_name());
            ml.put("seller_statis", seller.getStatus());
        }
        rr.setMsg("success");
        rr.setData(mapList);
        return rr;
    }
}
