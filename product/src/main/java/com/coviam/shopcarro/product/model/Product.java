package com.coviam.shopcarro.product.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Objects;

/**
 * @author sandeepgupta007
 * @package com.coviam.shopcarro.product.model
 * @project product
 */

@Document(collection = Product.COLLECTION_NAME)

public class Product {
    public static final String COLLECTION_NAME="product";

    @Id
    private String id;
    private String usp;
    private String description;
    private String attribute;
    private Long price;
    private String merchantId;
    private String imgUrl;

    public Product() {
    }

    public Product(String id, String usp, String description, String attribute, Long price, String merchantId, String imgUrl) {
        this.id = id;
        this.usp = usp;
        this.description = description;
        this.attribute = attribute;
        this.price = price;
        this.merchantId = merchantId;
        this.imgUrl = imgUrl;
    }


    public static String getCollectionName() {
        return COLLECTION_NAME;
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


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(id, product.id) &&
                Objects.equals(usp, product.usp) &&
                Objects.equals(description, product.description) &&
                Objects.equals(attribute, product.attribute) &&
                Objects.equals(price, product.price) &&
                Objects.equals(merchantId, product.merchantId) &&
                Objects.equals(imgUrl, product.imgUrl);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, usp, description, attribute, price, merchantId, imgUrl);
    }

    @Override
    public String toString() {
        return "Product{" +
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
