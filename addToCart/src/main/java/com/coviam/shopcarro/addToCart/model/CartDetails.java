package com.coviam.shopcarro.addToCart.model;

import com.coviam.shopcarro.addToCart.dto.DetailsDto;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Document(collection=CartDetails.COLLECTION_NAME)
public class CartDetails {
    public static final String COLLECTION_NAME="cartdetails";

    @Id
    private String email;

    private List<DetailsDto> detailsDtos;

    public CartDetails() {
    }

    public CartDetails(String email) {
        this.email = email;
    }

    public CartDetails(String email, List<DetailsDto> detailsDtos) {
        this.email = email;
        this.detailsDtos = detailsDtos;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<DetailsDto> getDetailsDtos() {
        return detailsDtos;
    }

    public void setDetailsDtos(List<DetailsDto> detailsDtos) {
        this.detailsDtos = detailsDtos;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CartDetails that = (CartDetails) o;
        return Objects.equals(email, that.email) &&
                Objects.equals(detailsDtos, that.detailsDtos);
    }

    @Override
    public int hashCode() {

        return Objects.hash(email, detailsDtos);
    }

    @Override
    public String toString() {
        return "CartDetails{" +
                "email='" + email + '\'' +
                ", detailsDtos=" + detailsDtos +
                '}';
    }
}

