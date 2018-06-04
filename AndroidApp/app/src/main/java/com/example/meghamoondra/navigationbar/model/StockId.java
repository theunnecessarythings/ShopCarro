package com.example.meghamoondra.navigationbar.model;

import java.io.Serializable;

public class StockId  implements Serializable {
    private String merchantId;
    private String productId;

    public StockId() {
    }

    public StockId(String merchantId, String productId) {
        this.merchantId = merchantId;
        this.productId = productId;
    }

    @Override
    public String toString() {
        return "StockId{" +
                "merchantId='" + merchantId + '\'' +
                ", productId='" + productId + '\'' +
                '}';
    }
}