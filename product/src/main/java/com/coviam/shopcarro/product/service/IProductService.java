package com.coviam.shopcarro.product.service;

import com.coviam.shopcarro.product.dto.ProductDto;
import com.coviam.shopcarro.product.model.Product;

import java.util.List;

/**
 * @author sreerajr
 * @package com.coviam.shopcarro.product.service
 * @project product
 */
public interface IProductService {

    public ProductDto create(ProductDto productDto);
    public List<ProductDto> getAllProducts();
    public  ProductDto get(String id);
    public String getProductNameById(String id);
}
