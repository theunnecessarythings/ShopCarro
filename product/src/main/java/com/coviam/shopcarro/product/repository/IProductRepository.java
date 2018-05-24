package com.coviam.shopcarro.product.repository;

import com.coviam.shopcarro.product.model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author sandeepgupta007
 * @package com.coviam.shopcarro.product.repository
 * @project product
 */
public interface IProductRepository extends MongoRepository<Product,String> {

}
