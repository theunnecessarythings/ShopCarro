package com.coviam.shopcarro.order.services;

import com.coviam.shopcarro.order.details.Details;
import com.coviam.shopcarro.order.dto.OrderDto;
import com.coviam.shopcarro.order.model.Order;
import com.coviam.shopcarro.order.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author: sandeepgupta
 *
 * */

@Service
public class OrderServices implements IOrderservices {

    @Autowired
    private OrderRepository orderRepository;

    public List<Details> productBuy(OrderDto orderDto){
        /**
         *
         *  List of orders that are present in the cart are in the OrderDto for that particular user.
         *
         * */

        List< Details > listOfProductsPurchased = new ArrayList<>();

        for (Details details:orderDto.getDetails()){

            /**
             *
             * get the stocks for a particular a particular product and merchant Id.
             *
             * */
            String urlGetAvailable = "http://10.177.1.239:8080/get-available/?merchantId="+details.getMerchantId()+"&productId="+details.getId();
            RestTemplate restTemplate= new RestTemplate();
            Boolean availablity;
            availablity = restTemplate.getForObject(urlGetAvailable,Boolean.class);

            if(availablity){
                listOfProductsPurchased.add(details);
                /**
                 *  link to decrement the stock.
                 * */
                String decrementStock = "http://10.177.1.239:8080/decrement-stock/?merchantId="+details.getMerchantId()+"&productId="+details.getId();
                RestTemplate restTemplate1= new RestTemplate();
                Boolean decremented;
                decremented = restTemplate1.getForObject(decrementStock,Boolean.class);
            }
        }
        Order order = new Order(orderDto.getEmail(),listOfProductsPurchased);
        orderRepository.save(order);
        return listOfProductsPurchased;
    }

    /**
     *
     *  Will be returning the history if present otherwise this will be returning the null value.
     *
     * */
    public OrderDto getHistory(String email){
        if(!orderRepository.existsById(email)){
            return null;
        }

        Optional<Order> order = Optional.of(new Order());
        order = orderRepository.findById(email);

        OrderDto orderDto = new OrderDto();
        orderDto.setEmail(order.get().getEmail());
        orderDto.setDetails(order.get().getDetails());
        return orderDto;
    }
}
