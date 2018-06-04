package com.coviam.shopcarro.search.service;

import com.coviam.shopcarro.search.dto.ProductDto;
import com.coviam.shopcarro.search.model.Product;
import com.coviam.shopcarro.search.repository.SearchRepository;
import com.coviam.shopcarro.search.utilities.UtilityFunctions;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

/**
 * @author sreerajr
 * @package com.coviam.shopcarro.search.service
 * @project search
 */

@Service
public class SearchServiceImpl implements SearchService {
    Logger LOGGER = Logger.getLogger(SearchServiceImpl.class.getName());

    @Autowired
    private SearchRepository searchRepository;


    @Value("${shopcarro.search.suggest.rooturl}")
    private String ROOT_URL;

    private SolrClient solrClient;
    private SolrQuery solrQuery;


    //Am never using this function
    @Override
    public List<ProductDto> search(String productName, Pageable pageable) {
        //Check the parameters for page request
        Page<Product> products = searchRepository.findByProductName(productName, pageable);
        return UtilityFunctions.productsToProductDtos(products.getContent());
    }

    @Override
    public Boolean addProductToSearchRepository(ProductDto productDto) {
        if (searchRepository.findById(productDto.getId()).isPresent()) {
            LOGGER.info("Product already exist");
            return false;
        }
        LOGGER.info("Product saved succesfully");
        searchRepository.save(UtilityFunctions.productDtoToProduct(productDto));
        return true;
    }

    @Override
    public List<ProductDto> searchGeneric(String query, Pageable pageable) {
        List<String> strings = Arrays.asList(query.split(" "));
        Page<Product> products = searchRepository.findByProductNameContainsOrDescriptionContainsOrAttributeContains(strings, strings, strings, pageable);
        return UtilityFunctions.productsToProductDtos(products.getContent());
    }


    @Override
    public List<String> autoSuggest(String query) {
        if (null == solrClient) {
            solrClient = new HttpSolrClient.Builder(ROOT_URL).build();
        }
        if(null == solrQuery) {
            solrQuery = new SolrQuery();
            solrQuery.setRequestHandler("/suggest");
            solrQuery.set("suggest", "true");
            solrQuery.set("suggest.dictionary", "mySuggester");
            solrQuery.set("wt", "json");
        }

        solrQuery.setParam("suggest.q", query);
        try {
            QueryResponse queryResponse = solrClient.query(solrQuery);
            return queryResponse.getSuggesterResponse().getSuggestedTerms().get("mySuggester");
        } catch (SolrServerException e) {
            LOGGER.warning("SolrServerException");
            e.printStackTrace();
        } catch (IOException e) {
            LOGGER.warning("IOException");
            e.printStackTrace();
        }
        LOGGER.info("Returning empty suggest");
        return null;
    }
}
