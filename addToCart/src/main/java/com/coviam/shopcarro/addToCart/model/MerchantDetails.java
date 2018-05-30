package com.coviam.shopcarro.addToCart.model;

import java.util.Objects;

public class MerchantDetails {
    private String merchantName;
    private String price;

    public MerchantDetails() {
    }

    public MerchantDetails(String merchantName, String price) {
        this.merchantName = merchantName;
        this.price = price;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MerchantDetails that = (MerchantDetails) o;
        return Objects.equals(merchantName, that.merchantName) &&
                Objects.equals(price, that.price);
    }

    @Override
    public int hashCode() {

        return Objects.hash(merchantName, price);
    }

    @Override
    public String toString() {
        return "MerchantDetails{" +
                "merchantName='" + merchantName + '\'' +
                ", price='" + price + '\'' +
                '}';
    }
}
