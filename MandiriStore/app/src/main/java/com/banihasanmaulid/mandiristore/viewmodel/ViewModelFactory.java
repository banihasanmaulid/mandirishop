package com.banihasanmaulid.mandiristore.viewmodel;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.banihasanmaulid.mandiristore.data.repository.ProductRepository;
import com.banihasanmaulid.mandiristore.data.repository.UserRepository;

import javax.inject.Inject;

/**
 * Copyright (c) 2024 Mandiri-Store. All rights reserved. <br>
 * Created by {@author} <b>Bani Hasan Maulid</b> on {@since} <b>11/5/2024</b>
 * --------------------------------------------------------------------------
 * Factory class for creating ViewModel instances with required dependencies.
 * <p>
 * This factory handles the instantiation of various ViewModel classes (e.g., LoginViewModel,
 * HomeViewModel, RegisterViewModel) with specific repository dependencies. This approach
 * enables dependency injection, allowing each ViewModel to receive the correct repository
 * needed for its operations.
 * </p>
 */
public class ViewModelFactory implements ViewModelProvider.Factory {
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    /**
     * Constructor with required repositories for dependency injection.
     * @param userRepository Repository for user-related data
     * @param productRepository Repository for product-related data
     */
    @Inject
    public ViewModelFactory(UserRepository userRepository, ProductRepository productRepository) {
        this.userRepository = userRepository;
        this.productRepository = productRepository;
    }

    /**
     * Creates an instance of the specified ViewModel class with injected dependencies.
     * @param modelClass The class of the ViewModel to create
     * @return A new instance of the requested ViewModel
     * @throws IllegalArgumentException If the modelClass is not recognized
     */
    @Override
    @SuppressWarnings("unchecked")
    public <T extends ViewModel> T create(Class<T> modelClass) {
        if (modelClass.isAssignableFrom(LoginViewModel.class)) {
            return (T) new LoginViewModel(userRepository);
        } else if (modelClass.isAssignableFrom(HomeViewModel.class)) {
            return (T) new HomeViewModel(productRepository);
        } else if (modelClass.isAssignableFrom(RegisterViewModel.class)) {
            return (T) new RegisterViewModel(userRepository);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}