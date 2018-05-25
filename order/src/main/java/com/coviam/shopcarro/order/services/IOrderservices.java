package com.coviam.shopcarro.order.services;

import com.coviam.shopcarro.order.details.Details;

import java.util.List;

public interface IOrderservices {

    public List<Details> productBuy(String email);
}
