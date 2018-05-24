package com.coviam.shopcarro.merchant.dto;

/**
 * @author sreerajr
 * @package com.coviam.shopcarro.merchant.dto
 * @project merchant
 */
public class MerchantDto {
    private String merchantId;
    private String merchantName;
    private Long noOfProductsOfferedToSell;
    private Long noOfProductsSold;

    public MerchantDto(String merchantId, String merchantName, Long noOfProductsOfferedToSell, Long noOfProductsSold) {
        this.merchantId = merchantId;
        this.merchantName = merchantName;
        this.noOfProductsOfferedToSell = noOfProductsOfferedToSell;
        this.noOfProductsSold = noOfProductsSold;
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
                '}';
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
