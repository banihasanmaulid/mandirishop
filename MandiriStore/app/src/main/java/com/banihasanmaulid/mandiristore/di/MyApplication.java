package com.banihasanmaulid.mandiristore.di;

import android.app.Application;

/**
 * Copyright (c) 2024 Mandiri-Store. All rights reserved. <br>
 * Created by {@author} <b>Bani Hasan Maulid</b> on {@since} <b>11/6/2024</b>
 * --------------------------------------------------------------------------
 * Application class for initializing Dagger dependencies.
 * <p>
 * This class is part of the Dependency Injection (DI) setup for Mandiri Store.
 * It extends {@link Application} to ensure that the Dagger AppComponent
 * is instantiated when the application is created.
 * <p>
 */
public class MyApplication extends Application {
    private AppComponent appComponent;

    /**
     * Called when the application is starting, before any other application objects
     * have been created. Initializes the Dagger AppComponent.
     */
    @Override
    public void onCreate() {
        super.onCreate();
        appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule())
                .build();
    }

    /**
     * Provides the Dagger AppComponent instance for dependency injection.
     * @return The AppComponent instance
     */
    public AppComponent getAppComponent() {
        return appComponent;
    }
}
