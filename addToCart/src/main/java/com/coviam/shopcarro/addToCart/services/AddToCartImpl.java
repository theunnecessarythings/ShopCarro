package com.coviam.shopcarro.addToCart.services;

import com.coviam.shopcarro.addToCart.dto.CartDetailsDto;
import com.coviam.shopcarro.addToCart.dto.DetailsDto;
import com.coviam.shopcarro.addToCart.model.CartDetails;
import com.coviam.shopcarro.addToCart.repository.IAddToCartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AddToCartImpl implements IAddToCartService {
    @Autowired
    private IAddToCartRepository iAddToCartRepository;

    @Override
    public void addToCart(String email, String merchantId, String Id) {
           // CartDetails cartDetails1 = new CartDetails(email);
        if (!iAddToCartRepository.existsById(email)) {
            List<DetailsDto> detailsDtoList = new ArrayList<>();
            detailsDtoList.add(new DetailsDto(merchantId,Id));
            CartDetails cartDetails = new CartDetails(email,detailsDtoList);
            iAddToCartRepository.save(cartDetails);
        }
        else{
            Optional<CartDetails> cartDetailsOpt = iAddToCartRepository.findById(email);
            if(cartDetailsOpt.isPresent()) {
                CartDetails cartDetails = cartDetailsOpt.get();
                List<DetailsDto> list = new ArrayList<>();
                list = cartDetails.getDetailsDtos();
                list.add(new DetailsDto(merchantId, Id));
                CartDetails cart = new CartDetails(email, list);
                iAddToCartRepository.save(cart);
            }
        }
    }

    @Override
    public CartDetailsDto getCart(String email) {
        Optional<CartDetails> cartDetailsOpt = iAddToCartRepository.findById(email);
        return new CartDetailsDto(cartDetailsOpt);


    }



}

