package com.coviam.shopcarro.order.controller;

import com.coviam.shopcarro.order.details.OrderDetails;
import com.coviam.shopcarro.order.dto.OrderDto;
import com.coviam.shopcarro.order.dto.OrderHistory;
import com.coviam.shopcarro.order.model.HistoryProducts;
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
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;


/**
 * @author: sandeepgupta
 *
 * End points:
 *  /check-order
 *  /get-history
 *  /buy-product-by-id
 * */

@RestController
public class OrderController {

    private static final String CHECKOUT = "/check-order";
    private static final String HISTORY = "/get-history";
    private static final String BUY_SINGLE_PRODUCT = "/buy-product-by-id";

    @Autowired
    private IOrderservices iOrderservices;

    /**
     *  This end-point will be buying all the products which are in mycart of that particular user. checking whether the
     *  product is available in the stock, decrementing the stock, storing in the database and sending the mail.
     * */

    @Value("${urlGetOfCart}") private String urlOfGetCart;
    private static final Logger LOGGER = Logger.getLogger( OrderController.class.getName() );

    @RequestMapping(value = CHECKOUT ,method = RequestMethod.GET)
    ResponseEntity<OrderHistory> getProductsCanBePurchased(@RequestParam String email) throws IOException {
        LOGGER.info("Inside check-order for placing the order");
        /**
         *  To make a request to cart so as to get all the product details which are present in the cart for the current
         *  user
         * */
        String urlGetCart = urlOfGetCart + email;
        RestTemplate restTemplate= new RestTemplate();
        OrderDto orderDto = restTemplate.getForObject(urlGetCart,OrderDto.class);
        /**
         *  This will be returning the list of products which are to be purchased.
         **/
        if(!orderDto.equals(null))
            LOGGER.info(orderDto.toString());

        List<OrderDetails> listOfProductOfPurchased;
        listOfProductOfPurchased = iOrderservices.productBuy(orderDto);
        OrderHistory orderHistory = new OrderHistory(email,listOfProductOfPurchased);
        return new ResponseEntity<>(orderHistory,HttpStatus.OK);
    }

    /**
     *  This will be returning the list of products for a particular product which are being purchased by
     *  the user in past.
     * */

    @RequestMapping(value = HISTORY,method = RequestMethod.GET)
    ResponseEntity<OrderHistory> gethistory(@RequestParam String email) throws ParseException {
        LOGGER.info("Inside get history");
        List<OrderDetails> historyProducts;
        historyProducts = iOrderservices.getHistoryOfUser(email);
        List<OrderDetails> historyProducts1 = new ArrayList<>();
        /**
         * Reversing the list to have history in order.
         * */
        for(int index = historyProducts.size()-1;index>=0;index--){
            historyProducts1.add(historyProducts.get(index));
        }
        OrderHistory orderHistory = new OrderHistory(email,historyProducts1);
        LOGGER.info(orderHistory.toString());
        return new ResponseEntity<>(orderHistory,HttpStatus.OK);
    }

    /**
     *  if only products is to be purchased then use this end-point
     * */

    @RequestMapping(value = BUY_SINGLE_PRODUCT ,method = RequestMethod.GET)
    ResponseEntity < Long > buyProductById(@RequestParam String email,@RequestParam String productId,@RequestParam String merchantId,@RequestParam String quantity,@RequestParam String productName){
        LOGGER.info("Inside get Buy product by Id");
        return new ResponseEntity<>(iOrderservices.purchaseProduct(email,productId,merchantId,quantity,productName),HttpStatus.OK);
            //return null;
    }
}
