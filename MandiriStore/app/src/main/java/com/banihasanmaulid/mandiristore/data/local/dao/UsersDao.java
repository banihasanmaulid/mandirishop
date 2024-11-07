package com.banihasanmaulid.mandiristore.data.local.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.banihasanmaulid.mandiristore.model.Users;

/**
 * Copyright (c) 2024 Mandiri-Store. All rights reserved. <br>
 * Created by {@author} <b>Bani Hasan Maulid</b> on {@since} <b>11/6/2024</b>
 * --------------------------------------------------------------------------
 * UsersDao interface serves as the Data Access Object (DAO) for the Users entity.
 * It provides methods to interact with the database (Room) for performing CRUD operations on the cart_users table.
 * This interface includes methods for inserting, querying, and deleting users from the cart.
 * Each method corresponds to a specific SQL query that Room will automatically implement.
 */
@Dao
public interface UsersDao {

    /**
     * Inserts a new user into the cart_users table.
     *
     * @param users The user to be inserted into the table.
     */
    @Insert
    void insertUsers(Users users);

    /**
     * Retrieves a user from the cart_users table by matching the username and password.
     * This method ensures that only one user is returned by using "LIMIT 1".
     *
     * @param username The username of the user.
     * @param password The password of the user.
     * @return The user with the specified username and password, or null if no match is found.
     */
    @Query("SELECT * FROM cart_users WHERE username = :username AND password = :password LIMIT 1")
    Users getLogin(String username, String password);

    /**
     * Clears all users from the cart_users table.
     * This operation will delete every user from the cart.
     */
    @Query("DELETE FROM cart_users")
    void clearCart();

    /**
     * Deletes a specific user from the cart_users table by their ID.
     *
     * @param id The ID of the user to be deleted.
     */
    @Query("DELETE FROM cart_users WHERE id = :id")
    void deleteUsersById(int id);
}