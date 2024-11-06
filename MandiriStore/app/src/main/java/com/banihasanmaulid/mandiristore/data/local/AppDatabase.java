package com.banihasanmaulid.mandiristore.data.local;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.banihasanmaulid.mandiristore.data.local.dao.ProductDao;
import com.banihasanmaulid.mandiristore.data.local.dao.UsersDao;
import com.banihasanmaulid.mandiristore.model.Product;
import com.banihasanmaulid.mandiristore.model.Users;

/**
 * Copyright (c) 2024 Mandiri-Store. All rights reserved. <br>
 * Created by {@author} <b>Bani Hasan Maulid</b> on {@since} <b>11/6/2024</b>
 */
@Database(entities = {Product.class, Users.class}, version = 3)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase instance;

    public abstract ProductDao productDao();
    public abstract UsersDao usersDao();

    public static synchronized AppDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, "cart_database")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }
}
