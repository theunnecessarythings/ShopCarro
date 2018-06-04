package com.coviam.shopcarro.search.service;

import com.coviam.shopcarro.search.dto.ProductDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @author sreerajr
 * @package com.coviam.shopcarro.search.service
 * @project search
 */
public interface SearchService {
    List<ProductDto> search(String productName, Pageable pageable);

    Boolean addProductToSearchRepository(ProductDto productDto);

    List<ProductDto> searchGeneric(String query, Pageable pageable);

    List<String> autoSuggest(String query);

}
