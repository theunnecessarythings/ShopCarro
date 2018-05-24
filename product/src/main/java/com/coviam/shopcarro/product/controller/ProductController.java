package com.coviam.shopcarro.product.controller;

import com.coviam.shopcarro.product.model.Product;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author sreerajr
 * @package com.coviam.shopcarro.product.controller
 * @project product
 */
public class ProductController {

    @RequestMapping(value = "/get-products", method = RequestMethod.GET)
    ResponseEntity<List<Product>> getProductList() {

    }


    @RequestMapping(value = "/get", method = RequestMethod.GET)
    ResponseEntity<Product> getProduct(@RequestParam String productId) {

    }

}
