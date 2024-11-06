package com.banihasanmaulid.mandiristore.data.api;

import com.banihasanmaulid.mandiristore.data.api.request.LoginRequest;
import com.banihasanmaulid.mandiristore.data.api.request.UserRequest;
import com.banihasanmaulid.mandiristore.data.api.response.LoginResponse;
import com.banihasanmaulid.mandiristore.data.api.response.UserResponse;
import com.banihasanmaulid.mandiristore.model.Product;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Copyright (c) 2024 Mandiri-Store. All rights reserved. <br>
 * Created by {@author} <b>Bani Hasan Maulid</b> on {@since} <b>11/5/2024</b>
 */
public interface ApiInterface {

    @POST("login")
    Call<LoginResponse> login(@Body LoginRequest loginRequest);

    @GET("products")
    Call<List<Product>> getProducts();

    @GET("products/categories")
    Call<List<String>> getCategories();

    @GET("products/category/{category}")
    Call<List<Product>> getProductsByCategory(@Path("category") String category);

    // Endpoint untuk mengambil detail produk berdasarkan ID
    @GET("products/{id}")
    Call<Product> getProductById(@Path("id") int id);

    @POST("users")
    Call<UserResponse> registerUser(@Body UserRequest userRequest);

    @FormUrlEncoded
    @POST("auth/login")
    Call<LoginResponse> loginUser(@Body LoginRequest loginRequest);
}
