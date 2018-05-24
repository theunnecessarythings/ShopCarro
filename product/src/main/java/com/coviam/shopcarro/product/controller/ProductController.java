package com.coviam.shopcarro.product.controller;

import com.coviam.shopcarro.product.dto.ProductDto;
import com.coviam.shopcarro.product.model.Product;
import com.coviam.shopcarro.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author sandeepgupta
 * @package com.coviam.shopcarro.product.controller
 * @project product
 */

@RestController
public class ProductController {

    /**
     *     private String usp;
     *     private String description;
     *     private String attribute;
     *     private Long price;
     *     private String merchantId;
     *     private String imgUrl;
     *
     * */

    @Autowired ProductService productService;


    /**
     *
     *  Add the product to the products using get request though it is not asked in the project but still this will be needed
     *  to populate the product DB.
     *
     * */
    @RequestMapping(value= "/create-product",method = RequestMethod.GET)
    public ProductDto addProduct(@RequestParam String id,@RequestParam String usp, @RequestParam String description, @RequestParam String attribute, @RequestParam Long price, @RequestParam String merchantId,@RequestParam String imgUrl){
        System.out.println("Hello - "+ usp);
        ProductDto productDto = new ProductDto();
        productDto = productService.create(new ProductDto(id,usp,description,attribute,price,merchantId,imgUrl));
        return productDto;
    }


    /**
     *  get-products will be returning all the product
     * */
    @RequestMapping(value = "/get-products", method = RequestMethod.GET)
    ResponseEntity<List<ProductDto>> getProductList() {
        List<ProductDto> list = new ArrayList<>();
        list = productService.getAllProducts();
        System.out.println("Controller " + list.size());
        return new ResponseEntity<>(list,HttpStatus.ACCEPTED);
    }

    @RequestMapping(value = "/get", method = RequestMethod.GET)
    ResponseEntity<ProductDto> getProduct(@RequestParam String productId) {
        ProductDto product = new ProductDto();
        product = productService.get(productId);
        return new ResponseEntity<>(product,HttpStatus.ACCEPTED);
    }
}
