package com.coviam.shopcarro.search.controller;

import com.coviam.shopcarro.search.dto.ProductDto;
import com.coviam.shopcarro.search.exceptions.NoItemsMatchingDescriptionException;
import com.coviam.shopcarro.search.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Logger;

/**
 * @author sreerajr
 * @package com.coviam.shopcarro.search.controller
 * @project search
 */

@RestController
public class SearchController {

    private final static Logger LOGGER = Logger.getLogger(SearchController.class.getName());

    @Autowired
    private SearchService searchService;

    @RequestMapping(value = "/search-specific")
    public ResponseEntity<List<ProductDto> > search(@RequestParam(value = "q") String productName, Pageable pageable) throws NoItemsMatchingDescriptionException {
        List<ProductDto> productDtos = searchService.search(productName.toLowerCase(), pageable);
        if(null == productDtos) {
            //This never happens actually ... great
            throw new NoItemsMatchingDescriptionException("Empty Search Results");
        }
        return new ResponseEntity<>(productDtos, HttpStatus.OK);
    }

    /**
     * Search will return if the query is contained in either product name or description
     * Make the change such that first it will search by name then it will search by description so as to maintain
     * the preference
     * @param query
     * @param pageable
     * @return
     * @throws NoItemsMatchingDescriptionException
     */
    @RequestMapping(value = "/search")
    public ResponseEntity<List<ProductDto> > searchGeneric(@RequestParam(value = "q") String query, Pageable pageable) throws NoItemsMatchingDescriptionException {
        LOGGER.info("search query : " + query);
        List<ProductDto> productDtos = searchService.searchGeneric(query.toLowerCase(), pageable);
        if(null == productDtos) {
            //This never happens actually ... great
            LOGGER.warning("Empty Search Results");
            throw new NoItemsMatchingDescriptionException("Empty Search Results");
        }
        LOGGER.info("Returning search results");
        return new ResponseEntity<>(productDtos, HttpStatus.OK);
    }

    /**
     *
     * For adding product catalog into Solr data repository
     * If product cannot be added it will return a false response and true on success
     */
    @RequestMapping(value = "/add-product-for-search", method = RequestMethod.POST)
    public ResponseEntity<Boolean> addProductToSearchRepository(@RequestBody ProductDto productDto) {
        LOGGER.info("adding product to search repository " + productDto);
        if(searchService.addProductToSearchRepository(productDto)) {
            LOGGER.info("Product added");
            return new ResponseEntity<>(true, HttpStatus.CREATED);
        }
        LOGGER.info("Product Not Added");
        return new ResponseEntity<> (false, HttpStatus.OK);
    }

    /**
     * http://localhost:8983/solr/shopcarro/suggest?suggest=true&suggest.dictionary=mySuggester&wt=json&suggest.q=
     * @param query
     * @return
     */


    @RequestMapping(value = "/suggest")
    public ResponseEntity<?> autoSuggestKeyword(@RequestParam(value = "q") String query) {
        LOGGER.info("suggest query : " + query);
        List<String> suggestions = searchService.autoSuggest(query);
        if(null == suggestions) {
            LOGGER.info("empty suggestions");
            return new ResponseEntity<>(false, HttpStatus.OK);
        }
        LOGGER.info("suggestions returned");
        return new ResponseEntity<>(suggestions, HttpStatus.OK);
    }
}
