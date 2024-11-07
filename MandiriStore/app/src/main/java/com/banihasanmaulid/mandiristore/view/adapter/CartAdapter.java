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
 * --------------------------------------------------------------------------
 * CartAdapter
 * Adapter for displaying products in a cart using RecyclerView.
 * It provides functionality to:
 * - Display product details (name, description, image).
 * - Handle delete action through an `OnDeleteClickListener`.
 * - Track selected products with checkboxes.
 *
 * Key Features:
 * - Glide is used for image loading with placeholders.
 * - SparseBooleanArray manages selected items efficiently.
 */
public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {

    private final List<Product> cartProductList;
    private final OnDeleteClickListener onDeleteClickListener;
    private final SparseBooleanArray selectedItems = new SparseBooleanArray();

    /**
     * Constructor for CartAdapter.
     *
     * @param cartProductList List of products in the cart
     * @param onDeleteClickListener Listener for delete action on a product
     */
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

        // Set product details
        holder.productName.setText(product.getTitle());
        holder.productDescription.setText(product.getDescription());

        // Load product image with Glide
        Glide.with(holder.itemView.getContext())
                .load(product.getImage())
                .placeholder(R.drawable.anim_soon)
                .error(R.drawable.ic_broken_image)
                .into(holder.productImage);

        // Handle delete button click
        holder.delete.setOnClickListener(v -> onDeleteClickListener.onDelete(product.getId()));

        // Set checkbox status based on selection
        holder.cbItem.setChecked(selectedItems.get(position, false));
        holder.cbItem.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                selectedItems.put(position, true);
            } else {
                selectedItems.delete(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return cartProductList.size();
    }

    /**
     * Returns a list of products selected by the user.
     *
     * @return List of selected products
     */
    public List<Product> getSelectedProducts() {
        List<Product> selectedProducts = new ArrayList<>();
        for (int i = 0; i < cartProductList.size(); i++) {
            if (selectedItems.get(i, false)) {
                selectedProducts.add(cartProductList.get(i));
            }
        }
        return selectedProducts;
    }

    /**
     * Interface for handling delete actions.
     */
    public interface OnDeleteClickListener {
        void onDelete(int productId);
    }

    /**
     * ViewHolder for cart items, holding references to UI elements.
     */
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
