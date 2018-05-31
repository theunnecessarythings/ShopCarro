package com.coviam.shopcarro.product.controller;

import com.coviam.shopcarro.product.dto.ProductDto;
import com.coviam.shopcarro.product.model.Product;
import com.coviam.shopcarro.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

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

@RestController
public class ProductController {

    @Autowired ProductService productService;

    public static final String CREATE_PRODUCT = "/create-product";
    private static final String GET_ALL_PRODUCTS = "/get-products";
    private static final String GET_PRODUCT_BY_ID = "/get-product-by-id";
    private static final String GET_PRODUCT_NAME = "/get-product-name";
    private static final String GET_PRODUCT_IMAGE = "/get-product-image";

    /**
     *  Add the product to the products using get request though it is not asked in the project but still this will be needed
     *  to populate the product DB.
     * */
    @RequestMapping(value= CREATE_PRODUCT,method = RequestMethod.POST)
    public ProductDto addProduct(@RequestBody ProductDto productDto){
        System.out.println("Create-Product  - "+ productDto.getProductName());
        ProductDto productDtoCopy;
        productDtoCopy = productService.create(new ProductDto(productDto.getId(),productDto.getProductName(),productDto.getDescription(),productDto.getAttribute(),productDto.getPrice(),productDto.getMerchantId(),productDto.getImgUrl()));
        return productDtoCopy;
    }


    /**
     *  get-products will be returning all the product
     * */
    @RequestMapping(value = GET_ALL_PRODUCTS, method = RequestMethod.GET)
    ResponseEntity<List<ProductDto>> getProductList() throws NoSuchElementException{
        System.out.println("get-product");
        List<ProductDto> list;
        list = productService.getAllProducts();
        // System.out.println("Controller " + list.size());
        return new ResponseEntity<>(list,HttpStatus.ACCEPTED);
    }

    /**
     *  get the product the details with product Id
     * */
    @RequestMapping(value = GET_PRODUCT_BY_ID, method = RequestMethod.GET)
    ResponseEntity<ProductDto> getProduct(@RequestParam String productId) {
        System.out.println("inside - (get-product-by-id) ");
        ProductDto product;
        product = productService.get(productId);
        return new ResponseEntity<>(product,HttpStatus.ACCEPTED);
    }

    /**
     * Get product name by Id.
     * */

    @RequestMapping(value = GET_PRODUCT_NAME,method = RequestMethod.GET)
    ResponseEntity < String > getProductName(@RequestParam String productId){
        System.out.println("Inside get Product Name");
        String productName = productService.getProductNameById(productId);
        return new ResponseEntity<>(productName,HttpStatus.ACCEPTED);
    }

    /**
     *  Get product image by product Id.
     * */

    @RequestMapping(value = GET_PRODUCT_IMAGE,method = RequestMethod.GET)
    ResponseEntity < String > getProductImage(@RequestParam String productId){
        System.out.println("Inside get Product Image");
        String productImage = productService.getProductImageById(productId);
        return new ResponseEntity<>(productImage,HttpStatus.ACCEPTED);
    }
}