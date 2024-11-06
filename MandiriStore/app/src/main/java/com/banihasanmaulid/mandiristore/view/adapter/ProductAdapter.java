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
 */
public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {

    private List<Product> productList;

    public ProductAdapter(List<Product> productList) {
        this.productList = productList;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Product product = productList.get(position);
        holder.productName.setText(product.getTitle());
        holder.productCategory.setText(product.getCategory());
        holder.productDescription.setText(product.getDescription());

        Glide.with(holder.itemView.getContext())
                .load(product.getImage())
                .placeholder(R.drawable.anim_soon)
                .error(R.drawable.ic_broken_image)
                .into(holder.productImage);

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), DetailActivity.class);
            intent.putExtra("product", product);
            v.getContext().startActivity(intent);
        });

        holder.btnSeeMore.setOnClickListener(v -> {
            AppDatabase db = AppDatabase.getInstance(v.getContext());
            new Thread(() -> {
                Product existingProduct = db.productDao().getProductByTitle(product.getTitle());
                if (existingProduct != null) {
                    ((Activity) v.getContext()).runOnUiThread(() ->
                            Toast.makeText(v.getContext(), "Produk sudah ada di keranjang", Toast.LENGTH_SHORT).show()
                    );
                } else {
                    db.productDao().insertProduct(product);
                    ((Activity) v.getContext()).runOnUiThread(() ->
                        Toast.makeText(v.getContext(), "Produk berhasil ditambahkan ke keranjang", Toast.LENGTH_SHORT).show()
                    );

                    Intent intent = new Intent(v.getContext(), CartActivity.class);
                    intent.putExtra("product", product);
                    v.getContext().startActivity(intent);
                }
            }).start();
        });
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public static class ProductViewHolder extends RecyclerView.ViewHolder {
        TextView productName, productDescription, productCategory, price;
        ImageView productImage;
        Button btnSeeMore;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            productName = itemView.findViewById(R.id.productName);
            productCategory = itemView.findViewById(R.id.productCategory);
            productDescription = itemView.findViewById(R.id.productDescription);
            price = itemView.findViewById(R.id.price);
            productImage = itemView.findViewById(R.id.productImage);
            btnSeeMore = itemView.findViewById(R.id.btnSeeMore);
        }
    }
}
