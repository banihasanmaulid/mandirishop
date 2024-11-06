package com.banihasanmaulid.mandiristore.data.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.banihasanmaulid.mandiristore.data.api.ApiInterface;
import com.banihasanmaulid.mandiristore.data.api.LoginRequest;
import com.banihasanmaulid.mandiristore.data.api.LoginResponse;
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
public class UserRepository {
    private final ApiInterface apiInterface;

    @Inject
    public UserRepository(ApiInterface apiInterface) {
        this.apiInterface = apiInterface;
    }

    public LiveData<LoginResponse> login(String username, String password) {
        MutableLiveData<LoginResponse> data = new MutableLiveData<>();
        LoginRequest request = new LoginRequest(username, password);

        apiInterface.login2(request).enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    data.postValue(response.body());
                } else {
                    // Bisa menambahkan penanganan error di sini
                    data.postValue(null);
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                data.postValue(null);
                // Tambahkan log untuk memudahkan debug jika diperlukan
                t.printStackTrace();
            }
        });

        return data;
    }

    public LiveData<List<Product>> getProducts() {
        MutableLiveData<List<Product>> data = new MutableLiveData<>();

        List<Product> products = new ArrayList<>();
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
}
