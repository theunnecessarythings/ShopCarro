package com.coviam.shopcarro.addToCart.dto;

import javax.validation.constraints.NotNull;
import java.util.Objects;

public class Details {

    @NotNull
    private String merchantId;
    @NotNull
    private String id;
    private String quantity;
    private String merchantName;
    private String productName;
    private String price;
    private String imageUrl;
    private String date;

    public Details(@NotNull String merchantId, @NotNull String id, String quantity, String merchantName, String productName, String price, String imageUrl, String date) {
        this.merchantId = merchantId;
        this.id = id;
        this.quantity = quantity;
        this.merchantName = merchantName;
        this.productName = productName;
        this.price = price;
        this.imageUrl = imageUrl;
        this.date = date;
    }

    public String getDate() {
        return date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Details details = (Details) o;
        return Objects.equals(merchantId, details.merchantId) &&
                Objects.equals(id, details.id) &&
                Objects.equals(quantity, details.quantity) &&
                Objects.equals(merchantName, details.merchantName) &&
                Objects.equals(productName, details.productName) &&
                Objects.equals(price, details.price) &&
                Objects.equals(imageUrl, details.imageUrl) &&
                Objects.equals(date, details.date);
    }

    @Override
    public int hashCode() {

        return Objects.hash(merchantId, id, quantity, merchantName, productName, price, imageUrl, date);
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Details() {

    }

    @Override
    public String toString() {
        return "Details{" +
                "merchantId='" + merchantId + '\'' +
                ", id='" + id + '\'' +
                ", quantity='" + quantity + '\'' +
                ", merchantName='" + merchantName + '\'' +
                ", productName='" + productName + '\'' +
                ", price='" + price + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", date='" + date + '\'' +
                '}';
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

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
