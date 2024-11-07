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
 * --------------------------------------------------------------------------
 * ProductRepository is responsible for interacting with the remote API to fetch product-related data.
 * It acts as a bridge between the API and the UI layer (ViewModel).
 * This class provides methods for retrieving product lists, product categories, and products filtered by category.
 * Data is returned as LiveData to allow the UI to observe changes.
 */
public class ProductRepository {
    // Instance of the ApiInterface to interact with the remote API
    private final ApiInterface apiInterface;

    /**
     * Constructor to inject the ApiInterface dependency.
     *
     * @param apiInterface The ApiInterface to communicate with the remote server.
     */
    @Inject
    public ProductRepository(ApiInterface apiInterface) {
        this.apiInterface = apiInterface;
    }

    /**
     * Fetches the list of products from the remote API.
     * The result is returned as LiveData so that the UI can observe it and react to changes.
     *
     * @return LiveData containing a list of products.
     */
    public LiveData<List<Product>> getProducts() {
        MutableLiveData<List<Product>> data = new MutableLiveData<>();
        // Initiating the API request to fetch products
        apiInterface.getProducts().enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                // If the response is successful and contains a valid body, post the data
                if (response.isSuccessful() && response.body() != null) {
                    data.postValue(response.body());
                } else {
                    // If the response is not successful, post null to indicate no data
                    data.postValue(null);
                }
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                // If the request fails, post null and print the stack trace
                data.postValue(null);
                t.printStackTrace();
            }
        });

        return data;
    }

    /**
     * Fetches the list of product categories from the remote API.
     * The result is returned as LiveData so that the UI can observe it and react to changes.
     *
     * @return LiveData containing a list of product categories.
     */
    public LiveData<List<String>> getCategories() {
        MutableLiveData<List<String>> data = new MutableLiveData<>();
        // Initiating the API request to fetch categories
        apiInterface.getCategories().enqueue(new Callback<List<String>>() {
            @Override
            public void onResponse(Call<List<String>> call, Response<List<String>> response) {
                // If the response is successful and contains a valid body, post the data
                if (response.isSuccessful() && response.body() != null) {
                    data.postValue(response.body());
                } else {
                    // If the response is not successful, post null to indicate no data
                    data.postValue(null);
                }
            }

            @Override
            public void onFailure(Call<List<String>> call, Throwable t) {
                // If the request fails, post null and print the stack trace
                data.postValue(null);
                t.printStackTrace();
            }
        });

        return data;
    }

    /**
     * Fetches the list of products filtered by category from the remote API.
     * The result is returned as LiveData so that the UI can observe it and react to changes.
     *
     * @param category The category to filter products by.
     * @return LiveData containing a list of products in the specified category.
     */
    public LiveData<List<Product>> getProductsByCategory(String category) {
        MutableLiveData<List<Product>> data = new MutableLiveData<>();
        // Initiating the API request to fetch products filtered by category
        apiInterface.getProductsByCategory(category).enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                // If the response is successful and contains a valid body, post the data
                if (response.isSuccessful() && response.body() != null) {
                    data.postValue(response.body());
                } else {
                    // If the response is not successful, post null to indicate no data
                    data.postValue(null);
                }
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                // If the request fails, post null and print the stack trace
                data.postValue(null);
                t.printStackTrace();
            }
        });

        return data;
    }
}
