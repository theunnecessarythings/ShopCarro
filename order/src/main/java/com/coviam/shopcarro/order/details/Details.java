package com.coviam.shopcarro.order.details;

import java.util.Objects;

public class Details {

    private String id;
    private String merchantId;
    private String quantity;

    public Details() {
    }

    public Details(String id, String merchantId, String quantity) {
        this.id = id;
        this.merchantId = merchantId;
        this.quantity = quantity;
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

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "Details{" +
                "id='" + id + '\'' +
                ", merchantId='" + merchantId + '\'' +
                ", quantity='" + quantity + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Details details = (Details) o;
        return Objects.equals(id, details.id) &&
                Objects.equals(merchantId, details.merchantId) &&
                Objects.equals(quantity, details.quantity);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, merchantId, quantity);
    }
}
