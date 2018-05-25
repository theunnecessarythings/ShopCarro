package com.coviam.shopcarro.search.repository;

import com.coviam.shopcarro.search.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.solr.repository.SolrCrudRepository;
import org.springframework.stereotype.Repository;


/**
 * @author sreerajr
 * @package com.coviam.shopcarro.search.repository
 * @project search
 */

@Repository
public interface ISearchRepository extends SolrCrudRepository<Product, String> {
    public Page<Product> findByProductName(String productName, Pageable pageable);
}
