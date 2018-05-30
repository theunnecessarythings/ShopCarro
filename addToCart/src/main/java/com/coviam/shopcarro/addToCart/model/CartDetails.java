package com.coviam.shopcarro.addToCart.model;

import com.coviam.shopcarro.addToCart.dto.Details;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.Objects;

@Document(collection=CartDetails.COLLECTION_NAME)
public class CartDetails {
    public static final String COLLECTION_NAME="cart";

    @Id
    private String email;

    private List<Details> details;

    public CartDetails() {
    }

    public CartDetails(String email) {
        this.email = email;
    }

    public CartDetails(String email, List<Details> details) {
        this.email = email;
        this.details = details;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Details> getDetails() {
        return details;
    }

    public void setDetails(List<Details> details) {
        this.details = details;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CartDetails that = (CartDetails) o;
        return Objects.equals(email, that.email) &&
                Objects.equals(details, that.details);
    }

    @Override
    public int hashCode() {

        return Objects.hash(email, details);
    }

    @Override
    public String toString() {
        return "CartDetails{" +
                "email='" + email + '\'' +
                ", details=" + details +
                '}';
    }
}

