package com.coviam.shopcarro.search.utilities;

import com.coviam.shopcarro.search.dto.ProductDto;
import com.coviam.shopcarro.search.model.Product;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * Utility functions to convert from dtos to models and vice versa
 * @author sreerajr
 * @package com.coviam.shopcarro.search.utilities
 * @project search
 */
public class UtilityFunctions {
    public static ProductDto productToProductDto(Product product) {
        ProductDto productDto = new ProductDto();
        productDto.setDescription(product.getDescription());
        productDto.setId(product.getId());
        productDto.setImgUrl(product.getImgUrl());
        productDto.setMerchantId(product.getMerchantId());
        productDto.setPrice(product.getPrice());
        productDto.setProductName(product.getProductName());
        return productDto;
    }

    public static Product productDtoToProduct(ProductDto productDto) {
        Product product = new Product();
        product.setDescription(productDto.getDescription());
        product.setId(productDto.getId());
        product.setImgUrl(productDto.getImgUrl());
        product.setMerchantId(productDto.getMerchantId());
        product.setPrice(productDto.getPrice());
        product.setProductName(productDto.getProductName());
        return product;
    }

    public static List<ProductDto> productsToProductDtos(List<Product> products) {
        List<ProductDto> productDtos = new ArrayList<>();
        for(Product product : products) {
            productDtos.add(UtilityFunctions.productToProductDto(product));
        }
        return productDtos;
    }
}
