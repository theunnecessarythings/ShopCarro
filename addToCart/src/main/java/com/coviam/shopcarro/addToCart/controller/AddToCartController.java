package com.coviam.shopcarro.addToCart.controller;

import com.coviam.shopcarro.addToCart.dto.CartDetailsDto;
import com.coviam.shopcarro.addToCart.dto.DetailsDto;
import com.coviam.shopcarro.addToCart.services.IAddToCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController

public class AddToCartController {
    @Autowired
    private IAddToCartService iAddToCartService;
    @RequestMapping(value = "/add-cart", method = RequestMethod.GET)
    ResponseEntity<Boolean> addCart(@RequestParam String email,String merchantId,String Id) {

        iAddToCartService.addToCart(email,merchantId,Id);
//
        return new ResponseEntity<>(true, HttpStatus.CREATED);

    }

    @RequestMapping(value = "/get-cart", method = RequestMethod.POST)
    ResponseEntity<CartDetailsDto> add(@RequestParam String email) {

        CartDetailsDto cartDetailsDto = iAddToCartService.getCart(email);
//
        return new ResponseEntity<>(cartDetailsDto, HttpStatus.CREATED);

    }







}
