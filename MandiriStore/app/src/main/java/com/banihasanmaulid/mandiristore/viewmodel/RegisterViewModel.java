package com.banihasanmaulid.mandiristore.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.banihasanmaulid.mandiristore.data.api.request.UserRequest;
import com.banihasanmaulid.mandiristore.data.api.response.UserResponse;
import com.banihasanmaulid.mandiristore.data.repository.UserRepository;

import javax.inject.Inject;

/**
 * Copyright (c) 2024 Mandiri-Store. All rights reserved. <br>
 * Created by {@author} <b>Bani Hasan Maulid</b> on {@since} <b>11/6/2024</b>
 */
public class RegisterViewModel extends ViewModel {
    private final UserRepository userRepository;

    @Inject
    public RegisterViewModel(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public LiveData<UserResponse> register(UserRequest request) {
        return userRepository.register(request);
    }
}