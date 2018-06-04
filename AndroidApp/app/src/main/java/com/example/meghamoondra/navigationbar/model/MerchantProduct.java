package com.example.meghamoondra.navigationbar.model;

public class MerchantProduct {

    private String id;
    private String usp;
    private String description;
    private String attribute;
    private Long price;
    private String merchantId;
    private String imgUrl;
    private int drawableId;

    public MerchantProduct(String id, String usp, String description, String attribute, Long price, String merchantId, int drawableId) {
        this.id = id;
        this.usp = usp;
        this.description = description;
        this.attribute = attribute;
        this.price = price;
        this.merchantId = merchantId;
        this.drawableId = drawableId;
    }



    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsp() {
        return usp;
    }

    public void setUsp(String usp) {
        this.usp = usp;
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

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
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
}
