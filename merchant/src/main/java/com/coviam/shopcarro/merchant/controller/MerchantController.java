package com.coviam.shopcarro.merchant.controller;

import com.coviam.shopcarro.merchant.dto.MerchantDto;
import com.coviam.shopcarro.merchant.dto.StockDetailsDto;
import com.coviam.shopcarro.merchant.exceptions.IdAlreadyExistsException;
import com.coviam.shopcarro.merchant.exceptions.IdNotFoundException;
import com.coviam.shopcarro.merchant.model.key.StockId;
import com.coviam.shopcarro.merchant.service.IMerchantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @RequestMapping(value = "/get-merchant", method = RequestMethod.GET)
    ResponseEntity<MerchantDto> getMerchantById(@RequestParam String merchantId) throws IdNotFoundException {
        MerchantDto merchantDto = iMerchantService.getMerchantById(merchantId);
        if(null == merchantDto) {
            throw new IdNotFoundException("Merchant not found in the database");
        }
        return new ResponseEntity<> (merchantDto, HttpStatus.OK);
    }


    @RequestMapping(value = "/get-stock-details", method = RequestMethod.GET)
    ResponseEntity<StockDetailsDto> getStockDetails(@RequestParam String merchantId, @RequestParam String productId) throws IdNotFoundException {
        StockDetailsDto stockDetailsDto = iMerchantService.getStockById(new StockId(merchantId, productId));
        if(null == stockDetailsDto) {
            throw new IdNotFoundException("Stock not found in the database");
        }
        return new ResponseEntity<>(stockDetailsDto, HttpStatus.OK);
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
