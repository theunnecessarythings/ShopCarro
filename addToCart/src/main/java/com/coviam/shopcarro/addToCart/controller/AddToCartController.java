package com.coviam.shopcarro.addToCart.controller;

import com.coviam.shopcarro.addToCart.dto.CartDetailsDto;
import com.coviam.shopcarro.addToCart.services.IAddToCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 *
 * @author: sandeepgupta
 *
 * */

@RestController

public class AddToCartController {
    @Autowired
    private IAddToCartService iAddToCartService;

    /**
     *
     * localhost:8080/add-cart
     *
     * This will be adding the products in the cart DB, it's a get request and will be returning Boolean value
     * whether the product can be added to the cart or not.
     *
     * */

    @RequestMapping(value = "/add-cart", method = RequestMethod.GET)
    ResponseEntity<Boolean> addCart(@RequestParam String email,String merchantId,String id) {
        iAddToCartService.addToCart(email,merchantId,id);
        return new ResponseEntity<>(true, HttpStatus.CREATED);
    }


    /**
     *
     * localhost:8080/get-cart
     *
     * This function will be returing the cartDetailsDto which is called by the ORDER module to get all the products
     * for the particular user.
     *
     * CartDetailsDto is an object having email, and merchantId and ProductId (id - id where-ever is used is called upon as productId)
     *
     *
     * */

    @RequestMapping(value = "/get-cart", method = RequestMethod.GET)
    ResponseEntity<CartDetailsDto> get(@RequestParam String email) {
        System.out.println(email);
        CartDetailsDto cartDetailsDto = iAddToCartService.getCart(email);
        return new ResponseEntity<>(cartDetailsDto, HttpStatus.CREATED);
    }



    @RequestMapping(value = "/del-item",method = RequestMethod.GET)
    ResponseEntity<Boolean> delete(@RequestParam String email,@RequestParam String merchantId,@RequestParam String id ){

        iAddToCartService.delItem(email,merchantId,id);
        return new ResponseEntity<>(true, HttpStatus.CREATED);
    }







}
