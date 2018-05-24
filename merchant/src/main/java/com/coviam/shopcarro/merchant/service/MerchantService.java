package com.coviam.shopcarro.merchant.service;

import com.coviam.shopcarro.merchant.dto.MerchantDto;
import com.coviam.shopcarro.merchant.dto.StockDetailsDto;
import com.coviam.shopcarro.merchant.model.Merchant;
import com.coviam.shopcarro.merchant.model.Stock;
import com.coviam.shopcarro.merchant.model.key.StockId;
import com.coviam.shopcarro.merchant.repository.IMerchantRepository;
import com.coviam.shopcarro.merchant.repository.IStockRepository;
import com.coviam.shopcarro.merchant.utilities.UtilityFunctions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author sreerajr
 * @package com.coviam.shopcarro.merchant.service
 * @project merchant
 */

@Service
public class MerchantService implements IMerchantService {

    @Autowired
    private IMerchantRepository iMerchantRepository;

    @Autowired
    private IStockRepository iStockRepository;

    @Override
    public MerchantDto getMerchantById(String merchantId) {
        Optional<Merchant> merchant = iMerchantRepository.findById(merchantId);
        if(!merchant.isPresent())
            return null;
        return UtilityFunctions.merchantToMerchantDto(merchant.get());
    }

    @Override
    public StockDetailsDto getStockById(StockId stockId) {
        Optional<Stock> stock = iStockRepository.findById(stockId);
        if(!stock.isPresent())
            return null;
        return UtilityFunctions.stockToStockDetailsDto(stock.get());
    }

    @Override
    public Boolean createMerchant(MerchantDto merchantDto) {
        if(null != getMerchantById(merchantDto.getMerchantId())) {
            return false;
        }
        iMerchantRepository.save(UtilityFunctions.merchantDtoToMerchant(merchantDto));
        return true;
    }

    @Override
    public Boolean createStock(StockDetailsDto stockDetailsDto) {
        if(null != getStockById(new StockId(stockDetailsDto.getMerchantId(), stockDetailsDto.getProductId()))) {
            return false;
        }
        iStockRepository.save(UtilityFunctions.stockDetailsDtoToStock(stockDetailsDto));
        return true;
    }
}
