package com.coviam.shopcarro.search.controller;

import com.coviam.shopcarro.search.dto.ProductDto;
import com.coviam.shopcarro.search.exceptions.NoItemsMatchingDescriptionException;
import com.coviam.shopcarro.search.service.ISearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author sreerajr
 * @package com.coviam.shopcarro.search.controller
 * @project search
 */

@RestController
public class SearchController {

    @Autowired
    private ISearchService iSearchService;

    //Don't do this, this doesn't make much sense now
    @RequestMapping(value = "/search-specific")
    public ResponseEntity<List<ProductDto> > search(@RequestParam(value = "q") String productName, Pageable pageable) throws NoItemsMatchingDescriptionException {
        List<ProductDto> productDtos = iSearchService.search(productName.toLowerCase(), pageable);
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
        List<ProductDto> productDtos = iSearchService.searchGeneric(query.toLowerCase(), pageable);
        if(null == productDtos) {
            //This never happens actually ... great
            throw new NoItemsMatchingDescriptionException("Empty Search Results");
        }
        System.out.println(productDtos);
        return new ResponseEntity<>(productDtos, HttpStatus.OK);
    }

    /**
     *
     * For adding product catalog into Solr data repository
     * If product cannot be added it will return a false response and true on success
     */
    @RequestMapping(value = "/add-product-for-search", method = RequestMethod.POST)
    public ResponseEntity<Boolean> addProductToSearchRepository(@RequestBody ProductDto productDto) {
        if(iSearchService.addProductToSearchRepository(productDto))
            return new ResponseEntity<>(true, HttpStatus.CREATED);
        return new ResponseEntity<> (false, HttpStatus.OK);
    }
}
