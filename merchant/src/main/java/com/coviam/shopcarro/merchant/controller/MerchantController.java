package com.coviam.shopcarro.merchant.controller;

import com.coviam.shopcarro.merchant.dto.MerchantDto;
import com.coviam.shopcarro.merchant.dto.StockDetailsDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author sreerajr
 * @package com.coviam.shopcarro.merchant.controller
 * @project merchant
 */
public class MerchantController {

    @RequestMapping(value = "/get-merchant", method = RequestMethod.GET)
    ResponseEntity<MerchantDto> getMerchantById(@RequestParam String merchantId) {

    }


    @RequestMapping(value = "/get-stock-details", method = RequestMethod.GET)
    ResponseEntity<StockDetailsDto> getStockDetails(@RequestParam String merchantId, @RequestParam String productId) {

    }


}
