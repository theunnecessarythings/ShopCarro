package com.coviam.shopcarro.order.services;

import com.coviam.shopcarro.order.details.OrderDetails;
import com.coviam.shopcarro.order.dto.OrderDto;

import java.text.ParseException;
import java.util.List;

public interface IOrderservices {

    public List<OrderDetails> productBuy(OrderDto orderDto);
    public List<OrderDetails> getHistoryOfUser(String email) throws ParseException;
    public Long purchaseProduct(String email, String productId, String merchantId, String quantity, String productName);
}
