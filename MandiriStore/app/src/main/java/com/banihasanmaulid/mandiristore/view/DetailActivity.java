package com.banihasanmaulid.mandiristore.view;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.banihasanmaulid.mandiristore.R;
import com.banihasanmaulid.mandiristore.model.Product;
import com.bumptech.glide.Glide;

/**
 * Activity to display detailed information about a product.
 * Shows the product's name, category, description, price, and image.
 */
public class DetailActivity extends AppCompatActivity {
    private TextView productName, productDescription, productCategory, productPrice;
    private ImageView productImage, btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_detail);

        initializeViews();
        loadProductDetails();

        btnBack.setOnClickListener(v -> getOnBackPressedDispatcher().onBackPressed());
    }

    /**
     * Initializes the views used in this activity.
     */
    private void initializeViews() {
        productName = findViewById(R.id.productName);
        productCategory = findViewById(R.id.productCategory);
        productDescription = findViewById(R.id.productDescription);
        productPrice = findViewById(R.id.price);
        productImage = findViewById(R.id.productImage);
        btnBack = findViewById(R.id.back);
    }

    /**
     * Loads product details passed via intent and sets them in the UI.
     */
    private void loadProductDetails() {
        Product product = (Product) getIntent().getSerializableExtra("product");
        if (product != null) {
            productName.setText(product.getTitle());
            productCategory.setText(product.getCategory());
            productDescription.setText(product.getDescription());
            productPrice.setText(String.format("$%s", product.getPrice()));

            Glide.with(this)
                    .load(product.getImage())
                    .placeholder(R.drawable.anim_soon)
                    .error(R.drawable.anim_soon)
                    .into(productImage);
        }
    }
}