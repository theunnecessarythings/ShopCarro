package com.coviam.shopcarro.merchant.controller;

import com.coviam.shopcarro.merchant.dto.MerchantDto;
import com.coviam.shopcarro.merchant.dto.MerchantProductListDto;
import com.coviam.shopcarro.merchant.dto.StockDetailsDto;
import com.coviam.shopcarro.merchant.exceptions.IdAlreadyExistsException;
import com.coviam.shopcarro.merchant.exceptions.IdNotFoundException;
import com.coviam.shopcarro.merchant.model.MerchantProductDetails;
import com.coviam.shopcarro.merchant.model.key.StockId;
import com.coviam.shopcarro.merchant.service.MerchantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Logger;

/**
 * @author sreerajr
 * @package com.coviam.shopcarro.merchant.controller
 * @project merchant
 */

@CrossOrigin
@RestController
public class MerchantController {

    private final Logger LOGGER = Logger.getLogger(MerchantController.class.getName());
    @Autowired
    private MerchantService merchantService;

    /**
     *
     * Primary endpoints for client interaction
     * @param merchantId
     * @return
     * @throws IdNotFoundException
     */

    @RequestMapping(value = "/get-merchant-by-id", method = RequestMethod.GET)
    ResponseEntity<MerchantDto> getMerchantById(@RequestParam String merchantId) throws IdNotFoundException {
        LOGGER.info("Get Merchant By id : " + merchantId);
        MerchantDto merchantDto = merchantService.getMerchantById(merchantId);
        if(null == merchantDto) {
            LOGGER.info("Merchant not found in the database");
            throw new IdNotFoundException("Merchant not found in the database");
        }
        LOGGER.info("Merchant Details Returning");
        return new ResponseEntity<> (merchantDto, HttpStatus.OK);
    }

    @RequestMapping(value = "/get-merchants", method = RequestMethod.POST)
    ResponseEntity<List<StockDetailsDto> > getMerchants(@RequestBody MerchantProductListDto merchantProductListDto) {
        LOGGER.info("Getting merchant details of a product : " + merchantProductListDto);
        List<StockDetailsDto> stockDetailsDtos = merchantService.getMerchants(merchantProductListDto);
        return new ResponseEntity<>(stockDetailsDtos, HttpStatus.OK);
    }


    @RequestMapping(value = "/get-stock", method = RequestMethod.GET)
    ResponseEntity<StockDetailsDto> getStockDetails(@RequestParam String merchantId, @RequestParam String productId) throws IdNotFoundException {
        LOGGER.info("Get stock : merchantId -> " + merchantId + " productId -> " + productId);
        StockDetailsDto stockDetailsDto = merchantService.getStockById(new StockId(merchantId, productId));
        if(null == stockDetailsDto) {
            LOGGER.info("Stock not found");
            throw new IdNotFoundException("Stock not found in the database");
        }
        return new ResponseEntity<>(stockDetailsDto, HttpStatus.OK);
    }


    /**
     * This function will never get an invalid productId and merchantId (reason : order of API call)
     * @param merchantId
     * @param productId
     * @return
     */
    @RequestMapping(value = "/decrement-stock", method = RequestMethod.GET)
    ResponseEntity<Boolean> decrementStock(@RequestParam String merchantId, @RequestParam String productId, @RequestParam Long quantity)  {
        LOGGER.info("Decrement Stock : merchantId -> " + merchantId + " productId -> " + productId + " quantity -> " + quantity);
        return new ResponseEntity<>(merchantService.decrementStock(merchantId, productId, quantity), HttpStatus.OK);
    }

    @RequestMapping(value = "/get-available", method = RequestMethod.GET)
    ResponseEntity<Boolean> checkAvailability(@RequestParam String merchantId, @RequestParam String productId, @RequestParam Long quantity) {
        LOGGER.info("Check availability of Stock : merchantId -> " + merchantId + " productId -> " + productId + " quantity -> " + quantity);
        return new ResponseEntity<>(merchantService.getAvailability(merchantId, productId, quantity), HttpStatus.OK);
    }

    /**
     *
     * Creating API endpoints to add merchants and stock details
     * Might need to insert data into the database or to provide later functionalities
     * @param merchantDto
     * @return
     * @throws IdAlreadyExistsException
     */


    @RequestMapping(value = "/create-merchant", method = RequestMethod.POST)
    ResponseEntity<String> createMerchant(@RequestBody MerchantDto merchantDto) throws IdAlreadyExistsException {
        LOGGER.info("Creating merchant row : " + merchantDto);
        if(!merchantService.createMerchant(merchantDto)) {
            LOGGER.info("Merchant Id already exists");
            throw new IdAlreadyExistsException("merchant already exists in the database");
        }
        return new ResponseEntity<String>("{\"result\":\"success\"}", HttpStatus.CREATED);
    }



    @RequestMapping(value = "/create-stock", method = RequestMethod.POST)
    ResponseEntity<String> createStock(@RequestBody StockDetailsDto stockDetailsDto) throws IdAlreadyExistsException {
        LOGGER.info("Create Stock : " + stockDetailsDto);
        if(!merchantService.createStock(stockDetailsDto)) {
            throw new IdAlreadyExistsException("stock already exists in the database");
        }
        return new ResponseEntity<> ("stock created", HttpStatus.CREATED);
    }

    @RequestMapping(value = "/get-merchant-product",method = RequestMethod.GET)
    ResponseEntity<MerchantProductDetails> getMerchantProduct(@RequestParam String merchantId,@RequestParam String productId,@RequestParam String quantity) throws IdNotFoundException {
        LOGGER.info("Getting merchant and product of Stock : merchantId -> " + merchantId + " productId -> " + productId + " quantity -> " + quantity);
        StockDetailsDto stockDetailsDto = merchantService.getStockById(new StockId(merchantId, productId));
        if(null == stockDetailsDto) {
            LOGGER.info("Stock not found");
            throw new IdNotFoundException("Stock not found in the database");
        }
        return new ResponseEntity<>(new MerchantProductDetails(stockDetailsDto), HttpStatus.OK);
    }


}
