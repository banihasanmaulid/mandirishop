package com.banihasanmaulid.mandiristore.data.local.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.banihasanmaulid.mandiristore.model.Product;

import java.util.List;

/**
 * Copyright (c) 2024 Mandiri-Store. All rights reserved. <br>
 * Created by {@author} <b>Bani Hasan Maulid</b> on {@since} <b>11/6/2024</b>
 * --------------------------------------------------------------------------
 * ProductDao interface serves as the Data Access Object (DAO) for the Product entity.
 * It provides methods to interact with the database (Room) for performing CRUD operations on the cart_products table.
 * This interface includes methods for inserting, querying, and deleting products from the cart.
 * Each method corresponds to a specific SQL query that Room will automatically implement.
 */
@Dao
public interface ProductDao {

    // Insert a product into the cart_products table
    @Insert
    void insertProduct(Product product);

    // Retrieve a product from cart_products table by its title (LIMIT 1 ensures only one result)
    @Query("SELECT * FROM cart_products WHERE title = :name LIMIT 1")
    Product getProductByTitle(String name);

    // Retrieve all products from the cart_products table
    @Query("SELECT * FROM cart_products")
    List<Product> getAllCartProducts();

    // Clear all products from the cart_products table
    @Query("DELETE FROM cart_products")
    void clearCart();

    // Delete a specific product from the cart_products table by its ID
    @Query("DELETE FROM cart_products WHERE id = :id")
    void deleteProductById(int id);
}