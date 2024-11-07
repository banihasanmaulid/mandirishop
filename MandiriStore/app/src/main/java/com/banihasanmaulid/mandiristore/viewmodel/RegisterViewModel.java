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
 * --------------------------------------------------------------------------
 * ViewModel for handling user registration operations.
 * <p>
 * This ViewModel interacts with the {@link UserRepository} to manage user registration requests.
 * It exposes a method to initiate the registration process and observes the registration status
 * through a {@link LiveData} object. The data is retained during configuration changes.
 * <p>
 * This class is injected with an instance of {@link UserRepository} to handle user-related data.
 */
public class RegisterViewModel extends ViewModel {
    private final UserRepository userRepository;

    /**
     * Constructor with dependency injection.
     * @param userRepository Repository that manages user-related data and registration functionality
     */
    @Inject
    public RegisterViewModel(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Initiates the registration process for a new user.
     * @param request The UserRequest object containing registration details (e.g., username, password)
     * @return LiveData containing the registration response, including success status and user details
     */
    public LiveData<UserResponse> register(UserRequest request) {
        return userRepository.register(request);
    }
}