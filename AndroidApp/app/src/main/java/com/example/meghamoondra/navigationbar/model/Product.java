package com.example.meghamoondra.navigationbar.model;

import java.io.Serializable;
import java.util.List;

public class Product implements Serializable {

    private String id;
    private String productName;
    private String description;
    private String attribute;
    private Long price;
    private List<String> merchantId;
    private String imgUrl;
    private int drawableId;


    public Product() {
    }

    public Product(String id, String productName, String description, String attribute, Long price, List<String> merchantId, String imgUrl, int drawableId) {
        this.id = id;
        this.productName = productName;
        this.description = description;
        this.attribute = attribute;
        this.price = price;
        this.merchantId = merchantId;
        this.imgUrl = imgUrl;
        this.drawableId = drawableId;
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

    public String getAttribute() {
        return attribute;
    }

    public void setAttribute(String attribute) {
        this.attribute = attribute;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public List<String> getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(List<String> merchantId) {
        this.merchantId = merchantId;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public int getDrawableId() {
        return drawableId;
    }

    public void setDrawableId(int drawableId) {
        this.drawableId = drawableId;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id='" + id + '\'' +
                ", productName='" + productName + '\'' +
                ", description='" + description + '\'' +
                ", attribute='" + attribute + '\'' +
                ", price=" + price +
                ", merchantId=" + merchantId +
                ", imgUrl='" + imgUrl + '\'' +
                ", drawableId=" + drawableId +
                '}';
    }
}
