package com.coviam.shopcarro.merchant.model.key;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author sreerajr
 * @package com.coviam.shopcarro.merchant.model
 * @project merchant
 */

/**
 *
 * Composite key for merchant_product table
 */

@Embeddable
public class StockId implements Serializable {

    @NotNull
    //@Column(name = "merchant_id")
    private String merchantId;

    @NotNull
    @Column(name = "product_id")
    private String productId;

    public StockId(String merchantId, String productId) {
        this.merchantId = merchantId;
        this.productId = productId;
    }

    public StockId() {
    }

    @Override
    public String toString() {
        return "StockId{" +
                "merchantId='" + merchantId + '\'' +
                ", productId='" + productId + '\'' +
                '}';
    }

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }
}