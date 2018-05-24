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
public class Stock {

    @NotNull
    @EmbeddedId
    private StockId stockId;

    @NotNull
    @Column(name = "product_price")
    private Long productPrice;

    @NotNull
    private Double rating;

    @NotNull
    @Column(name = "no_of_items")
    private Long noOfItems;


    public Stock(StockId stockId, Long productPrice, Double rating, Long noOfItems) {
        this.stockId = stockId;
        this.productPrice = productPrice;
        this.rating = rating;
        this.noOfItems = noOfItems;
    }

    public Stock() {
    }

    @Override
    public String toString() {
        return "Stock{" +
                "stockId=" + stockId +
                ", productPrice=" + productPrice +
                ", rating=" + rating +
                ", noOfItems=" + noOfItems +
                '}';
    }

    public StockId getStockId() {
        return stockId;
    }

    public void setStockId(StockId stockId) {
        this.stockId = stockId;
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
}
