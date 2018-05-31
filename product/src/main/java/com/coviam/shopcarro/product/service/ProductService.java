package com.coviam.shopcarro.product.service;

import com.coviam.shopcarro.product.controller.ProductController;
import com.coviam.shopcarro.product.dto.ProductDto;
import com.coviam.shopcarro.product.model.Product;
import com.coviam.shopcarro.product.repository.IProductRepository;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.logging.Logger;


@Service
public class ProductService implements IProductService {

    @Autowired
    private IProductRepository iProductRepository;

    private static final Logger LOGGER = Logger.getLogger( ProductService.class.getName() );

    @Value("${urlAddSearch}") private String urlOfAddSearch;
    @Value("${urlAddMerchants}") private String urlOfAddMerchants;
    @Value("${urlAddStock}") private String urlOfAddStock;

    /**
     *  Create: For adding products in the mongoDB database.
     *  Id's will be fixed and will be considered as the Key Id's for mongoDB.
     *
     *  Through this call we can add product in merchant DB and search DB simultaneously.
     * */


    private JSONArray getJsonArray(List<String> list) {
        JSONArray resp = new JSONArray();
        list.forEach(item -> {
            resp.put(item);
        });
        return resp;
    }



    @Override
    public ProductDto create(ProductDto productDto) {
        Product product = new Product();
        BeanUtils.copyProperties(productDto,product);


        /**
         * adding products in to the search DB for Data Consistency.
         * */
        LOGGER.info( "Adding product to search:" + productDto);
        RestTemplate restTemplate = new RestTemplate();
        JSONObject map = new JSONObject();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        //headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        //MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();

        try {
            map.put("id", product.getId());
            map.put("description", product.getDescription());
            map.put("productName", product.getProductName());
            map.put("attribute", product.getAttribute());
            map.put("price", product.getPrice());
            map.put("merchantId", getJsonArray(product.getMerchantId()));
            map.put("imgUrl", product.getImgUrl());

        } catch (JSONException e) {
            LOGGER.info(e.toString());
        }

        HttpEntity<String> entity = new HttpEntity<String>(map.toString(), headers);
        ResponseEntity<Boolean> response = restTemplate.exchange(urlOfAddSearch, HttpMethod.POST, entity, Boolean.class);

        LOGGER.info("Product added to the search DB " + response);

        /**
         * adding products to merchants DB
         * */

        for(int i=0;i<product.getMerchantId().size();i++){
            JSONObject map1 = new JSONObject();
            try {
                map1.put("merchantId", product.getMerchantId().get(i));
                map1.put("merchantName", "Shriya");
                map1.put("noOfProductsSold","0");
                map1.put("merchantRating", "4");

            } catch (JSONException e) {
                LOGGER.info(e.toString());
            }
            HttpEntity<String> entity1 = new HttpEntity<String>(map1.toString(), headers);
            ResponseEntity<String> response1 = restTemplate.exchange(urlOfAddMerchants, HttpMethod.POST, entity1, String.class);
            LOGGER.info("Added to the merchant DB: " + response);
        }

        /**
         * Adding products with merchant Db having details about the stock
         * */

        //String urlForAddingToStock = "";
        //MultiValueMap<String, Object> map2= new LinkedMultiValueMap<String, Object>();
        for(int i=0;i<product.getMerchantId().size();i++){
            JSONObject map2 = new JSONObject();
            try {
                map2.put("productId", product.getId());
                map2.put("merchantId", product.getMerchantId().get(i));
                map2.put("productPrice",product.getPrice());
                map2.put("rating", "4");
                map2.put("noOfItems", "100");
                map2.put("merchantName", "Shriya");
            } catch (JSONException e) {
                LOGGER.info(e.toString());
                //e.printStackTrace();
            }
            HttpEntity<String> entity2 = new HttpEntity<String>(map2.toString(), headers);
            ResponseEntity<String> response2 = restTemplate.exchange(urlOfAddStock, HttpMethod.POST, entity2, String.class);
            LOGGER.info("Added to the Stock DB: " + response2);
        }

        LOGGER.info("Adding to the product DB");
        iProductRepository.save(product);
        return productDto;
    }


    /**
     *  getAllProducts() will be returning the list of all product available in database.
     *  later we can pass the userCharacteristics to this functions to filter out the products details for particular users to display
     * */
    @Override
    public List<ProductDto> getAllProducts(){
        List<Product> list = new ArrayList<>();
        list = iProductRepository.findAll();
        List<ProductDto> listDto = new ArrayList<>();

        for(Product products:list){
            ProductDto productDto = new ProductDto();

            productDto.setAttribute(products.getAttribute());
            productDto.setPrice(products.getPrice());
            productDto.setImgUrl(products.getImgUrl());
            productDto.setDescription(products.getDescription());
            productDto.setId(products.getId());
            productDto.setMerchantId(products.getMerchantId());
            productDto.setProductName(products.getProductName());

            listDto.add(productDto);
        }

        return listDto;
    }

    @Override
    public  ProductDto get(String id) throws  NoSuchElementException{
        Optional<Product> product = Optional.of(new Product());
        if(!iProductRepository.existsById(id)){
            throw new NoSuchElementException("This is not a valid Id");
        }
        product = iProductRepository.findById(id);
        ProductDto productDto = new ProductDto();
        /**
         *  This is for converting the optional response to ProductDto
         * */

        productDto.setId(product.get().getId());
        productDto.setAttribute(product.get().getAttribute());
        productDto.setDescription(product.get().getDescription());
        productDto.setProductName(product.get().getProductName());
        productDto.setMerchantId(product.get().getMerchantId());
        productDto.setImgUrl(product.get().getImgUrl());
        productDto.setPrice(product.get().getPrice());

        return productDto;
    }

    public String getProductNameById(String id) throws NoSuchElementException{
        if(!iProductRepository.existsById(id)){
            throw new NoSuchElementException("Product Name not found");
        }
        Optional<Product> product = Optional.of(new Product());
        product = iProductRepository.findById(id);
        return product.get().getProductName();
    }

    public String getProductImageById(String id){
        if(!iProductRepository.existsById(id)){
            throw new NoSuchElementException("Product Name not found");
        }
        Optional<Product> product = Optional.of(new Product());
        product = iProductRepository.findById(id);
        return product.get().getImgUrl();
    }
}
