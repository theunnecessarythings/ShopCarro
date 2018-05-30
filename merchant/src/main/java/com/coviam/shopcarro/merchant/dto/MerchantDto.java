package com.coviam.shopcarro.merchant.dto;

/**
 * @author sreerajr
 * @package com.coviam.shopcarro.merchant.dto
 * @project merchant
 */
public class MerchantDto {
    private String merchantId;
    private String merchantName;

    /**
     * Find a way to get this value directly from the database itself
     */
    private Long noOfProductsOfferedToSell;
    private Long noOfProductsSold;
    private Double merchantRating;


    public MerchantDto(String merchantId, String merchantName, Long noOfProductsOfferedToSell, Long noOfProductsSold, Double merchantRating) {
        this.merchantId = merchantId;
        this.merchantName = merchantName;
        this.noOfProductsOfferedToSell = Long.valueOf(0);
        this.noOfProductsSold = Long.valueOf(0);
        this.merchantRating = merchantRating;
    }

    public MerchantDto() {
    }

    @Override
    public String toString() {
        return "MerchantDto{" +
                "merchantId='" + merchantId + '\'' +
                ", merchantName='" + merchantName + '\'' +
                ", noOfProductsOfferedToSell=" + noOfProductsOfferedToSell +
                ", noOfProductsSold=" + noOfProductsSold +
                ", merchantRating=" + merchantRating +
                '}';
    }

    public Double getMerchantRating() {
        return merchantRating;
    }

    public void setMerchantRating(Double merchantRating) {
        this.merchantRating = merchantRating;
    }

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    public Long getNoOfProductsOfferedToSell() {
        return noOfProductsOfferedToSell;
    }

    public void setNoOfProductsOfferedToSell(Long noOfProductsOfferedToSell) {
        this.noOfProductsOfferedToSell = noOfProductsOfferedToSell;
    }

    public Long getNoOfProductsSold() {
        return noOfProductsSold;
    }

    public void setNoOfProductsSold(Long noOfProductsSold) {
        this.noOfProductsSold = noOfProductsSold;
    }
}
