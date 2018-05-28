package com.coviam.shopcarro.search.service;

import com.coviam.shopcarro.search.dto.ProductDto;
import com.coviam.shopcarro.search.model.Product;
import com.coviam.shopcarro.search.repository.ISearchRepository;
import com.coviam.shopcarro.search.utilities.UtilityFunctions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

/**
 * @author sreerajr
 * @package com.coviam.shopcarro.search.service
 * @project search
 */

@Service
public class SearchService implements ISearchService {

    @Autowired
    private ISearchRepository iSearchRepository;

    //Never  j,
    @Override
    public List<ProductDto> search(String productName, Pageable pageable) {
        //Check the parameters for page request
        Page<Product> products = iSearchRepository.findByProductName(productName, pageable);
        return UtilityFunctions.productsToProductDtos(products.getContent());
    }

    @Override
    public Boolean addProductToSearchRepository(ProductDto productDto) {
        if(iSearchRepository.findById(productDto.getId()).isPresent())
            return false;
        iSearchRepository.save(UtilityFunctions.productDtoToProduct(productDto));
        return true;
    }

    @Override
    public List<ProductDto> searchGeneric(String query, Pageable pageable) {
        List<String> strings = Arrays.asList(query.split(" "));
        Page<Product> products = iSearchRepository.findByProductNameContainsOrDescriptionContains(strings, strings, pageable);
        return UtilityFunctions.productsToProductDtos(products.getContent());
    }
}
