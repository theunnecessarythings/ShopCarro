package com.coviam.shopcarro.order.model;

import java.util.Objects;

public class HistoryProducts {
    private String productId;
    private String merchantId;
    private String productName;

    public HistoryProducts() {
    }

    public HistoryProducts(String productId, String merchantId, String productName) {
        this.productId = productId;
        this.merchantId = merchantId;
        this.productName = productName;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HistoryProducts that = (HistoryProducts) o;
        return Objects.equals(productId, that.productId) &&
                Objects.equals(merchantId, that.merchantId) &&
                Objects.equals(productName, that.productName);
    }

    @Override
    public int hashCode() {

        return Objects.hash(productId, merchantId, productName);
    }

    @Override
    public String toString() {
        return "HistoryProducts{" +
                "productId='" + productId + '\'' +
                ", merchantId='" + merchantId + '\'' +
                ", productName='" + productName + '\'' +
                '}';
    }
}
