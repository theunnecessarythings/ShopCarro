package com.coviam.shopcarro.addToCart.dto;

import javax.validation.constraints.NotNull;
import java.util.Objects;

public class DetailsDto {

    @NotNull
    private String merchantId;
    @NotNull
    private String id;

    public DetailsDto() {
    }

    public DetailsDto(@NotNull String merchantId, @NotNull String id) {
        this.merchantId = merchantId;
        this.id = id;
    }

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DetailsDto detailsDto = (DetailsDto) o;
        return Objects.equals(merchantId, detailsDto.merchantId) &&
                Objects.equals(id, detailsDto.id);
    }

    @Override
    public int hashCode() {

        return Objects.hash(merchantId, id);
    }

    @Override
    public String toString() {
        return "DetailsDto{" +
                "merchantId='" + merchantId + '\'' +
                ", id='" + id + '\'' +
                '}';
    }
}
