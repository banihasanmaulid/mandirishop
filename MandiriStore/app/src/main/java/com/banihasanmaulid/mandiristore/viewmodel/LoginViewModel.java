package com.banihasanmaulid.mandiristore.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.banihasanmaulid.mandiristore.data.api.request.UserRequest;
import com.banihasanmaulid.mandiristore.data.api.response.LoginResponse;
import com.banihasanmaulid.mandiristore.data.api.response.UserResponse;
import com.banihasanmaulid.mandiristore.data.repository.UserRepository;

import javax.inject.Inject;

/**
 * Copyright (c) 2024 Mandiri-Store. All rights reserved. <br>
 * Created by {@author} <b>Bani Hasan Maulid</b> on {@since} <b>11/5/2024</b>
 * --------------------------------------------------------------------------
 * ViewModel for handling user login operations.
 * <p>
 * This ViewModel communicates with the {@link UserRepository} to manage user login requests.
 * It exposes a method to initiate the login process, which observes the login status
 * through a {@link LiveData} object. The data is retained during configuration changes.
 * <p>
 * This class is injected with an instance of {@link UserRepository} to handle authentication data.
 */
public class LoginViewModel extends ViewModel {
    private final UserRepository userRepository;

    /**
     * Constructor with dependency injection.
     * @param userRepository Repository that manages user authentication and login data
     */
    @Inject
    public LoginViewModel(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Initiates the login process for the given username and password.
     * @param username The username entered by the user
     * @param password The password entered by the user
     * @return LiveData containing the login response, which includes success status and user data
     */
    public LiveData<LoginResponse> login(String username, String password) {
        return userRepository.login(username, password);
    }
}