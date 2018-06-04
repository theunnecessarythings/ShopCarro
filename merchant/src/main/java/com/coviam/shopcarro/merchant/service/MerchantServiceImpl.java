package com.coviam.shopcarro.merchant.service;

import com.coviam.shopcarro.merchant.dto.MerchantDto;
import com.coviam.shopcarro.merchant.dto.MerchantProductListDto;
import com.coviam.shopcarro.merchant.dto.StockDetailsDto;
import com.coviam.shopcarro.merchant.model.Merchant;
import com.coviam.shopcarro.merchant.model.Stock;
import com.coviam.shopcarro.merchant.model.key.StockId;
import com.coviam.shopcarro.merchant.repository.MerchantRepository;
import com.coviam.shopcarro.merchant.repository.StockRepository;
import com.coviam.shopcarro.merchant.utilities.UtilityFunctions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

/**
 * @author sreerajr
 * @package com.coviam.shopcarro.merchant.service
 * @project merchant
 */

@Service
public class MerchantServiceImpl implements MerchantService {

    @Autowired
    private MerchantRepository merchantRepository;

    @Autowired
    private StockRepository stockRepository;

    @Value("${merchant.rating.weight.price}") Double priceWeight;
    @Value("${merchant.rating.weight.sold}") Double soldWeight;
    @Value("${merchant.rating.weight.offered}") Double offeredWeight;
    @Value("${merchant.rating.weight.items}") Double itemsWeight;
    @Value("${merchant.rating.weight.rating}") Double ratingWeight;

    private final Logger LOGGER = Logger.getLogger(MerchantServiceImpl.class.getName());

    @Override
    public MerchantDto getMerchantById(String merchantId) {
        Optional<Merchant> merchant = merchantRepository.findById(merchantId);
        if(!merchant.isPresent()) {
            LOGGER.info("Id not present");
            return null;
        }
        return UtilityFunctions.merchantToMerchantDto(merchant.get());
    }

    @Override
    public StockDetailsDto getStockById(StockId stockId) {
        Optional<Stock> stock = stockRepository.findById(stockId);
        if(!stock.isPresent()) {
            LOGGER.info("Stock not present");
            return null;
        }
        return UtilityFunctions.stockToStockDetailsDto(stock.get());
    }

    @Override
    public Boolean createMerchant(MerchantDto merchantDto) {
        if(null != getMerchantById(merchantDto.getMerchantId())) {
            LOGGER.info("Merchant exists");
            return false;
        }
        merchantRepository.save(UtilityFunctions.merchantDtoToMerchant(merchantDto));
        return true;
    }

    @Override
    public Boolean createStock(StockDetailsDto stockDetailsDto) {
        StockDetailsDto stocks = getStockById(new StockId(stockDetailsDto.getMerchantId(), stockDetailsDto.getProductId()));
        if(null != stocks){
            LOGGER.info("Stock exists : updating quantity...");
            stocks.setNoOfItems(stocks.getNoOfItems() + stockDetailsDto.getNoOfItems());
        }
        stockRepository.save(UtilityFunctions.stockDetailsDtoToStock(stockDetailsDto));
        return true;
    }

    @Override
    public Boolean decrementStock(String merchantId, String productId, Long quantity) {
        StockDetailsDto stockDetailsDto = getStockById(new StockId(merchantId, productId));
        if(null == stockDetailsDto || stockDetailsDto.getNoOfItems() <= 0) {
            LOGGER.info("Stock unavailable");
            return false;
        }
        MerchantDto merchantDto = getMerchantById(merchantId);
        LOGGER.info("Stock available");
        /**
         * Add stock solds count and decrement stock in stock repository
         */
        merchantDto.setNoOfProductsSold(merchantDto.getNoOfProductsSold() + quantity);
        stockDetailsDto.setNoOfItems(stockDetailsDto.getNoOfItems() - quantity);
        merchantRepository.save(UtilityFunctions.merchantDtoToMerchant(merchantDto));
        stockRepository.save(UtilityFunctions.stockDetailsDtoToStock(stockDetailsDto));
        return true;
    }

    @Override
    public Boolean getAvailability(String merchantId, String productId, Long quantity) {
        StockDetailsDto stockDetailsDto = getStockById(new StockId(merchantId, productId));
        if(null == stockDetailsDto) {
            LOGGER.info("Stock unavailable");
            return false;
        }
        return stockDetailsDto.getNoOfItems() - quantity >= 0;
    }



    @Override
    public List<StockDetailsDto> getMerchants(MerchantProductListDto merchantProductListDto) {
        List<Stock> stocks = updateScore(stockRepository.findByIdIn(merchantProductListDto.getStockIds()), merchantProductListDto.getStockIds());
        //Sort the merchants by the weighted average score
        Collections.sort(stocks);
        return UtilityFunctions.stockListToDtoList(stocks);
    }


    private List<Stock> updateScore(List<Stock> stocks, List<StockId> stockIds) {

        List<Double> ratings = new ArrayList<>();
        for(Stock stock : stocks) {

            Merchant merchant = merchantRepository.findById(stock.getId().getMerchantId()).get();
            Double rating = merchant.getMerchantRating();
            Long offered = stockRepository.countByIdMerchantId(merchant.getMerchantId());

            Long sold = merchant.getNoOfProductsSold();
            Long items = stock.getNoOfItems();   //current stock
            //Price of the product by varprivate Date entryDate;
            //   private Double netRevenue;
            //   private Double monthlyRecurringRevenue;
            //   private Double incomePaymentType;
            //   private Double incomeProductType;
            //   private int activeSubscriptions;
            //   private int newMonthlySubsciptions;
            //   private int upcomingPayments;
            //   private int deferredPayments;
            //   private Double churnRate;
            //   private Double refundsProcessed;ious merchant -> should be affecting inversely
            Long price = stock.getProductPrice();

            Double stockRating = ratingWeight * rating + offered * offeredWeight + sold * soldWeight + items * itemsWeight + (1 / price) * priceWeight;
            stock.setRating(stockRating);
            ratings.add(stockRating);
        }
        /**
         * Naive Normalization
         */
        Double maxRating = Collections.max(ratings);
        for(Stock stock : stocks){
            stock.setRating((stock.getRating() / maxRating) * 5);
        }

        return stocks;
    }


}
