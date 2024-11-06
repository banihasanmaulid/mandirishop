package com.banihasanmaulid.mandiristore.data.local.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.banihasanmaulid.mandiristore.model.Product;

import java.util.List;

/**
 * Copyright (c) 2024 Mandiri-Store. All rights reserved. <br>
 * Created by {@author} <b>Bani Hasan Maulid</b> on {@since} <b>11/6/2024</b>
 */
@Dao
public interface ProductDao {

    @Insert
    void insertProduct(Product product);

    @Query("SELECT * FROM cart_products WHERE title = :name LIMIT 1")
    Product getProductByTitle(String name);

    @Query("SELECT * FROM cart_products")
    List<Product> getAllCartProducts();

    @Query("DELETE FROM cart_products")
    void clearCart();

    @Query("DELETE FROM cart_products WHERE id = :id")
    void deleteProductById(int id);
}