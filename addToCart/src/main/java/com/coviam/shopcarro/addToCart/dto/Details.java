package com.coviam.shopcarro.addToCart.dto;

import javax.validation.constraints.NotNull;
import java.util.Objects;

public class Details {

    @NotNull
    private String merchantId;
    @NotNull
    private String id;

    public Details() {
    }

    public Details( String merchantId, String id) {
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
        Details details = (Details) o;
        return Objects.equals(merchantId, details.merchantId) &&
                Objects.equals(id, details.id);
    }

    @Override
    public int hashCode() {

        return Objects.hash(merchantId, id);
    }

    @Override
    public String toString() {
        return "Details{" +
                "merchantId='" + merchantId + '\'' +
                ", id='" + id + '\'' +
                '}';
    }
}
