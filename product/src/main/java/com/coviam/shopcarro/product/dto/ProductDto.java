package com.coviam.shopcarro.product.dto;

import com.coviam.shopcarro.product.model.Product;

import java.util.Objects;

/**
 * @author sandeepgupta
 * @package com.coviam.shopcarro.product.dto
 * @project product
 */

public class ProductDto {

    /**
    *
     *  Product details in the
    * */
    private String id;
    private String usp;
    private String description;
    private String attribute;
    private Long price;
    private String merchantId;
    private String imgUrl;

    public ProductDto() {

    }


    public ProductDto(Product product){
        this.id = product.getId();
        this.usp = product.getUsp();
        this.description = product.getDescription();
        this.attribute = product.getAttribute();
        this.price = product.getPrice();
        this.merchantId = product.getMerchantId();
        this.imgUrl = product.getImgUrl();
    }

    public String getUsp() {
        return usp;
    }

    public ProductDto(String id, String usp, String description, String attribute, Long price, String merchantId, String imgUrl) {
        this.id = id;
        this.usp = usp;
        this.description = description;
        this.attribute = attribute;
        this.price = price;
        this.merchantId = merchantId;
        this.imgUrl = imgUrl;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductDto that = (ProductDto) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(usp, that.usp) &&
                Objects.equals(description, that.description) &&
                Objects.equals(attribute, that.attribute) &&
                Objects.equals(price, that.price) &&
                Objects.equals(merchantId, that.merchantId) &&
                Objects.equals(imgUrl, that.imgUrl);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, usp, description, attribute, price, merchantId, imgUrl);
    }

    @Override
    public String toString() {
        return "ProductDto{" +
                "id='" + id + '\'' +
                ", usp='" + usp + '\'' +
                ", description='" + description + '\'' +
                ", attribute='" + attribute + '\'' +
                ", price=" + price +
                ", merchantId='" + merchantId + '\'' +
                ", imgUrl='" + imgUrl + '\'' +
                '}';
    }
}
