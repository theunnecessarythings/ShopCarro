package com.example.meghamoondra.navigationbar.model;

import java.util.List;

public class CartProduct {
    private String email;
    private List<CartDetails> details;

    public CartProduct(String email, List<CartDetails> details) {
        this.email = email;
        this.details = details;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<CartDetails> getDetails() {
        return details;
    }

    public void setDetails(List<CartDetails> details) {
        this.details = details;
    }

    @Override
    public String toString() {
        return "CartProduct{" +
                "email='" + email + '\'' +
                ", details=" + details +
                '}';
    }
}

