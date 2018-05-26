package com.coviam.shopcarro.merchant.controller;

import com.coviam.shopcarro.merchant.dto.MerchantDto;
import com.coviam.shopcarro.merchant.dto.MerchantProductListDto;
import com.coviam.shopcarro.merchant.dto.StockDetailsDto;
import com.coviam.shopcarro.merchant.exceptions.IdAlreadyExistsException;
import com.coviam.shopcarro.merchant.exceptions.IdNotFoundException;
import com.coviam.shopcarro.merchant.model.key.StockId;
import com.coviam.shopcarro.merchant.service.IMerchantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author sreerajr
 * @package com.coviam.shopcarro.merchant.controller
 * @project merchant
 */

@RestController
public class MerchantController {

    @Autowired
    private IMerchantService iMerchantService;

    /**
     *
     * Primary endpoints for client interaction
     * @param merchantId
     * @return
     * @throws IdNotFoundException
     */

    @RequestMapping(value = "/get-merchant-by-id", method = RequestMethod.GET)
    ResponseEntity<MerchantDto> getMerchantById(@RequestParam String merchantId) throws IdNotFoundException {
        System.out.println("some connection came in get merchant");
        MerchantDto merchantDto = iMerchantService.getMerchantById(merchantId);
        if(null == merchantDto) {
            throw new IdNotFoundException("Merchant not found in the database");
        }
        return new ResponseEntity<> (merchantDto, HttpStatus.OK);
    }

    @RequestMapping(value = "/get-merchants", method = RequestMethod.GET)
    ResponseEntity<List<StockDetailsDto> > getMerchants(@RequestBody MerchantProductListDto merchantProductListDto) {
        List<StockDetailsDto> stockDetailsDtos = iMerchantService.getMerchants(merchantProductListDto);
        return new ResponseEntity<>(stockDetailsDtos, HttpStatus.OK);
    }


    @RequestMapping(value = "/get-stock", method = RequestMethod.GET)
    ResponseEntity<StockDetailsDto> getStockDetails(@RequestParam String merchantId, @RequestParam String productId) throws IdNotFoundException {
        System.out.println("some connection came in get-stock");
        StockDetailsDto stockDetailsDto = iMerchantService.getStockById(new StockId(merchantId, productId));
        if(null == stockDetailsDto) {
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
    ResponseEntity<Boolean> decrementStock(@RequestParam String merchantId, @RequestParam String productId)  {
        System.out.println("some connection came in decrement-stock");
        return new ResponseEntity<>(iMerchantService.decrementStock(merchantId, productId), HttpStatus.OK);
    }

    @RequestMapping(value = "/get-available", method = RequestMethod.GET)
    ResponseEntity<Boolean> checkAvailability(@RequestParam String merchantId, @RequestParam String productId) {
        System.out.println("some connection came in get-available");
        return new ResponseEntity<>(iMerchantService.getAvailability(merchantId, productId), HttpStatus.OK);
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
        if(!iMerchantService.createMerchant(merchantDto))
            throw new IdAlreadyExistsException("merchant already exists in the database");
        return new ResponseEntity<> ("merchant created", HttpStatus.CREATED);
    }



    @RequestMapping(value = "/create-stock", method = RequestMethod.POST)
    ResponseEntity<String> createStock(@RequestBody StockDetailsDto stockDetailsDto) throws IdAlreadyExistsException {
        if(!iMerchantService.createStock(stockDetailsDto))
            throw new IdAlreadyExistsException("stock already exists in the database");
        return new ResponseEntity<> ("stock created", HttpStatus.CREATED);
    }


}
