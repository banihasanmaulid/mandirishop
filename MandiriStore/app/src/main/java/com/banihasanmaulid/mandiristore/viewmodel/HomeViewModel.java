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
 * --------------------------------------------------------------------------
 * ViewModel for the Home screen.
 * <p>
 * This ViewModel provides data to the UI by interacting with the {@link ProductRepository}.
 * It exposes methods to retrieve lists of products and categories and is designed to survive
 * configuration changes, ensuring a seamless user experience.
 * <p>
 * This class is injected with an instance of {@link ProductRepository} to handle data operations.
 */
public class HomeViewModel extends ViewModel {
    private final ProductRepository productRepository;

    /**
     * Constructor with dependency injection.
     * @param productRepository Repository that provides product data
     */
    @Inject
    public HomeViewModel(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    /**
     * Retrieves the list of all products.
     * @return LiveData containing a list of products
     */
    public LiveData<List<Product>> getProducts() {
        return productRepository.getProducts();
    }

    /**
     * Retrieves the list of product categories.
     * @return LiveData containing a list of categories
     */
    public LiveData<List<String>> getCategories() {
        return productRepository.getCategories();
    }

    /**
     * Retrieves products filtered by a specific category.
     * @param category The category to filter products by
     * @return LiveData containing a list of products in the specified category
     */
    public LiveData<List<Product>> getProductsByCategory(String category) {
        return productRepository.getProductsByCategory(category);
    }
}