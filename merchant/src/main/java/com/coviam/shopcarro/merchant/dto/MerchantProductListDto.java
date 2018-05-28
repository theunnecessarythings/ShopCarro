package com.coviam.shopcarro.merchant.dto;

import com.coviam.shopcarro.merchant.model.key.StockId;

import java.util.List;

/**
 * @author sreerajr
 * @package com.coviam.shopcarro.merchant.dto
 * @project merchant
 */
public class MerchantProductListDto {
    private List<StockId> stockIds;

    public MerchantProductListDto(List<StockId> stockIds) {
        this.stockIds = stockIds;
    }

    public MerchantProductListDto() {
    }

    public List<StockId> getStockIds() {
        return stockIds;
    }

    public void setStockIds(List<StockId> stockIds) {
        this.stockIds = stockIds;
    }


    @Override
    public String toString() {
        return "MerchantProductListDto{" +
                "stockIds=" + stockIds +
                '}';
    }
}
