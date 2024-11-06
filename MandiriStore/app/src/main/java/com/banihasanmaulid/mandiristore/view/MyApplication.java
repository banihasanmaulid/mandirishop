package com.banihasanmaulid.mandiristore.view;

import android.app.Application;

import com.banihasanmaulid.mandiristore.di.AppComponent;
import com.banihasanmaulid.mandiristore.di.AppModule;
import com.banihasanmaulid.mandiristore.di.DaggerAppComponent;

/**
 * Copyright (c) 2024 Mandiri-Store. All rights reserved. <br>
 * Created by {@author} <b>Bani Hasan Maulid</b> on {@since} <b>11/6/2024</b>
 */
public class MyApplication extends Application {
    private AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule())
                .build();
    }

    public AppComponent getAppComponent() {
        return appComponent;
    }
}
