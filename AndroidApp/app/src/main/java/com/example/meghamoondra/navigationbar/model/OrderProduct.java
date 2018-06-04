package com.example.meghamoondra.navigationbar.model;

import java.util.List;

public class OrderProduct {
    private String email;
    private List<OrderDetails> details;
    public OrderProduct() {
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    @Override
    public String toString() {
        return "OrderProduct{" +
                "email='" + email + '\'' +
                ", details=" + details +
                '}';
    }
    public OrderProduct(String email, List<OrderDetails> details) {
        this.email = email;
        this.details = details;
    }
    public List<OrderDetails> getDetails() {
        return details;
    }
    public void setDetails(List<OrderDetails> details) {
        this.details = details;
    }
}
