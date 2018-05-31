package com.coviam.shopcarro.product.controller;

import com.coviam.shopcarro.product.dto.ProductDto;
import com.coviam.shopcarro.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.*;
import java.util.logging.*;

/**
 * @author sandeepgupta
 * @package com.coviam.shopcarro.product.controller
 * @project product
 */

/**
 * end points used.
 *      /create-product : adding product to the product DB
 *      /get-products : get all products
 *      /get-product-by-id : get product description by Id
 *      /get-product-name : get product name by Id
 *      /get-product-image : get product image url by Id
 * */
@CrossOrigin
@RestController
public class ProductController {

    @Autowired ProductService productService;

    public static final String CREATE_PRODUCT = "/create-product";
    private static final String GET_ALL_PRODUCTS = "/get-products";
    private static final String GET_PRODUCT_BY_ID = "/get-product-by-id";
    private static final String GET_PRODUCT_NAME = "/get-product-name";
    private static final String GET_PRODUCT_IMAGE = "/get-product-image";

    private static final Logger LOGGER = Logger.getLogger( ProductController.class.getName() );

    /**
     *  Add the product to the products using get request though it is not asked in the project but still this will be needed
     *  to populate the product DB.
     * */
    @RequestMapping(value= CREATE_PRODUCT,method = RequestMethod.POST)
    public ProductDto addProduct(@RequestBody ProductDto productDto){
        LOGGER.info( "Adding product to the Product DB with these details :" + productDto.toString());
        ProductDto productDtoCopy = productService.create(new ProductDto(productDto.getId(),productDto.getProductName(),productDto.getDescription(),productDto.getAttribute(),productDto.getPrice(),productDto.getMerchantId(),productDto.getImgUrl()));
        return productDtoCopy;
    }


    /**
     *  get-products will be returning all the product
     * */
    @RequestMapping(value = GET_ALL_PRODUCTS, method = RequestMethod.GET)
    ResponseEntity<List<ProductDto>> getProductList() throws NoSuchElementException{
        LOGGER.info( "Getting all products from DB :");
        List<ProductDto> list;
        list = productService.getAllProducts();
        // System.out.println("Controller " + list.size());
        return new ResponseEntity<>(list,HttpStatus.OK);
    }

    /**
     *  get the product the details with product Id
     * */
    @RequestMapping(value = GET_PRODUCT_BY_ID, method = RequestMethod.GET)
    ResponseEntity<ProductDto> getProduct(@RequestParam String productId) {
        ProductDto product = productService.get(productId);
        LOGGER.info( "Product Description by ID :" + product);
        return new ResponseEntity<>(product,HttpStatus.OK);
    }

    /**
     * Get product name by Id.
     * */

    @RequestMapping(value = GET_PRODUCT_NAME,method = RequestMethod.GET)
    ResponseEntity < String > getProductName(@RequestParam String productId){
        String productName = productService.getProductNameById(productId);
        LOGGER.info( "Getting product Name:" + productId);
        return new ResponseEntity<>(productName,HttpStatus.OK);
    }

    /**
     *  Get product image by product Id.
     * */

    @RequestMapping(value = GET_PRODUCT_IMAGE,method = RequestMethod.GET)
    ResponseEntity < String > getProductImage(@RequestParam String productId){
        String productImage = productService.getProductImageById(productId);
        LOGGER.info( "Getting product Image with Id" + productImage);
        return new ResponseEntity<>(productImage,HttpStatus.OK);
    }
}