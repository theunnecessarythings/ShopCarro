package com.coviam.shopcarro.search.model;

import org.apache.solr.client.solrj.beans.Field;
import org.springframework.data.annotation.Id;
import org.springframework.data.solr.core.mapping.SolrDocument;

/**
 * @author sreerajr
 * @package com.coviam.shopcarro.search.model
 * @project search
 */
@SolrDocument(solrCoreName = "product")
public class Product {
    @Id
    @Field
    private String id;

    @Field
    private String productName;

    public Product(String id, String productName, String description, Long price, String merchantId, String imgUrl) {
        this.id = id;
        this.productName = productName;
        this.description = description;
        this.price = price;
        this.merchantId = merchantId;
        this.imgUrl = imgUrl;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    @Field
    private String description;

    @Field
    private Long price;

    @Field
    private String merchantId;

    @Field
    private String imgUrl;

    public Product() {
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

}
