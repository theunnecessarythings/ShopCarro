package com.coviam.shopcarro.addToCart.services;

import com.coviam.shopcarro.addToCart.CustomException;
import com.coviam.shopcarro.addToCart.dto.CartDetailsDto;

public interface IAddToCartService {
    public Boolean addToCart(String email,String merchantId,String Id) throws CustomException;
    public boolean delItem(String email,String merchantId,String Id) throws CustomException;
    public CartDetailsDto getCart(String email);
}

