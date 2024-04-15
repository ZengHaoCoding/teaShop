package com.shop.Service;

import com.shop.Entity.Address;
import com.shop.Entity.Cart;
import com.shop.Entity.Seller;
import com.shop.Entity.User;
import com.shop.Utils.ResponseResult;

public interface UserService {

    ResponseResult register(User user);

    ResponseResult getUserInfo(String token) throws Exception;

    ResponseResult getCartInfo(String token) throws Exception;

    ResponseResult addCart(Cart cart, String token) throws Exception;

    ResponseResult decCart(Cart cart, String token) throws Exception;

    ResponseResult removeCart(Cart cart);

    ResponseResult addSeller(Seller seller, String token) throws Exception;

    ResponseResult getSellerInfo(String token) throws Exception;

    ResponseResult getSellerInfoByAdmin(Seller seller);

    ResponseResult passAuditing(Seller seller);

    ResponseResult getAddress(String token) throws Exception;

    ResponseResult removeAddress(Address address, String token) throws Exception;

    ResponseResult insertAddress(Address address, String token) throws Exception;

    ResponseResult selectAddressByUid(Address address);

    ResponseResult selectAllApplySeller();

    ResponseResult selectAllUser();


}
