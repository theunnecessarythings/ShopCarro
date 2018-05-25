package com.coviam.shopcarro.addToCart.dto;

import com.coviam.shopcarro.addToCart.model.CartDetails;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class CartDetailsDto {

    private String email;
    private List<Details> details;

    public CartDetailsDto(Optional<CartDetails> cartDetailsOpt) {
        this.email = cartDetailsOpt.get().getEmail();
        this.details = cartDetailsOpt.get().getDetails();
    }

    public CartDetailsDto() {
    }

    public CartDetailsDto(@NotNull  String email, List<Details> details) {
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
        CartDetailsDto that = (CartDetailsDto) o;
        return Objects.equals(email, that.email) &&
                Objects.equals(details, that.details);
    }

    @Override
    public int hashCode() {

        return Objects.hash(email, details);
    }

    @Override
    public String toString() {
        return "CartDetailsDto{" +
                "email='" + email + '\'' +
                ", details=" + details +
                '}';
    }
}
