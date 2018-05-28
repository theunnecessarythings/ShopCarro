package com.coviam.shopcarro.addToCart.controller;

import com.coviam.shopcarro.addToCart.CustomException;
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
    ResponseEntity<Boolean> addCart(@RequestParam String email,String merchantId,String id) throws CustomException {
        System.out.println(email + merchantId + id);
        if (email == null || merchantId==null||id==null )
        {
            //System.out.println("hey");
            throw new CustomException("false");
        } else {

           // System.out.println("hey");
            boolean create = iAddToCartService.addToCart(email, merchantId, id);
            System.out.println(create);
            return new ResponseEntity<>(create, HttpStatus.CREATED);
        }
    }


    /**
     *
     * localhost:8081/get-cart
     *
     * This function will be returing the cartDetailsDto which is called by the ORDER module to get all the products
     * for the particular user.
     *
     * CartDetailsDto is an object having email, and merchantId and ProductId (id - id where-ever is used is called upon as productId)
     *
     *
     * */


    @RequestMapping(value = "/get-cart", method = RequestMethod.GET)
    ResponseEntity<CartDetailsDto> get(@RequestParam String email) throws CustomException {
        System.out.println(email);
        if (email == null ) {
            throw new CustomException("false");
        }

        CartDetailsDto cartDetailsDto = iAddToCartService.getCart(email);
        return new ResponseEntity<>(cartDetailsDto, HttpStatus.CREATED);
    }

    /**
     * This function
     *
     */



    @RequestMapping(value = "/del-item",method = RequestMethod.GET)
    ResponseEntity<Boolean> delete(@RequestParam String email,@RequestParam String merchantId,@RequestParam String id ) throws CustomException {
       if (email == null || merchantId==null||id==null )
        {
            throw new CustomException("false");
        }
        iAddToCartService.delItem(email,merchantId,id);
        return new ResponseEntity<>(true, HttpStatus.CREATED);
    }








}
