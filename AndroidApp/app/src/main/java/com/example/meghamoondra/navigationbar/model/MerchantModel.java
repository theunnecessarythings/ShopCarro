package com.example.meghamoondra.navigationbar.model;

import java.io.Serializable;
import java.util.List;

public class MerchantModel implements Serializable {
    private List<StockId> stockIds;
    public List<StockId> getStockIds() {
        return stockIds;
    }

    public void setStockIds(List<StockId> stockIds) {
        this.stockIds = stockIds;
    }

    @Override
    public String toString() {
        return "MerchantModel{" +
                "stockIds=" + stockIds +
                '}';
    }
}
