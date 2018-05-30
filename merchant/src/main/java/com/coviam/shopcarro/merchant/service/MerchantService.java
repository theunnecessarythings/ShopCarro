package com.coviam.shopcarro.merchant.service;

import com.coviam.shopcarro.merchant.dto.MerchantDto;
import com.coviam.shopcarro.merchant.dto.MerchantProductListDto;
import com.coviam.shopcarro.merchant.dto.StockDetailsDto;
import com.coviam.shopcarro.merchant.model.Merchant;
import com.coviam.shopcarro.merchant.model.Stock;
import com.coviam.shopcarro.merchant.model.key.StockId;
import com.coviam.shopcarro.merchant.repository.IMerchantRepository;
import com.coviam.shopcarro.merchant.repository.IStockRepository;
import com.coviam.shopcarro.merchant.utilities.UtilityFunctions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    @Value("${merchant.rating.weight.price}") Double priceWeight;
    @Value("${merchant.rating.weight.sold}") Double soldWeight;
    @Value("${merchant.rating.weight.offered}") Double offeredWeight;
    @Value("${merchant.rating.weight.items}") Double itemsWeight;
    @Value("${merchant.rating.weight.rating}") Double ratingWeight;


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

    @Override
    public Boolean decrementStock(String merchantId, String productId, Long quantity) {
        StockDetailsDto stockDetailsDto = getStockById(new StockId(merchantId, productId));
        if(null == stockDetailsDto || stockDetailsDto.getNoOfItems() <= 0) {
            return false;
        }
        MerchantDto merchantDto = getMerchantById(merchantId);

        /**
         * Add stock solds count and decrement stock in stock repository
         */
        merchantDto.setNoOfProductsSold(merchantDto.getNoOfProductsSold() + quantity);
        stockDetailsDto.setNoOfItems(stockDetailsDto.getNoOfItems() - quantity);
        iMerchantRepository.save(UtilityFunctions.merchantDtoToMerchant(merchantDto));
        iStockRepository.save(UtilityFunctions.stockDetailsDtoToStock(stockDetailsDto));
        return true;
    }

    @Override
    public Boolean getAvailability(String merchantId, String productId, Long quantity) {
        StockDetailsDto stockDetailsDto = getStockById(new StockId(merchantId, productId));
        if(null == stockDetailsDto)
            return false;
        System.out.println(stockDetailsDto.getNoOfItems());
        System.out.println(quantity);
        return stockDetailsDto.getNoOfItems() - quantity >= 0;
    }



    @Override
    public List<StockDetailsDto> getMerchants(MerchantProductListDto merchantProductListDto) {
        List<Stock> stocks = calculateScore(iStockRepository.findByIdIn(merchantProductListDto.getStockIds()), merchantProductListDto.getStockIds());
        //Sort the merchants by the weighted average score
        Collections.sort(stocks);
        return UtilityFunctions.stockListToDtoList(stocks);
    }


    private List<Stock> calculateScore(List<Stock> stocks, List<StockId> stockIds) {

        List<Double> ratings = new ArrayList<>();
//        List<String> merchantIds = stockIds.stream().map((stockId) -> stockId.getMerchantId()).collect(Collectors.toList());
//        List<Merchant> merchants = iMerchantRepository.findByMerchantIdIn(merchantIds);

        for(Stock stock : stocks) {

            //Use the findByMerchantIdIn(List<String> ids) later
            Merchant merchant = iMerchantRepository.findById(stock.getId().getMerchantId()).get();
            Double rating = merchant.getMerchantRating();
            Long offered = iStockRepository.countByIdMerchantId(merchant.getMerchantId());

            Long sold = merchant.getNoOfProductsSold();
            Long items = stock.getNoOfItems();   //current stock
            //Price of the product by various merchant -> should be affecting inversely
            Long price = stock.getProductPrice();

            Double stockRating = ratingWeight * rating + offered * offeredWeight + sold * soldWeight + items * itemsWeight + (1 / price) * priceWeight;
            stock.setRating(stockRating);
            ratings.add(stockRating);
        }

        /**
         *
         *
         * Don't do this
         *
         */

        Double maxRating = Collections.max(ratings);
        for(Stock stock : stocks){
            stock.setRating((stock.getRating() / maxRating) * 5);
        }

        return stocks;
    }


}
