package com.example.meghamoondra.navigationbar.controller;

import com.example.meghamoondra.navigationbar.model.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface IUser {

    @POST("login/")
    Call<Boolean> doLogin(@Body User user);

}
