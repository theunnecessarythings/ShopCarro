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
public interface SearchRepository extends SolrCrudRepository<Product, String> {

    /**
     *
     * Functionalities supported by search
     *
     * Pageable results (user can request page number, size page)
     * Sort by any field which is present in the search results in ascending or descending order
     * The search query is case insensitive
     * request format   /search?q=query?size=20&page=0&sort=price,desc
     * Autosuggest
     *
     * @param productName
     * @param pageable
     * @return
     */


    public Page<Product> findByProductName(String productName, Pageable pageable);
    public Page<Product> findByProductNameContainsOrDescriptionContainsOrAttributeContains(List<String> productName, List<String> description, List<String> attribute, Pageable pageable);


}
