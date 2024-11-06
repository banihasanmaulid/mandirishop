package com.banihasanmaulid.mandiristore.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.banihasanmaulid.mandiristore.model.Product;
import com.banihasanmaulid.mandiristore.data.repository.ProductRepository;

import java.util.List;

import javax.inject.Inject;

/**
 * Copyright (c) 2024 Mandiri-Store. All rights reserved. <br>
 * Created by {@author} <b>Bani Hasan Maulid</b> on {@since} <b>11/5/2024</b>
 */
public class HomeViewModel extends ViewModel {
    private final ProductRepository productRepository;

    @Inject
    public HomeViewModel(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public LiveData<List<Product>> getProducts() {
        return productRepository.getProducts();
    }

    public LiveData<List<String>> getCategories() {
        return productRepository.getCategories();
    }

    public LiveData<List<Product>> getProductsByCategory(String category) {
        return productRepository.getProductsByCategory(category);
    }
}