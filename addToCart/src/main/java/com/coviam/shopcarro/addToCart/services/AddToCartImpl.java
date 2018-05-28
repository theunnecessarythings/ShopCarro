package com.coviam.shopcarro.addToCart.services;

import com.coviam.shopcarro.addToCart.CustomException;
import com.coviam.shopcarro.addToCart.dto.CartDetailsDto;
import com.coviam.shopcarro.addToCart.dto.Details;
import com.coviam.shopcarro.addToCart.model.CartDetails;
import com.coviam.shopcarro.addToCart.repository.IAddToCartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public Boolean addToCart(String email, String merchantId, String Id) throws CustomException {
        System.out.println(email + merchantId + Id);
        boolean create = validateUser(email, merchantId, Id);
        if (!create)
            return false;

        /**
         *
         * if the key (email) doesn't exists in the DB than do this or do it otherwise
         *
         * */

        if (!iAddToCartRepository.existsById(email)) {
            List<Details> detailsList = new ArrayList<>();
            System.out.println(Id);
            detailsList.add(new Details(merchantId, Id));
            CartDetails cartDetails = new CartDetails(email, detailsList);
            iAddToCartRepository.save(cartDetails);
            return true;
        }

        Optional<CartDetails> cartDetailsOpt = iAddToCartRepository.findById(email);
        CartDetails cartDetails = cartDetailsOpt.get();
        List<Details> list = new ArrayList<>();
        list = cartDetails.getDetails();
        list.add(new Details(merchantId, Id));
        CartDetails cart = new CartDetails(email, list);
        iAddToCartRepository.save(cart);
        return true;

    }

    @Override
    public boolean delItem(String email, String merchantId, String id) throws CustomException {
        validateUser(email, merchantId, id);
        if (!iAddToCartRepository.existsById(email)) {
            return false;
        }
        Optional<CartDetails> cartDetailsOpt = iAddToCartRepository.findById(email);
        Details details = new Details(merchantId, id);
        CartDetails cartDetails = cartDetailsOpt.get();
        List<Details> list = new ArrayList<>();
        list = cartDetails.getDetails();
        list.remove(details);
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

    private boolean validateUser(String email, String merchantId, String id) throws CustomException {
        if (email.length() == 0 || merchantId.length() == 0 || id.length() == 0) {
            System.out.println("hey");
            throw new CustomException("false");
        }
        return true;

    }
}

