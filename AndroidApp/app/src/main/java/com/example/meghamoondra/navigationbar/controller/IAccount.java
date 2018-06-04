package com.example.meghamoondra.navigationbar.controller;

import com.example.meghamoondra.navigationbar.model.CartProduct;
import com.example.meghamoondra.navigationbar.model.MerchantModel;
import com.example.meghamoondra.navigationbar.model.NewAccount;
import com.example.meghamoondra.navigationbar.model.OrderProduct;
import com.example.meghamoondra.navigationbar.model.Product;
import com.example.meghamoondra.navigationbar.model.SearchProduct;
import com.example.meghamoondra.navigationbar.model.Stock;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface IAccount {

    @POST("signup")
    Call<Boolean> add(@Body NewAccount newaccount);

    @GET("get-product-by-id")
    Call<Product> get(@Query("productId") String productid);

    @GET("get-products")
    Call<Product[]> getAll();

    @GET("search")
    Call<SearchProduct[]> getSearchProducts(@Query("q") String query);

    @POST("get-merchants")
    Call<Stock[]> getMerchantProducts(@Body MerchantModel merchantModel );

    @GET("add-cart")
    Call<Boolean>getCartProducts(@Query("email") String email, @Query("merchantId") String merchantId, @Query("id") String id, @Query("quantity") String quantity);

    @GET("get-history")
    Call<OrderProduct> getUserHistory(@Query("email") String email);

    @GET("del-item")
    Call<Boolean>getDeleteItem(@Query("email") String email, @Query("merchantId") String merchantId, @Query("id") String productId,@Query("quantity") String quantity);

    @GET("buy-product-by-id")
    Call<Boolean> buyItem(@Query("email") String email, @Query("merchantId") String merchantId, @Query("productId") String productId, @Query("quantity") String quantity, @Query("productName") String productName);

    @GET("get-cart")
    Call<CartProduct>getCart(@Query("email") String email);

    @GET("check-order")
    Call<CartProduct> buyAll(@Query("email") String email);

    @GET("buy-product-by-id")
    Call<Long> getUserOrder(@Query("email") String email, @Query("merchantId") String productId, @Query("productId") String merchantId, @Query("quantity") String quantity, @Query("productName") String productName);

    @GET("suggest")
    Call<List<String> > getSearchSuggestions(@Query("q") String query);
}
