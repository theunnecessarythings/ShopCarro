package com.coviam.shopcarro.addToCart.services;

import com.coviam.shopcarro.addToCart.dto.CartDetailsDto;

public interface IAddToCartService {
    public void addToCart(String email,String merchantId,String Id);
    public boolean delItem(String email,String merchantId,String Id);
    public CartDetailsDto getCart(String email);
}

