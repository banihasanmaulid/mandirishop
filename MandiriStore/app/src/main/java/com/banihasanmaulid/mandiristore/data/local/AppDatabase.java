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
 * --------------------------------------------------------------------------
 * AppDatabase is the main database class for the application.
 * It defines the database schema by including the entities (Product and Users)
 * and provides access to DAOs (Data Access Objects) for interacting with the database.
 *
 * The database version is 3, and Room will handle migrations automatically. If a migration is not defined,
 * it will fall back to destructive migration, which deletes all existing data in the database.
 */
@Database(entities = {Product.class, Users.class}, version = 3)
public abstract class AppDatabase extends RoomDatabase {

    // Singleton instance of the database
    private static AppDatabase instance;

    /**
     * Provides access to the ProductDao, which allows interactions with the product-related database operations.
     *
     * @return The ProductDao for accessing product data.
     */
    public abstract ProductDao productDao();

    /**
     * Provides access to the UsersDao, which allows interactions with the user-related database operations.
     *
     * @return The UsersDao for accessing user data.
     */
    public abstract UsersDao usersDao();

    /**
     * Returns the singleton instance of the AppDatabase.
     * This method ensures that only one instance of the database is created and reused throughout the application.
     * The database instance is created lazily, i.e., only when it is needed.
     *
     * @param context The context to access the application-level resources.
     * @return The singleton instance of the AppDatabase.
     */
    public static synchronized AppDatabase getInstance(Context context) {
        if (instance == null) {
            // Creates the database if it does not exist and provides access to it
            instance = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, "cart_database")
                    // If there is a version mismatch, the database will be wiped and rebuilt
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }
}
