package com.banihasanmaulid.mandiristore.di;

import com.banihasanmaulid.mandiristore.BuildConfig;
import com.banihasanmaulid.mandiristore.data.api.ApiInterface;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Copyright (c) 2024 Mandiri-Store. All rights reserved. <br>
 * Created by {@author} <b>Bani Hasan Maulid</b> on {@since} <b>11/5/2024</b>
 */
@Module
public class AppModule {

    /**
     * Provides a singleton instance of Retrofit.
     * This Retrofit instance will be used to make network requests.
     * We set the base URL for the API and specify the Gson converter for JSON serialization.
     *
     * @return Retrofit instance configured with base URL and converter factory.
     */
    @Provides
    @Singleton
    Retrofit provideRetrofit() {
        return new Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL_DEV.toUpperCase()) // Base URL for the API
                .addConverterFactory(GsonConverterFactory.create())  // Gson converter to parse JSON
                .build();
    }

    /**
     * Provides a singleton instance of ApiInterface.
     * This interface is used to define the API endpoints and make network calls.
     * Retrofit will implement this interface and provide the network methods.
     *
     * @param retrofit The Retrofit instance used to create ApiInterface.
     * @return ApiInterface instance for making API calls.
     */
    @Provides
    @Singleton
    ApiInterface provideApiInterface(Retrofit retrofit) {
        return retrofit.create(ApiInterface.class);  // Create and return the ApiInterface implementation
    }
}