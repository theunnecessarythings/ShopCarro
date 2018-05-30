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

@RestController
public class ProductController {

    @Autowired ProductService productService;


    /**
     *  Add the product to the products using get request though it is not asked in the project but still this will be needed
     *  to populate the product DB.
     * */
    @RequestMapping(value= "/create-product",method = RequestMethod.POST)
    public ProductDto addProduct(@RequestBody ProductDto productDto){
        System.out.println("Create-Product  - "+ productDto.getProductName());
        ProductDto productDtoCopy = new ProductDto();
        productDtoCopy = productService.create(new ProductDto(productDto.getId(),productDto.getProductName(),productDto.getDescription(),productDto.getAttribute(),productDto.getPrice(),productDto.getMerchantId(),productDto.getImgUrl()));
        return productDtoCopy;
    }


    /**
     *  get-products will be returning all the product
     * */
    @RequestMapping(value = "/get-products", method = RequestMethod.GET)
    ResponseEntity<List<ProductDto>> getProductList() throws NoSuchElementException{
        System.out.println("get-product");
        List<ProductDto> list = new ArrayList<>();
        list = productService.getAllProducts();
        // System.out.println("Controller " + list.size());
        return new ResponseEntity<>(list,HttpStatus.ACCEPTED);
    }

    /**
     *  get the product the details with product Id
     * */
    @RequestMapping(value = "/get-product-by-id", method = RequestMethod.GET)
    ResponseEntity<ProductDto> getProduct(@RequestParam String productId) {
        System.out.println("inside - (get-product-by-id) ");
        ProductDto product = new ProductDto();
        product = productService.get(productId);
        return new ResponseEntity<>(product,HttpStatus.ACCEPTED);
    }

    @RequestMapping(value = "/get-product-name",method = RequestMethod.GET)
    ResponseEntity < String > getProductName(@RequestParam String productId){
        System.out.println("Inside get Product Name");
        String productName = productService.getProductNameById(productId);
        return new ResponseEntity<>(productName,HttpStatus.ACCEPTED);
    }

    @RequestMapping(value = "/get-product-image",method = RequestMethod.GET)
    ResponseEntity < String > getProductImage(@RequestParam String productId){
        System.out.println("Inside get Product Image");
        String productImage = productService.getProductImageById(productId);
        return new ResponseEntity<>(productImage,HttpStatus.ACCEPTED);
    }
}