package com.coviam.shopcarro.addToCart.services;

import com.coviam.shopcarro.addToCart.CustomException;
import com.coviam.shopcarro.addToCart.dto.CartDetailsDto;
import com.coviam.shopcarro.addToCart.dto.Details;
import com.coviam.shopcarro.addToCart.model.CartDetails;
import com.coviam.shopcarro.addToCart.model.MerchantDetails;
import com.coviam.shopcarro.addToCart.repository.IAddToCartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class AddToCartImpl implements IAddToCartService {
    @Autowired
    private IAddToCartRepository iAddToCartRepository;

    /**
     * addToCart implementation is used to insert the data in the DB.
     */
    @Override
    public Boolean addToCart(String email, String merchantId, String Id, String quantity) throws CustomException {
        System.out.println(email + merchantId + Id);
        boolean create = validate(email, merchantId, Id);
        if (!create)
            return false;
        /**
         *
         * if the key (email) doesn't exists in the DB than do this or do it otherwise
         *
         * */
        RestTemplate restTemplate = new RestTemplate();
        String urlMerchantProductAvailable = "http://10.177.1.239:8083/get-available/?merchantId=" + merchantId + "&productId=" + Id + "&quantity=" + quantity;
        Boolean available = restTemplate.getForObject(urlMerchantProductAvailable, Boolean.class);

        if (!available) {
            return false;
        }

        if (!iAddToCartRepository.existsById(email)) {
            List<Details> detailsList = new ArrayList<>();
            System.out.println(Id);

            /**
             *
             *
             * Api calls for accessing merchant database for merchant name and price of the product
             *
             */
            String urlMerchantProductDetails = "http://10.177.1.239:8083/get-merchant-product/?merchantId=" + merchantId + "&productId=" + Id + "&quantity=" + quantity;
            MerchantDetails merchantDetails = restTemplate.getForObject(urlMerchantProductDetails, MerchantDetails.class);

            /**
             *
             * Api calls for accessing product database for product name and the image url
             *
             */
            String urlProductImage = "http://10.177.1.69:8080/get-product-image/?productId=" + Id;
            String imageURL = restTemplate.getForObject(urlProductImage, String.class);
            String urlProductName = "http://10.177.1.69:8080/get-product-name/?productId=" + Id;
            String productName = restTemplate.getForObject(urlProductName, String.class);
            detailsList.add(new Details(merchantId, Id, quantity, merchantDetails.getMerchantName(), productName, merchantDetails.getPrice(), imageURL, null));
            CartDetails cartDetails = new CartDetails(email, detailsList);
            iAddToCartRepository.save(cartDetails);
            return true;
        }
        Optional<CartDetails> cartDetailsOpt = iAddToCartRepository.findById(email);
        CartDetails cartDetails = cartDetailsOpt.get();
        Long totalQuantity = Long.valueOf(quantity);
        List<Details> list = cartDetails.getDetails();
        System.out.println(list);
        /**
         *  Api calls for accessing merchant database for merchant name and price of the product
         */
        String urlMerchantProductDetails = "http://10.177.1.239:8083/get-merchant-product/?merchantId=" + merchantId + "&productId=" + Id + "&quantity=" + quantity;
        MerchantDetails merchantDetails = restTemplate.getForObject(urlMerchantProductDetails, MerchantDetails.class);
        /**
         * Api calls for accessing product database for product name and the image url
         */
        String urlProductImage = "http://10.177.1.69:8080/get-product-image/?productId=" + Id;
        String imageURL = restTemplate.getForObject(urlProductImage, String.class);
        String urlProductName = "http://10.177.1.69:8080/get-product-name/?productId=" + Id;
        String productName = restTemplate.getForObject(urlProductName, String.class);

        List<Details> listOfRedundantProducts = new ArrayList<>();

        /**
         * Checking for the redundant projects.
         * */
        for (Details details:list) {
            if (merchantId.equals(details.getMerchantId()) && (Id.equals(details.getId()))) {
                totalQuantity += Long.valueOf(details.getQuantity());
                listOfRedundantProducts.add(details);
            }
        }
        /**
         * Removing all the redundant projects
         * */
        for(Details details:listOfRedundantProducts){
            list.remove(details);
        }

        //quantity = String.valueOf(totalQuantity);
        System.out.println(list);
        quantity = String.valueOf(totalQuantity);
        System.out.println(quantity);
        list.add(new Details(merchantId, Id, quantity, merchantDetails.getMerchantName(), productName, merchantDetails.getPrice(), imageURL, null));
        CartDetails cartDetails1 = new CartDetails(email, list);
        iAddToCartRepository.save(cartDetails1);
        return true;

    }

    @Override
    public boolean delItem(String email, String merchantId, String id, String quantity) throws CustomException {
        validate(email, merchantId, id);
        if (!iAddToCartRepository.existsById(email)) {
            return false;
        }
        RestTemplate restTemplate = new RestTemplate();
        String urlMerchantProductDetails = "http://10.177.1.239:8083/get-merchant-product/?merchantId=" + merchantId + "&productId=" + id + "&quantity=" + quantity;
        System.out.println(urlMerchantProductDetails);
        MerchantDetails merchantDetails = restTemplate.getForObject(urlMerchantProductDetails, MerchantDetails.class);
        String urlProductImage = "http://10.177.1.69:8080/get-product-image/?productId=" + id;
        String imageURL = restTemplate.getForObject(urlProductImage, String.class);
        String urlProductName = "http://10.177.1.69:8080/get-product-name/?productId=" + id;
        String productName = restTemplate.getForObject(urlProductName, String.class);
        Optional<CartDetails> cartDetailsOpt = iAddToCartRepository.findById(email);
        Details details = new Details(merchantId, id, quantity, merchantDetails.getMerchantName(), productName, merchantDetails.getPrice(), imageURL, null);
        CartDetails cartDetails = cartDetailsOpt.get();
        List<Details> list = new ArrayList<>();
        list = cartDetails.getDetails();
        System.out.println(details);
        list.remove(details);
        System.out.println(list);
        CartDetails cart = new CartDetails(email, list);
        iAddToCartRepository.save(cart);
        return true;
    }

    /**
     * This will be returning the details if the key exists in the DB otherwise returning null.
     **/

    @Override
    public CartDetailsDto getCart(String email) {
        //System.out.println(iAddToCartRepository.findAll());;
        if (iAddToCartRepository.existsById(email)) {
            Optional<CartDetails> cartDetailsOpt = iAddToCartRepository.findById(email);
            System.out.println(cartDetailsOpt);
            return new CartDetailsDto(cartDetailsOpt);
        } else {
            return null;
        }
    }


    @Override
    public Boolean delCart(String email) {

        if (!iAddToCartRepository.existsById(email)) {
            return false;
        }

        Optional<CartDetails> cartDetailsOpt = iAddToCartRepository.findById(email);
        CartDetails cartDetails = cartDetailsOpt.get();
        List<Details> list = new ArrayList<>();
        list = cartDetails.getDetails();
        list.removeAll(list);
        CartDetails cart = new CartDetails(email, list);
        iAddToCartRepository.save(cart);
        return true;
    }

    @Override
    public CartDetailsDto getCartAll(String email) {


        if (iAddToCartRepository.existsById(email)) {
            Optional<CartDetails> cartDetailsOpt = iAddToCartRepository.findById(email);
            return new CartDetailsDto(cartDetailsOpt);
        }
        return null;
    }

    private boolean validate(String email, String merchantId, String id) throws CustomException {
        if (email.length() == 0 || merchantId.length() == 0 || id.length() == 0) {
            throw new CustomException("false");
        }
        return true;

    }
}

