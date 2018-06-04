package com.example.meghamoondra.navigationbar.model;

import java.util.List;

public class SearchProduct {
    private String id;
    private String productName;
    private String description;
    private Long price;
    private List<String> merchantId;
    private String imgUrl;
    private String attribute;


    public SearchProduct() {
    }

    public SearchProduct(String id, String productName, String description, Long price, List<String> merchantId, String imgUrl, String attribute) {
        this.id = id;
        this.productName = productName;
        this.description = description;
        this.price = price;
        this.merchantId = merchantId;
        this.imgUrl = imgUrl;
        this.attribute = attribute;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }


    @Override
    public String toString() {
        return "SearchProduct{" +
                "id='" + id + '\'' +
                ", productName='" + productName + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", merchantId=" + merchantId +
                ", imgUrl='" + imgUrl + '\'' +
                ", attribute='" + attribute + '\'' +
                '}';
    }

    public List<String> getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(List<String> merchantId) {
        this.merchantId = merchantId;
    }

    public String getAttribute() {
        return attribute;
    }

    public void setAttribute(String attribute) {
        this.attribute = attribute;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

}