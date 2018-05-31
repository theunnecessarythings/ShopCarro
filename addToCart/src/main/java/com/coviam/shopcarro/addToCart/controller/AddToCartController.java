package com.coviam.shopcarro.addToCart.controller;

import com.coviam.shopcarro.addToCart.CustomException;
import com.coviam.shopcarro.addToCart.dto.CartDetailsDto;
import com.coviam.shopcarro.addToCart.services.IAddToCartService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.logging.Logger;

/**
 *
 * @author: sandeepgupta
 *
 * */

@RestController

public class AddToCartController {
    //logger.addHandler(new ConsoleHandler());
    @Autowired
    private IAddToCartService iAddToCartService;

    /**
     * localhost:8080/add-cart
     * <p>
     * This will be adding the products in the cart DB, it's a get request and will be returning Boolean value
     * whether the product can be added to the cart or not.
     */

    @RequestMapping(value = "/add-cart", method = RequestMethod.GET)
    ResponseEntity<Boolean> addCart(@RequestParam String email, @RequestParam String merchantId, @RequestParam String id, @RequestParam String quantity) throws CustomException {
       // System.out.println(email + merchantId + id+quantity);

        if (email == null || merchantId == null || id == null) {
            //System.out.println("hey");
            throw new CustomException("false");
        } else {
            Boolean create = iAddToCartService.addToCart(email, merchantId, id, quantity);
            System.out.println(create);
            return new ResponseEntity<>(create, HttpStatus.CREATED);
        }
    }


    /**
     * localhost:8081/get-cart
     * <p>
     * This function will be returing the cartDetailsDto which is called by the ORDER module to get all the products
     * for the particular user.
     * <p>
     * CartDetailsDto is an object having email, and merchantId and ProductId (id - id where-ever is used is called upon as productId)
     */


    @RequestMapping(value = "/get-cart", method = RequestMethod.GET)
    ResponseEntity<CartDetailsDto> get(@RequestParam String email) throws CustomException {

        System.out.println(email);
        if (email.length() == 0) {
            throw new CustomException("false");
        } else{

            CartDetailsDto cartDetailsDto = iAddToCartService.getCart(email);
//
        return new ResponseEntity<>(cartDetailsDto, HttpStatus.CREATED);
    }

}
  @RequestMapping(value = "/get-cart-all",method = RequestMethod.GET)
  ResponseEntity<CartDetailsDto> getall(@RequestParam  String email) throws CustomException {
      if (email.length() == 0) {
          System.out.println("null");
          throw new CustomException("false");
      } else {

          CartDetailsDto cartDetailsDto = iAddToCartService.getCartAll(email);
          System.out.println(cartDetailsDto.toString());
          return new ResponseEntity<>(cartDetailsDto, HttpStatus.CREATED);

      }
  }
    /**
     * This function clears the product from the users cart
     *
     */



    @RequestMapping(value = "/del-item",method = RequestMethod.GET)
    ResponseEntity<Boolean> delete(@RequestParam String email,@RequestParam String merchantId,@RequestParam String id,@RequestParam String quantity ) throws CustomException {
       if (email == null || merchantId==null||id==null )
        {
            throw new CustomException("false");
        }
        iAddToCartService.delItem(email,merchantId,id,quantity);


        return new ResponseEntity<>(true, HttpStatus.CREATED);
    }

    /**
     *
     * clears all the products from cart the order been placed
     *
     *
     * @param email
     * @return
     * @throws CustomException
     */

    @RequestMapping(value = "/del-all", method = RequestMethod.GET)
    ResponseEntity<Boolean> delAll(@RequestParam String email) throws CustomException {
        System.out.println(email);
        if (email.length() == 0) {
            // System.out.println("null");
            throw new CustomException("false");
        } else {

            boolean create = iAddToCartService.delCart(email);
            System.out.println(email);
            return new ResponseEntity<>(create, HttpStatus.CREATED);
        }


    }


}

