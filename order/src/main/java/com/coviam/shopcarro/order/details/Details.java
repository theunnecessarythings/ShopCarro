package com.coviam.shopcarro.order.details;

import java.util.Objects;

public class Details {

    private String id;
    private String merchantId;

    public Details() {
    }

    public Details(String Id, String merchantId) {
        this.id = Id;
        this.merchantId = merchantId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Details details = (Details) o;
        return Objects.equals(id, details.id) &&
                Objects.equals(merchantId, details.merchantId);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, merchantId);
    }

    @Override
    public String toString() {
        return "Details{" +
                "id='" + id + '\'' +
                ", merchantId='" + merchantId + '\'' +
                '}';
    }
}
