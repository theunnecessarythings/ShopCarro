package com.coviam.shopcarro.product.service;

import com.coviam.shopcarro.product.dto.ProductDto;
import com.coviam.shopcarro.product.model.Product;
import com.coviam.shopcarro.product.repository.IProductRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;


@Service
public class ProductService implements IProductService {

    @Autowired
    private IProductRepository iProductRepository;

    /**
     *  Create: For adding products in the mongoDB database.
     *
     *  Id's will be fixed and will be considered as the product Id's for mongoDB.
     *
     * */


    @Override
    public ProductDto create(ProductDto productDto) {
        Product product = new Product();
        BeanUtils.copyProperties(productDto,product);
        iProductRepository.save(product);
        return productDto;
    }


    /**
     *
     * getAllProducts() will be returning the list of all product available in database.
     *
     *  later we can pass the userCharacteristics to this functions to filter out the products details for particular users to display
     *
     * */
    @Override
    public List<ProductDto> getAllProducts(){
        List<Product> list = new ArrayList<>();
        list = iProductRepository.findAll();
        //System.out.println("Services model " + list.size());
        List<ProductDto> listDto = new ArrayList<>();

        for(Product products:list){
            ProductDto productDto = new ProductDto(products);
            listDto.add(productDto);
        }
        return listDto;
    }

    /**
     *
     * Get a particular product with a particular Product Id
     *
     * */
    @Override
    public  ProductDto get(String id) throws  NoSuchElementException{
        Optional<Product> product = Optional.of(new Product());
        if(!iProductRepository.existsById(id)){
            throw new NoSuchElementException("This is not a valid Id");
        }

        product = iProductRepository.findById(id);

        ProductDto productDto = new ProductDto();

        /**
         *  This is for converting the optional response to ProductDto
         * */

        productDto.setId(product.get().getId());
        productDto.setAttribute(product.get().getAttribute());
        productDto.setDescription(product.get().getDescription());
        productDto.setUsp(product.get().getUsp());
        productDto.setMerchantId(product.get().getMerchantId());
        productDto.setImgUrl(product.get().getImgUrl());
        productDto.setPrice(product.get().getPrice());

        return productDto;
    }
}
