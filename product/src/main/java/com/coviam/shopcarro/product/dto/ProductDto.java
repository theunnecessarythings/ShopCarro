package com.coviam.shopcarro.product.dto;

import com.coviam.shopcarro.product.model.Product;

import java.util.List;
import java.util.Objects;

/**
 * @author sandeepgupta
 * @package com.coviam.shopcarro.product.dto
 * @project product
 */

public class ProductDto {

    /**
     *  Product details
     * */
    private String id;
    private String productName;
    private String description;
    private String attribute;
    private Long price;
    private List<String> merchantId;
    private String imgUrl;

    public ProductDto() {

    }

    public ProductDto(String id, String productName, String description, String attribute, Long price, List<String> merchantId, String imgUrl) {
        this.id = id;
        this.productName = productName;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductDto that = (ProductDto) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(productName, that.productName) &&
                Objects.equals(description, that.description) &&
                Objects.equals(attribute, that.attribute) &&
                Objects.equals(price, that.price) &&
                Objects.equals(merchantId, that.merchantId) &&
                Objects.equals(imgUrl, that.imgUrl);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, productName, description, attribute, price, merchantId, imgUrl);
    }

    @Override
    public String toString() {
        return "ProductDto{" +
                "id='" + id + '\'' +
                ", productName='" + productName + '\'' +
                ", description='" + description + '\'' +
                ", attribute='" + attribute + '\'' +
                ", price=" + price +
                ", merchantId=" + merchantId +
                ", imgUrl='" + imgUrl + '\'' +
                '}';
    }
}
