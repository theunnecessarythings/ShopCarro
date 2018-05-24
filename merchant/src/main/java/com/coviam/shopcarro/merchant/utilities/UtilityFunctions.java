package com.coviam.shopcarro.merchant.utilities;

import com.coviam.shopcarro.merchant.dto.MerchantDto;
import com.coviam.shopcarro.merchant.dto.StockDetailsDto;
import com.coviam.shopcarro.merchant.model.Merchant;
import com.coviam.shopcarro.merchant.model.Stock;
import com.coviam.shopcarro.merchant.model.key.StockId;

/**
 * @author sreerajr
 * @package com.coviam.shopcarro.merchant.utilities
 * @project merchant
 */


/**
 *
 * Utility to functions to convert to and fro between dto objects and model objects
 */
public class UtilityFunctions {


    public static Merchant merchantDtoToMerchant(MerchantDto merchantDto) {
        Merchant merchant = new Merchant();
        merchant.setNoOfProductsSold(merchantDto.getNoOfProductsSold());
        merchant.setNoOfProductsOfferedToSell(merchantDto.getNoOfProductsOfferedToSell());
        merchant.setMerchantId(merchantDto.getMerchantId());
        merchant.setMerchantName(merchantDto.getMerchantName());
        return merchant;
    }


    public static MerchantDto merchantToMerchantDto(Merchant merchant) {
        MerchantDto merchantDto = new MerchantDto();
        merchantDto.setNoOfProductsSold(merchant.getNoOfProductsSold());
        merchantDto.setNoOfProductsOfferedToSell(merchant.getNoOfProductsOfferedToSell());
        merchantDto.setMerchantId(merchant.getMerchantId());
        merchantDto.setMerchantName(merchant.getMerchantName());
        return merchantDto;
    }

    public static Stock stockDetailsDtoToStock(StockDetailsDto stockDetailsDto) {
        Stock stock = new Stock();
        stock.setRating(stockDetailsDto.getRating());
        stock.setNoOfItems(stockDetailsDto.getNoOfItems());
        stock.setProductPrice(stockDetailsDto.getProductPrice());
        stock.setStockId(new StockId(stockDetailsDto.getMerchantId(),stockDetailsDto.getProductId()));
        return stock;
    }

    public static StockDetailsDto stockToStockDetailsDto(Stock stock) {
        StockDetailsDto stockDetailsDto = new StockDetailsDto();
        stockDetailsDto.setRating(stock.getRating());
        stockDetailsDto.setProductPrice(stock.getProductPrice());
        stockDetailsDto.setNoOfItems(stock.getNoOfItems());
        stockDetailsDto.setProductId(stock.getStockId().getProductId());
        stockDetailsDto.setMerchantId(stock.getStockId().getMerchantId());
        return stockDetailsDto;
    }

}
