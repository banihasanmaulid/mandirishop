package com.banihasanmaulid.mandiristore.view.adapter;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.banihasanmaulid.mandiristore.R;
import com.banihasanmaulid.mandiristore.data.local.AppDatabase;
import com.banihasanmaulid.mandiristore.model.Product;
import com.banihasanmaulid.mandiristore.view.CartActivity;
import com.banihasanmaulid.mandiristore.view.DetailActivity;
import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Copyright (c) 2024 Mandiri-Store. All rights reserved. <br>
 * Created by {@author} <b>Bani Hasan Maulid</b> on {@since} <b>11/6/2024</b>
 * --------------------------------------------------------------------------
 * ProductAdapter is the adapter for RecyclerView used to display a list of products.
 * Each item in the RecyclerView contains product details such as name, category, description,
 * price, and product image. Products can be added to the shopping cart by clicking the "See More" button.
 *
 * Key Features:
 * - Displays product details using Glide to load images.
 * - Allows interaction to view product details and add products to the cart.
 * - Checks if a product is already in the cart and shows a Toast notification accordingly.
 */
public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {

    private List<Product> productList;

    // Constructor untuk menginisialisasi list produk
    public ProductAdapter(List<Product> productList) {
        this.productList = productList;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate layout item_product untuk setiap item pada RecyclerView
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Product product = productList.get(position);

        // Set data ke UI
        holder.productName.setText(product.getTitle());
        holder.productCategory.setText(product.getCategory());
        holder.productDescription.setText(product.getDescription());
        holder.price.setText(String.format("%s %s", "$", product.getPrice()));

        // Load gambar produk menggunakan Glide
        Glide.with(holder.itemView.getContext())
                .load(product.getImage())
                .placeholder(R.drawable.anim_soon)
                .error(R.drawable.ic_broken_image)
                .into(holder.productImage);

        // Set listener untuk item klik, mengarahkan ke DetailActivity
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), DetailActivity.class);
            intent.putExtra("product", product);
            v.getContext().startActivity(intent);
        });

        // Set listener untuk tombol "See More", untuk menambahkan produk ke cart
        holder.btnSeeMore.setOnClickListener(v -> {
            AppDatabase db = AppDatabase.getInstance(v.getContext());
            new Thread(() -> {
                Product existingProduct = db.productDao().getProductByTitle(product.getTitle());
                // Jika produk sudah ada di cart, tampilkan toast
                if (existingProduct != null) {
                    ((Activity) v.getContext()).runOnUiThread(() ->
                            Toast.makeText(v.getContext(), v.getContext().getString(R.string.product_in_cart), Toast.LENGTH_SHORT).show()
                    );
                } else {
                    // Jika belum ada, insert produk ke database dan beri notifikasi
                    db.productDao().insertProduct(product);
                    ((Activity) v.getContext()).runOnUiThread(() ->
                            Toast.makeText(v.getContext(), v.getContext().getString(R.string.product_added_to_cart), Toast.LENGTH_SHORT).show()
                    );

                    // Arahkan ke CartActivity
                    Intent intent = new Intent(v.getContext(), CartActivity.class);
                    intent.putExtra("product", product);
                    v.getContext().startActivity(intent);
                }
            }).start();
        });
    }

    @Override
    public int getItemCount() {
        return productList.size(); // Mengembalikan jumlah item
    }

    public static class ProductViewHolder extends RecyclerView.ViewHolder {
        TextView productName, productDescription, productCategory, price;
        ImageView productImage;
        Button btnSeeMore;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            // Inisialisasi UI element dari layout item_product
            productName = itemView.findViewById(R.id.productName);
            productCategory = itemView.findViewById(R.id.productCategory);
            productDescription = itemView.findViewById(R.id.productDescription);
            price = itemView.findViewById(R.id.price);
            productImage = itemView.findViewById(R.id.productImage);
            btnSeeMore = itemView.findViewById(R.id.btnSeeMore);
        }
    }
}
