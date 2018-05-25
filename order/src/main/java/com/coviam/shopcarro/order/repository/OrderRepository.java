package com.coviam.shopcarro.order.repository;

import com.coviam.shopcarro.order.details.Details;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface OrderRepository extends MongoRepository<List<Details>,String> {
}
