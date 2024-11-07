package com.banihasanmaulid.mandiristore.di;

import com.banihasanmaulid.mandiristore.view.HomeActivity;
import com.banihasanmaulid.mandiristore.view.LoginActivity;
import com.banihasanmaulid.mandiristore.view.RegisterActivity;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Copyright (c) 2024 Mandiri-Store. All rights reserved. <br>
 * Created by {@author} <b>Bani Hasan Maulid</b> on {@since} <b>11/5/2024</b>
 * --------------------------------------------------------------------------
 * AppComponent is the Dagger component that serves as the bridge for dependency injection.
 * It is used to inject dependencies into activities such as LoginActivity, HomeActivity, and RegisterActivity.
 *
 * The @Singleton annotation ensures that only one instance of the component is used throughout the app,
 * providing a centralized way of managing dependencies.
 *
 * This component depends on AppModule for providing the necessary dependencies.
 * Key Activities Injected:
 * - LoginActivity
 * - HomeActivity
 * - RegisterActivity
 */
@Singleton
@Component(modules = {AppModule.class})
public interface AppComponent {
    // Inject dependencies into LoginActivity
    void inject(LoginActivity loginActivity);

    // Inject dependencies into HomeActivity
    void inject(HomeActivity homeActivity);

    // Inject dependencies into RegisterActivity
    void inject(RegisterActivity registerActivity);
}