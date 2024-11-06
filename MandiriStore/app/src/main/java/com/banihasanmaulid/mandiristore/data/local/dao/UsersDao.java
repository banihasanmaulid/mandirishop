package com.banihasanmaulid.mandiristore.data.local.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.banihasanmaulid.mandiristore.model.Users;

/**
 * Copyright (c) 2024 Mandiri-Store. All rights reserved. <br>
 * Created by {@author} <b>Bani Hasan Maulid</b> on {@since} <b>11/6/2024</b>
 */
@Dao
public interface UsersDao {

    @Insert
    void insertUsers(Users users);

    @Query("SELECT * FROM cart_users WHERE username = :username AND password = :password LIMIT 1")
    Users getLogin(String username, String password);

    @Query("DELETE FROM cart_users")
    void clearCart();

    @Query("DELETE FROM cart_users WHERE id = :id")
    void deleteUsersById(int id);
}