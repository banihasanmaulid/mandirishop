package com.banihasanmaulid.mandiristore.data.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.banihasanmaulid.mandiristore.data.api.ApiInterface;
import com.banihasanmaulid.mandiristore.data.api.request.LoginRequest;
import com.banihasanmaulid.mandiristore.data.api.request.UserRequest;
import com.banihasanmaulid.mandiristore.data.api.response.LoginResponse;
import com.banihasanmaulid.mandiristore.data.api.response.UserResponse;

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

        apiInterface.loginUser(request).enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    data.postValue(response.body());
                } else {
                    data.postValue(null);
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                data.postValue(null);
                t.printStackTrace();
            }
        });

        return data;
    }

    public LiveData<UserResponse> register(UserRequest request) {
        MutableLiveData<UserResponse> data = new MutableLiveData<>();
        apiInterface.registerUser(request).enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    data.postValue(response.body());
                } else {
                    data.postValue(null);
                }
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                data.postValue(null);
                t.printStackTrace();
            }
        });

        return data;
    }
}
