package com.banihasanmaulid.mandiristore.data.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.banihasanmaulid.mandiristore.data.api.ApiInterface;
import com.banihasanmaulid.mandiristore.model.Product;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Copyright (c) 2024 Mandiri-Store. All rights reserved. <br>
 * Created by {@author} <b>Bani Hasan Maulid</b> on {@since} <b>11/5/2024</b>
 */
public class ProductRepository {
    private final ApiInterface apiInterface;

    @Inject
    public ProductRepository(ApiInterface apiInterface) {
        this.apiInterface = apiInterface;
    }

    public LiveData<List<Product>> getProducts() {
        MutableLiveData<List<Product>> data = new MutableLiveData<>();
        apiInterface.getProducts().enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    data.postValue(response.body());
                } else {
                    data.postValue(null);
                }
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                data.postValue(null);
                t.printStackTrace();
            }
        });

        return data;
    }

    public LiveData<List<String>> getCategories() {
        MutableLiveData<List<String>> data = new MutableLiveData<>();
        apiInterface.getCategories().enqueue(new Callback<List<String>>() {
            @Override
            public void onResponse(Call<List<String>> call, Response<List<String>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    data.postValue(response.body());
                } else {
                    data.postValue(null);
                }
            }

            @Override
            public void onFailure(Call<List<String>> call, Throwable t) {
                data.postValue(null);
                t.printStackTrace();
            }
        });

        return data;
    }

    public LiveData<List<Product>> getProductsByCategory(String category) {
        MutableLiveData<List<Product>> data = new MutableLiveData<>();
        apiInterface.getProductsByCategory(category).enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    data.postValue(response.body());
                } else {
                    data.postValue(null);
                }
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                data.postValue(null);
                t.printStackTrace();
            }
        });

        return data;
    }
}
