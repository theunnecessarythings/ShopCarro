package com.coviam.shopcarro.order.controller;

import com.coviam.shopcarro.order.details.OrderDetails;
import com.coviam.shopcarro.order.dto.OrderDto;
import com.coviam.shopcarro.order.dto.OrderHistory;
import com.coviam.shopcarro.order.model.HistoryProducts;
import com.coviam.shopcarro.order.services.IOrderservices;
import com.coviam.shopcarro.order.utility.GetCartClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;


/**
 * @author: sandeepgupta
 *
 * End points:
 *  /check-order
 *  /get-order
 *  /get-history
 *  /buy-product-by-id
 *
 * */

@RestController
public class OrderController {

    @Autowired
    private IOrderservices iOrderservices;

    /**
     *  This end-point will be buying all the products which are in mycart of that particular user. checking whether the
     *  product is available in the stock, decrementing the stock, storing in the database and sending the mail.
     * */

    @RequestMapping(value = "/check-order",method = RequestMethod.GET)
    ResponseEntity<OrderHistory> getProductsCanBePurchased(@RequestParam String email) throws IOException {
        System.out.println("Inside check-order for placing the order");
        // GetCartClient getCartClient = new GetCartClient();

        /**
         *  To make a request to cart so as to get all the product details which are present in the cart for the current
         *  user
         * */
        String urlGetCart = "http://10.177.1.101:8081/get-cart/?email=" + email;
        System.out.println(urlGetCart);
        RestTemplate restTemplate= new RestTemplate();
        OrderDto orderDto = restTemplate.getForObject(urlGetCart,OrderDto.class);
        // System.out.println(orderDto);
        /**
         *  This will be returning the list of products which are to be purchased.
         **/
        System.out.println(orderDto);

        List<OrderDetails> listOfProductOfPurchased = new ArrayList<>();
        listOfProductOfPurchased = iOrderservices.productBuy(orderDto);

        OrderHistory orderHistory = new OrderHistory(email,listOfProductOfPurchased);
        return new ResponseEntity<>(orderHistory,HttpStatus.ACCEPTED);
    }

    /**
     *  This will be returning the list of products for a particular product which are being purchased by
     *  the user in past.
     * */

    @RequestMapping(value = "/get-history",method = RequestMethod.GET)
    ResponseEntity<OrderHistory> gethistory(@RequestParam String email) throws ParseException {
        System.out.println("Inside get history");
        List<OrderDetails> historyProducts = new ArrayList<>();
        historyProducts = iOrderservices.getHistoryOfUser(email);
        OrderHistory orderHistory = new OrderHistory(email,historyProducts);
        System.out.println(orderHistory);
        return new ResponseEntity<>(orderHistory,HttpStatus.ACCEPTED);
    }

    /**
     *  if only products is to be purchased then use this end-point
     * */

    @RequestMapping(value = "/buy-product-by-id",method = RequestMethod.GET)
    ResponseEntity < Long > buyProductById(@RequestParam String email,@RequestParam String productId,@RequestParam String merchantId,@RequestParam String quantity,@RequestParam String productName){
        System.out.println("Inside get Buy product by Id");
        return new ResponseEntity<>(iOrderservices.purchaseProduct(email,productId,merchantId,quantity,productName),HttpStatus.ACCEPTED);
            //return null;
    }
}
