package com.coviam.shopcarro.order.model;


import com.coviam.shopcarro.order.details.OrderDetails;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.Objects;

@Document(collection = Order.COLLECTION_NAME)
public class Order {
    public static final String COLLECTION_NAME="order";

    @Id
    private String email;

    private List<OrderDetails> details;

    public Order() {
    }

    public Order(String email, List<OrderDetails> details) {
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
        return "Order{" +
                "email='" + email + '\'' +
                ", details=" + details +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return Objects.equals(email, order.email) &&
                Objects.equals(details, order.details);
    }

    @Override
    public int hashCode() {

        return Objects.hash(email, details);
    }
}
