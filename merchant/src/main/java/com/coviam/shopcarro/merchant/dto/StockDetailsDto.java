package com.coviam.shopcarro.merchant.dto;

/**
 * @author sreerajr
 * @package com.coviam.shopcarro.merchant.dto
 * @project merchant
 */
public class StockDetailsDto {
    private String productId;
    private String merchantId;
    private Long productPrice;
    private Double rating;
    private String merchantName;
    private Long noOfItems;


    public StockDetailsDto(String productId, String merchantId, Long productPrice, Double rating, String merchantName, Long noOfItems) {
        this.productId = productId;
        this.merchantId = merchantId;
        this.productPrice = productPrice;
        this.rating = rating;
        this.merchantName = merchantName;
        this.noOfItems = noOfItems;
    }


    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    public StockDetailsDto() {
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

    public Long getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(Long productPrice) {
        this.productPrice = productPrice;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public Long getNoOfItems() {
        return noOfItems;
    }

    public void setNoOfItems(Long noOfItems) {
        this.noOfItems = noOfItems;
    }


    @Override
    public String toString() {
        return "StockDetailsDto{" +
                "productId='" + productId + '\'' +
                ", merchantId='" + merchantId + '\'' +
                ", productPrice=" + productPrice +
                ", rating=" + rating +
                ", merchantName='" + merchantName + '\'' +
                ", noOfItems=" + noOfItems +
                '}';
    }
}
