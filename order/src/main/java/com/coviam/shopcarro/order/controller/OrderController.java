package com.coviam.shopcarro.order.controller;

import com.coviam.shopcarro.order.details.Details;
import com.coviam.shopcarro.order.dto.OrderDto;
import com.coviam.shopcarro.order.services.IOrderservices;
import com.coviam.shopcarro.order.utility.GetCartClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
public class OrderController {

    @Autowired
    private IOrderservices iOrderservices;

    @RequestMapping(value = "/check-order",method = RequestMethod.GET)
    ResponseEntity< OrderDto > getProductsCanBePurchased(@RequestParam String email) throws IOException {
        System.out.println("Hello ");
        GetCartClient getCartClient = new GetCartClient();

        /**
         *
         *  To make a request to cart so as to get all the product details which are present in the cart for the current
         *  user
         *
         * */

        String urlGetCart = "http://10.177.2.64:8080/get-cart/?email="+email;
        RestTemplate restTemplate= new RestTemplate();
        OrderDto orderDto = restTemplate.getForObject(urlGetCart,OrderDto.class);

        /**
         *
         *  This will be returning the list of products which are to be purchased.
         *
         **/
        System.out.println(orderDto);

        List< Details > listOfProductOfPurchased = new ArrayList<>();
        listOfProductOfPurchased = iOrderservices.productBuy(orderDto);

        OrderDto orderDto1 = new OrderDto(email,listOfProductOfPurchased);
        return new ResponseEntity<>(orderDto1,HttpStatus.ACCEPTED);
    }

    /**
     *
     * This will be returning the list of products for a particular product which are being purchased by
     *  the user.
     *
     * */
    @RequestMapping(value = "/history",method = RequestMethod.GET)
    ResponseEntity<OrderDto> history(@RequestParam String email){
        OrderDto orderDto = new OrderDto();
        orderDto = iOrderservices.getHistory(email);
        return new ResponseEntity<>(orderDto,HttpStatus.ACCEPTED);
    }
}
