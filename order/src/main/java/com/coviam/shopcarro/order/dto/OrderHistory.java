package com.coviam.shopcarro.order.dto;

import com.coviam.shopcarro.order.details.OrderDetails;

import java.util.List;

public class OrderHistory {

    private String email;
    private List<OrderDetails> details;

    public OrderHistory() {
    }

    public OrderHistory(String email, List<OrderDetails> details) {
        this.email = email;
        this.details = details;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<OrderDetails> getDetails() {
        return details;
    }

    public void setDetails(List<OrderDetails> details) {
        this.details = details;
    }

    @Override
    public String toString() {
        return "OrderHistory{" +
                "email='" + email + '\'' +
                ", details=" + details +
                '}';
    }
}
