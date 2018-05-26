package com.coviam.shopcarro.search.dto;

/**
 * @author sreerajr
 * @package com.coviam.shopcarro.search.dto
 * @project search
 */
public class ProductDto {
    private String id;
    private String productName;
    private String description;
    private Long price;
    private String merchantId;
    private String imgUrl;


    public ProductDto(String id, String productName, String description, Long price, String merchantId, String imgUrl) {
        this.id = id;
        this.productName = productName;
        this.description = description;
        this.price = price;
        this.merchantId = merchantId;
        this.imgUrl = imgUrl;
    }

    public ProductDto() {
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

    @Override
    public String toString() {
        return "ProductDto{" +
                "id='" + id + '\'' +
                ", productName='" + productName + '\'' +
                ", description='" + description + '\'' +
                ", price='" + price + '\'' +
                ", merchantId='" + merchantId + '\'' +
                ", imgUrl='" + imgUrl + '\'' +
                '}';
    }
}
