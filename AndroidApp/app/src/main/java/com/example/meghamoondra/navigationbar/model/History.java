package com.example.meghamoondra.navigationbar.model;

public class History {
    private String productId;
    private String merchantId;
    private String productName;

    public History() {
    }

    public History(String productId, String merchantId, String productName) {
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
    public String toString() {
        return "History{" +
                "productId='" + productId + '\'' +
                ", merchantId='" + merchantId + '\'' +
                ", productName='" + productName + '\'' +
                '}';
    }
}
