package com.example.meghamoondra.navigationbar.controller;

import android.app.Application;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AppController extends Application {

    private static Retrofit retrofitAuthentication = null;
    private static Retrofit retrofitSearch = null;
    private static Retrofit retrofitProduct = null;
    private static Retrofit retrofitMerchant = null;
    private static Retrofit retrofitOrder = null;
    private static Retrofit retrofitCart = null;
    private static Retrofit retrofitAddCart = null;

    public static AppController mInstance;


    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
    }

    public static AppController getInstance() {
        return mInstance;
    }

    public Retrofit getClientAuthentication(String url) {
        if (null == retrofitAuthentication) {
            OkHttpClient client = new OkHttpClient.Builder().build();
            retrofitAuthentication = new Retrofit.Builder()
                    .baseUrl(url)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client)
                    .build();
        }
        return retrofitAuthentication;
    }

    public Retrofit getClientProduct(String url) {
        if (null == retrofitProduct) {
            OkHttpClient client = new OkHttpClient.Builder().build();
            retrofitProduct = new Retrofit.Builder()
                    .baseUrl(url)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client)
                    .build();
        }
        return retrofitProduct;
    }



    public Retrofit getClientMerchant(String url) {
        if (null == retrofitMerchant) {
            OkHttpClient client = new OkHttpClient.Builder().build();
            retrofitMerchant = new Retrofit.Builder()
                    .baseUrl(url)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client)
                    .build();
        }
        return retrofitMerchant;
    }

    public Retrofit getClientSearch(String url) {
        if (null == retrofitSearch) {
            OkHttpClient client = new OkHttpClient.Builder().build();
            retrofitSearch = new Retrofit.Builder()
                    .baseUrl(url)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client)
                    .build();
        }
        return retrofitSearch;
    }

    public Retrofit getClientCart(String url) {
        if (null == retrofitCart) {
            OkHttpClient client = new OkHttpClient.Builder().build();
            retrofitCart = new Retrofit.Builder()
                    .baseUrl(url)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client)
                    .build();
        }
        return retrofitCart;
    }

    public Retrofit getClientOrder(String url) {
        if (null == retrofitOrder) {
            OkHttpClient client = new OkHttpClient.Builder().build();
            retrofitOrder = new Retrofit.Builder()
                    .baseUrl(url)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client)
                    .build();
        }
        return retrofitOrder;
    }

    public Retrofit getClientAddCart(String url) {
        if (null == retrofitAddCart) {
            OkHttpClient client = new OkHttpClient.Builder().build();
            retrofitAddCart = new Retrofit.Builder()
                    .baseUrl(url)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client)
                    .build();
        }
        return retrofitAddCart;
    }


}
