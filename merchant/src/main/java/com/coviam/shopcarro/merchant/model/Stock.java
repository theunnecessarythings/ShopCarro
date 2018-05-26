package com.coviam.shopcarro.merchant.model;

import com.coviam.shopcarro.merchant.model.key.StockId;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 * @author sreerajr
 * @package com.coviam.shopcarro.merchant.model
 * @project merchant
 */

@Entity
@Table(name = "stock_details")
public class Stock implements Comparable{

    @NotNull
    @EmbeddedId
    private StockId id; // merchantId and productId

    @NotNull
    @Column(name = "product_price")
    private Long productPrice;

    @NotNull
    private Double rating; //rating is 0

    @NotNull
    @Column(name = "no_of_items")
    private Long noOfItems;

    @Column(name = "merchant_name")
    private String merchantName;



    public Stock(@NotNull StockId id, @NotNull Long productPrice, @NotNull Double rating, @NotNull Long noOfItems, String merchantName) {
        this.id = id;
        this.productPrice = productPrice;
        this.rating = rating;
        this.noOfItems = noOfItems;
        this.merchantName = merchantName;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    public Stock() {
    }


    @Override
    public String toString() {
        return "Stock{" +
                "id=" + id +
                ", productPrice=" + productPrice +
                ", rating=" + rating +
                ", noOfItems=" + noOfItems +
                ", merchantName='" + merchantName + '\'' +
                '}';
    }

    public StockId getId() {
        return id;
    }

    public void setId(StockId id) {
        this.id = id;
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
    public int compareTo(Object o) {
        Double compareRating=((Stock)o).getRating();
        return (int) Math.ceil(compareRating - this.rating);
    }


}
