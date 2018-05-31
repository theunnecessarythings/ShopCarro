package com.coviam.shopcarro.merchant.service;

import com.coviam.shopcarro.merchant.dto.MerchantDto;
import com.coviam.shopcarro.merchant.dto.MerchantProductListDto;
import com.coviam.shopcarro.merchant.dto.StockDetailsDto;
import com.coviam.shopcarro.merchant.model.key.StockId;
import com.sun.org.apache.xpath.internal.operations.Bool;

import java.util.List;

/**
 * @author sreerajr
 * @package com.coviam.shopcarro.merchant.service
 * @project merchant
 */

public interface MerchantService {
    MerchantDto getMerchantById(String merchantId);

    StockDetailsDto getStockById(StockId stockId);

    Boolean createMerchant(MerchantDto merchantDto);

    Boolean createStock(StockDetailsDto stockDetailsDto);

    Boolean decrementStock(String merchantId, String productId, Long quantity);

    Boolean getAvailability(String merchantId, String productId, Long quantity);

    List<StockDetailsDto> getMerchants(MerchantProductListDto merchantProductListDto);
}
