package com.coviam.shopcarro.search.repository;

import com.coviam.shopcarro.search.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.solr.repository.SolrCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * @author sreerajr
 * @package com.coviam.shopcarro.search.repository
 * @project search
 */

@Repository
public interface ISearchRepository extends SolrCrudRepository<Product, String> {
    public Page<Product> findByProductName(String productName, Pageable pageable);
    //public Page<Product> findByProductNameContainsOrDescriptionContains(String productName, String description, Pageable pageable);
    public Page<Product> findByProductNameContainsOrDescriptionContains(List<String> productName, List<String> description, Pageable pageable);

    //Add Sort Options Also Later
}
