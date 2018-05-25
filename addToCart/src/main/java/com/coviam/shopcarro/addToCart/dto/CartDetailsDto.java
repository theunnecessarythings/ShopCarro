package com.coviam.shopcarro.addToCart.dto;

import com.coviam.shopcarro.addToCart.model.CartDetails;

import javax.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class CartDetailsDto {

    private String email;
    private List<DetailsDto> detailsDtos;

    public CartDetailsDto(Optional<CartDetails> cartDetailsOpt) {
    }

    public CartDetailsDto(@NotNull String email, List<DetailsDto> detailsDtos) {
        this.email = email;
        this.detailsDtos = detailsDtos;
    }


}
