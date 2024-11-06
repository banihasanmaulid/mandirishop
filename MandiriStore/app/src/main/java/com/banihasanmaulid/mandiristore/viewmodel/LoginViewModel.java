package com.banihasanmaulid.mandiristore.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.banihasanmaulid.mandiristore.data.api.LoginResponse;
import com.banihasanmaulid.mandiristore.model.Product;
import com.banihasanmaulid.mandiristore.data.repository.UserRepository;

import java.util.List;

import javax.inject.Inject;

/**
 * Copyright (c) 2024 Mandiri-Store. All rights reserved. <br>
 * Created by {@author} <b>Bani Hasan Maulid</b> on {@since} <b>11/5/2024</b>
 */
public class LoginViewModel extends ViewModel {
    private final UserRepository userRepository;

    @Inject
    public LoginViewModel(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public LiveData<LoginResponse> login(String username, String password) {
        return userRepository.login(username, password);
    }

    public LiveData<List<Product>> getProducts() {
        return userRepository.getProducts();
    }
}