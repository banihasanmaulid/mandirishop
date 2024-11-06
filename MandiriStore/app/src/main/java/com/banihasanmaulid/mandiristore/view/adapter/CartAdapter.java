package com.banihasanmaulid.mandiristore.view.adapter;

import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.banihasanmaulid.mandiristore.R;
import com.banihasanmaulid.mandiristore.model.Product;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

/**
 * Copyright (c) 2024 Mandiri-Store. All rights reserved. <br>
 * Created by {@author} <b>Bani Hasan Maulid</b> on {@since} <b>11/6/2024</b>
 */
public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {

    private List<Product> cartProductList;
    private OnDeleteClickListener onDeleteClickListener;
    private SparseBooleanArray selectedItems = new SparseBooleanArray();

    public CartAdapter(List<Product> cartProductList, OnDeleteClickListener onDeleteClickListener) {
        this.cartProductList = cartProductList;
        this.onDeleteClickListener = onDeleteClickListener;
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cart_product, parent, false);
        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        Product product = cartProductList.get(position);
        holder.productName.setText(product.getTitle());
        holder.productDescription.setText(product.getDescription());
        holder.delete.setOnClickListener(v -> onDeleteClickListener.onDelete(product.getId()));

        Glide.with(holder.itemView.getContext())
                .load(product.getImage())
                .placeholder(R.drawable.anim_soon)
                .error(R.drawable.ic_broken_image)
                .into(holder.productImage);

        holder.cbItem.setChecked(selectedItems.get(position, false));
        holder.cbItem.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                selectedItems.put(position, true);
            } else {
                selectedItems.delete(position);
            }
        });
    }

    public List<Product> getSelectedProducts() {
        List<Product> selectedProducts = new ArrayList<>();
        for (int i = 0; i < cartProductList.size(); i++) {
            if (selectedItems.get(i, false)) {
                selectedProducts.add(cartProductList.get(i));
            }
        }
        return selectedProducts;
    }

    @Override
    public int getItemCount() {
        return cartProductList.size();
    }

    public interface OnDeleteClickListener {
        void onDelete(int productId);
    }

    public static class CartViewHolder extends RecyclerView.ViewHolder {
        TextView productName, productDescription;
        ImageView productImage, delete;
        CheckBox cbItem;

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            productName = itemView.findViewById(R.id.productName);
            productDescription = itemView.findViewById(R.id.productDescription);
            productImage = itemView.findViewById(R.id.productImage);
            delete = itemView.findViewById(R.id.delete);
            cbItem = itemView.findViewById(R.id.cbItem);
        }
    }
}
