package com.coviam.shopcarro.addToCart.repository;

import com.coviam.shopcarro.addToCart.model.CartDetails;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IAddToCartRepository extends MongoRepository<CartDetails,String> {

}
