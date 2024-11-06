package com.banihasanmaulid.mandiristore.di;

import com.banihasanmaulid.mandiristore.view.HomeActivity;
import com.banihasanmaulid.mandiristore.view.LoginActivity;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Copyright (c) 2024 Mandiri-Store. All rights reserved. <br>
 * Created by {@author} <b>Bani Hasan Maulid</b> on {@since} <b>11/5/2024</b>
 */
@Singleton
@Component(modules = {AppModule.class})
public interface AppComponent {
    void inject(LoginActivity loginActivity);
    void inject(HomeActivity loginActivity);
}